package com.glitchedcode.ct.thread;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.command.Command;
import com.glitchedcode.ct.command.CommandManager;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class InputListener extends Thread {

    private final LineReader reader;
    private final AtomicBoolean running;
    private static final Logger logger = CoolThing.getLogger();

    public InputListener() {
        super("inputListener");
        running = new AtomicBoolean(false);
        setDefaultUncaughtExceptionHandler(CoolThing.getExceptionHandler());
        LineReader a = null;
        try {
            a = LineReaderBuilder.builder()
                    .appName("Game")
                    .terminal(TerminalBuilder.builder()
                            .jansi(true)
                            .dumb(true)
                            .build()
                    ).build();
        } catch (IOException e) {
            CoolThing.getLogger().handleError(this, e);
        }
        this.reader = a;
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            String line = reader.readLine();
            if (line.isEmpty())
                continue;
            String[] s = line.split(" ");
            String a = s[0];
            if (CommandManager.isCommand(a)) {
                Command command = CommandManager.getCommand(a);
                if (command == null) {
                    logger.error(Ansi.Color.RED, "#isCommand(String) returned true while #getCommand(String) returned null.");
                    continue;
                }
                if (s.length > 1) {
                    List<String> l = new ArrayList<>();
                    Collections.addAll(l, s);
                    l.remove(0);
                    String[] b = l.toArray(new String[0]);
                    if (!CommandManager.execute(a, b))
                        logger.command(Ansi.Color.RED, "Usage: " + command.getUsage());
                } else {
                    if (!CommandManager.execute(a, new String[0]))
                        logger.command(Ansi.Color.RED, "Usage: " + command.getUsage());
                }
            } else
                logger.command(Ansi.Color.RED, "Unknown command \"" + a + "\".");
        }
    }

    public boolean isRunning() {
        return running.get();
    }

    public void close() {
        System.out.print("\b\b");
        running.set(false);
        try {
            reader.getTerminal().flush();
            reader.getTerminal().close();
        } catch (IOException e) {
            CoolThing.getLogger().handleError(this, e);
        }
    }
}