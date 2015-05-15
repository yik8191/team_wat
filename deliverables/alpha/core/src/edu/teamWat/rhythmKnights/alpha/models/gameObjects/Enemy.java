package edu.teamWat.rhythmKnights.alpha.models.gameObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import edu.teamWat.rhythmKnights.alpha.utils.FilmStrip;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

public class Enemy extends GameObject{

    public static final String SKELETON_FILE = "images/spriteSheets/skeletonsheet.png";
    public static final String SLIME_FILE = "images/spriteSheets/slimesheet.png";
    public static Texture skeletonTexture;
    public static Texture slimeTexture;
    private FilmStrip sprite;
    
    public enum EnemyType{
    	SKELETON, SLIME
    }
    
    public EnemyType type;
    public boolean isEnemy = true;

    // The number of frames before a sprite refreshes
    protected final int animDelay = 5;
    protected final int animDelayShort = 3;
    private int curTime = 5;
    private int curFrame = 0;

    // Constants for reference to the spritesheet
    protected final int IDLE_START = 0;
    protected final int IDLE_END = IDLE_START+2;
    protected final int DEAD_START = IDLE_END+1;
    protected final int DEAD_END = DEAD_START+3;
    protected final int ATTACK_START = IDLE_START+8;

    protected final int SPRITE_ROWS = 4;
    protected final int SPRITE_COLS = 12;
    protected final int SPRITE_TOT = 48;

    // Used for enemy's currently facing direction
    // front = 0, back = 1, left = 2, right = 3
    private int facingFact = 0;

    // Used for animation
    private EnemyState state = EnemyState.IDLE;

    public Enemy(int id, float x, float y, EnemyType e){
        this.id = id;
        this.position = new Vector2(x,y);
	    this.animatedPosition.set(position);
	    this.oldPosition.set(position);
	    this.type = e;
        isAlive = true;
        isActive = true;
        isCharacter = true;

        Texture enemyTexture = this.getTexture();
        sprite = new FilmStrip(enemyTexture, SPRITE_ROWS, SPRITE_COLS, SPRITE_TOT);
        sprite.setFrame(0);
    }

    public void update() {
        // If we are dead do nothing.
        if (!isAlive || (this.state == EnemyState.DEAD)) {
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

    public Texture getTexture(){
    	Texture enemyTexture = null;
    	EnemyType e = this.type;
    	switch(e){
    		case SKELETON:
    			enemyTexture = skeletonTexture;
    			break;
    		case SLIME:
    			enemyTexture = slimeTexture;
    			break;
    	}
    	return enemyTexture;
    }
    
    public void draw(GameCanvas canvas, Vector2 times) {

        setAnimate();
        Vector2 loc = canvas.boardToScreen(animatedPosition.x, animatedPosition.y);

        loc.y = loc.y + (times.x/times.y)*(canvas.getHeight()-loc.y);
        canvas.draw(sprite, loc.x, loc.y, canvas.tileSize, canvas.tileSize);

    }


    /** Used for animating different states and directions */
    public void setAnimate() {
        int numFrames;
        int startFrame = 0;
        int endFrame = 0;
        int delay = 2;
        switch(this.state) {
            case IDLE:
                numFrames = 2;
                startFrame = this.facingFact*SPRITE_COLS;
                endFrame = startFrame + numFrames;
                delay = animDelay;
                break;
            case DEAD:
                numFrames = 4;
                startFrame = this.facingFact*SPRITE_COLS + DEAD_START;
                endFrame = startFrame + numFrames;
                delay = animDelay;
                break;
            case ATTACKING:
                numFrames = 4;
                startFrame = this.facingFact*SPRITE_COLS + ATTACK_START;
                endFrame = startFrame + numFrames;
                delay = animDelayShort;
                break;
        }

        curTime --;
        if (curTime == 0) {
            curFrame ++;
            // the animation loop is finished
            if (curFrame > endFrame) {
                curFrame = facingFact*SPRITE_COLS;
                if (this.state == EnemyState.DEAD) {
                    this.setActive(false);
                } else if (this.state == EnemyState.ATTACKING) {
                    this.state = EnemyState.IDLE;
                }
            }
            curTime = delay;
        } else {
            sprite.setFrame(curFrame);
        }
    }

    public void setFacing(EnemyDirection dir) {
        switch(dir) {
            case FRONT:
                this.facingFact = 0;
                break;
            case BACK:
                this.facingFact = 1;
                break;
            case LEFT:
                this.facingFact = 2;
                break;
            case RIGHT:
                this.facingFact = 3;
                break;
        }
        curFrame = this.facingFact*SPRITE_COLS;
    }

    /** Used for setting the enemy to display the death animation */
    public void setDead() {
        this.state = EnemyState.DEAD;
        this.curTime = animDelayShort;
        this.curFrame = facingFact*SPRITE_COLS + DEAD_START;
    }

    /** Used for setting the enemy to display the attacking animation */
    public void setAttacking() {
        this.state = EnemyState.ATTACKING;
        this.curTime = animDelayShort;
        this.curFrame = facingFact*SPRITE_COLS + ATTACK_START;
    }

    // BEGIN HACKY CODE
    public boolean isKindaDead() {
        return this.state == EnemyState.DEAD;
    }

    //	/**
//	 * Moves the knight based on the input code*/
//	public void move(int code){
//
//	}
    /**
     * Preloads the assets for the all Enemy types.
     *
     * The asset manager for LibGDX is asynchronous.  That means that
     * you tell it what to load and then wait while it
     * loads them.  This is the first step: telling it what to load.
     *
     * @param manager Reference to global asset manager.
     */
    public static void PreLoadContent(AssetManager manager) {
        manager.load(SKELETON_FILE, Texture.class);
        manager.load(SLIME_FILE, Texture.class);
    }

    /**
     * Loads the assets for all Enemy types.
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
        if (manager.isLoaded(SKELETON_FILE)) {
            skeletonTexture = manager.get(SKELETON_FILE,Texture.class);
            skeletonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            skeletonTexture = null;  // Failed to load
        }
        if (manager.isLoaded(SLIME_FILE)) {
            slimeTexture = manager.get(SLIME_FILE,Texture.class);
            slimeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            slimeTexture = null;  // Failed to load
        }
    }

    /**
     * Unloads the assets for the Enemy types.
     *
     * This method erases the static variables.  It also deletes the associated textures from the assert manager.
     *
     * @param manager Reference to global asset manager.
     */
    public static void UnloadContent(AssetManager manager) {
        if (skeletonTexture != null) {
            skeletonTexture = null;
            manager.unload(SKELETON_FILE);
        }
        if (slimeTexture != null) {
            slimeTexture = null;
            manager.unload(SLIME_FILE);
        }
    }

    public enum EnemyDirection {
        FRONT,
        BACK,
        LEFT,
        RIGHT
    }

    public enum EnemyState {
        IDLE,
        DEAD,
        ATTACKING
    }
}

