package com.bebopser.china.item.items;

import com.bebopser.china.item.ItemFoodBasic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemRawFood extends ItemFoodBasic {

    public ItemRawFood(String name, int stackSize, int[] amounts, float[] saturations, String[] subNames) {
        super(name, stackSize, amounts, saturations, subNames);
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 10 * 20, 5, false, true));
        }

        super.onFoodEaten(stack, world, player);
    }
}
