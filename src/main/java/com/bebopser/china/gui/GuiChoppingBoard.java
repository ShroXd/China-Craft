package com.bebopser.china.gui;

import com.bebopser.china.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiChoppingBoard extends GuiContainer {

    private static final ResourceLocation CHOPPING_BOARD_GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/chopping_board.png");

    private final InventoryPlayer playerInventory;
    private final IInventory tileFurnace;

    public GuiChoppingBoard(InventoryPlayer playerInv, IInventory furnaceInv) {
        super(new ContainerChoppingBoard(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.tileFurnace = furnaceInv;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(CHOPPING_BOARD_GUI_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //TODO: 特殊情况下的绘制
    }
}
