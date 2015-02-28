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
 * This code has been copied from the above original authors and modified by Gagik Hakobyan.
 */
package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.*;
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
	/** Whether or not the player jumped */
	boolean didJump;
	/** Set to +-1 or 0 to indicate vertical movement */
	int vertical;
	/** Set to +-1 or 0 to indicate horizontal movement */
	int horizontal;


	private XBox360Controller xbox;



	/**
	 * Returns true if the exit button was pressed.
	 *
	 * @return true if the exit button was pressed.
	 */
	public boolean didExit() {
		return exitPressed;
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
		// TODO: fill in input code
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
		// Todo: fill in input code
	}
}
