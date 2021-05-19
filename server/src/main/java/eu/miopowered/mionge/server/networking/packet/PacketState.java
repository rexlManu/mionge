package eu.miopowered.mionge.server.networking.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public enum PacketState {

    HANDSHAKE(0), STATUS(1), LOGIN(2), PLAY(3);

    private int id;

    public static PacketState stateById(int id) {
        return Arrays.stream(PacketState.values()).filter(packetState -> packetState.id() == id).findFirst().orElse(null);
    }
}
