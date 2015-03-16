package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import edu.teamWat.rhythmKnights.technicalPrototype.models.*;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.*;

public class GameplayController {
	/** Reference to the game board */
	public Board board;
	/** Reference to all the game objects in the game */	
	public GameObjectList gameObjects;
	/** List of all the input (both player and AI) controllers */
	protected InputController[] controls;
	/** Ticker */
	public Ticker ticker;

	private boolean gameOver = false;

	/**
	* Initializes the ships to new random locations.
	*
	* TODO: need level design
	*/
	private void initGameObjectPositions() {
		// Set the player position
//		float px = board.boardToScreen(board.getWidth() / 2);
//		float py = board.boardToScreen(board.getHeight() / 2);
//		gameobjs.get(0).getPosition().set(px,py);

		// Create a list of available AI positions
		// Assign positions
	}

	public GameplayController() {	}

	public void initialize() {

		board = new Board(13, 7);
		gameObjects = new GameObjectList(9);

		gameObjects.add(new Knight(0, 0, 3));
		gameObjects.add(new DynamicTile(1, 3, 3));
		gameObjects.add(new Skeleton(2, 5, 0));
		gameObjects.add(new Skeleton(3, 5, 6));
		gameObjects.add(new Slime(4, 7, 0));
		gameObjects.add(new Slime(5, 7, 6));
		gameObjects.add(new Slime(6, 9, 2));
		gameObjects.add(new Slime(7, 9, 4));
		gameObjects.add(new Slime(8, 11, 3));

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

		ticker = new Ticker(new Ticker.TickerAction[] {Ticker.TickerAction.MOVE, Ticker.TickerAction.MOVE, Ticker.TickerAction.MOVE, Ticker.TickerAction.DASH});

		controls = new InputController[gameObjects.size()];
		controls[0] = new PlayerController();
		for (int ii = 1; ii < gameObjects.size(); ii++) {
			controls[ii] = new AIController(ii, board, gameObjects);
		}
	}


	public void update() {
		int action = controls[0].getAction();
		
	}

	public boolean isGameOver() {
		return gameOver;
	}
}
