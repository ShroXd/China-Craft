package com.bebopser.china.recipes.virtual;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Collection;

public interface ICookingTableRecipeManager<R> {

    boolean equal(R recipe1, R recipe2);

    void add(R recipe);

    void remove(R recipe);

    void remove(NonNullList<ItemStack> list, ItemStack output);

    void removeAll();

    Collection<R> getRecipes();

    R getRecipe(ItemStack input);

    R getRecipe(NonNullList<ItemStack> inputs);
}
