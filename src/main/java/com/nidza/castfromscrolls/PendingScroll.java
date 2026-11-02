package com.nidza.castfromscrolls;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.item.Scroll;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public final class PendingScroll {
    private static final String TAG = CastFromScrolls.MOD_ID + ":pending_recast";

    private PendingScroll() {
    }

    public static boolean retainIfRecastActive(ServerPlayer player) {
        MagicData magicData = MagicData.getPlayerMagicData(player);
        String spellId = magicData.getCastingSpellId();
        boolean active = magicData.getPlayerRecasts().hasRecastForSpell(spellId);
        ItemStack stack = magicData.getPlayerCastingItem();
        if (ScrollCastRules.retainScroll(active) && stack.getItem() instanceof Scroll) {
            stack.getOrCreateTag().putString(TAG, spellId);
            return true;
        }
        return false;
    }

    public static void consumeWhenFinished(ServerPlayer player, RecastInstance recast, RecastResult result) {
        if (recast.getCastSource() != CastSource.SCROLL) {
            return;
        }

        ItemStack stack = find(player, recast.getSpellId());
        if (stack.isEmpty()) {
            return;
        }

        stack.getTag().remove(TAG);
        if (!player.isCreative()) {
            stack.shrink(1);
        }

        MagicData magicData = MagicData.getPlayerMagicData(player);
        if (result == RecastResult.USED_ALL_RECASTS && magicData.getPlayerCastingItem() == stack) {
            magicData.setPlayerCastingItem(ItemStack.EMPTY);
        }
    }

    private static ItemStack find(ServerPlayer player, String spellId) {
        ItemStack castingItem = MagicData.getPlayerMagicData(player).getPlayerCastingItem();
        if (isPending(castingItem, spellId)) {
            return castingItem;
        }

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (isPending(stack, spellId)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean isPending(ItemStack stack, String spellId) {
        return stack.getItem() instanceof Scroll
                && stack.hasTag()
                && spellId.equals(stack.getTag().getString(TAG));
    }
}
