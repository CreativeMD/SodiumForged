package me.jellysquid.mods.sodium.mixin.features.render.immediate.matrix_stack;

import java.util.ArrayDeque;
import java.util.Deque;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.mojang.blaze3d.vertex.PoseStack;

@Mixin(PoseStack.class)
public abstract class MatrixStackMixin {
    @Shadow
    @Final
    private Deque<PoseStack.Pose> poseStack;

    @Unique
    private final Deque<PoseStack.Pose> cache = new ArrayDeque<>();

    /**
     * @author JellySquid
     * @reason Re-use entries when possible
     */
    @Overwrite
    public void push() {
        var prev = this.poseStack.getLast();

        PoseStack.Pose entry;

        if (!this.cache.isEmpty()) {
            entry = this.cache.removeLast();
            entry.pose().set(prev.pose());
            entry.normal().set(prev.normal());
        } else {
            entry = new PoseStack.Pose(new Matrix4f(prev.pose()), new Matrix3f(prev.normal()));
        }

        this.poseStack.addLast(entry);
    }

    /**
     * @author JellySquid
     * @reason Re-use entries when possible
     */
    @Overwrite
    public void pop() {
        this.cache.addLast(this.poseStack.removeLast());
    }
}
