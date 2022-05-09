package me.zenox.outlands.block;

import me.zenox.outlands.Main;
import me.zenox.outlands.block.custom.*;
import me.zenox.outlands.world.feature.ModConfiguredFeatures;
import me.zenox.outlands.world.feature.tree.OutlandSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import me.zenox.outlands.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
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

    public static final Block OLYUM_LOG = registerBlock("olyum_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block OLYUM_WOOD = registerBlock("olyum_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block STRIPPED_OLYUM_LOG = registerBlock("stripped_olyum_log",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);
    public static final Block STRIPPED_OLYUM_WOOD = registerBlock("stripped_olyum_wood",
            new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD).strength(6f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_PLANKS = registerBlock("olyum_planks",
            new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).strength(4f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_LEAVES = registerBlock("olyum_leaves",
            new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).nonOpaque()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_SAPLING = registerBlock("olyum_sapling",
            new OlyumSaplingBlock(new OutlandSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING)), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_SHRUB = registerBlock("olyum_shrub",
            new OlyumPlantBlock(FabricBlockSettings.copy(Blocks.GRASS)), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_SPROUTS = registerBlock("olyum_sprouts",
            new OlyumFungusBlock(FabricBlockSettings.copy(Blocks.WARPED_FUNGUS), () -> ModConfiguredFeatures.OLYUM_FUNGI, () -> ModConfiguredFeatures.PATCH_OLYUM_SHRUB),
            ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLCRIUM = registerBlock("olcrium",
            new Block(FabricBlockSettings.copy(Blocks.DIRT).strength(1f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_OLCRIUM = registerBlock("olyum_olcrium",
            new OlcriumBlock(FabricBlockSettings.copy(Blocks.GRASS_BLOCK).strength(1f).requiresTool()), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block OLYUM_FUNGI_CAP = registerBlock("olyum_fungi_cap",
            new JellyBlock(FabricBlockSettings.copy(Blocks.SLIME_BLOCK)), ModItems.OUTLAND_ITEM_GROUP);

    public static final Block CORRUPTED_ALTAR = registerBlock("corrupted_altar",
            new CorruptedAltarBlock(FabricBlockSettings.of(Material.METAL).strength(50f)), ModItems.OUTLAND_ITEM_GROUP);

    // Block Entities

    public static final BlockEntityType<CorruptedAltarBlockEntity> CORRUPTED_ALTAR_BLOCK_ENTITY = registerBlockEntity("corrupted_altar_entity", FabricBlockEntityTypeBuilder.create(CorruptedAltarBlockEntity::new, CORRUPTED_ALTAR).build(null));


    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Main.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group){
        return Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    private static BlockEntityType registerBlockEntity(String name, BlockEntityType<?> type){
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MOD_ID, name), type);
    }

    public static void registerModBlocks(){
        Main.LOGGER.info("Registering ModBlocks for " + Main.MOD_ID);
    }
}
