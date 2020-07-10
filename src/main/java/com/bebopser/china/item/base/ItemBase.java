package com.bebopser.china.item.base;

import com.bebopser.china.Reference;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name, int maxStack) {
        super();

        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MODID + "." + name);
        this.setMaxStackSize(maxStack);
        this.setCreativeTab(Reference.tab);
    }
}
