package eu.miopowered.mionge.server.networking.netty.transfer.decoder;

import eu.miopowered.mionge.server.MiongeServer;
import eu.miopowered.mionge.server.networking.netty.protocol.ByteBufMessage;
import eu.miopowered.mionge.server.networking.packet.in.IncomingPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class NettyPacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int packetId = ByteBufMessage.readVarInt(in);
        MiongeServer
                .server()
                .networkHandler()
                .holderByChannel(ctx.channel())
                .ifPresentOrElse(connectionHolder -> connectionHolder
                        .findPacketById(packetId)
                        .ifPresentOrElse(
                                registeredPacket -> {
                                    IncomingPacket packet = registeredPacket.createPacket(in);
                                    if (packet == null) return;
                                    out.add(packet);
                                },
                                () -> MiongeServer.server().logger().error(String.format("Unknown packet received: id: %s", packetId))), () -> ctx.channel().close()
                );


        // MiongeServer.server().packetRegistry().registeredPackets().findById(packetId);
        /*if (packetId == 0) {
            int protocolVersion = ByteBufMessage.readVarInt(in);
            String serverAddress = ByteBufMessage.readString(in);
            short serverPort = ByteBufMessage.readShort(in);
            int state = ByteBufMessage.readVarInt(in);

            MiongeServer.server().logger().info(String.format("id: %s, protocolVersion: %s, serverAddress: %s, serverPort: %s, state: %s",
                    packetId,
                    protocolVersion,
                    serverAddress,
                    serverPort,
                    state
            ));

            ByteBuf buffer = ctx.alloc().buffer();
            ByteBufMessage.writeVarInt(0x00, buffer);
            ByteBufMessage.writeString("{\"version\":{\"name\":\"1.8.7\",\"protocol\":47},\"players\":{\"max\":100,\"online\":5,\"sample\":[{\"name\":\"thinkofdeath\",\"id\":\"4566e69f-c907-48ee-8d71-d7ba5aa00d20\"}]},\"description\":{\"text\":\"Hello world\"},\"favicon\":\"data:image/png;base64,<data>\"}", buffer);
            // ByteBufMessage.writeVarInt(1, buffer);

            ctx.channel().writeAndFlush(buffer);
        } else {

            MiongeServer.server().logger().info(String.format("id: %s", packetId));
        }*/
    }
}
