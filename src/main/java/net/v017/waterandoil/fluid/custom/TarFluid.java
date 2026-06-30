package net.v017.waterandoil.fluid.custom;

import java.util.Optional;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import net.v017.waterandoil.block.ModBlocks;
import net.v017.waterandoil.fluid.ModFluidTags;
import net.v017.waterandoil.fluid.ModFluids;
import net.v017.waterandoil.item.ModItems;


// #region abstract_fluid
public abstract class TarFluid extends FlowingFluid {
	// #endregion abstract_fluid

	//flowing block
	@Override
	public Fluid getFlowing() {
		return ModFluids.TAR_FLOWING;
	}
    //source block
	@Override
	public Fluid getSource() {
		return ModFluids.TAR_STILL;
	}
    //interaction with empty bucket
	@Override
	public Item getBucket() {
		return ModItems.TAR_BUCKET;
	}
    //animation
	@Override
	public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
		if (!state.isSource() && !(Boolean) state.getValue(FALLING)) {
			if (random.nextInt(64) == 0) {
				world.playLocalSound(
						pos.getX() + 0.5,
						pos.getY() + 0.5,
						pos.getZ() + 0.5,
						SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, // Bubbling poison/swamp sound
						SoundSource.AMBIENT,
						random.nextFloat() * 0.25F + 0.75F,
						random.nextFloat() + 0.5F,
						false);
			}
		} else if (random.nextInt(10) == 0) {
			world.addParticle(
					ParticleTypes.UNDERWATER, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(),
					pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
		}
	}
    //when entity is inside tar
	@Override
	protected void entityInside(Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler) {
		handler.apply(InsideBlockEffectType.EXTINGUISH);

		if (!(entity instanceof LivingEntity livingEntity)) return;

		if (world.getGameTime() % 20 == 0) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 300, -3));
		}
	}
    //drip effect
	@Nullable
	@Override
	public ParticleOptions getDripParticle() {
		return ParticleTypes.DRIPPING_WATER;
	}
    //when destroying
	@Override
	protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropResources(state, world, pos, blockEntity);
	}
    //how fast it spreads
	@Override
	protected int getSlopeFindDistance(LevelReader world) {
		return 1;
	}
    //idk
	@Override
	public boolean isSame(Fluid fluid) {
		return fluid == ModFluids.TAR_STILL || fluid == ModFluids.TAR_FLOWING;
	}
    // //stole this from lavafluid
    // 	private void fizz(final LevelAccessor level, final BlockPos pos) {
	// 	level.levelEvent(1501, pos, 0);
	// }
    // //interaction with other fluids
	// @Override
	// protected void spreadTo(final LevelAccessor level, final BlockPos pos, final BlockState state, final Direction direction, final FluidState target) {
	// 	if (direction == Direction.DOWN) {
	// 		FluidState fluidState = level.getFluidState(pos);
	// 		if (this.is(ModFluidTags.TAR) && fluidState.is(FluidTags.LAVA)) {
	// 			if (state.getBlock() instanceof LiquidBlock) {
	// 				level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
	// 			}

	// 			this.fizz(level, pos);
	// 			return;
	// 		}
	// 	}

	// 	super.spreadTo(level, pos, state, direction, target);
	// }

	@Override
	protected boolean canConvertToSource(ServerLevel world) {
		return world.getGameRules().get(GameRules.LAVA_SOURCE_CONVERSION);
	}




	// #endregion abstract_fluid
	// #region legacy_block
	@Override
	protected BlockState createLegacyBlock(FluidState state) {
		return ModBlocks.TAR.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
	}
	// #endregion legacy_block
	// #region abstract_fluid
	@Override
	public int getDropOff(LevelReader world) {
		return 1;
	}

	@Override
	public int getTickDelay(LevelReader world) {
		return 5;
	}

	@Override
	public boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid,
			Direction direction) {
		return direction == Direction.DOWN && !fluid.is(ModFluidTags.TAR);
	}

	@Override
	protected float getExplosionResistance() {
		return 100.0F;
	}

	@Override
	public Optional<SoundEvent> getPickupSound() {
		return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
	}
	// #endregion abstract_fluid
	// #region fluid_subclasses
	public static class Flowing extends TarFluid {
		@Override
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		@Override
		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		@Override
		public boolean isSource(FluidState state) {
			return false;
		}
	}

	public static class Source extends TarFluid {
		@Override
		public int getAmount(FluidState state) {
			return 8;
		}

		@Override
		public boolean isSource(FluidState state) {
			return true;
		}
	}
	// #endregion fluid_subclasses
	// #region abstract_fluid
}
// #endregion abstract_fluid