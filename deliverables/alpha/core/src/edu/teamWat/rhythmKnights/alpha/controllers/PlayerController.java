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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.awt.*;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class PlayerController implements InputController, InputProcessor {

	public ArrayList<KeyEvent> keyEvents = new ArrayList<KeyEvent>();
	public boolean didReset = false;

    //variables used by GameMode
    private int clickX = -1;
    private int clickY = -1;
    private boolean escapePressed = false;
    private boolean listenForInput = true;

	public static long inputOffset = 0;

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
	    return keyEvents.get(keyEvents.size()-1).code;
    }

	public void clear() {
		keyEvents.clear();
		didReset = false;
	}

	@Override
	public boolean keyDown(int keycode) {
        if (listenForInput) {
            switch (keycode) {
                case Keys.ESCAPE:
                    this.escapePressed = true;
                    break;
                case Keys.A:
                    addKeyEvent(CONTROL_MOVE_LEFT, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.D:
                    addKeyEvent(CONTROL_MOVE_RIGHT, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.W:
                    addKeyEvent(CONTROL_MOVE_UP, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.S:
                    addKeyEvent(CONTROL_MOVE_DOWN, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.LEFT:
                    addKeyEvent(CONTROL_MOVE_LEFT, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.RIGHT:
                    addKeyEvent(CONTROL_MOVE_RIGHT, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.UP:
                    addKeyEvent(CONTROL_MOVE_UP, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.DOWN:
                    addKeyEvent(CONTROL_MOVE_DOWN, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.R:
                    didReset = true;
                    break;
            }
        }
		return true;
	}

	public synchronized void addKeyEvent(int code, long time) {
		keyEvents.add(new KeyEvent(code, time));
	}

	@Override
	public boolean keyUp(int keycode) {
        if (listenForInput) {
            switch (keycode) {
                case Keys.ESCAPE:
                    this.escapePressed = false;
                    break;
                case Keys.A:
                    addKeyEvent(CONTROL_MOVE_LEFT | CONTROL_RELEASE, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.D:
                    addKeyEvent(CONTROL_MOVE_RIGHT | CONTROL_RELEASE, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.W:
                    addKeyEvent(CONTROL_MOVE_UP | CONTROL_RELEASE, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.S:
                    addKeyEvent(CONTROL_MOVE_DOWN | CONTROL_RELEASE, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.LEFT:
                    addKeyEvent(CONTROL_MOVE_LEFT | CONTROL_RELEASE, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.RIGHT:
                    addKeyEvent(CONTROL_MOVE_RIGHT | CONTROL_RELEASE, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.UP:
                    addKeyEvent(CONTROL_MOVE_UP | CONTROL_RELEASE, (RhythmController.getSequencePosition() - inputOffset));
                    break;
                case Keys.DOWN:
                    addKeyEvent(CONTROL_MOVE_DOWN | CONTROL_RELEASE, (RhythmController.getSequencePosition() - inputOffset));
                    break;
            }
        }
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.clickX = screenX;
        this.clickY = screenY;
        return false;
	}

    public Vector2 getClick(){
        return new Vector2(clickX, clickY);
    }
    public boolean getEscape() { return this.escapePressed;}
    public void setListenForInput(boolean b){this.listenForInput = b;}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.clickX = -1;
        this.clickY = -1;
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

		public KeyEvent(int code, long time) {
			this.code = code;
			this.time = time;
		}
	}
}