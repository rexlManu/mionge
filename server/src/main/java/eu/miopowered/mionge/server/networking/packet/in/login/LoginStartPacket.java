package eu.miopowered.mionge.server.networking.packet.in.login;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;
import io.netty.buffer.ByteBuf;

public class LoginStartPacket extends IncomingPacket {

    private String playerName;

    public LoginStartPacket() {
        super(0x00, PacketState.LOGIN, PacketDirection.SERVER);
    }

    @Override
    public void read(ByteBuf byteBuf) {
        this.playerName = ByteBufMessage.readString(byteBuf);
    }
}
