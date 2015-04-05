package edu.teamWat.rhythmKnights.alpha.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import edu.teamWat.rhythmKnights.alpha.utils.FilmStrip;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

import java.util.Random;

public class Board {
    /* Width of current board */
    private int width;
    /* Height of current board */
    private int height;
	/*Which color tiles should be*/
	private int colorInd = 0;

    /* Variables for tile sprite */
    public static final String TILE_FILE = "images/tileFull.png";
    public static Texture tileTexture;

    /* Holds the array of tiles that make up this board */
    private Tile[][] tiles;

    /** Initialize a board with dimensions w x h with empty tiles*/
    public Board(int w, int h){
        width = w;
        height = h;
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    /** Clears the board by calling clear on each tile, but maintains dimensions. */
    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j].clear();
            }
        }
    }

    /* Destroy the board by making it null (you need to initialize it again after this)*/
    public void destroy(){
        tiles = null;
    }

    public void update(float delta) {
        // TODO: Update the board; E.g state of each tile
        // Might not need this method.
    }

    /*Set a certain tile to have all listed variables*/
    public void setTile(int x, int y, Tile.tileType t){
        this.tiles[x][y].type = t;
        this.tiles[x][y].setColor();
    }

    public void draw(GameCanvas canvas){
        for (int i=0; i<this.width; i++){
            for (int j=0; j<this.height; j++){
                Vector2 loc = canvas.boardToScreen(i,j);
                Color c = tiles[i][j].col;
                float scale = (float)canvas.tileSize/(float)tileTexture.getHeight();
                //texture, color, sprite origin x/y, x/y offset, angle, scale x/y
                canvas.draw(tileTexture, c, 0, 0, loc.x, loc.y, 0, scale, scale);
            }
        }
    }

    /* Returns if the tile at (x,y) is a goal tile */
    public boolean isGoalTile(int x, int y){
        return this.tiles[x][y].type == Tile.tileType.GOAL;
    }

    /* Returns if the tile at (x,y) is the start tile */
    public boolean isStartTile(int x, int y){
        return this.tiles[x][y].type == Tile.tileType.START;
    }

    /* Returns if the tile at (x,y) is an obstacle (and therefore impassable) */
    public boolean isObstacleTile(int x, int y){
        return this.tiles[x][y].type == Tile.tileType.OBSTACLE;
    }

    /**
     * Preloads the assets for the Tile.
     *
     * The asset manager for LibGDX is asynchronous.  That means that
     * you tell it what to load and then wait while it
     * loads them.  This is the first step: telling it what to load.
     *
     * @param manager Reference to global asset manager.
     */
    public static void PreLoadContent(AssetManager manager) {
        manager.load(TILE_FILE, Texture.class);
    }

    /**
     * Loads the assets for the Tile.
     *
     * All shell objects use one of two textures, so this is a static method.
     * This keeps us from loading the same images
     * multiple times for more than one Shell object.
     *
     * The asset manager for LibGDX is asynchronous.  That means that you tell it what to load and then wait while it
     * loads them.  This is the second step: extracting assets from the manager after it has finished loading them.
     *
     * @param manager Reference to global asset manager.
     */
    public static void LoadContent(AssetManager manager) {
        if (manager.isLoaded(TILE_FILE)) {
            tileTexture = manager.get(TILE_FILE,Texture.class);
            tileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            tileTexture = null;  // Failed to load
        }
    }

    /**
     * Unloads the assets for the Tile
     *
     * This method erases the static variables.  It also deletes the associated textures from the assert manager.
     *
     * @param manager Reference to global asset manager.
     */
    public static void UnloadContent(AssetManager manager) {
        if (tileTexture != null) {
            tileTexture = null;
            manager.unload(TILE_FILE);
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void updateColors() {
	    int rand = colorInd % 2; 
	    // Intentionally removed red and yellow, too jarring
	    colorInd++;
	    if (rand == 2) {
		    //pinkish
		    Tile.randCol = new Color(202f / 255f, 75f / 255f, 155f / 255f, 1);
	    } else if (rand == 1) {
		    //blueish
		    Tile.randCol = new Color(69f / 255f, 197f / 255f, 222f / 255f, 1);
	    } else if (rand == 0) {
		    //greenish
		    Tile.randCol = new Color(106f / 255f, 189f / 255f, 69f / 255f, 1);
	    } else if (rand == 3) {
		    //yellowish
		    Tile.randCol = new Color(233f / 255f, 230f / 255f, 18f / 255f, 1);
	    }
	    for (int i = 0; i < width; i++) {
		    for (int j = 0; j < height; j++) {
			    tiles[i][j].setColor();
		    }
	    }
    }

    /**
     * Each tile on the board has a set of attributes associated with it.
     */
    public static class Tile {
        /** Is this a goal tile?*/
        public tileType type = tileType.NORMAL;
        /** Color of this tile */
        public Color col;
	    /** Random color */
	    public static Color randCol = 
	    		new Color(69f / 255f, 197f / 255f, 222f / 255f, 1);

        public enum tileType {
            GOAL,
            START,
            OBSTACLE,
            NORMAL
        }

        public Tile() {
            type = tileType.NORMAL;
            setColor();
        }

        public void setColor(){
            if (this.type == tileType.START){
                //bright blue
                col = new Color(7f/255f, 82f/255f, 1,1);
            }else if (this.type == tileType.OBSTACLE){
                //dark gray
                col = Color.DARK_GRAY;
            }else if (this.type == tileType.GOAL){
                //bright green
                col = new Color(27f/255f, 253f/255f,34f/255f, 1);
                col = Color.GREEN;
            }else {
                col = randCol;
            }
        }

        public void clear() {
            type = tileType.NORMAL;
            col = null;
        }
    }

    /** Checks if the pair (x,y) represents a valid coordinate on the board. */
	public boolean isSafeAt(int x, int y) {
		return (x >= 0 && y >= 0 && x < width && y < height && 
				!isObstacleTile(x,y));
	}
}
