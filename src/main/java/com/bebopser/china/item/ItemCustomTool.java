package com.bebopser.china.item;

import com.bebopser.china.Reference;
import com.bebopser.china.item.base.ItemBase;

public class ItemCustomTool extends ItemBase {

    public ItemCustomTool(String name, int maxStack, int maxDamage) {
        super(name, maxStack);
        this.setMaxDamage(maxDamage);
        this.setCreativeTab(Reference.tab);
    }
}
