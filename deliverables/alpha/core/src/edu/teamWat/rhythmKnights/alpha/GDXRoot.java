
/**
 * GDXRoot.java
 *
 * This is the primary class file for running the game.  It is the "static main" of
 * LibGDX; it must extend ApplicationAdapter to work properly. 
 *
 * We prefer to keep this class fairly lightweight.  We want the ModeControllers to
 * do the hard work.  This class should just schedule the ModeControllers and allow
 * the player to switch between them. We will see more on this in a later lab. 
 *
 * Author: Walker M. White, modified TeamWat
 * Based on original GameX Ship Demo by Rama C. Hoetzlein, 2002
 * LibGDX version, 1/16/2015
 */
package edu.teamWat.rhythmKnights.alpha;


import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.assets.loaders.resolvers.*;

import edu.teamWat.rhythmKnights.alpha.utils.ScreenListener;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

/**
 * Root class for a LibGDX.  
 * 
 * This class is technically not the ROOT CLASS. Each platform has another class above
 * this (e.g. PC games use DesktopLauncher) which serves as the true root.  However, 
 * those classes are unique to each platform, while this class is the same across all 
 * plaforms. In addition, this functions as the root class all intents and purposes, 
 * and you would draw it as a root class in an architecture specification.  
 *
 * All of the methods of ApplicationAdapter are extremely important.  You should study
 * how each one is used.
 */
public class GDXRoot extends Game implements ScreenListener {
	/** AssetManager to load game assets (textures, sounds, etc.) */
	private AssetManager manager;
	/** Drawing context to display graphics (VIEW CLASS) */
	private GameCanvas canvas;
	/** Player mode for the asset loading screen (CONTROLLER CLASS) */
	private LoadingMode loading;
	/** Player mode for the game proper (CONTROLLER CLASS) */
	private GameMode playing;
    /** Player mode for the level select screen (CONTROLLER CLASS) */
    private SelectMode selecting;


    private int numLevels = 11;

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
		selecting = null;
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
        if (screen == selecting) {
            GameMode.LoadContent(manager);
            playing = new GameMode(canvas);
            selecting.dispose();
            selecting = null;

            playing.setNumLevels(this.numLevels);
            playing.setLevel(exitCode);
            playing.setScreenListener(this);
            setScreen(playing);
        } else if (screen == playing && exitCode == 1){
            selecting = new SelectMode(canvas, this.numLevels);
            selecting.setScreenListener(this);
            setScreen(selecting);
            playing.dispose();
            playing = null;
        }else if (exitCode != 0) {
            Gdx.app.error("RhythmKnights", "Exit with error code " + exitCode, new RuntimeException());
            Gdx.app.exit();
        } else if (screen == loading){
            selecting = new SelectMode(canvas, this.numLevels);
            selecting.setScreenListener(this);
            setScreen(selecting);
            loading.dispose();
            loading = null;

		}  else {
			// We quit the main application
			Gdx.app.exit();
		}
	}
}