package lws.banksystem.client.network;

import lws.banksystem.client.cryption.SHA512;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Network {

    public static NetworkHandler handler;
    public static PrivateKey ownPrivateKey;
    public static PublicKey ownPublicKey;
    public static PublicKey otherPublicKey;

    private static void crypt() {

    }

    public static NetworkResponse login(String userID, String password) {
        handler = new NetworkHandler("172.17.186.133", 7347);
        handler.connect();
        handler.send("Konto-Login");
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(userID);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(SHA512.crypt(password));
        String response = handler.recive();
        if(response.equals("Login-TRUE")) {
            return NetworkResponse.allow;
        } else if(response.equals("Login-FALSE")) {
            return NetworkResponse.deny;
        } else {
            return NetworkResponse.error;
        }
    }

    public static int register(String firstName, String lastName, String mail, String birthdate, String street, String houseNumber, String city, String zipCode, String password) {
        handler = new NetworkHandler("172.17.186.133", 7347);
        handler.connect();
        handler.send("System-Register");
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(firstName);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(lastName);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(birthdate);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(mail);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(street);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(houseNumber);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(zipCode);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(city);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(SHA512.crypt(password));
        int response = -34732434;
        String recived = handler.recive();
        try {
            response = Integer.valueOf(recived);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(recived);
        }
        return response;
    }

    public static NetworkResponse remove(String ID) {
        handler.send("System-Remove");
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(ID);
        String response = handler.recive();
        if(response.equals("Remove-TRUE")) {
            return NetworkResponse.allow;
        } else if(response.equals("Remove-FALSE")) {
            return NetworkResponse.deny;
        } else {
            return NetworkResponse.error;
        }
    }

    public static String getBalance() {
        String balance = "-1";
        handler.send("Konto-Status");
        balance = handler.recive();
        return balance;
    }

    public static NetworkResponse sendMoney(String targetID, String ammount) {
        handler.send("Konto-TransferMoney");
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(targetID);
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(ammount);
        String response = handler.recive();
        if(response.equals("Transfer-TRUE")) {
            return NetworkResponse.allow;
        } else if(response.equals("Transfer-FALSE")) {
            return NetworkResponse.deny;
        } else {
            return NetworkResponse.error;
        }
    }

    public static NetworkResponse addMoney(String ammount) {
        handler.send("Konto-AddMoney");
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.send(ammount);
        return NetworkResponse.error;
    }

    public static NetworkResponse getMoney(String ammount) {

        return NetworkResponse.error;
    }

    public static void logout() {
        handler.send("System-Disconnect");
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        handler.disconnect();
    }

    public static String getUsername() {
        String username = "";
        return username;
    }

    public static ArrayList<String> getLog() {
        ArrayList<String> log = new ArrayList<>();
        return log;
    }

    

}