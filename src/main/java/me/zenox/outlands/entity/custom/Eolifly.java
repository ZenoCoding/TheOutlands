package me.zenox.outlands.entity.custom;

import me.zenox.outlands.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Eolifly extends PathAwareEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public Eolifly(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    private <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
        if (this.isAttacking() && !this.isDead()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.eolifly.attack", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.eolifly.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState flapPredicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.eolifly.flap", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<Eolifly> mainController = new AnimationController<>(this, "controller", 0, this::mainPredicate);
        AnimationController<Eolifly> flapController = new AnimationController<>(this, "flap_controller", 0, this::flapPredicate);

        animationData.addAnimationController(mainController);
        animationData.addAnimationController(flapController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void initGoals(){
        this.goalSelector.add(1, new FlyGoal(this, 2));
        this.goalSelector.add(0, new MeleeAttackGoal(this, 1, false));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
    }
}
