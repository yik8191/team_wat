package edu.cornell.cs3152.gameplayprototype.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.cornell.cs3152.gameplayprototype.GDXRoot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.width = 600;
		config.resizable = false;
		new LwjglApplication(new GDXRoot(), config);
	}
}
