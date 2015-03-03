package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;

import edu.cornell.cs3152.gameplayprototype.utils.FilmStrip;

/**
 * Knight class!
 * Fill in description here!
 * */
public class Knight{
	
	// Constants for the control codes
	// We would normally use an enum here, but Java enums do not bitmask nicely
	/** Do not do anything */
	public static final int NO_ACTION  = 0x00;
	/** Moving the enemy to the left */
	public static final int MOVE_LEFT  = 0x01;
	/** Moving the enemy to the right */
	public static final int MOVE_RIGHT = 0x02;
	/** Moving the enemy to the up */
	public static final int MOVE_UP    = 0x04;
	/** Moving the enemy to the down */
	public static final int MOVE_DOWN  = 0x08;
	
	public boolean isAlive;
	public int move;  // encode movement information
	public Vector2 position;
	
	public static final String KNIGHT_FILE = "images/knight.png";
	public static Texture knightTexture;
	
	public Knight(Vector2 position){
		this.position = position;
	}
	
	

	public Vector2 getPostion() {
		return position;
	}

	public boolean isAlive(){
		return isAlive;
	}
	
	/**
	 * Updates this knight position according to the control code.
	 *
	 * @param controlCode The movement controlCode (from InputController).
	 */
	public void update() {
		// If we are dead do nothing.
		if (!isAlive) {
			return;
		}
	}

	public void draw(GameCanvas canvas) {
		// TODO: this method
		FilmStrip sprite = new FilmStrip(knightTexture, 1, 1);
		Vector2 curPos = this.position;
		Vector2 loc = new Vector2();
		loc.x = curPos.x*80 + 35;
		loc.y = curPos.y*80 + 185;
		canvas.draw(sprite, loc.x, loc.y);
	}

	public void move(int horizontal, int vertical){
		
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
