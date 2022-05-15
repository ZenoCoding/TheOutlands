package me.zenox.outlands.enchantment;

import me.zenox.outlands.util.interfaces.EnchantmentAddon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;

public class CrushingEnchantment extends Enchantment implements EnchantmentAddon {
    public CrushingEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void outlands$onUserDamaged(LivingEntity user, DamageSource source, int level) {
        if (!user.world.isClient()) {
            if (shouldTrigger(source.isFromFalling(), user instanceof PlayerEntity, user.isSneaking())) {
                ServerWorld world = (ServerWorld) user.world;
                Random r = new Random();
                for (int i = 0; i < 250; i++) {
                    float s = r.nextFloat() * 360;
                    float t = r.nextFloat() * 360;
                    float radius = level * 2;
                    world.spawnParticles(ParticleTypes.SMOKE, user.getX(), user.getY(), user.getZ(), 1, radius * Math.cos(s) * Math.sin(t), r.nextFloat(), radius * Math.cos(t), (radius + r.nextFloat()) / 4);
                }
                List<Entity> nearbyentities = world.getOtherEntities(user, Box.of(new Vec3d(user.getX(), user.getY() + level / 3, user.getZ()), level * 2, level * 3, level * 2));
                for (Entity entity : nearbyentities) {
                    if (entity instanceof LivingEntity) {
                        entity.damage(DamageSource.mob(user), level * 2);
                        ((LivingEntity) entity).takeKnockback(level, entity.getX() - user.getX(), entity.getZ() - user.getZ());
                    }
                }
            }
        }
    }

    private boolean shouldTrigger(Boolean falling, Boolean isplayer, Boolean issneaking) {
        if (falling) {
            if (!isplayer) {
                return true;
            } else return issneaking;
        }
        return false;
    }
}
