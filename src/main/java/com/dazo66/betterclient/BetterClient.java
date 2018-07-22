package com.dazo66.betterclient;

import com.dazo66.betterclient.coremod.IRegisterTransformer;
import com.dazo66.betterclient.coremod.MainTransformer;
import com.dazo66.shulkerboxshower.ShulkerBoxViewer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * @author Dazo66
 */
@Mod(modid = BetterClient.MODID, name = BetterClient.NAME, version = BetterClient.VERSION, useMetadata = true, clientSideOnly = true, guiFactory = "com.dazo66.betterclient.config.gui.ConfigGuiFactory")
public class BetterClient {
    public static final String MODID = "betterclient";
    public static final String NAME = "BetterClient";
    public static final String VERSION = "1.0";
    public static final String MCVersion = MainTransformer.getMCVERSION();

    @Mod.Instance
    public static BetterClient betterClient = new BetterClient();
    public static Logger logger;
    public static Configuration config;
    public static Set<IFeature> enableFeatures = FeaturesRegister.enableFeatures;
    public static Set<IFeature> disableFeatures = FeaturesRegister.disableFeatures;

    public static void registerTransformerClass(MainTransformer mainTransformer) {
        for (IFeature feature : enableFeatures) {
            if (feature.transformerClass() == null) {
                continue;
            }
            for (Class<? extends IRegisterTransformer> transClass : feature.transformerClass()) {
                IRegisterTransformer iRegisterTransformer;
                try {
                    iRegisterTransformer = transClass.newInstance();
                    mainTransformer.register(iRegisterTransformer);
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("Cant register transformer class : " + transClass.getName());
                }
            }
        }
    }

    public static void registerFeatures() {
        FeaturesRegister.register(new ShulkerBoxViewer());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        for (IFeature feature : enableFeatures) {
            feature.getConfigEntrys();
        }
        logger = event.getModLog();
        for (IFeature feature : enableFeatures) {
            feature.preInit(event);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        for (IFeature feature : enableFeatures) {
            if (feature.eventHandlerClass() == null) {
                continue;
            }
            MinecraftForge.EVENT_BUS.register(feature.eventHandlerClass());
            feature.init(event);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (IFeature feature : enableFeatures) {
            feature.postInit(event);
        }
    }

}
