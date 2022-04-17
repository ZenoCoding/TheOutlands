package net.fabricmc.outlands.entity.custom;

import net.fabricmc.outlands.sound.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class OutlandInquisitor extends HostileEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public OutlandInquisitor(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.isAttacking()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.attack", false));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<OutlandInquisitor> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);

    }

//    @Override
//    public void onDeath(DamageSource source){
//        super.onDeath(source);
//    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void initGoals(){
        this.goalSelector.add(1, new WanderAroundGoal(this, 1));
        this.goalSelector.add(0, new AttackGoal(this));
        this.targetSelector.add(0, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
    }
}


