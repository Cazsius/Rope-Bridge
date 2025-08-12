package com.mrtrollnugnug.ropebridge.datagen.client;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(PackOutput packOutput) {
		super(packOutput, Constants.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + Constants.MOD_ID + ".tab", "Rope Bridge");

		addItem(ContentHandler.bridge_builder, "Bridge Building Gun");
		addItem(ContentHandler.bridge_builder_hook, "Bridge Building Gun Hook");
		addItem(ContentHandler.bridge_builder_barrel, "Gun Barrel");
		addItem(ContentHandler.bridge_builder_handle, "Gun Stock");
		addItem(ContentHandler.rope, "Rope");
		addItem(ContentHandler.ladder_hook, "Ladder Gun Hook");
		addItem(ContentHandler.ladder_builder, "Ladder Gun");

		addBlock(ContentHandler.oak_bridge, "Oak Rope Bridge");
		addBlock(ContentHandler.birch_bridge, "Birch Rope Bridge");
		addBlock(ContentHandler.jungle_bridge, "Jungle Rope Bridge");
		addBlock(ContentHandler.spruce_bridge, "Spruce Rope Bridge");
		addBlock(ContentHandler.acacia_bridge, "Acacia Rope Bridge");
		addBlock(ContentHandler.cherry_bridge, "Cherry Rope Bridge");
		addBlock(ContentHandler.dark_oak_bridge, "Dark Oak Rope Bridge");
		addBlock(ContentHandler.mangrove_bridge, "Mangrove Rope Bridge");
		addBlock(ContentHandler.bamboo_bridge, "Bamboo Rope Bridge");
		addBlock(ContentHandler.crimson_bridge, "Crimson Rope Bridge");
		addBlock(ContentHandler.warped_bridge, "Warped Rope Bridge");

		addBlock(ContentHandler.oak_rope_ladder, "Oak Rope Ladder");
		addBlock(ContentHandler.birch_rope_ladder, "Birch Rope Ladder");
		addBlock(ContentHandler.jungle_rope_ladder, "Jungle Rope Ladder");
		addBlock(ContentHandler.spruce_rope_ladder, "Spruce Rope Ladder");
		addBlock(ContentHandler.acacia_rope_ladder, "Acacia Rope Ladder");
		addBlock(ContentHandler.cherry_rope_ladder, "Cherry Rope Ladder");
		addBlock(ContentHandler.dark_oak_rope_ladder, "Dark Oak Rope Ladder");
		addBlock(ContentHandler.mangrove_rope_ladder, "Mangrove Rope Ladder");
		addBlock(ContentHandler.bamboo_rope_ladder, "Bamboo Rope Ladder");
		addBlock(ContentHandler.crimson_rope_ladder, "Crimson Rope Ladder");
		addBlock(ContentHandler.warped_rope_ladder, "Warped Rope Ladder");

		addSubtitle(ContentHandler.load, "Loading Gun");
		addSubtitle(ContentHandler.swoosh, "Gun Swoosh");

		addAdvancement("root", "Rope Bridge", "Build bridges with ease!");
		addAdvancement("craft_bridge_builder", "Bridge Building Gun", "Craft a bridge building grappling gun with a bridge hook, barrel, and handle");
		addAdvancement("build_bridge", "Rope Bridge", "Build a rope bridge with your new Bridge Building Gun");
		addAdvancement("craft_ladder_builder", "Ladder Building Gun", "Craft a ladder building grappling gun with a ladder hook, barrel, and handle");
		addAdvancement("build_ladder", "Rope Ladder", "Build a rope ladder with your new Ladder Gun");

		add("chat.ropebridge.warning.breaking", "WARNING! Breaking whole bridge!");
		add("chat.ropebridge.info.notonground", "You must be standing on something to build a bridge!");
		add("chat.ropebridge.info.notcardinal", "Sorry, bridge must be built in a cardinal direction. Please try again.");
		add("chat.ropebridge.info.greatslope", "Sorry, your slope is too great. Please try again.");
		add("chat.ropebridge.info.obstruction", "Oops! Looks like there's something in the way.");
		//TODO Fix smoke! Look for the Smoke to see where that is and try again
		add("chat.ropebridge.info.underfunded_bridge", "You need at least %f slabs and %f rope to build this bridge.");
		add("chat.ropebridge.info.underfunded_ladder", "You need at least %f slabs and %f rope to build this ladder.");
		add("chat.ropebridge.info.bad_side", "You can't build a ladder on the %f of a block. Try the sides!");
		add("chat.ropebridge.info.not_solid", "That block can't support a rope ladder on that side.");
		add("chat.ropebridge.params.top", "top");
		add("chat.ropebridge.params.bottom", "bottom");
	}


	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event
	 * @param text  The subtitle text
	 */
	public void addSubtitle(DeferredHolder<SoundEvent, SoundEvent> sound, String text) {
		this.addSubtitle(sound.get(), text);
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event registry object
	 * @param text  The subtitle text
	 */
	public void addSubtitle(SoundEvent sound, String text) {
		String path = Constants.MOD_ID + ".subtitle." + sound.location().getPath();
		this.add(path, text);
	}


	/**
	 * Add the translation of an advancement
	 *
	 * @param id          The advancement id
	 * @param name        The name of the advancement
	 * @param description The description of the advancement
	 */
	private void addAdvancement(String id, String name, String description) {
		String prefix = "advancement." + Constants.MOD_ID + ".";
		add(prefix + id + ".title", name);
		add(prefix + id + ".desc", description);
	}
}
