package me.jellysquid.mods.sodium.mixin.features.render.immediate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import me.jellysquid.mods.sodium.mixin.core.render.immediate.consumer.OverlayVertexConsumerMixin;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(Direction.class)
public class DirectionMixin {
    /**
     * Benchmarking looking at a ton of glinted/lodestone compasses: time spent in {@link OverlayVertexConsumerMixin#writeVerticesSlow}
     * <ul>
     *     <li>Before optimization: 10.4% (n=2979)</li>
     *     <li>After optimization: 1.5% (n=2409)</li>
     * </ul>
     * Used by:
     * <ul>
     *     <li>{@link OverlayVertexConsumer}</li>
     *     <li>UV-locked faces and cullfaces in {@link JsonUnbakedModel} and {@link BakedQuadFactory}</li>
     *     <li>Raycasts, when creating the {@link BlockHitResult}</li>
     * </ul>
     *
     * @author <a href="mailto:skaggsm333@gmail.com">Mitchell Skaggs</a>
     * @reason Avoid looping over all directions and computing the dot product
     */
    @SuppressWarnings({ "StatementWithEmptyBody", "JavadocReference" })
    @Overwrite
    public static Direction getNearest(float x, float y, float z) {
        // First choice in ties: negative, positive; Y, Z, X
        var yM = Math.abs(y);
        var zM = Math.abs(z);
        var xM = Math.abs(x);

        if (yM >= zM) {
            if (yM >= xM) {
                // Y biggest
                if (y <= 0) {
                    return Direction.DOWN;
                } else /* y > 0 */ {
                    return Direction.UP;
                }
            } else /* zM <= yM < xM */ {
                // X biggest, fall through
            }
        } else /* yM < zM */ {
            if (zM >= xM) {
                // Z biggest
                if (z <= 0) {
                    return Direction.NORTH;
                } else /* z > 0 */ {
                    return Direction.SOUTH;
                }
            } else /* yM < zM < xM */ {
                // X biggest, fall through
            }
        }

        // X biggest
        if (x <= 0) {
            return Direction.WEST;
        } else /* x > 0 */ {
            return Direction.EAST;
        }
    }
}
