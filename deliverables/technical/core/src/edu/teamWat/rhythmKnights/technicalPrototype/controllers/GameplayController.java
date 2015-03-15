package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import edu.teamWat.rhythmKnights.technicalPrototype.models.*;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.*;

public class GameplayController {
	/** Reference to the game board */
	public Board board;
	/** Reference to all the game objects in the game */	
	public GameObjectList gameobjs;
	/** List of all the input (both player and AI) controllers */
	protected InputController[] controls;
	/**
	* Creates a GameplayController for the given models.
	*
	* @param board The game board
	* @param ships The list of ships
	* @param TODO: add projectile list
	*/
	public GameplayController(Board board, GameObjectList gameobjs) {
		this.board = board;
		this.gameobjs = gameobjs;
		initGameObjectPositions();
		controls = new InputController[gameobjs.size()];
		controls[0] = new PlayerController();
		for(int ii = 1; ii < gameobjs.size(); ii++) {
			controls[ii] = new AIController(ii,board,gameobjs);
		}
	}
	/**
	* Initializes the ships to new random locations.
	*
	* TODO: need level design
	*/
	private void initGameObjectPositions() {
		// Set the player position
		float px = board.boardToScreen(board.getWidth() / 2);
		float py = board.boardToScreen(board.getHeight() / 2);
		gameobjs.get(0).getPosition().set(px,py);
		// Create a list of available AI positions
		// Assign positions
	}

}
