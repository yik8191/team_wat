/*
 * GameMode.java
 *
 * This is the primary class file for running the game.  You should study this file for
 * ideas on how to structure your own root class. This class follows a
 * model-view-controller pattern fairly strictly.
 *
 * Author: Walker M. White
 * Based on original Optimization Lab by Don Holden, 2007
 * LibGDX version, 2/2/2015
 *
 * This code has been copied from the above original authors and modified by Gagik Hakobyan.
 */

package edu.cornell.cs3152.gameplayprototype;
// TODO: Import the package that contains our GameObjects

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;

import edu.cornell.cs3152.gameplayprototype.utils.*;


/**
 * The primary controller class for the game. <p/> While GDXRoot is the root class, it delegates all of the work to the
 * player mode classes. This is the player mode class for running the game. In initializes all of the other classes in
 * the game and hooks them together.  It also provides the basic game loop (update-draw).
 */
public class GameMode implements Screen{
	/**
	 * Track the current state of the game for the update loop.
	 */
	public enum GameState {
		/** Before the game has started */
		INTRO,
		/** While we are playing the game */
		PLAY
	}

	// GRAPHICS AND SOUND RESOURCES
	// Path names to texture and sound assets
	// TODO: replace the assets in these paths with the correct files
	private static String BKGD_FILE = "images/loading.png";
	private static String LVL1_FILE = "images/level1.png";
	private static String FONT_FILE = "fonts/TimesRoman.ttf";
	private static int FONT_SIZE = 24;
	// Asset loading is handled statically so these are static variables
	/** The background image for the game */
	private static Texture background;
	/**backgroung image for level 1 */
	private static Texture level1;
	/** The font for giving messages to the player*/
	private static BitmapFont displayFont;

	/**
	 * Preloads the assets for this game.
	 *
	 * All instances of the game use the same assets, so this is a static method.
	 * This keeps us from loading the assets multiple times.
	 *
	 * The asset manager for LibGDX is asynchronous. That means that you tell it what to load and then wait while it
	 * loads them. This is the first step : telling it what to load.
	 *
	 * @param manager Reference to global asset manager
	 */
	public static void PreLoadContent(AssetManager manager){
		// Load the background
		manager.load(BKGD_FILE, Texture.class);
		manager.load(LVL1_FILE, Texture.class);

		// Load the font
		FreetypeFontLoader.FreeTypeFontLoaderParameter size2Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		size2Params.fontFileName = FONT_FILE;
		size2Params.fontParameters.size = FONT_SIZE;
		manager.load(FONT_FILE, BitmapFont.class, size2Params);

		// Pre-load the other assets
		// TODO: Fill in the other assets we'll be using in this style:
		// s.PreLoadContent(manager);
		Knight.PreLoadContent(manager);
		Enemy.PreLoadContent(manager);
	}

	/**
	 * Loads the assets for this game.
	 *
	 * All instance of the game use the same assets, so this is a static method.
	 * This keeps us from loading the assets multiple times.
	 *
	 * The asset manager for LibGDX is asynchronous.  That means that you
	 * tell it what to load and then wait while it loads them.  This is
	 * the second step: extracting assets from the manager after it has
	 * finished loading them.
	 *
	 * @param manager Reference to global asset manager.
	 */
	public static void LoadContent(AssetManager manager){
		// Allocate the background
		if (manager.isLoaded(BKGD_FILE)) {
			background = manager.get(BKGD_FILE, Texture.class);
			background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			displayFont = null;
		}
		
		if (manager.isLoaded(LVL1_FILE)) {
			level1 = manager.get(LVL1_FILE, Texture.class);
			level1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			displayFont = null;
		}

		// Allocate the font
		if (manager.isLoaded(FONT_FILE)) {
			displayFont = manager.get(FONT_FILE, BitmapFont.class);
		} else {
			displayFont = null;
		}

		// Load other assets
		// TODO: Fill in the other assets we'll be using in this style:
		Knight.LoadContent(manager);
		Enemy.LoadContent(manager);
	}

	/**
	 * Unloads the assets for this game.
	 * <p/>
	 * This method erases the static variables.  It also deletes the associated textures
	 * from the asset manager.
	 *
	 * @param manager Reference to global asset manager.
	 */
	public static void UnloadContent(AssetManager manager) {
		if (background != null) {
			background = null;
			manager.unload(BKGD_FILE);
		}
		if (level1 != null) {
			level1 = null;
			manager.unload(LVL1_FILE);
		}
		if (displayFont != null) {
			displayFont = null;
			manager.unload(FONT_FILE);
		}

		// Load the other assets
		// TODO: Fill in the other assets we'll be using in this style:
		Knight.UnloadContent(manager);
		Enemy.UnloadContent(manager);
	}

	// CONSTANTS

	// TODO: Fill in UI related the constants we'll need
	// Ex: offset floats for strings we might display to screen like this:
	// /** Offset for the score counter message on the screen */
	// private static final float SCORE_OFFSET = 55.0f;

	/** Reference to drawing context to display graphics (VIEW CLASS) */
	private GameCanvas canvas;
	/** Reads input from keyboard or game pad (CONTROLLER CLASS) */
	private InputController inputController;
	/** Constructs the game models and handle basic gameplay (CONTROLLER CLASS) */
	private GameplayController gameplayController;

	// TODO: Fill in game state related variables like the one below
	/** Variable to track the game state (SIMPLE FIELDS) */
	private GameState gameState;

	/** Whether or not this player mode is still active */
	private boolean active;

	/** Listener that will update the player mode when we are done */
	private ScreenListener listener;

	/**
	 * Creates a new game with the given drawing context.
	 *
	 * This constructor initializes the models and controllers for the game.  The
	 * view has already been initialized by the root class.
	 */
	public GameMode(GameCanvas canvas) {
		this.canvas = canvas;
		active = false;
		// Null out all pointers, 0 out all ints, etc
		gameState = GameState.INTRO;

		// Create the controllers.
		inputController = new InputController();
		gameplayController = new GameplayController();
		// TODO: Fill in other initialization code
	}

	/**
	 * Dispose of all (non-static) resources allocated to this mode.
	 */
	public void dispose() {
		inputController = null;
		gameplayController = null;
		canvas = null;
	}

	/**
	 * Update the game state.
	 *
	 * We prefer to separate update and draw from one another as separate methods, instead
	 * of using the single render() method that LibGDX does.  We will talk about why we
	 * prefer this in lecture.
	 *
	 * @param delta Number of seconds since last animation frame
	 */
	private void update(float delta){
		// Process the game input
		int action = inputController.getAction();

        boolean hacky = gameplayController.knight.update(action);
        if (hacky == true) {
            gameplayController.enemies[0].update();
            gameplayController.enemies[1].update();
        }

		// Test whether to reset the game.
		switch (gameState) {
			case INTRO:
				gameState = GameState.PLAY;
				gameplayController.initialize();
				// TODO: intialize
				break;
			case PLAY:
				if (gameplayController.isGameOver()) reset();
				else play();
				break;
		}

		// TODO: do we need any other update code?
	}

	/**
	 * This method processes a single step in the game loop.
	 */
	protected void play() {
		// TODO: this is the main game loop. Call update on everything, set values, garbage collect
		// NO DRAWING CODE HERE
		inputController.getAction();
		gameplayController.resolveActions(inputController);

	}

	/** This method resets the game */
	protected void reset() {
		gameState = gameState.INTRO;
		// TODO: take care of other resetting code. E.g. call reset on gameplayController
		// NO INITIALIZATION CODE HERE. That's taken care of in update.
	}

	/**
	 * Draw the status of this player mode.
	 *
	 * We prefer to separate update and draw from one another as separate methods, instead
	 * of using the single render() method that LibGDX does.  We will talk about why we
	 * prefer this in lecture.
	 *
	 * @param delta Number of seconds since last animation frame
	 */
	protected void draw(float delta) {
		canvas.begin();
		// TODO: this is the main drawing loop. Draw the background, draw objects, draw UI
		// NO UPDATE CODE HERE
		if (gameState == GameState.INTRO){
			canvas.drawBackground(background, 0, 0);
		}else{
			canvas.drawBackground(level1, 0, 0);
		}
		gameplayController.knight.draw(canvas);
		for (Enemy e : gameplayController.enemies) {
			e.draw(canvas);
		}
		canvas.end();
	}

	/**
	 * Called when the Screen is resized.
	 *
	 * This can happen at any point during a non-paused state but will never happen before a call to show().
	 *
	 * @param width  The new width in pixels
	 * @param height The new height in pixels
	 */
	public void resize(int width, int height) {
		// IGNORE FOR NOW
	}

	/**
	 * Called when the Screen should render itself.
	 *
	 * We defer to the other methods update() and draw().  However, it is VERY important that we only quit AFTER a
	 * draw.
	 *
	 * @param delta Number of seconds since last animation frame
	 */
	public void render(float delta) {
		if (active) {
			update(delta);
			draw(delta);
			if (inputController.didExit() && listener != null) {
				listener.exitScreen(this, 0);
			}
		}
	}

	/**
	 * Called when the Screen is paused.
	 *
	 * This is usually when it's not active or visible on screen. An Application is also paused before it is destroyed.
	 */
	public void pause() {
		// TODO Auto-generated method stub
	}

	/**
	 * Called when the Screen is resumed from a paused state.
	 *
	 * This is usually when it regains focus.
	 */
	public void resume() {
		// TODO Auto-generated method stub
	}

	/**
	 * Called when this screen becomes the current screen for a Game.
	 */
	public void show() {
		// Useless if called in outside animation loop
		active = true;
	}

	/**
	 * Called when this screen is no longer the current screen for a Game.
	 */
	public void hide() {
		// Useless if called in outside animation loop
		active = false;
	}

	/**
	 * Sets the ScreenListener for this mode
	 *
	 * The ScreenListener will respond to requests to quit.
	 */
	public void setScreenListener(ScreenListener listener) {
		this.listener = listener;
	}
}
