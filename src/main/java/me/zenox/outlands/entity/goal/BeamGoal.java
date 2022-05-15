package me.zenox.outlands.entity.goal;

import me.zenox.outlands.Main;
import me.zenox.outlands.entity.interfaces.BeamMob;
import me.zenox.outlands.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class BeamGoal
        extends Goal {
    private final MobEntity mob;
    private final BeamMob owner;
    @Nullable
    private LivingEntity target;
    private int cooldownCountdownTicks = 10;
    private int durationCountdownTicks = -1;
    private int blockBreakTimer = -1;
    private final double mobSpeed;
    private final int durationTicks;
    private final int cooldownTicks;
    private final float maxShootRange;
    private final float squaredMaxShootRange;

    public BeamGoal(BeamMob mob, double mobSpeed, int durationTicks, int cooldownTicks, float maxShootRange) {
        if (!(mob instanceof BeamMob)) {
            throw new IllegalArgumentException("BeamGoal requires Mob implements BeamMob");
        }
        this.owner = mob;
        this.mob = (MobEntity) mob;
        this.mobSpeed = mobSpeed;
        this.durationTicks = durationTicks;
        this.cooldownTicks = cooldownTicks;
        this.maxShootRange = maxShootRange;
        this.squaredMaxShootRange = maxShootRange * maxShootRange;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null || !livingEntity.isAlive()) {
            return false;
        }
        this.target = livingEntity;
        return true;
    }

    @Override
    public boolean canStop() {
        return this.durationCountdownTicks < 10;
    }

    @Override
    public boolean shouldContinue() {
        Vec3f targetPos = new Vec3f((float) this.target.getX(), (float) this.target.getY(), (float) this.target.getZ());

        double squaredDistanceTo = this.mob.squaredDistanceTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        boolean shouldStop = squaredDistanceTo > (double) this.squaredMaxShootRange;
        return this.canStart() || !shouldStop;

    }

    @Override
    public void start() {
        super.start();
        Main.LOGGER.info("[GOAL] Starting Goal [BEAM].");
    }

    @Override
    public void stop() {
        this.target = null;
        this.cooldownCountdownTicks = -1;
        this.owner.stopBeam();
    }


    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {

        Vec3f pos = new Vec3f((float) mob.getX(), (float) mob.getEyeY(), (float) mob.getZ());
        Vec3f targetPos = new Vec3f((float) this.target.getX(), (float) this.target.getY(), (float) this.target.getZ());

        double squaredDistanceTo = this.mob.squaredDistanceTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        boolean shouldStop = squaredDistanceTo > (double) this.squaredMaxShootRange;

        this.mob.getNavigation().stop();

        this.mob.getLookControl().lookAt(this.target, 30.0f, 30.0f);

        if (this.cooldownCountdownTicks == -1) {
            this.durationCountdownTicks = this.durationTicks;
        } else if (this.durationCountdownTicks > 0 && !shouldStop) {
            double dx = targetPos.getX() - pos.getX();
            double dy = this.target.getBodyY(0.5) - pos.getY();
            double dz = targetPos.getZ() - pos.getZ();

            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            List<Vec3f> obstaclePosInBeam = new ArrayList<>();
            //Kept track of to prevent duplicates
            List<LivingEntity> entitiesInBeam = new ArrayList<>();

            // Moving the position of targetPos to be at the center of the hitbox of the entity, kept as a copy as to not interfere with the actual targetPos
            Vec3f targetPosCenter = targetPos.copy();
            targetPosCenter.add(0f, (float) (this.target.getBoundingBox().getCenter().getY() - (float) this.target.getY()), 0f);

            // Loops through the line in increments of 0.25 to try and find any entities/blocks in the path of the beam
            for (float i = 0; i < distance; i+=0.25f) {
                Vec3f localPos = pos.copy();
                localPos.add(0f, (float) -(this.mob.getBoundingBox().getCenter().getY() - (float) this.mob.getY()), 0f);
                localPos.lerp(targetPosCenter, i / (float) (distance));
                // If there is a block, stop here
                BlockPos bPos = new BlockPos(localPos.getX(), localPos.getY(), localPos.getZ());
                BlockState block = this.mob.world.getBlockState(bPos);

                Box boundingBox = new Box(localPos.getX()-0.5, localPos.getY()-0.5, localPos.getZ()-0.5, localPos.getX()+0.5, localPos.getY()+0.5, localPos.getZ()+0.5);

                if(!block.isAir() && !block.isOf(Blocks.WATER) && !block.isOf(Blocks.LAVA) && !bPosinList(obstaclePosInBeam, bPos)){
                    targetPosCenter.set(localPos);
                    if(block.getBlock().getBlastResistance() < 1000){
                        if(--this.blockBreakTimer < 0){
                            this.blockBreakTimer = (int) (block.getBlock().getBlastResistance()/10)+1;
                        } else if (this.blockBreakTimer == 0){
                            if(!mob.getWorld().isClient){

                                ((ServerWorld) this.mob.world).spawnParticles(ParticleTypes.EXPLOSION,
                                        localPos.getX(), localPos.getY(), localPos.getZ(),
                                        1, 0.5, 0.5, 0.5, 0.5);
                                mob.world.breakBlock(bPos, true);

                            }
                        }
                        if(!mob.getWorld().isClient) mob.getWorld().playSound(localPos.getX(), localPos.getY(), localPos.getZ(), block.getSoundGroup().getBreakSound(), SoundCategory.BLOCKS, 1f, block.getSoundGroup().getPitch(), true);
                        if(!mob.getWorld().isClient) mob.getWorld().playSound(localPos.getX(), localPos.getY(), localPos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1f, block.getSoundGroup().getPitch(), true);
                        //if(!mob.getWorld().isClient && durationCountdownTicks >= durationTicks-5) mob.getWorld().playSound(localPos.getX(), localPos.getY(), localPos.getZ(), ModSounds.ENERGY_BEAM_FIRE, SoundCategory.HOSTILE, 1f, 1f, true);
                    }
                    obstaclePosInBeam.add(targetPosCenter);
                    break;
                // If there is an entity, damage it, or if it has high health, stop here
                } else {
                    for (Entity entity : this.mob.world.getOtherEntities(this.mob, boundingBox)) {
                        if(entity instanceof LivingEntity && !entitiesInBeam.contains(entity)){
                            if(((LivingEntity) entity).getMaxHealth() > 20){
                                targetPosCenter.set((float) entity.getPos().getX(), (float) (entity.getBoundingBox().getCenter().getY() - (float) entity.getY()), (float) entity.getPos().getZ());
                                break;
                            }
                            entity.damage(new ProjectileDamageSource("beam", this.mob, this.mob), 5);
                            ((LivingEntity) entity).takeKnockback(1, targetPosCenter.getX()-pos.getX(), targetPosCenter.getZ()-pos.getZ());
                            obstaclePosInBeam.add(new Vec3f((float) entity.getPos().getX(), (float) (entity.getBoundingBox().getCenter().getY() - (float) entity.getY()), (float) entity.getPos().getZ()));
                            entitiesInBeam.add((LivingEntity) entity);
                        }
                    }
                }
            }

            Vec3f targetParticlePos = targetPosCenter.copy();

            // Spawning the particles
            for (int i = 0; i < distance; i++) {
                if (!this.mob.world.isClient) {
                    Vec3f particlePos = pos.copy();
                    particlePos.add(0f, (float) -(this.mob.getBoundingBox().getCenter().getY() - (float) this.mob.getY()), 0f);
                    particlePos.lerp(targetParticlePos, i / (float) (distance));
                    ((ServerWorld) this.mob.world).spawnParticles(ParticleTypes.ELECTRIC_SPARK,
                            particlePos.getX(), particlePos.getY(), particlePos.getZ(),
                            1, 0.5, 0.5, 0.5, 0.5);
                    ((ServerWorld) this.mob.world).spawnParticles(ParticleTypes.ENCHANTED_HIT,
                            particlePos.getX(), particlePos.getY(), particlePos.getZ(),
                            1, 0.5, 0.5, 0.5, 0.5);
                }
            }

            for (Vec3f lpos : obstaclePosInBeam) {
                if (!this.mob.world.isClient) {
                    ((ServerWorld) this.mob.world).spawnParticles(ParticleTypes.END_ROD,
                            lpos.getX(), lpos.getY(), lpos.getZ(),
                            1, 1, 1, 1, 1);
                }
            }

            this.owner.initiateBeam(targetPosCenter);
        }

        if (this.durationCountdownTicks == 0) {
            this.cooldownCountdownTicks = cooldownTicks;
        }

        this.durationCountdownTicks--;
        this.cooldownCountdownTicks--;

    }

    private boolean bPosinList(List<Vec3f> list, BlockPos bPos){
        for (Vec3f pos : list) {
            if(bPos.getX() == pos.getX() && bPos.getY() == pos.getY() && bPos.getZ() == pos.getZ()){
                return true;
            }
        }
        return false;
    }
}

