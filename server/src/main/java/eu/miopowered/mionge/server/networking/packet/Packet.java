package eu.miopowered.mionge.server.networking.packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class Packet {

    private int id;
    private PacketState state;
    private PacketDirection direction;

}
