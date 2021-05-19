package eu.miopowered.mionge.server.networking.packet.in;

import eu.miopowered.mionge.server.networking.packet.Packet;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import io.netty.buffer.ByteBuf;

public abstract class IncomingPacket extends Packet {

    public IncomingPacket(int id, PacketState state, PacketDirection direction) {
        super(id, state, direction);
    }

    public abstract void read(ByteBuf byteBuf);
}
