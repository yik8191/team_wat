package org.jfugue.bugs.midivisualizer;

import java.awt.Color;
import java.awt.Graphics;

//Key for the Piano
public class MV_Keys{
    
    private Color normalColor;
    private Color currentColor;
    private int xPos;

    public MV_Keys(boolean halfKey, int xPos){
        if(halfKey){
            normalColor = Color.BLACK;
        }
        else{
            normalColor = Color.WHITE;
        }
        currentColor = normalColor;
        this.xPos = xPos;
    }
    
    public void setKeyColor(int velocity, boolean activ){
        if(normalColor == Color.WHITE){
            if(activ){
                this.currentColor = new Color(255, 255 - (velocity * 2), 255 - (velocity * 2));
            }
            else{
                this.currentColor = normalColor;
            }
        } else{
            if(activ){
                this.currentColor = new Color(velocity * 2, 0, 0);
            }
            else{
                this.currentColor = normalColor;
            }
        }
    }
    
    public void buildKey(Graphics g){
        g.setColor(Color.GRAY);
        g.drawRect(xPos, 0, 10, 60);
        g.setColor(currentColor);
        g.fillRect(xPos+1, 1, 8, 98);
    }
}