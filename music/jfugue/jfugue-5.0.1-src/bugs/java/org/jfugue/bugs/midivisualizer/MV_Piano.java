package org.jfugue.bugs.midivisualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;

//Visuell Piano
public class MV_Piano extends JLabel implements I_MV_MidiNoteListener{
    private static final long serialVersionUID = 1L;
    
    private ArrayList<MV_Scale> scales;

    public MV_Piano(MV_Model model, int windowWidth){
        model.addMidiNoteListener(this);
        this.setLayout(new GridLayout(1,0));
        this.setBackground(new Color(168,214,233));
        this.setSize(960, 60);
        this.setPreferredSize(this.getSize());
        
        scales = new ArrayList<MV_Scale>();
        
        //int-value to place flying notes on the pianokeys
        int intIntoCenter = windowWidth / 2 - this.getWidth() / 2;
        model.setIntIntoCenter(intIntoCenter);
        
        for(int i = 0; i <= 7; i++){
            scales.add(new MV_Scale(i * 120 + intIntoCenter));
        }
        this.setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(MV_Scale s : scales){
            s.buildScale(g);
        }
    }

    @Override
    public void midiNoteChanged(int octave, int key, int velocity, boolean activity) {
        scales.get(octave).setKeyActivity(key, velocity, activity);
        repaint();
    }
}
