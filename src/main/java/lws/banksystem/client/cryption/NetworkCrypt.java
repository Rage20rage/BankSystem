package lws.banksystem.client.cryption;

import lws.banksystem.client.network.Network;
import lws.banksystem.server.network.Connections;
import lws.banksystem.server.network.NetworkHandler;
import lws.banksystem.client.cryption.crypter.Crypter;

import java.net.Socket;
import java.security.PublicKey;

public class NetworkCrypt {

    public static Connections inizialisizeServer(Connections connections) {
        Crypter.getInstance().getGenerator().createKeys();
        connections.ownPrivateKey = Crypter.getInstance().getKeys().getPrivateKey();
        connections.ownPublicKey = Crypter.getInstance().getKeys().getPublicKey();
        NetworkHandler.objectSend(connections.socket, connections.ownPublicKey);
        connections.otherPublicKey = (PublicKey) NetworkHandler.objectRecive(connections.socket);
        return connections;
    }

    public static void inizialisizeClient(Socket socket) {
        Network.otherPublicKey = (PublicKey) Network.handler.objectRecive();
        Crypter.getInstance().getGenerator().createKeys();
        Network.ownPrivateKey = Crypter.getInstance().getKeys().getPrivateKey();
        Network.ownPublicKey = Crypter.getInstance().getKeys().getPublicKey();
        Network.handler.objectSend(Crypter.getInstance().getKeys().getPublicKey());
    }

}
