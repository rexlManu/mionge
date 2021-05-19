package eu.miopowered.mionge.server.networking.packet;

import eu.miopowered.mionge.server.networking.packet.in.handshake.HandshakePacket;
import eu.miopowered.mionge.server.networking.packet.in.login.EncryptionResponsePacket;
import eu.miopowered.mionge.server.networking.packet.in.login.LoginStartPacket;
import eu.miopowered.mionge.server.networking.packet.in.status.PingPacket;
import eu.miopowered.mionge.server.networking.packet.in.status.StatusRequestPacket;
import eu.miopowered.mionge.server.networking.packet.out.login.DisconnectLoginPacket;
import eu.miopowered.mionge.server.networking.packet.out.login.EncryptionRequestPacket;
import eu.miopowered.mionge.server.networking.packet.out.status.PongPacket;
import eu.miopowered.mionge.server.networking.packet.out.status.StatusResponsePacket;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class PacketRegistry {

    private List<RegisteredPacket> registeredPackets;

    public PacketRegistry() {
        this.registeredPackets = new ArrayList<>();

        this.registerPackets();
    }

    private void registerPackets() {
        // Packets for the server
        this.register(PacketDirection.SERVER, PacketState.HANDSHAKE, 0, HandshakePacket.class);
        this.register(PacketDirection.SERVER, PacketState.STATUS, 0, StatusRequestPacket.class);
        this.register(PacketDirection.SERVER, PacketState.STATUS, 0x01, PingPacket.class);
        this.register(PacketDirection.SERVER, PacketState.LOGIN, 0x00, LoginStartPacket.class);
        this.register(PacketDirection.SERVER, PacketState.LOGIN, 0x01, EncryptionResponsePacket.class);

        // Packets for the client
        this.register(PacketDirection.CLIENT, PacketState.STATUS, 0, StatusResponsePacket.class);
        this.register(PacketDirection.CLIENT, PacketState.STATUS, 0x01, PongPacket.class);
        this.register(PacketDirection.CLIENT, PacketState.LOGIN, 0, DisconnectLoginPacket.class);
        this.register(PacketDirection.CLIENT, PacketState.LOGIN, 0x01, EncryptionRequestPacket.class);
    }

    private void register(PacketDirection direction, PacketState state, int id, Class<? extends Packet> packet) {
        this.registeredPackets.add(new RegisteredPacket(direction, state, id, packet));
    }
}
