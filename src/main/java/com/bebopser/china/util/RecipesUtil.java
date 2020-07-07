package com.bebopser.china.util;

import com.bebopser.china.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesUtil {

    /* 注册物品、方块合成 */
    public static void addRecipes(Item item, IRecipe iRecipe) {
        String name = item.getRegistryName().toString().replaceAll(":", ".");
        addRecipes(name, iRecipe);
    }

    public static void addRecipes(Block block, IRecipe iRecipe) {
        String name = block.getRegistryName().toString().replaceAll(":", ".");
        addRecipes(name, iRecipe);
    }

    public static void addRecipes(String key, IRecipe iRecipe) {
        if (iRecipe.getRegistryName() == null) {
            iRecipe.setRegistryName(new ResourceLocation(Reference.MODID, key));
        }
        ForgeRegistries.RECIPES.register(iRecipe);
    }

    /* 注册矿物熔炼 */
    public static void addOreDictionarySmelting(String ore, ItemStack output, float exp) {
        for (ItemStack itemStack : OreDictionary.getOres(ore)) {
            GameRegistry.addSmelting(itemStack, output, exp);
        }
    }

    public static void addOreDictionarySmelting(String ore, ItemStack output) {
        for (ItemStack itemStack : OreDictionary.getOres(ore)) {
            GameRegistry.addSmelting(itemStack, output, 0F);
        }
    }

    public static NBTTagCompound getItemTagCompound(ItemStack stack) {
        NBTTagCompound tag;
        if (stack.hasTagCompound()) {
            tag = stack.getTagCompound();
        } else {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }
        return tag;
    }
}
