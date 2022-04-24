package me.zenox.outlands.entity.custom;

import me.zenox.outlands.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class OutlandInquisitor extends HostileEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public OutlandInquisitor(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

    private <E extends IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState walkPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.walk", true));
        if(event.isMoving()){
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;

    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if(this.handSwinging && this.handSwingProgress == 0f){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.attack", false));
        }
        return PlayState.CONTINUE;

    }

    private <E extends IAnimatable> PlayState deathPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.death", false));
        if(this.isDead()){
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        //AnimationController<OutlandInquisitor> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        AnimationController<OutlandInquisitor> attackController = new AnimationController<>(this, "attackController", 0, this::attackPredicate);
        AnimationController<OutlandInquisitor> deathController = new AnimationController<>(this, "deathController", 0, this::deathPredicate);
        AnimationController<OutlandInquisitor> walkController = new AnimationController<>(this, "walkController", 0, this::walkPredicate);

        //data.addAnimationController(idleController);
        data.addAnimationController(attackController);
        data.addAnimationController(deathController);
        data.addAnimationController(walkController);
    }

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


