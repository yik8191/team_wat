package edu.teamWat.rhythmKnights.alpha.models.gameObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import edu.teamWat.rhythmKnights.alpha.utils.FilmStrip;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

public class Slime extends GameObject{
    
    public static final String SLIME_FILE = "images/slime.png";
    public static Texture slimeTexture;
	private int animFrames = 10;
	private int animAge = 0;

    public Slime(int id, float x, float y){
        this.id = id;
        this.position.set(x,y);
	    this.oldPosition.set(position);
        isAlive = true;
        isActive = true;
    }

	@Override
    public void update() {
        // If we are dead do nothing.
        if (!isAlive) {
            return;
        }
	    animAge++;
	    if (animAge == animFrames) {
		    animAge = 0;
		    animatedPosition.set(position);
		    oldPosition.set(position);
	    } else {
		    animatedPosition.set(position).sub(oldPosition).scl((float)animAge/animFrames).add(oldPosition);
	    }
        //TODO: implement this

    }

    public void draw(GameCanvas canvas) {
        FilmStrip sprite = new FilmStrip(slimeTexture, 1, 1);
        Vector2 loc = canvas.boardToScreen(animatedPosition.x, animatedPosition.y);
        canvas.draw(sprite, loc.x, loc.y, canvas.tileSize, canvas.tileSize);
    }


    //	/**
//	 * Moves the knight based on the input code*/
//	public void move(int code){
//
//	}
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
        manager.load(SLIME_FILE, Texture.class);
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
        if (manager.isLoaded(SLIME_FILE)) {
            slimeTexture = manager.get(SLIME_FILE,Texture.class);
            slimeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            slimeTexture = null;  // Failed to load
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
        if (slimeTexture != null) {
            slimeTexture = null;
            manager.unload(SLIME_FILE);
        }
    }


}
