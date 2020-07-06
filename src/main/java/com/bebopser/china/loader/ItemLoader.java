package com.bebopser.china.loader;

import com.bebopser.china.Reference;
import com.bebopser.china.item.ItemFoodBasic;
import com.bebopser.china.item.items.ItemEdibleFood;
import com.bebopser.china.item.items.ItemRawFood;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader {

    public static ItemEdibleFood edibleFood = new ItemEdibleFood("edibleFood", 64,
            new int[] {4, 2},
            new float[] {0.2F, 0.2F},
            new String[] {
                    Reference.MODID + "." + "dumplings",
                    Reference.MODID + "." + "moon_cake",
            });

    public static ItemRawFood rawFood = new ItemRawFood("rawFood", 64,
            new int[] {-1, -1},
            new float[] {0.6F, 0.6F},
            new String[] {
                    Reference.MODID + "." + "flour",
                    Reference.MODID + "." + "flour_pastry",
                    Reference.MODID + "." + "flour_silk",
                    Reference.MODID + "." + "rice",
                    Reference.MODID + "." + "sticky_rice"
            });

    public ItemLoader(FMLPreInitializationEvent event) {
        register(edibleFood);
        register(rawFood);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        registerRender(edibleFood);
        registerRender(rawFood);
    }

    private static void register(Item item) {
        // item.            5
        // chinacraft       Reference.MODID.length()
        // .                1
        int prefixLength = 5 + Reference.MODID.length() + 1;
        ForgeRegistries.ITEMS.register(item.setRegistryName(item.getUnlocalizedName().substring(prefixLength)));
        item.setCreativeTab(Reference.tab);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemFoodBasic itemBase) {
        for (int i = 0; i < itemBase.getSubNames().length; i++) {
            String name = itemBase.getSubNames()[i].substring(Reference.MODID.length() + 1);
            ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation(Reference.MODID, name), "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBase, i, model);
        }
    }
}
