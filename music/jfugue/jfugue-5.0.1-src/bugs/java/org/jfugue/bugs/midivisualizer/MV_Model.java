package org.jfugue.bugs.midivisualizer;

import java.io.File;
import java.util.ArrayList;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.temporal.TemporalPLP;
import org.staccato.StaccatoParser;

public class MV_Model {
    
    private ArrayList<I_MV_MidiNoteListener> midiNoteListeners;
    
    private File midiFile;
    private Pattern pattern;
    private TemporalPLP plp;
    private StaccatoParser parser;
    private MidiNoteListener midiNoteListener;
    private Player player;
    private ManagedPlayer managedPlayer;
    
    private int intIntoCenter;
    
    public MV_Model(){
    	//Realtime ParserListener and Parser for the midievents
        plp = new TemporalPLP();
        parser = new StaccatoParser();
        parser.addParserListener(plp);
        midiNoteListener = new MidiNoteListener(this);
        plp.addParserListener(midiNoteListener);
        //playing midi-files
        player = new Player(); 
        //extends player with pause stop and resume functions
        managedPlayer = player.getManagedPlayer();
        //classes who get called if a midievent happens
        midiNoteListeners = new ArrayList<I_MV_MidiNoteListener>();
    }
    
    public void startPlaying(){
    	Runnable r = new Runnable() {
    		public void run() {
    	    	parser.parse(pattern);
    	    	player.delayPlay(0, pattern);
    	    	//starts the process for returning realtime midievents
    	        plp.parse();
    		}
    	};
    	new Thread(r).start();
    }
    
    public void pausePlaying(){
    	managedPlayer.pause();
    }
    
    public void resumePlaying(){
    	managedPlayer.resume();
    }
    
    public void stopPlaying(){
    	managedPlayer.finish();
    }
    
    //set new midifile
    public void setMidiFile(String midiPath) throws Exception{
        midiFile = new File(midiPath);
        pattern = MidiFileManager.loadPatternFromMidi(midiFile);
        System.out.println(pattern);
    }
    
    //reset all keys to normal
    public void resetKeys(){
        for(int i = 0; i <= 7; i++){
            for(int j = 0; j <= 11; j++){
                this.midiNotesChanged(i,j,127,false);
            }
        }
    }
    
    //position flying keys to the pianokeys
    public void setIntIntoCenter(int intIntoCenter){
        this.intIntoCenter = intIntoCenter;
    }
    
    public int getIntIntoCenter(){
        return intIntoCenter;
    }
    
    //add, remove, call listener
    public void addMidiNoteListener(I_MV_MidiNoteListener midiNoteListener){
        this.midiNoteListeners.add(midiNoteListener);
    }
    
    public void removeMidiNoteListener(I_MV_MidiNoteListener midiNoteListener){
        this.midiNoteListeners.remove(midiNoteListener);
    }
    
    public void midiNotesChanged(int octave, int key, int velocity, boolean activity){
        for(I_MV_MidiNoteListener midiNoteListener : midiNoteListeners){
            midiNoteListener.midiNoteChanged(octave, key, velocity, activity);
        }
    }
}
