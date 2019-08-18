package com.glitchedcode.ct.command;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.concurrent.ConcurrentSet;
import com.glitchedcode.ct.immutable.ImmutableSet;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import javax.annotation.Nullable;
import java.util.Set;

public final class CommandManager {

    private static final Logger logger = CoolThing.getLogger();
    private static final Set<Command> commands = new ConcurrentSet<>();

    private CommandManager() {}

    public static void register(Command command) {
        if (!isCommand(command.getName())) {
            for (String s : command.getAliases()) {
                if (isCommand(s))
                    throw new IllegalStateException("Given command has already registered alias: " + s);
            }
            commands.add(command);
            logger.debug("Registered new command: " + command.getName());
        } else
            throw new IllegalStateException("Given command name " + command.getName() + " is already registered.");
    }

    public static void unregister(Command command) {
        if (isCommand(command.getName())) {
            commands.remove(command);
            logger.debug("Unregistered command \"" + command.getName() + "\".");
        }
    }

    public static boolean isCommand(String s) {
        for (Command c : commands) {
            if (c.getName().equalsIgnoreCase(s))
                return true;
            if (c.isAlias(s))
                return true;
        }
        return false;
    }

    public static boolean isAlias(String s) {
        for (Command c : commands) {
            if (c.isAlias(s))
                return true;
        }
        return false;
    }

    public static boolean execute(String s, String[] args) {
        if (isCommand(s)) {
            Command command = getCommand(s);
            if (command != null)
                return command.execute(s, args);
            else
                logger.error(Ansi.Color.RED, "#isCommand(String) returned true while #getCommand(String) returned null.");
        }
        return false;
    }

    @Nullable
    public static Command getCommand(String s) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(s))
                return command;
            if (command.isAlias(s))
                return command;
        }
        return null;
    }

    public static Set<Command> getCommands() {
        return new ImmutableSet<>(commands);
    }
}