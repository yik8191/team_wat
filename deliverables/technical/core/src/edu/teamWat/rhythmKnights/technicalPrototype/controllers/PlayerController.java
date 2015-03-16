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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.TimeUtils;

public class PlayerController implements InputController, InputProcessor {

	public int controlCode = CONTROL_NO_ACTION;
	public long lastEventTime = 0;



	/**
	 * Return the action of this knight (but do not process)
	 * 
	 * The value returned must be some bitmasked combination of the static ints 
	 * in the implemented interface. 
	 *
	 * @return the action of this ship
	 */
    public int getAction() {
	    return controlCode;
    }

	public void clear() {
		controlCode = CONTROL_NO_ACTION;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.A:
				controlCode |= CONTROL_MOVE_LEFT;
				lastEventTime = TimeUtils.millis();
				break;
			case Keys.D:
				controlCode |= CONTROL_MOVE_RIGHT;
				lastEventTime = TimeUtils.millis();
				break;
			case Keys.W:
				controlCode |= CONTROL_MOVE_UP;
				lastEventTime = TimeUtils.millis();
				break;
			case Keys.S:
				controlCode |= CONTROL_MOVE_DOWN;
				lastEventTime = TimeUtils.millis();
				break;
			case Keys.LEFT:
				controlCode |= CONTROL_MOVE_LEFT;
				lastEventTime = TimeUtils.millis();
				break;
			case Keys.RIGHT:
				controlCode |= CONTROL_MOVE_RIGHT;
				lastEventTime = TimeUtils.millis();
				break;
			case Keys.UP:
				controlCode |= CONTROL_MOVE_UP;
				lastEventTime = TimeUtils.millis();
				break;
			case Keys.DOWN:
				controlCode |= CONTROL_MOVE_DOWN;
				lastEventTime = TimeUtils.millis();
				break;

		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}