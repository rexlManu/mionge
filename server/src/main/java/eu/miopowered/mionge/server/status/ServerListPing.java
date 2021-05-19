package eu.miopowered.mionge.server.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class ServerListPing {

    private Version version;
    private Players players;
    private Description description;
    private String favicon;

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    public static class Version {
        private String name;
        private int protocol;
    }

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    public static class Players {
        private int max, online;
        private Player[] sample;

        @Data
        @Accessors(fluent = true)
        @AllArgsConstructor
        public static class Player {
            private String name;
            private UUID id;
        }
    }

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    public static class Description {
        private String text;
    }

}
