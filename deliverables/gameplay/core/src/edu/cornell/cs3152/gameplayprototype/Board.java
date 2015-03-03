package edu.cornell.cs3152.gameplayprototype;

import edu.cornell.cs3152.gameplayprototype.*;
import edu.cornell.cs3152.gameplayprototype.utils.*;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;


/**
 * Board class for storing information about board tiles.
 *
 * As well as other things :P Fill in description here.
 */
public class Board {

	public TileState[][] tiles;

	public void update(float delta) {
		// TODO: Update the board; E.g state of each tile
		// Might not need this method.
	}

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
	}
	
}
