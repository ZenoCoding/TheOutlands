package net.fabricmc.test.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.test.Main;
import net.fabricmc.test.item.custom.ModAxeItem;
import net.fabricmc.test.item.custom.ModBattleaxeItem;
import net.fabricmc.test.item.custom.ModPickaxeItem;
import net.fabricmc.test.item.custom.OutlandIngot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final ItemGroup OUTLAND_ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier("testmod", "general"), () -> new ItemStack(ModItems.OUTLAND_INGOT));

    public static final Item OUTLAND_INGOT = registerItem("outland_ingot",
            new OutlandIngot(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_SHARD = registerItem("outland_shard",
            new Item(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    // Tools

    public static final Item OUTLAND_SWORD = registerItem("outland_sword",
        new SwordItem(ModToolMaterials.OUTLAND, 7, 2f, new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_AXE = registerItem("outland_axe",
            new ModAxeItem(ModToolMaterials.OUTLAND, 13, 1f, new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_BATTLEAXE = registerItem("outland_battleaxe",
            new ModBattleaxeItem(ModToolMaterials.OUTLAND, 10, 1.3f, new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_PICKAXE = registerItem("outland_pickaxe",
            new ModPickaxeItem(ModToolMaterials.OUTLAND, 6, 2f, new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, name), item);
    }

    public static void registerItems(){
        Main.LOGGER.info("Registering ModItems for " + Main.MOD_ID);
    }

}
