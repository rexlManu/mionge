package eu.miopowered.mionge.server.networking.netty.transfer.decoder;

import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class NettyPacketLengthDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        int packetLength = ByteBufMessage.readVarInt(in);
        if (packetLength > in.readableBytes()) {
            in.resetReaderIndex();
        } else {
            out.add(in.readBytes(packetLength));
        }
    }
}
