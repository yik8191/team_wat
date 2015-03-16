package edu.teamWat.rhythmKnights.technicalPrototype.models;

import java.util.*;

import com.badlogic.gdx.Game;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.GameObject;
import edu.teamWat.rhythmKnights.technicalPrototype.views.GameCanvas;

public class GameObjectList implements Iterable<GameObject>{
	/** The list of ships managed by this object. */
	public GameObject[] gameobjs;
	/** The amount of time that has passed since creation (for animation) */
	private float time;
	/** Custom iterator so we can use this object in for-each loops */
	private GameObjectIterator iterator = new GameObjectIterator();
	
	/**
	 * Create a new ShipList with the given number of ships.
	 *
	 * @param size The number of ships to allocate
	 */
	public GameObjectList(int size) {
		gameobjs = new GameObject[size];
	}
	
	/**
	 * Returns the number of ships in this list
	 *
	 * Returns the number of ships in this list
	 */
	public int size() {
		return gameobjs.length;
	}
	
	/**
	 * Returns the ship for the given (unique) id
	 *
	 * The value given must be between 0 and size-1.
	 *
	 * Returns the ship for the given id
	 */
	public GameObject get(int id) {
		return gameobjs[id];
	}

	/** Adds a gameObject to the list at the gameObject's ID
	 *  Careful not to add a gameObject whose ID is already taken. That would be bad.
	 *
	 * @param gameObject gameObject to be added
	 */
	public void add(GameObject gameObject) { gameobjs[gameObject.getId()] = gameObject; }

	public GameObject getPlayer() {
		return gameobjs[0];
	}

	/** 
	 * Returns the number of ships alive at the end of an update.
	 *
	 * @return the number of ships alive at the end of an update.
	 */
	public int numActive() {
		int objsActive = 0;
		for (GameObject s : this) {
			if (s.isActive()) {
				objsActive++;
			}
		}
		return objsActive;
	}

	/** 
	 * Returns the number of ships alive at the end of an update.
	 *
	 * @return the number of ships alive at the end of an update.
	 */
	public int numAlive() {
		int objsAlive = 0;
		for (GameObject s : this) {
			if (s.isAlive()) {
				objsAlive++;
			}
		}
		return objsAlive;
	}

	/**
	 * Draws the ships to the given canvas.
	 *
	 * This method draws all of the ships in this list. It should be the second drawing
	 * pass in the GameEngine.
	 *
	 * @param canvas the drawing context
	 */
	public void draw(GameCanvas canvas) {
		//draw all objects
		for (GameObject gameObject : this) {
			gameObject.draw(canvas);
		}
	}
	

	/**
	 * Returns a ship iterator, satisfying the Iterable interface.
	 *
	 * This method allows us to use this object in for-each loops.
	 *	 
	 * @return a ship iterator.
	 */
	public Iterator<GameObject> iterator() {
		// Take a snapshot of the current state and return iterator.
		iterator.pos = 0;
		return iterator;
	}
	
	/**
	 * Implementation of a custom iterator.
	 *
	 * Iterators are notorious for making new objects all the time.  We make
	 * a custom iterator to cut down on memory allocation.
	 */
	private class GameObjectIterator implements Iterator<GameObject> {
		/** The current position in the ship list */
		public int pos = 0;
		
		/**
		 * Returns true if there are still items left to iterate.
		 *
		 * @return true if there are still items left to iterate
		 */
		public boolean hasNext() {
			return pos < gameobjs.length;
		}
		
		/**
		 * Returns the next ship.
		 *
		 * Dead ships are skipped, but inactive ships are not skipped.
		 */
		public GameObject next() {
			if (pos >= gameobjs.length) {
				throw new NoSuchElementException();
			}
			int idx = pos;
			do {
				pos++;
			} while (pos < gameobjs.length && !gameobjs[pos].isAlive());
			return gameobjs[idx];
		}
	}
}
