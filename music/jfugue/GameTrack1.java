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
	    "B5i B5i D6i D6i D6i B5i E6i E6i G6i G6i F#6i E6i " +
	    "E6i D6i C6i B5i C6i D6i A5q F#5q D#5q " +
	    "E5i F#5i G5i A5i B5i C6i B5q E5q F#5q " +
	    "E5i F#5i G5i A5i B5i C6i B5h. " +
	    "E6i D6i C6i B5i C6i D6i C6q B5q A5q " +
	    "G5i F#5i E5i F#5i G5i F#5i D#5q.- D#5-q D#5i " +
	    "E5i F#5i E5i G5i F#5i E5i A5i B5i C6i B5i G5i F#5i " +
	    "E5i F#5i E5i G5i F#5i E5i A5i E6i D6i C6i B5i C6i " +
	    "B5i G6i F#6i A6i F#6i C6i A5i F#6i E6i F#6i B5i A5i " +
	    "G5i E6i D6i E6i C6i A5i F#5i D6i C6i B5i G5i E5i " +
	    "C5i E5i A5i C6i B5i A5i C5i E5i A5i C6i A5i F#5i " +
	    "B4i E5i G5i B5i G5i F#5i B5i G5i F#5i B5i G5i E5i " +
	    "A5i G5i F#5i E5i F#5i G5i D5i C5i B4i A4i B4i C5i " +
    	    "B4i E5i D5i C5i B4i A4i G4i B4i D5i G5i A5i C6i " ;

	String voice2 = "G3h.- G3-q.- G3-i A3i B3i " + 
	    "G3h.- G3-q.- G3-i A3i B3i " +
	    "G3h. C4h. " +
	    "B3w. " +
	    "E3h.- E3-q.- E3-i G3i F#3i " +
    	    "E3h.- E3-q.- E3-i F#3i G3i " + 
	    "A3h. E3q.- E3-i F#3i G3i " +
	    "F#3w. " +
	    "E3w. " +
	    "E3w. " +
	    "G3h. F#3h. " +
	    "E3h. D3h. " +
	    "A3w. " +
	    "E4w. " +
	    "D4h. A3h. " +
	    "E3q. F#3q. G3h. ";

	String voice3 = "B4i Ri B4i Ri B4i B4i ";
	Pattern spattern = new Pattern(voice3)
	    .repeat(16);
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.....O.....") // This is Layer 0
	    .addLayer("^.^.^^^.^.^^");
	// Set the length of the rhythm to 16 measures

	Pattern rpattern = rhythm.getPattern()
	    .repeat(16);

	Pattern bpattern = new Pattern("Ch. Ch. ")
	    .repeat(16);
	
        pattern.add("T180 V0 I[Bass_Drum] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        // pattern.add("V1 I[Guitar] X[Volume_Coarse]=192 " + voice1);
	// pattern.add("V2 I[Cello] X[Volume_Coarse]=192 " + voice2);
	// pattern.add("V3 I[Cowbell] X[Volume_Coarse]=192 " + voice3);
	pattern.add("V9 X[Volume_Coarse]=255 " + rpattern);
        return pattern;
    }

    
} 


