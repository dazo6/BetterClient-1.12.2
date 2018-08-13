package com.dazo66.betterclient.config.configentrys;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.functionsbase.IFunction;
import com.google.common.primitives.Floats;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nullable;

/**
 * @author Dazo66
 */
public class FloatConfigEntry implements IConfigEntry<Float> {

    private Configuration config;
    private String key;
    private float defaultValue;
    private IFunction owner;
    private String comment;
    private Float min;
    private Float max;
    private Property property;

    public FloatConfigEntry(String keyIn, float defultValueIn, IFunction ownerIn, @Nullable String commentIn, @Nullable Float minIn, @Nullable Float maxIn) {
        config = BetterClient.config;
        key = keyIn;
        defaultValue = defultValueIn;
        owner = ownerIn;
        comment = commentIn;
        min = minIn;
        max = maxIn;
        property = getProperty();
    }

    public FloatConfigEntry(String keyIn, float defultValueIn, IFunction ownerIn) {
        this(keyIn, defultValueIn, ownerIn, null, null, null);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Float getDefaultValue() {
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

    public Float getMin() {
        return min;
    }

    public Float getMax() {
        return max;
    }


    @Override
    public Float getValue() {
        try
        {
            String s = getProperty().getString();
            float parseFloat = Float.parseFloat(getProperty().getString());
            if (min != null && max != null) {
                return Floats.constrainToRange(parseFloat, min, max);
            }
            return parseFloat;
        }
        catch (Exception e)
        {
            FMLLog.log.error("Failed to get float for {}/{}", key, owner.getID(), e);
        }
        return defaultValue;
    }

    @Override
    public void setValue(Float i) {
        getProperty().setValue(i);
        config.save();
    }

    @Override
    public Property getProperty() {
        Property prop = config.get(owner.getID(), key, Float.toString(defaultValue), key);
        prop.setLanguageKey(key);
        return prop;
    }

}
