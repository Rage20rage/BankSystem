package lws.banksystem.server.network;

import lws.banksystem.client.network.NetworkResponse;
import lws.banksystem.server.MySQL.MySQL;
import lws.banksystem.server.log.Kontoauszug;

public class Action {

    public static void execute(String action, Connections connection) {
        if (!action.equals("Konto-Login") && !action.equals("System-Register") && !connection.loggedIn) {
            NetworkHandler.send(connection, "NO-Action");
            NetworkHandler.disconnect(connection);
        }
        if (action.equals("Konto-Login")) {
            String userID = NetworkHandler.recive(connection);
            String password = NetworkHandler.recive(connection);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NetworkResponse response = MySQL.login(userID, password);
            if (response == NetworkResponse.allow) {
                NetworkHandler.send(connection, "Login-TRUE");
                connection.loggedIn = true;
                connection.id = userID;
            } else if (response == NetworkResponse.deny) {
                NetworkHandler.send(connection, "Login-FALSE");
                NetworkHandler.disconnect(connection);
            } else {
                NetworkHandler.send(connection, "Login-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if (action.equals("System-Register")) {
            if (AntiDos.getInstance().addAddress(new String(connection.socket.getInetAddress().getHostAddress()))) {
                String firstName = NetworkHandler.recive(connection);
                String lastName = NetworkHandler.recive(connection);
                String birthdate = NetworkHandler.recive(connection);
                String mail = NetworkHandler.recive(connection);
                String street = NetworkHandler.recive(connection);
                String houseNumber = NetworkHandler.recive(connection);
                String zipCode = NetworkHandler.recive(connection);
                String city = NetworkHandler.recive(connection);
                String password = NetworkHandler.recive(connection);
                int tmp = MySQL.register(firstName, lastName, mail, birthdate, street, houseNumber, city, zipCode, password);
                Kontoauszug.createPostBox(String.valueOf(String.valueOf(tmp)));
                Kontoauszug.fileWriterAttribute(String.valueOf(tmp), (firstName + " " + lastName));
                NetworkHandler.send(connection, String.valueOf(tmp));
                NetworkHandler.disconnect(connection);
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NetworkHandler.send(connection, "-2");
                NetworkHandler.disconnect(connection);
            }
        } else if (action.equals("System-Remove")) {
            String id = NetworkHandler.recive(connection);
            NetworkResponse response = MySQL.remove(id);
            if (response == NetworkResponse.allow) {
                NetworkHandler.send(connection, "Remove-TRUE");
            } else if (response == NetworkResponse.deny) {
                NetworkHandler.send(connection, "Remove-FALSE");
            } else {
                NetworkHandler.send(connection, "Remove-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if (action.equals("System-Disconnect")) {
            NetworkHandler.disconnect(connection);
        } else if (action.equals("Konto-Status")) {
            String balance = String.valueOf(MySQL.getBalance(connection.id));
            NetworkHandler.send(connection, balance);
        } else if (action.equals("Konto-AddMoney")) {
            String ammount = NetworkHandler.recive(connection);
            NetworkResponse response = MySQL.addMoney(connection.id, ammount);
            if (response == NetworkResponse.allow) {
                Kontoauszug.dataSevr(connection.id, ammount, "Einzahlen");
                NetworkHandler.send(connection, "Add-TRUE");
            } else {
                NetworkHandler.send(connection, "Add-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if (action.equals("Konto-GetMoney")) {
            String ammount = NetworkHandler.recive(connection);
            NetworkResponse response = MySQL.getMoney(connection.id, ammount);
            if (response == NetworkResponse.allow) {
                Kontoauszug.dataSevr(connection.id, ammount, "Auszahlen");
                NetworkHandler.send(connection, "Get-TRUE");
            } else if (response == NetworkResponse.deny) {
                NetworkHandler.send(connection, "Get-FALSE");
            } else {
                NetworkHandler.send(connection, "Get-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if (action.equals("Konto-TransferMoney")) {
            String targetID = NetworkHandler.recive(connection);
            String amount = NetworkHandler.recive(connection);
            NetworkResponse response = MySQL.sendMoney(connection.id, targetID, amount);
            if (response == NetworkResponse.allow) {
                Kontoauszug.dataSevr(connection.id, amount, ("Überweizen an Konto-ID: " + targetID));
                NetworkHandler.send(connection, "Transfer-TRUE");
            } else if (response == NetworkResponse.deny) {
                NetworkHandler.send(connection, "Transfer-FALSE");
            } else {
                NetworkHandler.send(connection, "Transfer-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if (action.equals("Konto-Username")) {
            String[] user = new String[2];
            user = MySQL.getUser(connection.id);
            NetworkHandler.send(connection, user[0]);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NetworkHandler.send(connection, user[1]);
        } else if (action.equals("Konto-LOG")) {
            String data = Kontoauszug.readAccontData(connection.id);
            String[] user = MySQL.getUser(connection.id);
            Kontoauszug.dataMoving(connection.id, (user[0] + " " + user[1]));
            NetworkHandler.send(connection, data);
        } else {
            try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
            NetworkHandler.send(connection, "NO-Action");
        }
    }

}
