package net.fabricmc.test.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

import java.util.Random;

public class ModBattleaxeItem extends AxeItem {
    public ModBattleaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Random r = new Random();
        if(r.nextInt(1, 2) == 1){
            target.applyDamageEffects(attacker, );
        }


        return super.postHit(stack, target, attacker);
    }
}
