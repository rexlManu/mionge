package eu.miopowered.mionge.server.networking.packet.out.login;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.out.OutgoingPacket;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class LoginSuccessPacket extends OutgoingPacket {

    private UUID uuid;
    private String username;

    public LoginSuccessPacket(UUID uuid, String username) {
        super(0x02, PacketState.LOGIN, PacketDirection.CLIENT);
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        ByteBufMessage.writeUuid(this.uuid, byteBuf);
        ByteBufMessage.writeString(this.username, byteBuf);
    }
}
