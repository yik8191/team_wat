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

	private Knight knight;
	
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

	public void update() {
		knight = (Knight)gameobjs.getPlayer();
		updateBoard();
		moveKnight();
		moveEnemies();
	}

	void moveKnight() {
		hasPlayerMoved = false;
		if (knight.getVelocity().len() ==  1) {
			Vector2 proposedPos = new Vector2();
			proposedPos.set(knight.getPosition()).add(knight.getVelocity());
			boolean enemyHit = false;
			for (GameObject enemy : gameobjs) {
				if (enemy != knight && !(enemy instanceof DynamicTile)) {
					if (enemy.getPosition().x == proposedPos.x && enemy.getPosition().y == proposedPos.y) {
						((Enemy)enemy).setDead();
						knight.setAttacking();
						enemyHit = true;
					}
				}
			}
			if (!enemyHit) {
				if (board.isSafeAt((int)proposedPos.x, (int)proposedPos.y)) {
					knight.setPosition(proposedPos);
					hasPlayerMoved = true;
				} else if (!board.isOffScreen((int)proposedPos.x, (int)proposedPos.y)) {
					knight.setPosition(proposedPos);
					knight.setFalling();
				}
			}
		} else if (knight.getVelocity().len() == 2) {
			Vector2 proposedPos = new Vector2();
			Vector2 halfPos = new Vector2();
			proposedPos.set(knight.getPosition()).add(knight.getVelocity());
			halfPos.set(knight.getVelocity()).scl(0.5f).add(knight.getPosition());
			for (GameObject enemy : gameobjs) {
				boolean enemyHit = false;
				if (enemy != knight && !(enemy instanceof DynamicTile)) {
					if (enemy.getPosition().x == proposedPos.x && enemy.getPosition().y == proposedPos.y) {
						((Enemy)enemy).setDead();
						knight.setAttacking();
						enemyHit = true;
					} else if (enemy.getPosition().x == halfPos.x && enemy.getPosition().y == halfPos.y){
						((Enemy)enemy).setDead();
						knight.setAttacking();
					}
				}
				if (enemyHit) {
					proposedPos.set(halfPos);
					for (GameObject enemy2 : gameobjs) {
						boolean enemyHit2 = false;
						if (enemy2 != knight && !(enemy2 instanceof DynamicTile)) {
							if (enemy2.getPosition().x == proposedPos.x && enemy2.getPosition().y == proposedPos.y) {
								((Enemy)enemy2).setDead();
								knight.setAttacking();
								enemyHit2 = true;
							}
						}
						if (!enemyHit2) {
							if (board.isSafeAt((int)proposedPos.x, (int)proposedPos.y)) {
								knight.setPosition(proposedPos);
								hasPlayerMoved = true;
							} else if (!board.isOffScreen((int)proposedPos.x, (int)proposedPos.y)) {
								knight.setPosition(proposedPos);
								knight.setFallingAfterAttacking();
							}
						}
					}
				} else {
					if (board.isSafeAt((int)proposedPos.x, (int)proposedPos.y)) {
						knight.setDashing();
						knight.setPosition(proposedPos);
						hasPlayerMoved = true;
					} else if (!board.isOffScreen((int)proposedPos.x, (int)proposedPos.y)) {
						knight.setPosition(proposedPos);
						knight.setFalling();
					} else {
						if (board.isSafeAt((int)halfPos.x, (int)halfPos.y)){
							knight.setPosition(halfPos);
							hasPlayerMoved = true;
						} else if (!board.isOffScreen((int)halfPos.x, (int)halfPos.y)) {
							knight.setPosition(halfPos);
							knight.setFalling();
						}
					}
				}
			}
		}
		knight.getVelocity().set(0, 0);
	}

	void moveEnemies() {
		for (GameObject g : gameobjs) {
			if (g instanceof Enemy && g.isAlive()) {
				Enemy enemy = (Enemy) g;
				if (enemy.isKindaDead()) continue;
				Vector2 proposedPos = new Vector2();
				proposedPos.set(enemy.getPosition()).add(enemy.getVelocity());
				boolean canMove = true;
				for (int i = 0; i < gameobjs.size(); i++) {
					GameObject gameObject = gameobjs.get(i);
					if (gameObject.getPosition().x == proposedPos.x && gameObject.getPosition().y == proposedPos.y) {
						if (gameObject instanceof Knight) {
							knight.takeDamage();
							enemy.setAttacking();
							canMove = false;
						} else if (gameObject instanceof Enemy) {
							canMove = false;
						}
					}
				}
				if (canMove) {
					if (board.isSafeAt((int)proposedPos.x, (int)proposedPos.y)) {
						enemy.setPosition(proposedPos);
					}
				}
				enemy.getVelocity().set(0, 0);
			}
		}
	}
}
