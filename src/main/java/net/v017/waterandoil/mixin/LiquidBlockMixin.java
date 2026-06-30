package net.v017.waterandoil.mixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.v017.waterandoil.fluid.ModFluidTags;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {
    @ModifyReturnValue(method = "shouldSpreadLiquid", at = @At("RETURN"))
    private boolean onShouldSpreadLiquid(boolean original, Level level, BlockPos pos, BlockState state) {
        FluidState fluidState = level.getFluidState(pos);
        if (fluidState.is(ModFluidTags.TAR)) {
            for (Direction direction : Direction.Plane.HORIZONTAL) { // avoid needing access widener
                BlockPos neighbourPos = pos.relative(direction);
                if (level.getFluidState(neighbourPos).is(FluidTags.LAVA)) {
                    level.setBlockAndUpdate(pos, Blocks.NETHERRACK.defaultBlockState());
                    return false;
                }
            }
        }
        return original;
    }
}
