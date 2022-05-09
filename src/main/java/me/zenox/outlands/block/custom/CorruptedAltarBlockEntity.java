package me.zenox.outlands.block.custom;

import me.zenox.outlands.block.ModBlocks;
import me.zenox.outlands.entity.custom.Eolifly;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CorruptedAltarBlockEntity extends BlockEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public CorruptedAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.CORRUPTED_ALTAR_BLOCK_ENTITY, pos, state);
    }


    private <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
        if (true) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.corrupted_altar.spin", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<CorruptedAltarBlockEntity> mainController = new AnimationController<>(this, "controller", 0, this::mainPredicate);

        animationData.addAnimationController(mainController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
