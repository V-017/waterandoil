package net.v017.waterandoil.feature;

import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import net.v017.waterandoil.block.ModBlocks;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> TAR_POOL_CONFIGURED_KEY =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    Identifier.fromNamespaceAndPath("waterandoil", "tar_pool")
            );

    public static void bootstrap(final BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(
			context,
			TAR_POOL_CONFIGURED_KEY,
			Feature.LAKE,
			new LakeFeature.Configuration(
				BlockStateProvider.simple(ModBlocks.TAR.defaultBlockState()),
				BlockStateProvider.simple(Blocks.NETHERRACK.defaultBlockState()),
				BlockPredicate.alwaysTrue(),
				BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.FEATURES_CANNOT_REPLACE)),
				BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE))
			)
		);
    }
}