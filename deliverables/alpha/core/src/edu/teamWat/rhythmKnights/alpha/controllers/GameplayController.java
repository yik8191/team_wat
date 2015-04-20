package edu.teamWat.rhythmKnights.alpha.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.javafx.collections.VetoableListDecorator;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.assets.AssetManager;

import edu.teamWat.rhythmKnights.alpha.JSONReader;
import edu.teamWat.rhythmKnights.alpha.models.*;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.*;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;
import jdk.nashorn.internal.ir.IfNode;

import javax.swing.*;
import javax.swing.plaf.TreeUI;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Vector;
import java.util.function.DoubleUnaryOperator;

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

	private boolean gameOver = false;

	public boolean gameStateAdvanced;

	private final int DOT_HP = 3;
	private int timeHP = DOT_HP;
	private boolean hasMoved = false;

	public GameplayController() {	}


	public void initialize(int levelNum) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		board = JSONReader.parseFile("/levels/level" + levelNum + ".json");
        JSONReader.getObjects();
        ticker = JSONReader.initializeTicker();
        String audio = JSONReader.getAudio();

		// Preallocate memory
		ProjectilePool projs = new ProjectilePool();

		collisionController = new CollisionController(board, gameObjects, projs);

		knight = (Knight)gameObjects.getPlayer();
		knight.setInvulnerable(true);
		gameOver = false;
		try {
			RhythmController.init(audio, ticker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RhythmController.launch();
	}

//	int moved = 0;

	public void update() {
//		System.out.println("Time: " + RhythmController.getSequencePosition());

//		float beatTIme = RhythmController.toBeatTime(RhythmController.getPosition());
//		if ( beatTIme < prevBeatTime){
//			System.out.println(TimeUtils.millis());
//		}
//		prevBeatTime = beatTIme;

		if (hasMoved) {
			timeHP--;
			if (timeHP == 0) {
				knight.decrementHP();
				timeHP = DOT_HP;
			}
			gameStateAdvanced = false;
		}

		if (playerController.didReset) {
			playerController.clear();
			gameOver = true;
			return;
		}

		for (GameObject gameObject : gameObjects) {
			gameObject.update();
		}

		long currentTick = RhythmController.getSequencePosition();
		int prevActionIndex = RhythmController.getClosestEarlierActionIndex(currentTick);
		int nextActionIndex = (prevActionIndex + 1) % RhythmController.numActions;
		int currentActionIndex;
		// Keep clearing the action ahead of us! Simple! :D
		RhythmController.clearNextAction(nextActionIndex);

		for (PlayerController.KeyEvent keyEvent : playerController.keyEvents) {
			prevActionIndex = RhythmController.getClosestEarlierActionIndex(keyEvent.time);
			nextActionIndex = (prevActionIndex + 1) % RhythmController.numActions;
			if (nextActionIndex < prevActionIndex) {
				if (RhythmController.getTrackLength() - keyEvent.time > keyEvent.time - RhythmController.getTick(prevActionIndex)) {
					currentActionIndex = prevActionIndex;
				} else {
					currentActionIndex = nextActionIndex;
				}
			} else {
				if (RhythmController.getTick(nextActionIndex) - keyEvent.time > keyEvent.time - RhythmController.getTick(prevActionIndex)) {
					currentActionIndex = prevActionIndex;
				} else {
					currentActionIndex = nextActionIndex;
				}
			}

			if ((keyEvent.code & InputController.CONTROL_RELEASE) == 0 &&  RhythmController.getCompleted(currentActionIndex)) {
				damagePlayer();
			} else {
				switch (RhythmController.getTickerAction(currentActionIndex)) {
					case MOVE:
//						if (moved > 2) {
//							double a = 0;
//						}
						if ((keyEvent.code & PlayerController.CONTROL_RELEASE) != 0) break;
//						moved++;
						if (knight.getPosition().y == 4) {
							double a = 0;
						}
						RhythmController.setCompleted(currentActionIndex, true);
						RhythmController.setPlayerAction(currentActionIndex, keyEvent.code);
						Vector2 vel = new Vector2(0, 0);
						switch (keyEvent.code) {
							case PlayerController.CONTROL_MOVE_RIGHT:
								vel.x = 1;
								knight.setDirection(Knight.KnightDirection.RIGHT);
								break;
							case PlayerController.CONTROL_MOVE_UP:
								vel.y = 1;
								knight.setDirection(Knight.KnightDirection.BACK);
								break;
							case PlayerController.CONTROL_MOVE_LEFT:
								vel.x = -1;
								knight.setDirection(Knight.KnightDirection.LEFT);
								break;
							case PlayerController.CONTROL_MOVE_DOWN:
								vel.y = -1;
								knight.setDirection(Knight.KnightDirection.FRONT);
								break;
						}
						hasMoved = true;
						knight.setVelocity(vel);
						advanceGameState();
						RhythmController.playSuccess();

						// Display visual feedback to show success
						knight.showSuccess();
						// Set current tile type to SUCCESS
						board.setSuccess((int)knight.getPosition().x, (int)knight.getPosition().y);
						RhythmController.playSuccess();

						break;
					case DASH:
						if ((keyEvent.code & PlayerController.CONTROL_RELEASE) != 0) break;
						RhythmController.setCompleted(currentActionIndex, true);
						RhythmController.setPlayerAction(currentActionIndex, keyEvent.code);
						switch (keyEvent.code) {
							case PlayerController.CONTROL_MOVE_RIGHT:
								knight.setDirection(Knight.KnightDirection.RIGHT);
								break;
							case PlayerController.CONTROL_MOVE_UP:
								knight.setDirection(Knight.KnightDirection.BACK);
								break;
							case PlayerController.CONTROL_MOVE_LEFT:
								knight.setDirection(Knight.KnightDirection.LEFT);
								break;
							case PlayerController.CONTROL_MOVE_DOWN:
								knight.setDirection(Knight.KnightDirection.FRONT);
								break;
						}
						break;
					case DASH2:
						if ((keyEvent.code & PlayerController.CONTROL_RELEASE) != 0) break;
						RhythmController.setCompleted(currentActionIndex, true);
						RhythmController.setPlayerAction(currentActionIndex, keyEvent.code);
						if (RhythmController.getPlayerAction(currentActionIndex) != RhythmController.getPlayerAction(currentActionIndex - 1)) {
							damagePlayer();
						} else {
							vel = new Vector2(0, 0);
							switch (keyEvent.code) {
								case PlayerController.CONTROL_MOVE_RIGHT:
									vel.x = 2;
									break;
								case PlayerController.CONTROL_MOVE_UP:
									vel.y = 2;
									break;
								case PlayerController.CONTROL_MOVE_LEFT:
									vel.x = -2;
									break;
								case PlayerController.CONTROL_MOVE_DOWN:
									vel.y = -2;
									break;
							}
							knight.setVelocity(vel);
							advanceGameState();
							RhythmController.playSuccess();

							// Display visual feedback to show success
							knight.showSuccess();
//							knight.setDashing();
							// Set current tile type to SUCCESS
							board.setSuccess((int)knight.getPosition().x, (int)knight.getPosition().y);
							RhythmController.playSuccess();
						}
						break;
					case FIREBALL:
						if ((keyEvent.code & PlayerController.CONTROL_RELEASE) != 0) break;
						break;
					case FIREBALL2:
						if ((keyEvent.code & PlayerController.CONTROL_RELEASE) == 0) break;
						break;
				}
			}
		}

		playerController.clear();

		prevActionIndex = RhythmController.getClosestEarlierActionIndex(currentTick);
		nextActionIndex = (prevActionIndex + 1) % RhythmController.numActions;

		if (nextActionIndex < prevActionIndex) {
			if (RhythmController.getTrackLength() - currentTick > currentTick - RhythmController.getTick(prevActionIndex)) {
				currentActionIndex = prevActionIndex;
			} else {
				currentActionIndex = nextActionIndex;
			}
		} else {
			if (RhythmController.getTick(nextActionIndex) - currentTick > currentTick - RhythmController.getTick(prevActionIndex)) {
				currentActionIndex = prevActionIndex;
			} else {
				currentActionIndex = nextActionIndex;
			}
		}

		if ((currentActionIndex == nextActionIndex) && !RhythmController.getCompleted(prevActionIndex)){
			damagePlayer();
			RhythmController.setCompleted(prevActionIndex, true);
			if (RhythmController.getTickerAction(prevActionIndex) != Ticker.TickerAction.DASH2 && RhythmController.getTickerAction(prevActionIndex) != Ticker.TickerAction.FIREBALL2) {
				advanceGameState();
			}
		}


//		if (RhythmController.updateBeat()) {
//
//			//Final actions
//			if (!playerMoved) {
//				damagePlayer();
//				advanceGameState();
//			}
//
//			// Cleanup
//			gameStateAdvanced = false;
//			playerMoved = false;
//			calibrationBeatSent = false;
//		} else {
//			if (playerMoved) {
//				if (playerController.numKeyEvents > 0) {
//					damagePlayer();
//				}
//			} else {
//				switch (ticker.getAction()) {
//					case MOVE:
//						if (playerController.numKeyEvents > 1) {
//							damagePlayer();
//							advanceGameState();
//							playerMoved = true;
//						} else if (playerController.numKeyEvents == 1) {
//							PlayerController.KeyEvent event = playerController.keyEvents[0];
//
//							//DEBUGGING CODE
//							RhythmController.isWithinActionWindow(event.time, 0, true);
//
//							// Send a calibration beat
//							if (!calibrationBeatSent) {
//								RhythmController.sendCalibrationBeat(event.time);
//								calibrationBeatSent = true;
//							}
//
//
//							if (RhythmController.isWithinActionWindow(event.time, 0, false)) {
//								Vector2 vel = new Vector2();
//								switch (event.code) {
//									case InputController.CONTROL_MOVE_RIGHT:
//										vel.x = 1;
//										knight.setDirection(Knight.KnightDirection.RIGHT);
//										break;
//									case InputController.CONTROL_MOVE_UP:
//										vel.y = 1;
//										knight.setDirection(Knight.KnightDirection.BACK);
//										break;
//									case InputController.CONTROL_MOVE_LEFT:
//										vel.x = -1;
//										knight.setDirection(Knight.KnightDirection.LEFT);
//										break;
//									case InputController.CONTROL_MOVE_DOWN:
//										vel.y = -1;
//										knight.setDirection(Knight.KnightDirection.FRONT);
//										break;
//								}
//								playerMoved = true;
//								knight.setVelocity(vel);
//								advanceGameState();
//
//								// Display visual feedback to show success
//								knight.showSuccess();
//								// Set current tile type to SUCCESS
//								board.setSuccess((int) knight.getPosition().x, (int) knight.getPosition().y);
//								RhythmController.playSuccess();
//							}
//						}
//						break;
//					case DASH:
//						if (playerController.numKeyEvents > 1) {
//							damagePlayer();
//							advanceGameState();
//							playerMoved = true;
//						} else if (playerController.numKeyEvents == 1) {
//							PlayerController.KeyEvent event = playerController.keyEvents[0];
//
//							//DEBUGGING CODE
//							RhythmController.isWithinActionWindow(event.time, 0, true);
//
//							// Send a calibration beat
//							if (!calibrationBeatSent) {
//								RhythmController.sendCalibrationBeat(event.time);
//								calibrationBeatSent = true;
//							}
//
//
//							if (RhythmController.isWithinActionWindow(event.time, 0, false)) {
//								Vector2 vel = new Vector2();
//								switch (event.code) {
//									case InputController.CONTROL_MOVE_RIGHT:
//										vel.x = 2;
//										knight.setDirection(Knight.KnightDirection.RIGHT);
//										break;
//									case InputController.CONTROL_MOVE_UP:
//										vel.y = 2;
//										knight.setDirection(Knight.KnightDirection.BACK);
//										break;
//									case InputController.CONTROL_MOVE_LEFT:
//										vel.x = -2;
//										knight.setDirection(Knight.KnightDirection.LEFT);
//										break;
//									case InputController.CONTROL_MOVE_DOWN:
//										vel.y = -2;
//										knight.setDirection(Knight.KnightDirection.FRONT);
//										break;
//								}
//								playerMoved = true;
//								knight.setVelocity(vel);
//								advanceGameState();
//
//								// Display visual feedback to show success
//								knight.showSuccess();
//								// Set current tile type to SUCCESS
//								board.setSuccess((int) knight.getPosition().x, (int) knight.getPosition().y);
//								RhythmController.playSuccess();
//							}
//						}
//						break;
//				}
//			}
//		}
	}

	private void advanceGameState () {
		if (gameStateAdvanced) return;
		gameStateAdvanced = true;
		ticker.advance();
		board.updateColors();
		moveEnemies();
		collisionController.update();
		if (collisionController.hasPlayerMoved) knight.setInvulnerable(false);
//		System.out.println(RhythmController.getSequencePosition());

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

	public void damagePlayer() {
		if (!knight.isInvulnerable()) {
//			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
//				System.out.println(ste);
//			}
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
			((EnemyController)controls[i]).nextAction();
			gameObjects.get(i).setVelocity(vel);
		}
	}

	public boolean isGameOver() {
		return gameOver;
	}
}
