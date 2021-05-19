package eu.miopowered.mionge.server.networking.listener;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.netty.connection.ConnectionHolder;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketListenerProvider {

    private List<Object> registeredListeners;

    public PacketListenerProvider() {
        this.registeredListeners = new ArrayList<>();
    }

    public void register(Object listener) {
        this.registeredListeners.add(listener);
    }

    public void listen(ConnectionHolder connectionHolder, IncomingPacket packet) {
        this.registeredListeners.forEach(listenerClass -> {
            Arrays.stream(listenerClass.getClass().getMethods()).filter(method -> method.isAnnotationPresent(PacketListener.class) && method.getAnnotation(PacketListener.class).packet().equals(packet.getClass())).forEach(method -> {
                try {
                    method.invoke(listenerClass, new ReceivedPacket(packet, connectionHolder));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    MiongeServer.server().logger().error("Couldn't invoke listener", e);
                }
            });
        });
    }
}
