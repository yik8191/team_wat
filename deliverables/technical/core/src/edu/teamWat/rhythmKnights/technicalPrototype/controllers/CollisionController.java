/*
 * CollisionController.java
 *
 *
 * Author: Walker M. White, Cristian Zaloj
 * Based on original AI Game Lab by Yi Xu and Don Holden, 2007
 * LibGDX version, 1/24/2015
 * Modified: Austin Liu
 */
package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import java.util.Random;

import com.badlogic.gdx.math.*;

import edu.teamWat.rhythmKnights.technicalPrototype.models.*;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.GameObject;

/**
 * Class to handle basic collisions in the game.
 *
 * This is essentially a fake physics engine for the game board.
 *
 * As a major subcontroller, this class must have a reference to all the models.
 */

public class CollisionController {
	/** Reference to the game board */
	public Board board; 
	/** Reference to all the game objects in the game */	
	public GameObjectList gameobjs; 
	
	/** Cache attribute for calculations */
	private Vector2 tmp;

	/**
	 * Creates a CollisionController for the given models.
	 *
	 * @param b The game board 
	 * @param s The list of ships 
	 * @param p The active photons
	 */
	public CollisionController(Board b, GameObjectList g) {
		board = b;
		gameobjs = g;
		
		tmp = new Vector2();
	}

	/**
	 * Updates all of the ships and photons, moving them forward.
	 *
	 * This is part of the collision phase, because movement can cause collisions!
	 * That is why we do not combine this with the gameply controller. When we study 
	 * the sense-think-act cycle later, we will see another reason for this design.
	 */
	public void update() {
		// Move live game objects when possible.
		for (GameObject g : gameobjs) {
			if (g.isActive()) {
				moveIfSafe(g);
			}
		}

		// Test collisions between ships.
		int length = ships.size();
		for (int ii = 0; ii < length - 1; ii++) {
			for (int jj = ii + 1; jj < length; jj++) {
				checkForCollision(ships.get(ii), ships.get(jj));
			}
		}

		// Test collisions between ships and photons.
		for (Ship s : ships) {
			for (Photon p : photons) {
				checkForCollision(s, p);
			}
		}
	}
	
	/** 
	 * Moves the ship according to its velocity
	 * 
	 * This only does something if the new position is safe. Otherwise, this ship
	 * stays in place.
	 * 
	 * @param ship The ship to move.
	 */
	private void moveIfSafe(Ship ship) {
		tmp.set(ship.getX(), ship.getY());
		boolean safeBefore = board.isSafeAtScreen(tmp.x, tmp.y);
		
		// Test add velocity
		tmp.add(ship.getVX(), ship.getVY());
		boolean safeAfter  = board.isSafeAtScreen(tmp.x, tmp.y);

		if (!(safeBefore && !safeAfter)) {
			ship.getPosition().set(tmp);
		}
	}

	/**
	 * Keeps nudging the ship until a safe location is found.
	 *
	 * @param ship The ship to nudge.
	 */
	private void safeNudge(Ship ship) {
		int i = 0;
		int tileX, tileY;
		float xNudge, yNudge;
		do {
			xNudge = random.nextFloat() * 2 * NUDGE_AMOUNT - NUDGE_AMOUNT;
			yNudge = random.nextFloat() * 2 * NUDGE_AMOUNT - NUDGE_AMOUNT;
			ship.setX(ship.getX()+xNudge);
			ship.setY(ship.getY()+yNudge);
			tileX = board.screenToBoard(ship.getX());
			tileY = board.screenToBoard(ship.getY());
		} while (!board.isSafeAt(tileX, tileY) && ++i < NUDGE_LIMIT);
	}


	/**
	 * Handles collisions between ships, causing them to bounce off one another.
	 *
	 * This method updates the velocities of both ships: the collider and the
	 * collidee. Therefore, you should only call this method for one of the
	 * ships, not both. Otherwise, you are processing the same collisions
	 * twice.
	 *
	 * @param ship1 The collider
	 * @param ship2 The collidee
	 */
	private void checkForCollision(Ship ship1, Ship ship2) {
		// Do nothing if either ship is off the board.
		if (!ship1.isActive() || !ship2.isActive()) {
			return;
		}

		// Get the tiles for each ship
		int s1x = board.screenToBoard(ship1.getX());
		int s1y = board.screenToBoard(ship1.getY());
		int s2x = board.screenToBoard(ship2.getX());
		int s2y = board.screenToBoard(ship2.getY());

		// If the two ships occupy the same tile,
		if (s1x == s2x && s1y == s2y) {
			// If they have the same (continuous) location, then nudge them.
			if (ship1.getX() == ship2.getX() && ship1.getY() == ship2.getY()) {
				safeNudge(ship1);
				safeNudge(ship2);
			}

			// If this ship is farther from the tile center than the other one,
			if (manhattan(ship1.getX(), ship1.getX(), board.boardToScreen(s1x), board.boardToScreen(s1y))
				> manhattan(ship2.getX(), ship2.getX(), board.boardToScreen(s2x), board.boardToScreen(s2y))
				&& board.isSafeAtScreen(ship1.getX() + (ship1.getX() - ship2.getX()),
										ship1.getY() + (ship1.getY() - ship2.getY()))) {
				// Then push it away.
				ship1.getPosition().add(ship1.getX() - ship2.getX(), ship1.getY() - ship2.getY());
			} else if (board.isSafeAtScreen(ship2.getX() + (ship2.getX() - ship1.getX()), 
											ship2.getY() + (ship2.getY() - ship1.getY()))) {
				// Otherwise, push the other ship away
				ship2.getPosition().add(ship2.getX() - ship1.getX(), ship2.getY() - ship1.getY());
			} else {
				// Neither ship can be pushed away in an appropriate
				// direction, so nudge them.
				safeNudge(ship1);
				safeNudge(ship2);
			}
		}
	}

	/**
	 * Handles collisions between a ship and a photon
	 *
	 *
	 * Recall that when a photon collides with a ship, the tile at that position 
	 * is destroyed.
	 * 
	 * @param ship The ship
	 * @param photon The photon
	 */
	private void checkForCollision(Ship ship, Photon photon) {
		// Do nothing if ship is off the board.
		if (!ship.isActive()) {
			return;
		} else if (ship.getId() == photon.getSource()) {
			// Our own photon; do nothing.
			return;
		}

		// Get the tiles for ship and photon
		int sx = board.screenToBoard(ship.getX());
		int sy = board.screenToBoard(ship.getY());
		int px = board.screenToBoard(photon.getX());
		int py = board.screenToBoard(photon.getY());

		// If the ship and photon occupy the same tile,
		if (sx == px && sy == py) {
			// Have the photon push the ship.
			board.destroyTileAt(sx, sy);
			float x = photon.getPushX() * (board.getTileSize() + board.getTileSpacing());
			float y = photon.getPushY() * (board.getTileSize() + board.getTileSpacing());
			ship.getPosition().add(x,y);

			photons.destroy(photon);
			ship.play(SoundController.BUMP_SOUND);
		}
	}
	
	/**
	 * Returns the manhattan distance between two points
	 *
	 * @return the manhattan distance between two points
	 */
	private float manhattan(float x0, float y0, float x1, float y1) {
		return Math.abs(x1 - x0) + Math.abs(y1 - y0);
	}


	
	
}
