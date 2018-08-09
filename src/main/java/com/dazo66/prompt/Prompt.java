package com.dazo66.prompt;

import com.dazo66.betterclient.config.configentrys.FloatConfigEntry;
import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import com.dazo66.betterclient.featuresbase.AbstractFeature;
import com.dazo66.betterclient.util.BetterClientUtils;
import com.dazo66.prompt.eventhandler.PromptEventhandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dazo66
 */
public class Prompt extends AbstractFeature {

    public static final String MODID = "fasttrading";
    public static final String NAME = "FastTrading";
    public static final String VERSION = "2.0";
    public static IConfigEntry<Float> warnHeal;

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getAuthor() {
        return "Dazo66";
    }

    @Override
    public Class eventHandlerClass() {
        return PromptEventhandler.class;
    }

    @Override
    public List<IConfigEntry> getConfigEntrys() {
        ArrayList<IConfigEntry> list = new ArrayList<>();
        list.add(warnHeal);
        return list;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        warnHeal = new FloatConfigEntry("warnHeal", 0.45f, this, "", 0f, 1f);
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }
}
