package rhythmKnights.alpha.models.gameObjects;

import rhythmKnights.alpha.views.GameCanvas;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

    protected int id;
    public boolean isAlive = false;
    protected boolean isActive = false;
    protected Vector2 position = new Vector2();
    protected Vector2 velocity = new Vector2();

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
		this.position.x = pos.x;
		this.position.y = pos.y;
	}
	
	public void setAlive(boolean alive){
		this.isAlive = alive;
		this.isActive = alive;
	}
	
}
