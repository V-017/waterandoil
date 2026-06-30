package net.v017.waterandoil.mixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.v017.waterandoil.fluid.ModFluidTags;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FlowingFluid.class)
public abstract class FlowingFluidMixin {

    @Redirect(
        method = "getSpread",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;canBeReplacedWith(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/core/Direction;)Z")
    )
    private boolean redirectCanBeReplacedWith(FluidState existingState, BlockGetter level, BlockPos pos, Fluid newFluidType, Direction direction) {
        // default vanilla check first
        boolean vanillaResult = existingState.canBeReplacedWith(level, pos, newFluidType, direction);

        boolean existingIsTar = existingState.is(ModFluidTags.TAR);
        boolean newIsWater = newFluidType.is(FluidTags.WATER);
        boolean existingIsWater = existingState.is(FluidTags.WATER);
        boolean newIsTar = newFluidType.is(ModFluidTags.TAR);

        if ((existingIsTar && newIsWater) || (existingIsWater && newIsTar)) {
        FluidState recomputed = ((FlowingFluidInvoker) (Object) this).callGetNewLiquid((ServerLevel) level, pos, level.getBlockState(pos));
        return recomputed.getAmount() > existingState.getAmount();
    }

        return vanillaResult;
    }
}