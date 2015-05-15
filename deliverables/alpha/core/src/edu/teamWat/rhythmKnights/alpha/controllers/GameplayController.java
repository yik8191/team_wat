package edu.teamWat.rhythmKnights.alpha.controllers;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


import edu.teamWat.rhythmKnights.alpha.JSONReader;
import edu.teamWat.rhythmKnights.alpha.models.*;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.*;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;
import javafx.scene.effect.Light;

import java.awt.*;

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

    //HP stat numbers
	private static int framesPerDrain = 3;
    private static int HPPerDrain = 1;
	private int timeHP = framesPerDrain;
	public boolean hasMoved = false;
	public boolean startLoopDone = false;
	public boolean canPlayerMove = false;

	public int initHP;

	public int startBeatNumber;


	/**DRAWING CODE*/
	public static final String TICK_ONE_FILE = "images/1.png";
	public static final String TICK_TWO_FILE = "images/2.png";
	public static final String TICK_THREE_FILE = "images/3.png";
	public static final String TICK_DANCE_FILE = "images/dance.png";

	public static Texture tickOne;
	public static Texture tickTwo;
	public static Texture tickThree;
	public static Texture tickDance;

	public static void PreLoadContent(AssetManager manager) {
		manager.load(TICK_ONE_FILE, Texture.class);
		manager.load(TICK_TWO_FILE, Texture.class);
		manager.load(TICK_THREE_FILE, Texture.class);
		manager.load(TICK_DANCE_FILE, Texture.class);
	}

	public static void LoadContent(AssetManager manager) {
		if (manager.isLoaded(TICK_ONE_FILE)) {
			tickOne = manager.get(TICK_ONE_FILE, Texture.class);
			tickOne.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			tickOne = null;  // Failed to load
		}
		if (manager.isLoaded(TICK_TWO_FILE)) {
			tickTwo = manager.get(TICK_TWO_FILE, Texture.class);
			tickTwo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			tickTwo = null;  // Failed to load
		}
		if (manager.isLoaded(TICK_THREE_FILE)) {
			tickThree = manager.get(TICK_THREE_FILE, Texture.class);
			tickThree.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			tickThree = null;  // Failed to load
		}
		if (manager.isLoaded(TICK_DANCE_FILE)) {
			tickDance = manager.get(TICK_DANCE_FILE, Texture.class);
			tickDance.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			tickDance = null;  // Failed to load
		}
	}

	public static void UnloadContent(AssetManager manager) {
		if (tickOne != null) {
			tickOne = null;
			manager.unload(TICK_ONE_FILE);
		}
		if (tickTwo != null) {
			tickTwo = null;
			manager.unload(TICK_TWO_FILE);
		}
		if (tickThree != null) {
			tickThree = null;
			manager.unload(TICK_THREE_FILE);
		}
		if (tickDance != null) {
			tickDance = null;
			manager.unload(TICK_DANCE_FILE);
		}
	}

	public GameplayController() {}

	public void initialize(int levelNum) {

		FileHandle levelhandle = Gdx.files.internal("levels/level" + levelNum + ".json");
		FileHandle audiohandle;
		board = JSONReader.parseFile(levelhandle.readString());
        board.setTileSprite(JSONReader.getTileSprite());
		audiohandle = JSONReader.getAudioHandle();

        int hp = JSONReader.getHP();
        int fr = JSONReader.getFrames();
        int lossPerMiss = JSONReader.getLossPerMiss();
        setHPConstants(hp, fr, lossPerMiss);

		JSONReader.getObjects();
		ticker = JSONReader.initializeTicker();

        collisionController = new CollisionController(board, gameObjects);

		knight = (Knight)gameObjects.getPlayer();
		knight.setInvulnerable(true);
		hasMoved = false;
		gameOver = false;
		startLoopDone = false;
		canPlayerMove = false;
		initHP = hp;
		knight.knightHP = 1;
		startBeatNumber = 0;
		try {
			RhythmController.init(audiohandle, ticker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RhythmController.launch();
	}

	public void update() {
		if (hasMoved) {
			timeHP--;
			if (timeHP == 0) {
				knight.decrementHP(HPPerDrain);
				timeHP = framesPerDrain;
			}
		}

		gameStateAdvanced = false;

		board.updateColors();

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
		RhythmController.clearNextAction(nextActionIndex);
		if (RhythmController.getTickerAction(prevActionIndex) == Ticker.TickerAction.FIREBALL2 || RhythmController.getTickerAction(prevActionIndex) == Ticker.TickerAction.DASH2) {
			prevActionIndex--;
		} else if (RhythmController.getTickerAction(nextActionIndex) == Ticker.TickerAction.FIREBALL2 || RhythmController.getTickerAction(nextActionIndex) == Ticker.TickerAction.DASH2){
			nextActionIndex++;
		}
		if (RhythmController.getTick(nextActionIndex) < RhythmController.getTick(prevActionIndex) && currentTick < RhythmController.getTick(prevActionIndex)){
			ticker.indicatorOffsetRatio = ((float)currentTick - (float)RhythmController.getTick(prevActionIndex) + RhythmController.getTrackLength()) / ((float)RhythmController.getTick(nextActionIndex) - (float)RhythmController.getTick(prevActionIndex) + RhythmController.getTrackLength());
		} else if (RhythmController.getTick(nextActionIndex) < RhythmController.getTick(prevActionIndex)) {
			ticker.indicatorOffsetRatio = ((float)currentTick - (float)RhythmController.getTick(prevActionIndex)) / ((float)RhythmController.getTick(nextActionIndex) - (float)RhythmController.getTick(prevActionIndex) + RhythmController.getTrackLength());
		} else {
			ticker.indicatorOffsetRatio = ((float)currentTick - (float)RhythmController.getTick(prevActionIndex)) / ((float)RhythmController.getTick(nextActionIndex) - (float)RhythmController.getTick(prevActionIndex));
		}
		board.setDistanceToBeat(0.5f - Math.abs(ticker.indicatorOffsetRatio - 0.5f));

		int currentActionIndex;
		// Keep clearing the action ahead of us! Simple! :D
		RhythmController.clearNextAction(nextActionIndex);

		if (knight.isAlive() && canPlayerMove) {
			for (PlayerController.KeyEvent keyEvent : playerController.keyEvents) {
				prevActionIndex = RhythmController.getClosestEarlierActionIndex(keyEvent.time);
				nextActionIndex = (prevActionIndex + 1) % RhythmController.numActions;
				if (nextActionIndex > prevActionIndex) {
					if (keyEvent.time - RhythmController.getTick(prevActionIndex) < RhythmController.getTick(nextActionIndex) - keyEvent.time) {
						currentActionIndex = prevActionIndex;
					} else {
						currentActionIndex = nextActionIndex;
					}
 				} else if (RhythmController.getTick(prevActionIndex) < keyEvent.time) {
					if (keyEvent.time - RhythmController.getTick(prevActionIndex) < RhythmController.getTick(nextActionIndex) + RhythmController.getTrackLength() - keyEvent.time){
						currentActionIndex = prevActionIndex;
					} else {
						currentActionIndex = nextActionIndex;
					}
				} else {
					if (keyEvent.time + RhythmController.getTrackLength() - RhythmController.getTick(prevActionIndex) < RhythmController.getTick(nextActionIndex)  - keyEvent.time) {
						currentActionIndex = prevActionIndex;
					} else {
						currentActionIndex = nextActionIndex;
					}
				}

				if ((keyEvent.code & InputController.CONTROL_RELEASE) == 0 && RhythmController.getCompleted(currentActionIndex)) {
					if (knight.isAlive()) damagePlayer();
				} else {
					switch (RhythmController.getTickerAction(currentActionIndex)) {
						case MOVE:
							if ((keyEvent.code & PlayerController.CONTROL_RELEASE) != 0) break;
							RhythmController.setCompleted(currentActionIndex, true);
							RhythmController.setPlayerAction(currentActionIndex, keyEvent.code);
							ticker.glowBeat(currentActionIndex % ticker.numExpandedActions, 15);
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
							if (knight.isAlive && !(knight.getState() == Knight.KnightState.ATTACKING)) {
								knight.showSuccess();
							}
							// Set current tile type to SUCCESS
							board.setSuccess((int) knight.getPosition().x, (int) knight.getPosition().y);
							RhythmController.playSuccess();

							break;
						case DASH:
							if ((keyEvent.code & PlayerController.CONTROL_RELEASE) != 0) break;
							RhythmController.setCompleted(currentActionIndex, true);
							RhythmController.setPlayerAction(currentActionIndex, keyEvent.code);
							ticker.glowBeat(currentActionIndex % ticker.numExpandedActions, 15);
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
							ticker.glowBeat(currentActionIndex % ticker.numExpandedActions, 15);
							if (RhythmController.getPlayerAction(currentActionIndex) != RhythmController.getPlayerAction(currentActionIndex - 1)) {
								if (knight.isAlive()) damagePlayer();
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
								if (knight.isAlive && !(knight.getState() == Knight.KnightState.ATTACKING)) {
									knight.showSuccess();
								}
								hasMoved = true;
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
		}

		playerController.clear();

		prevActionIndex = RhythmController.getClosestEarlierActionIndex(currentTick);
		nextActionIndex = (prevActionIndex + 1) % RhythmController.numActions;

		if (nextActionIndex < prevActionIndex) {
			if (RhythmController.getTick(prevActionIndex) > currentTick) {
				if (RhythmController.getTick(nextActionIndex) - currentTick > currentTick + RhythmController.getTrackLength() - RhythmController.getTick(prevActionIndex)) {
					currentActionIndex = prevActionIndex;
				} else {
					currentActionIndex = nextActionIndex;
				}
			} else {
				if (RhythmController.getTrackLength() - currentTick > currentTick - RhythmController.getTick(prevActionIndex)) {
					currentActionIndex = prevActionIndex;
				} else {
					currentActionIndex = nextActionIndex;
				}
			}
		} else {
			if (RhythmController.getTick(nextActionIndex) - currentTick > currentTick - RhythmController.getTick(prevActionIndex)) {
				currentActionIndex = prevActionIndex;
			} else {
				currentActionIndex = nextActionIndex;
			}
		}

		if ((currentActionIndex == nextActionIndex) && !RhythmController.getCompleted(prevActionIndex)) {
			RhythmController.setCompleted(prevActionIndex, true);
			if (knight.isAlive()) damagePlayer();
			if (RhythmController.getTickerAction(prevActionIndex) != Ticker.TickerAction.DASH2 && RhythmController.getTickerAction(prevActionIndex) != Ticker.TickerAction.FIREBALL2) {
				advanceGameState();
			}
		}
		if (RhythmController.startBeatCount > 0 && RhythmController.getSequencePosition() > RhythmController.startBeatTimes[startBeatNumber]){
			RhythmController.startBeatCount--;
			startBeatNumber++;
		}
	}

	private void advanceGameState() {
		if (gameStateAdvanced) return;
		gameStateAdvanced = true;
		moveEnemies();
		collisionController.update();
		if (collisionController.hasPlayerMoved) knight.setInvulnerable(false);
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
			if (((Enemy)gameObjects.get(i)).isKindaDead()) continue;
			vel.set(0, 0);
			switch (controls[i].getAction()) {
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

			// For animation of enemy
			if (vel.x > 0){
				((Enemy)gameObjects.get(i)).setFacing(Enemy.EnemyDirection.RIGHT);
			} else if(vel.x < 0){
				((Enemy)gameObjects.get(i)).setFacing(Enemy.EnemyDirection.LEFT);
			} else if(vel.y > 0){
				((Enemy)gameObjects.get(i)).setFacing(Enemy.EnemyDirection.BACK);
			} else{
				((Enemy)gameObjects.get(i)).setFacing(Enemy.EnemyDirection.FRONT);
			}

			gameObjects.get(i).setVelocity(vel);
		}
	}

    /* Sets HPPerDrain and framesPerDrain for curent level */
    public static void setHPConstants(int hp, int frames, int lossPerMiss){
        if (hp <= 0 ){
            System.out.println("Can't have a negative HP drain. Setting to default of 1");
            HPPerDrain = 1;
        }else{
            HPPerDrain = hp;
        }
        if (frames <=0){
            System.out.println("Can't have a negative number of frames. Setting to default of 3");
            framesPerDrain = 3;
        }else{
            framesPerDrain = frames;
        }
        if (lossPerMiss < 0){
            System.out.println("Can't have negative lossPerMiss. Setting to default");
            Knight.setHPLossPerMiss(10);
        }else{
            Knight.setHPLossPerMiss(lossPerMiss);
        }
    }

    public void reset(){
        FileHandle audiohandle;
        audiohandle = JSONReader.getAudioHandle();
        JSONReader.getObjects();
        collisionController = new CollisionController(board, gameObjects);
        knight = (Knight)gameObjects.getPlayer();
        knight.setInvulnerable(true);
        hasMoved = false;
        gameOver = false;
	    startBeatNumber = 0;
        playerController.setListenForInput(true);
        RhythmController.stopMusic();
        try {
            RhythmController.init(audiohandle, ticker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RhythmController.launch();
	    startLoopDone = false;
    }

	public void drawStartTicks(GameCanvas canvas) {
		if (startLoopDone) return;
		long period = RhythmController.startBeatTimes[1] - RhythmController.startBeatTimes[0];
		switch (RhythmController.startBeatCount) {
			case 3:
				long dist = Math.abs(RhythmController.getSequencePosition() - RhythmController.startBeatTimes[0]);
				if (dist > period) break;
				Color tint = new Color();
				tint.set(Color.WHITE);
				tint.a = 1 - ((float)dist / (float)period);
				canvas.draw(tickThree, tint, tickThree.getWidth() / 2, tickThree.getHeight() / 2, canvas.getWidth() / 2, canvas.getHeight() / 2, 0, 1, 1);
				break;
			case 2:
				dist = Math.abs(RhythmController.getSequencePosition() - RhythmController.startBeatTimes[1]);
				if (dist > period) break;
				tint = new Color();
				tint.set(Color.WHITE);
				tint.a = 1 - ((float)dist / (float)period);
				canvas.draw(tickTwo, tint, tickThree.getWidth() / 2, tickThree.getHeight() / 2, canvas.getWidth() / 2, canvas.getHeight() / 2, 0, 1, 1);
				break;
			case 1:
				dist = Math.abs(RhythmController.getSequencePosition() - RhythmController.startBeatTimes[2]);
				if (dist > period) break;
				tint = new Color();
				tint.set(Color.WHITE);
				tint.a = 1 - ((float)dist / (float)period);
				canvas.draw(tickOne, tint, tickThree.getWidth() / 2, tickThree.getHeight() / 2, canvas.getWidth() / 2, canvas.getHeight() / 2, 0, 1, 1);
				break;
			case 0:
				dist = Math.abs(RhythmController.getSequencePosition() - RhythmController.startBeatTimes[3]);
				if (dist > period * 0.75f) canPlayerMove = true;
				if (dist > period) {
					startLoopDone = true;
					break;
				}
				tint = new Color();
				tint.set(Color.WHITE);
				tint.a = 1 - ((float)dist / (float)period);
				canvas.draw(tickDance, tint, tickThree.getWidth() / 2, tickThree.getHeight() / 2, canvas.getWidth() / 2, canvas.getHeight() / 2, 0, 3, 3);
				break;
			default:
				dist = Math.abs(RhythmController.getSequencePosition() - RhythmController.startBeatTimes[0]);
				if (dist > period) break;
				tint = new Color();
				tint.set(Color.WHITE);
				tint.a = 1 - ((float)dist / (float)period);
				canvas.draw(tickThree, tint, tickThree.getWidth() / 2, tickThree.getHeight() / 2, canvas.getWidth() / 2, canvas.getHeight() / 2, 0, 1, 1);
				break;
		}
	}


	public boolean isGameOver() {
		return gameOver;
	}
}