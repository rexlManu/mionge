package eu.miopowered.mionge.server.networking.listener;

import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PacketListener {

    public Class<? extends IncomingPacket> packet();

}
