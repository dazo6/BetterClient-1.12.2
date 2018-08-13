package com.dazo66.betterclient.util;

import com.dazo66.betterclient.BetterClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;

/**
 * @author Dazo66
 */
public class I18n {
    public static String format(String translateKey, Object... parameters) {
        if (hasKey(translateKey)) {
            return net.minecraft.client.resources.I18n.format(translateKey, parameters);
        }else {
            Language lang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
            BetterClient.logger.warn("TranslateKey not found: key: %s, local: %s", translateKey, lang.toString());
            return translateKey;
        }

    }

    public static boolean hasKey(String key) {
        return net.minecraft.client.resources.I18n.hasKey(key);
    }



}
