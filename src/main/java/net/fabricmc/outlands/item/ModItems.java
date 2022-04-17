package net.fabricmc.outlands.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.outlands.Main;
import net.fabricmc.outlands.item.custom.ModAxeItem;
import net.fabricmc.outlands.item.custom.ModBowItem;
import net.fabricmc.outlands.item.custom.ModPickaxeItem;
import net.fabricmc.outlands.item.custom.OutlandIngot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final ItemGroup OUTLAND_ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier("outlands", "general"), () -> new ItemStack(ModItems.OUTLAND_INGOT));

    public static final Item OUTLAND_INGOT = registerItem("outland_ingot",
            new OutlandIngot(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_SHARD = registerItem("outland_shard",
            new Item(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item NETHERITE_HANDLE = registerItem("netherite_handle",
            new Item(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP).maxCount(1)));

    // Tools/Weapons

    public static final Item OUTLAND_SWORD = registerItem("outland_sword",
            new SwordItem(ModToolMaterials.OUTLAND, 7, -2f,
                    new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_AXE = registerItem("outland_axe",
            new ModAxeItem(ModToolMaterials.OUTLAND, 13, -3f,
                    new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_BATTLEAXE = registerItem("outland_battleaxe",
            new ModAxeItem(ModToolMaterials.OUTLAND, 10, -2.7f,
                    new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_PICKAXE = registerItem("outland_pickaxe",
            new ModPickaxeItem(ModToolMaterials.OUTLAND, 5, -2.5f,
                    new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_BOW = registerItem("outland_bow",
            new ModBowItem(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP).maxDamage(3000)));

    // Tool Heads
    public static final Item OUTLAND_SWORD_BLADE = registerItem("outland_sword_blade",
            new Item(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_SWORD_HILT = registerItem("outland_sword_hilt",
            new Item(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_AXE_HEAD = registerItem("outland_axe_head",
            new Item(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_BATTLEAXE_HEAD = registerItem("outland_battleaxe_head",
            new Item(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_PICKAXE_HEAD = registerItem("outland_pickaxe_head",
            new Item(new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    // Armor

    public static final Item OUTLAND_HELMET = registerItem("outland_helmet",
            new ArmorItem(ModArmorMaterials.OUTLAND, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_CHESTPLATE = registerItem("outland_chestplate",
            new ArmorItem(ModArmorMaterials.OUTLAND, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_LEGGINGS = registerItem("outland_leggings",
            new ArmorItem(ModArmorMaterials.OUTLAND, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    public static final Item OUTLAND_BOOTS = registerItem("outland_boots",
            new ArmorItem(ModArmorMaterials.OUTLAND, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ModItems.OUTLAND_ITEM_GROUP)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, name), item);
    }

    public static void registerItems(){
        Main.LOGGER.info("Registering ModItems for " + Main.MOD_ID);
    }

}
