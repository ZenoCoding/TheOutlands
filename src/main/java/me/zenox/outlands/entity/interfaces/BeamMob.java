package me.zenox.outlands.entity.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3f;

public interface BeamMob {
    void initiateBeam(Vec3f targetPos);

    void stopBeam();

    boolean isUsingBeam();
}
