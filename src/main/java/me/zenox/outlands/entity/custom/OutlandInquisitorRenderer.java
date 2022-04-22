package me.zenox.outlands.entity.custom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OutlandInquisitorRenderer extends GeoEntityRenderer<OutlandInquisitor> {

    public OutlandInquisitorRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new OutlandInquisitorModel());
    }
}
