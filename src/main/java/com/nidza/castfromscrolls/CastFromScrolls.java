package com.nidza.castfromscrolls;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

@Mod(CastFromScrolls.MOD_ID)
public final class CastFromScrolls {
    public static final String MOD_ID = "cast_from_scrolls";

    public CastFromScrolls() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CastFromScrollsConfig.SPEC);
    }
}
