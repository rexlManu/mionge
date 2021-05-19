package eu.miopowered.mionge.server.networking.packet.out;

import eu.miopowered.mionge.server.networking.packet.Packet;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import io.netty.buffer.ByteBuf;

public abstract class OutgoingPacket extends Packet {
    public OutgoingPacket(int id, PacketState state, PacketDirection direction) {
        super(id, state, direction);
    }

    public abstract void write(ByteBuf byteBuf);
}
