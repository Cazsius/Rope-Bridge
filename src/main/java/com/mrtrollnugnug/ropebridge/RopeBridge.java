package com.mrtrollnugnug.ropebridge;

import com.mrtrollnugnug.ropebridge.common.CommonProxy;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import com.mrtrollnugnug.ropebridge.network.BridgeMessage;
import com.mrtrollnugnug.ropebridge.network.BridgeMessageHandler;
import com.mrtrollnugnug.ropebridge.network.BuildMessage;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER)
public class RopeBridge {

    public static SimpleNetworkWrapper snw;

    public static Configuration config;

    public static int maxBridgeDistance, bridgeDroopFactor;

    public static float bridgeYOffset;

    public static boolean zoomOnAim;

    public static boolean breakThroughBlocks;

    public static boolean ignoreSlopeWarnings;

    public static boolean customAchievements;

    public static Achievement craftAchievement, buildAchievement;

    @Mod.Instance(Constants.MOD_ID)
    public static RopeBridge instance;

    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit (FMLPreInitializationEvent e) {

        ContentHandler.initBlocks();
        ContentHandler.initItems();
        ContentHandler.initRecipes();
        proxy.preInit(e);

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

        if (customAchievements) {
            craftAchievement = new Achievement("achievement.grapplingGun", "grapplingGun", 8, 2, ContentHandler.itemBridgeBuilder, AchievementList.BUILD_BETTER_PICKAXE);
            craftAchievement.registerStat();
            buildAchievement = new Achievement("achievement.buildBridge", "buildBridge", 10, 2, ContentHandler.blockBridgeSlab2, craftAchievement);
            buildAchievement.registerStat();
        }

        snw = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD_ID);
        snw.registerMessage(BridgeMessageHandler.class, BridgeMessage.class, this.discriminator++, Side.SERVER);
        snw.registerMessage(BuildMessage.BuildMessageHandler.class, BuildMessage.class, this.discriminator++, Side.SERVER);
    }

    private int discriminator = 0;

    @EventHandler
    public void serverLoad (FMLServerStartingEvent e) {

        e.registerServerCommand(new RopeBridgeCommand());
    }

}