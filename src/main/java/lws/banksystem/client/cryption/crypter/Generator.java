package lws.banksystem.client.cryption.crypter;

import javax.crypto.*;
import java.security.*;
import java.util.Base64;

public class Generator {

    private KeyPairGenerator keyGenerator;
    private KeyPair keyPair;
    private PrivateKey ownPrivateKey;
    private PublicKey ownPublicKey;
    private PublicKey otherPublicKey;
    private final int keySize = 4096;

    private static Generator generator;

    protected static Generator getInstance() {
        if(generator == null) {
            try {
                generator = new Generator();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return generator;
    }

    public Generator() throws NoSuchAlgorithmException {
        keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(keySize);
    }

    public void setOtherPublicKey(PublicKey key) {

    }

    public void createKeys() {
        keyPair = keyGenerator.genKeyPair();
        ownPrivateKey = keyPair.getPrivate();
        ownPublicKey = keyPair.getPublic();
        Keys.getKeys().privateKey = ownPrivateKey;
        Keys.getKeys().publicKey = ownPublicKey;
    }

    public String EncryptMessage(String message, PrivateKey ownPrivateKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, ownPrivateKey);
        byte[] b = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(b);
    }

    public String getDecryptMessage(String message, PublicKey otherPublicKey) throws BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, otherPublicKey);
            byte[] b = cipher.doFinal(Base64.getDecoder().decode(message));
        return new String(b);
    }

}
