package edu.teamWat.rhythmKnights.alpha;


import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.assets.loaders.resolvers.*;

import edu.teamWat.rhythmKnights.alpha.utils.ScreenListener;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;


public class GDXRoot extends Game implements ScreenListener {
	/** AssetManager to load game assets (textures, sounds, etc.) */
	private AssetManager manager;
	/** Drawing context to display graphics (VIEW CLASS) */
	private GameCanvas canvas;
	/** Player mode for the asset loading screen (CONTROLLER CLASS) */
	private LoadingMode loading;
	/** Player mode for the game proper (CONTROLLER CLASS) */
	private GameMode playing;

	public GDXRoot() {
		// Start loading with the asset manager
		manager = new AssetManager();

		// Add font support to the asset manager
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
	}

	/**
	 * Called when the Application is first created.  This is method immediately loads assets for the loading
	 * screen, and prepares the asynchronous loader for all other assets.
	 */
	public void create() {
		canvas = new GameCanvas();
		loading = new LoadingMode(canvas, manager, 1);
		playing = null;

		loading.setScreenListener(this);
		GameMode.PreLoadContent(manager); // Load game assets statically.
		setScreen(loading);
	}

	/**
	 * Called when the Application is destroyed.  This is preceded by a call to pause().
	 */
	public void dispose() {
		// Call dispose on our children
		Screen screen = getScreen();
		setScreen(null);
		screen.dispose();
		canvas.dispose();
		canvas = null;

		// Unload all of the resources
		GameMode.UnloadContent(manager);
		manager.clear();
		manager.dispose();
		super.dispose();
	}

	/**
	 * Called when the Application is resized.  This can happen at any point during a non-paused state but will
	 * never happen before a call to create().
	 *
	 * @param width  The new width in pixels
	 * @param height The new height in pixels
	 */
	public void resize(int width, int height) {
		canvas.resize();
		super.resize(width, height);
	}

	/**
	 * The given screen has made a request to exit its player mode.  The value exitCode can be used to implement
	 * menu options.
	 *
	 * @param screen   The screen requesting to exit
	 * @param exitCode The state of the screen upon exit
	 */
	public void exitScreen(Screen screen, int exitCode) {
		if (exitCode != 0) {
			Gdx.app.error("RhythmKnights", "Exit with error code " + exitCode, new RuntimeException());
			Gdx.app.exit();
		} else if (screen == loading) {
			GameMode.LoadContent(manager);
			playing = new GameMode(canvas);

			playing.setScreenListener(this);
			setScreen(playing);

			loading.dispose();
			loading = null;
		} else {
			// We quit the main application
			Gdx.app.exit();
		}
	}
}
