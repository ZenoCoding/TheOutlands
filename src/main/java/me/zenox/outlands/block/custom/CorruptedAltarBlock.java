package me.zenox.outlands.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class CorruptedAltarBlock extends Block implements BlockEntityProvider {
    public CorruptedAltarBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CorruptedAltarBlockEntity(pos, state);
    }
}
