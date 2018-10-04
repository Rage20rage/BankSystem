package lws.banksystem.server.network;

import lws.banksystem.server.log.Logger;

import javax.net.ssl.SSLSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Connections extends Thread {

    public Socket socket;
    public PrivateKey ownPrivateKey;
    public PublicKey ownPublicKey;
    public PublicKey otherPublicKey;

    public Connections(Socket socket) {
        this.socket = socket;
    }

    public boolean loggedIn = false;
    public boolean continu = true;
    public boolean first = true;

    public String id;

    @Override
    public void run() {
        while (continu) {
            if(this.continu) {
                first = false;
            }
            String action = NetworkHandler.recive(this);
            if(action == null) {
                NetworkHandler.disconnect(this);
            }
            Action.execute(action, this);
        }
    }

}
