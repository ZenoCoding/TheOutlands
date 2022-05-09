package me.zenox.outlands;

import me.zenox.outlands.entity.ModEntities;
import me.zenox.outlands.entity.custom.EoliflyRenderer;
import me.zenox.outlands.entity.custom.OutlandInquisitorRenderer;
import me.zenox.outlands.particle.ModParticles;
import me.zenox.outlands.particle.custom.SlashSmallParticle;
import me.zenox.outlands.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import me.zenox.outlands.block.ModBlocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        // Blocks

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OLYUM_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OLYUM_SAPLING, RenderLayer.getCutout());

        // entities

        EntityRendererRegistry.register(ModEntities.OUTLAND_INQUISITOR, OutlandInquisitorRenderer::new);
        EntityRendererRegistry.register(ModEntities.EOLIFLY, EoliflyRenderer::new);

        ModModelPredicateProvider.registerModels();

        ParticleFactoryRegistry.getInstance().register(ModParticles.SLASH_SMALL_PARTICLE, SlashSmallParticle.Factory::new);

    }
}
