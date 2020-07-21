package com.bebopser.china.recipes.blocks;

import com.bebopser.china.recipes.virtual.ICookingTableRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class CookingTableRecipe implements ICookingTableRecipe {

    private final NonNullList<ItemStack> inputs;
    private final ItemStack output;

    public CookingTableRecipe(NonNullList<ItemStack> inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public NonNullList<ItemStack> getInputs() {
        return inputs;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public boolean isTheSameInput(ItemStack input) {
        return !this.output.isEmpty() &&
                checkSame(this.inputs, input);
    }

    public String toString() {
        return inputs + "@" + output;
    }

    private boolean checkSame(NonNullList<ItemStack> stacks, @Nonnull ItemStack target) {
        for (ItemStack input : stacks) {
            if (OreDictionary.itemMatches(input, target, true)) {
                return true;
            }
        }

        return false;
    }
}
