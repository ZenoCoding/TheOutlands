package net.fabricmc.outlands.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.outlands.Main;
import net.fabricmc.outlands.block.custom.ModSaplingBlock;
import net.fabricmc.outlands.item.ModItems;
import net.fabricmc.outlands.world.feature.tree.OutlandSaplingGenerator;
import net.minecraft.block.*;
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

    public static final Block OUTLAND_LOG = registerBlock("olyum_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block OUTLAND_WOOD = registerBlock("olyum_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block STRIPPED_OLYUM_LOG = registerBlock("stripped_olyum_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block STRIPPED_OLYUM_WOOD = registerBlock("stripped_olyum_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_PLANKS = registerBlock("outland_planks",
            new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_LEAVES = registerBlock("olyum_leaves",
            new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).nonOpaque()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_SAPLING = registerBlock("olyum_sapling",
            new ModSaplingBlock(new OutlandSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING)), ModItems.OUTLAND_ITEM_GROUP);


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
