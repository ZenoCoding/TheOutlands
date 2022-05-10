package me.zenox.outlands.util.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public interface EnchantmentAddon {
    void outlands$onUserDamaged(LivingEntity user, DamageSource source, int level);
}
