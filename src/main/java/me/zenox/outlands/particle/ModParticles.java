package me.zenox.outlands.particle;

import me.zenox.outlands.Main;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType SLASH_SMALL_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "slash_small_particle"),
                SLASH_SMALL_PARTICLE);
    }
}
