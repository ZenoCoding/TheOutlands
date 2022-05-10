package me.zenox.outlands.enchantment;

import me.zenox.outlands.util.interfaces.EnchantmentAddon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.registry.Registry;

public class EnchantHelper {
    public static void outlands$onUserDamaged(LivingEntity user, DamageSource source){
        Consumer consumer = (enchantment, level) -> ((EnchantmentAddon) enchantment).outlands$onUserDamaged(user, source, level);
        if (user != null) {
            forEachEnchantment(consumer, user.getItemsEquipped());
        }
    }

    private static void forEachEnchantment(Consumer consumer, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        NbtList nbtList = stack.getEnchantments();
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            Registry.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(nbtCompound)).ifPresent(enchantment -> consumer.accept((Enchantment)enchantment, EnchantmentHelper.getLevelFromNbt(nbtCompound)));
        }
    }

    private static void forEachEnchantment(Consumer consumer, Iterable<ItemStack> stacks) {
        for (ItemStack itemStack : stacks) {
            forEachEnchantment(consumer, itemStack);
        }
    }

    @FunctionalInterface
    static interface Consumer {
        public void accept(Enchantment var1, int var2);
    }
}
