package eu.miopowered.mionge.server.networking.packet.out.status;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.out.OutgoingPacket;
import io.netty.buffer.ByteBuf;

public class PongPacket extends OutgoingPacket {

    private long payload;

    public PongPacket(long payload) {
        super(0x01, PacketState.STATUS, PacketDirection.CLIENT);

        this.payload = payload;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        ByteBufMessage.writeVarLong(this.payload, byteBuf);
    }
}
