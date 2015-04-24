package edu.teamWat.rhythmKnights.alpha.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.TimeUtils;
import edu.teamWat.rhythmKnights.alpha.controllers.RhythmController;
import edu.teamWat.rhythmKnights.alpha.utils.FilmStrip;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.awt.*;
import java.sql.Time;


/** Ticker */ // lol
public class Ticker {

    public static final String BLANK_FILE = "images/tickerBlankSheet.png";
    public static final String DASH_FILE = "images/tickerDashSheet.png";
    public static final String FREEZE_FILE = "images/tickerFreeze.png";
    public static final String FIREBALL_FILE = "images/tickerFireball.png";
    public static final String INDICATOR_FILE = "images/tickerCurrent.png";
    public static Texture blankTexture;
    public static Texture dashTexture;
    public static Texture freezeTexture;
    public static Texture fireballTexture;
    public static Texture indicatorTexture;

    public static FilmStrip moveSprite;
    public static FilmStrip dashSprite;
    public static FilmStrip fireballSprite;
    public static FilmStrip freezeSprite;
    public static FilmStrip indicatorSprite;

	public TickerAction[] tickerActions;
    private int[] glowFrame;
	private int beat;
    public int numExpandedActions;
    public float indicatorOffsetRatio;

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
        glowFrame = new int[actions.length];
		beat = 0;
        numExpandedActions = 0;
        for (TickerAction action : tickerActions) {
            if (action == TickerAction.DASH || action == TickerAction.FIREBALL) {
                numExpandedActions += 2;
            } else {
                numExpandedActions += 1;
            }
        }
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
        float startX = canvas.getWidth() / 2 - (TICK_SQUARE_SIZE * tickerActions.length + SPACING * (tickerActions.length - 1)) / 2;
        FilmStrip sprite;
        FilmStrip spriteIndicator;
        Vector2 loc = new Vector2(0, canvas.getHeight() - (TICK_SQUARE_SIZE + 70));

//        for (int i = 0; i < tickerActions.length; i++) {
//
//            if (tickerActions[i] == TickerAction.MOVE) {
//                sprite = moveSprite;
//            } else if (tickerActions[i] == TickerAction.DASH) {
//                sprite = dashSprite;
//            } else if (tickerActions[i] == TickerAction.FREEZE) {
//                sprite = freezeSprite;
//            } else { //fireball
//                sprite = fireballSprite;
//            }
//
//            loc.x = startX + (width * i);
//
////            canvas.draw(sprite, loc.x, loc.y, TICK_SQUARE_SIZE, TICK_SQUARE_SIZE);
//            canvas.draw(sprite, com.badlogic.gdx.graphics.Color.WHITE, 0, 0, loc.x, loc.y, 0, 1f, 1f);
//        }

        for (int i = 0; i < tickerActions.length; i++) {

            if (tickerActions[i] == TickerAction.MOVE) {
                sprite = moveSprite;
            } else if (tickerActions[i] == TickerAction.DASH) {
                sprite = dashSprite;
            } else if (tickerActions[i] == TickerAction.FREEZE) {
                sprite = freezeSprite;
            } else { //fireball
                sprite = fireballSprite;
            }

            loc.x = startX + (width * i);
            float oldx = loc.x;

            sprite.setFrame(0);
            canvas.draw(sprite, loc.x, loc.y + glowFrame[i], TICK_SQUARE_SIZE, TICK_SQUARE_SIZE);
            if (glowFrame[i] > 0) glowFrame[i]--;
            if (beat == i) {

	            float beatTime = 0;// RhythmController.toBeatTime(TimeUtils.millis());
	            loc.x += indicatorOffsetRatio*width;

                if (loc.x >= oldx) {
                    // System.out.println("hi");
                    sprite.setFrame(1);
                    canvas.draw(sprite, oldx, loc.y + glowFrame[i], TICK_SQUARE_SIZE, TICK_SQUARE_SIZE);
                }

                // draw the indicator for current action
                canvas.draw(indicatorSprite, loc.x, loc.y-5, INDICATOR_WIDTH, INDICATOR_HEIGHT);
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
            moveSprite = new FilmStrip(blankTexture, 1, 2);
        } else {
            blankTexture = null;  // Failed to load
        }
        //load dash file
        if (manager.isLoaded(DASH_FILE)) {
            dashTexture = manager.get(DASH_FILE,Texture.class);
            dashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            dashSprite = new FilmStrip(dashTexture, 1, 2);
        } else {
            dashTexture = null;  // Failed to load
        }
        //load freeze file
        if (manager.isLoaded(FREEZE_FILE)) {
            freezeTexture = manager.get(FREEZE_FILE,Texture.class);
            freezeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            freezeSprite = new FilmStrip(freezeTexture, 1, 1);
        } else {
            freezeTexture = null;  // Failed to load
        }
        //load fireball file
        if (manager.isLoaded(FIREBALL_FILE)) {
            fireballTexture = manager.get(FIREBALL_FILE,Texture.class);
            fireballTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            fireballSprite = new FilmStrip(fireballTexture, 1, 1);
        } else {
            fireballTexture = null;  // Failed to load
        }
        //load indicator file
        if (manager.isLoaded(INDICATOR_FILE)) {
            indicatorTexture = manager.get(INDICATOR_FILE, Texture.class);
            indicatorTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            indicatorSprite = new FilmStrip(indicatorTexture, 1, 1);
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

    public void glowBeat(int beatNumber, int intensity) {
        glowFrame[beatNumber] = intensity;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

	public enum TickerAction {
		MOVE,
		DASH,
        DASH2,
		FREEZE,
		FIREBALL,
        FIREBALL2
	}
}
