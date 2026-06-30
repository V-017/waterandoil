package net.v017.waterandoil;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;
import net.v017.waterandoil.fluid.ModFluids;
import net.v017.waterandoil.item.ModItems;
import net.v017.waterandoil.block.ModBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaterAndOil implements ModInitializer {
	public static final String MOD_ID = "waterandoil";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ModItems.initialize();
		ModBlocks.initialize();
		ModFluids.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
