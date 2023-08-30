package me.jellysquid.mods.sodium.client.render.chunk;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

public record ChunkRenderMatrices(Matrix4fc projection, Matrix4fc modelView) {
    public static ChunkRenderMatrices from(PoseStack stack) {
        PoseStack.Pose entry = stack.last();
        return new ChunkRenderMatrices(new Matrix4f(RenderSystem.getProjectionMatrix()), new Matrix4f(entry.pose()));
    }
}
