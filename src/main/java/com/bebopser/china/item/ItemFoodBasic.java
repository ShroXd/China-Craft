package com.bebopser.china.item;

import com.bebopser.china.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemFoodBasic extends ItemFood {

    protected String[] subNames;
    protected int[] amounts;
    protected float[] saturations;

    public ItemFoodBasic(String name, int stackSize, int[] amounts, float[] saturations, String... subNames) {
        super(amounts[0], saturations[0], false);
        this.setUnlocalizedName(Reference.MODID + "." + name);
        this.setAlwaysEdible();
        this.setMaxStackSize(stackSize);
        this.setHasSubtypes(subNames!=null&&subNames.length > 0);
        this.subNames = subNames!=null&&subNames.length > 0 ? subNames: null;
        this.amounts = amounts;
        this.saturations = saturations;
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        int index = stack.getMetadata();
        return stack.getMetadata() < getAmounts().length ? getAmounts()[index] : 0;
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        int index = stack.getMetadata();
        return stack.getMetadata() < getSaturations().length ? getSaturations()[index] : 0;
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 60*20, 5, false, true));
        }

        super.onFoodEaten(stack, world, player);
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
            int index = stack.getMetadata();
            return index < getSubNames().length ? "item." + getSubNames()[index] : "";
        }

        return this.getUnlocalizedName();
    }

    public int[] getAmounts() {
        return this.amounts;
    }

    public float[] getSaturations() {
        return this.saturations;
    }

    public String[] getSubNames() {
        return this.subNames;
    }

    private boolean checkSubnames(String... subNames) {
        return subNames != null && subNames.length > 0;
    }
}
