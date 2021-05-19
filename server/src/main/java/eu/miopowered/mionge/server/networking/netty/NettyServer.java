package eu.miopowered.mionge.server.networking.netty;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.netty.handler.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class NettyServer {

    private EventLoopGroup bossGroup, workerGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture channel;

    public NettyServer() {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.bootstrap = new ServerBootstrap()
                .group(this.bossGroup, this.workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void start(String address, int port) {
        this.channel = this.bootstrap.bind(address, port).addListener(future -> {
            if (future.isSuccess()) {
                MiongeServer.server().logger().info(String.format("Netty is bind to %s:%s", address, port));
            } else {
                MiongeServer.server().logger().error(String.format("Failed to bind %s:%s", address, port), future.cause());
            }
        });
    }

    public void stop() {
        try {
            this.channel.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            MiongeServer.server().logger().error("Error occurred while stopping the netty server.", e);
        } finally {
            this.bossGroup.shutdownGracefully();
            this.workerGroup.shutdownGracefully();
        }
    }
}
