package net.fabricmc.outlands.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> OUTLAND_PLACED = PlacedFeatures.register("outland_tree_placed",
            ModConfiguredFeatures.OUTLAND_TREE_SPAWN, VegetationPlacedFeatures.modifiers(
                PlacedFeatures.createCountExtraModifier(1, 0.1f, 2)));
}
