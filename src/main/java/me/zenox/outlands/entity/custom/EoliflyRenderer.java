package me.zenox.outlands.entity.custom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EoliflyRenderer extends GeoEntityRenderer<Eolifly> {

    public EoliflyRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EoliflyModel());
    }
}
