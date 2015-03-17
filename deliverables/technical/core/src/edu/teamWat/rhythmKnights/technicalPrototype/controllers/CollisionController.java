/*
 * CollisionController.java
 *
 *
 * Author: Walker M. White, Cristian Zaloj
 * Based on original AI Game Lab by Yi Xu and Don Holden, 2007
 * LibGDX version, 1/24/2015
 * Modified: Team Wat
 */
package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import java.util.Random;

import com.badlogic.gdx.math.*;

import edu.teamWat.rhythmKnights.technicalPrototype.models.*;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.GameObject;
import edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects.Knight;

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
	 * @param s The list of game objects 
	 * TODO: @param p The active projectiles -- eventually
	 */
	public CollisionController(Board b, GameObjectList g) {
		board = b;
		gameobjs = g;
		
		tmp = new Vector2();
	}

	/**
	 * Updates all of the game objects and photons, moving them forward.
	 *
	 */
	public void update() {
		// Move only the player
		if (gameobjs.getPlayer().isActive()){
			moveIfSafe(gameobjs.getPlayer());
		}
		
		// Test collisions between game objects.
		int length = gameobjs.size();
		int ii = 0; int jj;
		// handle player movement first
		for (jj = ii + 1; jj < length; jj++) {
			checkForCollision(gameobjs.get(ii), gameobjs.get(jj));
		}
		
		// clear player velocity
		tmp.x = 0; tmp.y = 0;
		gameobjs.getPlayer().setVelocity(tmp);
		
		// Move everything else
		for (GameObject g: gameobjs){
			if (g.isActive()){
				moveIfSafe(g);
			}
		}
		// handle all enemy movement first
		for (ii = 1; ii < length - 1; ii++) {
			for (jj = ii + 1; jj < length; jj++) {
				checkForCollision(gameobjs.get(ii), gameobjs.get(jj));
			}
		}
		
		// handle final interaction between enemy and player
		ii = 0;
		for (jj = ii + 1; jj < length; jj ++){
			checkForCollision(gameobjs.get(jj), gameobjs.get(ii));
		}
		
		// Test collisions between game objects and projectiles
		/*for (GameObject s : gameobjs) {
			for (Photon p : photons) {
				checkForCollision(s, p);
			}
		}*/
	}


	/** 
	 * Moves the game object according to its velocity
	 * 
	 * This only does something if the new position is safe. 
	 * Otherwise, the game object stays in place.
	 * 
	 * @param g The ship to move.
	 */
	private void moveIfSafe(GameObject g) {
		tmp.set(g.getPosition());
		boolean safeBefore = board.isSafeAt((int)tmp.x, (int)tmp.y);
		
		// Test add velocity
		tmp.add(g.getVelocity());
		boolean safeAfter  = board.isSafeAt((int)tmp.x, (int)tmp.y);

		if (!(safeBefore && !safeAfter)) {
			g.setPosition(tmp);
		}
	}

	
	/**
	 * Bounces a game object back to its original position.*/
	private void bounceBackGameObject(GameObject g){
		tmp = g.getVelocity();
		tmp.x = -tmp.x; tmp.y = -tmp.y;
		g.setVelocity(tmp);
		moveIfSafe(g);
		tmp.x = 0; tmp.y = 0;
		g.setVelocity(tmp);
	}
	
	/**
	 * Handles collisions between game objects, 
	 *
	 * @param g1 The collider - the one moving
	 * @param g2 The collidee - the one getting hit
	 */
	private void checkForCollision(GameObject g1, GameObject g2) {
		// Do nothing if either game object is off the board.
		if (!g1.isActive() || !g2.isActive()) {
			return;
		}

		// Get the tiles for each game object
		int g1x = (int) g1.getPosition().x;
		int g1y = (int) g1.getPosition().y;
		int g2x = (int) g2.getPosition().x;
		int g2y = (int) g2.getPosition().y;

		// If the two game objects occupy the same tile,
		if (g1x == g2x && g1y == g2y) {
			if (g1 instanceof Knight){
				// damage the enemy
				g2.setAlive(false);
				// bounce back the player
				bounceBackGameObject(g1);
				
			} else if (g2 instanceof Knight){
				// damage the player
				if (!((Knight) g2).isInvulnerable()) {
					((Knight) g2).takeDamage();
					((Knight) g2).setInvulnerable(true);
				}
				// bounce back the other object
				bounceBackGameObject(g1);
			} else {
				// bounce back both enemies
				bounceBackGameObject(g1);
				bounceBackGameObject(g2);
			}
		}
	}
}
