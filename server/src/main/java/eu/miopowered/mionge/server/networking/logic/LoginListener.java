package eu.miopowered.mionge.server.networking.logic;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.listener.PacketListener;
import eu.miopowered.mionge.server.networking.listener.ReceivedPacket;
import eu.miopowered.mionge.server.networking.packet.PacketState;
import eu.miopowered.mionge.server.networking.packet.in.login.EncryptionResponsePacket;
import eu.miopowered.mionge.server.networking.packet.in.login.LoginStartPacket;
import eu.miopowered.mionge.server.networking.packet.out.login.DisconnectLoginPacket;
import eu.miopowered.mionge.server.networking.packet.out.login.EncryptionRequestPacket;
import eu.miopowered.mionge.server.networking.packet.out.login.LoginSuccessPacket;

import java.util.UUID;

public class LoginListener {

    @PacketListener(packet = LoginStartPacket.class)
    public void handleLoginStart(ReceivedPacket<LoginStartPacket> receivedPacket) {
        /*receivedPacket.holder().sendPacket(new EncryptionRequestPacket(
                MiongeServer.SERVER_ID,
                MiongeServer.server().networkHandler().encryptionManager().publicKey().getEncoded(),
                receivedPacket.holder().verifyToken()
        ));*/

        // receivedPacket.holder().state(PacketState.PLAY);

        receivedPacket.holder().sendPacket(new LoginSuccessPacket(UUID.fromString("43f79582-18a2-4710-883f-f3ac68b5ac72"), "Mio"));
    }

    @PacketListener(packet = EncryptionResponsePacket.class)
    public void onResponse(ReceivedPacket<EncryptionResponsePacket> receivedPacket) {
        EncryptionResponsePacket packet = receivedPacket.packet();
        if (!MiongeServer.server().networkHandler().encryptionManager().verifyToken(receivedPacket.holder().verifyToken(), packet.verifyToken())) {
            receivedPacket.holder().sendPacket(new DisconnectLoginPacket("Couldn't not verify login."));
            return;
        }
        receivedPacket.holder().enableEncryption(MiongeServer.server().networkHandler().encryptionManager().decrypt(packet.sharedSecret()));
        receivedPacket.holder().state(PacketState.PLAY);

        receivedPacket.holder().sendPacket(new LoginSuccessPacket(UUID.randomUUID(), "Mio"));
    }
}
