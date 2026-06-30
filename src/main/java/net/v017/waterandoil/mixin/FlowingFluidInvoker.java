package net.v017.waterandoil.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;

@Mixin(FlowingFluid.class)
public interface FlowingFluidInvoker {
    @Invoker("getNewLiquid")
    FluidState callGetNewLiquid(ServerLevel level, BlockPos pos, BlockState state);
}
