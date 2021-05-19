package eu.miopowered.mionge.server.networking.netty.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import javax.crypto.Cipher;
import java.util.List;

public class EncryptionHandler extends MessageToMessageCodec<ByteBuf, ByteBuf> {

    private EncryptionContextCodec encodeCodec, decodeCodec;

    public EncryptionHandler(byte[] sharedSecret) {
        this.encodeCodec = new EncryptionContextCodec(Cipher.ENCRYPT_MODE, sharedSecret);
        this.decodeCodec = new EncryptionContextCodec(Cipher.DECRYPT_MODE, sharedSecret);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        this.encodeCodec.crypt(msg, out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        this.encodeCodec.crypt(msg, out);
    }
}
