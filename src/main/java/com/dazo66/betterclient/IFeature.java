package com.dazo66.betterclient;

import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import com.dazo66.betterclient.coremod.IRegisterTransformer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

/**
 * @author Dazo66
 */
public interface IFeature {

    String getID();

    String getName();

    String getVersion();

    List<Class<? extends IRegisterTransformer>> transformerClass();

    Class eventHandlerClass();

    List<IConfigEntry> getConfigEntrys();

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void loadComplete(FMLLoadCompleteEvent event);

}
