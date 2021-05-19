package eu.miopowered.mionge.server.networking.netty.encryption;

import lombok.Getter;
import lombok.experimental.Accessors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Arrays;

@Getter
@Accessors(fluent = true)
public class EncryptionManager {

    private KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;

    private Key publicKey;

    public EncryptionManager() {
        try {
            this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            this.keyPairGenerator.initialize(1024);

            this.keyPair = this.keyPairGenerator.generateKeyPair();
            this.publicKey = this.keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyToken(byte[] verifyToken, byte[] encryptedToken) {
        return Arrays.equals(verifyToken, decrypt(encryptedToken));
    }

    public byte[] decrypt(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.keyPair.getPrivate());
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
