package com.mrtrollnugnug.ropebridge.handler;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigurationHandler {
    private static ForgeConfigSpec.IntValue maxBridgeDistance;
    private static ForgeConfigSpec.IntValue bridgeDroopFactor;
    private static ForgeConfigSpec.DoubleValue bridgeYOffset;
    private static ForgeConfigSpec.BooleanValue breakThroughBlocks;
    private static ForgeConfigSpec.BooleanValue ignoreSlopeWarnings;
    private static ForgeConfigSpec.IntValue slabsPerBlock;
    private static ForgeConfigSpec.IntValue stringPerBlock;
    private static ForgeConfigSpec.IntValue woodPerBlock;
    private static ForgeConfigSpec.IntValue ropePerBlock;
    private static ForgeConfigSpec.IntValue bridgeDamage;
    private static ForgeConfigSpec.IntValue ladderDamage;

    public static final ConfigurationHandler SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<ConfigurationHandler, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigurationHandler::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public ConfigurationHandler(ForgeConfigSpec.Builder builder) {
        builder.push("general");
        maxBridgeDistance = builder.comment("Max length of bridges made be Grappling Gun.").defineInRange("maxBridgeDistance", 400, 1, 1000);
        bridgeDroopFactor = builder.comment("Percent of slack the bridge will have, causing it to hang.").defineInRange("bridgeDroopFactor", 100, 0, 100);
        bridgeYOffset = builder.comment("Generated bridges will be raised or lowered by this ammount in blocks.\nDefault is just below user's feet.").defineInRange("bridgeYOffset", -0.3, -1, 1);
        breakThroughBlocks = builder.comment("If enabled, all blocks that dare stand in a bridge's way will be broken.\nVery useful in creative mode.").define("breakThroughBlocks",  false);
        ignoreSlopeWarnings = builder.comment("Set true to ignore all slope warnings and allow building of very steep bridges.").define("ignoreSlopeWarnings", false);
        slabsPerBlock = builder.comment("Slabs consumed for each bridge block built.").defineInRange("slabsPerBlock",  1, 0, 10);
        stringPerBlock = builder.comment("String consumed for each bridge block built.").defineInRange("stringPerBlock", 2, 0, 20);
        woodPerBlock = builder.comment("Wood consumed for each ladder block built.").defineInRange("woodPerBlock",  1, 0, 10);
        ropePerBlock = builder.comment("Rope consumed for each ladder block built.").defineInRange("ropePerBlock",  2, 0, 20);
        bridgeDamage = builder.comment("How much the Ladder Gun is damaged after creating each ladder.").defineInRange("damageForLadder", 1, 0, 64);
        ladderDamage = builder.comment("How much the Bridge Gun is damaged after creating each ladder.").defineInRange("damageForBridge",  1, 0, 64);
        builder.pop();
    }

    public static boolean isIgnoreSlopeWarnings() {
        return ignoreSlopeWarnings.get();
    }

    public static int getBridgeDroopFactor() {
        return bridgeDroopFactor.get();
    }


    public static double getBridgeYOffset() {
        return bridgeYOffset.get();
    }


    public static boolean isBreakThroughBlocks() {
        return breakThroughBlocks.get();
    }

    public static int getMaxBridgeDistance() {
        return maxBridgeDistance.get();
    }

    public static int getRopePerBlock() {
        return ropePerBlock.get();
    }

    public static int getSlabsPerBlock() {
        return slabsPerBlock.get();
    }

    public static int getStringPerBlock() {
        return stringPerBlock.get();
    }

    public static int getWoodPerBlock() {
        return woodPerBlock.get();
    }

    public static int getLadderDamage () {
        return ladderDamage.get();
    }

    public static int getBridgeDamage () {
        return bridgeDamage.get();
    }
}