package com.mrtrollnugnug.ropebridge;

import java.util.ArrayList;
import java.util.List;

import com.mrtrollnugnug.ropebridge.handler.BridgeBuildingHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class RopeBridgeCommand implements ICommand {

    private final List<String> aliases;

    private final String usage = "ropebridge <variable> <value> OR ropebridge <x1> <y1> <z1> <x2> <y2> <z2> <materialId>";

    private final String commandsString = "breakThroughBlocks, bridgeDroopFactor, " + "bridgeYOffset, customAchievements, " + "ignoreSlopeWarnings, maxBridgeDistance, zoomOnAim";

    public RopeBridgeCommand () {
        this.aliases = new ArrayList<>();
        this.aliases.add("ropebridge");
        this.aliases.add("rb");
    }

    @Override
    public int compareTo (ICommand o) {

        return 0;
    }

    private void setVariable (String variable, String value) {

        try {
            if (RopeBridge.instance.getClass().getField(variable).getType().getName().equals("boolean")) {
                final boolean boolVal = value.equals("true") ? true : false;
                RopeBridge.instance.getClass().getField(variable).set(RopeBridge.instance, boolVal);
            }
            else if (variable.equals("bridgeYOffset")) {
                final float floatVal = Float.parseFloat(value);
                RopeBridge.instance.getClass().getField(variable).set(RopeBridge.instance, floatVal);
            }
            else {
                final int intVal = Integer.parseInt(value);
                RopeBridge.instance.getClass().getField(variable).set(RopeBridge.instance, intVal);
            }
        }
        catch (final NumberFormatException exception) {
            exception.printStackTrace();
        }
        catch (final NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        catch (final SecurityException exception) {
            exception.printStackTrace();
        }
        catch (final IllegalArgumentException exception) {
            exception.printStackTrace();
        }
        catch (final IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean isUsernameIndex (String[] p_82358_1_, int p_82358_2_) {

        return false;
    }

    public void tell (ICommandSender sender, String message) {

        sender.sendMessage(new TextComponentString("[Rope Bridge]: " + message).setStyle(new Style().setColor(TextFormatting.DARK_AQUA)));
    }

    @Override
    public String getName () {

        return "ropebridge";
    }

    @Override
    public String getUsage (ICommandSender sender) {

        return this.usage;
    }

    @Override
    public List<String> getAliases () {

        return this.aliases;
    }

    @Override
    public void execute (MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length < 2) {
            this.tell(sender, "Usage: " + this.usage);
        }
        else if (this.commandsString.contains(args[0])) {
            // Set value
            this.setVariable(args[0], args[1]);

            // Tell player that it's temp
            this.tell(sender, "Value set temporarily. Please use config file for permanent changes.");
        }
        else if (args.length >= 6) {
            // Convert each one to number
            try {

                final int type = args.length == 6 ? 0 : Integer.parseInt(args[6]);
                final BlockPos pos1 = new BlockPos(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                final BlockPos pos2 = new BlockPos(Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));

                BridgeBuildingHandler.newBridge((EntityPlayer) sender, 30, null, type, pos1, pos2);
            }
            catch (final NumberFormatException e) {
                this.tell(sender, "That doesn't look right. Make sure you use the format <x1> <y1> <z1> <x2> <y2> <z2> <woodType>. If no wood type is given '0' or oak is used.");
            }
        }
        else {
            this.tell(sender, "Not a valid argument. You can enter coordinates or try one of these: " + this.commandsString);
        }

    }

    @Override
    public boolean checkPermission (MinecraftServer server, ICommandSender sender) {

        if (sender instanceof EntityPlayer)
            return true;
        else
            return false;
    }

    @Override
    public List<String> getTabCompletions (MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {

        return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, this.commandsString.split(", ")) : null;
    }

}
