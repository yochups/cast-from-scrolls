package com.nidza.castfromscrolls.mixin;

import com.nidza.castfromscrolls.CastFromScrollsConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CastSource.class, remap = false)
public abstract class CastSourceMixin {
    @Inject(method = "consumesMana", at = @At("HEAD"), cancellable = true)
    private void castFromScrolls$configureScrollMana(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == CastSource.SCROLL && CastFromScrollsConfig.SCROLLS_USE_MANA.get()) {
            cir.setReturnValue(true);
        }
    }
}
