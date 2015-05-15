import java.util.Map;
import java.util.HashMap;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Instrument;

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
 * This class is for generating tracks 07 through 09 (enemy tutorials)
 * @author Austin Liu
 * @version JFugue 5.01
 */
public class ETutorialTrack {

    public static void main(String[] args) throws Exception {
	Player player = new Player();
        ETutorialTrack tt = new ETutorialTrack();
	MidiFileManager mymanager = new MidiFileManager();
	
	// Track E-1
	Pattern pattern1 = tt.getPattern1();
   	File track1 = new File("midis/07_MMMM_120.mid");

	try {
     	    mymanager.savePatternToMidi(pattern1, track1);
         } catch (IOException e) {
	    e.printStackTrace();
	}
	// player.play(pattern1);
	
	// Track E-2
	Pattern pattern2 = tt.getPattern2();
   	File track2 = new File("midis/08_MMMM_120.mid");

	try {
     	    mymanager.savePatternToMidi(pattern2, track2);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track E-3
	Pattern pattern3 = tt.getPattern3();
   	File track3 = new File("midis/09_MMMM_120.mid");

	try {
     	    mymanager.savePatternToMidi(pattern3, track3);
         } catch (IOException e) {
	    e.printStackTrace();
	}
    }


    public Pattern getPattern1() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASS_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[CLOSED_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]i");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer(".s.S.s.S")
	    .addLayer("X.X.X.X.");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(4);
	
        pattern.add("T120 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 X[Volume_Coarse]=255 " +  rpattern);

        return pattern;
    }


    
    public Pattern getPattern2() {
	
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASS_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[CLOSED_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]i");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .setRhythmKit(MY_RHYTHM_KIT)
	    .addLayer("O..O..O..O..")
	    .addLayer("***********.")
	    .addLayer("...........X");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(2);

	Pattern bpattern = new Pattern("C4q. C4q. C4q. C4q. ")
	    .repeat(2);
	
        pattern.add("T180 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 X[Volume_Coarse]=255 " +  rpattern);

        return pattern;
    }

    
    public Pattern getPattern3() {
	
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";
	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[BASS_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i Ri ");
		put('s', "[ACOUSTIC_SNARE]i. [ACOUSTIC_SNARE]s ");
		put('^', "[CLOSED_HI_HAT]i ");
		put('*', "[CRASH_CYMBAL_1]i ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern hpattern = new Pattern(voice1);
	Pattern lpattern = new Pattern(voice2);
				       
	Rhythm rhythm = new Rhythm()
	    .setRhythmKit(MY_RHYTHM_KIT)
	    .addLayer("O.O.O.O.O.O.O.O.")
	    .addLayer("..^^..^^..^^..^^");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(8);
	
        pattern.add("T120 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + hpattern);
	pattern.add("V2 I[Piano] X[Volume_Coarse]=192 " + lpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }
    
    
} 


