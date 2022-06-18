package me.zenox.outlands.entity.custom;

import me.zenox.outlands.Main;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EoliflyModel extends AnimatedGeoModel<Eolifly> {

    @Override
    public Identifier getModelResource(Eolifly object) {
        return new Identifier(Main.MOD_ID, "geo/eolifly.geo.json");
    }

    @Override
    public Identifier getTextureResource(Eolifly object) {
        return new Identifier(Main.MOD_ID, "textures/entity/eolifly.png");
    }

    @Override
    public Identifier getAnimationResource(Eolifly animatable) {
        return new Identifier(Main.MOD_ID, "animations/eolifly.animation.json");
    }

}
