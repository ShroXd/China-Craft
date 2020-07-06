package com.bebopser.china.effect;

import com.bebopser.china.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionThirsty extends Potion {
    private static final ResourceLocation res = new ResourceLocation("chinacraft:textures/gui/potion.png");

    public PotionThirsty() {
        super(false, 0x000000);
        setPotionName("china.effect.thirsty");
        setRegistryName(Reference.MODID, "thirsty");
    }

    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.getTextureManager().bindTexture(res);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 18, 18, 18);
    }

    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(res);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 18, 18, 18, 256.0F, 256.0F);
    }
}
