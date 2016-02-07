package com.chauffr.home.adapter;

import java.util.ArrayList;

/**
 * Created by Wenyue on 24/12/2015.
 */
public class SideBarList {
    ArrayList<NavDrawerItem> list;
    public SideBarList(){
        list = new ArrayList<NavDrawerItem>();
        prepareDrawerItems();
    }

    private void prepareDrawerItems() {
        list.add(new NavDrawerItem("{fa-home}","Request a Ridea"));
        list.add(new NavDrawerItem("{fa-home}","Request a Ridea"));
        list.add(new NavDrawerItem("{fa-home}","Request a Ridea"));
        list.add(new NavDrawerItem("{fa-home}","Request a Ridea"));
        list.add(new NavDrawerItem("{fa-home}","Request a Ridea"));
        list.add(new NavDrawerItem("{fa-home}","Request a Ridea"));
        list.add(new NavDrawerItem("{fa-sign-out}","Logout"));
    }

    public ArrayList<NavDrawerItem> getList(){
        return list;
    }

}
