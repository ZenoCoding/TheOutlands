package me.zenox.outlands.util;

import me.zenox.outlands.block.ModBlocks;
import me.zenox.outlands.block.custom.CorruptedAltarRenderer;
import me.zenox.outlands.util.interfaces.DamageTimer;
import me.zenox.outlands.util.interfaces.EntityDamagedCallback;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.util.ActionResult;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Random;

public class ModRegistries {
    public static void registerEvents() {
        EntityDamagedCallback.EVENT.register((attacker, target, amount) -> {
            Random r = new Random();
            if (r.nextInt(0, 2) == 1) {
                ((DamageTimer) target).outlands$addTimer(new MutablePair(new MutablePair(amount, attacker), 5));
            }

            return ActionResult.PASS;
        });
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry instance = FlammableBlockRegistry.getDefaultInstance();
    }

    public static void registerRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.OLYUM_SHRUB);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.OLYUM_SPROUTS);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.OLYUM_FUNGI_CAP);
    }

    public static void registerBlockRenderers() {
        BlockEntityRendererRegistry.register(ModBlocks.CORRUPTED_ALTAR_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new CorruptedAltarRenderer());
    }
}
