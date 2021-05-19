package eu.miopowered.mionge.server.console;

import eu.miopowered.mionge.server.MiongeServer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class ConsoleProvider {

    private Terminal terminal;
    private LineReader lineReader;

    public ConsoleProvider() {
        try {
            this.terminal = TerminalBuilder
                    .builder()
                    .system(true)
                    .signalHandler(Terminal.SignalHandler.SIG_IGN)
                    .name("Mionge")
                    .build();
        } catch (IOException e) {
            MiongeServer.server().logger().error("Couldn't found a terminal for console reader.", e);
        }

        this.lineReader = LineReaderBuilder
                .builder()
                .terminal(this.terminal)
                .appName("Mionge")
                .build();
    }
}
