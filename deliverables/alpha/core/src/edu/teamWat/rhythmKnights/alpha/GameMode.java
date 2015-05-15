/*
 * GameMode.java
 *
 * This is the primary class file for running the game. This class follows a
 * model-view-controller pattern fairly strictly.
 *
 * Author: Walker M. White, modified TeamWat
 * Based on original Optimization Lab by Don Holden, 2007
 * LibGDX version, 2/2/2015
 * Modified: Gagik Hakobyan
 */

package edu.teamWat.rhythmKnights.alpha;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import com.badlogic.gdx.math.Vector2;
import edu.teamWat.rhythmKnights.alpha.controllers.*;
import edu.teamWat.rhythmKnights.alpha.models.*;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.*;
import edu.teamWat.rhythmKnights.alpha.utils.ScreenListener;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

import java.util.ArrayList;


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
        /** Player has won the level */
        WIN,
        /** Player has won the game */
        COMPLETE,
        /** Player has paused the game */
        PAUSE,
        /** Player is dee-ee-dee dead */
		LOSE
	}

	// GRAPHICS AND SOUND RESOURCES
	// Path names to texture and sound assets
    private static ArrayList<String> BKGD_FILES = new ArrayList<String>(); //7
    private static String[] MENU_FILES = {"images/menus/replay.png",
            "images/menus/select.png", "images/menus/next.png", "images/menus/restart.png"};
    private static int numBackgrounds = 7;
    private static int backNum = 0;
	// Asset loading is handled statically so these are static variables
	/** The background image for the game */
	private static Texture[] backgrounds = new Texture[numBackgrounds];
	private static Texture[] menus = new Texture[4];

    //TODO: Replace this with an actual texture
    private Texture tileTexture;
    private static final String TILE_FILE = "images/tiles/tileFull1.png";

    private ArrayList<int[]> bounds = new ArrayList<int[]>();

    private int curLevel = -1;
    private int numLevels = 1;

    //private int DEFAULT_FRAMES = 179;
    private int DEFAULT_FRAMES = 5000;
    private int framesRemaining = DEFAULT_FRAMES;

    private static BitmapFont displayFont;
    private static String FONT_FILE = "fonts/RobotoSlab-Regular.ttf";
    private static int TUTORIAL_FONT_SIZE = 50;
    private static int FONT_SIZE = 50;

    private int selection = 0;

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
        //Populate background list then load them
        BKGD_FILES.clear();
        for (int i=0; i<numBackgrounds; i++){
            BKGD_FILES.add("images/backgrounds/bg"+(i+1)+".png");
            manager.load(BKGD_FILES.get(i), Texture.class);
        }

        for (int i=0; i<4; i++){
            manager.load(MENU_FILES[i], Texture.class);
        }

		// Load the font
		FreetypeFontLoader.FreeTypeFontLoaderParameter size2Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		size2Params.fontFileName = FONT_FILE;
		size2Params.fontParameters.size = FONT_SIZE;
		manager.load(FONT_FILE, BitmapFont.class, size2Params);

		// Pre-load the other assets
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
        for (int i=0; i<BKGD_FILES.size(); i++) {
            if (manager.isLoaded(BKGD_FILES.get(i))) {
                backgrounds[i] = manager.get(BKGD_FILES.get(i), Texture.class);
                backgrounds[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            } else {
                backgrounds[i] = null;
            }
        }

        for (int i=0; i<4; i++){
            if (manager.isLoaded(MENU_FILES[i])){
                menus[i] = manager.get(MENU_FILES[i], Texture.class);
                menus[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }else{
                menus[i] = null;
            }
        }

		// Allocate the font
		if (manager.isLoaded(FONT_FILE)) {
			displayFont = manager.get(FONT_FILE, BitmapFont.class);
		} else {
			displayFont = null;
		}

		// Load other assets
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
        for (int i=0; i<BKGD_FILES.size(); i++) {
            if (backgrounds[i] != null) {
                backgrounds[i] = null;
                manager.unload(BKGD_FILES.get(i));
            }
        }

        for (int i=0; i<4; i++){
            if (menus[i] != null){
                menus[i] = null;
                manager.unload(MENU_FILES[i]);
            }
        }

        if (displayFont != null) {
            displayFont = null;
            manager.unload(FONT_FILE);
        }

		// Load the other assets
		Board.UnloadContent(manager);
		Knight.UnloadContent(manager);
		Enemy.UnloadContent(manager);
		Ticker.UnloadContent(manager);
		DynamicTile.UnloadContent(manager);
		RhythmController.UnloadContent(manager);
	}

	// CONSTANTS

	// Ex: offset floats for strings we might display to screen like this:
	// /** Offset for the score counter message on the screen */
	// private static final float SCORE_OFFSET = 55.0f;

	/** Reference to drawing context to display graphics (VIEW CLASS) */
	private GameCanvas canvas;
	/** Reads input from keyboard or game pad (CONTROLLER CLASS) */
	private PlayerController playerController;
	/** Constructs the game models and handle basic gameplay (CONTROLLER CLASS) */
	private GameplayController gameplayController;

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
//        this.BKGD_FILES.clear();
//        for (int i=1; i<=this.numBackgrounds; i++){
//            this.BKGD_FILES.add("images/backgrounds/bg"+i+".png");
//        }

		this.canvas = canvas;
		active = false;
		// Null out all pointers, 0 out all ints, etc
		gameState = GameState.INTRO;

        tileTexture = new Texture(TILE_FILE);

        // Create the controllers.
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
	private boolean update(float delta) {
		switch (gameState) {
			case INTRO:
				gameState = GameState.PLAY;

                gameplayController.initialize(this.curLevel);
                this.backNum = JSONReader.getBackground();
                canvas.setOffsets(gameplayController.board.getWidth(), gameplayController.board.getHeight());
				//143.882f
                playerController.setListenForInput(true);
				break;
			case PLAY:
                if (playerController.getEscape()){
                    this.gameState = GameState.PAUSE;
                    bounds.clear();
                    canvas.setMenuConstants(2);
                    for (int i = 0; i < 2; i++) {
                        bounds.add(canvas.getButtonBounds(i));
                    }
                    playerController.setListenForInput(false);
                    playerController.setEscape(false);
                    break;
                }else{
                    Knight knight = (Knight) gameplayController.gameObjects.getPlayer();
                    if (gameplayController.isGameOver()) reset();
                    else if (!knight.isActive()) reset();
                    else if (gameplayController.board.isGoalTile((int) knight.getPosition().x, (int) knight.getPosition().y)) {
                        // Animate victory dance
                        playerController.setListenForInput(false);
                        gameplayController.hasMoved = false;
                        if (knight.notDancing) {
                            knight.setDancing();
                            knight.setInvulnerable(true);
                        }
                        play();
                        if (knight.doneDancing) {
                            gameState = GameState.WIN;
                            framesRemaining = DEFAULT_FRAMES;
                            bounds.clear();
                            canvas.setMenuConstants(3);
                            selection = 2;
                            for (int i = 0; i < 3; i++) {
                                bounds.add(canvas.getButtonBounds(i));
                            }
                            play();
                        }

                    } else play();
                    break;
                }
            case WIN:
                if ((this.curLevel+1) % (this.numLevels+1) == 0){
                    gameState = GameState.COMPLETE;
                }else {
                    framesRemaining -= 1;
                    if (framesRemaining <= 0){
                        this.curLevel ++;
                        gameState = GameState.INTRO;
                    }
                    Vector2 click = playerController.getClick();
                    if (click.x != -1) {
                        if (canvas.pointInBox((int) click.x, (int) click.y, 0)) {
                            //Replay level
                            gameState = GameState.INTRO;
                        } else if (canvas.pointInBox((int) click.x, (int) click.y, 2)) {
                            //next level
                            this.curLevel++;
                            gameState = GameState.INTRO;
                            RhythmController.stopMusic();
                        } else if (canvas.pointInBox((int) click.x, (int) click.y, 1)) {
                            //level select
                            listener.exitScreen(this, 1);
                            RhythmController.stopMusic();
                            return false;
                        }
                    }
                    char c = PlayerController.getKeyPush();
                    switch (c){
                        case 'L':
                            //since there are only two buttons, just switch between the two
                            selection -= 1;
                            selection += 3;
                            selection %= 3;
                            break;
                        case 'R':
                            selection += 1;
                            selection %= 3;
                            break;
                        case 'S':
                            //pressed the space bar so process whichever button this is
                            if (selection == 0){
                                //Replay level
                                gameState = GameState.INTRO;
                            }else if (selection == 1) {
                                //level select
                                listener.exitScreen(this, 1);
                                RhythmController.stopMusic();
                                return false;
                            }else{
                                //selection ==2
                                this.curLevel++;
                                gameState = GameState.INTRO;
                                RhythmController.stopMusic();
                            }
                            break;
                    }
                    Vector2 move = playerController.getMove();
                    if (move.x != -1){
                        //mouse was moved, process selection
                        if (canvas.pointInBox((int) move.x, (int) move.y, 0)) {
                            //Replay level
                            selection = 0;
                        } else if (canvas.pointInBox((int) move.x, (int) move.y, 1)) {
                            selection = 1;
                        }
                    }
                }
                break;
            case COMPLETE:
                //TODO: do something for winning the entire game
                //for now just go back to level select
                listener.exitScreen(this, 1);
                RhythmController.stopMusic();
                return false;
            case PAUSE:
                if (playerController.getEscape()){
                    //escape was pressed, resume game
                    this.gameState = GameState.PLAY;
                    playerController.setListenForInput(true);
                    playerController.setEscape(false);
                    break;
                }
                Vector2 click = playerController.getClick();
                if (click.x != -1) {
                    //clicked the screen, so process it
                    if (canvas.pointInBox((int) click.x, (int) click.y, 0)) {
                        //Replay level
                        gameState = GameState.INTRO;
                    } else if (canvas.pointInBox((int) click.x, (int) click.y, 1)) {
                        //level select
                        listener.exitScreen(this, 1);
                        RhythmController.stopMusic();
                        return false;
                    }
                }else{
                    //no click, check for button presses
                    char c = PlayerController.getKeyPush();
                    switch (c){
                        case 'L':
                            //since there are only two buttons, just switch between the two
                            selection += 1;
                            selection %= 2;
                            break;
                        case 'R':
                            selection += 1;
                            selection %= 2;
                            break;
                        case 'S':
                            //pressed the space bar so process whichever button this is
                            if (selection == 0){
                                //first box aka restart level
                                gameState = GameState.INTRO;
                            }else{
                                //selection == 1
                                //second box aka go to level select
                                listener.exitScreen(this, 1);
                                RhythmController.stopMusic();
                                return false;
                            }
                            break;
                    }
                    Vector2 move = playerController.getMove();
                    if (move.x != -1){
                        //mouse was moved, process selection
                        if (canvas.pointInBox((int) move.x, (int) move.y, 0)) {
                            //Replay level
                            selection = 0;
                        } else if (canvas.pointInBox((int) move.x, (int) move.y, 1)) {
                            selection = 1;
                        }
                    }

                }
			case LOSE:
				// Print level failed message!
				break;
		}
        return true;
	}

    public void setNumLevels(int a){
        this.numLevels = a;
    }

	/**
	 * This method processes a single step in the game loop.
	 */
	protected void play() {
		// NO DRAWING CODE HERE
		gameplayController.update();

	}

	/** This method resets the game */
	protected void reset() {
		gameState = GameState.INTRO;
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
		if (this.gameState == GameState.WIN){
            //draw level complete menu
            //TODO: make sure this background is a good one
            canvas.drawBackground(backgrounds[0], 1,1);

            String message = "NEXT LEVEL IN " + (framesRemaining/60+1);

            canvas.drawText(message, displayFont, canvas.getWidth()/2-displayFont.getBounds(message).width/2, canvas.getHeight()/2-displayFont.getBounds(message).height/2);
            float scale = (float)canvas.pauseMenuSize/(float)tileTexture.getHeight();
            int[] text = new int[]{0,1,2};
            for (int i=0; i<3; i++){
            //for (int i : new int[]{0,1,2}){
                //draw replay, select, then next
                Vector2 loc = new Vector2(bounds.get(i)[0], bounds.get(i)[1]);
                loc.y = canvas.getHeight() - loc.y - canvas.menuTileHeight;
                Color c;
                if (selection == i) {
                    c = new Color(.8f,.3f,.4f,1f);
                }else{
                    c = new Color(69f / 255f, 197f / 255f, 222f / 255f, 1f);
                }
                //canvas.draw(tileTexture, c, 0, 0, loc.x, loc.y, 0, scale, scale);
                canvas.draw(menus[text[i]], c, 0, 0, loc.x, loc.y, 0, scale, scale);
            }
        }else if (gameState == GameState.PAUSE) {
            //TODO: Make sure this is a good background (again)
            canvas.draw(backgrounds[0],1,1);
            float scale = (float)canvas.pauseMenuSize/(float)tileTexture.getHeight();
            int[] text = new int[]{3,1};
            for (int i=0; i<=1; i++){
            //for (int i : new int[]{3, 1}){
                //draw restart then select
                Vector2 loc = new Vector2(bounds.get(i)[0], bounds.get(i)[1]);
                loc.y = canvas.getHeight() - loc.y - canvas.menuTileHeight;
                Color c;
                if (selection == i) {
                    c = new Color(.8f,.3f,.4f,1f);
                }else{
                    c = new Color(69f / 255f, 197f / 255f, 222f / 255f, 1);
                }
                canvas.draw(menus[text[i]], c, 0, 0, loc.x, loc.y, 0, scale, scale);
            }
        }else{
                //draw the level
            canvas.draw(backgrounds[this.backNum - 1], 0, 0);
            gameplayController.board.draw(canvas);
            gameplayController.ticker.draw(canvas);
            gameplayController.gameObjects.draw(canvas);
            if (this.curLevel == 1){
                String message = "Tap an arrow key (or WASD) to move!";
                canvas.drawText(message, displayFont, canvas.getWidth()/2-displayFont.getBounds(message).width/2, canvas.getHeight()*3/4-displayFont.getBounds(message).height/2);
            }else if (this.curLevel == 7){
                String message = "Move into an enemy tile to attack!";
                canvas.drawText(message, displayFont, canvas.getWidth()/2-displayFont.getBounds(message).width/2, canvas.getHeight()*3/4-displayFont.getBounds(message).height/2);
            }
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
        if (gameState == GameState.WIN) {
            canvas.setMenuConstants(3);

            bounds.clear();
            for (int i = 0; i < 3; i++) {
                bounds.add(canvas.getButtonBounds(i));
            }
        }else if (gameState == GameState.PAUSE){
            canvas.setMenuConstants(2);

            bounds.clear();
            for (int i = 0; i < 2; i++) {
                bounds.add(canvas.getButtonBounds(i));
            }
        }
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
			if (update(delta)) {
                draw(delta);
            }
		}
	}

	/**
	 * Called when the Screen is paused.
	 *
	 * This is usually when it's not active or visible on screen. An Application is also paused before it is destroyed.
	 */
	public void pause() {}

	/**
	 * Called when the Screen is resumed from a paused state.
	 *
	 * This is usually when it regains focus.
	 */
	public void resume() {}

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
