package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

public class ConfigValueAdapter {

    public Object adapt(NamedConfigValue namedConfigValue) {
        Config config = namedConfigValue.getConfig();
        ConfigValue configValue = config.getValue(namedConfigValue.getPath());

        switch (configValue.valueType()) {
            case OBJECT:
            case LIST:
                throw new UnsupportedOperationException();
            case NUMBER:
                return config.getNumber(namedConfigValue.getPath());
            case BOOLEAN:
                return config.getBoolean(namedConfigValue.getPath());
            case STRING:
                return config.getString(namedConfigValue.getPath());
            case NULL:
            default:
                return null;
        }
    }
}
