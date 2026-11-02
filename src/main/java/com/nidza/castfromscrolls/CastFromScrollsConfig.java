package com.nidza.castfromscrolls;

import net.minecraftforge.common.ForgeConfigSpec;

public final class CastFromScrollsConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue SCROLLS_USE_MANA;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        SCROLLS_USE_MANA = builder
                .comment("If true, casting from a scroll costs mana like casting from a spellbook.")
                .define("scrolls_use_mana", false);
        SPEC = builder.build();
    }

    private CastFromScrollsConfig() {
    }
}
