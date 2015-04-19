package edu.teamWat.rhythmKnights.alpha.models.gameObjects;

import com.badlogic.gdx.math.Vector2;

import edu.teamWat.rhythmKnights.alpha.views.GameCanvas;

public abstract class GameObject {

	protected int id;
    public boolean isAlive = false;
    protected boolean isActive = false;
    public boolean isCharacter = true; 
    protected Vector2 position = new Vector2();
    protected Vector2 velocity = new Vector2();
	protected Vector2 oldPosition = new Vector2();
	protected Vector2 animatedPosition = new Vector2();
	protected int animFrames = 5;
	protected int animAge;
	protected boolean moved;

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

    /* Returns a boolean stating if this object is a character. Used 
     * for collision detection */
    public boolean isCharacter(){
    	return this.isCharacter;
    }
    
    /* Updates objects appropriately on each frame*/
    public abstract void update();

    /* Returns 2D array containing this object's position on the board*/
    public Vector2 getPosition(){
        return this.position;
    }

    /* Pass in relative vector of which direction this object should move */
    public void move(Vector2 direction){
	    this.position.add(direction);}

    /* Get the ID of this GameObject */
    public int getId(){ 
    	return this.id;
    }

	public Vector2 getVelocity() {
		return this.velocity;
	}
	
	public void setVelocity(Vector2 v){
		this.velocity.x = v.x;
		this.velocity.y = v.y;
	}
	
	public void setPosition(Vector2 pos){
		if ((this.oldPosition.x != pos.x || this.oldPosition.y != pos.y) && !moved) {
			this.oldPosition.set(position);
			moved = true;
			animAge = 0;
		}
		this.position.x = pos.x;
		this.position.y = pos.y;
	}
	
	public void setAlive(boolean alive){
		this.isAlive = alive;
		this.isActive = alive;
	}
	
}
