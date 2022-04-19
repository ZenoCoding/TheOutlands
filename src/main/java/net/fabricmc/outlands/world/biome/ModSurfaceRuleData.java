package net.fabricmc.outlands.world.biome;

import net.fabricmc.outlands.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModSurfaceRuleData {
    private static final MaterialRules.MaterialRule OLCRIUM = makeStateRule(ModBlocks.OLCRIUM);
    private static final MaterialRules.MaterialRule OLYUM_OLCRIUM = makeStateRule(ModBlocks.OLYUM_OLCRIUM);
    private static final MaterialRules.MaterialRule STONE = makeStateRule(Blocks.STONE);

    public static MaterialRules.MaterialRule makeRules()
    {
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);
        MaterialRules.MaterialCondition isAtOrBelowStoneLevel = MaterialRules.stoneDepth(-1, true, VerticalSurfaceType.CEILING);
        MaterialRules.MaterialRule olyumSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel, OLYUM_OLCRIUM), OLCRIUM, MaterialRules.condition(isAtOrBelowStoneLevel, STONE));

        return MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.biome(ModBiomes.OUTLANDS), olyumSurface)
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block)
    {
        return MaterialRules.block(block.getDefaultState());
    }
}
