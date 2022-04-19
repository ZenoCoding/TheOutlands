package net.fabricmc.outlands.world.feature;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

public class ModBiomeFeatures {
    public static void addOutlandsFeatures(GenerationSettings.Builder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OUTLAND_TREE_PLACED);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OUTLAND_SHRUB_PLACED);
    }
}
