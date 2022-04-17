package net.fabricmc.outlands;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.outlands.block.ModBlocks;
import net.fabricmc.outlands.entity.ModEntities;
import net.fabricmc.outlands.item.ModItems;
import net.fabricmc.outlands.util.ModRegistries;
import net.fabricmc.outlands.world.feature.ModConfiguredFeatures;
import net.fabricmc.outlands.world.gen.ModWorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

import java.util.Random;

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
		ModConfiguredFeatures.registerConfiguredFeatures();

		ModWorldGen.generateModWorldGen();
		// ModParticles.registerParticles();


	}
}
