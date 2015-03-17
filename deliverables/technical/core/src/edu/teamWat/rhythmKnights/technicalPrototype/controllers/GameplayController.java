package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.javafx.collections.VetoableListDecorator;
import edu.teamWat.rhythmKnights.technicalPrototype.models.*;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.*;
import edu.teamWat.rhythmKnights.technicalPrototype.models.Ticker.*;


import javax.swing.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class GameplayController {
	/** Reference to the game board */
	public Board board;
	/** Reference to all the game objects in the game */	
	public GameObjectList gameObjects;
	/** List of all the input (both player and AI) controllers */
	protected InputController[] controls;
	/** Ticker */
	public Ticker ticker;
	
	private Knight knight;

	public PlayerController playerController;

	public CollisionController collisionController;

	private ArrayList<Integer> playerActionQueue = new ArrayList<Integer>();

	boolean playerMoved = false;

	boolean gameStateAdvanced;

	boolean calibrationBeatSent;

	private boolean gameOver = false;

	public GameplayController() {	}

	public void initialize() {

		board = new Board(13, 7);
		gameObjects = new GameObjectList(9);

		controls = new InputController[gameObjects.size()];

		gameObjects.add(new Knight(0, 0, 3));
		controls[0] = playerController;

		gameObjects.add(new DynamicTile(1, 3, 3));
		int[] path = {InputController.CONTROL_MOVE_UP, InputController.CONTROL_NO_ACTION, InputController.CONTROL_MOVE_UP,
				InputController.CONTROL_NO_ACTION, InputController.CONTROL_MOVE_RIGHT, InputController.CONTROL_NO_ACTION,
				InputController.CONTROL_MOVE_DOWN, InputController.CONTROL_NO_ACTION, InputController.CONTROL_MOVE_DOWN,
				InputController.CONTROL_NO_ACTION, InputController.CONTROL_MOVE_DOWN, InputController.CONTROL_NO_ACTION,
				InputController.CONTROL_MOVE_DOWN, InputController.CONTROL_NO_ACTION,
				InputController.CONTROL_MOVE_LEFT, InputController.CONTROL_NO_ACTION, InputController.CONTROL_MOVE_UP,
				InputController.CONTROL_NO_ACTION,
				InputController.CONTROL_MOVE_UP, InputController.CONTROL_NO_ACTION};
		controls[1] = new AIController(1, gameObjects, path);

		gameObjects.add(new Skeleton(2, 5, 0));
		path = new int[] {InputController.CONTROL_MOVE_LEFT, InputController.CONTROL_MOVE_RIGHT,
				InputController.CONTROL_MOVE_RIGHT, InputController.CONTROL_MOVE_LEFT};
		controls[2] = new AIController(2, gameObjects, path);

		gameObjects.add(new Skeleton(3, 5, 6));
		path = new int[] {InputController.CONTROL_MOVE_LEFT, InputController.CONTROL_MOVE_RIGHT,
				InputController.CONTROL_MOVE_RIGHT, InputController.CONTROL_MOVE_LEFT};
		controls[3] = new AIController(3, gameObjects, path);

		gameObjects.add(new Slime(4, 7, 0));
		path = new int[] {InputController.CONTROL_MOVE_UP, InputController.CONTROL_MOVE_RIGHT,
				InputController.CONTROL_MOVE_DOWN, InputController.CONTROL_MOVE_LEFT};
		controls[4] = new AIController(4, gameObjects, path);

		gameObjects.add(new Slime(5, 7, 6));
		path = new int[] {InputController.CONTROL_MOVE_LEFT, InputController.CONTROL_MOVE_UP,
				InputController.CONTROL_MOVE_RIGHT, InputController.CONTROL_MOVE_DOWN};
		controls[5] = new AIController(5, gameObjects, path);

		gameObjects.add(new Slime(6, 9, 2));
		path = new int[] {InputController.CONTROL_MOVE_RIGHT, InputController.CONTROL_MOVE_DOWN,
				InputController.CONTROL_MOVE_LEFT, InputController.CONTROL_MOVE_UP};
		controls[6] = new AIController(6, gameObjects, path);

		gameObjects.add(new Slime(7, 9, 4));
		path = new int[] {InputController.CONTROL_MOVE_RIGHT, InputController.CONTROL_MOVE_UP,
				InputController.CONTROL_MOVE_LEFT, InputController.CONTROL_MOVE_DOWN};
		controls[7] = new AIController(7, gameObjects, path);

		gameObjects.add(new Slime(8, 11, 3));
		path = new int[] {InputController.CONTROL_MOVE_UP, InputController.CONTROL_MOVE_DOWN,
				InputController.CONTROL_MOVE_DOWN, InputController.CONTROL_MOVE_UP};
		controls[8] = new AIController(8, gameObjects, path);

		board.setTile(0, 3, false, true, false);
		board.setTile(3, 1, false, false, true);
		board.setTile(3, 2, false, false, true);
		board.setTile(3, 3, false, false, true);
		board.setTile(3, 4, false, false, true);
		board.setTile(3, 5, false, false, true);
		board.setTile(4, 1, false, false, true);
		board.setTile(4, 2, false, false, true);
		board.setTile(4, 3, false, false, true);
		board.setTile(4, 4, false, false, true);
		board.setTile(4, 5, false, false, true);
		board.setTile(7, 3, false, false, true);
		board.setTile(8, 3, false, false, true);
		board.setTile(9, 3, false, false, true);
		board.setTile(10, 3, false, false, true);
		board.setTile(12, 2, false, false, true);
		board.setTile(12, 3, true, false, false);
		board.setTile(12, 4, false, false, true);

		ticker = new Ticker(new TickerAction[] {TickerAction.MOVE, TickerAction.MOVE, TickerAction.MOVE, TickerAction.MOVE});

		collisionController = new CollisionController(board, gameObjects);

		knight = (Knight)gameObjects.getPlayer();
		knight.setInvulnerable(true);
		gameOver = false;
		playerMoved = true;
		calibrationBeatSent = true;
		gameStateAdvanced = true;
	}


	public void update() {
		if (playerController.didReset) {
			playerController.clear();
			gameOver = true;
			return;
		}

		if (RhythmController.updateBeat()) {
			//Final actions
			if (!playerMoved) {
				damagePlayer();
				advanceGameState();
			}

			// Cleanup
			gameStateAdvanced = false;
			playerMoved = false;
			calibrationBeatSent = false;
		} else {
			if (playerMoved) {
				if (playerController.numKeyEvents > 0) {
					damagePlayer();
				}
			} else {
				switch (ticker.getAction()) {
					case MOVE:
						if (playerController.numKeyEvents > 1) {
							damagePlayer();
							advanceGameState();
							playerMoved = true;
						} else if (playerController.numKeyEvents == 1) {
							PlayerController.KeyEvent event = playerController.keyEvents[0];

							//DEBUGGING CODE
							RhythmController.isWithinActionWindow(event.time, 0, true);

							// Send a calibration beat
							if (!calibrationBeatSent) {
								RhythmController.sendCalibrationBeat(event.time);
								calibrationBeatSent = true;
							}


							if (RhythmController.isWithinActionWindow(event.time, 0, false)) {
								Vector2 vel = new Vector2();
								switch (event.code) {
									case InputController.CONTROL_MOVE_RIGHT:
										vel.x = 1;
										break;
									case InputController.CONTROL_MOVE_UP:
										vel.y = 1;
										break;
									case InputController.CONTROL_MOVE_LEFT:
										vel.x = -1;
										break;
									case InputController.CONTROL_MOVE_DOWN:
										vel.y = -1;
										break;
								}
								playerMoved = true;
								knight.setVelocity(vel);
								advanceGameState();
							}
						}
						break;
					case DASH:
						if (playerController.numKeyEvents > 1) {
							damagePlayer();
							advanceGameState();
							playerMoved = true;
						} else if (playerController.numKeyEvents == 1) {
							PlayerController.KeyEvent event = playerController.keyEvents[0];

							//DEBUGGING CODE
							RhythmController.isWithinActionWindow(event.time, 0, true);

							// Send a calibration beat
							if (!calibrationBeatSent) {
								RhythmController.sendCalibrationBeat(event.time);
								calibrationBeatSent = true;
							}


							if (RhythmController.isWithinActionWindow(event.time, 0, false)) {
								Vector2 vel = new Vector2();
								switch (event.code) {
									case InputController.CONTROL_MOVE_RIGHT:
										vel.x = 1;
										break;
									case InputController.CONTROL_MOVE_UP:
										vel.y = 1;
										break;
									case InputController.CONTROL_MOVE_LEFT:
										vel.x = -1;
										break;
									case InputController.CONTROL_MOVE_DOWN:
										vel.y = -1;
										break;
								}
								playerMoved = true;
								knight.setVelocity(vel);
								advanceGameState();
							}
						}
						break;
				}
			}
		}

		playerController.clear();
	}

	private void advanceGameState () {
		if (!gameStateAdvanced) {
			ticker.advance();
			board.updateColors();
			moveEnemies();
			collisionController.update();
			if (collisionController.hasPlayerMoved) knight.setInvulnerable(false);
			gameStateAdvanced = true;

			// Configures the next beat to handle inputs properly
//			switch (ticker.getAction()) {
//				case MOVE:
//					RhythmController.actionWindowRadius = 0.15f;
//					RhythmController.finalActionOffset = 0.5f;
//
//					break;
//				case DASH:
//
//
//					break;
//			}

		}
	}

	public void damagePlayer() {
		if (!knight.isInvulnerable()) {
			knight.takeDamage();
			knight.setInvulnerable(true);
		}
	}

	public void moveEnemies() {
		Vector2 vel = new Vector2();
		for (int i = 1; i < controls.length; i++) {
			vel.set(0,0);
			switch(controls[i].getAction()){
				case InputController.CONTROL_MOVE_RIGHT:
					vel.x = 1;
					break;
				case InputController.CONTROL_MOVE_UP:
					vel.y = 1;
					break;
				case InputController.CONTROL_MOVE_LEFT:
					vel.x = -1;
					break;
				case InputController.CONTROL_MOVE_DOWN:
					vel.y = -1;
					break;
			}
			((AIController)controls[i]).nextAction();
			gameObjects.get(i).setVelocity(vel);
		}
	}

	public boolean isGameOver() {
		return gameOver;
	}
}
