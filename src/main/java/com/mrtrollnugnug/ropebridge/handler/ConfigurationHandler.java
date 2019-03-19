package com.mrtrollnugnug.ropebridge.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public final class ConfigurationHandler {
    private static Configuration config = null;
    private static int maxBridgeDistance;
    private static int bridgeDroopFactor;
    private static float bridgeYOffset;
    private static boolean breakThroughBlocks;
    private static boolean ignoreSlopeWarnings;
    private static int slabsPerBlock;
    private static int stringPerBlock;
    private static int woodPerBlock;
    private static int ropePerBlock;
    private static int bridgeDamage;
    private static int ladderDamage;

    /**
     * Initializes the configuration file.
     *
     * @param file
     *            The file to read/write config stuff to.
     */
    public static void initConfig(File file) {
        setConfig(new Configuration(file));
        syncConfig();
    }

    /**
     * Syncs all configuration properties.
     */
    public static void syncConfig() {
        setMaxBridgeDistance(getConfig().getInt("maxBridgeDistance", Configuration.CATEGORY_GENERAL, 400, 1, 1000, "Max length of bridges made be Grappling Gun."));
        setBridgeDroopFactor(getConfig().getInt("bridgeDroopFactor", Configuration.CATEGORY_GENERAL, 100, 0, 100, "Percent of slack the bridge will have, causing it to hang."));
        setBridgeYOffset(getConfig().getFloat("bridgeYOffset", Configuration.CATEGORY_GENERAL, -0.3F, -1.00F, 1.00F, "Generated bridges will be raised or lowered by this ammount in blocks.\nDefault is just below user's feet."));
        setBreakThroughBlocks(getConfig().getBoolean("breakThroughBlocks", Configuration.CATEGORY_GENERAL, false, "If enabled, all blocks that dare stand in a bridge's way will be broken.\nVery useful in creative mode."));
        setIgnoreSlopeWarnings(getConfig().getBoolean("ignoreSlopeWarnings", Configuration.CATEGORY_GENERAL, false, "Set true to ignore all slope warnings and allow building of very steep bridges."));
        setSlabsPerBlock(getConfig().getInt("slabsPerBlock", Configuration.CATEGORY_GENERAL, 1, 0, 10, "Slabs consumed for each bridge block built."));
        setStringPerBlock(getConfig().getInt("stringPerBlock", Configuration.CATEGORY_GENERAL, 2, 0, 20, "String consumed for each bridge block built."));
        setWoodPerBlock(getConfig().getInt("woodPerBlock", Configuration.CATEGORY_GENERAL, 1, 0, 10, "Wood consumed for each ladder block built."));
        setRopePerBlock(getConfig().getInt("ropePerBlock", Configuration.CATEGORY_GENERAL, 2, 0, 20, "Rope consumed for each ladder block built."));
        setDamagePerLadder(getConfig().getInt("damageForLadder", Configuration.CATEGORY_GENERAL, 1, 0, 64, "How much the Ladder Gun is damaged after creating each ladder."));
        setDamagePerBridge(getConfig().getInt("damageForBridge", Configuration.CATEGORY_GENERAL, 1, 0, 64, "How much the Bridge Gun is damaged after creating each ladder."));

        if (getConfig().hasChanged()) {
            getConfig().save();
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void setConfig(Configuration config) {
        ConfigurationHandler.config = config;
    }

    public static boolean isIgnoreSlopeWarnings() {
        return ignoreSlopeWarnings;
    }

    public static void setIgnoreSlopeWarnings(boolean ignoreSlopeWarnings) {
        ConfigurationHandler.ignoreSlopeWarnings = ignoreSlopeWarnings;
    }

    public static int getBridgeDroopFactor() {
        return bridgeDroopFactor;
    }

    public static void setBridgeDroopFactor(int bridgeDroopFactor) {
        ConfigurationHandler.bridgeDroopFactor = bridgeDroopFactor;
    }

    public static float getBridgeYOffset() {
        return bridgeYOffset;
    }

    public static void setBridgeYOffset(float bridgeYOffset) {
        ConfigurationHandler.bridgeYOffset = bridgeYOffset;
    }

    public static boolean isBreakThroughBlocks() {
        return breakThroughBlocks;
    }

    public static void setBreakThroughBlocks(boolean breakThroughBlocks) {
        ConfigurationHandler.breakThroughBlocks = breakThroughBlocks;
    }

    public static int getMaxBridgeDistance() {
        return maxBridgeDistance;
    }

    public static void setMaxBridgeDistance(int maxBridgeDistance) {
        ConfigurationHandler.maxBridgeDistance = maxBridgeDistance;
    }

    public static void setRopePerBlock(int ropePerBlock) {
        ConfigurationHandler.ropePerBlock = ropePerBlock;
    }

    public static void setSlabsPerBlock(int slabsPerBlock) {
        ConfigurationHandler.slabsPerBlock = slabsPerBlock;
    }

    public static void setStringPerBlock(int stringPerBlock) {
        ConfigurationHandler.stringPerBlock = stringPerBlock;
    }

    public static void setWoodPerBlock(int woodPerBlock) {
        ConfigurationHandler.woodPerBlock = woodPerBlock;
    }

    public static void setDamagePerBridge (int bridgeDamage) {
        ConfigurationHandler.bridgeDamage = bridgeDamage;
    }

    public static void setDamagePerLadder (int ladderDamage) {
        ConfigurationHandler.ladderDamage = ladderDamage;
    }

    public static int getRopePerBlock() {
        return ropePerBlock;
    }

    public static int getSlabsPerBlock() {
        return slabsPerBlock;
    }

    public static int getStringPerBlock() {
        return stringPerBlock;
    }

    public static int getWoodPerBlock() {
        return woodPerBlock;
    }

    public static int getLadderDamage () {
        return ladderDamage;
    }

    public static int getBridgeDamage () {
        return bridgeDamage;
    }
}