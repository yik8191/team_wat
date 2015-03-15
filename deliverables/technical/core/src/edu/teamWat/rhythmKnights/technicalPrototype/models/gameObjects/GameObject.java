package edu.teamWat.rhythmKnights.technicalPrototype.models.gameObjects;

import com.badlogic.gdx.math.Vector2;
import edu.teamWat.rhythmKnights.technicalPrototype.views.GameCanvas;

public abstract class GameObject {

    protected int id;
    protected boolean isAlive = false;
    protected boolean isActive = false;
    protected Vector2 position;
    protected Vector2 velocity;

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

    /* Get the ID of this GameObject */
    public int getId(){ return this.id;}
}
