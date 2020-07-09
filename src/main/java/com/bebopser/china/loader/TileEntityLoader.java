package com.bebopser.china.loader;

import com.bebopser.china.Reference;
import com.bebopser.china.block.tileentity.TileEntityChoppingBoard;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityLoader {

    public TileEntityLoader() {
        registerTileEntity(TileEntityChoppingBoard.class, "ChoppingBoard");
    }

    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
        GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(Reference.MODID, id));
    }
}
