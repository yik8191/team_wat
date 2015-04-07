package editor.models;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import editor.views.GameCanvas;

/**
 * Created by Kylar on 4/6/2015.
 */
public class Menu {

    public Menu(){
        //TODO: Fill this in
    }

    public void draw(GameCanvas canvas){
        ShapeRenderer r = new ShapeRenderer();
        r.begin(ShapeRenderer.ShapeType.Filled);
        r.setColor(0, 1, 0, 1);
        r.rect(canvas.getWidth()*2/3, 0, canvas.getWidth(), canvas.getHeight());
        r.end();

    }

}
