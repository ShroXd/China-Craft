package com.bebopser.china;

import com.bebopser.china.loader.BlockLoader;
import com.bebopser.china.loader.ItemLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {


    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        ItemLoader.registerRenders();
    }

    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        BlockLoader.initModels();
    }
}
