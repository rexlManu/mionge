package eu.miopowered.mionge.server.networking.packet;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class RegisteredPacket {

    private PacketDirection direction;
    private PacketState state;
    private int id;
    private Class<? extends Packet> packet;

    public IncomingPacket createPacket(ByteBuf in) {
        try {
            IncomingPacket packet = (IncomingPacket) this.packet.getConstructors()[0].newInstance();
            packet.read(in);
            return packet;
        } catch (Exception e) {
            MiongeServer.server().logger().error("Couldn't create packet", e);
        }
        // Returning empty packet
        return null;
    }

}
