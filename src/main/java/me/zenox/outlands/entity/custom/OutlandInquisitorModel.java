package me.zenox.outlands.entity.custom;

import me.zenox.outlands.Main;
import me.zenox.outlands.util.MHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OutlandInquisitorModel extends AnimatedGeoModel<OutlandInquisitor> {

    @Override
    public void setLivingAnimations(OutlandInquisitor entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        Vec3d pos = entity.getBeamTargetPos();
        if (entity.isUsingBeam() && pos != null) {
            IBone beam = this.getAnimationProcessor().getBone("beam");
            beam.setPositionZ(-3);
            beam.setScaleZ((float) -entity.squaredDistanceTo(pos) * 16);
            float angle = MHelper.angle2d(0, 0, (float) (entity.getX() - pos.getX()), (float) (entity.getZ() - pos.getZ()));
            beam.setRotationZ(angle);
        }
    }

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
