package me.zenox.outlands;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface EntityDamagedCallback {
    Event<EntityDamagedCallback> EVENT = EventFactory.createArrayBacked(EntityDamagedCallback.class,
            (listeners) -> (attacker, target, damage) -> {
                for (EntityDamagedCallback listener : listeners) {
                    ActionResult result = listener.interact(attacker, target, damage);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(LivingEntity attacker, LivingEntity target, Float damage);
}
