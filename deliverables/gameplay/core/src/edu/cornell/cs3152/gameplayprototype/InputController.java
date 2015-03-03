/*
 * InputController.java
 *
 * This class buffers in input from the devices and converts it into its
 * semantic meaning. If your game had an option that allows the player to
 * remap the control keys, you would store this information in this class.
 * That way, the main GameEngine does not have to keep track of the current
 * key mapping.
 *
 * This class is a singleton for this application, but we have not designed
 * it as one.  That is to give you some extra functionality should you want
 * to add multiple ships.
 *
 * Author: Walker M. White
 * Based on original Optimization Lab by Don Holden, 2007
 * LibGDX version, 2/2/2015
 *
 * This code has been copied from the above original authors and modified by
 * Gagik Hakobyan and Charles Tark.
 *
 */
package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import edu.cornell.cs3152.gameplayprototype.utils.*;

/**
 * Class for reading player input.
 *
 * This supports both a keyboard and X-Box controller. In previous solutions, we only detected the X-Box controller on
 * start-up.  This class allows us to hot-swap in a controller via the new XBox360Controller class.
 */
public class InputController {
    // Constants for the control codes
    /** Do not do anything */
    public static final int CONTROL_NO_ACTION  = 0x00;
    /** Move the player to the left */
    public static final int CONTROL_MOVE_LEFT  = 0x01;
    /** Move the player to the right */
    public static final int CONTROL_MOVE_RIGHT = 0x02;
    /** Move the player to the up */
    public static final int CONTROL_MOVE_UP    = 0x04;
    /** Move the player to the down */
    public static final int CONTROL_MOVE_DOWN  = 0x08;
    /** Apply the jump modifier */
    public static final int CONTROL_JUMP = 0x10;
    /** Reset the game */
    public static final int CONTROL_RESET = 0x20;
    /** Exit the game */
    public static final int CONTROL_EXIT = 0x40;

	/**
	 * Reads input from the keyboard.
	 *
	 * This controller reads from the keyboard.
     *
     * @return bit masked code for the action.
	 */
	public int getAction() {

        int code = CONTROL_NO_ACTION;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  code |= CONTROL_MOVE_LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) code |= CONTROL_MOVE_RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    code |= CONTROL_MOVE_UP;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  code |= CONTROL_MOVE_DOWN;

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))  code |= CONTROL_JUMP;
        if (Gdx.input.isKeyPressed(Input.Keys.R))  code |= CONTROL_RESET;
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))  code |= CONTROL_EXIT;

        // Prevent diagonal movement.
        if ((code & CONTROL_MOVE_UP) != 0 && (code & CONTROL_MOVE_LEFT) != 0) {
            code ^= CONTROL_MOVE_UP;
        }
        if ((code & CONTROL_MOVE_UP) != 0 && (code & CONTROL_MOVE_RIGHT) != 0) {
            code ^= CONTROL_MOVE_RIGHT;
        }
        if ((code & CONTROL_MOVE_DOWN) != 0 && (code & CONTROL_MOVE_RIGHT) != 0) {
            code ^= CONTROL_MOVE_DOWN;
        }
        if ((code & CONTROL_MOVE_DOWN) != 0 && (code & CONTROL_MOVE_LEFT) != 0) {
            code ^= CONTROL_MOVE_LEFT;
        }

        // Cancel out conflicting movements.
        if ((code & CONTROL_MOVE_LEFT) != 0 && (code & CONTROL_MOVE_RIGHT) != 0) {
            code ^= (CONTROL_MOVE_LEFT | CONTROL_MOVE_RIGHT);
        }
        if ((code & CONTROL_MOVE_UP) != 0 && (code & CONTROL_MOVE_DOWN) != 0) {
            code ^= (CONTROL_MOVE_UP | CONTROL_MOVE_DOWN);
        }

        return code;

        // To implement in future update: Double-tap to jump scheme
	}
	
	public boolean didExit() {
		return ((CONTROL_EXIT&getAction()) == CONTROL_EXIT);
	}
}
