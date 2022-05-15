package me.zenox.outlands.mixin;

import me.zenox.outlands.util.interfaces.EnchantmentAddon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin implements EnchantmentAddon {

    @Override
    public void outlands$onUserDamaged(LivingEntity user, DamageSource source, int level) {
    }
}

