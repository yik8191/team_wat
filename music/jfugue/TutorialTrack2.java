import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

import org.jfugue.midi.MidiParser;
import org.jfugue.player.Player;
import org.jfugue.player.SynthesizerManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Note;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.rhythm.Rhythm;
import org.staccato.StaccatoParserListener;

import com.sun.media.sound.*;

/**
 * This class plays the first game track
 * @author Austin Liu
 * @version JFugue 5.01
 */
public class TutorialTrack2 {

    public static void main(String[] args) {
        TutorialTrack2 tt2 = new TutorialTrack2();
        Pattern pattern = tt2.getPattern();
	MidiFileManager mymanager = new MidiFileManager();

	
	// Player player = new Player();
   	File track = new File("tutorialtrack2.mid");
	
	try {
     	    mymanager.savePatternToMidi(pattern, track);
         } catch (IOException e) {
	    e.printStackTrace();
	}
	
     	// player.play(pattern);
    }

    public Pattern getPattern() {
	Pattern pattern = new Pattern();
	String voice1 = "G4t Rt Rs Rs G4t Rt G4t Rt Rs ";
	String voice2 = "";

	Pattern mpattern = new Pattern(voice1)
	    .repeat(8);

	Rhythm rhythm = new Rhythm()
	    .addLayer("");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(8);

	Pattern bpattern = new Pattern("Ci Rq")
	    .repeat(8);
	
        pattern.add("T144 V0 I[Xylophone] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " + rpattern);

        return pattern;
    }

    
} 


