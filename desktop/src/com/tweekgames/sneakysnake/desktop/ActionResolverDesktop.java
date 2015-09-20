package com.tweekgames.sneakysnake.desktop;

import com.tweekgames.sneakysnake.util.ActionResolver;

/**
 * Created by EvansDesktop on 4/5/2015.
 */
public class ActionResolverDesktop implements ActionResolver {
    @Override
    public void showOrLoadInterstitial() {
        System.out.println("showOrLoadInterstitial");
    }
}
