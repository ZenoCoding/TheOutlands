package me.zenox.outlands.world.feature;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

public class ModBiomeFeatures {
    public static void addOlyumVegetation(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OLYUM_SHRUB_PLACED);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OLYUM_FUNGI_PATCH_PLACED);
    }

    public static void addOlyumTrees(GenerationSettings.Builder builder){
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OLYUM_TREE_PLACED);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OLYUM_FUNGI_PLACED);
    }
}
