package com.dazo66.betterclient;

import com.dazo66.betterclient.coremod.IRegisterTransformer;
import com.dazo66.betterclient.coremod.MainTransformer;
import com.dazo66.betterclient.event.BetterClientEventHandler;
import com.dazo66.betterclient.functionsbase.IFunction;
import com.dazo66.betterclient.util.reflection.ReflectionHelper;
import com.dazo66.bugfix.BugFix;
import com.dazo66.elytrafix.ElytraFix;
import com.dazo66.fastcrafting.FastCrafting;
import com.dazo66.fasttrading.FastTrading;
import com.dazo66.prompt.Prompt;
import com.dazo66.shulkerboxshower.ShulkerBoxViewer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Dazo66
 */
@Mod(modid = BetterClient.MODID, name = BetterClient.NAME, version = BetterClient.VERSION, useMetadata = true, clientSideOnly = true, guiFactory = "com.dazo66.betterclient.config.gui.ConfigGuiFactory")
public class BetterClient {
    public static final String MODID = "betterclient";
    public static final String NAME = "BetterClient";
    public static final String VERSION = "@version@";
    public static final String MCVersion = MainTransformer.getMCVERSION();

    @Mod.Instance
    public static BetterClient betterClient = new BetterClient();
    public static Logger logger;
    public static boolean DEBUG = isDEBUG();
    public static Configuration config = new Configuration(new File("config\\" + MODID + ".cfg"));
    public static Set<IFunction> enableFeatures = FunctionsRegister.enableFunctions;
    public static Set<IFunction> disableFeatures = FunctionsRegister.disableFunctions;

    static {
        config.load();
        logger = (org.apache.logging.log4j.core.Logger) LogManager.getLogger(MODID);
        if (DEBUG) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(Level.WARN);
        }
    }

    public BetterClient() {
    }

    /**
     * feature register at here
     */
    public static void registerFunctions() {
        FunctionsRegister.register(new ShulkerBoxViewer());
        FunctionsRegister.register(new FastCrafting());
        FunctionsRegister.register(new FastTrading());
        FunctionsRegister.register(new Prompt());
        FunctionsRegister.register(new ElytraFix());
        FunctionsRegister.register(new BugFix());
    }

    public static void registerTransformerClass(MainTransformer mainTransformer) {
        for (IFunction feature : enableFeatures) {
            List<Class<? extends IRegisterTransformer>> transformers = feature.transformerClass();
            if (transformers == null) {
                continue;
            }
            for (Class<? extends IRegisterTransformer> transClass : transformers) {
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

    @SuppressWarnings("unchecked")
    private static boolean isDEBUG() {
        Map<String, String> map = (Map<String, String>) Launch.blackboard.get("launchArgs");
        String s = map.get("--betterclient_debug");
        return Boolean.valueOf(s);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ReflectionHelper.getInstance().addDefineFile("betterclient_rh.cfg");
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
