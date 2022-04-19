package net.fabricmc.outlands.block.custom;

import net.fabricmc.outlands.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class OlyumSaplingBlock extends SaplingBlock {
    public OlyumSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
    }

    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.OLCRIUM) || floor.isOf(ModBlocks.OLYUM_OLCRIUM) || floor.isIn(BlockTags.SOUL_SPEED_BLOCKS) || floor.isOf(Blocks.COARSE_DIRT);
    }
}
