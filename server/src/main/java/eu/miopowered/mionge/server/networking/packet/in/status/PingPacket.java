package eu.miopowered.mionge.server.networking.packet.in.status;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PingPacket extends IncomingPacket {

    private long payload;

    public PingPacket() {
        super(0x01, PacketState.STATUS, PacketDirection.SERVER);
    }

    @Override
    public void read(ByteBuf byteBuf) {
        this.payload = ByteBufMessage.readVarLong(byteBuf);
    }
}
