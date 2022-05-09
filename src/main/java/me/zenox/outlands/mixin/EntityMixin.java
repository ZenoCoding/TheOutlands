package me.zenox.outlands.mixin;

import me.zenox.outlands.EntityDamagedCallback;
import me.zenox.outlands.Main;
import me.zenox.outlands.item.ModItems;
import me.zenox.outlands.particle.ModParticles;
import me.zenox.outlands.util.DamageTimer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(LivingEntity.class)
public abstract class EntityMixin implements DamageTimer {

    @Unique
    private List<Pair<Pair<Float, LivingEntity>, Integer>> outlands$timers = new ArrayList();
    private List<Pair<Pair<Float, LivingEntity>, Integer>> outlands$queueaddtimers = new ArrayList();

    @Inject(at = @At("TAIL"), method = "damage", cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(source.getAttacker() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) source.getAttacker();
            if(attacker.getStackInHand(Hand.MAIN_HAND).getItem().equals(ModItems.OUTLAND_BATTLEAXE)) {
                ActionResult result = EntityDamagedCallback.EVENT.invoker().interact(attacker, (LivingEntity) (Object) this, amount);

                if(result == ActionResult.FAIL) {
                    cir.cancel();
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "tick", cancellable = true)
    private void onTick(CallbackInfo ci) {
        this.outlands$timers.addAll(this.outlands$queueaddtimers);
        this.outlands$queueaddtimers = new ArrayList();
        this.outlands$decrementTimers();
        List<Pair<Pair<Float, LivingEntity>, Integer>> foundTimers = new ArrayList();
        for (Pair<Pair<Float, LivingEntity>, Integer> timer : this.outlands$timers){
            if(timer.getValue() == 0){
                this.damageTrigger(timer.getKey().getValue(), (LivingEntity) (Object) this, timer.getKey().getKey());
                foundTimers.add(timer);
            }
        }

        this.outlands$timers.removeAll(foundTimers);
    }

    private void damageTrigger(LivingEntity attacker, LivingEntity target, Float amount) {
        Random r = new Random();
        Vec3d pos = attacker.getPos();
        if (!attacker.getWorld().isClient) {
            attacker.getWorld().playSoundFromEntity(null, target, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.PLAYERS, 0.5f, 1.7f + (r.nextFloat() * 0.1f - 0.05f));
            attacker.getWorld().playSoundFromEntity(null, target, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 0.5f, 0.6f + (r.nextFloat() * 0.1f - 0.05f));
            for (int i = 0; i < 3; i++) {
                double d = -MathHelper.sin(attacker.getYaw() * ((float) Math.PI / 180));
                double e = MathHelper.cos(attacker.getYaw() * ((float) Math.PI / 180));

                ((ServerWorld) target.getWorld()).spawnParticles(ModParticles.SLASH_SMALL_PARTICLE, pos.x + d, attacker.getBodyY(0.5), pos.z + e, 0, d, 0.0, e, 0.0);
            }

        }
        target.timeUntilRegen = 0;
        target.damage(DamageSource.mob(attacker), amount);
        target.takeKnockback(2, target.getX() - attacker.getX(), target.getZ() - attacker.getZ());
        Main.LOGGER.info("Health After: " + target.getHealth());
    }

    private void outlands$decrementTimers() {
        for (Pair<Pair<Float, LivingEntity>, Integer> timer: this.outlands$timers){
            timer.setValue(timer.getValue()-1);
        }
    }


    @Override
    public List<Pair<Pair<Float, LivingEntity>, Integer>> outlands$getTimers() {
        return outlands$timers;
    }

    @Override
    public void outlands$addTimer(Pair<Pair<Float, LivingEntity>, Integer> timer) {
        this.outlands$queueaddtimers.add(timer);
    }
}

