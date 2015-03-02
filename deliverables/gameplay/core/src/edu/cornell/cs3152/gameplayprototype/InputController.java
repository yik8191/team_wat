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
	/** Whether the exit button was pressed. */
	protected boolean exitPressed;
	/** Whether the jump button was pressed */
	protected boolean jumpPressed;
	/** Set to +-1, +-2, or 0 to indicate vertical movement */
	protected int vertical;
	/** Set to +-1, +-2, or 0 to indicate horizontal movement */
	protected int horizontal;

	private XBox360Controller xbox;

    /**
     * Returns the x and y movement of the player
     *
     * -1 or -2 = left/down, 1 or 2 = right/up, 0 = still
     *
     * @return the amount of movement in x and y directions
     */
    public Vector2 getMovement() {
        Vector2 move = new Vector2(horizontal, vertical);
        return move;
    }

	/**
	 * Returns true if the exit button was pressed.
	 *
	 * @return true if the exit button was pressed.
	 */
	public boolean didExit() {
		return exitPressed;
	}

    /**
     *
     * Returns true if the jump button was pressed.
     *
     * @return true if the jump button was pressed.
     */
    public boolean didJump() {
        return jumpPressed;
    }

	/**
	 * Creates a new input controller
	 *
	 * The input controller attempts to connect to the X-Box controller at device 0, if it exists.  Otherwise, it falls
	 * back to the keyboard control.
	 */
	public InputController() {
		// If we have a game-pad for id, then use it.
		xbox = new XBox360Controller(0);
	}

	/**
	 * Reads the input for the player and converts the result into game logic.
	 */
	public void readInput() {
		// Check to see if a GamePad is connected
		if (xbox.isConnected()) {
			readGamepad();
			readKeyboard(true); // Read as a back-up
		} else {
			readKeyboard(false);
		}
	}

	/**
	 * Reads input from an X-Box controller connected to this computer.
	 */
	private void readGamepad() {
        exitPressed = xbox.getY();
        jumpPressed = xbox.getA();
        int jumpMul = 1;

        if (jumpPressed == true) {
           jumpMul = 2;
        }

        if (xbox.getDPadUp() == true) {
            vertical = 1*jumpMul;
        } else if (xbox.getDPadDown() == true) {
            vertical = -1*jumpMul;
        } else if (xbox.getDPadRight() == true) {
            horizontal = 1*jumpMul;
        } else if (xbox.getDPadLeft() == true) {
            horizontal = -1*jumpMul;
        } else {
            vertical = 0;
            horizontal = 0;
        }
	}

	/**
	 * Reads input from the keyboard.
	 *
	 * This controller reads from the keyboard regardless of whether or not an X-Box controller is connected.  However,
	 * if a controller is connected, this method gives priority to the X-Box controller.
	 *
	 * @param secondary true if the keyboard should give priority to a gamepad
	 */
	private void readKeyboard(boolean secondary) {
		exitPressed = (secondary && exitPressed) || (Gdx.input.isKeyPressed(Input.Keys.ESCAPE));
        jumpPressed = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
        int jumpMul = 1;

        // Check if the jump key is being held down
        if (jumpPressed == true) {
            jumpMul = 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            horizontal = -1*jumpMul;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            horizontal = 1*jumpMul;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            vertical = 1*jumpMul;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            vertical = -1*jumpMul;
        } else {
            vertical = 0;
            horizontal = 0;
        }

        // To implement in future update: Double-tap to jump scheme
	}
}
