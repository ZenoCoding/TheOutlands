package me.zenox.outlands.block.custom;

import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CorruptedAltarRenderer extends GeoBlockRenderer<CorruptedAltarBlockEntity> {
    public CorruptedAltarRenderer() {
        super(new CorruptedAltarModel());
    }
}
