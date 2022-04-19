package net.fabricmc.outlands.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> OLYUM_TREE_PLACED = PlacedFeatures.register("olyum_tree_placed",
            ModConfiguredFeatures.OLYUM_TREE_SPAWN, VegetationPlacedFeatures.modifiers(
                PlacedFeatures.createCountExtraModifier(1, 0.1f, 2)));

    public static final RegistryEntry<PlacedFeature> OLYUM_SHRUB_PLACED = PlacedFeatures.register("olyum_shrub_placed",
            ModConfiguredFeatures.PATCH_OLYUM_SHRUB, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(2, 0.1f, 2)));
}