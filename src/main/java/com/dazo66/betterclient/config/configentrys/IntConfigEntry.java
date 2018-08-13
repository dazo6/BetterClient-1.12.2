package com.dazo66.betterclient.config.configentrys;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.functionsbase.IFunction;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import javax.annotation.Nullable;

/**
 * @author Dazo66
 */
public class IntConfigEntry implements IConfigEntry<Integer> {

    private Configuration config;
    private String key;
    private int defaultValue;
    private IFunction owner;
    private String comment;
    private Integer min;
    private Integer max;
    private Property property;

    public IntConfigEntry(String keyIn, int defultValueIn, IFunction ownerIn, @Nullable String commentIn, @Nullable Integer minIn, @Nullable Integer maxIn) {
        config = BetterClient.config;
        key = keyIn;
        defaultValue = defultValueIn;
        owner = ownerIn;
        comment = commentIn;
        min = minIn;
        max = maxIn;
        property = getProperty();
    }

    public IntConfigEntry(String keyIn, int defultValueIn, IFunction ownerIn) {
        this(keyIn, defultValueIn, ownerIn, null, null, null);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Integer getDefaultValue() {
        return defaultValue;
    }

    @Override
    public IFunction getOwner() {
        return owner;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }


    @Override
    public Integer getValue() {
        if (comment == null && (min == null || max == null)) {
            return property.getInt(defaultValue);
        }
        int i = property.getInt(defaultValue);
        return i < min ? min : (i > max ? max : i);
    }

    @Override
    public void setValue(Integer i) {
        property.setValue(i);
        config.save();
    }

    @Override
    public Property getProperty() {
        if (property != null) {
            return property;
        }
        Property prop = config.get(owner.getID(), key, defaultValue);
        prop.setLanguageKey(key);
        if (min != null && max != null) {
            prop.setComment(comment + " [range: " + min + " ~ " + max + ", default: " + defaultValue + "]");
            prop.setMinValue(min);
            prop.setMaxValue(max);
        }
        return prop;
    }


}
