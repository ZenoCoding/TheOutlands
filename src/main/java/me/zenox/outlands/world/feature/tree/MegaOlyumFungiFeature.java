package me.zenox.outlands.world.feature.tree;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import me.zenox.outlands.block.ModBlocks;
import me.zenox.outlands.util.MHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

public class MegaOlyumFungiFeature extends OlyumFungiFeature {
    public MegaOlyumFungiFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    private static final List<Vec3f> ROOT;

    static {
        ROOT = Lists.newArrayList(
                new Vec3f(0.1F, 1F, 0),
                new Vec3f(0.05F, 0.40F, 0),
                new Vec3f(0.1F, 0.2F, 0),
                new Vec3f(0.125F, -0.20F, 0)
        );
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();

        // Random Instance
        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();

        int height = MHelper.randRange(25, 35, random);
        float radius = height;

        if (radius < 1.5F) {
            radius = 1.5F;
        }

        //If the block is in the air, move it down a block (Unless you are below 2 blocks from the bottom of the world)
        while (world.isAir(blockPos) && blockPos.getY() > world.getBottomY() + 2) {
            blockPos = blockPos.down();
        }
        // The block it is generating on must be a valid block
        if (!world.getBlockState(blockPos.down()).isOf(ModBlocks.OLYUM_OLCRIUM)) {
            return false;
        }

        // Generation

        // Lower Cap
        makeCap(world, blockPos.add(0, height / 3, 0), radius / 2, random, ModBlocks.OLYUM_FUNGI_CAP.getDefaultState());
        makeSpokes(world, blockPos.add(0, height / 3 - 2, 0), radius / 2, random, ModBlocks.OLCRIUM.getDefaultState());

        // Trunk
        makeTrunk(world, blockPos.add(0, height / 4, 0), radius, random, ModBlocks.OLYUM_LOG.getDefaultState());

        //Make Higher Cap
        makeCap(world, blockPos.add(0, height / 2.4, 0), radius / 1.5f, random, ModBlocks.OLYUM_FUNGI_CAP.getDefaultState());
        makeSpokes(world, blockPos.add(0, height / 2.4 - 2, 0), radius / 1.5f, random, ModBlocks.OLCRIUM.getDefaultState());

        // At the end of the method, return true to signal it has worked
        return true;
    }
}
