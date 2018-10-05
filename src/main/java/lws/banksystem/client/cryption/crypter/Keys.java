package lws.banksystem.client.cryption.crypter;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Keys {

    protected PrivateKey privateKey;
    protected PublicKey publicKey;

    private static Keys keys;

    protected static Keys getKeys() {
        if(keys == null) {
            keys = new Keys();
        }
     return keys;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}
