package edu.teamWat.rhythmKnights.alpha.models.gameObjects;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;

import edu.teamWat.rhythmKnights.alpha.controllers.RhythmController;
import edu.teamWat.rhythmKnights.alpha.models.Board;
import edu.teamWat.rhythmKnights.alpha.utils.*;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

/**
 * Knight class! Fill in description here!
 */
public class Knight extends GameObject {

    private KnightState state = KnightState.NORMAL;
    private KnightDirection facing = KnightDirection.FRONT;
    private int facingFact = 0;
    public static final String KNIGHT_DASH_FILE = "images/spriteSheets/knightDash.png";
    public static final String KNIGHT_NORMAL_FILE = "images/spriteSheets/knightsheet.png";
    public static final String KNIGHT_HP_FULL_FILE = "images/knightHpFullSheet.png";
    public static final String KNIGHT_HP_EMPTY_FILE = "images/knightHpEmpty.png";
    public static final String KNIGHT_HP_ICON = "images/hpicon.png";
    public static final String LIGHT_SPLASH_FILE = "images/tiles/lightSplashSheet.png";
    public static Texture knightTexture;
    public static Texture knightDashTexture;
    public static Texture knightHpFullTexture;
    public static Texture knightHpEmptyTexture;
    public static Texture knightHpIconTexture;
    public static Texture lightSplashTexture;

    // Constants relating to Knight HP
    private static int HP_SIZE;
    protected int knightHP;
    protected int INITIAL_HP = 160;
    protected int HP_KILL_BOOST = 50;
    private static int  hpDamageAmount = 5;

    // Used for animating the knight
    private FilmStrip sprite;
    private FilmStrip spriteHP;
    private FilmStrip spriteDash;
    private FilmStrip spriteLight;
    // The number of frames before a sprite refreshes
    private int animDelay = 5;
    private int animDelayShort = 2;
    private int curTime = 5;
    private int curFrame = 0;

    // Used for animating the knight's dash afterimages
    private int curFrameDash = 0;
    private Vector2 oldLoc;

    // Constants for reference to the spritesheet
    // All loops take 5 frames
    private int IDLE_START = 0;
    private int IDLE_END = IDLE_START + 4;
    private int HURT_START = 5;
    private int HURT_END = HURT_START + 4;
    // Currently placeholders
    private int SUCCESS_START = 0;
    private int SUCCESS_END = 0;
    // Directional sprites
    private int IDLE_UP_START = 10;
    private int IDLE_UP_END = IDLE_UP_START + 4;
    private int HURT_UP_START = 15;
    private int HURT_UP_END = HURT_UP_START + 4;

    private int IDLE_LEFT_START = 20;
    private int IDLE_LEFT_END = IDLE_LEFT_START + 4;
    private int HURT_LEFT_START = 25;
    private int HURT_LEFT_END = HURT_LEFT_START + 4;

    private int IDLE_RIGHT_START = 30;
    private int IDLE_RIGHT_END = IDLE_RIGHT_START + 4;
    private int HURT_RIGHT_START = 35;
    private int HURT_RIGHT_END = HURT_RIGHT_START + 4;

    private int DEAD_START = 40;
    private int DEAD_UP_START = 45;
    private int DEAD_LEFT_START = 50;
    private int DEAD_RIGHT_START = 55;

    private int DASH_START = 60;
    private int DASH_UP_START = 65;
    private int DASH_LEFT_START = 70;
    private int DASH_RIGHT_START = 75;

    private int FALL_START = 80;
    private int FALL_UP_START = 85;
    private int FALL_LEFT_START = 90;
    private int FALL_RIGHT_START = 95;

    private int SWORD_START = 100;
    private int SWORD_UP_START = 105;
    private int SWORD_LEFT_START = 110;
    private int SWORD_RIGHT_START = 115;

    private int DANCE_START = 120;

    // Sheet constants
    private int SPRITE_ROWS = 26;
    private int SPRITE_COLS = 5;
    private int SPRITE_TOT = 130;

    // Light Splash Sheet
    private int L_ROWS = 1;
    private int L_COLS = 12;
    private int L_TOT = 12;
    private int curFrameL = 0;
    private int curTimeL = animDelayShort;

    private boolean isInvulnerable;
    private boolean isDashing = false;
    public boolean doneDancing = false;
    public boolean notDancing = true;
    private boolean doneFirstHalf = false;
    private boolean reverse = false;

    public Knight(int id, float x, float y) {
        this.id = id;
        this.position = new Vector2(x, y);
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

        // Used for animating the knight's dash afterimages
        spriteDash = new FilmStrip(knightTexture, SPRITE_ROWS, SPRITE_COLS, SPRITE_TOT);

        // Used for animating the visual feedback light splash
        spriteLight = new FilmStrip(lightSplashTexture, L_ROWS, L_COLS, L_TOT);
        spriteLight.setFrame(11);
    }

    public void update() {
        // If we are dead do nothing.
        if (!isActive) {
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
    }

    public void setState(KnightState ks) {
        this.state = ks;
    }

    public KnightState getState() {
        return this.state;
    }

    public void setDirection(KnightDirection d) {
        this.facing = d;
        switch (d) {
            case FRONT:
                this.facingFact = 0;
                break;
            case BACK:
                this.facingFact = 2;
                break;
            case LEFT:
                this.facingFact = 4;
                break;
            case RIGHT:
                this.facingFact = 6;
                break;
        }
    }

    public KnightDirection getDirection() {
        return this.facing;
    }

    public void draw(GameCanvas canvas) {
        // Animation code for knight
        if (this.state == KnightState.NORMAL) {
            curTime--;
            if (curTime == 0) {
                curFrame++;
                if (curFrame >= ((this.facingFact * 5) + 4)) {
                    curFrame = this.facingFact * 5;
                }
                curTime = animDelay;
            } else {
                sprite.setFrame(curFrame);
            }
        } else if (this.state == KnightState.TAKINGDMG) {
            curTime--;
            if (curTime == 0) {
                curFrame++;
                // Finished animating the "taking damage" frames
                if (curFrame >= ((this.facingFact + 1) * 5 + 4)) {
                    curFrame = (this.facingFact + 1) * 5;
                    this.setState(KnightState.NORMAL);
                }
                curTime = animDelay;
            } else {
                sprite.setFrame(curFrame);
            }
            // Used for changing sprite direction
        } else if (this.state == KnightState.MOVING) {
            curTime--;
            if (curTime == 0) {
                curFrame++;
                // Finished animating the success frame
                if (curFrame >= (this.facingFact * 5 + 4)) {
                    curFrame = this.facingFact * 5;
                    this.setState(KnightState.NORMAL);
                }
                curTime = animDelay;
            } else {
                sprite.setFrame(curFrame);
            }
        } else if (this.state == KnightState.ATTACKING) {
            curTime--;
            if (curTime == 0) {
                curFrame++;
                // Finished animating the attack frames
                if (curFrame >= (this.facingFact / 2 * 5 + 104)) {
                    curFrame = this.facingFact * 5;
                    this.setState(KnightState.NORMAL);
                }
                curTime = animDelayShort;
            } else {
                sprite.setFrame(curFrame);
            }
        } else if (this.state == KnightState.DEAD) {
            curTime--;
            if (curTime == 0) {
                curFrame++;
                // Finished animating the death frames
                if (curFrame >= (this.facingFact / 2 * 5 + 44)) {
                    this.setActive(false);
                }
                curTime = animDelay;
            } else {
                sprite.setFrame(curFrame);
            }
        } else if (this.state == KnightState.FALLING) {
            curTime--;
            if (curTime == 0) {
                curFrame++;
                // Finished animating the death frames
                if (curFrame >= (this.facingFact / 2 * 5 + 84)) {
                    this.setState(KnightState.DEAD);
                    switch (this.facing) {
                        case FRONT:
                            curFrame = DEAD_START;
                            break;
                        case BACK:
                            curFrame = DEAD_UP_START;
                            break;
                        case LEFT:
                            curFrame = DEAD_LEFT_START;
                            break;
                        case RIGHT:
                            curFrame = DEAD_RIGHT_START;
                            break;
                    }
                }
                curTime = animDelay;
            } else {
                sprite.setFrame(curFrame);
            }
        } else if (this.state == KnightState.DANCING) {
            curTime--;
            if (curTime == 0) {
                if (reverse) {
                    curFrame--;
                } else {
                    curFrame++;
                }
                // the animation loop is finished
                if (curFrame > 124 && !doneFirstHalf) {
                    reverse = true;
                } else if (curFrame < 118) {
                    doneFirstHalf = true;
                }
                if (doneFirstHalf) {
                    reverse = false;
                    if (curFrame > 129) {
                        this.doneDancing = true;
                        this.setState(KnightState.NORMAL);
                        this.doneFirstHalf = false;
                    }
                }
                curTime = animDelayShort+1;
            } else {
                if (!(curFrame > 129)) {
                    sprite.setFrame(curFrame);
                }
            }
        } else {
            sprite.setFrame(0);
        }

        Vector2 loc = canvas.boardToScreen(animatedPosition.x, animatedPosition.y);
//        Vector2 loc = canvas.boardToScreen(position.x, position.y);
//        // Animate afterimages for dashing
//        if (this.isDashing) {
//            oldLoc = loc;
//            curTime--;
//            if (curTime == 0) {
//                curFrameDash++;
//                curTime = animDelay;
//            } else {
//
//            }
//            canvas.draw(spriteDash, oldLoc.x, oldLoc.y, canvas.tileSize, canvas.tileSize);
//        }

        // Draw light splash visual feedback
        if (this.state == KnightState.MOVING) {
            animateSplash();
            canvas.draw(spriteLight, loc.x - canvas.tileSize, loc.y - canvas.tileSize, canvas.tileSize*3, canvas.tileSize*3);
        }

        // Draw main knight
        // Position offsets for moving "halfway" when attacking an enemy
        if (this.getState() == KnightState.ATTACKING) {
            switch(this.facing) {
                case FRONT:
                    canvas.draw(sprite, loc.x, loc.y - canvas.tileSize/2, canvas.tileSize, canvas.tileSize);
                    break;
                case BACK:
                    canvas.draw(sprite, loc.x, loc.y + canvas.tileSize/2, canvas.tileSize, canvas.tileSize);
                    break;
                case LEFT:
                    canvas.draw(sprite, loc.x - canvas.tileSize/2, loc.y, canvas.tileSize, canvas.tileSize);
                    break;
                case RIGHT:
                    canvas.draw(sprite, loc.x + canvas.tileSize/2, loc.y, canvas.tileSize, canvas.tileSize);
                    break;
            }
        }
        else canvas.draw(sprite, loc.x, loc.y, canvas.tileSize, canvas.tileSize);


        // Drawing code for the Knight HP
        HP_SIZE = (int)((float)canvas.HP_SIZE / 1.5f);
        int barWidth = HP_SIZE / 5;
        // Draw remaining hearts
        if (this.knightHP == 0) {
            spriteHP = new FilmStrip(knightHpEmptyTexture, 1, 1);
            for (int j = 0; j < (INITIAL_HP - this.knightHP); j++) {
                canvas.draw(spriteHP, (j + 1) * HP_SIZE / 7f, 0, barWidth, HP_SIZE);
            }
        }

        spriteHP = new FilmStrip(knightHpFullTexture, 3, 1, 3);
        for (int i = 0; i < this.knightHP; i++) {

            // For drawing different colored segments
//            if (((float) i)/INITIAL_HP >= 0.5) spriteHP.setFrame(0);
//            else if ((float) i/INITIAL_HP < 0.5 && ((float) i)/INITIAL_HP >= 0.2) spriteHP.setFrame(1);
//            else spriteHP.setFrame(2);

            // For changing color as HP decreases
            if (((float) knightHP)/INITIAL_HP >= 0.5) spriteHP.setFrame(0);
            else if ((float) knightHP/INITIAL_HP < 0.5 && ((float) knightHP)/INITIAL_HP >= 0.2) spriteHP.setFrame(1);
            else spriteHP.setFrame(2);

            canvas.draw(spriteHP, (i + 1) * HP_SIZE / 7f, 0, barWidth, HP_SIZE);
            if (i == this.knightHP - 1) {
                spriteHP = new FilmStrip(knightHpEmptyTexture, 1, 1);
                for (int j = 0; j < (INITIAL_HP - this.knightHP); j++) {
                    canvas.draw(spriteHP, (i + j + 2) * HP_SIZE / 7f, 0, barWidth, HP_SIZE);
                }
            }
        }

        // Drawing the hp icon
        HP_SIZE = canvas.HP_SIZE;
        FilmStrip spriteHpIcon = new FilmStrip(knightHpIconTexture, 1, 1);
        canvas.draw(spriteHpIcon, 0, 0, HP_SIZE, HP_SIZE);
    }

    public void animateSplash() {
        curTimeL--;
        if (curTimeL == 0) {
            curFrameL++;
            // the animation loop is finished
            if (curFrameL > 11) {
                curFrameL = 0;
            }
            curTimeL = animDelayShort;
        } else {
            spriteLight.setFrame(curFrameL);

        }
    }
    /**
     * Preloads the assets for the Knight.
     *
     * The asset manager for LibGDX is asynchronous.  That means that you tell it what to load and then wait while it
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
        manager.load(LIGHT_SPLASH_FILE, Texture.class);
    }

    /**
     * Loads the assets for the Knight.
     *
     * All shell objects use one of two textures, so this is a static method. This keeps us from loading the same images
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
            knightTexture = manager.get(KNIGHT_NORMAL_FILE, Texture.class);
            knightTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            knightTexture = null;  // Failed to load
        }
        //load dash file
        if (manager.isLoaded(KNIGHT_DASH_FILE)) {
            knightDashTexture = manager.get(KNIGHT_DASH_FILE, Texture.class);
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

        // load light splash icon
        if (manager.isLoaded(LIGHT_SPLASH_FILE)) {
            lightSplashTexture = manager.get(LIGHT_SPLASH_FILE, Texture.class);
            lightSplashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            lightSplashTexture = null; //Failed to load
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
        if (lightSplashTexture != null) {
            lightSplashTexture = null;
            manager.unload(LIGHT_SPLASH_FILE);
        }
    }

    public void setFalling() {
        if (this.isAlive) {
            this.isAlive = false;
            this.setState(KnightState.FALLING);
            switch (this.facing) {
                case FRONT:
                    curFrame = FALL_START;
                    break;
                case BACK:
                    curFrame = FALL_UP_START;
                    break;
                case LEFT:
                    curFrame = FALL_LEFT_START;
                    break;
                case RIGHT:
                    curFrame = FALL_RIGHT_START;
                    break;
            }
        }
    }

    public void setDancing() {
        this.setState(KnightState.DANCING);
        this.notDancing = false;
        this.curFrame = DANCE_START;
        this.curTime = animDelayShort;
    }

    /**
     * Decrements the player health by 1
     *
     * GameplayController will handle invulnerability
     */
    public void takeDamage() {
        this.knightHP -= hpDamageAmount;
        if (this.knightHP <= 0) {
            this.isAlive = false;
            this.setState(KnightState.DEAD);
            RhythmController.playDamage();
            switch (this.facing) {
                case FRONT:
                    curFrame = DEAD_START;
                    break;
                case BACK:
                    curFrame = DEAD_UP_START;
                    break;
                case LEFT:
                    curFrame = DEAD_LEFT_START;
                    break;
                case RIGHT:
                    curFrame = DEAD_RIGHT_START;
                    break;
            }
        }else {
            curTime = animDelay;
            switch (this.facing) {
                case FRONT:
                    curFrame = HURT_START;
                    break;
                case BACK:
                    curFrame = HURT_UP_START;
                    break;
                case LEFT:
                    curFrame = HURT_LEFT_START;
                    break;
                case RIGHT:
                    curFrame = HURT_RIGHT_START;
                    break;
            }
            this.state = KnightState.TAKINGDMG;
            RhythmController.playDamage();
        }
    }

    public void decrementHP(int amount) {
        this.knightHP -= amount;
        if (this.knightHP < 0) {
            this.knightHP = 0;
        }
        if (this.knightHP <= 0) {
            if (this.isAlive) {
                this.isAlive = false;
                this.setState(KnightState.DEAD);
                switch (this.facing) {
                    case FRONT:
                        curFrame = DEAD_START;
                        break;
                    case BACK:
                        curFrame = DEAD_UP_START;
                        break;
                    case LEFT:
                        curFrame = DEAD_LEFT_START;
                        break;
                    case RIGHT:
                        curFrame = DEAD_RIGHT_START;
                        break;
                }
            }
        }
    }

    public static void setHPLossPerMiss(int h){
        hpDamageAmount = h;
    }

    /** Called whenever the player successfully inputs a move on the beat */
    public void showSuccess() {
        if (this.knightHP < INITIAL_HP) {
            this.knightHP += 10;
            if (this.knightHP > INITIAL_HP) {
                this.knightHP = INITIAL_HP;
            }
        }
        switch (this.facing) {
            case FRONT:
                curFrame = IDLE_START;
                break;
            case BACK:
                curFrame = IDLE_UP_START;
                break;
            case LEFT:
                curFrame = IDLE_LEFT_START;
                break;
            case RIGHT:
                curFrame = IDLE_RIGHT_START;
                break;
        }
        curTime = animDelay;
        this.state = KnightState.MOVING;
    }

    public void setAttacking() {
        if (this.knightHP < INITIAL_HP) {
            this.knightHP += HP_KILL_BOOST;
            if (this.knightHP > INITIAL_HP) {
                this.knightHP = INITIAL_HP;
            }
        }
        curTime = animDelayShort;
        switch (this.facing) {
            case FRONT:
                curFrame = SWORD_START;
                break;
            case BACK:
                curFrame = SWORD_UP_START;
                break;
            case LEFT:
                curFrame = SWORD_LEFT_START;
                break;
            case RIGHT:
                curFrame = SWORD_RIGHT_START;
                break;
        }
        this.state = KnightState.ATTACKING;
    }

    public void setDashing() {
        this.isDashing = true;
        curTime = animDelay;
        switch (this.facing) {
            case FRONT:
                curFrameDash = DASH_START;
                break;
            case BACK:
                curFrameDash = DASH_UP_START;
                break;
            case LEFT:
                curFrameDash = DASH_LEFT_START;
                break;
            case RIGHT:
                curFrameDash = DASH_RIGHT_START;
                break;
        }
    }

    public boolean isInvulnerable() {
        return isInvulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.isInvulnerable = invulnerable;
    }

    // Currently being used for animation of the knight
    public enum KnightState {
        /** Draw the knight normally */
        NORMAL,
        /** Knight is attacking */
        ATTACKING,
        /** Knight is using freeze spell */
        FREEZING,
        /** Knight is taking damage */
        TAKINGDMG,
        /** Knight has successfully taken an action */
        MOVING,
        /** Knight is falling into an empty tile */
        FALLING,
        /** Knight is dead */
        DEAD,
        /** Knight has completed the level */
        DANCING
    }

    // Used in animating the sprite of the knight
    public enum KnightDirection {
        FRONT,
        BACK,
        LEFT,
        RIGHT
    }
}