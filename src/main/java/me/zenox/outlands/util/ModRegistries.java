package me.zenox.outlands.util;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import me.zenox.outlands.EntityDamagedCallback;
import me.zenox.outlands.Main;
import me.zenox.outlands.block.ModBlocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class ModRegistries {
    public static void registerEvents(){
        EntityDamagedCallback.EVENT.register((attacker, target, amount) -> {
            Random r = new Random();
            if(r.nextInt(0, 2) == 1){
                Main.LOGGER.info("BattleAxe Doublehit triggered, damage is: " + amount.toString());
                Vec3d pos = target.getPos();
                if (!attacker.getWorld().isClient) {
                    attacker.getWorld().playSoundFromEntity(null, target, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.PLAYERS, 0.5f, 1.7f+(r.nextFloat()*0.1f-0.05f));
                    attacker.getWorld().playSoundFromEntity(null, target, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 0.5f, 0.6f+(r.nextFloat()*0.1f-0.05f));
                    for (int i = 0; i < 20; i++) {
                        ((ServerWorld)target.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, attacker.getX()+r.nextFloat()-0.5f, attacker.getY()+1, attacker.getZ()+r.nextFloat()-0.5f, 1, pos.x+(r.nextFloat()*2-0.5f), pos.y+(r.nextFloat()*2-0.5f), pos.z+(r.nextFloat()*2-0.5f), 1d);
                    }

                }
                Main.LOGGER.info("Health Before: " + target.getHealth());
                target.damage(DamageSource.mob(attacker), amount);
                Main.LOGGER.info("Health After: " + target.getHealth());
            }

            return ActionResult.PASS;
        });
    }

    public static void registerFlammableBlocks(){
        FlammableBlockRegistry instance = FlammableBlockRegistry.getDefaultInstance();
    }

    public static void registerRenderLayers(){
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.OLYUM_SHRUB);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.OLYUM_FUNGI);
    }
}
