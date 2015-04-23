package org.jfugue.bugs.midivisualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.Timer;

public class MV_Label extends JLabel implements MouseListener, ActionListener, I_MV_MidiNoteListener{
    private static final long serialVersionUID = 1L;
    
    private MV_Model model;
    private ArrayList<MV_Rectangle> rectangles;
    private Timer clock3;
    
    public MV_Label(MV_Model model, int windowWidth){
        this.model = model;
        this.addMouseListener(this);
        model.addMidiNoteListener(this);
        this.rectangles = new ArrayList<MV_Rectangle>();
        this.clock3 = new Timer(10, this);
        this.clock3.start();
        
        this.setBackground(new Color(168,214,233));
        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(MV_Rectangle rectangle : this.rectangles){
            rectangle.drawRectangle(g);
        }
        ArrayList<MV_Rectangle> tmpRectangles = this.rectangles;
        Iterator<MV_Rectangle> it = tmpRectangles.iterator();
        while(it.hasNext()) {
        	MV_Rectangle i = it.next();
            if(i.getYPos() <= -15){
                    it.remove();
            }
        }
        this.rectangles = tmpRectangles;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        rectangles.add(new MV_Rectangle(e.getX(), e.getY(), 0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void midiNoteChanged(int octave, int key, int velocity, boolean activity) {
        if(activity){
            rectangles.add(new MV_Rectangle(octave * 120 + key * 10 + model.getIntIntoCenter() + 5, 
            		this.getHeight() + 5, velocity));
        }
    }
}
