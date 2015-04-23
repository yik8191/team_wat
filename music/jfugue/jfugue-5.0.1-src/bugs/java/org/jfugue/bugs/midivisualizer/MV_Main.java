package org.jfugue.bugs.midivisualizer;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MV_Main extends JFrame{
	
    private static final long serialVersionUID = 1L;

    public MV_Main(){
        super("Midi-Visualizer");
        this.setSize(1000, 500);
        this.setPreferredSize(this.getSize());
        this.setLocation(100, 60);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.pack();
        
        MV_Model model = new MV_Model();
        //Menubar for selecting a midifile and start,stop,pause, resume functions
        this.add(new MV_MenuBar(model), BorderLayout.NORTH);
        //test-class for flying notes
        this.add(new MV_Label(model, this.getContentPane().getWidth()),BorderLayout.CENTER);
        //Visuelpiano
        this.add(new MV_Piano(model, this.getContentPane().getWidth()), BorderLayout.SOUTH);
        
        this.setVisible(true);
    }
    
    public static void main(String[] args){
    	try {
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
          	e.printStackTrace();
        }
        new MV_Main();
    }
}
