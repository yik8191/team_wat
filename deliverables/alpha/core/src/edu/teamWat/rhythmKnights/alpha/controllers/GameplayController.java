package edu.teamWat.rhythmKnights.alpha.controllers;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


import edu.teamWat.rhythmKnights.alpha.JSONReader;
import edu.teamWat.rhythmKnights.alpha.models.*;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.*;
import javafx.scene.effect.Light;

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

	public int initHP;

	public GameplayController() {
	}

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
		initHP = hp;
		knight.knightHP = 1;
		try {
			RhythmController.init(audiohandle, ticker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RhythmController.launch();
	}

//	int moved = 0;

	public void update() {
		if (hasMoved) {
			timeHP--;
			if (timeHP == 0) {
				knight.decrementHP(HPPerDrain);
				timeHP = framesPerDrain;
			}
		} else {
//			// Animate hp filling
//			if (knight.knightHP < knight.INITIAL_HP) {
//				knight.knightHP+=3;
//			} else {
//				knight.knightHP = knight.INITIAL_HP;
//			}
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
		ticker.setBeat(RhythmController.convertToTickerBeatNumber(prevActionIndex, ticker));
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
//		System.out.println(0.5f - Math.abs(ticker.indicatorOffsetRatio - 0.5f));

		int currentActionIndex;
		// Keep clearing the action ahead of us! Simple! :D
		RhythmController.clearNextAction(nextActionIndex);

		if (knight.isAlive()) {
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

				long nextTick = RhythmController.getTick(nextActionIndex);
				long prevTick = RhythmController.getTick(prevActionIndex);

				float distToClosestBeat = 0;
				if (prevTick < currentTick && currentTick < nextTick) { // prevTick < currentTick < nextTick
					distToClosestBeat = (float)(currentTick - prevTick) / (float)(nextTick - prevTick);
				} else if (prevTick < currentTick && nextTick < currentTick) { // nextTick < prevTick < currentTick
					distToClosestBeat = (float) (currentTick - prevTick) / (float)(nextTick + RhythmController.getTrackLength() - prevTick);
				} else { // currentTick < nextTick < prevTick
					distToClosestBeat = (float) (currentTick + RhythmController.getTrackLength() - prevTick) / (nextTick + RhythmController.getTrackLength() - prevTick);
				}
				if (distToClosestBeat > 0.5f) distToClosestBeat = 1 - distToClosestBeat;
//				System.out.println(distToClosestBeat);

				if ((keyEvent.code & InputController.CONTROL_RELEASE) == 0 && RhythmController.getCompleted(currentActionIndex)) {
					if (knight.isAlive()) damagePlayer();
				} else {
					switch (RhythmController.getTickerAction(currentActionIndex)) {
						case MOVE:
//						if (moved > 2) {
//							double a = 0;
//						}
							if ((keyEvent.code & PlayerController.CONTROL_RELEASE) != 0) break;
							RhythmController.setCompleted(currentActionIndex, true);
							RhythmController.setPlayerAction(currentActionIndex, keyEvent.code);
							ticker.glowBeat(currentActionIndex % ticker.numExpandedActions, 15);
//							System.out.println(0.5f - Math.abs(ticker.indicatorOffsetRatio - 0.5f));
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
							board.setSuccess((int)knight.getPosition().x, (int)knight.getPosition().y);
							RhythmController.playSuccess();

							break;
						case DASH:
							if ((keyEvent.code & PlayerController.CONTROL_RELEASE) != 0) break;
							RhythmController.setCompleted(currentActionIndex, true);
							RhythmController.setPlayerAction(currentActionIndex, keyEvent.code);
							ticker.glowBeat(currentActionIndex % ticker.numExpandedActions, 15);
//							System.out.println(0.5f - Math.abs(ticker.indicatorOffsetRatio - 0.5f));
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
	}

	private void advanceGameState() {
		if (gameStateAdvanced) return;
		gameStateAdvanced = true;
		//ticker.advance();
		moveEnemies();
		collisionController.update();
		if (collisionController.hasPlayerMoved) knight.setInvulnerable(false);
//		System.out.println(RhythmController.getSequencePosition());
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
        JSONReader.getObjects();
        collisionController = new CollisionController(board, gameObjects);
        knight = (Knight)gameObjects.getPlayer();
        knight.setInvulnerable(true);
        hasMoved = false;
        gameOver = false;
    }


	public boolean isGameOver() {
		return gameOver;
	}
}