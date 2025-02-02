package me.jellysquid.mods.sodium.mixin.core.model.quad;

import static me.jellysquid.mods.sodium.client.util.ModelQuadUtil.COLOR_INDEX;
import static me.jellysquid.mods.sodium.client.util.ModelQuadUtil.POSITION_INDEX;
import static me.jellysquid.mods.sodium.client.util.ModelQuadUtil.TEXTURE_INDEX;
import static me.jellysquid.mods.sodium.client.util.ModelQuadUtil.vertexOffset;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.jellysquid.mods.sodium.client.model.quad.BakedQuadView;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadFlags;
import me.jellysquid.mods.sodium.client.util.ModelQuadUtil;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

@Mixin(BakedQuad.class)
public abstract class BakedQuadMixin implements BakedQuadView {
    @Shadow
    @Final
    protected int[] vertices;

    @Shadow
    @Final
    protected TextureAtlasSprite sprite;

    @Shadow
    @Final
    protected int tintIndex;

    @Shadow
    @Final
    protected Direction direction; // This is really the light face, but we can't rename it.

    @Shadow
    @Final
    private boolean shade;

    @Unique
    private int flags;

    @Unique
    private int normal;

    @Unique
    private ModelQuadFacing normalFace;

    @Inject(method = "<init>([IILnet/minecraft/core/Direction;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;ZZ)V", at = @At("RETURN"))
    private void init(int[] vertexData, int colorIndex, Direction face, TextureAtlasSprite sprite, boolean shade, boolean hasAmbientOcclusion, CallbackInfo ci) {
        this.normal = ModelQuadUtil.calculateNormal(this);
        this.normalFace = ModelQuadUtil.findNormalFace(this.normal);

        this.flags = ModelQuadFlags.getQuadFlags(this, face);
    }

    @Override
    public float getX(int idx) {
        return Float.intBitsToFloat(this.vertices[vertexOffset(idx) + POSITION_INDEX]);
    }

    @Override
    public float getY(int idx) {
        return Float.intBitsToFloat(this.vertices[vertexOffset(idx) + POSITION_INDEX + 1]);
    }

    @Override
    public float getZ(int idx) {
        return Float.intBitsToFloat(this.vertices[vertexOffset(idx) + POSITION_INDEX + 2]);
    }

    @Override
    public int getColor(int idx) {
        return this.vertices[vertexOffset(idx) + COLOR_INDEX];
    }

    @Override
    public TextureAtlasSprite getSprite() {
        return this.sprite;
    }

    @Override
    public int getNormal() {
        return this.normal;
    }

    @Override
    public float getTexU(int idx) {
        return Float.intBitsToFloat(this.vertices[vertexOffset(idx) + TEXTURE_INDEX]);
    }

    @Override
    public float getTexV(int idx) {
        return Float.intBitsToFloat(this.vertices[vertexOffset(idx) + TEXTURE_INDEX + 1]);
    }

    @Override
    public int getFlags() {
        return this.flags;
    }

    @Override
    public int getColorIndex() {
        return this.tintIndex;
    }

    @Override
    public ModelQuadFacing getNormalFace() {
        return this.normalFace;
    }

    @Override
    public Direction getLightFace() {
        return this.direction;
    }

    @Override
    @Unique(silent = true) // The target class has a function with the same name in a remapped environment
    public boolean hasShade() {
        return this.shade;
    }
}
