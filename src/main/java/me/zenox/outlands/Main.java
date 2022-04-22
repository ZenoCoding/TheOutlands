package me.zenox.outlands;

import me.zenox.outlands.entity.ModEntities;
import me.zenox.outlands.util.ModRegistries;
import me.zenox.outlands.world.feature.ModConfiguredFeatures;
import net.fabricmc.api.ModInitializer;
import me.zenox.outlands.block.ModBlocks;
import me.zenox.outlands.item.ModItems;
import me.zenox.outlands.world.biome.ModBiomes;
import me.zenox.outlands.world.gen.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class Main implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.

    public static final String MOD_ID = "outlands";
    public static final Logger LOGGER = LoggerFactory.getLogger("Outlands");

    @Override
    public void onInitialize() {

        LOGGER.info("Hello Fabric world!");

        GeckoLib.initialize();

        ModItems.registerItems();
        ModBlocks.registerModBlocks();

        ModEntities.registerEntities();

        ModRegistries.registerEvents();
        ModRegistries.registerFlammableBlocks();
        ModRegistries.registerRenderLayers();

        ModConfiguredFeatures.registerConfiguredFeatures();
        ModBiomes.registerBiomes();
        ModWorldGen.generateModWorldGen();
        // ModParticles.registerParticles();


    }


}
