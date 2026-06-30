package net.v017.waterandoil.item;


import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.v017.waterandoil.WaterAndOil;
import net.v017.waterandoil.fluid.ModFluids;



public class ModItems {

	// #region custom_creative_tab
	public static final ResourceKey<CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
			BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(WaterAndOil.MOD_ID, "creative_tab")
	);
	public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricCreativeModeTab.builder()
			//.icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
			.title(Component.translatable("creativeTab.water-and-oil"))
			.displayItems((params, output) -> {
				output.accept(ModItems.TAR_BUCKET);
				// #region custom_creative_tab
			})
			.build();
	// #endregion custom_creative_tab

	// #region mod_items_class
	public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		// Create the item key.
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WaterAndOil.MOD_ID, name));

		// Create the item instance.
		T item = itemFactory.apply(settings.setId(itemKey));

		// Register the item.
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);

		return item;
	}
    public static void initialize() {

            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
            // #endregion register_creative_tab

            CreativeModeTabEvents.modifyOutputEvent(CUSTOM_CREATIVE_TAB_KEY).register(creativeTab -> {
                creativeTab.accept(ModItems.TAR_BUCKET);
            });
        }
	// #endregion mod_items_class

	// #region tar_bucket
	public static final Item TAR_BUCKET = register(
			"tar_bucket",
			props -> new BucketItem(ModFluids.TAR_STILL, props),
			new Item.Properties()
					.craftRemainder(Items.BUCKET)
					.stacksTo(1)
	);
	// #endregion tar_bucket

	// #region mod_items_class
}
// #endregion mod_items_class