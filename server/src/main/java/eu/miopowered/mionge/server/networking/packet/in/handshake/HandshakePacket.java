package eu.miopowered.mionge.server.networking.packet.in.handshake;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class HandshakePacket extends IncomingPacket {

    private int protocolVersion;
    private String serverAddress;
    private short serverPort;
    private PacketState nextState;

    public HandshakePacket() {
        super(0, PacketState.HANDSHAKE, PacketDirection.SERVER);
    }

    @Override
    public void read(ByteBuf byteBuf) {
        this.protocolVersion = ByteBufMessage.readVarInt(byteBuf);
        this.serverAddress = ByteBufMessage.readString(byteBuf);
        this.serverPort = ByteBufMessage.readShort(byteBuf);
        this.nextState = PacketState.stateById(ByteBufMessage.readVarInt(byteBuf));
    }
}
