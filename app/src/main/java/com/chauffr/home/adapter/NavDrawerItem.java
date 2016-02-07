package com.chauffr.home.adapter;

/**
 * Created by Wenyue on 21/12/2015.
 */
public class NavDrawerItem {


    private String icon;
    private String text;

    public NavDrawerItem(String icon, String text){
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}