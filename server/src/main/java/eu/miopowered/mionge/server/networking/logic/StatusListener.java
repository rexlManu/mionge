package eu.miopowered.mionge.server.networking.logic;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.listener.PacketListener;
import eu.miopowered.mionge.server.networking.listener.ReceivedPacket;
import eu.miopowered.mionge.server.networking.packet.in.status.PingPacket;
import eu.miopowered.mionge.server.networking.packet.in.status.StatusRequestPacket;
import eu.miopowered.mionge.server.networking.packet.out.status.PongPacket;
import eu.miopowered.mionge.server.networking.packet.out.status.StatusResponsePacket;

public class StatusListener {

    @PacketListener(packet = StatusRequestPacket.class)
    public void handleRequest(ReceivedPacket<StatusRequestPacket> receivedPacket) {
        receivedPacket.holder().sendPacket(new StatusResponsePacket(MiongeServer.server().serverListPing()));
    }

    @PacketListener(packet = PingPacket.class)
    public void handlePing(ReceivedPacket<PingPacket> receivedPacket) {
        receivedPacket.holder().sendPacket(new PongPacket(receivedPacket.packet().payload()));
    }
}
