package com.bebopser.china.loader;

import com.bebopser.china.Reference;
import com.bebopser.china.item.ItemCustomTool;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ItemRegister {

    // 厨具
    public static Item rolling_pin = new ItemCustomTool("rolling_pin", 1, 5);

    public ItemRegister() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                rolling_pin
        );
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        registerRender(rolling_pin);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item) {
        if (item.getHasSubtypes()) {
            NonNullList<ItemStack> subItems = NonNullList.create();
            item.getSubItems(item.getCreativeTab(), subItems);
            for (ItemStack subItem: subItems) {
                String subItemName = item.getUnlocalizedName(subItem);
                subItemName = subItemName.substring(5 + Reference.MODID.length() + 1);

                ModelBakery.registerItemVariants(item, new ResourceLocation(Reference.MODID, subItemName));
                ModelLoader.setCustomModelResourceLocation(item, subItem.getMetadata(), new ModelResourceLocation(Reference.MODID + ":" + subItemName, "inventory"));
            }
        } else {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.delegate.name().getResourcePath(), "inventory"));
        }
    }
}
