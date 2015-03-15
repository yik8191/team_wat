package edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import edu.teamWat.rhythmKnights.technicalPrototype.utils.FilmStrip;
import edu.teamWat.rhythmKnights.technicalPrototype.views.GameCanvas;

public class Skeleton extends GameObject {

    private boolean isAlive = false;
    private boolean isActive = false;

    public Vector2 position;
    public int moveCooldown;

    public static final String SKELETON_FILE = "images/skeleton.png";
    public static Texture skeletonTexture;

    public Skeleton(Vector2 position){
        this.position = position;
        this.moveCooldown = 0;
        isAlive = true;
        isActive = true;
    }

    public void update() {
        // If we are dead do nothing.
        if (!isAlive) {
            return;
        }
        //TODO: implement this

    }

    public void draw(GameCanvas canvas) {
        FilmStrip sprite = new FilmStrip(skeletonTexture, 1, 1);
        Vector2 loc = canvas.boardToScreen(position.x, position.y);
        canvas.draw(sprite, loc.x, loc.y);
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
        manager.load(SKELETON_FILE, Texture.class);
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
        if (manager.isLoaded(SKELETON_FILE)) {
            skeletonTexture = manager.get(SKELETON_FILE,Texture.class);
            skeletonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            skeletonTexture = null;  // Failed to load
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
        if (skeletonTexture != null) {
            skeletonTexture = null;
            manager.unload(SKELETON_FILE);
        }
    }

}
