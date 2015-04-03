package rhythmKnights.alpha.controllers;

import rhythmKnights.alpha.models.Board;
import rhythmKnights.alpha.models.GameObjectList;
import rhythmKnights.alpha.models.gameObjects.GameObject;

import com.badlogic.gdx.math.Vector2;



public class AIController implements InputController{

	// Instance Attributes
	/** The GameObject being controlled by this AIController, typically 
	 * an enemy character */
	private GameObject enemy;
	/** The game board; used for bounds checking */
	private Board board;
	/** The game object's next action (may include firing). */
	private int move; // A ControlCode
	/** The path this enemy will take */
    private int[] path;
    /** Int to show where enemy is in the path */
    private int curPathPlace;
	
	/** The number of ticks since we started this controller. Use this 
	 * 	to control how often enemies move*/
	private long ticks;
	public AIController(int id, GameObjectList gameobjs, int[] path) {
		this.enemy = gameobjs.get(id);
        this.curPathPlace = 0;
        this.path = path;
	}
	public int getAction(){
        return this.path[this.curPathPlace];
	}

    public void nextAction(){
        this.curPathPlace++;
        this.curPathPlace = this.curPathPlace % this.path.length;
    }
}
