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
	
	public boolean isAlive = true;
	public boolean movingLeft;
	public boolean movingRight;
	public boolean movingUp;
	public boolean movingDown;
	public Vector2 position;
    public int moveCooldown;

	public static final String KNIGHT_FILE = "images/knight.png";
	public static Texture knightTexture;
	
	public Knight(Vector2 position){
        this.position = position;
        this.moveCooldown = 0;
    }
	
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
    /** Move cooldown time for the knight in frames */
    public static final int MOVE_COOLDOWN = 10;

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
		// Hard code bounds for now
		// VERY IMPORTANT - Movement must not be done in this class!
		// ENSURE THAT THIS CODE DOES NOT CAUSE POSITION UPDATES IN TECHNICAL
		// PROTOTYPE
        if (moveCooldown == 0) {
            if (movingLeft) {
    			if (position.x > 0) {
    				position.x --;
    			}
            } else if (movingRight) {
            	if (position.x < 8){
    				position.x++;
    			}
            } else if (movingUp) {
            	if ((position.x == 1 || position.x == 8) && position.y < 2){
    				position.y++;
    			}
            } else if (movingDown) {
            	if ((position.x == 1 || position.x == 8) && position.y > 0){
    				position.y--;
    			}
            }
        }
        if (controlCode != 0) {
            moveCooldown = MOVE_COOLDOWN;
        } else {
            moveCooldown = Math.max(0, moveCooldown - 1);
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
