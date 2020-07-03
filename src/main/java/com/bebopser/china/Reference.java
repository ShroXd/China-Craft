package com.bebopser.china;

import com.bebopser.china.loader.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Reference {

    public static final String MODID = "chinacraft";
    public static final String MODNAME = "China Craft";
    public static final String VERSION = "@version@";
    public static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.12.2]";

    public static CreativeTabs tab = new CreativeTabs("china_craft") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemLoader.dumplings);
        }
    };
}
