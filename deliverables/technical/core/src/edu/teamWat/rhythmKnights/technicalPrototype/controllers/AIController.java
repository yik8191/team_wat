package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import com.badlogic.gdx.math.Vector2;
import edu.teamWat.rhythmKnights.technicalPrototype.models.Board;
import edu.teamWat.rhythmKnights.technicalPrototype.models.GameObjectList;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.GameObject;
import jdk.internal.util.xml.impl.Input;


public class AIController implements InputController{

	// Instance Attributes
	/** The GameObject being controlled by this AIController, typically 
	 * an enemy character */
	private GameObject enemy;
	/** The game board; used for pathfinding */
	private Board board;
	/** The game object's next action (may include firing). */
	private int move; // A ControlCode
	/** The path this enemy will take */
    private Vector2[] path;
    /** Int to show where enemy is in the path */
    private int curPathPlace;
	
	/** The number of ticks since we started this controller. Use this 
	 * 	to control how often enemies move*/
	private long ticks;
	public AIController(int id, GameObjectList gameobjs, Vector2[] path) {
		this.enemy = gameobjs.get(id);
        this.curPathPlace = 0;
        this.path = path;
	}

	public int getAction(){
        Vector2 prev = this.path[this.curPathPlace];
        Vector2 next = this.path[this.curPathPlace + 1];

        if (next.x > prev.x){
            return InputController.CONTROL_MOVE_RIGHT;
        } else if (next.x < prev.x){
            return InputController.CONTROL_MOVE_LEFT;
        } else if (next.y > prev.y){
            return InputController.CONTROL_MOVE_UP;
        } else if (next.y < prev.y){
            return InputController.CONTROL_MOVE_DOWN;
        } else{
            return InputController.CONTROL_NO_ACTION;
        }
	}

    public void nextAction(){
        this.curPathPlace++;
        this.curPathPlace = this.curPathPlace % this.path.length;
    }
}
