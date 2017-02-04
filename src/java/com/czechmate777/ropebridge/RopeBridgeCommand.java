package com.czechmate777.ropebridge;

import java.util.ArrayList;
import java.util.List;

import com.czechmate777.ropebridge.items.Builder;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

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

	@Override
	public String getCommandName() {
		return "ropebridge";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return usage;
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] arguments) {
		if (arguments.length < 2) {
			tell(sender, "Usage: "+usage);
		} else if (commandsString.contains(arguments[0])) {
			// Set value
			setVariable(arguments[0], arguments[1]);
			
			// Tell player that it's temp
			tell(sender, "Value set temporarily. Please use config file for permanent changes.");
		} else if ( arguments.length >= 6) {
			// Convert each one to number
			try {
				
				int type = arguments.length == 6 ? 0 : Integer.parseInt(arguments[6]);
				BlockPos pos1 = new BlockPos(Double.parseDouble(arguments[0]),Double.parseDouble(arguments[1]),Double.parseDouble(arguments[2]));
				BlockPos pos2 = new BlockPos(Double.parseDouble(arguments[3]),Double.parseDouble(arguments[4]),Double.parseDouble(arguments[5]));

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
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if(sender instanceof EntityPlayer) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] typed, BlockPos pos) {
		return typed.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(typed, commandsString.split(", ")) : null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}
	
	public void tell(ICommandSender sender, String message) {
		sender.addChatMessage(new ChatComponentText("[Rope Bridge]: "+message).setChatStyle( new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)));
	}

}
