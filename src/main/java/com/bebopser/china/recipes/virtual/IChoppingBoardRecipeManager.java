package com.bebopser.china.recipes.virtual;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Collection;

public interface IChoppingBoardRecipeManager<R> {

    boolean equal(R recipe1, R recipe2);

    void add(R recipe);

    void remove(R recipe);

    void remove(NonNullList<ItemStack> rawFoods,
                NonNullList<ItemStack> tools,
                ItemStack output);

    void removeAll();

    Collection<R> getRecipes();

    R getRecipes(ItemStack rawFood, ItemStack tool);

    R getRecipes(NonNullList<ItemStack> rawFood, NonNullList<ItemStack> tool);
}
