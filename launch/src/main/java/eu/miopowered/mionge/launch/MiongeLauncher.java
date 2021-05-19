package eu.miopowered.mionge.launch;

import eu.miopowered.mionge.api.Server;
import eu.miopowered.mionge.server.MiongeServer;

/**
 * The entrypoint for the software
 */
public class MiongeLauncher {

    public static void main(String[] args) {
        // DependencyLoader.loadDependencies();
        Server server = new MiongeServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        server.startup();
    }

}
