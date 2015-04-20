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
public class TutorialTrack1 {

    public static void main(String[] args) {
        TutorialTrack1 tt1 = new TutorialTrack1();
        Pattern pattern = tt1.getPattern();
	MidiFileManager mymanager = new MidiFileManager();

	Player player = new Player();
   	File track = new File("tutorialtrack1.mid");
	
	try {
     	    mymanager.savePatternToMidi(pattern, track);
         } catch (IOException e) {
	    e.printStackTrace();
	}
	
     	player.play(pattern);
    }

    public Pattern getPattern() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Rhythm rhythm = new Rhythm()
	    .addLayer("O..oO...O..oOO..")
	    .addLayer("..S...S...S...S.")
	    .addLayer("````````````````")
	    .addLayer("...............+");
	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(8);

	Pattern bpattern = new Pattern("Ch Ch")
	    .repeat(16);

        pattern.add("T144 V0 I[Steel_Drums] " +
		    "X[Volume_Coarse]=255 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V9 " + rpattern);

        return pattern;
    }

    
} 


