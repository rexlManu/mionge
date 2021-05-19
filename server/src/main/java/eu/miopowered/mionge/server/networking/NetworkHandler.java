package eu.miopowered.mionge.server.networking;

import eu.miopowered.mionge.server.networking.listener.PacketListenerProvider;
import eu.miopowered.mionge.server.networking.logic.HandshakeListener;
import eu.miopowered.mionge.server.networking.logic.LoginListener;
import eu.miopowered.mionge.server.networking.logic.StatusListener;
import eu.miopowered.mionge.server.networking.netty.NettyServer;
import eu.miopowered.mionge.server.networking.netty.connection.ConnectionHolder;
import eu.miopowered.mionge.server.networking.netty.encryption.EncryptionManager;
import eu.miopowered.mionge.server.networking.packet.PacketRegistry;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Accessors(fluent = true)
public class NetworkHandler {

    private EncryptionManager encryptionManager;
    private PacketRegistry registry;
    private NettyServer server;
    private PacketListenerProvider packetListenerProvider;

    private List<ConnectionHolder> connectionHolders;

    public NetworkHandler() {
        this.encryptionManager = new EncryptionManager();
        this.registry = new PacketRegistry();
        this.server = new NettyServer();
        this.packetListenerProvider = new PacketListenerProvider();

        this.connectionHolders = new ArrayList<>();

        this.packetListenerProvider.register(new HandshakeListener());
        this.packetListenerProvider.register(new StatusListener());
        this.packetListenerProvider.register(new LoginListener());
    }

    public void startServer(String address, int port) {
        this.server.start(address, port);
    }

    public void stop() {
        this.server.stop();
    }

    public void register(ConnectionHolder connectionHolder) {
        this.connectionHolders.add(connectionHolder);
    }

    public Optional<ConnectionHolder> holderByChannel(Channel channel) {
        return this.connectionHolders.stream().filter(connectionHolder -> connectionHolder.channel().equals(channel)).findFirst();
    }

    public void unregister(Channel channel) {
        this.holderByChannel(channel).ifPresent(connectionHolder -> this.connectionHolders.remove(connectionHolder));
    }
}
