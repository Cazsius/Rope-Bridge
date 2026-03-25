package com.mrtrollnugnug.ropebridge.datagen.client;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider {

	public ModSoundProvider(PackOutput packOutput) {
		super(packOutput, Constants.MOD_ID);
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

	public String modSubtitle(Identifier id) {
		return Constants.MOD_ID + ".subtitle." + id.getPath();
	}
}
