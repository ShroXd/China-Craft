package com.bebopser.china.item.items;

import com.bebopser.china.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemRawFood extends ItemFood {

    // https://blog.csdn.net/qp120291570/article/details/52705360?utm_source=blogxgwz4
    private String[] subNames;
    private int[] amounts;
    private float[] saturations;

    public ItemRawFood(String name, int stackSize, int[] amounts, float[] saturations, String[] subNames) {
        super(amounts[0], saturations[0], false);
        boolean isSubnamesExist = checkSubNames(subNames);

        this.setUnlocalizedName(Reference.MODID + "." + name);
        this.setMaxStackSize(stackSize);
        this.setHasSubtypes(isSubnamesExist);
        this.setAlwaysEdible();

        this.subNames = isSubnamesExist ? subNames : null;
        this.amounts = amounts;
        this.saturations = saturations;
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 10 * 20, 5, false, true));
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

    @Override
    public int getHealAmount(ItemStack stack) {
        int index = stack.getMetadata();
        // return stack.getMetadata() < getAmounts().length ? getAmounts()[index] : 0;
        return getAmounts()[index];
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        int index = stack.getMetadata();
        // return stack.getMetadata() < getSaturations().length ? getSaturations()[index] : 0;
        return getSaturations()[index];
    }

    public String[] getSubNames() {
        return this.subNames;
    }

    public int[] getAmounts() {
        return this.amounts;
    }

    public float[] getSaturations() {
        return this.saturations;
    }

    private boolean checkSubNames(String[] s) {
        return s != null && s.length > 0;
    }
}
