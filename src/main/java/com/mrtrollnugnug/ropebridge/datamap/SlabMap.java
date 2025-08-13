package com.mrtrollnugnug.ropebridge.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public record SlabMap(Block bridge, Block ladder) {
	public static final Codec<SlabMap> CODEC = RecordCodecBuilder.create(in -> in.group(
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("bridge").forGetter(SlabMap::bridge),
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("ladder").forGetter(SlabMap::ladder)
		)
		.apply(in, SlabMap::new));
}
