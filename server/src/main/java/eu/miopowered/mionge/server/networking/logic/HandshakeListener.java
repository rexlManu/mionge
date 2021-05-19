package eu.miopowered.mionge.server.networking.logic;

import eu.miopowered.mionge.server.networking.listener.PacketListener;
import eu.miopowered.mionge.server.networking.listener.ReceivedPacket;
import eu.miopowered.mionge.server.networking.packet.in.handshake.HandshakePacket;

public class HandshakeListener {

    @PacketListener(packet = HandshakePacket.class)
    public void handleHandshake(ReceivedPacket<HandshakePacket> receivedPacket) {
        HandshakePacket packet = receivedPacket.packet();
        receivedPacket.holder().state(packet.nextState());
    }

}
