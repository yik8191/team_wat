package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

/**
 * Interface for either player or AI control. We made use of InputController in 
 * CS 3152 Lab 2 as a guide.
 */
public interface InputController {

	// Constants for the control codes
	// We want to use bitmasks to handle multiple actions
	/** Do not do anything */
	public static final int CONTROL_NO_ACTION  = 0x00;
	/** Move the ship to the left */
	public static final int CONTROL_MOVE_LEFT  = 0x01;
	/** Move the ship to the right */
	public static final int CONTROL_MOVE_RIGHT = 0x02;
	/** Move the ship to the up */
	public static final int CONTROL_MOVE_UP    = 0x04;
	/** Move the ship to the down */
	public static final int CONTROL_MOVE_DOWN  = 0x08;
    /** Apply the jump modifier */
    public static final int CONTROL_JUMP = 0x10;
    /** Reset the game */
    public static final int CONTROL_RESET = 0x20;
    /** Exit the game */
    public static final int CONTROL_EXIT = 0x40;

	/**
	 * Return the action of this game object
	 *
	 * @return the action of this game object handled by the controller
	 */
	public int getAction();
}
