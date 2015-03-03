package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;

import edu.cornell.cs3152.gameplayprototype.utils.FilmStrip;

/** Enemy class!
 *  Fill in description here!
 */
public class Enemy{

	// Make sure to add fields for position as well as texture files
	public Vector2[] path;
	private int currentStep;
	public static final String ENEMY_FILE = "images/enemy.png";
	public static Texture enemyTexture;
	public Vector2 position;

	public Enemy(Vector2 position, Vector2[] path){
		this.position = position;
		this.path = path;
	}
	
	/**
	 * Return the position of the enemy
	 */
	public Vector2 getPostion() {
		return position;
	}


	/**
	 * For now, just move the enemy to its new position manually
	 */
	public void update(){
		position = path[currentStep];
		currentStep++;
		int arraySize = path.length;
		if (currentStep >= arraySize){
			currentStep = currentStep % arraySize; 
		}				
	}

	/**
	 * For now, just move the enemy to its new position manually
	 */
	public void draw(GameCanvas canvas) {
		// TODO: this method
		FilmStrip sprite = new FilmStrip(enemyTexture, 1, 1);
		Vector2 curPos = this.position;
		Vector2 loc = new Vector2();
		loc.x = curPos.x*80 + 35;
		loc.y = curPos.y*80 + 185;
		canvas.draw(sprite, loc.x, loc.y);
	}

	/**
	 * Preloads the assets for this Enemy.
	 *
	 * All shell objects use one of two textures, so this is a static method. This keeps us from loading the same images
	 * multiple times for more than one Shell object.
	 *
	 * The asset manager for LibGDX is asynchronous.  That means that you tell it what to load and then wait while it
	 * loads them.  This is the first step: telling it what to load.
	 *
	 * @param manager Reference to global asset manager.
	 */
	public static void PreLoadContent(AssetManager manager) {
		manager.load(ENEMY_FILE, Texture.class);
	}

	/**
	 * Loads the assets for this Enemy.
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
		if (manager.isLoaded(ENEMY_FILE)) {
			enemyTexture = manager.get(ENEMY_FILE,Texture.class);
			enemyTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			enemyTexture = null;  // Failed to load
		}
	}

	/**
	 * Unloads the assets for this Enemy
	 *
	 * This method erases the static variables.  It also deletes the associated textures from the assert manager.
	 *
	 * @param manager Reference to global asset manager.
	 */
	public static void UnloadContent(AssetManager manager) {
		if (enemyTexture != null) {
			enemyTexture = null;
			manager.unload(ENEMY_FILE);
		}
	}
}
