package eu.miopowered.mionge.server.networking.packet.out.login;

import com.google.gson.JsonObject;
import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.PacketDirection;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.out.OutgoingPacket;
import io.netty.buffer.ByteBuf;

public class DisconnectLoginPacket extends OutgoingPacket {

    private String message;

    public DisconnectLoginPacket(String message) {
        super(0, PacketState.LOGIN, PacketDirection.CLIENT);

        this.message = message;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", this.message);
        ByteBufMessage.writeString(jsonObject.toString(), byteBuf);
    }
}
