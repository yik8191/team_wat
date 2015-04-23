package org.jfugue.bugs.midivisualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MV_MenuBar extends JMenuBar implements ActionListener{
    private static final long serialVersionUID = 1L;
    
    private MV_Model model;
    private JMenuItem startClip;
    private JMenuItem pauseClip;

    public MV_MenuBar(MV_Model model){
        this.model = model;
        
        JMenu midiControlMenu = new JMenu("MidiControl");
        
        this.startClip = new JMenuItem("Start");
        this.startClip.addActionListener(this);
        midiControlMenu.add(this.startClip);
        
        this.pauseClip = new JMenuItem("Pause");
        this.pauseClip.addActionListener(this);
        midiControlMenu.add(this.pauseClip);
        
        JMenuItem midiFileChooser = new JMenuItem("Choose File...");
        midiFileChooser.addActionListener(this);
        midiControlMenu.add(midiFileChooser);
        
        this.add(midiControlMenu);
        
        this.startClip.setEnabled(false);
        this.pauseClip.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Midi-Filechooser
        if(e.getActionCommand().equals("Choose File...")){
            //set filter for Midi-Files
            FileFilter midiFilter = new FileNameExtensionFilter("Midi-Datein", "mid", "midi");
            JFileChooser midiChooser = new JFileChooser("C:/Users/Felix/workspaceNeu/MidiVisualizer/src/midiVisualizer/");
            midiChooser.addChoosableFileFilter(midiFilter);
            midiChooser.setFileFilter(midiFilter);
            int openReturn = midiChooser.showOpenDialog(null);
            if (openReturn == JFileChooser.APPROVE_OPTION)
            {
                //set the new Midi-File in the model
                try {
                    this.model.setMidiFile(midiChooser.getSelectedFile().getPath());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            this.startClip.setEnabled(true);
            this.pauseClip.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Start")){
            model.startPlaying();
            startClip.setText("Stop");
            this.pauseClip.setEnabled(true);
        }
        else if(e.getActionCommand().equals("Stop")){
        	model.stopPlaying();
        	startClip.setText("Start");
        	pauseClip.setText("Pause");
        	this.pauseClip.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Pause")){
        	model.pausePlaying();
        	pauseClip.setText("Resume");
        	this.startClip.setEnabled(true);
        }
        else if(e.getActionCommand().equals("Resume")){
            model.resumePlaying();
            pauseClip.setText("Pause");
            this.startClip.setEnabled(true);
        }
    }
}
