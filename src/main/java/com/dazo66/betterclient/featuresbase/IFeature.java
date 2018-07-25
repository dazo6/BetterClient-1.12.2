package com.dazo66.betterclient.featuresbase;

import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import com.dazo66.betterclient.coremod.IRegisterTransformer;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * @author Dazo66
 */
public interface IFeature {

    /**
     * the feature id
     *
     * @return id
     */
    String getID();

    /**
     * the feature name return the local name{@link net.minecraft.client.resources.I18n} was best
     *
     * @return name
     */
    String getName();

    /**
     * this feature version
     *
     * @return version
     */
    String getVersion();

    /**
     * the author of this feature
     *
     * @return author
     */
    String getAuthor();

    /**
     * a list if transformer class
     *
     * @return list
     */
    List<Class<? extends IRegisterTransformer>> transformerClass();

    /**
     * you can put all event {@link SubscribeEvent} to the handler class don't need to register
     * please don't make method static in handles class
     *
     * @return handler class
     */
    Class eventHandlerClass();

    /**
     * the config entryts
     * all of this will display in config gui
     *
     * @return a list of {@link IConfigEntry}
     */
    List<IConfigEntry> getConfigEntrys();

    /**
     * on FMLPreInitializationEvent invoke
     *
     * @param event FMLPreInitializationEvent
     */
    void preInit(FMLPreInitializationEvent event);

    /**
     * on FMLInitializationEvent invoke
     *
     * @param event FMLInitializationEvent
     */
    void init(FMLInitializationEvent event);

    /**
     * on FMLPostInitializationEvent invoke
     *
     * @param event FMLPostInitializationEvent
     */
    void postInit(FMLPostInitializationEvent event);

    /**
     * on FMLLoadCompleteEvent invoke
     *
     * @param event FMLLoadCompleteEvent
     */
    void loadComplete(FMLLoadCompleteEvent event);

    /**
     * on FMLServerStartedEvent invoke
     *
     * @param event FMLServerStartedEvent
     */
    void serverStarted(FMLServerStartedEvent event);

    /**
     * on FMLServerStartingEvent invoke
     *
     * @param event FMLServerStartingEvent
     */
    void serverStarting(FMLServerStartingEvent event);

    /**
     * on FMLServerAboutToStartEvent invoke
     *
     * @param event FMLServerAboutToStartEvent
     */
    void serverAboutToStart(FMLServerAboutToStartEvent event);

    /**
     * on FMLServerStoppingEvent invoke
     *
     * @param event FMLServerStoppingEvent
     */
    void serverStoping(FMLServerStoppingEvent event);

    /**
     * on FMLServerStoppedEvent invoke
     *
     * @param event FMLServerStoppedEvent
     */
    void serverStoped(FMLServerStoppedEvent event);

}
