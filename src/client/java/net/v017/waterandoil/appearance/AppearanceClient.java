package net.v017.waterandoil.appearance;

import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.v017.waterandoil.fluid.ModFluids;


public class AppearanceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		// #region fluid_texture
		FluidRenderingRegistry.register(
				ModFluids.TAR_STILL,
				ModFluids.TAR_FLOWING,
				new FluidModel.Unbaked(
						new Material(Identifier.withDefaultNamespace("block/lava_still")),
						new Material(Identifier.withDefaultNamespace("block/lava_flow")),
						new Material(Identifier.withDefaultNamespace("block/lava_overlay")),
						BlockTintSources.constant(ARGB.color(20,20,20))
				)
		);
		// #endregion fluid_texture
	}
}