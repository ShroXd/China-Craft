package com.bebopser.china.item;

import com.bebopser.china.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBasic extends Item {
    protected String[] subNames;

    public ItemBasic(String name, int stackSize, String[] subNames) {
        boolean isSubnamesExist = checkSubNames(subNames);

        this.setUnlocalizedName(Reference.MODID + "." + name);
        this.setMaxStackSize(stackSize);
        this.setHasSubtypes(isSubnamesExist);
        this.subNames = isSubnamesExist ? subNames : null;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (!this.isInCreativeTab(tab)) {
            return;
        }

        if (getSubNames() != null) {
            for (int i = 0; i < getSubNames().length; i++) {
                list.add(new ItemStack(this, 1, i));
            }
        } else {
            list.add(new ItemStack(this));
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
        return this.subNames;
    }

    private boolean checkSubNames(String[] s) {
        return s != null && s.length > 0;
    }
}
