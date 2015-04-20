import java.io.File;
import java.io.IOException;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiSystem;

import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.pattern.Pattern;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.tools.GetPatternStats;
import org.jfugue.mitools.Rearranger;


/**
 * This class plays Johann Sebastian Bach's "Crab Canon".  
 * This is an interesting song
 * in which there are two voices, and each voice mirrors the other.  
 * If the first voice
 * is playing D A F E, the other voice is playing E F A D.
 * @author David Koelle, edited Austin Liu
 * @version 5.01
 */
public class CrabCanon {

    public static void main(String[] args) {
        CrabCanon crab = new CrabCanon();
        Pattern pattern = crab.getPattern();
	MidiFileManager mymanager = new MidiFileManager();
	
	
	// Load instruments from the soundbank
	// into the synthesizer
	// Synthesizer synth = MidiSystem.getSynthesizer();

        Player player = new Player();
        player.play(pattern);

        try {
            mymanager.savePatternToMidi(pattern, new File("crabcanon.mid"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Pattern getPattern() {
	Pattern pattern = new Pattern();
	String canonStr = "C5h Eb5h G5h Ab5h B4h Rq G5h "+
	    "F#5h F5h E5h Eb5h " +
	    "D5q Db5q C5q B4q G4q C5q F5q " +
	    "Eb5h D5h C5h Eb5h " +
	    "G5i F5i G5i C6i G5i Eb5i D5i Eb5i " +
	    "F5i G5i A5i B5i C6i Eb5i F5i G5i " +
	    "Ab5i D5i Eb5i F5i G5i F5i Eb5i D5i " +
	    "Eb5i F5i G5i Ab5i Bb5i Ab5i G5i F5i " +
	    "G5i Ab5i Bb5i C6i Db6i Bb5i A5i G5i " +
	    "A5i B5i C6i D6i Eb6i C6i B5i A5i " +
	    "B5i C6i D6i Eb6i F6i D6i G5i D6i " +
	    "C6i D6i Eb6i F6i Eb6i D6i C6i B5i " +
	    "C6q G5q Eb5q C5q ";

	String canonStrOctave = "C4q Eb4q G4q C5q " +
	    "B4i C5i D5i Eb5i F5i Eb5i D5i C5i " +
	    "D5i G4i D5i F5i Eb5i D5i C5i B4i " +
	    "A4i B4i C5i Eb5i D5i C5i B4i A4i " +
	    "G4i A4i Bb4i Db5i C5i Bb4i Ab4i G4i " +
	    "F4i G4i Ab4i Bb4i Ab4i G4i F4i Eb4i " +
	    "D4i Eb4i F4i G4i F4i Eb4i D4i Ab4i " +
	    "G4i F4i Eb4i C5i B4i A4i G4i F4i " +
	    "Eb4i D4i Eb4i G4i C5i G4i F4i G4i " +
	    "Eb4h C4h D4h Eb4h " +
	    "F4q C4q G3q B3q C4q Db4q D4q " +
	    "Eb4h E4h F4h F#4h " +
	    "G4h Rq B3h Ab4h G4h Eb4h C4h";
	
        pattern.add("T144 V0 " + canonStr);
        pattern.add("V1 " + canonStrOctave);

        return pattern;
    }
    
} 


