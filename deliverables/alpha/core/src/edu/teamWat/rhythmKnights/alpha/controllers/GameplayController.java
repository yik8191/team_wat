package edu.teamWat.rhythmKnights.alpha.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.javafx.collections.VetoableListDecorator;

import edu.teamWat.rhythmKnights.alpha.JSONReader;
import edu.teamWat.rhythmKnights.alpha.models.*;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.*;

import javax.swing.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class GameplayController {
	/** Reference to the game board */
	public Board board;
	/** Reference to all the game objects in the game */	
	public static GameObjectList gameObjects;
	/** List of all the input (both player and AI) controllers */
	public static InputController[] controls;
	/** Ticker */
	public Ticker ticker;
	
	private Knight knight;

	public static PlayerController playerController;

	public CollisionController collisionController;

	private ArrayList<Integer> playerActionQueue = new ArrayList<Integer>();

	boolean playerMoved = false;

	boolean gameStateAdvanced;

	boolean calibrationBeatSent;

	private boolean gameOver = false;

	public GameplayController() {	}

	public void initialize() {

		board = JSONReader.parseFile("../levels/level1.json");
        JSONReader.getObjects();
        ticker = JSONReader.initializeTicker();

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
