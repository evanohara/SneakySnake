package com.tweekgames.sneakysnake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.tweekgames.sneakysnake.SneakySnakeGame;

public class DesktopLauncher {

    private static boolean rebuildAtlas = true;
    private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {
        if (rebuildAtlas) {
            Settings settings = new Settings();
            settings.maxWidth = 2048;
            settings.maxHeight = 2048;
            settings.debug = drawDebugOutline;
            TexturePacker.process(settings, "android/assets/assets-raw/images", "android/assets/images", "game-atlas");
            TexturePacker.process(settings, "android/assets/assets-raw/images-ui", "android/assets/images", "ui-atlas");
        }
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        //config.width = 800;
        //config.height = 480;

        //config.width = 1280;
        //config.height = 768;

        config.width = 1136;
        config.height = 640;

        //config.width = 960;
        //config.height = 640;

        //config.width = 1920;
        //config.height = 1080;

        //config.width = 1280;
        //config.height = 720;

		new LwjglApplication(new SneakySnakeGame(new ActionResolverDesktop()), config);
	}
}
