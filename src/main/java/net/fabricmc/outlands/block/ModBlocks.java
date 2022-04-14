package net.fabricmc.outlands.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.outlands.Main;
import net.fabricmc.outlands.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
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

    public static final Block OUTLAND_LOG = registerBlock("outland_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block OUTLAND_WOOD = registerBlock("outland_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block STRIPPED_OUTLAND_LOG = registerBlock("stripped_outland_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block STRIPPED_OUTLAND_WOOD = registerBlock("stripped_outland_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OUTLAND_PLANKS = registerBlock("outland_planks",
            new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);


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
