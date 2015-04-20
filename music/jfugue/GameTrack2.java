import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

import org.jfugue.midi.MidiParser;
import org.jfugue.player.Player;
import org.jfugue.pattern.Pattern;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Note;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.rhythm.Rhythm;
import org.staccato.StaccatoParserListener;

/**
 * This class plays the first game track
 * @author Austin Liu
 * @version JFugue 5.01
 */
public class GameTrack2 {

    public static void main(String[] args) {
        GameTrack2 gt2 = new GameTrack2();
        Pattern pattern = gt2.getPattern();
	MidiFileManager mymanager = new MidiFileManager();
	
        Player player = new Player();
	File track = new File("track2.mid");
        
        try {
	    mymanager.savePatternToMidi(pattern, track);
        } catch (IOException e) {
             e.printStackTrace();
        }

	// MidiParser parser = new MidiParser();
	// StaccatoParserListener listener = new StaccatoParserListener();
        // parser.addParserListener(listener);
	// parser.parse(MidiSystem.getSequence(track));
        // Pattern staccatoPattern = listener.getPattern();
        // System.out.println(staccatoPattern);

        // player.play(staccatoPattern);
	player.play(pattern);
    }

    public Pattern getPattern() {
	Pattern pattern = new Pattern();
	String voice1 = "G5i C5i G5i C5i G5i C5i Ab5i C5i " +
	    "G5i C5i G5i C5i G5i C5i Ab5i C5i " +
	    "Ab5i C5i G5i C5i F5i C5i Eb5i D5i " +
	    "Ab5i C5i G5i C5i F5i C5i Eb5i D5i " +
	    "G5i C5i G5i B5i D6i C6i Ab5i C6i " +
	    "G5i C5i G5i B5i D6i C6i Ab5i C6i " +
	    "B5i G5i B5i D6i G6i Ab6i G6i G5i " +
	    "B6i F6i Ab6i D6i F6i B5i D6i G5i " +
	    "F5i G5i F5i Ab5i G5i Ab5i G5i C6i " +
    	    "F5i G5i F5i Ab5i G5i Ab5i G5i C6i " +
	    "Bb5i G5i Bb5i F5i Ab5i G5i Ab5i Eb5i " +
	    "F5i Eb5i D5i G5i F5i Ab5i G5i F5i " +
	    "Eb5i C5i D5i C5i Eb5i C5i B4i F5i " +
	    "Eb5i C5i D5i C5i Eb5i C5i B4i G5i " +
	    "Ab5i F5i C6i Ab5i G5i Eb5i D5i F5i " +
	    "Eb5i D5i C5i C5i B4i G4i B4i D5i ";

	String voice2 = "C4i Ri Ri Ri Ri Ri D4i Eb4i " +
	    "C4i Ri Ri Ri Ri Ri D4i Eb4i " +
	    "C4i Ri Ri Ri Ab3i Ri G3i Ri " + 
	    "C4i Ri Ri Ri Ab3i Ri G3i Ri " +
	    "C4i Ri Ri Ri Ri Ri D4i Eb4i " +
	    "C4i Ri Ri Ri Ri Ri D4i Eb4i " +
	    "F4i Ri Ri Ri B3i Ri D4i Ri " +
	    "Ab3i Ri F3i Ri Ab3i Ri G3i Ri " +
	    "Ab3i Ri Ri F3i Eb3i Ri G3i Ri " +
	    "Ab3i Ri Ri F3i Eb3i Ri G3i Ri " +
	    "G3i Ri Ri Eb3i C3i Ri Ab3i Ri " +
	    "Ab3i Ri F3i Ri Ab3i Ri G3i Ri " +
	    "Eb3i Ri D3i Ri C3i Ri F3i Ri " +
	    "Eb3i Ri D3i Ri C3i Ri Eb3i Ri " +
	    "F3i Ri Ab3i Ri Eb3i Ri F3i Ri " +
    	    "G3i Ri Eb3i Ri D3i Ri G3i Ri ";
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O...O...") // This is Layer 0
	    // .addLayer("..S...S...S...S.")
	    // .addLayer("````````````````")
	    // .addLayer("...............+") // This is Layer 3
	    // Replace Layer 3 with this string on the 4th (count from 0)
	    // measure
	    .setLength(16); // Set the length of the rhythm to 16 measures

	Pattern rpattern = rhythm.getPattern();

	Pattern bpattern =
	    new Pattern("Cq Cq Cq Cq")
	    .repeat(16);
	
        pattern.add("T144 V0 I[Bass_Drum] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V1 I[Piano] " + voice1);
	pattern.add("V2 I[Piano] " + voice2);
	pattern.add(rpattern);
        return pattern;
    }

    
} 


