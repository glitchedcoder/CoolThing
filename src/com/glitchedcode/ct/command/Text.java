package com.glitchedcode.ct.command;

import javax.annotation.Nonnull;

public class Text implements Command {

    @Nonnull
    @Override
    public String getName() {
        return "text";
    }

    @Override
    public String getDescription() {
        return "Create, move, or delete text.";
    }

    @Override
    public String getUsage() {
        return "text [create|list|move|delete] [text...|id] [location[int,int]]";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(String name, String[] args) {
        // TODO
        return false;
    }
}
