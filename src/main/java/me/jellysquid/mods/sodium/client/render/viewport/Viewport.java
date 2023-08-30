package me.jellysquid.mods.sodium.client.render.viewport;

import org.joml.Vector3d;

import me.jellysquid.mods.sodium.client.render.viewport.frustum.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public final class Viewport {
    private final Frustum frustum;
    private final CameraTransform transform;

    private final SectionPos chunkCoords;
    private final BlockPos blockCoords;

    public Viewport(Frustum frustum, Vector3d position) {
        this.frustum = frustum;
        this.transform = new CameraTransform(position.x, position.y, position.z);

        this.chunkCoords = SectionPos.of(SectionPos.blockToSectionCoord(position.x), SectionPos.blockToSectionCoord(position.y),
                SectionPos.blockToSectionCoord(position.z));

        this.blockCoords = BlockPos.containing(position.x, position.y, position.z);
    }

    public boolean isBoxVisible(int intX, int intY, int intZ, float radius) {
        float floatX = (intX - this.transform.intX) - this.transform.fracX;
        float floatY = (intY - this.transform.intY) - this.transform.fracY;
        float floatZ = (intZ - this.transform.intZ) - this.transform.fracZ;

        return this.frustum.testAab(floatX - radius, floatY - radius, floatZ - radius,

                floatX + radius, floatY + radius, floatZ + radius);
    }

    public CameraTransform getTransform() {
        return this.transform;
    }

    public SectionPos getChunkCoord() {
        return this.chunkCoords;
    }

    public BlockPos getBlockCoord() {
        return this.blockCoords;
    }
}
