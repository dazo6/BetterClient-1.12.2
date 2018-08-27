package com.dazo66.betterclient;

import com.dazo66.betterclient.coremod.IRegisterTransformer;
import com.dazo66.betterclient.coremod.MainTransformer;
import com.dazo66.betterclient.event.BetterClientEventHandler;
import com.dazo66.betterclient.functionsbase.IFunction;
import com.dazo66.fastcrafting.FastCrafting;
import com.dazo66.fasttrading.FastTrading;
import com.dazo66.prompt.Prompt;
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
    public static final String VERSION = "beta-1.0";
    public static final String MCVersion = MainTransformer.getMCVERSION();

    @Mod.Instance
    public static BetterClient betterClient = new BetterClient();
    public static Logger logger;
    public static Configuration config = new Configuration(new File("config\\" + MODID + ".cfg"));
    public static Set<IFunction> enableFeatures = FunctionsRegister.enableFunctions;
    public static Set<IFunction> disableFeatures = FunctionsRegister.disableFunctions;

    static {
        config.load();
    }

    /**
     * feature register at here
     */
    public static void registerFunctions() {
        FunctionsRegister.register(new ShulkerBoxViewer());
        FunctionsRegister.register(new FastCrafting());
        FunctionsRegister.register(new FastTrading());
        FunctionsRegister.register(new Prompt());
    }

    public static void registerTransformerClass(MainTransformer mainTransformer) {
        for (IFunction feature : enableFeatures) {
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
        for (IFunction feature : enableFeatures) {
            feature.preInit(event);
            FunctionsRegister.configEntryInit(feature);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FunctionsRegister.registerHandleClass(BetterClientEventHandler.class);
        for (IFunction feature : enableFeatures) {
            boolean regResult = FunctionsRegister.registerHandleClass(feature);
            if (!regResult) {
                logger.error("Registor %s handle class was failed", feature.eventHandlerClass().getSimpleName());
            }
            feature.init(event);
        }
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        for (IFunction feature : enableFeatures) {
            feature.loadComplete(event);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (IFunction feature : enableFeatures) {
            feature.postInit(event);
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        for (IFunction feature : enableFeatures) {
            feature.serverStarting(event);
        }
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        for (IFunction feature : enableFeatures) {
            feature.serverStarted(event);
        }
    }

    @EventHandler
    public void serverAbortToStart(FMLServerAboutToStartEvent event) {
        for (IFunction feature : enableFeatures) {
            feature.serverAboutToStart(event);
        }
    }

    @EventHandler
    public void serverStoped(FMLServerStoppedEvent event) {
        for (IFunction feature : enableFeatures) {
            feature.serverStoped(event);
        }
    }

    @EventHandler
    public void serverStoping(FMLServerStoppingEvent event) {
        for (IFunction feature : enableFeatures) {
            feature.serverStoping(event);
        }
    }
}
