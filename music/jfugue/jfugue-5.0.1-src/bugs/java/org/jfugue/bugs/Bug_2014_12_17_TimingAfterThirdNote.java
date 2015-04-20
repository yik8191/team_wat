package org.jfugue.bugs;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

/**
 * Problem: The timing in "A B C D E F G" sounds different than "R R R A B C D E F G" 
 * 
 * Result: 
 */
public class Bug_2014_12_17_TimingAfterThirdNote {
	public static void main(String[] args) {
		try {
			Pattern p1 = new Pattern("C D E F G A B");
			MidiFileManager.savePatternToMidi(p1, new File("p1.mid"));
	
			Pattern p2 = new Pattern("R R R C D E F G A B");
			MidiFileManager.savePatternToMidi(p2, new File("p2.mid"));
		
			Player player = new Player();
			player.play("I[Piano] " + p1);
			player.play("I[Flute] " + p2);
			
			Pattern p1_loaded = MidiFileManager.loadPatternFromMidi(new File("p1.mid"));
			Pattern p2_loaded = MidiFileManager.loadPatternFromMidi(new File("p2.mid"));
			
			System.out.println(p1_loaded);
			System.out.println(p2_loaded);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
}
