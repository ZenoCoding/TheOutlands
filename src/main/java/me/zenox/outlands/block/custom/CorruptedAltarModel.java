package me.zenox.outlands.block.custom;

import me.zenox.outlands.Main;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CorruptedAltarModel extends AnimatedGeoModel<CorruptedAltarBlockEntity> {

    @Override
    public Identifier getModelResource(CorruptedAltarBlockEntity object) {
        return new Identifier(Main.MOD_ID, "geo/corrupted_altar.geo.json");
    }

    @Override
    public Identifier getTextureResource(CorruptedAltarBlockEntity object) {
        return new Identifier(Main.MOD_ID + ":textures/block/corrupted_altar.png");
    }

    @Override
    public Identifier getAnimationResource(CorruptedAltarBlockEntity animatable) {
        return new Identifier(Main.MOD_ID, "animations/corrupted_altar.animation.json");
    }
}
