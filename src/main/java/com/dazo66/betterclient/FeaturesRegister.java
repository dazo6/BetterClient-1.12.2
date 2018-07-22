package com.dazo66.betterclient;

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

    private static boolean canLoad(IFeature feature) {
        return true;
    }

}
