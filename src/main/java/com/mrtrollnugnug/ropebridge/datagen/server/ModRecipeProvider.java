package com.mrtrollnugnug.ropebridge.datagen.server;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void buildRecipes(RecipeOutput output, Provider provider) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ContentHandler.bridge_builder.get())
			.pattern("TBH")
			.define('T', ContentHandler.bridge_builder_hook.get())
			.define('B', ContentHandler.bridge_builder_barrel.get())
			.define('H', ContentHandler.bridge_builder_handle.get())
			.unlockedBy("has_hook", has(ContentHandler.bridge_builder_hook.get()))
			.unlockedBy("has_barrel", has(ContentHandler.bridge_builder_barrel.get()))
			.unlockedBy("has_handle", has(ContentHandler.bridge_builder_handle.get()))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ContentHandler.bridge_builder_barrel.get())
			.pattern("III")
			.pattern("RRR")
			.pattern("III")
			.define('I', Tags.Items.INGOTS_IRON)
			.define('R', ContentHandler.rope.get())
			.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
			.unlockedBy("has_rope", has(ContentHandler.rope.get()))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ContentHandler.bridge_builder_handle.get())
			.pattern("I F")
			.pattern("RG ")
			.pattern("IWW")
			.define('I', Tags.Items.INGOTS_IRON)
			.define('F', Items.FLINT_AND_STEEL)
			.define('R', ContentHandler.rope.get())
			.define('G', Items.GUNPOWDER)
			.define('W', ItemTags.PLANKS)
			.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
			.unlockedBy("has_flint_and_steel", has(Items.FLINT_AND_STEEL))
			.unlockedBy("has_rope", has(ContentHandler.rope.get()))
			.unlockedBy("has_gunpowder", has(Items.GUNPOWDER))
			.unlockedBy("has_planks", has(ItemTags.PLANKS))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ContentHandler.bridge_builder_hook.get())
			.pattern("I  ")
			.pattern("III")
			.pattern("I  ")
			.define('I', Tags.Items.INGOTS_IRON)
			.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ContentHandler.ladder_builder.get())
			.pattern("TBH")
			.define('T', ContentHandler.ladder_hook.get())
			.define('B', ContentHandler.bridge_builder_barrel.get())
			.define('H', ContentHandler.bridge_builder_handle.get())
			.unlockedBy("has_ladder_hook", has(ContentHandler.ladder_hook.get()))
			.unlockedBy("has_bridge_builder_barrel", has(ContentHandler.bridge_builder_barrel.get()))
			.unlockedBy("has_bridge_builder_handle", has(ContentHandler.bridge_builder_handle.get()))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ContentHandler.ladder_hook.get())
			.pattern("R  ")
			.pattern("III")
			.pattern("I  ")
			.define('I', Tags.Items.INGOTS_IRON)
			.define('R', ContentHandler.rope.get())
			.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
			.unlockedBy("has_rope", has(ContentHandler.rope.get()))
			.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ContentHandler.rope.get(), 8)
			.pattern("SV")
			.pattern("VS")
			.pattern("SV")
			.define('S', Items.STRING)
			.define('V', Items.VINE)
			.unlockedBy("has_string", has(Items.STRING))
			.unlockedBy("has_vine", has(Items.VINE))
			.save(output);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 4)
			.requires(Items.WHITE_WOOL)
			.unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
			.save(output, Constants.modLoc("string"));
	}
}
