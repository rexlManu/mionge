package eu.miopowered.mionge.server.networking.packet.out.login;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.out.OutgoingPacket;
import io.netty.buffer.ByteBuf;

public class EncryptionRequestPacket extends OutgoingPacket {

    private String serverId;
    private byte[] publicKey;
    private byte[] verifyToken;

    public EncryptionRequestPacket(String serverId, byte[] publicKey, byte[] verifyToken) {
        super(0x01, PacketState.LOGIN, PacketDirection.CLIENT);

        this.serverId = serverId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        ByteBufMessage.writeString(this.serverId, byteBuf);
        ByteBufMessage.writeByteArray(this.publicKey, byteBuf);
        ByteBufMessage.writeByteArray(this.verifyToken, byteBuf);
    }
}
