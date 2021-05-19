package eu.miopowered.mionge.server.networking.netty.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class EncryptionContextCodec {

    private int mode;
    private byte[] sharedSecret;
    private Cipher cipher;

    public EncryptionContextCodec(int mode, byte[] sharedSecret) {
        this.mode = mode;
        this.sharedSecret = sharedSecret;
        try {
            this.cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(sharedSecret, "AES");
            this.cipher.init(this.mode, keySpec, new IvParameterSpec(keySpec.getEncoded()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void crypt(ByteBuf message, List<Object> out) {
        ByteBuffer buffer = ByteBuffer.allocate(message.readableBytes());

        try {
            cipher.update(message.nioBuffer(), buffer);
        } catch (ShortBufferException e) {
            e.printStackTrace();
        }

        buffer.flip();
        out.add(Unpooled.wrappedBuffer(buffer));
    }
}
