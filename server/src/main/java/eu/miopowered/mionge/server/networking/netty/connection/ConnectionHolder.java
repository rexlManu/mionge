package eu.miopowered.mionge.server.networking.netty.connection;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.netty.encryption.EncryptionHandler;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.RegisteredPacket;
import eu.miopowered.mionge.server.networking.packet.out.OutgoingPacket;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Optional;
import java.util.Random;

@Data
@Accessors(fluent = true)
public class ConnectionHolder {

    private static final Random RANDOM = new Random();

    private Channel channel;
    private PacketState state;
    private byte[] verifyToken;
    private boolean encrypted;

    private byte[] sharedSecret;

    public ConnectionHolder(Channel channel) {
        this.channel = channel;
        this.state = PacketState.HANDSHAKE;
        this.verifyToken = new byte[4];
        this.encrypted = false;

        RANDOM.nextBytes(this.verifyToken);
    }

    public void enableEncryption(byte[] sharedSecret) {
        this.encrypted = true;
        this.sharedSecret = sharedSecret;

        this.channel.pipeline().addBefore("lengthDecoder","encryption", new EncryptionHandler(this.sharedSecret));
    }

    public void sendPacket(OutgoingPacket packet) {
        this.channel.writeAndFlush(packet);
    }

    public Optional<RegisteredPacket> findPacketById(int id) {
        return MiongeServer
                .server()
                .networkHandler()
                .registry()
                .registeredPackets()
                .stream()
                .filter(registeredPacket -> registeredPacket.state().equals(this.state) && registeredPacket.id() == id)
                .findFirst();
    }
}
