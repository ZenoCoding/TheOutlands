package me.zenox.outlands.enchantment;

import me.zenox.outlands.Main;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchantments {

    public static Enchantment CRUSHING = register("crushing",
            new CrushingEnchantment(Enchantment.Rarity.VERY_RARE,
                    EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(Main.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments(){
        Main.LOGGER.info("Registering Enchantments for " + Main.MOD_ID);
    }
}
