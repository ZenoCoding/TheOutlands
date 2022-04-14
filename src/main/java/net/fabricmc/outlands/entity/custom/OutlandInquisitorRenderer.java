package net.fabricmc.outlands.entity.custom;

import net.fabricmc.outlands.ModClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OutlandInquisitorRenderer extends GeoEntityRenderer<OutlandInquisitor> {

    public OutlandInquisitorRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OutlandInquisitorModel());
    }
}
