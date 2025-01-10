package com.mrtrollnugnug.ropebridge.datagen.client;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider {

	public ModSoundProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
		super(packOutput, Constants.MOD_ID, existingFileHelper);
	}

	@Override
	public void registerSounds() {
		this.add(ContentHandler.load, definition()
			.subtitle(modSubtitle(ContentHandler.load.getId()))
			.with(sound(Constants.modLoc("cock"))));

		this.add(ContentHandler.swoosh, definition()
			.subtitle(modSubtitle(ContentHandler.swoosh.getId()))
			.with(sound(Constants.modLoc("swoosh"))));
	}

	public String modSubtitle(ResourceLocation id) {
		return Constants.MOD_ID + ".subtitle." + id.getPath();
	}
}
