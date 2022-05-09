package me.zenox.outlands.util;

import net.minecraft.entity.LivingEntity;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface DamageTimer {
    List<Pair<Pair<Float, LivingEntity>, Integer>> outlands$getTimers();

    void outlands$addTimer(Pair<Pair<Float, LivingEntity>, Integer> timers);

}
