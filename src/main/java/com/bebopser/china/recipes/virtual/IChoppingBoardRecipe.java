package com.bebopser.china.recipes.virtual;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IChoppingBoardRecipe {

    NonNullList<ItemStack> getRawFoodInput();
    NonNullList<ItemStack> getToolInput();

    ItemStack getOutput();

    boolean isTheSameInput(ItemStack rawFood, ItemStack tool);
}
