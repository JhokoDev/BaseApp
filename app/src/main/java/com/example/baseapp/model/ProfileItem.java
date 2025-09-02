package com.example.baseapp.model;

public class ProfileItem {
    private String title;
    private String value;
    private boolean isAction;

    public ProfileItem(String title, String value, boolean isAction) {
        this.title = title;
        this.value = value;
        this.isAction = isAction;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public boolean isAction() {
        return isAction;
    }
}