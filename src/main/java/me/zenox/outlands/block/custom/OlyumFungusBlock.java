package me.zenox.outlands.block.custom;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;

import java.util.function.Supplier;

public class OlyumFungusBlock extends OlyumPlantBlock implements Fertilizable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D);
    private static final double GROW_CHANCE = 0.05;
    private final Supplier<RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>>> feature;
    private final Supplier<RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>>> feature2;

    public OlyumFungusBlock(AbstractBlock.Settings settings, Supplier<RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>>> feature, Supplier<RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>>> feature2) {
        super(settings);
        this.feature = feature;
        this.feature2 = feature2;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.NYLIUM) || floor.isOf(Blocks.MYCELIUM) || floor.isOf(Blocks.SOUL_SOIL) || super.canPlantOnTop(floor, world, pos);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return (double) random.nextFloat() < 0.4;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.feature.get().value().generate(world, world.getChunkManager().getChunkGenerator(), random, pos);
        this.feature2.get().value().generate(world, world.getChunkManager().getChunkGenerator(), random, pos);
    }
}
