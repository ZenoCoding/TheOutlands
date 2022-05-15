package me.zenox.outlands.world.biome;

import me.zenox.outlands.entity.ModEntities;
import me.zenox.outlands.world.feature.ModBiomeFeatures;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

import javax.annotation.Nullable;

public class ModOverworldBiomes {
    @Nullable
    private static final MusicSound NORMAL_MusicSound = null;

    protected static int calculateSkyColor(float color) {
        float $$1 = color / 3.0F;
        $$1 = MathHelper.clamp($$1, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }


    private static void globalOverworldGeneration(GenerationSettings.Builder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    public static Biome outlands() {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.OUTLAND_INQUISITOR, 1, 1, 1));
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.EOLIFLY, 10, 2, 5));

        GenerationSettings.Builder biomeBuilder = new GenerationSettings.Builder();
        globalOverworldGeneration(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addDefaultDisks(biomeBuilder);
        ModBiomeFeatures.addOlyumVegetation(biomeBuilder);
        ModBiomeFeatures.addOlyumTrees(biomeBuilder);
        return new Biome.Builder().precipitation(Biome.Precipitation.NONE).category(Biome.Category.JUNGLE).temperature(2F).downfall(2f).effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(0x423961).skyColor(0).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();
    }


}