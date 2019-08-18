package com.glitchedcode.ct.command;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;

import javax.annotation.Nonnull;

public interface Command {

    @Nonnull
    String getName();

    default String getDescription() {
        return "";
    }

    default String getUsage() {
        return getName();
    }

    default String[] getAliases() {
        return new String[0];
    }

    default boolean hasAliases() {
        return getAliases().length != 0;
    }

    default boolean isAlias(String s) {
        for (String a : getAliases()) {
            if (a.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    default Logger getLogger() {
        return CoolThing.getLogger();
    }

    boolean execute(String name, String[] args);
}