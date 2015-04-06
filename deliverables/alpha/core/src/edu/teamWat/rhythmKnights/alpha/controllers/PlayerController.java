/*
 * PlayerController.java
 * 
 * This class provides the human player an interface to move the knight
 * 
 * Author: Team Wat
 * Heavily based on 2015 CS 3152 Game Lab 2 by 
 * Walker M. White, Cristian Zaloj, which was based on the
 * original AI Game Lab by Yi Xu and Don Holden, 2007
 */
package edu.teamWat.rhythmKnights.alpha.controllers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.sql.Time;

public class PlayerController implements InputController, InputProcessor {

	public KeyEvent[] keyEvents = new KeyEvent[5];
	public int numKeyEvents = 0;
	public boolean didReset = false;

	public PlayerController() {
		super();
		for (int i = 0; i < 5; i++) {
			keyEvents[i] = new KeyEvent();
		}
	}

	/**
	 * Return the action of this knight (but do not process)
	 * 
	 * The value returned must be some bitmasked combination of the static ints 
	 * in the implemented interface. 
	 *
	 * @return the action of this ship
	 */
	@Deprecated
    public int getAction() {
	    return keyEvents[numKeyEvents].code;
    }

	public void clear() {
		keyEvents[0].code = CONTROL_NO_ACTION;
		numKeyEvents = 0;
		didReset = false;
	}

	public KeyEvent getLastAction() {
		return keyEvents[numKeyEvents];
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.A:
				addKeyEvent(CONTROL_MOVE_LEFT, (long)(RhythmController.getPosition()));
				break;
			case Keys.D:
				addKeyEvent(CONTROL_MOVE_RIGHT, (long)(RhythmController.getPosition()));
				break;
			case Keys.W:
				addKeyEvent(CONTROL_MOVE_UP, (long)(RhythmController.getPosition()));
				break;
			case Keys.S:
				addKeyEvent(CONTROL_MOVE_DOWN, (long)(RhythmController.getPosition()));
				break;
			case Keys.LEFT:
				addKeyEvent(CONTROL_MOVE_LEFT, (long)(RhythmController.getPosition()));
				break;
			case Keys.RIGHT:
				addKeyEvent(CONTROL_MOVE_RIGHT, (long)(RhythmController.getPosition()));
				break;
			case Keys.UP:
				addKeyEvent(CONTROL_MOVE_UP, (long)(RhythmController.getPosition()));
				break;
			case Keys.DOWN:
				addKeyEvent(CONTROL_MOVE_DOWN, (long)(RhythmController.getPosition()));
				break;
			case Keys.R:
				didReset = true;
				break;
		}
		System.out.println(RhythmController.toBeatTime((long)(RhythmController.getPosition())));
		return true;
	}

	public synchronized void addKeyEvent(int code, long time) {
		if (numKeyEvents < 5) {
			keyEvents[numKeyEvents].code = code;
			keyEvents[numKeyEvents].time = time;
			numKeyEvents++;
		}
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

	public class KeyEvent {
		int code = 0;
		long time = 0;
	}
}