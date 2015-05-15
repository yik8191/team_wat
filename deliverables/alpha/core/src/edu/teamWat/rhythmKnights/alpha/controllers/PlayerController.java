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

import java.util.ArrayList;

public class PlayerController implements InputController, InputProcessor {

	public ArrayList<KeyEvent> keyEvents = new ArrayList<KeyEvent>();
	public boolean didReset = false;

    //variables used by GameMode
    private int clickX = -1;
    private int clickY = -1;
    private int moveX = -1;
    private int moveY = -1;
    private boolean escapePressed = false;
    private boolean listenForInput = true;
    private static int curKey = -1;

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
                    this.setEscape(true);
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
        } else if (keycode == Keys.ESCAPE){
            this.setEscape(true);
        }else{
            setKeyPush(keycode);
        }
        return true;
	}

    private void setKeyPush(int keycode){
        curKey = keycode;
    }

    public static char getKeyPush(){
        char c = 'N';
        switch (curKey) {
            case Keys.A:
                c = 'L';
                break;
            case Keys.LEFT:
                c = 'L';
                break;
            case Keys.S:
                c = 'D';
                break;
            case Keys.DOWN:
                c = 'D';
                break;
            case Keys.D:
                c = 'R';
                break;
            case Keys.RIGHT:
                c = 'R';
                break;
            case Keys.W:
                c = 'U';
                break;
            case Keys.UP:
                c = 'U';
                break;
            case Keys.SPACE:
                c = 'S';
                break;
            case Keys.ENTER:
                c = 'S';
                break;
        }
        curKey = -1;
        return c;
    }

	public synchronized void addKeyEvent(int code, long time) {
		keyEvents.add(new KeyEvent(code, time));
	}

	@Override
	public boolean keyUp(int keycode) {
        if (listenForInput) {
            switch (keycode) {
                case Keys.ESCAPE:
                    this.setEscape(false);
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

    public Vector2 getMove(){
        Vector2 v = new Vector2(moveX, moveY);
        moveX = -1;
        moveY = -1;
        return v;
    }
    public boolean getEscape() { return this.escapePressed;}
    public void    setEscape(boolean a){ this.escapePressed = a;}
    public void    setListenForInput(boolean b){this.listenForInput = b;}

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
		this.moveX = screenX;
        this.moveY = screenY;
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