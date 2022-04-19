package net.fabricmc.outlands.world.biome;

import net.fabricmc.outlands.Main;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class ModBiomes {

    public static final RegistryKey<Biome> OUTLANDS = registerBiome("outlands", ModOverworldBiomes.outlands());

    private static RegistryKey<Biome> registerBiome(String name, Biome biome)
    {
        RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Main.MOD_ID, name));
        BuiltinRegistries.add(BuiltinRegistries.BIOME, key, biome);
        return key;
    }

    public static void registerBiomes() {
        Main.LOGGER.info("Registering ModBiomes for " + Main.MOD_ID);
    }
}
