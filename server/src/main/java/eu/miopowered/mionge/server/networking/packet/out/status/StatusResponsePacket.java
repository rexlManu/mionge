package eu.miopowered.mionge.server.networking.packet.out.status;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.out.OutgoingPacket;
import eu.miopowered.mionge.server.status.ServerListPing;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class StatusResponsePacket extends OutgoingPacket {

    private ServerListPing serverListPing;

    public StatusResponsePacket(ServerListPing serverListPing) {
        super(0x00, PacketState.STATUS, PacketDirection.CLIENT);

        this.serverListPing = serverListPing;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        ByteBufMessage.writeJSON(this.serverListPing, byteBuf);
    }
}
