package edu.teamWat.rhythmKnights.alpha.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import com.sun.corba.se.impl.oa.toa.TOA;
// import com.sun.deploy.panel.ITreeNode;
import edu.teamWat.rhythmKnights.alpha.controllers.RhythmController;
import edu.teamWat.rhythmKnights.alpha.utils.FilmStrip;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;
import sun.util.resources.cldr.lag.LocaleNames_lag;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.util.ArrayList;

/** Ticker */ // lol
public class Ticker {

    public static final String BLANK_FILE = "images/ticker/tickerBlankSheet.png";
    public static final String DASH_FILE = "images/ticker/tickerDashSheet.png";
    public static final String FREEZE_FILE = "images/ticker/tickerFreeze.png";
    public static final String FIREBALL_FILE = "images/ticker/tickerFireball.png";
    public static final String INDICATOR_FILE = "images/ticker/tickerCurrent.png";
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
    public TickerAction[] expandedTickerActions;
    private int[] glowFrame;
    private int[] expandedIndex;
	private int beat;
    /** Period of one measure in beats */
    public long period;
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
		beat = 0;
        numExpandedActions = 0;
        for (TickerAction action : tickerActions) {
            if (action == TickerAction.DASH || action == TickerAction.FIREBALL) {
                numExpandedActions += 2;
            } else {
                numExpandedActions += 1;
            }
        }
        expandedTickerActions = new TickerAction[numExpandedActions];
        expandedIndex = new int[numExpandedActions];
        int count = 0;
        for (int i = 0; i < tickerActions.length; i++) {
            expandedIndex[i] = count;
            switch (tickerActions[i]) {
                case FIREBALL:
                    expandedTickerActions[count] = TickerAction.FIREBALL;
                    count++;
                    expandedTickerActions[count] = TickerAction.FIREBALL2;
                    count++;
                    break;
                case DASH:
                    expandedTickerActions[count] = TickerAction.DASH;
                    count++;
                    expandedTickerActions[count] = TickerAction.DASH2;
                    count++;
                    break;
                case FREEZE:
                    expandedTickerActions[count] = TickerAction.MOVE;
                    count++;
                    break;
                case MOVE:
                    expandedTickerActions[count] = TickerAction.FREEZE;
                    count++;
                    break;
                default:
                    expandedTickerActions[count] = TickerAction.MOVE;
                    count++;
                    break;
            }
        }
        glowFrame = new int[numExpandedActions];
	}

	public void reset(TickerAction[] actions) {
		beat = 0;
	}

	public void draw(GameCanvas canvas) {
        //float midX = canvas.getWidth()/2;)
        TICK_SQUARE_SIZE = canvas.TICK_SQUARE_SIZE;
        SPACING = TICK_SQUARE_SIZE / 2;
        INDICATOR_HEIGHT = canvas.INDICATOR_HEIGHT;
        INDICATOR_WIDTH = canvas.INDICATOR_WIDTH;
        float width = TICK_SQUARE_SIZE + SPACING;
        float startX = canvas.getWidth() / 2 - (TICK_SQUARE_SIZE * tickerActions.length + SPACING * (tickerActions.length - 1)) / 2;
        FilmStrip sprite;
        FilmStrip spriteIndicator;
        Vector2 loc = new Vector2(0, canvas.getHeight() - (TICK_SQUARE_SIZE * 0.5f + 70));

        long currentTimeinMeasure = RhythmController.getSequencePosition() % period;

        float totalWidth = (TICK_SQUARE_SIZE + SPACING) * tickerActions.length;

        float indicatorX = totalWidth * (float)currentTimeinMeasure / (float)period;
        float indicatorY = loc.y;

        float tickerScaling = (float)TICK_SQUARE_SIZE / (float)moveSprite.getRegionWidth();
        float indicatorScaling = (float)INDICATOR_WIDTH / (float)indicatorSprite.getRegionWidth();

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

            if (tickerActions[i] == TickerAction.DASH || tickerActions[i] == TickerAction.FIREBALL) {
                float rat = ((float)i / (float)tickerActions.length);
                float xPos = rat * totalWidth;
                if (Math.abs(indicatorX - xPos) < TICK_SQUARE_SIZE / 2 || Math.abs(indicatorX - xPos - totalWidth) < TICK_SQUARE_SIZE / 2) sprite.setFrame(1);
                else sprite.setFrame(0);
                canvas.draw(sprite, Color.WHITE, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, startX + xPos, loc.y, 0, tickerScaling, tickerScaling);
                if (glowFrame[expandedIndex[i]] > 0) {
                    Color tint = new Color(Color.WHITE);
                    tint.a = (float)glowFrame[expandedIndex[i]] / 10.0f;
                    canvas.draw(sprite, tint, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, startX + xPos, loc.y, 0, tickerScaling * ((10.0f - (float)glowFrame[expandedIndex[i]]) / 10.0f + 1.0f), tickerScaling * ((10.0f - (float)glowFrame[expandedIndex[i]]) / 10.0f + 1.0f));
                    glowFrame[expandedIndex[i]]--;
                }
//                canvas.draw(sprite, xPos + startX, loc.y, TICK_SQUARE_SIZE, TICK_SQUARE_SIZE);
                rat = ((i + 0.5f) / (float)tickerActions.length);
                float xPos2 = rat * totalWidth;
                if (Math.abs(indicatorX - xPos2) < TICK_SQUARE_SIZE / 2 || Math.abs(indicatorX - xPos2 - totalWidth) < TICK_SQUARE_SIZE / 2) sprite.setFrame(1);
                else sprite.setFrame(0);
                canvas.draw(sprite, Color.WHITE, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, startX + xPos2, loc.y + TICK_SQUARE_SIZE / 4.0f, 0, tickerScaling, tickerScaling);
                if (glowFrame[expandedIndex[i]+1] > 0) {
                    Color tint = new Color(Color.WHITE);
                    tint.a = (float)glowFrame[expandedIndex[i] + 1] / 10.0f;
                    canvas.draw(sprite, tint, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, startX + xPos2, loc.y + TICK_SQUARE_SIZE / 4.0f, 0, tickerScaling * ((10.0f - (float)glowFrame[expandedIndex[i] + 1]) / 10.0f + 1.0f), tickerScaling * ((10.0f - (float)glowFrame[expandedIndex[i]+1]) / 10.0f + 1.0f));
                    glowFrame[expandedIndex[i] + 1]--;
                }
//                canvas.draw(sprite, xPos + startX, loc.y, TICK_SQUARE_SIZE, TICK_SQUARE_SIZE);
                if (indicatorX > xPos && indicatorX < ((i + 1) / (float)tickerActions.length) * totalWidth) {
                    if (indicatorX < xPos2) {
                        indicatorY += (indicatorX - xPos) / (xPos2 - xPos) * INDICATOR_HEIGHT / 4.0f;
//                        System.out.println("Less: " + (indicatorX - xPos) / (xPos2 - xPos));
                    } else {
                        indicatorY += (1 - (indicatorX - xPos2) / (xPos2 - xPos)) * INDICATOR_HEIGHT / 4.0f;
//                        System.out.println("Greater: " +  (1-(indicatorX - xPos2) / (xPos2 - xPos)));
                    }
                }
            } else {
                float rat = ((float)i / (float)tickerActions.length);
                float xPos = rat * totalWidth;
                if (Math.abs(indicatorX - xPos) < TICK_SQUARE_SIZE / 2 || Math.abs(indicatorX - xPos - totalWidth) < TICK_SQUARE_SIZE / 2) sprite.setFrame(1);
                else sprite.setFrame(0);
                canvas.draw(sprite, Color.WHITE, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, xPos + startX, loc.y, 0, tickerScaling, tickerScaling);
                if (glowFrame[expandedIndex[i]] > 0) {
                    Color tint = new Color(Color.WHITE);
                    tint.a = (float)glowFrame[expandedIndex[i]] / 10.0f;
                    canvas.draw(sprite, tint, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, startX + xPos, loc.y, 0, tickerScaling * ((10.0f - (float)glowFrame[expandedIndex[i]]) / 10.0f + 1.0f), tickerScaling * ((10.0f - (float)glowFrame[expandedIndex[i]]) / 10.0f + 1.0f));
                    glowFrame[expandedIndex[i]]--;
                }
//                canvas.draw(sprite, xPos + startX, loc.y, TICK_SQUARE_SIZE, TICK_SQUARE_SIZE);
            }
        }

        if (indicatorX > (totalWidth - totalWidth / (float)tickerActions.length / 2.0f)) {
            Color tint = new Color(Color.WHITE);
            tint.a = (totalWidth - indicatorX) / (totalWidth / (float)tickerActions.length / 2.0f);
            canvas.draw(indicatorSprite, tint, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, startX + indicatorX, indicatorY, 0, indicatorScaling, indicatorScaling);
            tint.a = 1.0f - tint.a;
            canvas.draw(indicatorSprite, tint, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, startX + indicatorX - totalWidth, loc.y, 0, indicatorScaling, indicatorScaling);
        } else {
            canvas.draw(indicatorSprite, Color.WHITE, TICK_SQUARE_SIZE / 2, TICK_SQUARE_SIZE / 2, startX + indicatorX, indicatorY, 0, indicatorScaling, indicatorScaling);
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
