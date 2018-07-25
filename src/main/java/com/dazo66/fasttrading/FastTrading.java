package com.dazo66.fasttrading;

import com.dazo66.betterclient.featuresbase.AbstractFeature;
import com.dazo66.betterclient.config.configentrys.BooleanConfigEntry;
import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import com.dazo66.betterclient.config.configentrys.StringArrayConfigEntry;
import com.dazo66.betterclient.coremod.IRegisterTransformer;
import com.dazo66.fasttrading.config.ConfigLoader;
import com.dazo66.fasttrading.event.FastTradingEventHandler;
import com.dazo66.fasttrading.transformsclass.HookSetRecipeListEvent;
import com.dazo66.fasttrading.util.KeyLoader;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.NpcMerchant;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class FastTrading extends AbstractFeature {
    public static final String MODID = "fasttrading";
    public static final String NAME = "FastTrading";
    public static final String VERSION = "2.0";
    public static ConfigLoader configLoader = null;
    public static Logger logger;
    public static BooleanConfigEntry isAuto;
    public static StringArrayConfigEntry simpleRecipes;

    @Override
    public void init(FMLInitializationEvent event) {
        new KeyLoader();
        Class c = NpcMerchant.class;
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return I18n.format("fasttrading.name");
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
        return FastTradingEventHandler.class;
    }

    @Override
    public List<IConfigEntry> getConfigEntrys() {
        isAuto = new BooleanConfigEntry("fasttrading.isAuto", true, this, "is auto to trade when merchant gui open.");
        simpleRecipes = new StringArrayConfigEntry("fasttrading.autorecipelist", new String[0], this);
        return Arrays.asList(isAuto, simpleRecipes);
    }

    @Override
    public List<Class<? extends IRegisterTransformer>> transformerClass() {
        return Arrays.asList(HookSetRecipeListEvent.class);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        configLoader = new ConfigLoader(simpleRecipes);
        logger = event.getModLog();
    }

}
