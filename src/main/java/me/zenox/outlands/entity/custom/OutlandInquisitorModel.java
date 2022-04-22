package me.zenox.outlands.entity.custom;

import me.zenox.outlands.Main;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OutlandInquisitorModel extends AnimatedGeoModel<OutlandInquisitor> {

    @Override
    public Identifier getModelLocation(OutlandInquisitor object) {
        return new Identifier(Main.MOD_ID, "geo/outland_inquisitor.geo.json");
    }

    @Override
    public Identifier getTextureLocation(OutlandInquisitor object) {
        return new Identifier(Main.MOD_ID, "textures/entity/outland_inquisitor.png");
    }

    @Override
    public Identifier getAnimationFileLocation(OutlandInquisitor animatable) {
        return new Identifier(Main.MOD_ID, "animations/outland_inquisitor.animation.json");
    }

}
