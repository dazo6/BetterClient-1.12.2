package com.dazo66.betterclient.coremod;

import net.minecraft.launchwrapper.IClassTransformer;

import java.util.List;

/**
 * @author Dazo66
 */
public interface IRegisterTransformer extends IClassTransformer {

    String getMcVersion();

    List<String> getClassName();
}
