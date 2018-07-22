package com.dazo66.betterclient.config.gui;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.FeaturesRegister;
import com.dazo66.betterclient.IFeature;
import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dazo66
 */
public class BetterClientGuiConfig extends GuiConfig {
    public BetterClientGuiConfig(GuiScreen parentScreen, String modid, String title) {
        super(parentScreen, getConfigElements(), modid, false, false, title);
    }

    public static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        for (IFeature feature : FeaturesRegister.allFeatures) {
            List<IConfigElement> list1 = new ArrayList<IConfigElement>();
            for (IConfigEntry configEntry : feature.getConfigEntrys()) {
                configEntry.getValue();
                list1.add(new ConfigElement(configEntry.getProperty()));
            }
            list.add(new DummyConfigElement.DummyCategoryElement(feature.getName(), feature.getName(), list1));
        }
        return list;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        BetterClient.config.save();
    }
}
