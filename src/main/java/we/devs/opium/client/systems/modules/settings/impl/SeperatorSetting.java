package we.devs.opium.client.systems.modules.settings.impl;

import we.devs.opium.client.systems.modules.settings.Setting;

public class SeperatorSetting extends Setting {

    private final String title;

    public SeperatorSetting() {
        this(null);
    }

    public String getTitle() {
        return title;
    }

    public SeperatorSetting(String title) {
        super("name", "description", true);
        this.title = title;
    }
}
