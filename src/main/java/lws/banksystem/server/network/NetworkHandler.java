package lws.banksystem.server.network;

import lws.banksystem.client.cryption.NetworkCrypt;
import lws.banksystem.client.cryption.crypter.Crypter;
import lws.banksystem.client.network.Network;
import lws.banksystem.client.network.NetworkConfig;
import lws.banksystem.server.log.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NetworkHandler {

    private static Socket socket;
    private static ServerSocket serverSocket;
    private static int port;
    public static List<Connections> connections = new ArrayList<Connections>();

    public static void start(int port) {
        try {
            Logger.log("Starte Netzwerk-Server...");
            serverSocket = new ServerSocket(port);
            Logger.log("Netzwerk-Server gestartet! Port=" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            Logger.log("Warte auf Client Verbindung...");
            socket = serverSocket.accept();
            Connections connection = new Connections(socket);
            Logger.log("Verschlüssle Verbindung...");
            connection = NetworkCrypt.inizialisizeServer(connection);
            Logger.log("Verbindung verschlüsselt!");
            connections.add(connection);
            Main.startThread(new Thread(connection));
            Logger.log("Client mit der IP: \"" + socket.getInetAddress().getHostAddress() + "\" hat sich verbunden!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(Connections connection) {
        try {
            Logger.log("Trenne Verbindung von einem Client...");
            connection.socket.close();
            connection.socket = null;
            connection.continu = false;
            connections.remove(connection);
            Logger.log("Verbindung getrennt!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnected(Connections connection) {
        return connection.continu;
    }

    public static void disconnctInActiveConnections() {
        Logger.log("Schließe alle Inaktiven Verbindungen...");
        int counter = 0;
        for (Connections connection : connections) {
            if(isConnected(connection)) {
                counter++;
                connection.continu = false;
                connections.remove(connection);
            }
        }
        if(counter == 0) {
            Logger.log("Es wurden " + counter + " Verbindungen geschlossen!");
        } else {
            Logger.log("Es wurden keine Verbindungen geschlossen!");
        }
    }

    public static void send(Connections connections, String message) {
        try {
            Logger.log("Bereite Daten zum Versenden vor...");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connections.socket.getOutputStream()));
            message = Crypter.getInstance().getGenerator().EncryptMessage(message,connections.ownPrivateKey);
            writer.write(message);
            writer.newLine();
            Logger.log("Sende Daten...");
            writer.flush();
            Logger.log("Daten gesendet: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }

    public static String recive(Connections connections) {
        String message = null;
        try {
            Logger.log("Warte auf Daten...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connections.socket.getInputStream()));
            message = reader.readLine();
            message = Crypter.getInstance().getGenerator().getDecryptMessage(message,connections.otherPublicKey);
            Logger.log("Daten bekommen: " + message);
        } catch (NullPointerException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void objectSend(Socket socket, Object object) {
        try {
            Logger.log("Bereite Object zum Versenden vor...");
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);
            Logger.log("Sende Object...");
            out.flush();
            Logger.log("Object gesendet: " + object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object objectRecive(Socket socket) {
        Object object = null;
        try {
            Logger.log("Warten auf Objekt...");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            object = in.readObject();
            Logger.log("Object bekommen: " + object);
        } catch (NullPointerException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}