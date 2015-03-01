package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.utils.TimeUtils;


/**
 * Class to process enemies and player input
 *
 * As a major subcontroller, this class must have a reference to all the models.
 */
public class GameplayController {

	private Board board;
	private Knight knight;
	private Enemy enemies[];

	private final long startTime;
	private long currentTime;

	private final int numEnemies = 2;
	private final int bpm = 120;
	private final long period = 6000 / bpm;
	private final long tol = 50;
	private final long enemyTol = 25;
	private final long offset = 0;

	private boolean enemiesMoved;
	private boolean playerMoved;

	// Currently, the level is hard-coded, so the constructor doesn't need any inputs
	// In the future, we should call the constructor with the XML file detailing how the level is built.

	public GameplayController() {
		board = new Board();
		knight = new Knight();
		enemies = new Enemy[numEnemies];
		for (Enemy e : enemies){
			e = new Enemy();
		}
		startTime = TimeUtils.millis();
		currentTime = TimeUtils.millis();
	}

	public void resolveActions(InputController inputController) {

		resolvePlayer(inputController);
		resolveEnemies();
		updateBoard();
	}

	/** Resets the level */
	public void reset() {

	}

	/** Whether or not it's time to reset the game */
	public boolean isGameOver(){
		// TODO: fill in this method.
		return false;
	}

	/**
	 * Initializes the board.
	 * Set correct positions for enemies and player. Set what is contained in each cell of the board.
	 * */
	private void initBoard(){

	}


	/**
	 * Check whether the current game state is on beat or not. Very crucial method. Must implement correctly.
	 * */
	public boolean isOnBeat(){
		long modTime = (currentTime - startTime + offset) % period;

		if (modTime < tol || (period - modTime) < tol){
			return true;
		} else {
			return false;
		}
	}

	private void resolvePlayer(InputController inputController) {

	}

	private void resolveEnemies() {
		long modTime = (currentTime - startTime + offset) % period;
		if (modTime < enemyTol || (period - modTime) < enemyTol) {
			if (!enemiesMoved){
				enemiesMoved = true;
				for (Enemy e : enemies){
					e.update();
				}
			}
		} else {
			enemiesMoved = false;
		}
	}

	private void updateBoard(){

	}
}
