package com.mrtrollnugnug.ropebridge.lib;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

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
    public static void tellPlayer(PlayerEntity sender, String message, Object... params) {
        sender.sendMessage(new TranslationTextComponent(message, params));
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
}