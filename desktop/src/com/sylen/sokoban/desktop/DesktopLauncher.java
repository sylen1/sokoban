package com.sylen.sokoban.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sylen.sokoban.MySokoban;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		//config.width = 480;
		//config.height = 320;
		//config.width = 1280;
		//config.height = 800;
		//config.width = 1920;
		//config.height = 1200;
		new LwjglApplication(new MySokoban(), config);
	}
}
