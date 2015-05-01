/*
 * Author: Kylar Henderson
 */
package edu.teamWat.rhythmKnights.alpha;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import edu.teamWat.rhythmKnights.alpha.utils.*;
import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

import java.util.ArrayList;

public class SelectMode implements Screen, InputProcessor {
    // Textures necessary to support the loading screen
    private static final String BACKGROUND_FILE = "images/game_background.png";
    private static final String TILE_FILE = "images/tileFull1.png";

    /** Background texture for start-up */
    private Texture background;
    /** Play button to display when done */
    private Texture tileTexture;

    /** Reference to GameCanvas created by the root */
    private GameCanvas canvas;
    /** Listener that will update the player mode when we are done */
    private ScreenListener listener;

    /** Number of levels, how many buttons to create */
    private int numLevels;

    private boolean active;

    private ArrayList<int[]> bounds = new ArrayList<int[]>();
    private int levelNum = -1;

    /**
     * Creates a SelectMode with the default size and position.
     *
     * The budget is the number of milliseconds to spend loading assets each animation frame.  This allows you to do
     * something other than load assets.  An animation frame is ~16 milliseconds. So if the budget is 10, you have 6
     * milliseconds to do something else.  This is how game companies animate their loading screens.
     */
    public SelectMode(GameCanvas canvas, int levels) {
        this.canvas = canvas;
        this.numLevels = levels;

        active = false;

        // Compute the dimensions from the canvas
        resize(canvas.getWidth(), canvas.getHeight());

        // Load the next two images immediately.
        tileTexture = new Texture(TILE_FILE);
        background = new Texture(BACKGROUND_FILE);

        canvas.setMenuConstants(this.numLevels);

        for (int i=0;i<numLevels; i++){
            bounds.add(canvas.getButtonBounds(i));
        }

        Gdx.input.setInputProcessor(this);
    }

    /** Called when this screen should release all resources. */
    public void dispose() {
        background.dispose();
        tileTexture.dispose();
        background = null;
        tileTexture = null;
    }

    /**
     * Update the status of this player mode.
     *
     * We prefer to separate update and draw from one another as separate methods, instead of using the single render()
     * method that LibGDX does.  We will talk about why we prefer this in lecture.
     *
     * @param delta Number of seconds since last animation frame
     */
    private void update(float delta) {
        //do nothing
    }

    /**
     * Draw the status of this player mode.
     *
     * We prefer to separate update and draw from one another as separate methods, instead of using the single render()
     * method that LibGDX does.  We will talk about why we prefer this in lecture.
     */
    private void draw() {
        canvas.begin();
        canvas.draw(background, 0, 0);
        BitmapFont font = new BitmapFont();
        float scale = (float)canvas.menuTileSize/(float)tileTexture.getHeight();
        for (int i=0; i<numLevels; i++){
            Vector2 loc = new Vector2(bounds.get(i)[0], bounds.get(i)[1]);
            loc.y = canvas.getHeight() - loc.y - canvas.menuTileSize;
            Color c = new Color(69f / 255f, 197f / 255f, 222f / 255f, 1);
            canvas.draw(tileTexture, c, 0, 0, loc.x, loc.y, 0, scale, scale);
            font.setScale(2);
            canvas.drawText("Level \n" + (i + 1), font, loc.x + canvas.menuTileSize / 5, loc.y + canvas.menuTileSize * 3 /5);
        }
        canvas.end();
    }

    // ADDITIONAL SCREEN METHODS

    /**
     * Called when the Screen should render itself.
     *
     * We defer to the other methods update() and draw().  However, it is VERY important that we only quit AFTER a
     * draw.
     *
     * @param delta Number of seconds since last animation frame
     */
    public void render(float delta) {
        if (active) {
            update(delta);
            draw();

            // We are are ready, notify our listener
            if ((levelNum !=-1) && (listener != null)) {
                listener.exitScreen(this, levelNum);
            }
        }
    }

    /**
     * Called when the Screen is resized.
     *
     * This can happen at any point during a non-paused state but will never happen before a call to show().
     *
     * @param width  The new width in pixels
     * @param height The new height in pixels
     */
    public void resize(int width, int height) {
        canvas.setMenuConstants(this.numLevels);

        bounds.clear();
        for (int i=0;i<numLevels; i++){
            bounds.add(canvas.getButtonBounds(i));
        }
    }

    /**
     * Called when the Screen is paused.
     *
     * This is usually when it's not active or visible on screen. An Application is also paused before it is destroyed.
     */
    public void pause() {

    }

    /**
     * Called when the Screen is resumed from a paused state.
     *
     * This is usually when it regains focus.
     */
    public void resume() {

    }

    /**
     * Called when this screen becomes the current screen for a Game.
     */
    public void show() {
        // Useless if called in outside animation loop
        active = true;
    }

    /**
     * Called when this screen is no longer the current screen for a Game.
     */
    public void hide() {
        // Useless if called in outside animation loop
        active = false;
    }

    /**
     * Sets the ScreenListener for this mode
     *
     * The ScreenListener will respond to requests to quit.
     */
    public void setScreenListener(ScreenListener listener) {
        this.listener = listener;
    }

    // PROCESSING PLAYER INPUT

    /**
     * Called when the screen was touched or a mouse button was pressed.
     *
     * This method checks to see if the play button is available and if the click is in the bounds of the play button.
     * If so, it signals the that the button has been pressed and is currently down. Any mouse button is accepted.
     *
     * @param screenX the x-coordinate of the mouse on the screen
     * @param screenY the y-coordinate of the mouse on the screen
     * @param pointer the button or touch finger number
     * @return whether to hand the event to other listeners.
     */
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Mouse clicked at " + screenX + ", " + screenY);
        for (int i=0; i<numLevels; i++){
            if (canvas.pointInBox(screenX, screenY, i)){
                this.levelNum = i+1;
                return false;
            }
        }

        return true;
    }




    /**
     * Called when a finger was lifted or a mouse button was released.
     *
     * This method checks to see if the play button is currently pressed down. If so, it signals the that the player is
     * ready to go.
     *
     * @param screenX the x-coordinate of the mouse on the screen
     * @param screenY the y-coordinate of the mouse on the screen
     * @param pointer the button or touch finger number
     * @return whether to hand the event to other listeners.
     */
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    /**
     * Called when a key is pressed (UNSUPPORTED)
     *
     * @param keycode the key pressed
     * @return whether to hand the event to other listeners.
     */
    public boolean keyDown(int keycode) {
        return true;
    }

    /**
     * Called when a key is typed (UNSUPPORTED)
     *
     * @param character the key typed
     * @return whether to hand the event to other listeners.
     */
    public boolean keyTyped(char character) {
        return true;
    }

    /**
     * Called when a key is released (UNSUPPORTED)
     *
     * @param keycode the key released
     * @return whether to hand the event to other listeners.
     */
    public boolean keyUp(int keycode) {
        return true;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. (UNSUPPORTED)
     *
     * @param screenX the x-coordinate of the mouse on the screen
     * @param screenY the y-coordinate of the mouse on the screen
     * @return whether to hand the event to other listeners.
     */
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    /**
     * Called when the mouse wheel was scrolled. (UNSUPPORTED)
     *
     * @param amount the amount of scroll from the wheel
     * @return whether to hand the event to other listeners.
     */
    public boolean scrolled(int amount) {
        return true;
    }

    /**
     * Called when the mouse or finger was dragged. (UNSUPPORTED)
     *
     * @param screenX the x-coordinate of the mouse on the screen
     * @param screenY the y-coordinate of the mouse on the screen
     * @param pointer the button or touch finger number
     * @return whether to hand the event to other listeners.
     */
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }
}