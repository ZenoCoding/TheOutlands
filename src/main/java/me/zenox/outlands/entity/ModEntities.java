package me.zenox.outlands.entity;

import me.zenox.outlands.Main;
import me.zenox.outlands.entity.custom.Eolifly;
import me.zenox.outlands.entity.custom.OutlandInquisitor;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    public static final EntityType<OutlandInquisitor> OUTLAND_INQUISITOR = registerEntity("outland_inquisitor",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OutlandInquisitor::new)
                    .dimensions(EntityDimensions.fixed(1f, 3.5f)).build());

    public static final EntityType<Eolifly> EOLIFLY = registerEntity("eolifly",
            FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, Eolifly::new)
                    .dimensions(EntityDimensions.fixed(1f, 0.5f)).build());


    private static EntityType registerEntity(String name, EntityType entity) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Main.MOD_ID, name), entity);
    }

    public static void registerEntities() {
        Main.LOGGER.info("Registering ModEntities for " + Main.MOD_ID);

        FabricDefaultAttributeRegistry.register(OUTLAND_INQUISITOR, OutlandInquisitor.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 125)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.1)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 5));

        FabricDefaultAttributeRegistry.register(EOLIFLY, OutlandInquisitor.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 2)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.3));
    }
}
