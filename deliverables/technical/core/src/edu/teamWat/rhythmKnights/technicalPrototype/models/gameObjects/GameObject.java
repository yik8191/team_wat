package edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects;

import com.badlogic.gdx.math.*;

public abstract class GameObject {

	/** A unique identifier; used to decouple classes. */
	private int id;
	/** GameObject position */
	private Vector2 position;
	/** GameObject velocity */
	private Vector2 velocity;
	/**
	 * Create game object # id at the given position.
	 *
	 * @param id The unique game object id
	 * @param x The initial x-coordinate of the ship
	 * @param y The initial y-coordinate of the ship
	 */
	public GameObject(int id, float x, float y){
		this.id = id;
		
		position = new Vector2(x,y);
		velocity = new Vector2();
	}
	
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
	
}
