package edu.cornell.cs3152.gameplayprototype;

import edu.cornell.cs3152.gameplayprototype.*;
import edu.cornell.cs3152.gameplayprototype.utils.*;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;

import java.security.PublicKey;


/**
 * Board class for storing information about board of 2D tiles.
 *
 * As well as other things :P Fill in description here.
 */
public class Board {
	public int width;
	public int height;

	public TileState[][] tiles;

	public Board(int w, int h){
		width = w;
		height = h;
		tiles = new TileState[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new TileState();
			}
		}
	}


	/** Clears the board, calling clear on each tile. */
	public void clear() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j].clear();
			}
		}
	}


	public void update(float delta) {
		// TODO: Update the board; E.g state of each tile
		// Might not need this method.
	}


	/**
	 * Each tile on the board has a set of attributes associated with it.
	 */
	public static class TileState {
		/** Is this a goal tile?*/
		public boolean goal;
		/** Is this a start tile?*/
		public boolean start;
		/** Is the knight on this tile?*/
		public boolean knight;
		/** Is an enemy on this tile?*/
		public boolean enemy;
		/** Is there an obstacle on this tile?*/
		public boolean obstacle;

		public TileState() {
			goal = false;
			start = false;
			knight = false;
			enemy = false;
			obstacle = false;
		}

		public void clear() {
			goal = false;
			start = false;
			knight = false;
			enemy = false;
			obstacle = false;
		}
	}
	
}
