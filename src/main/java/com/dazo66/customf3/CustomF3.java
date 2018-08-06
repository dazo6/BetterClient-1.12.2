package com.dazo66.customf3;

import com.dazo66.customf3.eventhandler.CustomF3EventHandler;
import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import com.dazo66.betterclient.coremod.IRegisterTransformer;
import com.dazo66.betterclient.featuresbase.IFeature;
import net.minecraftforge.fml.common.event.*;

import java.util.List;

/**
 * @author Dazo66
 */
public class CustomF3 implements IFeature {
    public static final String ID = "customf3";

    @Override
    public String getID() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public List<Class<? extends IRegisterTransformer>> transformerClass() {
        return null;
    }

    @Override
    public Class eventHandlerClass() {
        return CustomF3EventHandler.class;
    }

    @Override
    public List<IConfigEntry> getConfigEntrys() {
        return null;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void loadComplete(FMLLoadCompleteEvent event) {

    }

    @Override
    public void serverStarted(FMLServerStartedEvent event) {

    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {

    }

    @Override
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {

    }

    @Override
    public void serverStoping(FMLServerStoppingEvent event) {

    }

    @Override
    public void serverStoped(FMLServerStoppedEvent event) {

    }
}
