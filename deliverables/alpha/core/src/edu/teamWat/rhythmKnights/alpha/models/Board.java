package edu.teamWat.rhythmKnights.alpha.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import edu.teamWat.rhythmKnights.alpha.utils.FilmStrip;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

import java.util.ArrayList;

public class Board {
    /* Width of current board */
    private int width;
    /* Height of current board */
    private int height;

    /* Variables for tile sprite */

    public static final String GLOWING_FILE = "images/tiles/glowingtile.png";
    public static Texture tileTexture;
    public static Texture glowingTexture;
    private FilmStrip sprite;
    // The number of frames before a sprite refreshes
    private int animDelay = 5;
    private int curTime = 5;
    private int curFrame = 0;
    // Constants for reference to the spritesheet
    private int FLASH_START = 0;
    private int FLASH_END = 3;
    private int SPRITE_ROWS = 1;
    private int SPRITE_COLS = 4;
    private int SPRITE_TOT = 4;

    private float distanceToBeat;

    /* Holds the array of tiles that make up this board */
    private Tile[][] tiles;

    private static ArrayList<String> TILE_FILES = new ArrayList<String>();

    private static int numTileTextures = 1;
    private static Texture[] textures = new Texture[numTileTextures];

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

        // Set current tile image
        sprite = new FilmStrip(glowingTexture, SPRITE_ROWS, SPRITE_COLS, SPRITE_TOT);
        sprite.setFrame(0);
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
        // Might not need this method.
    }

    public void setTileSprite(int i){
        tileTexture = textures[i-1];
    }

    /*Set a certain tile to have all listed variables*/
    public void setTile(int x, int y, Tile.tileType t){
        this.tiles[x][y].type = t;
        this.tiles[x][y].setColor();
    }

    /* Set specified tile to be animated after a successful action */
    public void setSuccess(int x, int y) {
        this.tiles[x][y].isSuccess = true;
    }

    public void draw(GameCanvas canvas){
        for (int i=0; i<this.width; i++){
            for (int j=0; j<this.height; j++){
                Vector2 loc = canvas.boardToScreen(i,j);
                Color c = tiles[i][j].col;
                float scale = (float)canvas.tileSize/(float)tileTexture.getHeight();

                // Check for success tiles
                if (tiles[i][j].isSuccess) {
                    curTime --;
                    if (curTime == 0) {
                        curFrame ++;
                        // Finished animating the flashing frames
                        if (curFrame >= FLASH_END) {
                            curFrame = FLASH_START;
                            tiles[i][j].isSuccess = false;
                        }
                        curTime = animDelay;
                    } else {
                        sprite.setFrame(curFrame);
                    }
                    canvas.draw(sprite, c, 0, 0, loc.x, loc.y, 0, scale, scale);
                } else {
                    //texture, color, sprite origin x/y, x/y offset, angle, scale x/y
                    canvas.draw(tileTexture, c, 0, 0, loc.x, loc.y, 0, scale, scale);
                }
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
        //Populate tile list then load them
        for (int i=0; i<numTileTextures; i++){
            TILE_FILES.add("images/tiles/tileFull"+(i+1)+".png");
            manager.load(TILE_FILES.get(i), Texture.class);
        }
        manager.load(GLOWING_FILE, Texture.class);
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
        for (int i=0; i<TILE_FILES.size(); i++) {
            if (manager.isLoaded(TILE_FILES.get(i))) {
                textures[i] = manager.get(TILE_FILES.get(i), Texture.class);
                textures[i].setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            } else {
                textures[i] = null;  // Failed to load
            }
        }

        if (manager.isLoaded(GLOWING_FILE)) {
            glowingTexture = manager.get(GLOWING_FILE, Texture.class);
            glowingTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            glowingTexture = null; // Failed to load
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
        for (int i=0; i<TILE_FILES.size(); i++) {
            if (textures[i] != null) {
                textures[i] = null;
                manager.unload(TILE_FILES.get(i));
            }
        }
        if (glowingTexture != null) {
            glowingTexture = null;
            manager.unload(GLOWING_FILE);
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void updateColors() {
        Color reddish = new Color(202f / 255f, 75f / 255f, 155f / 255f, 1);
        Color bluish = new Color(69f / 255f, 197f / 255f, 222f / 255f, 1);

//        distanceToBeat *= distanceToBeat;
        float ratio = (float)Math.pow(Math.cos(distanceToBeat * Math.PI), 16);

        reddish.mul(ratio);
        bluish.mul(1 - ratio);

        Tile.randCol = reddish.add(bluish);

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

        /** Is this a tile that the player successfully moved onto? */
        public boolean isSuccess = false;

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
                // bright blue
                col = new Color(7f/255f, 82f/255f, 1,1);
            } else if (this.type == tileType.OBSTACLE){
                // dark gray
                col = Color.DARK_GRAY;
            } else if (this.type == tileType.GOAL){
                // bright green
                col = new Color(27f/255f, 253f/255f,34f/255f, 1);
                col = Color.GREEN;
            } else {
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

    public boolean isOffScreen(int x, int y) {
        return !(x >= 0 && y >= 0 && x < width && y < height);
    }

    public void setDistanceToBeat(float ratio) {
        distanceToBeat = ratio;
    }
}
