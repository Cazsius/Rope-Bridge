package com.mrtrollnugnug.ropebridge.lib;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.block.RopeLadderBlock;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public final class ModUtils {
   
    /**
     * Sends a message to a command sender. Can be used for easier message
     * sending.
     *
     * @param sender
     *            The thing to send the message to. This should probably be a
     *            player.
     * @param message
     *            The message to send. This can be a normal message, however
     *            translation keys are HIGHLY encouraged!
     */

    public static final Map<Block, Pair<Block, Block>> map = new HashMap<>();

    public static void tellPlayer(PlayerEntity sender, String message, Object... params) {
        sender.sendMessage(new TranslationTextComponent(message, params), Util.DUMMY_UUID);
    }

    public static <T extends IForgeRegistryEntry<T>> void register(T obj, String name, IForgeRegistry<T> registry) {
        register(obj, Constants.MOD_ID, name, registry);
    }

    public static <T extends IForgeRegistryEntry<T>> void register(T obj, String modid, String name, IForgeRegistry<T> registry) {
        register(obj,new ResourceLocation(modid, name),registry);
    }

    public static <T extends IForgeRegistryEntry<T>> void register(T obj, ResourceLocation location, IForgeRegistry<T> registry) {
        registry.register(obj.setRegistryName(location));
    }

    public static void initMap(){
        map.put(Blocks.OAK_SLAB, Pair.of(ContentHandler.oak_bridge,ContentHandler.oak_rope_ladder));
        map.put(Blocks.BIRCH_SLAB, Pair.of(ContentHandler.birch_bridge,ContentHandler.birch_rope_ladder));
        map.put(Blocks.JUNGLE_SLAB, Pair.of(ContentHandler.jungle_bridge,ContentHandler.jungle_rope_ladder));
        map.put(Blocks.SPRUCE_SLAB, Pair.of(ContentHandler.spruce_bridge,ContentHandler.spruce_rope_ladder));
        map.put(Blocks.ACACIA_SLAB, Pair.of(ContentHandler.acacia_bridge,ContentHandler.acacia_rope_ladder));
        map.put(Blocks.DARK_OAK_SLAB, Pair.of(ContentHandler.dark_oak_bridge,ContentHandler.dark_oak_rope_ladder));

        ((RopeLadderBlock)ContentHandler.oak_rope_ladder).setSlab(Blocks.OAK_SLAB);
        ((RopeLadderBlock)ContentHandler.birch_rope_ladder).setSlab(Blocks.BIRCH_SLAB);
        ((RopeLadderBlock)ContentHandler.jungle_rope_ladder).setSlab(Blocks.JUNGLE_SLAB);
        ((RopeLadderBlock)ContentHandler.spruce_rope_ladder).setSlab(Blocks.SPRUCE_SLAB);
        ((RopeLadderBlock)ContentHandler.acacia_rope_ladder).setSlab(Blocks.ACACIA_SLAB);
        ((RopeLadderBlock)ContentHandler.dark_oak_rope_ladder).setSlab(Blocks.DARK_OAK_SLAB);

        ((RopeBridgeBlock)ContentHandler.oak_bridge).setSlab(Blocks.OAK_SLAB);
        ((RopeBridgeBlock)ContentHandler.birch_bridge).setSlab(Blocks.BIRCH_SLAB);
        ((RopeBridgeBlock)ContentHandler.jungle_bridge).setSlab(Blocks.JUNGLE_SLAB);
        ((RopeBridgeBlock)ContentHandler.spruce_bridge).setSlab(Blocks.SPRUCE_SLAB);
        ((RopeBridgeBlock)ContentHandler.acacia_bridge).setSlab(Blocks.ACACIA_SLAB);
        ((RopeBridgeBlock)ContentHandler.dark_oak_bridge).setSlab(Blocks.DARK_OAK_SLAB);
    }
}