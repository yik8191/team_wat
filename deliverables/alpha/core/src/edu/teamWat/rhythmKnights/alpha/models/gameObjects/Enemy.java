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

    // The number of frames before a sprite refreshes
    private int animDelay = 5;
    private int curTime = 5;
    private int curFrame = 0;

    // Constants for reference to the spritesheet
    private int IDLE_START = 0;
    private int IDLE_END = 5;
    private int HURT_START = 6;
    private int HURT_END = 11;
    private int SPRITE_ROWS = 2;
    private int SPRITE_COLS = 6;
    private int SPRITE_TOT = 12;


    public Enemy(int id, float x, float y, EnemyType e){
        this.id = id;
        this.position = new Vector2(x,y);
	    this.animatedPosition.set(position);
	    this.oldPosition.set(position);
	    this.type = e;
        isAlive = true;
        isActive = true;
        isCharacter = true;
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
    
    public void draw(GameCanvas canvas) {
//        curTime --;
//        if (curTime == 0) {
//            curFrame ++;
//            if (curFrame >= IDLE_END) {
//                curFrame = IDLE_START;
//            }
//            curTime = animDelay;
//        } else {
//            sprite.setFrame(curFrame);
//        }

        Texture enemyTexture = this.getTexture();
        sprite = new FilmStrip(enemyTexture, 1, 4);

        if (this.velocity.x > 0){
            sprite.setFrame(3);
        }else if(this.velocity.x < 0){
            sprite.setFrame(2);
        }else if(this.velocity.y > 0){
            sprite.setFrame(1);
        }else{
            sprite.setFrame(0);
        }

        Vector2 loc = canvas.boardToScreen(animatedPosition.x, animatedPosition.y);
        canvas.draw(sprite, loc.x, loc.y, canvas.tileSize, canvas.tileSize);
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
}

