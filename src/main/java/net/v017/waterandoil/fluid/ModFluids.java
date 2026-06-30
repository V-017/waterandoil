package net.v017.waterandoil.fluid;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FlowingFluid;

import net.v017.waterandoil.WaterAndOil;
import net.v017.waterandoil.fluid.custom.TarFluid;

// #region register
public class ModFluids {
	public static final FlowingFluid TAR_FLOWING = register("flowing_tar", new TarFluid.Flowing());
	public static final FlowingFluid TAR_STILL = register("tar", new TarFluid.Source());

	private static FlowingFluid register(String name, FlowingFluid fluid) {
		return Registry.register(BuiltInRegistries.FLUID, Identifier.fromNamespaceAndPath(WaterAndOil.MOD_ID, name), fluid);
	}

	public static void initialize() {
	}
}
// #endregion register