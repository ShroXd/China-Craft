package com.bebopser.china.loader;

import com.bebopser.china.effect.PotionRelax;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionLoader {
    public static Potion relax = new PotionRelax();

    public PotionLoader(FMLPreInitializationEvent event) {
        ForgeRegistries.POTIONS.register(relax);
    }
}
