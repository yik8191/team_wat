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
public class GameTrack1 {

    public static void main(String[] args) {
        GameTrack1 gt1 = new GameTrack1();
        Pattern pattern = gt1.getPattern();
	MidiFileManager mymanager = new MidiFileManager();
	
        Player player = new Player();
	File track = new File("track1.mid");
        
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
	String voice1 = "B5i B5i B5i D6i D6i D6i E6q D6q C6q " +
	    "B5i B5i B5i D6i D6i D6i A5q G5q F#5q " +
	    "B5i B5i D6i D6i D6i B6i E6i E6i G6i G6i F#6i E6i " +
	    "E6i D6i C6i B5i C6i D6i A5q F#5q D#5q " +
	    "E5i F#5i G5i A5i B5i C6i B5q E5q F#5q " +
	    "E5i F#5i G5i A5i B5i C6i B5h. " +
	    "E6i D6i C6i B5i C6i D6i C6q B5q A5q " +
	    "G5i F#5i E5i F#5i G5i F#5i D#5q.- D#5q D#5i " ;

	String voice2 = "G3h.- G3q.- G3i A3i B3i " + 
	    "G3h.- G3q.- G3i A3i B3i " +
	    "G3h.- C4h. " +
	    "B3w. " +
	    "E3h.- E3q.- E3i G3i F#3i " +
    	    "E3h.- E3q.- E3i F#3i G3i " + 
	    "A3h.- E3q.- E3i F#3i G3i" +
	    "F#3w. " +
	    ;
	    
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.....O.....") // This is Layer 0
	    .setLength(16); // Set the length of the rhythm to 16 measures

	Pattern rpattern = rhythm.getPattern();

	Pattern bpattern =
	    new Pattern("Cq. Cq. Cq. Cq.")
	    .repeat(16);
	
        pattern.add("T180 V0 I[Bass_Drum] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V1 I[Piano] " + voice1);
	pattern.add("V2 I[Piano] " + voice2);
	pattern.add(rpattern);
        return pattern;
    }

    
} 


