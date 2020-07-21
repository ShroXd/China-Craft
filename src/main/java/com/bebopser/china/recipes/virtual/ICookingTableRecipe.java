package com.bebopser.china.recipes.virtual;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface ICookingTableRecipe {

    NonNullList<ItemStack> getInputs();

    ItemStack getOutput();

    boolean isTheSameInput(ItemStack stack);
}
