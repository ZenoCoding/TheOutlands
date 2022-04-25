package me.zenox.outlands.entity.custom;

import me.zenox.outlands.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
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
    private boolean playAttack = false;

    public OutlandInquisitor(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 1000;
    }

    private <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.playAttack) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.walk", true));
            return PlayState.CONTINUE;
        } else if (this.isDead()) {
            Main.LOGGER.debug("Outland Inquisitor is dead, attempting to play animation.");
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.death", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event){
        if(playAttack) {
            Main.LOGGER.debug("Outland Inquisitor is attacking, attemtping to play animation..");
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.attack", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<OutlandInquisitor> mainController = new AnimationController<>(this, "controller", 0, this::mainPredicate);

        data.addAnimationController(mainController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void initGoals(){
        this.goalSelector.add(1, new WanderAroundGoal(this, 1));
        this.goalSelector.add(0, new FixedMeleeAttackGoal(this, 0.5, false));
        this.targetSelector.add(0, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
        this.targetSelector.add(1, new ActiveTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, false));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
    }

    public void setPlayAttack(Boolean value)
    {
        this.playAttack = true;
    }

    private static class FixedMeleeAttackGoal extends MeleeAttackGoal {

        public FixedMeleeAttackGoal(OutlandInquisitor mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
        }

        @Override
        public void tick() {
            super.tick();
            if(mob.world.isClient) {
                ((OutlandInquisitor) mob).setPlayAttack(true);
            }
        }
    }
}


