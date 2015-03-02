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
	private final int bpm = 120;         		// beats per minute
	private final long period = 6000 / bpm;
	private final long playerTol = 50;         	// player time tolerance in ms
	private final long enemyTol = 25;			// enemy time tolerance in ms
	private final long offset = 0;				// account for delay in frame render

	private boolean enemiesMoved;
	private boolean playerMoved;

	// Currently, the level is hard-coded, so the constructor 
	// doesn't need any inputs. In the future, we should call 
	// the constructor with the XML file detailing how the level is built.

	/**
	 * Controller to handle player input and gameplay interactions.
	 * 
	 * This controller must have a reference to all models.
	 */
	public GameplayController() {
		board = new Board();
		knight = new Knight();
		enemies = new Enemy[numEnemies];
		for (Enemy e : enemies){
			e = new Enemy();
		}
		startTime = TimeUtils.millis();
		currentTime = TimeUtils.millis();
		enemiesMoved = false;
		playerMoved = false;
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
	 * Set correct positions for enemies and player. 
	 * Set what is contained in each cell of the board.
	 * */
	private void initBoard(){

	}


	/**
	 * Check whether the current game state is on the beat. 
	 * Very crucial method. Must implement correctly.
	 * */
	public boolean isOnBeat(){
		long modTime = (currentTime - startTime + offset) % period;
		if (modTime < playerTol || (period - modTime) < playerTol){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Updates the player if he or she moves on an on-beat.
	 * The player has to restart otherwise
	 * @param inputController
	 */
	private void resolvePlayer(InputController inputController) {
		long modTime = (currentTime - startTime + offset) % period;
		if (modTime < playerTol || (period - modTime) < playerTol) {
			if (!playerMoved){
				playerMoved = true;
				knight.update();
			}
		} else {
			playerMoved = false;
		}
	}

	
	/**
	 * Updates the enemies when the on-beat is reached
	 */
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
