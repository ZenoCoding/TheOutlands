package me.zenox.outlands.world.feature.tree;

import me.zenox.outlands.world.feature.ModConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class OutlandSaplingGenerator extends SaplingGenerator {

    @Nullable
    @Override
    public RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return ModConfiguredFeatures.OLYUM_TREE;
    }
}
