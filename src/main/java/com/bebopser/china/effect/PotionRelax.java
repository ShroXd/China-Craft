package com.bebopser.china.effect;

import com.bebopser.china.Reference;
import net.minecraft.potion.Potion;

public class PotionRelax extends Potion {

    public PotionRelax() {
        super(false, 0x000000);
        setPotionName("china.effect.relax");
        setRegistryName(Reference.MODID, "relax");
    }


}
