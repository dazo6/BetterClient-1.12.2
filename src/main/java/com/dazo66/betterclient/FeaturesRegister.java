package com.dazo66.betterclient;

import com.dazo66.betterclient.featuresbase.IFeature;
import com.dazo66.betterclient.config.configentrys.BooleanConfigEntry;
import com.dazo66.betterclient.config.configentrys.IConfigEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dazo66
 */
public class FeaturesRegister {

    public static Set<IFeature> allFeatures = new HashSet<>();
    public static Set<IFeature> enableFeatures = new HashSet<>();
    public static Set<IFeature> disableFeatures = new HashSet<>();

    public static void register(IFeature featureIn) {
        allFeatures.add(featureIn);
        if (canLoad(featureIn)) {
            enableFeatures.add(featureIn);
        } else {
            disableFeatures.add(featureIn);
        }
    }

    public static boolean registerHandleClass(IFeature feature) {
        if (feature.eventHandlerClass() == null) {
            return false;
        }
        Class handler = feature.eventHandlerClass();
        for (Method method : handler.getMethods()) {
            if (method.isAnnotationPresent(SubscribeEvent.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    BetterClient.logger.error("Please don't use static in handler class. Method : %s will not register in event bus", method.getName());
                }
            }
        }
        try {
            MinecraftForge.EVENT_BUS.register(feature.eventHandlerClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            return false;
        }
        return true;
    }

    public static void configEntryInit(IFeature feature) {
        if (feature.getConfigEntrys() == null) {
            return;
        }
        for (IConfigEntry configEntry : feature.getConfigEntrys()) {
            configEntry.getValue();
        }

    }

    private static boolean canLoad(IFeature feature) {
        BooleanConfigEntry isEnable = new BooleanConfigEntry("enable", true, feature, "This feature is enable to load or not.");
        return isEnable.getValue();
    }

}
