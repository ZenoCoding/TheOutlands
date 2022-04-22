package me.zenox.outlands.mixin;

import me.zenox.outlands.EntityDamagedCallback;
import me.zenox.outlands.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class DamageMixin {

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
}

