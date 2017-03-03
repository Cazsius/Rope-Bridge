package com.mrtrollnugnug.ropebridge.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ConfigurationHandler
{

    /**
     * An instance of the Configuration object being used.
     */
    public static Configuration config = null;

    public static int maxBridgeDistance;

    public static int bridgeDroopFactor;

    public static float bridgeYOffset;

    public static boolean zoomOnAim;

    public static boolean breakThroughBlocks;

    public static boolean ignoreSlopeWarnings;

    public static boolean customAchievements;

    public static float slabsPerBlock, stringPerBlock;

    /**
     * Initializes the configuration file.
     *
     * @param file
     *            The file to read/write config stuff to.
     */
    public static void initConfig(File file)
    {

        config = new Configuration(file);
        syncConfig();
    }

    /**
     * Syncs all configuration properties.
     */
    public static void syncConfig()
    {

        maxBridgeDistance = config.getInt("maxBridgeDistance", Configuration.CATEGORY_GENERAL, 400, 1, 1000, "Max length of bridges made be Grappling Gun.");
        bridgeDroopFactor = config.getInt("bridgeDroopFactor", Configuration.CATEGORY_GENERAL, 100, 0, 100, "Percent of slack the bridge will have, causing it to hang.");
        bridgeYOffset = config.getFloat("bridgeYOffset", Configuration.CATEGORY_GENERAL, -0.3F, -1.00F, 1.00F, "Generated bridges will be raised or lowered by this ammount in blocks.\nDefault is just below user's feet.");
        zoomOnAim = config.getBoolean("zoomOnAim", Configuration.CATEGORY_CLIENT, true && !FMLClientHandler.instance().hasOptifine(), "WARNING - might cause problems with optifine. Due to restrictions in Minecrafts code, you won't be able to change your fov once enabled either.");
        breakThroughBlocks = config.getBoolean("breakThroughBlocks", Configuration.CATEGORY_GENERAL, false, "If enabled, all blocks that dare stand in a bridge's way will be broken.\nVery useful in creative mode.");
        ignoreSlopeWarnings = config.getBoolean("ignoreSlopeWarnings", Configuration.CATEGORY_GENERAL, false, "Set true to ignore all slope warnings and allow building of very steep bridges.");
        customAchievements = config.getBoolean("customAchievements", Configuration.CATEGORY_GENERAL, true, "Custom crafting and building achievements.");
        slabsPerBlock = config.getInt("slabsPerBlock", Configuration.CATEGORY_GENERAL, 1, 0, 10, "Slabs consumed for every bridge block built.");
        stringPerBlock = config.getInt("stringPerBlock", Configuration.CATEGORY_GENERAL, 2, 0, 20, "String consumed for every bridge block built.");

        if (config.hasChanged())
            config.save();
    }
}