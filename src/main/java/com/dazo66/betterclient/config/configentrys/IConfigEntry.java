package com.dazo66.betterclient.config.configentrys;

import com.dazo66.betterclient.FeaturesBase.IFeature;
import net.minecraftforge.common.config.Property;

/**
 * @author Dazo66
 */
public interface IConfigEntry<T> {

    IFeature getOwner();

    String getKey();

    T getDefaultValue();

    String getComment();

    T getValue();

    void setValue(T valueIn);

    Property getProperty();
}
