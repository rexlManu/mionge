package eu.miopowered.mionge.server;

import eu.miopowered.mionge.api.Server;
import eu.miopowered.mionge.server.console.ConsoleProvider;
import eu.miopowered.mionge.server.networking.NetworkHandler;
import eu.miopowered.mionge.server.status.ServerListPing;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Getter
@Accessors(fluent = true)
public class MiongeServer implements Server {

    public static final String SERVER_ID = "";

    /**
     * Singleton instance for handling everything
     */
    @Getter
    private static MiongeServer server;

    private Logger logger = LogManager.getLogger("mionge");
    private ConsoleProvider consoleProvider;

    private ServerListPing serverListPing;
    private NetworkHandler networkHandler;

    public MiongeServer() {
        if (Objects.nonNull(MiongeServer.server))
            throw new IllegalStateException("Server is already initialized");

        MiongeServer.server = this;
    }

    @Override
    public void startup() {
        this.logger.info("Starting the server...");

        this.consoleProvider = new ConsoleProvider();
        this.serverListPing = new ServerListPing(
                new ServerListPing.Version("1.16.5", 754),
                new ServerListPing.Players(20, 0, new ServerListPing.Players.Player[]{}),
                new ServerListPing.Description("A mionge server"),
                ""
        );
        this.networkHandler = new NetworkHandler();

        this.networkHandler.startServer("localhost", 25565);
    }

    @Override
    public void shutdown() {
        this.logger.info("Stopping the server...");

        this.networkHandler.stop();
    }
}
