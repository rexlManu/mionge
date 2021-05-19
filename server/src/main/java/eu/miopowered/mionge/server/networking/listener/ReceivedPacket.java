package eu.miopowered.mionge.server.networking.listener;

import eu.miopowered.mionge.server.networking.netty.connection.ConnectionHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class ReceivedPacket<P> {

    private P packet;
    private ConnectionHolder holder;

}
