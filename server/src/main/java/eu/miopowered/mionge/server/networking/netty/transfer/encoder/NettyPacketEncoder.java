package eu.miopowered.mionge.server.networking.netty.transfer.encoder;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.out.OutgoingPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyPacketEncoder extends MessageToByteEncoder<OutgoingPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, OutgoingPacket msg, ByteBuf out) throws Exception {
        MiongeServer.server().networkHandler().holderByChannel(ctx.channel()).ifPresent(connectionHolder -> {
            if (connectionHolder.encrypted()) {
                ByteBufMessage.writeVarInt(msg.id(), out);
            } else {
                ByteBufMessage.writeVarInt(msg.id(), out);
            }
            MiongeServer.server().logger().info(String.format("Packet sent: name: %s, id: %s", msg.getClass().getSimpleName(), msg.id()), msg);
            msg.write(out);
        });

    }
}
