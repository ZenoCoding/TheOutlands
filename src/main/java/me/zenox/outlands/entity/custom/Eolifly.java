package me.zenox.outlands.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Eolifly extends PathAwareEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public Eolifly(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    //Bird-Like Navigation
    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {

            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

            @Override
            public void tick() {
                super.tick();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        birdNavigation.setSpeed(3f);
        return birdNavigation;
    }

    // Animation Controllers

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
    public void initGoals() {
        this.goalSelector.add(1, new FlyGoal(this, 2));
        this.goalSelector.add(0, new MeleeAttackGoal(this, 1, false));
        this.targetSelector.add(2, new RevengeGoal(this));
    }
}
