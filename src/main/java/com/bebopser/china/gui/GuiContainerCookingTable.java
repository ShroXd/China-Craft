package com.bebopser.china.gui;

import com.bebopser.china.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiContainerCookingTable extends GuiContainer {

    private static final String TEXTURE_PATH = Reference.MODID + ":" + "textures/gui/cooking_table.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    private ContainerCookingTable inventory;
    private int totalDryTime = 1;
    private int totalSteam = 32;

    public GuiContainerCookingTable(ContainerCookingTable inventorySlotsIn) {
        super(inventorySlotsIn);

        this.xSize = 176;
        this.ySize = 166;
        this.inventory = inventorySlotsIn;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTick);
        this.renderHoveredToolTip(mouseX, mouseY);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0f);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);


    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = I18n.format(Reference.MODID + ".container.cooking_table");
        this.fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
    }
}
