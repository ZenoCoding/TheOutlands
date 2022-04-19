package net.fabricmc.outlands.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.outlands.world.biome.ModBiomes;
import net.fabricmc.outlands.world.feature.ModPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {
    public static void generateTrees(){
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.OUTLANDS),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OUTLAND_TREE_PLACED.getKey().get());
    }
}
