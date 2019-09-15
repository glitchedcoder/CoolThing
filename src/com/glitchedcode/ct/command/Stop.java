package com.glitchedcode.ct.command;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import javax.annotation.Nonnull;

public class Stop implements Command {

    @Nonnull
    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getUsage() {
        return "stop [exit code]";
    }

    @Override
    public String getDescription() {
        return "Closes the app and saves all data.";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "exit" };
    }

    @Override
    public boolean execute(String name, String[] args) {
        Logger logger = getLogger();
        if (args.length == 0)
            CoolThing.stop(0);
        else if (args.length == 1) {
            String s = args[0];
            try {
                int i = Integer.parseInt(s);
                CoolThing.stop(i);
            } catch (NumberFormatException e) {
                logger.command(Ansi.Color.YELLOW, "The given String \"" + s + "\" is not an integer.");
                return true;
            }
        }
        return false;
    }
}
