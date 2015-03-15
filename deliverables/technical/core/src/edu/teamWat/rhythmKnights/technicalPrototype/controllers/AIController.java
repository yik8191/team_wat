package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import edu.teamWat.rhythmKnights.technicalPrototype.models.Board;
import edu.teamWat.rhythmKnights.technicalPrototype.models.GameObjectList;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.GameObject;


public class AIController implements InputController{

	// Instance Attributes
	/** The GameObject being controlled by this AIController, typically 
	 * an enemy character */
	private GameObject enemy;
	/** The game board; used for pathfinding */
	private Board board;
	/** The game object's next action (may include firing). */
	private int move; // A ControlCode
	/** The other game objects. */
	private GameObjectList gameobjs;
	
	/** The number of ticks since we started this controller. Use this 
	 * 	to control how often enemies move*/
	private long ticks;
	public AIController(int id, Board board, GameObjectList gameobjs) {
		// TODO Auto-generated constructor stub
		this.enemy = gameobjs.get(id);
		this.board = board;
		this.gameobjs = gameobjs;
	}
	public int getAction(){
		return 0;
	}
}
