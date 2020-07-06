package com.bebopser.china.loader;

import com.bebopser.china.Reference;
import com.bebopser.china.item.ItemBase;
import com.bebopser.china.item.ItemFoodBasic;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader {

    public static Item moon_cake = new Item().setUnlocalizedName(Reference.MODID + "." + "moon_cake").setMaxStackSize(64);
    public static Item dumplings = new Item().setUnlocalizedName(Reference.MODID + "." + "dumplings").setMaxStackSize(64);

    public static ItemBase raw_materials = new ItemBase("itemRawMaterials", 64,
            //Reference.MODID + "." + "flour",
            //Reference.MODID + "." + "flour_pastry",
            Reference.MODID + "." + "flour_silk",
            Reference.MODID + "." + "rice",
            Reference.MODID + "." + "sticky_rice");

    public static ItemFoodBasic raw = new ItemFoodBasic("raw", 64,
            new int[] {6},
            new float[] {0.6F},
            new String[]{
            Reference.MODID + "." + "flour",
            Reference.MODID + "." + "flour_pastry"});

    public ItemLoader(FMLPreInitializationEvent event) {
        register(moon_cake);
        register(dumplings);
        register(raw_materials);
        register(raw);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        registerRender(moon_cake);
        registerRender(dumplings);
        registerRender(raw_materials);
        registerRender(raw);
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
    private static void registerRender(Item item) {
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemBase itemBase) {
        for (int i = 0; i < itemBase.getSubNames().length; i++) {
            String name = itemBase.getSubNames()[i].substring(Reference.MODID.length() + 1);
            ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation(Reference.MODID, name), "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBase, i, model);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(ItemFoodBasic item) {
        System.out.println("====================================");
        System.out.println(item.getSubNames());
        System.out.println("====================================");
        for(int i = 0; i < item.getSubNames().length; i++){
            String name = item.getSubNames()[i].substring(Reference.MODID.length() + 1);
            ModelResourceLocation model = new ModelResourceLocation(new ResourceLocation(Reference.MODID,name), "inventory");
            ModelLoader.setCustomModelResourceLocation(item, i, model);
        }
    }
}
