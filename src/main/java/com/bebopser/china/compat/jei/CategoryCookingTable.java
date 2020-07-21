package com.bebopser.china.compat.jei;

import com.bebopser.china.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class CategoryCookingTable implements IRecipeCategory<IRecipeWrapper> {

    protected IDrawable background;
    protected IDrawableAnimated fireOverlay;
    protected IDrawableAnimated progressBar;

    public CategoryCookingTable(IGuiHelper helper) {
        ResourceLocation backgroundTexture = new ResourceLocation(Reference.MODID, "textures/gui/cooking_table.png");
        background = helper.createDrawable(backgroundTexture, 41, 17, 108, 59);

        IDrawableStatic fire = helper.createDrawable(backgroundTexture, 176, 0, 14, 14);
        fireOverlay = helper.createAnimatedDrawable(fire, 300, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic progress = helper.createDrawable(backgroundTexture, 176, 14, 24, 17);
        progressBar = helper.createAnimatedDrawable(progress, 300, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return Reference.MODID + "." + "cookingtable";
    }

    @Override
    public String getTitle() {
        return I18n.format("jei." + Reference.MODID + ".category" + ".cookingtable");
    }

    @Override
    public String getModName() {
        return "ChinaCraft";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        fireOverlay.draw(minecraft, 12, 22);
        progressBar.draw(minecraft, 37, 21);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        setRecipe(recipeLayout, recipeWrapper);
    }

    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup items = recipeLayout.getItemStacks();

        items.init(0, true, 11, 2);
        items.set(0, ((RecipeCookingTable) recipeWrapper).getInputs());
        items.init(1, false, 74, 21);
        items.set(1, ((RecipeCookingTable) recipeWrapper).getOutputs());
    }
}
