package org.jfugue.bugs;

import javax.sound.midi.MidiUnavailableException;

import org.jfugue.pattern.Pattern;
import org.jfugue.realtime.RealtimePlayer;

public class Bug_2015_02_20_Ziad_Realtime {
	public static void main(String[] args) {
		RealtimePlayer rt = null; 
		try { 
		  rt = new RealtimePlayer(); 
		} catch (MidiUnavailableException e){ 
		   e.printStackTrace(); 
		} 
		Pattern pat = new Pattern("D5h E5h");
		rt.play(pat);
	}
}
