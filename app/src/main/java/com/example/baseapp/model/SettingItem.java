package com.example.baseapp.model;

public class SettingItem {
    private String title;
    private String description;
    private String key;
    private boolean isToggle;
    private boolean toggleValue;

    public SettingItem(String title, String description, String key, boolean isToggle, boolean toggleValue) {
        this.title = title;
        this.description = description;
        this.key = key;
        this.isToggle = isToggle;
        this.toggleValue = toggleValue;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public boolean isToggle() {
        return isToggle;
    }

    public boolean getToggleValue() {
        return toggleValue;
    }

    public void setToggleValue(boolean toggleValue) {
        this.toggleValue = toggleValue;
    }
}