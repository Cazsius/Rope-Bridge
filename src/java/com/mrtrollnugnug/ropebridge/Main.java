package com.mrtrollnugnug.ropebridge;

import com.mrtrollnugnug.ropebridge.blocks.ModBlocks;
import com.mrtrollnugnug.ropebridge.items.ModItems;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {

	public static final String MODID = "ropebridge";
    public static final String MODNAME = "Rope Bridge Mod";
    public static final String VERSION = "1.3";
    public static SimpleNetworkWrapper snw;
    public static Configuration config;
    public static int maxBridgeDistance, bridgeDroopFactor;
	public static float bridgeYOffset;
	public static boolean zoomOnAim;
	public static boolean breakThroughBlocks;
	public static boolean ignoreSlopeWarnings;
    public static boolean customAchievements;
    public static Achievement craftAchievement, buildAchievement;

    @SidedProxy(clientSide="com.mrtrollnugnug.ropebridge.ClientProxy", serverSide="com.mrtrollnugnug.ropebridge.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance
    public static Main instance = new Main();

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	// read your config file, create Blocks, Items, register with GameRegistry
    	Main.proxy.preInit(e);
    	
    	// Read configuration file
    	config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();
		maxBridgeDistance = config.getInt("maxBridgeDistance", Configuration.CATEGORY_GENERAL, 400, 1, 1000, "Max length of bridges made be Grappling Gun.");
		bridgeDroopFactor = config.getInt("bridgeDroopFactor", Configuration.CATEGORY_GENERAL, 100, 0, 100, "Percent of slack the bridge will have, causing it to hang.");
		bridgeYOffset = config.getFloat("bridgeYOffset", Configuration.CATEGORY_GENERAL, -0.3F, -1.00F, 1.00F, "Generated bridges will be raised or lowered by this ammount in blocks.\nDefault is just below user's feet.");
		zoomOnAim = config.getBoolean("zoomOnAim", Configuration.CATEGORY_GENERAL, true, "Turn this off if issues come up with Optifine.");
		breakThroughBlocks = config.getBoolean("breakThroughBlocks", Configuration.CATEGORY_GENERAL, false, "If enabled, all blocks that dare stand in a bridge's way will be broken.\nVery useful in creative mode.");
		ignoreSlopeWarnings = config.getBoolean("ignoreSlopeWarnings", Configuration.CATEGORY_GENERAL, false, "Set true to ignore all slope warnings and allow building of very steep bridges.");
		customAchievements = config.getBoolean("customAchievements", Configuration.CATEGORY_GENERAL, true, "Custom crafting and building achievements.");
		config.save();
    	
		// Register Achievements (if enabled in config)
		if (customAchievements) {
	    	craftAchievement = new Achievement("achievement.grapplingGun", "grapplingGun", 8, 2, ModItems.bridgeBuilder, AchievementList.BUILD_BETTER_PICKAXE);
	    	craftAchievement.registerStat();
	    	buildAchievement = new Achievement("achievement.buildBridge", "buildBridge", 10, 2, ModBlocks.bridgeBlock2, craftAchievement);
	    	buildAchievement.registerStat();
		}

    	// Register Simple Channel
    	snw = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    	snw.registerMessage(BridgeMessageHandler.class, BridgeMessage.class, 0, Side.SERVER);
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent e) {
    	e.registerServerCommand(new RopeBridgeCommand());
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
    	// build up data structures, add Crafting Recipes and register new handler
    	Main.proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	// communicate with other mods, adjust based on this
    	Main.proxy.postInit(e);
    }
}