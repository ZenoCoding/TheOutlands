package me.zenox.outlands.world.feature;

import me.zenox.outlands.Main;
import me.zenox.outlands.block.ModBlocks;
import me.zenox.outlands.world.feature.tree.MegaOlyumFungiFeature;
import me.zenox.outlands.world.feature.tree.OlyumFungiFeature;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> OLYUM_TREE =
            ConfiguredFeatures.register("olyum_tree", Feature.TREE, new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(ModBlocks.OLYUM_LOG),
                    new LargeOakTrunkPlacer(5, 10, 3),
                    BlockStateProvider.of(ModBlocks.OLYUM_LEAVES),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 6),
                    new TwoLayersFeatureSize(1, 0, 2)).build());

    public static final RegistryEntry<PlacedFeature> OLYUM_TREE_CHECKED =
            PlacedFeatures.register("olyum_tree_check", OLYUM_TREE,
                    PlacedFeatures.wouldSurvive(ModBlocks.OLYUM_SAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> OLYUM_TREE_SPAWN =
            ConfiguredFeatures.register("olyum_tree_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfig(List.of(new RandomFeatureEntry(OLYUM_TREE_CHECKED, 0.5f)),
                            OLYUM_TREE_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> OLYUM_FUNGI =
            ConfiguredFeatures.register("olyum_fungi", registerFeature("olyum_fungi", new OlyumFungiFeature(DefaultFeatureConfig.CODEC)));

    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> MEGA_OLYUM_FUNGI =
            ConfiguredFeatures.register("mega_olyum_fungi", registerFeature("mega_olyum_fungi", new MegaOlyumFungiFeature(DefaultFeatureConfig.CODEC)));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_OLYUM_SHRUB =
            ConfiguredFeatures.register("patch_olyum_shrub", Feature.RANDOM_PATCH,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(32, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.OLYUM_SHRUB)))));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> PATCH_OLYUM_SPROUTS =
            ConfiguredFeatures.register("patch_olyum_sprouts", Feature.RANDOM_PATCH,
                    ConfiguredFeatures.createRandomPatchFeatureConfig(32, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.OLYUM_SPROUTS)))));

    public static void registerConfiguredFeatures(){
        Main.LOGGER.info("Registering ModConfiguredFeatures for " + Main.MOD_ID);
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, name, feature);
    }
}
