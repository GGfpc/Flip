package com.jtbgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jtbgame.GameScreen;
import com.jtbgame.JTB;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "JTB";
        config.height = 480;
        config.width = 800;
        config.foregroundFPS = 60;
        new LwjglApplication(new JTB(), config);
	}
}
