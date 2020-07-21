package com.bebopser.china.recipes.blocks;

import com.bebopser.china.recipes.virtual.ICookingTableRecipe;
import com.bebopser.china.recipes.virtual.ICookingTableRecipeManager;
import com.bebopser.china.util.NonNullListHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collection;

public class CookingTableRecipeManager implements ICookingTableRecipeManager<ICookingTableRecipe> {

    private static ArrayList<ICookingTableRecipe> recipes = new ArrayList<>();

    @Override
    public boolean equal(ICookingTableRecipe recipe1, ICookingTableRecipe recipe2) {
        return recipe1.equals(recipe2);
    }

    @Override
    public void add(ICookingTableRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public void remove(ICookingTableRecipe recipe) {
        java.util.Iterator<ICookingTableRecipe> iter = recipes.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(recipe)) {
                iter.remove();
                return;
            }
        }
    }

    @Override
    public void remove(NonNullList<ItemStack> list, ItemStack output) {
        ICookingTableRecipe recipe = getRecipe(list);
        if (OreDictionary.itemMatches(output, recipe.getOutput(), false)) {
            remove(recipe);
        }
    }

    @Override
    public void removeAll() {
        recipes.clear();
    }

    @Override
    public Collection<ICookingTableRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public ICookingTableRecipe getRecipe(ItemStack input) {
        for (ICookingTableRecipe recipe : recipes) {
            if (recipe.isTheSameInput(input)) {
                return recipe;
            }
        }

        return new CookingTableRecipe(NonNullListHelper.createNonNullList(input), ItemStack.EMPTY);
    }

    @Override
    public ICookingTableRecipe getRecipe(NonNullList<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            ICookingTableRecipe recipe = getRecipe(input);
            if (!recipe.getOutput().isEmpty()) {
                return recipe;
            }
        }

        return new CookingTableRecipe(inputs, ItemStack.EMPTY);
    }
}
