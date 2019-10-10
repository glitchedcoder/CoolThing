package com.glitchedcode.ct.command;

import com.glitchedcode.ct.entity.EntityType;
import com.glitchedcode.ct.entity.Location;
import org.fusesource.jansi.Ansi;

import javax.annotation.Nonnull;

/**
 * A really niche {@link Command} with not a whole lot of
 * use other than some basic stuff like spawning static entities.
 * <br />
 * Even then, the entities being spawned are uneditable beyond their default vals.
 */
public class Spawn implements Command {

    @Nonnull
    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getDescription() {
        return "Spawns an entity with the given entity id at the given location.";
    }

    @Override
    public String getUsage() {
        return "spawn [list|id] <x> <y>";
    }

    @Override
    public String[] getAliases() {
        return new String[] { "s" };
    }

    @Override
    public boolean execute(String name, String[] args) {
        if (args.length > 0) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    StringBuilder sb = new StringBuilder("Spawnable entities: ");
                    for (EntityType t : EntityType.values())
                        sb.append(t.getId()).append(", ");
                    getLogger().command(sb.toString());
                    return true;
                }
            } else if (args.length == 3) {
                EntityType t = EntityType.fromId(args[0]);
                if (t == null) {
                    getLogger().command(Ansi.Color.RED, "Couldn't find an entity with the id \"" + args[0] + "\".");
                    return true;
                }
                int x, y;
                try {
                    x = Integer.parseInt(args[1]);
                    y = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    getLogger().command(Ansi.Color.RED, "Given x/y was not a valid integer.");
                    return true;
                }
                if (t.spawn(new Location(x, y)) == null) {
                    getLogger().warn(Ansi.Color.RED, "Failed to spawn " + t.getName());
                }
                getLogger().command("Spawned a " + t.getName() + " at (" + x + ", " + y + ").");
                return true;
            }
        }
        return false;
    }
}