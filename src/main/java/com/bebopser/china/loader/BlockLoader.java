package com.bebopser.china.loader;

import com.bebopser.china.Reference;
import com.bebopser.china.block.ChoppingBoard;
import com.bebopser.china.block.CookingTable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class BlockLoader {

    public static Block chopping_board = new ChoppingBoard("chopping_board");
    public static Block cooking_table = new CookingTable("cooking_table", 0.0F, false).setCreativeTab(Reference.tab);
    public static Block lit_cooking_table = new CookingTable("lit_cooking_table", 0.875F, true);


    public BlockLoader() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                cooking_table, chopping_board, lit_cooking_table
        );
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                getRegisteredItemBlock(cooking_table),
                getRegisteredItemBlock(chopping_board),
                getRegisteredItemBlock(lit_cooking_table)
        );
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        registerRender(cooking_table, "cooking_table");
        registerRender(chopping_board, "chopping_board");
        registerRender(lit_cooking_table, "lit_cooking_table");
    }

    private static Item getRegisteredItemBlock(Block block) {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block, String name) {
        ModelResourceLocation model = new ModelResourceLocation(Reference.MODID + ":" + name, "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
    }
}
