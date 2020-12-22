package com.mrtrollnugnug.ropebridge.block;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;

import java.util.ArrayList;
import java.util.List;

public class RopeLadderBlock extends LadderBlock {

    public RopeLadderBlock(Properties properties) {
        super(properties);
    }

    private Block slab;

    public Block getSlab(){
        return slab;
    }

    public void setSlab(Block slab){
        this.slab = slab;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(ContentHandler.rope, ConfigHandler.getRopePerLadder()));
        drops.add(new ItemStack(slab, ConfigHandler.getWoodPerLadder()));
        return drops;
    }
}
