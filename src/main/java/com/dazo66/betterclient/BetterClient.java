package com.dazo66.betterclient;

import com.dazo66.betterclient.FeaturesBase.IFeature;
import com.dazo66.betterclient.coremod.IRegisterTransformer;
import com.dazo66.betterclient.coremod.MainTransformer;
import com.dazo66.shulkerboxshower.ShulkerBoxViewer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

import java.io.File;
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
    public static Configuration config = new Configuration(new File("config\\" + MODID + ".cfg"));
    ;
    public static Set<IFeature> enableFeatures = FeaturesRegister.enableFeatures;
    public static Set<IFeature> disableFeatures = FeaturesRegister.disableFeatures;

    static {
        config.load();
    }

    public static void registerFeatures() {
        FeaturesRegister.register(new ShulkerBoxViewer());
    }

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

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        for (IFeature feature : enableFeatures) {
            FeaturesRegister.configEntryInit(feature);
            feature.preInit(event);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        for (IFeature feature : enableFeatures) {
            boolean regResult = FeaturesRegister.registerHandleClass(feature);
            if (!regResult) {
                logger.error("Registor %s handle class was failed", feature.eventHandlerClass().getSimpleName());
            }
            feature.init(event);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (IFeature feature : enableFeatures) {
            feature.postInit(event);
        }
    }


    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        for (IFeature feature : enableFeatures) {
            feature.serverStarting(event);
        }
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        for (IFeature feature : enableFeatures) {
            feature.serverStarted(event);
        }
    }

    @EventHandler
    public void serverAbortToStart(FMLServerAboutToStartEvent event) {
        for (IFeature feature : enableFeatures) {
            feature.serverAboutToStart(event);
        }
    }

    @EventHandler
    public void serverStoped(FMLServerStoppedEvent event) {
        for (IFeature feature : enableFeatures) {
            feature.serverStoped(event);
        }
    }

    @EventHandler
    public void serverStoping(FMLServerStoppingEvent event) {
        for (IFeature feature : enableFeatures) {
            feature.serverStoping(event);
        }
    }
}
