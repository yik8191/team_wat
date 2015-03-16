/*
 * KnightController.java
 * 
 * This class provides the human player an interface to move the knight
 * 
 * Heavily based on 2015 CS 3152 Game Lab 2 by 
 * Walker M. White, Cristian Zaloj, which was based on the
 * original AI Game Lab by Yi Xu and Don Holden, 2007
 */
package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerController implements InputController {

	/**
	 * Return the action of this knight (but do not process)
	 * 
	 * The value returned must be some bitmasked combination of the static ints 
	 * in the implemented interface. 
	 *
	 * @return the action of this ship
	 */
    public int getAction() {

        int code = CONTROL_NO_ACTION;
        if (Gdx.input.isKeyPressed(Input.Keys.A))  code |= CONTROL_MOVE_LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) code |= CONTROL_MOVE_RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.W))    code |= CONTROL_MOVE_UP;
        if (Gdx.input.isKeyPressed(Input.Keys.S))  code |= CONTROL_MOVE_DOWN;

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
    }
}
