package edu.teamWat.rhythmKnights.alpha.models.gameObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import edu.teamWat.rhythmKnights.alpha.utils.FilmStrip;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

public class DynamicTile extends GameObject {

	public static final String DYN_TILE_FILE = "images/dyn_tile.png";
    public static Texture dynTileTexture;
	
	public DynamicTile(int id, float x, float y) {
		this.id = id;
        this.position = new Vector2(x,y);
		this.animatedPosition.set(position);
		this.oldPosition.set(position);
        isAlive = true;
        isActive = true;
        isCharacter = false;
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

    public void draw(GameCanvas canvas) {
        FilmStrip sprite = new FilmStrip(dynTileTexture, 1, 1);
	    Vector2 loc = canvas.boardToScreen(animatedPosition.x, animatedPosition.y);
        canvas.draw(sprite, loc.x, loc.y, canvas.tileSize, canvas.tileSize);
    }


    /**
     * Preloads the assets for the DynamicTile
     *
     * The asset manager for LibGDX is asynchronous.  That means that
     * you tell it what to load and then wait while it
     * loads them.  This is the first step: telling it what to load.
     *
     * @param manager Reference to global asset manager.
     */
    public static void PreLoadContent(AssetManager manager) {
        manager.load(DYN_TILE_FILE, Texture.class);
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
        if (manager.isLoaded(DYN_TILE_FILE)) {
            dynTileTexture = manager.get(DYN_TILE_FILE,Texture.class);
            dynTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            dynTileTexture = null;  // Failed to load
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
        if (dynTileTexture != null) {
            dynTileTexture = null;
            manager.unload(DYN_TILE_FILE);
        }
    }
}
