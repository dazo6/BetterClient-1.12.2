package com.dazo66.betterclient.config.gui;

import com.dazo66.betterclient.BetterClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import com.dazo66.betterclient.util.I18n;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

public class ConfigGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new BetterClientGuiConfig(parentScreen, BetterClient.MODID, I18n.format("betterclient.name"));
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
}
