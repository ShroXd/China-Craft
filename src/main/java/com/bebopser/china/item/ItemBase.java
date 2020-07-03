package com.bebopser.china.item;

import com.bebopser.china.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBase extends Item {
    protected String[] subNames;

    public ItemBase(String name, int stackSize, String... subNames) {
        this.setUnlocalizedName(Reference.MODID + "." + name);
        this.setMaxStackSize(stackSize);
        this.setHasSubtypes(checkSubnames());
        this.subNames = checkSubnames(subNames) ? subNames : null;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            if (getSubNames() != null) {
                for (int i = 0; i < getSubNames().length; i++) {
                    list.add(new ItemStack(this, 1, i));
                }
            } else {
                list.add(new ItemStack(this));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (getSubNames() != null) {
            return stack.getMetadata() < getSubNames().length ? "item." + getSubNames()[stack.getMetadata()] : "";
        }
        return this.getUnlocalizedName();
    }

    public String[] getSubNames() {
        System.out.println("========================================================================");
        System.out.println(this.subNames);
        System.out.println("========================================================================");
        return this.subNames;
    }

    private boolean checkSubnames(String... subNames) {
        return subNames != null && subNames.length > 0;
    }
}
