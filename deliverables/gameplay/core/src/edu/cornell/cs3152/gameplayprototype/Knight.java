package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;

/**
 * Knight class!
 * Fill in description here!
 * */
public abstract class Knight{

	public void update() {
		// TODO: this method
	}

	public void draw() {
		// TODO: this method
	}

	// Update planned moved behavior here, but don't actually move until update!
	// (see other labs such as lab3's setMovement() for how this is done)
	public void move(int x, int y) {

	}

	/**
	 * Preloads the assets for the Knight.
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
		// TODO: this method
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
		// TODO: this method
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
	}
}
