package net.fabricmc.test.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.test.Main;
import net.fabricmc.test.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block OUTLAND_BLOCK = registerBlock("outland_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(20f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OUTLAND_ORE = registerBlock("outland_ore",
            new Block(FabricBlockSettings.of(Material.METAL).strength(10f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);


    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Main.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group){
        return Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks(){
        Main.LOGGER.info("Registering ModBlocks for " + Main.MOD_ID);
    }
}
