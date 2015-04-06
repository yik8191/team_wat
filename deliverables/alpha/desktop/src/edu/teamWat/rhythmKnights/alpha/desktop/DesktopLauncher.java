package edu.teamWat.rhythmKnights.alpha.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;

import edu.teamWat.rhythmKnights.alpha.GDXRoot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.addIcon("images/icon.png", Files.FileType.Internal);
		config.width = screenSize.width;
		config.height = screenSize.height;
		config.fullscreen = false;
		new LwjglApplication(new GDXRoot(), config);
	}
}