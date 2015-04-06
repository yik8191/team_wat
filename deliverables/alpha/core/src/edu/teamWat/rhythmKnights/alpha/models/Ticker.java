package edu.teamWat.rhythmKnights.alpha.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.TimeUtils;
import edu.teamWat.rhythmKnights.alpha.controllers.RhythmController;
import edu.teamWat.rhythmKnights.alpha.utils.FilmStrip;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

import java.awt.*;
import java.sql.Time;


/** Ticker */ // lol
public class Ticker {

    public static final String BLANK_FILE = "images/tickerBlank.png";
    public static final String DASH_FILE = "images/tickerDash.png";
    public static final String FREEZE_FILE = "images/tickerFreeze.png";
    public static final String FIREBALL_FILE = "images/tickerFireball.png";
    public static final String INDICATOR_FILE = "images/tickerCurrent.png";
    public static Texture blankTexture;
    public static Texture dashTexture;
    public static Texture freezeTexture;
    public static Texture fireballTexture;
    public static Texture indicatorTexture;

	private TickerAction[] tickerActions;
	private int beat;

    //how spaced out ticker squares should be
    //TODO: THese should be changed when the screen is rescaled.
    //   this can be done by putting these constants inside GameCanvas
    //   and updating them in GameCanvas.setOffsets()
    private int SPACING;
    private int TICK_SQUARE_SIZE;
    private int INDICATOR_HEIGHT;
    private int INDICATOR_WIDTH;

	public Ticker(TickerAction[] actions) {
		tickerActions = actions;
		beat = 0;
	}

	public void reset(TickerAction[] actions) {
		beat = 0;
	}

	public void draw(GameCanvas canvas) {
        //float midX = canvas.getWidth()/2;)
        SPACING = canvas.TICKER_SPACING;
        TICK_SQUARE_SIZE = canvas.TICK_SQUARE_SIZE;
        INDICATOR_HEIGHT = canvas.INDICATOR_HEIGHT;
        INDICATOR_WIDTH = canvas.INDICATOR_WIDTH;
        float width = TICK_SQUARE_SIZE + SPACING;
        float startX = canvas.getWidth()/2 - (TICK_SQUARE_SIZE*tickerActions.length + SPACING*(tickerActions.length-1))/2;
        FilmStrip sprite;
        FilmStrip spriteIndicator;
        Vector2 loc = new Vector2(0,canvas.getHeight()-(TICK_SQUARE_SIZE + 70));
        for (int i=0; i < tickerActions.length; i++){

            if (tickerActions[i] == TickerAction.MOVE){
                sprite = new FilmStrip(blankTexture, 1, 1);
            }else if (tickerActions[i] == TickerAction.DASH){
                sprite = new FilmStrip(dashTexture, 1, 1);
            }else if (tickerActions[i] == TickerAction.FREEZE){
                sprite = new FilmStrip(freezeTexture, 1, 1);
            }else{ //fireball
                sprite = new FilmStrip(fireballTexture, 1, 1);
            }

            loc.x = startX + (width*i);

            canvas.draw(sprite, loc.x, loc.y, TICK_SQUARE_SIZE, TICK_SQUARE_SIZE);
            if (beat == i) {

	            float beatTime = RhythmController.toBeatTime(TimeUtils.millis());
	            loc.x = startX + ((RhythmController.getCurrentTime() % tickerActions.length - 0.5f) * width);
                // draw the indicator for current action
                spriteIndicator = new FilmStrip(indicatorTexture, 1, 1);
                canvas.draw(spriteIndicator, loc.x-TICK_SQUARE_SIZE*1.34f, loc.y-5, INDICATOR_WIDTH, INDICATOR_HEIGHT);
            }
        }
	}

	public TickerAction getAction() {
		return tickerActions[beat];
	}

	public void advance() {
		beat++;
		beat %= tickerActions.length;
	}

    /**
     * Preloads the assets for the Knight.
     *
     * The asset manager for LibGDX is asynchronous.  That means that
     * you tell it what to load and then wait while it
     * loads them.  This is the first step: telling it what to load.
     *
     * @param manager Reference to global asset manager.
     */
    public static void PreLoadContent(AssetManager manager) {
        manager.load(BLANK_FILE, Texture.class);
        manager.load(DASH_FILE, Texture.class);
        manager.load(FREEZE_FILE, Texture.class);
        manager.load(FIREBALL_FILE, Texture.class);
        manager.load(INDICATOR_FILE, Texture.class);
    }

    /**
     * Loads the assets for the Knight.
     *
     * All shell objects use one of two textures, so this is a static method.
     * This keeps us from loading the same images
     * multiple times for more than one Shell object.
     *
     * The asset manager for LibGDX is asynchronous.  That means that you tell it what to load and then wait while it
     * loads them.  This is the second step: extracting assets from the manager after it has finished loading them.
     *
     * @param manager Reference to global asset manager.
     */
    public static void LoadContent(AssetManager manager) {
        //load normal file
        if (manager.isLoaded(BLANK_FILE)) {
            blankTexture = manager.get(BLANK_FILE,Texture.class);
            blankTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            blankTexture = null;  // Failed to load
        }
        //load dash file
        if (manager.isLoaded(DASH_FILE)) {
            dashTexture = manager.get(DASH_FILE,Texture.class);
            dashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            dashTexture = null;  // Failed to load
        }
        //load freeze file
        if (manager.isLoaded(FREEZE_FILE)) {
            freezeTexture = manager.get(FREEZE_FILE,Texture.class);
            freezeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            freezeTexture = null;  // Failed to load
        }
        //load fireball file
        if (manager.isLoaded(FIREBALL_FILE)) {
            fireballTexture = manager.get(FIREBALL_FILE,Texture.class);
            fireballTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            fireballTexture = null;  // Failed to load
        }
        //load indicator file
        if (manager.isLoaded(INDICATOR_FILE)) {
            indicatorTexture = manager.get(INDICATOR_FILE, Texture.class);
            indicatorTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            indicatorTexture = null; // Failed to load
        }

    }

    /**
     * Unloads the assets for the Knight
     *
     * This method erases the static variables.  It also deletes the associated textures from the assert manager.
     *
     * @param manager Reference to global asset manager.
     */
    public static void UnloadContent(AssetManager manager) {
        if (blankTexture != null) {
            blankTexture = null;
            manager.unload(BLANK_FILE);
        }
        if (freezeTexture != null) {
            freezeTexture = null;
            manager.unload(FREEZE_FILE);
        }
        if (dashTexture != null) {
            dashTexture = null;
            manager.unload(DASH_FILE);
        }
        if (fireballTexture != null) {
            fireballTexture = null;
            manager.unload(FIREBALL_FILE);
        }
        if (indicatorTexture != null) {
            indicatorTexture = null;
            manager.unload(INDICATOR_FILE);
        }
    }

	public enum TickerAction {
		MOVE,
		DASH,
		FREEZE,
		FIREBALL
	}
}
