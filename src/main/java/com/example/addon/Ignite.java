package com.ignite.rotatedesp;

import com.ignite.rotatedesp.modules.RotatedDeepslateESP;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class RotatedESPAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    // Your category shown in Meteor's GUI
    public static final Category CATEGORY = new Category("Visual");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Ignite");

        // Register your module
        Modules.get().add(new RotatedDeepslateESP());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.ignite.rotatedesp";
    }

    @Override
    public GithubRepo getRepo() {
        // Change this to your GitHub repo
        return new GithubRepo("Ignite", "RotatedESP");
    }
}
