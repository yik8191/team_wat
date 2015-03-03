package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;

/**
 * Knight class!
 * Fill in description here!
 * */
public class Knight{
	
	public boolean isAlive;
	public int move;  // encode movement information
	public Vector2 position;
	
	/** Do not do anything */
	public static final int CONTROL_NO_ACTION  = 0x00;
	/** Move the knight to the left */
	public static final int CONTROL_MOVE_LEFT  = 0x01;
	/** Move the knight to the right */
	public static final int CONTROL_MOVE_RIGHT = 0x02;
	/** Move the knight to the up */
	public static final int CONTROL_MOVE_UP    = 0x04;
	/** Move the knight to the down */
	public static final int CONTROL_MOVE_DOWN  = 0x08;
	/** If the player wants to jump */
	public static final int CONTROL_JUMP = 0x10;
	/** If the player wants to reset the game */
	public static final int CONTROL_RESET  = 0x40;
	/** If the player wants to exit the game */
	public static final int CONTROL_EXIT = 0x80;

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
	public void update(int controlCode) {
		// If we are dead do nothing.
		if (!isAlive) {
			return;
		}
		// Determine how we are moving.
		boolean movingLeft  = (controlCode & InputController.CONTROL_MOVE_LEFT) != 0;
		boolean movingRight = (controlCode & InputController.CONTROL_MOVE_RIGHT) != 0;
		boolean movingUp    = (controlCode & InputController.CONTROL_MOVE_UP) != 0;
		boolean movingDown  = (controlCode & InputController.CONTROL_MOVE_DOWN) != 0;
		// Process movement command.
		if (movingLeft) {
			position.x--;
		} else if (movingRight) {
			position.x++;
		} else if (movingUp) {
			position.y--;
		} else if (movingDown) {
			position.y++;
		} else {
			
		}
	}

	public void draw(GameCanvas canvas) {
		// TODO: this method
		//canvas.draw(sprite, this.position.x, this.position.y);
	}

	/**
	 * Moves the knight based on the input code*/
	public void move(int code){
		
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
