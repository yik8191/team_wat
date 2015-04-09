package edu.teamWat.rhythmKnights.alpha.models.gameObjects;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;

import edu.teamWat.rhythmKnights.alpha.controllers.RhythmController;
import edu.teamWat.rhythmKnights.alpha.models.Board;
import edu.teamWat.rhythmKnights.alpha.utils.*;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

/**
 * Knight class!
 * Fill in description here!
 * */
public class Knight extends GameObject {

    private KnightState state = KnightState.NORMAL;
    public static final String KNIGHT_DASH_FILE = "images/knightDash.png";
    public static final String KNIGHT_NORMAL_FILE = "images/glowing.png";
    public static final String KNIGHT_HP_FULL_FILE = "images/knightHpFull.png";
    public static final String KNIGHT_HP_EMPTY_FILE = "images/knightHpEmpty.png";
    public static final String KNIGHT_HP_ICON = "images/hpicon.png";
    public static Texture knightTexture;
    public static Texture knightDashTexture;
    public static Texture knightHpFullTexture;
    public static Texture knightHpEmptyTexture;
    public static Texture knightHpIconTexture;

    // Constants relating to Knight HP
    private static int HP_SIZE;
    protected int knightHP;
    protected int INITIAL_HP = 10;

    // Used for animating the knight
    private FilmStrip sprite;
    private FilmStrip spriteHP;
    // The number of frames before a sprite refreshes
    private int animDelay = 5;
    private int curTime = 5;
    private int curFrame = 0;

    // Constants for reference to the spritesheet
    private int IDLE_START = 0;
    private int IDLE_END = 4;
    private int HURT_START = 5;
    private int HURT_END = 9;
    private int SUCCESS_START = 0;
    private int SUCCESS_END = 0;
    private int SPRITE_ROWS = 2;
    private int SPRITE_COLS = 5;
    private int SPRITE_TOT = 10;

	private boolean isInvulnerable;

    public Knight(int id, float x, float y){
        this.id = id;
        this.position = new Vector2(x,y);
	    this.animatedPosition.set(position);
	    this.oldPosition.set(position);
        this.isAlive = true;
        this.isActive = true;
        this.knightHP = INITIAL_HP;
	    this.animFrames = 3;

        // Set current knight image
        sprite = new FilmStrip(knightTexture, SPRITE_ROWS, SPRITE_COLS, SPRITE_TOT);
        sprite.setFrame(0);
        this.isCharacter = true;
    }

    public void update() {
        // If we are dead do nothing.
        if (!isAlive) {
            return;
        }

	    if (moved) {
		    animAge++;
		    if (animAge == animFrames) {
			    animAge = 0;
			    animatedPosition.set(position);
			    oldPosition.set(position);
			    moved = false;
		    } else {
			    animatedPosition.set(position).sub(oldPosition).scl((float)animAge / animFrames).add(oldPosition);
		    }
	    }
        //TODO: implement this
    }

    public void setState(KnightState ks){
        this.state = ks;
    }

    public KnightState getState(){
        return this.state;
    }


    public void draw(GameCanvas canvas) {
        // Animation code for knight
        if (this.state == KnightState.NORMAL) {
            curTime --;
            if (curTime == 0) {
                curFrame ++;
                if (curFrame >= IDLE_END) {
                    curFrame = IDLE_START;
                }
                curTime = animDelay;
            } else {
                sprite.setFrame(curFrame);
            }
        } else if (this.state == KnightState.TAKINGDMG) {
            curTime --;
            if (curTime == 0) {
                curFrame ++;
                // Finished animating the "taking damage" frames
                if (curFrame >= HURT_END) {
                    curFrame = IDLE_START;
                    this.setState(KnightState.NORMAL);
                }
                curTime = animDelay;
            } else {
                sprite.setFrame(curFrame);
            }
        // Should not ever occur
        } else if (this.state == KnightState.MOVING) {
            curTime--;
            if (curTime == 0) {
                curFrame++;
                // Finished animating the success frames
                if (curFrame >= SUCCESS_END) {
                    curFrame = IDLE_START;
                    this.setState(KnightState.NORMAL);
                }
                curTime = animDelay;
            } else {
                sprite.setFrame(curFrame);
            }
        } else {
            sprite.setFrame(0);
        }
	    Vector2 loc = canvas.boardToScreen(animatedPosition.x, animatedPosition.y);
        canvas.draw(sprite, loc.x, loc.y, canvas.tileSize, canvas.tileSize);

        // Drawing the hp icon
        FilmStrip spriteHpIcon = new FilmStrip(knightHpIconTexture, 1, 1);
        canvas.draw(spriteHpIcon, (int) (HP_SIZE - HP_SIZE/1.5f), canvas.getHeight() - 11.9f*HP_SIZE, HP_SIZE, HP_SIZE);

        // Drawing code for the Knight HP
        HP_SIZE = canvas.HP_SIZE;
        // Draw remaining hearts
        if (this.knightHP == 0) {
            spriteHP = new FilmStrip(knightHpEmptyTexture, 1, 1);
            for (int j = 0; j < (INITIAL_HP - this.knightHP); j++) {
                canvas.draw(spriteHP, (int) (HP_SIZE + j* HP_SIZE/1.5f), canvas.getHeight() - 11.9f*HP_SIZE, (int) (HP_SIZE/1.3), HP_SIZE);
            }
        }

        spriteHP = new FilmStrip(knightHpFullTexture, 1, 1);
        for (int i = 0; i < this.knightHP; i++) {
            canvas.draw(spriteHP, (int) (HP_SIZE + i*HP_SIZE/1.5f), canvas.getHeight() - 11.9f*HP_SIZE, (int) (HP_SIZE/1.3), HP_SIZE);
            if (i == this.knightHP - 1) {
                spriteHP = new FilmStrip(knightHpEmptyTexture, 1, 1);
                for (int j = 0; j < (INITIAL_HP - this.knightHP); j++) {
                    canvas.draw(spriteHP, (int) (HP_SIZE + (j+1+i)*HP_SIZE/1.5), canvas.getHeight() - 11.9f*HP_SIZE, (int) (HP_SIZE/1.3), HP_SIZE);
                }
            }
        }
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
        manager.load(KNIGHT_NORMAL_FILE, Texture.class);
        manager.load(KNIGHT_DASH_FILE, Texture.class);
        manager.load(KNIGHT_HP_FULL_FILE, Texture.class);
        manager.load(KNIGHT_HP_EMPTY_FILE, Texture.class);
        manager.load(KNIGHT_HP_ICON, Texture.class);
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
        if (manager.isLoaded(KNIGHT_NORMAL_FILE)) {
            knightTexture = manager.get(KNIGHT_NORMAL_FILE,Texture.class);
            knightTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            knightTexture = null;  // Failed to load
        }
        //load dash file
        if (manager.isLoaded(KNIGHT_DASH_FILE)) {
            knightDashTexture = manager.get(KNIGHT_DASH_FILE,Texture.class);
            knightDashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            knightDashTexture = null;  // Failed to load
        }

        // load full heart
        if (manager.isLoaded(KNIGHT_HP_FULL_FILE)) {
            knightHpFullTexture = manager.get(KNIGHT_HP_FULL_FILE, Texture.class);
            knightHpFullTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            knightHpFullTexture = null; //Failed to load
        }

        // load empty heart
        if (manager.isLoaded(KNIGHT_HP_EMPTY_FILE)) {
            knightHpEmptyTexture = manager.get(KNIGHT_HP_EMPTY_FILE, Texture.class);
            knightHpEmptyTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            knightHpEmptyTexture = null; //Failed to load
        }

        // load hp icon
        if (manager.isLoaded(KNIGHT_HP_ICON)) {
            knightHpIconTexture = manager.get(KNIGHT_HP_ICON, Texture.class);
            knightHpIconTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            knightHpIconTexture = null; //Failed to load
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
        if (knightTexture != null) {
            knightTexture = null;
            manager.unload(KNIGHT_NORMAL_FILE);
        }
        if (knightDashTexture != null) {
            knightDashTexture = null;
            manager.unload(KNIGHT_DASH_FILE);
        }
        if (knightHpFullTexture != null) {
            knightHpFullTexture = null;
            manager.unload(KNIGHT_HP_FULL_FILE);
        }
        if (knightHpEmptyTexture != null) {
            knightHpEmptyTexture = null;
            manager.unload(KNIGHT_HP_EMPTY_FILE);
        }
        if (knightHpIconTexture != null) {
            knightHpIconTexture = null;
            manager.unload(KNIGHT_HP_ICON);
        }
    }

    /**
     * Decrements the player health by 1
     *
     * GameplayController will handle invulnerability
     */
	public void takeDamage() {
        this.knightHP -= 3;
        if (this.knightHP <= 0) {
            this.isAlive = false;
        }
        curTime = animDelay;
        curFrame = HURT_START;
        this.state = KnightState.TAKINGDMG;
        RhythmController.playDamage();
	}

    public void showSuccess() {
        if (this.knightHP < INITIAL_HP) {
            this.knightHP ++;
        }
        curFrame = SUCCESS_START;
        this.state = KnightState.MOVING;
    }

	public boolean isInvulnerable() {return isInvulnerable;}

	public void setInvulnerable(boolean invulnerable) {this.isInvulnerable = invulnerable;}

    // Currently being used for animation of the knight
    public enum KnightState {
        /** Draw the knight normally */
        NORMAL,
        /** Knight is dashing */
        DASHING,
        /** Knight is attacking */
        ATTACKING,
        /** Knight is using freeze spell */
        FREEZING,
        /** Knight is taking damage */
        TAKINGDMG,
        /** Knight has successfully taken an action */
        MOVING
    }

}