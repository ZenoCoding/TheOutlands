package net.fabricmc.outlands;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.outlands.block.ModBlocks;
import net.fabricmc.outlands.entity.custom.OutlandInquisitorRenderer;
import net.fabricmc.outlands.entity.ModEntities;
import net.fabricmc.outlands.util.ModModelPredicateProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModClient implements ClientModInitializer {

    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier("entitytesting", "cube"), "main");

    @Override
    public void onInitializeClient() {
//        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
//            registry.register(new Identifier(Main.MOD_ID, "particle/slash_small"));
//        }));
//
//        ParticleFactoryRegistry.getInstance().register(ModParticles.SLASH_SMALL, SweepAttackParticle.Factory::new);

        // Blocks

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OLYUM_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OLYUM_SAPLING, RenderLayer.getCutout());

        // entities

        EntityRendererRegistry.register(ModEntities.OUTLAND_INQUISITOR, OutlandInquisitorRenderer::new);

        ModModelPredicateProvider.registerModels();

    }
}
