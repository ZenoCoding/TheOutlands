package me.zenox.outlands.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;


public class ModRegion extends Region {
    public ModRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(BiomeKeys.BADLANDS, ModBiomes.OUTLANDS);
            builder.replaceBiome(BiomeKeys.WOODED_BADLANDS, ModBiomes.OUTLANDS);
            builder.replaceBiome(BiomeKeys.ERODED_BADLANDS, ModBiomes.OUTLANDS);
            builder.replaceBiome(BiomeKeys.DESERT, ModBiomes.OUTLANDS);
            builder.replaceBiome(BiomeKeys.MEADOW, ModBiomes.OUTLANDS);

        });
    }
}
