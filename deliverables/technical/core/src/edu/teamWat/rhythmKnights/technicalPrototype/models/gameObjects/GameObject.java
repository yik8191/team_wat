package edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	
	// Instance Attributes
	/** A unique identifier; used to decouple classes. */
	private int id;
	/** Object position */
	private Vector2 position;
	/** Object velocity */
	private Vector2 velocity;
	/** Boolean to track if we are dead yet */
	private boolean isAlive;
	

	/**
	 * Create ship # id at the given position.
	 *
	 * @param id The unique ship id
	 * @param x The initial x-coordinate of the ship
	 * @param y The initial y-coordinate of the ship
	 */
	public GameObject(int id, float x, float y) {
		this.id = id;
		
		position = new Vector2(x,y);
		velocity = new Vector2();
	}


	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}


	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
}
