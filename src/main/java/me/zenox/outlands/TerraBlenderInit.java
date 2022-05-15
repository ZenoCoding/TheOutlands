package me.zenox.outlands;

import me.zenox.outlands.world.biome.ModRegion;
import me.zenox.outlands.world.biome.ModSurfaceRuleData;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class TerraBlenderInit implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        // Given we only add two biomes, we should keep our weight relatively low.
        Regions.register(new ModRegion(new Identifier(Main.MOD_ID, "overworld"), 2));

        // Register our surface rules
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Main.MOD_ID, ModSurfaceRuleData.makeRules());
    }
}
