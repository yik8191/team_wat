import java.util.Map;
import java.util.HashMap;

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
public class GameTrack4 {

    public static void main(String[] args) {
        GameTrack4 gt4 = new GameTrack4();
        Pattern pattern = gt4.getPattern();
	MidiFileManager mymanager = new MidiFileManager();
	
        Player player = new Player();
	File track = new File("track4.mid");
        
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
	String voice1 = "A3i Ri A3i Ri A3i Ri Ri B3s Rs " +
	    "Ri B3s Rs Ri B3s Rs C4s Rs Ri B3s Rs Ri ";

	Pattern cpattern = new Pattern(voice1)
	    .repeat(4);
	

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri");
		put('O', "[ACOUSTIC_BASS_DRUM]i");
		put('S', "[ACOUSTIC_SNARE]i");
		put('^', "[PEDAL_HI_HAT]i");
		put('*', "[CRASH_CYMBAL_1]i");
		put('X', "[HAND_CLAP]i");
	    }};
	    
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.O.O.O.") // This is Layer 0
	    .setRhythmKit(MY_RHYTHM_KIT)
	    .setLength(4); // Set the length of the rhythm to 16 measures

	Pattern rpattern = rhythm.getPattern();

	Pattern bpattern =
	    new Pattern("C1q C1q C1q C1q ")
	    .repeat(4);
	
        pattern.add("T180 V0 I[Acoustic_Bass_Drum] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V1 I[Distortion_Guitar] X[Volume_Coarse]=192 " +
		    cpattern);
	pattern.add(rpattern);
        return pattern;
    }

    
} 


