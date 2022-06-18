package me.zenox.outlands.entity.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;

public class MovingBlockEntity extends FallingBlockEntity {
    public MovingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }
}
