package lws.banksystem.client.cryption.crypter;

public class Crypter {

    private static Crypter crypter;

    public static Crypter getInstance() {
        if(crypter == null) {
            crypter = new Crypter();
        }
        return crypter;
    }

    public Generator getGenerator() {
        return Generator.getInstance();
    }

    public Keys getKeys() {
        return Keys.getKeys();
    }

}
