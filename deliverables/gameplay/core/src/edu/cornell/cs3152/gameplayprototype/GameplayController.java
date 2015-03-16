package edu.cornell.cs3152.gameplayprototype;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;


/**
 * Class to process enemies and player input
 *
 * As a major subcontroller, this class must have a reference to all the models.
 * It contains a reference to inputController to handle inputs from the player
 */
public class GameplayController {

	/** Reference to the game board. */
	public Board board;
	
	public Knight knight;
	public Enemy enemies[];
	
	private long startTime;   // note that the start time may need to be reset
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
		board = new Board(9,3);
		knight = new Knight(new Vector2(0,1));
		enemies = new Enemy[numEnemies];
		Vector2[] path0 = new Vector2[4];
		Vector2[] path1 = new Vector2[4];
		
		path0[0] = new Vector2(5,2); 
		path0[1] = new Vector2(4,2); 
		path0[2] = new Vector2(3,2); 
		path0[3] = new Vector2(4,2); 
		enemies[0] = new Enemy(new Vector2(5,2), path0);
		
		path1[0] = new Vector2(4,0); 
		path1[1] = new Vector2(5,0); 
		path1[2] = new Vector2(4,0); 
		path1[3] = new Vector2(3,0); 
		enemies[1] = new Enemy(new Vector2(4,0), path1);
		
		startTime = TimeUtils.millis();
		currentTime = TimeUtils.millis();
		enemiesMoved = false;
		playerMoved = false;
	}

	/**
	 * Main method to handle updating player and enemy actions, as
	 * well as all interactions that result.
	 */
	public void resolveActions(InputController inputController) {
		resolvePlayer(inputController);
		resolveEnemies();
		updateBoard(inputController);
	}

	/** Resets the level */
	public void reset() {
		// probably need to print some sort of exit message or lose message
		initBoard();
		knight.position = new Vector2(0,1);
		enemies[0].position = new Vector2(5,2);
		enemies[0].currentStep = 1;
		enemies[1].position = new Vector2(4,0);
		enemies[1].currentStep = 1;
	}

	/** Whether or not it's time to reset the game */
	public boolean isGameOver(){ 
		return false;
	}

	/**
	 * Initializes the board.
	 * Set correct positions for enemies and player. 
	 * Set what is contained in each cell of the board.
	 * */
	private void initBoard(){
		// set the position of the knight
		// set the position of the enemy
		// set the type of each tile on the board
		
	}


	/**
	 * Check whether the current game state is on the beat for the player. 
	 * Very crucial method. Must implement correctly.
	 * */
	public boolean isPlayerOnBeat(){
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
				knight.update(inputController.getAction());
			}
		} else {
			playerMoved = false;
		}
		// need to set isAlive() for the knight at the end of this thing
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

	/**
	 * Updates the board to handle interactions.
	 */
	private void updateBoard(InputController inputController){
		if ((inputController.getAction()& InputController.CONTROL_RESET) == 
				InputController.CONTROL_RESET){
			reset();
		}
	}

	/** Initializes the board, enemy, and character positions. Sets time to 0. */
	public void initialize() {

		board.clear();

		// Start tile
		board.tiles[0][1].start = true;

		// Goal tile
		board.tiles[8][1].goal = true;

		// Obstacles
		board.tiles[0][0].obstacle = true;
		board.tiles[0][2].obstacle = true;
		board.tiles[8][0].obstacle = true;
		board.tiles[8][2].obstacle = true;
		board.tiles[2][1].obstacle = true;
		board.tiles[3][1].obstacle = true;
		board.tiles[4][1].obstacle = true;
		board.tiles[5][1].obstacle = true;
		board.tiles[6][1].obstacle = true;
	}
}
