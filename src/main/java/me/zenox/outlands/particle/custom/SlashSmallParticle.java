package me.zenox.outlands.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(value=EnvType.CLIENT)
public class SlashSmallParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteSet;

    protected SlashSmallParticle(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteSet) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        float f;
        this.spriteSet = spriteSet;
        this.velocityMultiplier = 0.6f;
        this.scale = 1.0f - (float)d * 0.5f;
        this.maxAge = 8;
        this.setSpriteForAge(spriteSet);

        this.red = f = this.random.nextFloat() * 0.6f + 0.4f;
        this.green = f;
        this.blue = f;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }
        this.setSpriteForAge(this.spriteSet);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Environment(value= EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteSet;

        public Factory(SpriteProvider spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SlashSmallParticle(clientWorld, d, e, f, g, this.spriteSet);
        }
    }
}
