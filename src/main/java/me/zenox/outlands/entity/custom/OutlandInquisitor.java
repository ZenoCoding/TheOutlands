package me.zenox.outlands.entity.custom;

import me.zenox.outlands.Main;
import me.zenox.outlands.entity.goal.BeamGoal;
import me.zenox.outlands.entity.interfaces.BeamMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.lang.annotation.Target;

public class OutlandInquisitor extends HostileEntity implements IAnimatable, BeamMob {

    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Boolean> IS_USING_RANGED = DataTracker.registerData(OutlandInquisitor.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> BEAM_TARGET_X = DataTracker.registerData(OutlandInquisitor.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> BEAM_TARGET_Y = DataTracker.registerData(OutlandInquisitor.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> BEAM_TARGET_Z = DataTracker.registerData(OutlandInquisitor.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> ATTACK_STATE = DataTracker.registerData(OutlandInquisitor.class, TrackedDataHandlerRegistry.INTEGER);

    public OutlandInquisitor(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 1000;
    }

    private <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
        if (this.dead || this.getHealth() < 0.01 || this.isDead()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.death", true));
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.dataTracker.get(ATTACK_STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.outland_inquisitor.attack", false));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<OutlandInquisitor> mainController = new AnimationController<>(this, "controller", 0, this::mainPredicate);
        AnimationController<OutlandInquisitor> attackController = new AnimationController<>(this, "attack_controller", 0, this::attackPredicate);

        data.addAnimationController(mainController);
        data.addAnimationController(attackController);
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FixedWanderAroundGoal(this, 1));
        this.goalSelector.add(2, new FixedMeleeAttackGoal(this, 1, false));
        this.goalSelector.add(3, new BeamGoal(this, this.speed, 70, 150, 25));
        this.targetSelector.add(0, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
        this.targetSelector.add(1, new ActiveTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, false));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    protected void updatePostDeath() {
        ++this.deathTime;
        if (this.deathTime == 30) {
            this.remove(Entity.RemovalReason.KILLED);
            this.dropXp();
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_USING_RANGED, false);
        this.dataTracker.startTracking(BEAM_TARGET_X, null);
        this.dataTracker.startTracking(BEAM_TARGET_Y, null);
        this.dataTracker.startTracking(BEAM_TARGET_Z, null);
        this.dataTracker.startTracking(ATTACK_STATE, AttackState.NONE.state());
    }

    private <T> T get(TrackedData<T> trackedData) {
        return this.getDataTracker().get(trackedData);
    }

    private <T> void set(TrackedData<T> trackedData, T data) {
        this.getDataTracker().set(trackedData, data);
    }

    public Vec3d getBeamTargetPos() {
        try {
            return new Vec3d(get(BEAM_TARGET_X), get(BEAM_TARGET_Y), get(BEAM_TARGET_Z));
        } catch (NullPointerException e){
            return null;
        }
    }

    public void setPlayAttack(Integer value) {
        set(ATTACK_STATE, value);
    }

    @Override
    public void initiateBeam(Vec3f targetPos) {
        set(IS_USING_RANGED, true);
        set(BEAM_TARGET_X, targetPos.getX());
        set(BEAM_TARGET_Y, targetPos.getY());
        set(BEAM_TARGET_Z, targetPos.getZ());
    }

    @Override
    public void stopBeam() {
        set(IS_USING_RANGED, false);
        set(BEAM_TARGET_X, null);
        set(BEAM_TARGET_Y, null);
        set(BEAM_TARGET_Z, null);
    }

    @Override
    public boolean isUsingBeam() {
        return get(IS_USING_RANGED);
    }

    public static class FixedMeleeAttackGoal extends MeleeAttackGoal {

        private final OutlandInquisitor mob;
        private int durationCount = 0;

        public FixedMeleeAttackGoal(OutlandInquisitor mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            this.mob = mob;
        }

        @Override
        public void start() {
            super.start();
            this.mob.setPlayAttack(AttackState.NONE.state());
            this.durationCount = 0;

            Main.LOGGER.info("[GOAL] Starting Goal [MELEE].");
        }

        @Override
        public boolean shouldContinue() {
            if (this.durationCount >= 100) return false;
            return super.shouldContinue();
        }

        @Override
        public void stop() {
            super.stop();
            this.mob.setPlayAttack(AttackState.NONE.state());
        }

        @Override
        public void tick() {
            if (this.getCooldown() < this.getMaxCooldown() * (2 / 3) && this.getCooldown() > 0)
                this.mob.setPlayAttack(AttackState.NONE.state());
            this.durationCount++;
            super.tick();
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            super.attack(target, squaredDistance);
            this.mob.setPlayAttack(AttackState.MELEE.state());
        }
    }

    public static class FixedWanderAroundGoal extends WanderAroundGoal {

        private final PathAwareEntity mob;

        public FixedWanderAroundGoal(PathAwareEntity mob, double speed) {
            super(mob, speed);
            this.mob = mob;
        }

        @Override
        public void start() {
            super.start();
            Main.LOGGER.info("[GOAL] Starting Goal [WANDER].");
        }

        @Override
        public boolean canStart() {
            if (this.mob.getTarget() != null) return false;
            return super.canStart();
        }
    }

    private enum AttackState {
        NONE(0),
        MELEE(1),
        RANGED(2),
        SPECIAL(3),
        MELEE_SPECIAL(4),
        RANGED_SPECIAL(5),
        SPECIAL_2(6);

        private final int state;

        AttackState(int state) {
            this.state = state;
        }

        private int state() {
            return state;
        }
    }
}


