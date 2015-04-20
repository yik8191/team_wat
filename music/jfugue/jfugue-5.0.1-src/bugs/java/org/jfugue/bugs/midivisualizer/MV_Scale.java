package org.jfugue.bugs.midivisualizer;

import java.awt.Graphics;
import java.util.ArrayList;

//Builds one Scale on the piano
public class MV_Scale{
    
    private ArrayList<MV_Keys> keys;

    public MV_Scale(int xPos){
        keys = new ArrayList<MV_Keys>();
        keys.add(new MV_Keys(false, xPos + 0));
        keys.add(new MV_Keys(true, xPos + 10));
        keys.add(new MV_Keys(false, xPos + 20));
        keys.add(new MV_Keys(true, xPos + 30));
        keys.add(new MV_Keys(false, xPos + 40));
        keys.add(new MV_Keys(false, xPos + 50));
        keys.add(new MV_Keys(true, xPos + 60));
        keys.add(new MV_Keys(false, xPos + 70));
        keys.add(new MV_Keys(true, xPos + 80));
        keys.add(new MV_Keys(false, xPos + 90));
        keys.add(new MV_Keys(true, xPos + 100));
        keys.add(new MV_Keys(false, xPos + 110));
    }
    
    public void setKeyActivity(int key, int velocity, boolean activity){
        keys.get(key).setKeyColor(velocity, activity);
    }
    
    public void buildScale(Graphics g){
        for(MV_Keys k : keys){
            k.buildKey(g);
        }
    }
}
