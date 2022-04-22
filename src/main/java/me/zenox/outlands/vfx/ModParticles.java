//package net.fabricmc.test.vfx;
//
//import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
//import net.fabricmc.test.Main;
//import net.minecraft.particle.DefaultParticleType;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.registry.Registry;
//
//public class ModParticles {
//
//    public static final DefaultParticleType SLASH_SMALL = registerParticle("slash_small", FabricParticleTypes.simple());
//
//    private static DefaultParticleType registerParticle(String name, DefaultParticleType particle){
//        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, name), particle);
//    }
//
//    public static void registerParticles(){
//        Main.LOGGER.info("Registering ModParticles for " + Main.MOD_ID);
//    }
//}
