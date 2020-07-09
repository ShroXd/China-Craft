package com.bebopser.china.gui;

import com.bebopser.china.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiContainerChoppingBoard extends GuiContainer {

    private static final String TEXTURE_PATH = Reference.MODID + ":" + "textures/gui/chopping_board.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    private ContainerChoppingBoard inventory;

    public GuiContainerChoppingBoard(ContainerChoppingBoard inventorySlots) {
        super(inventorySlots);
        this.xSize = 176;
        this.ySize = 166;
        this.inventory = inventorySlots;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2,
            offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        int cookTime = this.inventory.getCookTime();
        int textureWidth = 1 + (int) Math.ceil(24.0 * cookTime / 200);
        this.drawTexturedModalRect(offsetX + 75, offsetY + 33, 176, 0, textureWidth, 17);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = I18n.format(Reference.MODID + ".container.chopping_board");
        this.fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
    }
}
