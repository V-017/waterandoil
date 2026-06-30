package net.v017.waterandoil.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.v017.waterandoil.WaterAndOil;

// #region tags
public class ModFluidTags {
	public static TagKey<Fluid> TAR = TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(WaterAndOil.MOD_ID, "tar"));
}
// #endregion tags