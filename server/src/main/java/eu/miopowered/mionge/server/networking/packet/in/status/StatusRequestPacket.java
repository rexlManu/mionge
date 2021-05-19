package eu.miopowered.mionge.server.networking.packet.in.status;

import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;
import io.netty.buffer.ByteBuf;

public class StatusRequestPacket extends IncomingPacket {
    public StatusRequestPacket() {
        super(0, PacketState.STATUS, PacketDirection.SERVER);
    }

    @Override
    public void read(ByteBuf byteBuf) {

    }
}
