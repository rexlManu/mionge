package eu.miopowered.mionge.server.networking.netty.handler;

import eu.miopowered.mionge.server.networking.netty.transfer.decoder.NettyPacketDecoder;
import eu.miopowered.mionge.server.networking.netty.transfer.decoder.NettyPacketLengthDecoder;
import eu.miopowered.mionge.server.networking.netty.transfer.encoder.NettyPacketEncoder;
import eu.miopowered.mionge.server.networking.netty.transfer.encoder.NettyPacketLengthEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("lengthDecoder", new NettyPacketLengthDecoder());
        pipeline.addLast("decoder", new NettyPacketDecoder());

        pipeline.addLast("lengthEncoder", new NettyPacketLengthEncoder());
        pipeline.addLast("encoder", new NettyPacketEncoder());

        pipeline.addLast("handler", new NettyInboundHandler());
    }

}
