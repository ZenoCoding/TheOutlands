package me.zenox.outlands.world.feature.tree;


import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import me.zenox.outlands.Main;
import me.zenox.outlands.block.ModBlocks;
import me.zenox.outlands.util.MHelper;
import me.zenox.outlands.util.SplineHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class OlyumFungiFeature extends Feature<DefaultFeatureConfig> {

    public OlyumFungiFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    private static final List<Vec3f> ROOT;
    private static final List<Vec3f> CAP;
    private static final Function<BlockState, Boolean> REPLACE;

    static {
        ROOT = Lists.newArrayList(
                new Vec3f(0.2F, 1F, 0),
                new Vec3f(0.1F, 0.40F, 0),
                new Vec3f(0.2F, 0.2F, 0),
                new Vec3f(0.25F, -0.20F, 0)
        );
        CAP = Lists.newArrayList(
                new Vec3f(0F, 1F, 0),
                new Vec3f(0.5F, 0.85F, 0),
                new Vec3f(1F, 0.75F, 0),
                new Vec3f(0.5F, 0.8F, 0),
                new Vec3f(0.1F, 0.83F, 0)
        );
        SplineHelper.offset(ROOT, new Vec3f(0, -0.45F, 0));

        REPLACE = (state) -> {
            if (state.getMaterial().equals(Material.PLANT) || state.getBlock().equals(ModBlocks.OLYUM_FUNGI_CAP)) {
                return true;
            }
            return state.getMaterial().isReplaceable();
        };
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();



        // Random Instance
        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();

        int height = MHelper.randRange(10, 15, random);
        float radius = height * MHelper.randRange(0.7F, 1.2F, random);

        if(radius < 1.5F){
            radius = 1.5F;
        }

        Main.LOGGER.info("Attempted to Generate Olyum Fungi Structure at " + blockPos.toShortString() + "With Height/Radius of " + height + "/" + radius);

        //If the block is in the air, move it down a block (Unless you are below 2 blocks from the bottom of the world)
        while (world.isAir(blockPos) && blockPos.getY() > world.getBottomY() + 2) {
            blockPos = blockPos.down();
        }
        // The block it is generating on must be a valid block
//        if (!world.getBlockState(blockPos).isOf(ModBlocks.OLYUM_OLCRIUM)) {
//            return false;
//        }

        // Generation

        // Trunk
        makeTrunk(world, blockPos.add(0, 5, 0), radius, random, ModBlocks.OLYUM_LOG.getDefaultState());
        makeCap(world, blockPos.add(0, height/3+5, 0), radius, random, ModBlocks.OLYUM_FUNGI_CAP.getDefaultState());
        makeSpokes(world, blockPos.add(0, height/3+3, 0), radius, random, ModBlocks.OLCRIUM.getDefaultState());

        // Cap (Leaves)

        // At the end of the method, return true to signal it has worked
        return true;
    }

    private void makeTrunk(StructureWorldAccess world, BlockPos pos, float radius, Random random, BlockState wood) {
        int count = (int) (radius*3.5F);
        for (int i = 0; i < count; i++) {
            float angle = (float) i / (float) count * MHelper.PI2;
            float scale = radius * MHelper.randRange(0.85F, 1.15F, random);

            List<Vec3f> branch = SplineHelper.copySpline(ROOT);
            SplineHelper.rotateSpline(branch, angle);
            SplineHelper.scale(branch, scale);
            SplineHelper.fillSpline(branch, world, wood, pos, REPLACE);
        }

    }

    private void makeCap(StructureWorldAccess world, BlockPos pos, float radius, Random random, BlockState material) {

        int count = (int) (radius*3.5F);
        for (int i = 0; i < count; i++) {

            float angle = (float) i / (float) count * MHelper.PI2;
            float scale = radius * MHelper.randRange(0.85F, 1.15F, random);

            List<Vec3f> branch = SplineHelper.copySpline(CAP);
            SplineHelper.rotateSpline(branch, angle);
            SplineHelper.scale(branch, scale);

            SplineHelper.fillSpline(branch, world, material, pos, REPLACE);

        }

    }

    private void makeSpokes(StructureWorldAccess world, BlockPos pos, float radius, Random random, BlockState material) {
        int count = 5;
        for (int i = 0; i < count; i++) {

            float angle = (float) i / (float) count * MHelper.PI2;
            float scale = radius * MHelper.randRange(0.85F, 1.15F, random);

            List<Vec3f> branch = SplineHelper.copySpline(CAP);
            SplineHelper.rotateSpline(branch, angle);
            SplineHelper.scale(branch, scale);


            // If this is the count/5 repetition of loop, fill it instead with the lining material
            SplineHelper.fillSpline(branch, world, material, pos, REPLACE);

        }

    }

}
