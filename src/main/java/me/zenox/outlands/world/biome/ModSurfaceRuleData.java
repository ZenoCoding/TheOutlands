package me.zenox.outlands.world.biome;

import com.google.common.collect.ImmutableList;
import me.zenox.outlands.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModSurfaceRuleData {
    private static final MaterialRules.MaterialRule OLCRIUM = makeStateRule(ModBlocks.OLCRIUM);
    private static final MaterialRules.MaterialRule OLYUM_OLCRIUM = makeStateRule(ModBlocks.OLYUM_OLCRIUM);
    private static final MaterialRules.MaterialRule AIR = makeStateRule(Blocks.AIR);
    private static final MaterialRules.MaterialRule BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final MaterialRules.MaterialRule WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
    private static final MaterialRules.MaterialRule ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
    private static final MaterialRules.MaterialRule TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
    private static final MaterialRules.MaterialRule RED_SAND = makeStateRule(Blocks.RED_SAND);
    private static final MaterialRules.MaterialRule RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
    private static final MaterialRules.MaterialRule STONE = makeStateRule(Blocks.STONE);
    private static final MaterialRules.MaterialRule DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule PODZOL = makeStateRule(Blocks.PODZOL);
    private static final MaterialRules.MaterialRule COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final MaterialRules.MaterialRule MYCELIUM = makeStateRule(Blocks.MYCELIUM);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule CALCITE = makeStateRule(Blocks.CALCITE);
    private static final MaterialRules.MaterialRule GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final MaterialRules.MaterialRule SAND = makeStateRule(Blocks.SAND);
    private static final MaterialRules.MaterialRule SANDSTONE = makeStateRule(Blocks.SANDSTONE);
    private static final MaterialRules.MaterialRule PACKED_ICE = makeStateRule(Blocks.PACKED_ICE);
    private static final MaterialRules.MaterialRule SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
    private static final MaterialRules.MaterialRule POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
    private static final MaterialRules.MaterialRule ICE = makeStateRule(Blocks.ICE);
    private static final MaterialRules.MaterialRule WATER = makeStateRule(Blocks.WATER);

    private static MaterialRules.MaterialCondition surfaceNoiseAbove(double value) {
        return MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, value / 8.25D, Double.MAX_VALUE);
    }

    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition above97 = MaterialRules.aboveY(YOffset.fixed(97), 2);
        MaterialRules.MaterialCondition above62 = MaterialRules.aboveY(YOffset.fixed(62), 0);
        MaterialRules.MaterialCondition above63_0 = MaterialRules.aboveY(YOffset.fixed(63), 0);
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);
        MaterialRules.MaterialCondition isAboveWaterLevel = MaterialRules.water(0, 0);
        MaterialRules.MaterialCondition isHole = MaterialRules.hole();
        MaterialRules.MaterialCondition isSteep = MaterialRules.steepSlope();
        MaterialRules.MaterialRule grassSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel, OLYUM_OLCRIUM), OLCRIUM);
        MaterialRules.MaterialRule surfaceRules = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.OUTLANDS), grassSurface))), MaterialRules.condition(above62, OLCRIUM));

//        List<MaterialRules.MaterialRule> afterBedrockRules = List.of(SurfaceRuleManager.getDefaultSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD));

        ImmutableList.Builder<MaterialRules.MaterialRule> builder;
//        if (!afterBedrockRules.isEmpty()) {
//            builder = ImmutableList.builder();
//            builder.addAll(afterBedrockRules);
//            builder.add(surfaceRules);
//            surfaceRules = MaterialRules.sequence(builder.build().toArray(MaterialRules.MaterialRule[]::new));
//        }

        builder = ImmutableList.builder();
        // builder.addAll(List.of(SurfaceRuleManager.getDefaultSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD)));

        builder.add(MaterialRules.condition(MaterialRules.verticalGradient("bedrock_floor", YOffset.getBottom(), YOffset.aboveBottom(5)), BEDROCK));

        MaterialRules.MaterialRule surfacerules$rulesource9 = MaterialRules.condition(MaterialRules.surface(), surfaceRules);
        builder.add(surfacerules$rulesource9);
        builder.add(MaterialRules.condition(MaterialRules.verticalGradient("deepslate", YOffset.fixed(0), YOffset.fixed(8)), DEEPSLATE));
        return MaterialRules.sequence(builder.build().toArray(MaterialRules.MaterialRule[]::new));
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
