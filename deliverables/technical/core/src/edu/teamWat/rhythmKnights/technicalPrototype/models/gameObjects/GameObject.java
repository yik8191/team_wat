package edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects;

import com.badlogic.gdx.math.Vector2;
import edu.teamWat.rhythmKnights.technicalPrototype.views.GameCanvas;

public abstract class GameObject {

    protected int id;
    private boolean isAlive = false;
    private boolean isActive = false;
    private Vector2 position;
    private Vector2 velocity;

    /* Draw the sprite on the board*/
    public abstract void draw(GameCanvas canvas);

    /* Returns boolean stating if this object is alive*/
    public boolean isAlive(){
        return this.isAlive;
    }

    /* Returns a boolean stating if this object is active*/
    public boolean isActive(){
        return this.isActive;
    }

    /* Updates objects appropriately on each frame*/
    public abstract void update();

    /* Returns 2D array containing this object's position on the board*/
    public Vector2 getPosition(){
        return this.position;
    }

    /* Pass in relative vector of which direction this object should move */
    public void move(Vector2 direction){this.position.add(direction);}

}
