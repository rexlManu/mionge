package eu.miopowered.mionge.server.networking.packet.in.login;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class EncryptionResponsePacket extends IncomingPacket {

    private byte[] sharedSecret, verifyToken;

    public EncryptionResponsePacket() {
        super(0x01, PacketState.LOGIN, PacketDirection.SERVER);
    }

    @Override
    public void read(ByteBuf byteBuf) {
        this.sharedSecret = ByteBufMessage.readByteArray(byteBuf);
        this.verifyToken = ByteBufMessage.readByteArray(byteBuf);
    }
}
