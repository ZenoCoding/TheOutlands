package me.zenox.outlands.util;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class SplineHelper {
    public static List<Vec3f> makeSpline(float x1, float y1, float z1, float x2, float y2, float z2, int points) {
        List<Vec3f> spline = Lists.newArrayList();
        spline.add(new Vec3f(x1, y1, z1));
        int count = points - 1;
        for (int i = 1; i < count; i++) {
            float delta = (float) i / (float) count;
            float x = MathHelper.lerp(delta, x1, x2);
            float y = MathHelper.lerp(delta, y1, y2);
            float z = MathHelper.lerp(delta, z1, z2);
            spline.add(new Vec3f(x, y, z));
        }
        spline.add(new Vec3f(x2, y2, z2));
        return spline;
    }

    public static List<Vec3f> smoothSpline(List<Vec3f> spline, int segmentPoints) {
        List<Vec3f> result = Lists.newArrayList();
        Vec3f start = spline.get(0);
        for (int i = 1; i < spline.size(); i++) {
            Vec3f end = spline.get(i);
            for (int j = 0; j < segmentPoints; j++) {
                float delta = (float) j / segmentPoints;
                delta = 0.5F - 0.5F * MathHelper.cos(delta * 3.14159F);
                result.add(lerp(start, end, delta));
            }
            start = end;
        }
        result.add(start);
        return result;
    }

    private static Vec3f lerp(Vec3f start, Vec3f end, float delta) {
        float x = MathHelper.lerp(delta, start.getX(), end.getX());
        float y = MathHelper.lerp(delta, start.getY(), end.getY());
        float z = MathHelper.lerp(delta, start.getZ(), end.getZ());
        return new Vec3f(x, y, z);
    }

    public static void offsetParts(List<Vec3f> spline, Random random, float dx, float dy, float dz) {
        int count = spline.size();
        for (int i = 1; i < count; i++) {
            Vec3f pos = spline.get(i);
            float x = pos.getX() + (float) random.nextGaussian() * dx;
            float y = pos.getY() + (float) random.nextGaussian() * dy;
            float z = pos.getZ() + (float) random.nextGaussian() * dz;
            pos.set(x, y, z);
        }
    }

    public static void powerOffset(List<Vec3f> spline, float distance, float power) {
        int count = spline.size();
        float max = count + 1;
        for (int i = 1; i < count; i++) {
            Vec3f pos = spline.get(i);
            float x = (float) i / max;
            float y = pos.getY() + (float) Math.pow(x, power) * distance;
            pos.set(pos.getX(), y, pos.getZ());
        }
    }

//    public static SDF buildSDF(List<Vec3f> spline, float radius1, float radius2, Function<BlockPos, BlockState> placerFunction) {
//        int count = spline.size();
//        float max = count - 2;
//        SDF result = null;
//        Vec3f start = spline.get(0);
//        for (int i = 1; i < count; i++) {
//            Vec3f pos = spline.get(i);
//            float delta = (float) (i - 1) / max;
//            SDF line = new SDFLine().setRadius(MathHelper.lerp(delta, radius1, radius2))
//                    .setStart(start.getX(), start.getY(), start.getZ())
//                    .setEnd(pos.getX(), pos.getY(), pos.getZ())
//                    .setBlockState(placerFunction);
//            result = result == null ? line : new SDFUnion().setSourceA(result).setSourceB(line);
//            start = pos;
//        }
//        return result;
//    }
//
//    public static SDF buildSDF(List<Vec3f> spline, Function<Float, Float> radiusFunction, Function<BlockPos, BlockState> placerFunction) {
//        int count = spline.size();
//        float max = count - 2;
//        SDF result = null;
//        Vec3f start = spline.get(0);
//        for (int i = 1; i < count; i++) {
//            Vec3f pos = spline.get(i);
//            float delta = (float) (i - 1) / max;
//            SDF line = new SDFLine().setRadius(radiusFunction.apply(delta))
//                    .setStart(start.getX(), start.getY(), start.getZ())
//                    .setEnd(pos.getX(), pos.getY(), pos.getZ())
//                    .setBlockState(placerFunction);
//            result = result == null ? line : new SDFUnion().setSourceA(result).setSourceB(line);
//            start = pos;
//        }
//        return result;
//    }

    public static boolean fillSpline(List<Vec3f> spline, StructureWorldAccess world, BlockState state, BlockPos pos, Function<BlockState, Boolean> replace) {
        Vec3f startPos = spline.get(0);
        for (int i = 1; i < spline.size(); i++) {
            Vec3f endPos = spline.get(i);
            if (!(fillLine(startPos, endPos, world, state, pos, replace))) {
                return false;
            }
            startPos = endPos;
        }

        return true;
    }

    public static void fillSplineForce(List<Vec3f> spline, StructureWorldAccess world, BlockState state, BlockPos pos, Function<BlockState, Boolean> replace) {
        Vec3f startPos = spline.get(0);
        for (int i = 1; i < spline.size(); i++) {
            Vec3f endPos = spline.get(i);
            fillLineForce(startPos, endPos, world, state, pos, replace);
            startPos = endPos;
        }
    }

    public static boolean fillLine(Vec3f start, Vec3f end, StructureWorldAccess world, BlockState state, BlockPos pos, Function<BlockState, Boolean> replace) {
        float dx = end.getX() - start.getX();
        float dy = end.getY() - start.getY();
        float dz = end.getZ() - start.getZ();
        float max = MHelper.max(Math.abs(dx), Math.abs(dy), Math.abs(dz));
        int count = MHelper.floor(max + 1);
        dx /= max;
        dy /= max;
        dz /= max;
        float x = start.getX();
        float y = start.getY();
        float z = start.getZ();
        boolean down = Math.abs(dy) > 0.2;

        BlockState bState;
        BlockPos.Mutable bPos = new BlockPos.Mutable();
        for (int i = 0; i < count; i++) {
            bPos.set(x + pos.getX(), y + pos.getY(), z + pos.getZ());
            bState = world.getBlockState(bPos);
            if (bState.equals(state) || replace.apply(bState)) {
                world.setBlockState(bPos, state, 1 | 16 | 2);
                bPos.setY(bPos.getY() - 1);
                bState = world.getBlockState(bPos);
                if (down && bState.equals(state) || replace.apply(bState)) {
                    world.setBlockState(bPos, state, 1 | 16 | 2);
                }
            } else {
                return false;
            }
            x += dx;
            y += dy;
            z += dz;
        }
        bPos.set(end.getX() + pos.getX(), end.getY() + pos.getY(), end.getZ() + pos.getZ());
        bState = world.getBlockState(bPos);
        if (bState.equals(state) || replace.apply(bState)) {
            world.setBlockState(bPos, state, 1 | 16 | 2);
            bPos.setY(bPos.getY() - 1);
            bState = world.getBlockState(bPos);
            if (down && bState.equals(state) || replace.apply(bState)) {
                world.setBlockState(bPos, state, 1 | 16 | 2);
            }
            return true;
        } else {
            return false;
        }
    }

    public static void fillLineForce(Vec3f start, Vec3f end, StructureWorldAccess world, BlockState state, BlockPos pos, Function<BlockState, Boolean> replace) {
        float dx = end.getX() - start.getX();
        float dy = end.getY() - start.getY();
        float dz = end.getZ() - start.getZ();
        float max = MHelper.max(Math.abs(dx), Math.abs(dy), Math.abs(dz));
        int count = MHelper.floor(max + 1);
        dx /= max;
        dy /= max;
        dz /= max;
        float x = start.getX();
        float y = start.getY();
        float z = start.getZ();
        boolean down = Math.abs(dy) > 0.2;

        BlockState bState;
        BlockPos.Mutable bPos = new BlockPos.Mutable();
        for (int i = 0; i < count; i++) {
            bPos.set(x + pos.getX(), y + pos.getY(), z + pos.getZ());
            bState = world.getBlockState(bPos);
            if (replace.apply(bState)) {
                world.setBlockState(bPos, state, 1 | 16 | 2);
                bPos.setY(bPos.getY() - 1);
                bState = world.getBlockState(bPos);
                if (down && replace.apply(bState)) {
                    world.setBlockState(bPos, state, 1 | 16 | 2);
                }
            }
            x += dx;
            y += dy;
            z += dz;
        }
        bPos.set(end.getX() + pos.getX(), end.getY() + pos.getY(), end.getZ() + pos.getZ());
        bState = world.getBlockState(bPos);
        if (replace.apply(bState)) {
            world.setBlockState(bPos, state, 1 | 16 | 2);
            bPos.setY(bPos.getY() - 1);
            bState = world.getBlockState(bPos);
            if (down && replace.apply(bState)) {
                world.setBlockState(bPos, state, 1 | 16 | 2);
            }
        }
    }

    public static boolean canGenerate(List<Vec3f> spline, float scale, BlockPos start, StructureWorldAccess world, Function<BlockState, Boolean> canReplace) {
        int count = spline.size();
        Vec3f vec = spline.get(0);
        BlockPos.Mutable mut = new BlockPos.Mutable();
        float x1 = start.getX() + vec.getX() * scale;
        float y1 = start.getY() + vec.getY() * scale;
        float z1 = start.getZ() + vec.getZ() * scale;
        for (int i = 1; i < count; i++) {
            vec = spline.get(i);
            float x2 = start.getX() + vec.getX() * scale;
            float y2 = start.getY() + vec.getY() * scale;
            float z2 = start.getZ() + vec.getZ() * scale;

            for (float py = y1; py < y2; py += 3) {
                if (py - start.getY() < 10) continue;
                float lerp = (py - y1) / (y2 - y1);
                float x = MathHelper.lerp(lerp, x1, x2);
                float z = MathHelper.lerp(lerp, z1, z2);
                mut.set(x, py, z);
                if (!canReplace.apply(world.getBlockState(mut))) {
                    return false;
                }
            }

            x1 = x2;
            y1 = y2;
            z1 = z2;
        }
        return true;
    }

    public static boolean canGenerate(List<Vec3f> spline, BlockPos start, StructureWorldAccess world, Function<BlockState, Boolean> canReplace) {
        int count = spline.size();
        Vec3f vec = spline.get(0);
        BlockPos.Mutable mut = new BlockPos.Mutable();
        float x1 = start.getX() + vec.getX();
        float y1 = start.getY() + vec.getY();
        float z1 = start.getZ() + vec.getZ();
        for (int i = 1; i < count; i++) {
            vec = spline.get(i);
            float x2 = start.getX() + vec.getX();
            float y2 = start.getY() + vec.getY();
            float z2 = start.getZ() + vec.getZ();

            for (float py = y1; py < y2; py += 3) {
                if (py - start.getY() < 10) continue;
                float lerp = (py - y1) / (y2 - y1);
                float x = MathHelper.lerp(lerp, x1, x2);
                float z = MathHelper.lerp(lerp, z1, z2);
                mut.set(x, py, z);
                if (!canReplace.apply(world.getBlockState(mut))) {
                    return false;
                }
            }

            x1 = x2;
            y1 = y2;
            z1 = z2;
        }
        return true;
    }

    public static Vec3f getPos(List<Vec3f> spline, float index) {
        int i = (int) index;
        int last = spline.size() - 1;
        if (i >= last) {
            return spline.get(last);
        }
        float delta = index - i;
        Vec3f p1 = spline.get(i);
        Vec3f p2 = spline.get(i + 1);
        float x = MathHelper.lerp(delta, p1.getX(), p2.getX());
        float y = MathHelper.lerp(delta, p1.getY(), p2.getY());
        float z = MathHelper.lerp(delta, p1.getZ(), p2.getZ());
        return new Vec3f(x, y, z);
    }

    public static void rotateSpline(List<Vec3f> spline, float angle) {
        for (Vec3f v : spline) {
            float sin = (float) Math.sin(angle);
            float cos = (float) Math.cos(angle);
            float x = v.getX() * cos + v.getZ() * sin;
            float z = v.getX() * sin + v.getZ() * cos;
            v.set(x, v.getY(), z);
        }
    }

    public static List<Vec3f> copySpline(List<Vec3f> spline) {
        List<Vec3f> result = new ArrayList<Vec3f>(spline.size());
        for (Vec3f v : spline) {
            result.add(new Vec3f(v.getX(), v.getY(), v.getZ()));
        }
        return result;
    }

    public static void scale(List<Vec3f> spline, float scale) {
        scale(spline, scale, scale, scale);
    }

    public static void scale(List<Vec3f> spline, float x, float y, float z) {
        for (Vec3f v : spline) {
            v.set(v.getX() * x, v.getY() * y, v.getZ() * z);
        }
    }

    public static void offset(List<Vec3f> spline, Vec3f offset) {
        for (Vec3f v : spline) {
            v.set(offset.getX() + v.getX(), offset.getY() + v.getY(), offset.getZ() + v.getZ());
        }
    }
}
