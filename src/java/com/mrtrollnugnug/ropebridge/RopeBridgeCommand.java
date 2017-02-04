package com.mrtrollnugnug.ropebridge;

import java.util.ArrayList;
import java.util.List;

import com.mrtrollnugnug.ropebridge.items.Builder;

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
	
	private List aliases;
	private String usage = "ropebridge <variable> <value> OR ropebridge <x1> <y1> <z1> <x2> <y2> <z2> <materialId>";
	private String commandsString = 
			"breakThroughBlocks, bridgeDroopFactor, "+
	"bridgeYOffset, customAchievements, "+
	"ignoreSlopeWarnings, maxBridgeDistance, zoomOnAim";
	
	public RopeBridgeCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("ropebridge");
		this.aliases.add("rb");
	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	private void setVariable(String variable, String value) {
		try {
			if (Main.instance.getClass().getField(variable).getType().getName().equals("boolean")) {
				boolean boolVal = value.equals("true") ? true : false;
				Main.instance.getClass().getField(variable).set(Main.instance, boolVal);
			}
			else if (variable.equals("bridgeYOffset")) {
				float floatVal = Float.parseFloat(value);
				Main.instance.getClass().getField(variable).set(Main.instance, floatVal);
			}
			else {
				int intVal = Integer.parseInt(value);
				Main.instance.getClass().getField(variable).set(Main.instance, intVal);
			}
		} catch (NumberFormatException exception) {
			exception.printStackTrace();
		} catch (NoSuchFieldException exception) {
			exception.printStackTrace();
		} catch (SecurityException exception) {
			exception.printStackTrace();
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}
	
	public void tell(ICommandSender sender, String message) {
		sender.sendMessage(new TextComponentString("[Rope Bridge]: "+message).setStyle( new Style().setColor(TextFormatting.DARK_AQUA)));
	}

	@Override
	public String getName() {
		return "ropebridge";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return usage;
	}

	@Override
	public List<String> getAliases() {
		return this.aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 2) {
			tell(sender, "Usage: "+usage);
		} else if (commandsString.contains(args[0])) {
			// Set value
			setVariable(args[0], args[1]);
			
			// Tell player that it's temp
			tell(sender, "Value set temporarily. Please use config file for permanent changes.");
		} else if ( args.length >= 6) {
			// Convert each one to number
			try {
				
				int type = args.length == 6 ? 0 : Integer.parseInt(args[6]);
				BlockPos pos1 = new BlockPos(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2]));
				BlockPos pos2 = new BlockPos(Double.parseDouble(args[3]),Double.parseDouble(args[4]),Double.parseDouble(args[5]));

				Builder.newBridge((EntityPlayer) sender, 30, null, type, pos1, pos2);
			}
			catch (NumberFormatException e) {
				tell(sender, "That doesn't look right. Make sure you use the format <x1> <y1> <z1> <x2> <y2> <z2> <woodType>. If no wood type is given '0' or oak is used.");
			}
		}
		else {
			tell(sender, "Not a valid argument. You can enter coordinates or try one of these: "+commandsString);
		}
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if(sender instanceof EntityPlayer) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, commandsString.split(", ")) : null;
	}

}
