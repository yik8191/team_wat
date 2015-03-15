package edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import edu.teamWat.rhythmKnights.technicalPrototype.utils.FilmStrip;
import edu.teamWat.rhythmKnights.technicalPrototype.views.*;


public class Knight extends GameObject {

	public boolean isAlive = true;
    public int moveCooldown;

	public static final String KNIGHT_FILE = "images/knight.png";
	public static Texture knightTexture;
	
	public Knight(int id, float x, float y){
		super(id, x, y); // just call constructor of superclass 
    }
	

	public boolean isAlive(){
		return isAlive;
	}
	
	/**
	 * Updates this knight "velocity" according to the control code.
	 *
	 * @param controlCode The movement controlCode (from InputController).
	 */
	public boolean update(int controlCode) {
        return false;
	}

	public void draw(GameCanvas canvas) {
		// TODO: this method
		FilmStrip sprite = new FilmStrip(knightTexture, 1, 1);
		Vector2 curPos = this.getPosition();
		Vector2 loc = new Vector2();
		loc.x = curPos.x*79 + 45;  // may need adjustment
		loc.y = curPos.y*79 + 182; // may need adjustment
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
		// TODO: this method
		manager.load(KNIGHT_FILE, Texture.class);
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
		// TODO: this method
		if (manager.isLoaded(KNIGHT_FILE)) {
			knightTexture = manager.get(KNIGHT_FILE,Texture.class);
			knightTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			knightTexture = null;  // Failed to load
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
		// TODO: this method
		if (knightTexture != null) {
			knightTexture = null;
			manager.unload(KNIGHT_FILE);
		}
	}

}
