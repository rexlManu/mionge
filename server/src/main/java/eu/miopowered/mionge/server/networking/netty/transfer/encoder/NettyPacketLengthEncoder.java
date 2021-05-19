package eu.miopowered.mionge.server.networking.netty.transfer.encoder;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyPacketLengthEncoder extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        ByteBufMessage.writeVarInt(msg.readableBytes(), out);
        // MiongeServer.server().logger().info("Sent packet with length " + msg.readableBytes());
        out.writeBytes(msg);
    }
}
