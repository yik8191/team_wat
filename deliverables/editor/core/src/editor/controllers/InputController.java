package editor.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import editor.views.GameCanvas;

/**
 * Created by Kylar on 4/5/2015.
 */
public class InputController {


    public static Vector2 getMouseCoords(int height){
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        y = -y + height;
        return new Vector2(x,y);
    }
}
