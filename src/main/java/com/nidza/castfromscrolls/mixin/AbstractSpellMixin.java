package com.nidza.castfromscrolls.mixin;

import com.nidza.castfromscrolls.ScrollCastRules;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AbstractSpell.class, remap = false)
public abstract class AbstractSpellMixin {
    @Redirect(
            method = "canBeCastedBy",
            at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/spells/AbstractSpell;requiresLearning()Z"),
            require = 0)
    private boolean castFromScrolls$allowUnlearnedSpell(
            AbstractSpell spell,
            int spellLevel,
            CastSource castSource,
            MagicData magicData,
            Player player) {
        return ScrollCastRules.learningRequired(castSource == CastSource.SCROLL, spell.requiresLearning());
    }

    @Redirect(
            method = "canBeCastedBy",
            at = @At(
                    value = "INVOKE",
                    target = "Lio/redspace/ironsspellbooks/api/spells/AbstractSpell;getRecastCount(ILnet/minecraft/world/entity/LivingEntity;)I"))
    private int castFromScrolls$allowRecastSpell(
            AbstractSpell spell,
            int level,
            LivingEntity entity,
            int spellLevel,
            CastSource castSource,
            MagicData magicData,
            Player player) {
        int original = spell.getRecastCount(level, entity);
        return ScrollCastRules.recastCount(castSource == CastSource.SCROLL, original);
    }
}
