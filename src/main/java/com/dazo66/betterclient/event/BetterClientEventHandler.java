package com.dazo66.betterclient.event;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.util.LangFileUpdater;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * @author Dazo66
 */
public class BetterClientEventHandler {

    LangFileUpdater updater = new LangFileUpdater(new File("F:\\ModWorkSpace\\Mod\\BettterClient\\BettterClient-1.12.2\\src\\main\\resources\\assets\\betterclient\\lang\\en_us.lang"), new File("F:\\ModWorkSpace\\Mod\\BettterClient\\BettterClient-1.12.2\\src\\main\\resources\\assets\\betterclient\\lang\\zh_cn.lang"));

    @SubscribeEvent
    public void onI18n(I18nEvent event) {
        if (!I18n.hasKey(event.getLangKey())) {
            String langKey = event.getLangKey();
            Language lang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
            BetterClient.logger.warn(String.format("TranslateKey not found: key: %s, local: %s", langKey, lang.toString()));
            updater.put(langKey, null);
        }
    }
}
