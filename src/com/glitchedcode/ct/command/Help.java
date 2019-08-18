package com.glitchedcode.ct.command;

import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import javax.annotation.Nonnull;

public class Help implements Command {

    @Nonnull
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Lists commands and their descriptions.";
    }

    @Override
    public String getUsage() {
        return "help [command name]";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "?" };
    }

    @Override
    public boolean execute(String name, String[] args) {
        Logger logger = getLogger();
        if (args.length == 0) {
            StringBuilder b = new StringBuilder("A list of registered commands:\n");
            for (Command c : CommandManager.getCommands()) {
                b.append("\t");
                b.append(c.getName());
                b.append(" - ");
                b.append(c.getDescription());
                b.append("\n");
            }
            logger.command(b.toString());
            return true;
        } else if (args.length == 1) {
            if (CommandManager.isCommand(args[0])) {
                Command command = CommandManager.getCommand(args[0]);
                if (command != null) {
                    StringBuilder b = new StringBuilder("Details of command \"" + command.getName() + "\":\n");
                    b.append("\tName: ").append(command.getName()).append("\n");
                    b.append("\tDescription: ").append(command.getDescription()).append("\n");
                    b.append("\tAliases (").append(command.getAliases().length).append("): ");
                    if (command.getAliases().length > 0) {
                        for (int i = 0; i < command.getAliases().length; i++) {
                            b.append(command.getAliases()[i]);
                            if (i < command.getAliases().length - 1)
                                b.append(", ");
                        }
                        b.append("\n");
                    } else
                        b.append("(none)").append("\n");
                    b.append("\tUsage: ").append(command.getUsage());
                    logger.command(b.toString());
                    return true;
                } else
                    logger.error(Ansi.Color.RED, "CommandManager#isCommand(String) returned true while #getCommand(String) returned null.");
            } else
                logger.command(Ansi.Color.YELLOW, "A command with the name/alias \"" + args[0] + "\" doesn't exist.");
        }
        return false;
    }
}