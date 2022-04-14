package net.fabricmc.outlands.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class OutlandIngot extends Item {

    public OutlandIngot(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        playerEntity.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, 1, 1.0f / (RandomUtils.nextFloat() * .4f + .8f));

        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        tooltip.add(new TranslatableText("A mysterious ingot found rarely by defeating the outland minos.").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));

        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslatableText("Drop Chance: 25% from Outland Minos | 1% from Outland Inquisitor").formatted(Formatting.WHITE).formatted(Formatting.ITALIC));
        } else {
            tooltip.add(new TranslatableText("[SHIFT] for more info").formatted(Formatting.DARK_GRAY).formatted(Formatting.ITALIC));
        }

    }
}