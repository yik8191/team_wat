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


	public void update(float delta) {
		// TODO: Update the board; E.g state of each tile
		// Might not need this method.
	}


	/** Returns the state of the tile at the requested position
	 *
	 * @param x x-coordinate of requested tile
	 * @param y y-coordinate of requested tile
	 * @return Tile state at (x,y)
	 */
	public TileState getBoardState(int x, int y) {
		// TODO: fill this method
		return TileState.OOB;
	}

	public enum TileState {
		ENEMY,
		PLAYER,
		OBSTACLE,
		START,
		END,
		OOB
	}
}
