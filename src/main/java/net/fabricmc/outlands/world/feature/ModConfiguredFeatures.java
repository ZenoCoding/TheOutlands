package net.fabricmc.outlands.world.feature;

import net.fabricmc.outlands.Main;
import net.fabricmc.outlands.block.ModBlocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> OUTLAND_TREE =
            ConfiguredFeatures.register("outland_tree", Feature.TREE, new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(ModBlocks.OUTLAND_LOG),
                    new LargeOakTrunkPlacer(5, 10, 3),
                    BlockStateProvider.of(ModBlocks.OLYUM_LEAVES),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 6),
                    new TwoLayersFeatureSize(1, 0, 2)).build());

    public static final RegistryEntry<PlacedFeature> OUTLAND_TREE_CHECKED =
            PlacedFeatures.register("outland_tree_checked", OUTLAND_TREE,
                    PlacedFeatures.wouldSurvive(ModBlocks.OLYUM_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> OUTLAND_TREE_SPAWN =
            ConfiguredFeatures.register("outland_tree_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(OUTLAND_TREE_CHECKED, 0.5f)),
                            OUTLAND_TREE_CHECKED));

    public static void registerConfiguredFeatures(){
        Main.LOGGER.info("Registering ModConfiguredFeatures for " + Main.MOD_ID);
    }
}
