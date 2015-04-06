/*
 * Projectile.java
 * 
 * This is a lightweight class that just has getters and setters. 
 * Because memory 
 * allocation is handled by ProjectilePool, anything more complicated goes
 * through that class instead, 
 * particularly methods that require the Photon to be deleted.
 *
 * Author: Walker M. White, Cristian Zaloj, modified Austin Liu
 * Based on original AI Game Lab by Yi Xu and Don Holden, 2007
 * LibGDX version, 1/24/2015
 */
package edu.teamWat.rhythmKnights.alpha.models;

import com.badlogic.gdx.math.Vector2;

/**
 * An class that represents a single Projectile. 
 * 
 * To count down on memory references, the projectile is "flattened" so that
 * it contains no other objects.
 * 
 * 
 */
public class Projectile {
	
	/**
	 * Enum type to distinguish the type of a photon. 
	 */
	public enum ProjectileType{
		FIRE, ICE 
	}
	
	
	/** Number of animation frames a projectile lives before deleted */
	private static final int MAX_AGE = 96; // 48 originally
	
	/** Projectile position */
	public Vector2 position;
	/** Projectile velocity */
	public Vector2 velocity;
	/** Age for the projectile in frames (for decay) */
	public int age; 
	/** Type of the projectile **/
	public ProjectileType type;

	public boolean dirty;
    /** The size of the photon in pixels (image is square) */
    private static final int PROJECTILE_SIZE  = 40;
	
	/**
	 * Creates a new empty projectile with age -1.
	 * 
	 * Projectiles created this way "do not exist".  This constructor is
	 * solely for preallocation.  To actually use a projectile, use the
	 * allocate() method.
	 */
	public Projectile() {
		this.position = new Vector2();
		this.velocity = new Vector2();
		this.age = -1;
    }
	
	public Vector2 getPosition(){
		return position;
	}
	
	public Vector2 getVelocity(){
		return velocity;
	}
	
	public float getScale(){
		if (type == ProjectileType.FIRE){
			return 2.0f;
		} else {
			return 1.25f;
		}
	}
	
	public int getDiameter(){
		return PROJECTILE_SIZE;
	}
	
//		public float getMass(){
//			return 0.05f;
//		}
	
	/**
	 * "Allocates" a photon by setting its position, velocity and type.
	 *
	 * A newly allocated photon starts with age 0.
	 *
	 * @param x  The x-coordinate of the position
	 * @param y  The y-coordinate of the position
	 * @param vx The x-coordinate of the velocity
	 * @param vx The y-coordinate of the velocity
	 * @param type The type of the photon
	 */
	public void set(float x, float y, float vx, float vy, 
			ProjectileType type) {
		this.position.x  = x;  this.position.y  = y;
		this.velocity.x = vx; this.velocity.y = vy;
		this.age = 0; this.type = type;
	}

	public boolean isAlive() {
		return age < MAX_AGE;
	}

	/**
	 * Destroy this projectile immediately, removing it from the screen.
	 *
	 * This method will mark the projectile as dirty, so that it can be processed
	 * properly later (e.g. its deletion violates the FIFO property of the PhotonQueue
	 * in the previous lab).
	 *
	 * NEVER call this method directly.  This method should only be accessed by
	 * PhotonPool.  Call the destroy() method in PhotonPool instead.
	 */
	public void destroy() {
		age = MAX_AGE;
		dirty = true;
	}

	/**
	 * Age this projectile normally by one frame.
	 *
	 * When the projectile reaches life 0, it is dead.
	 */
	public void age() {
		this.age++;		
	}

	/**
	 * This projectile is dirty and was destroyed prematurely.
	 *
	 * Dirty projectile are those that did not age normally and were deleted "out of order".
	 * Because we are still using a queue structure like the last lab, we have to handle
	 * them specially.
	 */
	public boolean isDirty() {
		return dirty;
	}
}