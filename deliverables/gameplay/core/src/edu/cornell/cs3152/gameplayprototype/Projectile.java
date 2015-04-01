package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.math.Vector2;

/**
 * Class to represent a projectile object.
 * 
 * Projectile objects are lightweight, and just have getters and setters.  All of
 * the logic (and memory management) is provided by ProjectileQueue.
 * 
 * Based on Photons in CS 3152 Game Labs 1 and 2  
 */
public class Projectile {

	/** Projectile position */
	private Vector2 position;
	/** Projectile velocity */
	private Vector2 velocity;
	
	/**
	 * Returns the x-coordinate of the projectile position
	 *
	 * @return the x-coordinate of the projectile position
	 */
	public float getX() {
		return position.x;
	}

	/**
	 * Sets the x-coordinate of the projectile position
	 *
	 * @param value the x-coordinate of the projectile position
	 */
	public void setX(float value) {
		position.x = value;
	}

	/**
	 * Returns the y-coordinate of the projectile position
	 *
	 * @return the y-coordinate of the projectile position
	 */
	public float getY() {
		return position.y;
	}

	/**
	 * Sets the y-coordinate of the projectile position
	 *
	 * @param value the y-coordinate of the projectile position
	 */
	public void setY(float value) {
		position.y = value;
	}
	
	/**
	 * Returns the position of this projectile.
	 *
	 * This method returns a reference to the underlying projectile position vector.
	 * Changes to this object will change the position of the projectile.
	 *
	 * @return the position of this projectile.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Returns the x-coordinate of the projectile velocity
	 *
	 * @return the x-coordinate of the projectile velocity
	 */
	public float getVX() {
		return velocity.x;
	}

	/**
	 * Sets the x-coordinate of the projectile velocity
	 *
	 * @param value the x-coordinate of the projectile velocity
	 */
	public void setVX(float value) {
		velocity.x = value;
	}

	/**
	 * Returns the y-coordinate of the projectile velocity
	 *
	 * @return the y-coordinate of the projectile velocity
	 */
	public float getVY() {
		return velocity.y;
	}

	/**
	 * Sets the y-coordinate of the projectile velocity
	 *
	 * @param value the y-coordinate of the projectile velocity
	 */
	public void setVY(float value) {
		velocity.y = value;
	}

	/**
	 * Returns the velocity of this projectile.
	 *
	 * This method returns a reference to the underlying projectile velocity vector.
	 * Changes to this object will change the velocity of the projectile.
	 *
	 * @return the velocity of this projectile.
	 */	
	public Vector2 getVelocity() {
		return velocity;
	}
	
}
