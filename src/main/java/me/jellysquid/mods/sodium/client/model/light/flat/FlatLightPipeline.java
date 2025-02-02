package me.jellysquid.mods.sodium.client.model.light.flat;

import static me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess.getEmissiveLightmap;
import static me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess.unpackBL;
import static me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess.unpackEM;
import static me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess.unpackFC;
import static me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess.unpackLU;
import static me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess.unpackSL;

import java.util.Arrays;

import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import me.jellysquid.mods.sodium.client.model.light.data.QuadLightData;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadFlags;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A light pipeline which implements "classic-style" lighting through simply using the light value of the adjacent
 * block to a face.
 */
public class FlatLightPipeline implements LightPipeline {
    /**
     * The cache which light data will be accessed from.
     */
    private final LightDataAccess lightCache;

    public FlatLightPipeline(LightDataAccess lightCache) {
        this.lightCache = lightCache;
    }

    @Override
    public void calculate(ModelQuadView quad, BlockPos pos, QuadLightData out, Direction cullFace, Direction lightFace, boolean shade) {
        int lightmap;

        // To match vanilla behavior, use the cull face if it exists/is available
        if (cullFace != null) {
            lightmap = getOffsetLightmap(pos, cullFace);
        } else {
            int flags = quad.getFlags();
            // If the face is aligned, use the light data above it
            // To match vanilla behavior, also treat the face as aligned if it is parallel and the block state is a full cube
            if ((flags & ModelQuadFlags.IS_ALIGNED) != 0 || ((flags & ModelQuadFlags.IS_PARALLEL) != 0 && unpackFC(this.lightCache.get(pos)))) {
                lightmap = getOffsetLightmap(pos, lightFace);
            } else {
                lightmap = getEmissiveLightmap(this.lightCache.get(pos));
            }
        }

        Arrays.fill(out.lm, lightmap);
        Arrays.fill(out.br, this.lightCache.getWorld().getShade(lightFace, shade));
    }

    /**
     * When vanilla computes an offset lightmap with flat lighting, it passes the original BlockState but the
     * offset BlockPos to {@link WorldRenderer#getLightmapCoordinates(BlockRenderView, BlockState, BlockPos)}.
     * This does not make much sense but fixes certain issues, primarily dark quads on light-emitting blocks
     * behind tinted glass. {@link LightDataAccess} cannot efficiently store lightmaps computed with
     * inconsistent values so this method exists to mirror vanilla behavior as closely as possible.
     */
    private int getOffsetLightmap(BlockPos pos, Direction face) {
        int word = this.lightCache.get(pos);

        // Check emissivity of the origin state
        if (unpackEM(word)) {
            return LightTexture.FULL_BRIGHT;
        }

        // Use world light values from the offset pos, but luminance from the origin pos
        int adjWord = this.lightCache.get(pos, face);
        return LightTexture.pack(Math.max(unpackBL(adjWord), unpackLU(word)), unpackSL(adjWord));
    }
}
