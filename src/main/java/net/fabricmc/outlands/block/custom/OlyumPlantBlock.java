package net.fabricmc.outlands.block.custom;

import net.fabricmc.outlands.block.ModBlocks;
import net.fabricmc.outlands.world.feature.ModConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Random;

public class OlyumPlantBlock extends PlantBlock implements Fertilizable{
    protected static final float field_31261 = 6.0F;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D);

    public OlyumPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.OLCRIUM) || floor.isOf(ModBlocks.OLYUM_LOG) || floor.isOf(ModBlocks.OLYUM_OLCRIUM);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        ModConfiguredFeatures.PATCH_OLYUM_SHRUB.value().generate(world, chunkGenerator, random, blockPos);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }
}
