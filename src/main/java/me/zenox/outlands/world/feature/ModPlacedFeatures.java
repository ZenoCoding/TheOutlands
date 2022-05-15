package me.zenox.outlands.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class ModPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> OLYUM_TREE_PLACED = PlacedFeatures.register("olyum_tree_placed",
            ModConfiguredFeatures.OLYUM_TREE_SPAWN, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(1, 0.1f, 2)));


    public static final RegistryEntry<PlacedFeature> OLYUM_FUNGI_PLACED = PlacedFeatures.register("olyum_fungi_placed", ModConfiguredFeatures.OLYUM_FUNGI, CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> MEGA_OLYUM_FUNGI_PLACED = PlacedFeatures.register("mega_olyum_fungi_placed", ModConfiguredFeatures.MEGA_OLYUM_FUNGI, CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> OLYUM_SHRUB_PLACED = PlacedFeatures.register("olyum_shrub_placed",
            ModConfiguredFeatures.PATCH_OLYUM_SHRUB, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(2, 0.1f, 2)));

    public static final RegistryEntry<PlacedFeature> OLYUM_SPROUTS_PATCH_PLACED = PlacedFeatures.register("olyum_sprouts_patch_placed",
            ModConfiguredFeatures.PATCH_OLYUM_SPROUTS, VegetationPlacedFeatures.modifiers(
                    PlacedFeatures.createCountExtraModifier(2, 0.1f, 2)));
}
