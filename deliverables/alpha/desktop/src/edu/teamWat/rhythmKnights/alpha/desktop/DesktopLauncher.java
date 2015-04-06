package edu.teamWat.rhythmKnights.alpha.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.*;

import edu.teamWat.rhythmKnights.alpha.RhythmKnights;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = screenSize.width;
		config.height = screenSize.height;
		config.fullscreen = false;
		new LwjglApplication(new RhythmKnights(), config);
	}
}
