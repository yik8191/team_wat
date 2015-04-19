/*
 * GameMode.java
 *
 * This is the primary class file for running the game. This class follows a
 * model-view-controller pattern fairly strictly.
 *
 * Author: Walker M. White
 * Based on original Optimization Lab by Don Holden, 2007
 * LibGDX version, 2/2/2015
 * Modified: Gagik Hakobyan
 */

package edu.teamWat.rhythmKnights.alpha;
// TODO: Import the package that contains our gameObjects

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import edu.teamWat.rhythmKnights.alpha.controllers.*;
import edu.teamWat.rhythmKnights.alpha.models.*;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.*;
import edu.teamWat.rhythmKnights.alpha.utils.ScreenListener;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

import javax.naming.ldap.ManageReferralControl;


/**
 * The primary controller class for the game. While GDXRoot is the root class, 
 * it delegates all of the work to the
 * player mode classes. This is the player mode class for running the game. 
 * In initializes all of the other classes in
 * the game and hooks them together.  
 * It also provides the basic game loop (update-draw).
 */
public class GameMode implements Screen{
	/**
	 * Track the current state of the game for the update loop.
	 */
	public enum GameState {
		/** Before the game has started */
		INTRO,
		/** While we are playing the game */
		PLAY,
        /** Player has won the game */
        WIN,
		LOSE
	}

	// GRAPHICS AND SOUND RESOURCES
	// Path names to texture and sound assets
	private static String BKGD_FILE = "images/game_background.png";
//	private static String LVL1_FILE = "images/level1.png";
	private static String FONT_FILE = "fonts/TimesRoman.ttf";
	private static int FONT_SIZE = 24;
	// Asset loading is handled statically so these are static variables
	/** The background image for the game */
	private static Texture background;
	/** background image for level 1 */
	// private static Texture level1;
	/** The font for giving messages to the player*/
	private static BitmapFont displayFont;

    private int curLevel = -1;
    private int numLevels = 1;

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

		// Load the font
		FreetypeFontLoader.FreeTypeFontLoaderParameter size2Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		size2Params.fontFileName = FONT_FILE;
		size2Params.fontParameters.size = FONT_SIZE;
		manager.load(FONT_FILE, BitmapFont.class, size2Params);

		// Pre-load the other assets
		// TODO: Fill in the other assets we'll be using in this style:
		Board.PreLoadContent(manager);
		Knight.PreLoadContent(manager);
		Enemy.PreLoadContent(manager);
		Ticker.PreLoadContent(manager);
		DynamicTile.PreLoadContent(manager);
		RhythmController.PreloadContent(manager);
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

		// Allocate the font
		if (manager.isLoaded(FONT_FILE)) {
			displayFont = manager.get(FONT_FILE, BitmapFont.class);
		} else {
			displayFont = null;
		}

		// Load other assets
		// TODO: Fill in the other assets we'll be using in this style:
		Board.LoadContent(manager);
		Knight.LoadContent(manager);
		Enemy.LoadContent(manager);
		Ticker.LoadContent(manager);
		DynamicTile.LoadContent(manager);
		RhythmController.LoadContent(manager);
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
		if (displayFont != null) {
			displayFont = null;
			manager.unload(FONT_FILE);
		}

		// Load the other assets
		// TODO: Fill in the other assets we'll be using in this style:
		Board.UnloadContent(manager);
		Knight.UnloadContent(manager);
		Enemy.UnloadContent(manager);
		Ticker.UnloadContent(manager);
		DynamicTile.UnloadContent(manager);
		RhythmController.UnloadContent(manager);
	}

	// CONSTANTS

	// TODO: Fill in UI related the constants we'll need
	// Ex: offset floats for strings we might display to screen like this:
	// /** Offset for the score counter message on the screen */
	// private static final float SCORE_OFFSET = 55.0f;

	/** Reference to drawing context to display graphics (VIEW CLASS) */
	private GameCanvas canvas;
	/** Reads input from keyboard or game pad (CONTROLLER CLASS) */
	private PlayerController playerController;
	/** Constructs the game models and handle basic gameplay (CONTROLLER CLASS) */
	private GameplayController gameplayController;

	// TODO: Fill in game state related variables like the one below
	/** Variable to track the game state (SIMPLE FIELDS) */
	private GameState gameState;

	/** Whether or not this player mode is still active */
	private boolean active;

	/** Listener that will update the player mode when we are done */
	private SpriteBatch spriteBatch;
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
		// TODO: Properly create the controllers. InputController is now abstract.
		gameplayController = new GameplayController();
		playerController = new PlayerController();
		gameplayController.playerController = playerController;
		Gdx.input.setInputProcessor(playerController);
	}

	/**
	 * Dispose of all (non-static) resources allocated to this mode.
	 */
	public void dispose() {
		playerController = null;
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
	private void update(float delta) {
		switch (gameState) {
			case INTRO:
				gameState = GameState.PLAY;
				// TODO: Fill in other initialization code
				gameplayController.initialize(this.curLevel);
                canvas.setOffsets(gameplayController.board.getWidth(), gameplayController.board.getHeight());
				//143.882f
				break;
			case PLAY:
				Knight knight =(Knight)gameplayController.gameObjects.getPlayer();
				if (gameplayController.isGameOver()) reset();
                else if (!knight.isAlive()) reset();
                else if (gameplayController.board.isGoalTile((int)knight.getPosition().x, (int)knight.getPosition().y)) {
					gameState = GameState.WIN;
					play();
					play();
					play();
				}
				else play();
				break;
            case WIN:
                this.curLevel++;
                this.curLevel = this.curLevel % this.numLevels;
                //TODO: add some sort of 'good job you win!' message
                gameState = GameState.INTRO;
//				spriteBatch = new SpriteBatch();
//				displayFont = new BitmapFont();
//				spriteBatch.begin();
//				displayFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
//				displayFont.draw(spriteBatch, "my-string", 30, 30);
////				spriteBatch.end();
                // Print level complete message!
                break;
			case LOSE:
				// Print level failed message!
				break;
		}
	}

    public void setNumLevels(int a){
        this.numLevels = a;
    }

	/**
	 * This method processes a single step in the game loop.
	 */
	protected void play() {
		// TODO: this is the main game loop. Call update on everything, set values, garbage collect
		// NO DRAWING CODE HERE
		gameplayController.update();

	}

	/** This method resets the game */
	protected void reset() {
		gameState = GameState.INTRO;
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
        canvas.draw(background, 0, 0);
		gameplayController.board.draw(canvas);
		gameplayController.gameObjects.draw(canvas);
		gameplayController.ticker.draw(canvas);
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

    public void setLevel(int x){
        this.curLevel = x;
    }
}
