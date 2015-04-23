package org.jfugue.bugs.midivisualizer;

import java.awt.Color;
import java.awt.Graphics;

//Test class for flying notes
public class MV_Rectangle {
    
    private int xPos;
    private int yPos;
    private Color color;
    
    public MV_Rectangle(int xPos, int yPos, int velocity){
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = new Color(255,0,0, 255 -(velocity * 2));
    }
    
    public void drawRectangle(Graphics g){
        g.setColor(color);
        g.fillRect(this.xPos -5, this.yPos -5, 10, 100);
        this.yPos--;
    }
    
    public int getYPos(){
        return yPos;
    }
}