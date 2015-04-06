/**
 * 
 * ProjectilePool.java
 *
 * This class implements a "particle system" that manages the projectiles fired
 * by game objects in the game.  When a game object fires a projectile, it 
 * adds it to this particle system.  The particle system is responsible 
 * for moving (and drawing) the projectile.  It also keeps track of the age 
 * of the projectile.  Projectiles that are too old are deleted, so that they are not bouncing about the game
 * forever.
 *
 * Author: Walker M. White, modified Austin Liu
 * Based on original GameX Ship Demo by Rama C. Hoetzlein, 2002 and
 * original AI Game Lab by Yi Xu and Don Holden, 2007
 */

package edu.teamWat.rhythmKnights.alpha.models;

import java.util.*;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.assets.AssetManager;

import edu.teamWat.rhythmKnights.alpha.models.Projectile.ProjectileType;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

/**
 * Model class representing an "particle system" of projectiles.
 *
 * Note that the graphics resources in this class are static. 
 */
public class ProjectilePool implements Iterable<Projectile>{
	
	// GRAPHICS RESOURCES
	// Pathname to texture assets
	private final static String PROJECTILE_TEXTURE = "images/projectile.png";	

	/** Image representing a single projectile. 
	 * Static since all projectiles are same, for now. */
	private static Texture texture;
	
	/** 
	 * Preloads the texture information for the projectiles.
	 * 
	 * All projectiles use the same image for now, 
	 * so this is a static method.  This 
	 * keeps us from loading the image multiple times for more than one 
	 * Projectile object.
	 *
	 * The asset manager for LibGDX is asynchronous.  That means that you
	 * tell it what to load and then wait while it loads them.  This is 
	 * the first step: telling it what to load.
	 * 
	 * @param manager Reference to global asset manager.
	 */
	public static void PreLoadContent(AssetManager manager) {
		manager.load(PROJECTILE_TEXTURE,Texture.class);
	}
	
	/** 
	 * Loads the texture information for the projectiles.
	 * 
	 * All projectiles use the same image, so this is a static method.  This 
	 * keeps us from loading the image multiple times for more than one 
	 * projectile object.
	 *
	 * The asset manager for LibGDX is asynchronous.  That means that you
	 * tell it what to load and then wait while it loads them.  This is 
	 * the second step: extracting assets from the manager after it has
	 * finished loading them.
	 * 
	 * @param manager Reference to global asset manager.
	 */
	public static void LoadContent(AssetManager manager) {
		texture = manager.get(PROJECTILE_TEXTURE,Texture.class);
	}

	/** 
	 * Unloads the texture information for the projectiles.
	 * 
	 * This method erases the static variable.  It also deletes the
	 * associated texture from the assert manager.
	 * 
	 * @param manager Reference to global asset manager.
	 */
	public static void UnloadContent(AssetManager manager) {
		if (texture != null) {
			texture = null;
			manager.unload(PROJECTILE_TEXTURE);
		}
	}	

	// Private constants to avoid use of "magic numbers"
	
	/** Maximum number of projectiles allowed on screen at a time. */
	private static final int MAX_PROJECTILES = 512;
	/** Fixed velocity for a projectile */
	private static final float PROJECTILE_VELOCITY = 5.0f;

	// QUEUE DATA STRUCTURES
	/** Array implementation of a circular queue. */
	protected Projectile[] queue;
	/** Index of head element in the queue */
	protected int head;
	/** Index of tail element in the queue */
	protected int tail;
	/** Number of elements currently in the queue */
	protected int size;
	

	/** Custom iterator so we can use this object in for-each loops */
	private ProjectileIterator iterator = new ProjectileIterator();
	
	/**
	 *  Constructs a new (empty) ProjectilePool
	 */
	public ProjectilePool() {
		// Construct the queue.
		queue = new Projectile[MAX_PROJECTILES];
		
        head = 0;
        tail = -1;
        size = 0;

        // "Predeclare" all the projectiles for efficiency
        for (int ii = 0; ii < MAX_PROJECTILES; ii++) {
        	queue[ii] = new Projectile();
        }
	}

	/**
	 * Updates all of the projectiles in this pool.
	 *
	 * This method should be called once per game loop.  It moves projectiles 
	 * forward and deletes any projectiles that have gotten too old.
	 */
	public void update() {
		for (Projectile p : this) {
			p.age();
			p.getPosition().add(p.getVelocity());
		}

		// Remove dead photons
        while (size > 0 && !queue[head].isAlive()) {
        	// As photons are predeclared, all we have to do is move head forward.
            if (!queue[head].isDirty()) { size--; }
            head = ((head + 1) % queue.length);
        }
	}
	
	
	
	/**
	 * Draws the projectiles to the drawing canvas.
	 *
	 * This method uses additive blending, which is set before this method is
	 * called (in GameMode).
	 *
	 * @param canvas The drawing canvas.
	 */
	public void draw(GameCanvas canvas) {
		// Get projectile texture origin
		float ox = texture.getWidth()/2.0f;
		float oy = texture.getHeight()/2.0f;
		
		// avoid repeated memory allocation
		Color pcolor = new Color();
		
		// Step through each active projectile in the queue.
	    for (int ii = 0; ii < size; ii++) {
	    	// Find the position of this projectile.
	        int idx = ((head + ii) % MAX_PROJECTILES);
	        
	        if (queue[idx].type == ProjectileType.ICE){
	        	// How big to make the photon.
	        	float scale = queue[idx].getScale();
	        	float r = 0.8f - (float)queue[idx].age * 0.1f;
	        	float g = 1.0f - (float)queue[idx].age * 0.7f;
	        	float b = 1.0f - (float)queue[idx].age * 0.9f;
	        	float a = 1.0f - (float)queue[idx].age * 0.5f;
	        	pcolor.set(r,g,b,a);
	        	// Use this information to draw.
	        	canvas.draw(texture,pcolor,ox,oy,queue[idx].position.x,
	        			queue[idx].position.y,0,scale,scale);
	        } else if (queue[idx].type == ProjectileType.FIRE){
	        	// How big to make the photon.  Decreases with age.
	        	float scale = queue[idx].getScale();
	        	float r = 1.0f - (float)queue[idx].age * 0.1f;
	        	float g = 0.5f - (float)queue[idx].age * 0.5f;
	        	float b = 0.5f - (float)queue[idx].age * 0.5f;
	        	float a = 1.0f - (float)queue[idx].age * 0.5f;
	        	pcolor.set(r,g,b,a);
	        	// Use this information to draw.
	        	canvas.draw(texture,pcolor,ox,oy,queue[idx].position.x,
	        			queue[idx].position.y,0,scale,scale);
	        }
	    }
	}

	/**
	 * Allocates a new projectile with the given attributes.
	 *
	 * @param x  The initial x-coordinate of the projectile 
	 * @param y  The initial y-coordinate of the projectile 
	 * @param vx The x-value of the projectile velocity
	 * @param vy The y-value of the projectile velocity
	 * @param type The type of the projectile
	 */
	public void allocate(float x, float y, float vx, float vy, ProjectileType type) {
		// Check if any room in queue.  
		// If maximum is reached, remove the oldest projectile.
        if (size == queue.length) {
        	head = ((head + 1) % queue.length);
        	size--;
        }
        
        // Add a new projectile at the end.
        // Already declared, so just initialize.
        tail = ((tail + 1) % queue.length);
        queue[tail].set(x, y, vx, vy, type);
        size++;
	}
	
	/**
	 * Destroys the given projectile, removing it from the pool.
	 *
	 * A destroyed projectile reduces the size so that it is not drawn or used in
	 * collisions.  However, the memory is not reclaimed immediately.  It will
	 * only be reclaimed when we reach it in the queue.
	 *
	 * @param p the projectile to destroy
	 */
	public void destroy(Projectile p) {
		p.destroy();
		size--;
	}

	/**
	 * Returns a projectile iterator, satisfying the Iterable interface.
	 *
	 * This method allows us to use this object in for-each loops.
	 *	 
	 * @return a projectile iterator.
	 */
	public Iterator<Projectile> iterator() {
		// Take a snapshot of the current state and return iterator.
		iterator.limit = size;
		iterator.pos = head;
		iterator.cnt = 0;
		return iterator;
	}
	
	/**
	 * Implementation of a custom iterator.
	 *
	 * Iterators are notorious for making new objects all the time.  We make
	 * a custom iterator to cut down on memory allocation.
	 */
	private class ProjectileIterator implements Iterator<Projectile> {
		/** The current position in the photon queue */
		public int pos = 0;
		/** The number of projectiles shown already */
		public int cnt = 0;
		/** The number of projectiles to iterator over (snapshot to allow deletion) */
		public int limit =0;
		
		/**
		 * Returns true if there are still items left to iterate.
		 *
		 * @return true if there are still items left to iterate
		 */
		public boolean hasNext() {
			return cnt < limit;
		}
		
		/**
		 * Returns the next projectile.
		 *
		 * While it is safe to delete this projectile, it is not safe to delete
		 * other projectiles while this is running.
		 */
		public Projectile next() {
			if (cnt > limit) {
				throw new NoSuchElementException();
			}
			int idx = pos;
			do {
				pos = ((pos+1) % queue.length);
			} while (!queue[pos].isAlive());
			cnt++;
			return queue[idx];
		}
	}
}
