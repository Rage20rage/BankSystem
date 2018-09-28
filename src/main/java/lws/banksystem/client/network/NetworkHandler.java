package lws.banksystem.client.network;

import lws.banksystem.client.cryption.NetworkCrypt;
import lws.banksystem.client.cryption.crypter.Crypter;
import lws.banksystem.server.log.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class NetworkHandler extends Object {



    public NetworkHandler(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    private Socket socket;
    private String ipAddress;
    private int port;

    public synchronized void connect() {
        try {
            Logger.log("Verbinde mit Server...");
            socket = new Socket(ipAddress, port);
            Logger.log("Verbunden!");
            Logger.log("Verschlüssle Verbindung...");
            NetworkCrypt.inizialisizeClient(socket);
            Logger.log("Verbindung Verschlüsselt!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void disconnect() {
        try {
            Logger.log("Trenne Verbindung...");
            socket.close();
            socket = null;
            Logger.log("Verbindung getrennt!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isConnected() {
        if (socket == null) {
            return false;
        } else {
            return true;
        }
    }

    public synchronized void send(String message) {
        try {
            Logger.log("Bereite Daten zum Versenden vor...");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            message = Crypter.getInstance().getGenerator().EncryptMessage(message, Network.ownPrivateKey);
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

    public synchronized String recive() {
        String message = null;
        try {
            Logger.log("Warte auf Daten...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            message = reader.readLine();
            message = Crypter.getInstance().getGenerator().getDecryptMessage(message,Network.otherPublicKey);
            Logger.log("Daten bekommen: " + message);
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

    public synchronized void objectSend(Object object) {
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

    public synchronized Object objectRecive() {
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