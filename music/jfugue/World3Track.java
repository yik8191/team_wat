import java.util.Map;
import java.util.HashMap;

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
 * This class is for generating the tracks 27 through 30 (World 3)
 * @author Austin Liu
 * @version JFugue 5.01
 */
public class World3Track {

    public static void main(String[] args) {
        World3Track tt = new World3Track();
	MidiFileManager mymanager = new MidiFileManager();
	
	// Track1
	Pattern pattern1 = tt.getPattern1();
   	File track1 = new File("midis/27_MMMDD_108.mid");

	try {
     	    mymanager.savePatternToMidi(pattern1, track1);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track2
	Pattern pattern2 = tt.getPattern2();
   	File track2 = new File("midis/28_MDMDDM_108.mid");

	try {
     	    mymanager.savePatternToMidi(pattern2, track2);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track3
	Pattern pattern3 = tt.getPattern3();
   	File track3 = new File("midis/29_MMMDDMM_108.mid");

	try {
     	    mymanager.savePatternToMidi(pattern3, track3);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track4
	Pattern pattern4 = tt.getPattern4();
   	File track4 = new File("midis/30_MDDMDDMM_108.mid");

	try {
     	    mymanager.savePatternToMidi(pattern4, track4);
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
		put('O', "[ACOUSTIC_BASE_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.O.O.O.O.")
	    .addLayer(".^.^.^XXXX");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	// MMMDD
	Pattern bpattern = new Pattern("C4q C4q C4q C4q C4q ")
	    .repeat(4);
	
        pattern.add("T108 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }

    public Pattern getPattern2() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASE_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.O.O.O.O.O.")
	    .addLayer(".^XX.^XXXX.^");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	// MDMDDM
	Pattern bpattern = new Pattern("C4q C4q C4q C4q C4q C4q")
	    .repeat(4);
	
        pattern.add("T108 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }
    
    public Pattern getPattern3() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASE_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.O.O.O.O.O.O.")
	    .addLayer(".S.S.SXXXX.S.S");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);
	
	// MMMDDMM
	Pattern bpattern = new Pattern("C4q C4q C4q C4q C4q C4q C4q")
	    .repeat(4);
	
        pattern.add("T108 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }

    public Pattern getPattern4() {
	
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[LOW_TOM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.O.O.O.O.O.O.O.")
	    .addLayer("..XXXX..XXXX....");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	// MDDMDDMM
	Pattern bpattern = new Pattern("C4q C4q C4q C4q C4q C4q C4q C4q ")
	    .repeat(4);
	
        pattern.add("T108 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }

    public Pattern getPattern5() {
	
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[LOW_TOM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.O.O.")
	    .addLayer("..SSSS");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(4);
	
        pattern.add("T120 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }

    public Pattern getPattern6() {
	
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[LOW_TOM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.O.O.")
	    .addLayer("..SSSS");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(4);
	
        pattern.add("T120 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }    
} 


