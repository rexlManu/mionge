package eu.miopowered.mionge.server.networking.netty.protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ByteBufMessage {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static int readVarInt(ByteBuf buf) {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = buf.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static void writeVarInt(int value, ByteBuf buf) {
        do {
            byte temp = (byte) (value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            buf.writeByte(temp);
        } while (value != 0);
    }

    public static String readString(ByteBuf buf) {
        int length = readVarInt(buf);

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static long readVarLong(ByteBuf buf) {
        int numRead = 0;
        long result = 0;
        byte read;
        do {
            read = buf.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 10) {
                throw new RuntimeException("VarLong is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static void writeVarLong(long value, ByteBuf buf) {
        do {
            byte temp = (byte) (value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            buf.writeByte(temp);
        } while (value != 0);
    }

    public static short readShort(ByteBuf buf) {
        return buf.readShort();
    }

    public static void writeString(String string, ByteBuf buf) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        writeVarInt(bytes.length, buf);
        buf.writeBytes(bytes);
    }

    public static void writeShort(short val, ByteBuf buf) {
        buf.writeShort(val);
    }

    public static <T> T readJSON(ByteBuf buf, Class<T> clazz) {
        String string = readString(buf);

        try {
            return gson.fromJson(string, clazz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static void writeJSON(Object object, ByteBuf buf) {
        writeString(gson.toJson(object), buf);
    }

    public static void writeByteArray(byte[] bytes, ByteBuf byteBuf) {
        writeVarInt(bytes.length, byteBuf);
        byteBuf.writeBytes(bytes);
    }

    public static byte[] readByteArray(ByteBuf byteBuf) {
        int length = readVarInt(byteBuf);
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        return bytes;
    }

    public static void writeUuid(UUID uuid, ByteBuf byteBuf) {
        writeVarLong(uuid.getMostSignificantBits(), byteBuf);
        writeVarLong(uuid.getLeastSignificantBits(), byteBuf);
    }

    public static UUID readUuid(ByteBuf byteBuf) {
        return new UUID(readVarLong(byteBuf), readVarLong(byteBuf));
    }


}
