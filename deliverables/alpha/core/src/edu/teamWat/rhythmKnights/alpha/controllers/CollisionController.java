/*
 * CollisionController.java
 *
 *
 * Author: Walker M. White, Cristian Zaloj
 * Based on original AI Game Lab by Yi Xu and Don Holden, 2007
 * LibGDX version, 1/24/2015
 * Modified: Team Wat
 */
package edu.teamWat.rhythmKnights.alpha.controllers;

import java.util.Random;

import com.badlogic.gdx.math.*;

import edu.teamWat.rhythmKnights.alpha.models.*;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.DynamicTile;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.Enemy;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.GameObject;
import edu.teamWat.rhythmKnights.alpha.models.gameObjects.Knight;

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
	/** Reference to all active projectiles */
	public ProjectilePool projs;
	
	/** Cache attribute for calculations */
	private Vector2 tmp;
	/** Whether the player has moved */
	public boolean hasPlayerMoved;
	
	/**
	 * Creates a CollisionController for the given models.
	 *
	 * @param b The game board
	 * TODO: @param p The active projectiles -- eventually
	 */
	public CollisionController(Board b, GameObjectList g, ProjectilePool p) {
		board = b;
		gameobjs = g;
		projs = p;
		tmp = new Vector2();
	}

	/**
	 * Update game board due to dynamic tile states. */
	public void updateBoard() {
		for (GameObject g: gameobjs){
			if (g instanceof DynamicTile){
				board.setTile((int) g.getPosition().x, 
						(int) g.getPosition().y, Board.Tile.tileType.NORMAL);
			}
		}
	}
		
	/**
	 * Updates all of the game objects and projectiles, moving them forward.
	 *
	 */
	public void update() {
		// update dynamic tiles 
		updateBoard();
		// initialize boolean
		hasPlayerMoved = false;
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
		for (GameObject g : gameobjs) {
			for (Projectile p : projs) {
				checkForCollision(g, p);
			}
		}
		
		// update dynamic tiles 
		updateBoard();
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
		
		boolean nonzerov = (g.getVelocity().x != 0 || g.getVelocity().y != 0);
		tmp.set(g.getPosition());
		
		// Test add velocity
		tmp.add(g.getVelocity());
		boolean safeAfter  = board.isSafeAt((int)tmp.x, (int)tmp.y);

		if (safeAfter) {
			// update boolean only if the player actually can make a move
			if ((g instanceof Knight) && nonzerov){
				hasPlayerMoved = true;
				g.setPosition(tmp);
			} else {
				g.setPosition(tmp);
			}
		}
		if ((g instanceof DynamicTile) && nonzerov){
			board.setTile((int) g.getPosition().x, (int) g.getPosition().y, Board.Tile.tileType.OBSTACLE);
			// move player with DynamicTile if necessary
			if (g.getPosition().x == gameobjs.getPlayer().getPosition().x && g.getPosition().y == gameobjs.getPlayer().getPosition().y){
				gameobjs.getPlayer().setPosition(tmp);
				((Knight) gameobjs.getPlayer()).setInvulnerable(true);
			}
			g.setPosition(tmp);
			
		}
	}

	
	/**
	 * Bounces a game object back to its original position.*/
	private void bounceBackGameObject(GameObject g){
		tmp.set(g.getVelocity());
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
				// damage the enemy if it is not a dynamictile
				if (!(g2 instanceof DynamicTile)){
					g2.setAlive(false);
					// bounce back the player
					bounceBackGameObject(g1);
					hasPlayerMoved = true;
					// Uncomment once the attacking sprites have been put in!
					// ((Knight) g1).setState(Knight.KnightState.ATTACKING);
				}
			} else if (g2 instanceof Knight && g1 instanceof Enemy){
				// damage the player if enemy ran into player
				if (!((Knight) g2).isInvulnerable()) {
					((Knight) g2).takeDamage();
					((Knight) g2).setInvulnerable(true);
				}
				// bounce back the other object
				bounceBackGameObject(g1);
			} else if (g1.isCharacter() && g2.isCharacter()) {
				// bounce back both characters 
				bounceBackGameObject(g1);
				bounceBackGameObject(g2);
			}
		}
	}

	private void checkForCollision(GameObject g, Projectile p) {
		// Do nothing if either game object is off the board.
		if (!g.isActive() || !p.isAlive()) {
			return;
		}

		// Get the tiles for each game object
		int gx = (int) g.getPosition().x;
		int gy = (int) g.getPosition().y;
		int px = (int) p.getPosition().x;
		int py = (int) p.getPosition().y;
		// TODO : figure out damage
	}
}
