package net.v017.waterandoil.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.v017.waterandoil.feature.ModConfiguredFeatures;
import net.v017.waterandoil.feature.ModPlacedFeatures;
import net.v017.waterandoil.feature.WaterAndOilWorldgenProvider;

public class WaterAndOilDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(WaterAndOilWorldgenProvider::new);
        pack.addProvider(ModRecipeProvider::new);
	}
    @Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
