package eu.miopowered.mionge.server.networking.netty.handler;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.netty.connection.ConnectionHolder;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyInboundHandler extends SimpleChannelInboundHandler<IncomingPacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        MiongeServer.server().networkHandler().register(new ConnectionHolder(ctx.channel()));
        MiongeServer.server().logger().info("Remote connection from " + ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        MiongeServer.server().networkHandler().unregister(ctx.channel());
        MiongeServer.server().logger().info("Connection closed from " + ctx.channel().remoteAddress().toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IncomingPacket msg) throws Exception {
        MiongeServer.server().logger().info(String.format("Packet received: name: %s, id: %s", msg.getClass().getSimpleName(), msg.id()));

        MiongeServer.server().networkHandler().holderByChannel(ctx.channel()).ifPresent(connectionHolder -> {
            MiongeServer.server().networkHandler().packetListenerProvider().listen(connectionHolder, msg);
        });

    }
}
