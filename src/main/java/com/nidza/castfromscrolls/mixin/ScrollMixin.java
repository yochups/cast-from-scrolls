package com.nidza.castfromscrolls.mixin;

import com.nidza.castfromscrolls.PendingScroll;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.item.Scroll;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Scroll.class, remap = false)
public abstract class ScrollMixin {
    @Inject(method = "attemptRemoveScrollAfterCast", at = @At("HEAD"), cancellable = true)
    private static void castFromScrolls$retainForRecast(ServerPlayer player, CallbackInfo ci) {
        if (PendingScroll.retainIfRecastActive(player)) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "use",
            remap = true,
            at = @At(
                    value = "INVOKE",
                    target = "Lio/redspace/ironsspellbooks/capabilities/magic/SyncedSpellData;isSpellLearned(Lio/redspace/ironsspellbooks/api/spells/AbstractSpell;)Z",
                    remap = false))
    private boolean castFromScrolls$allowUnlearnedScroll(
            SyncedSpellData syncedSpellData,
            AbstractSpell spell) {
        return true;
    }
}
