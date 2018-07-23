package com.dazo66.betterclient;

import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import com.dazo66.betterclient.coremod.IRegisterTransformer;
import net.minecraftforge.fml.common.event.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Dazo66
 */
public interface IFeature {

    String getID();

    String getName();

    String getVersion();

    String getAuthor();

    List<Class<? extends IRegisterTransformer>> transformerClass();

    Class eventHandlerClass();

    LinkedList<IConfigEntry> getConfigEntrys();

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void loadComplete(FMLLoadCompleteEvent event);

    void serverStarted(FMLServerStartedEvent event);

    void serverStarting(FMLServerStartingEvent event);

    void serverAboutToStart(FMLServerAboutToStartEvent event);

    void serverStoping(FMLServerStoppingEvent event);

    void serverStoped(FMLServerStoppedEvent event);

}
