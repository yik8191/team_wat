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
 * This class is for generating the tracks 15 through 20 (World 1)
 * @author Austin Liu
 * @version JFugue 5.01
 */
public class World1Track {

    public static void main(String[] args) {
        World1Track tt = new World1Track();
	MidiFileManager mymanager = new MidiFileManager();
	
	// Track1
	Pattern pattern1 = tt.getPattern1();
   	File track1 = new File("midis/15_MMMD_120.mid");

	try {
     	    mymanager.savePatternToMidi(pattern1, track1);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track2
	Pattern pattern2 = tt.getPattern2();
   	File track2 = new File("midis/16_MMMD_120.mid");

	try {
     	    mymanager.savePatternToMidi(pattern2, track2);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track3
	Pattern pattern3 = tt.getPattern3();
   	File track3 = new File("midis/17_MDMD_120.mid");

	try {
     	    mymanager.savePatternToMidi(pattern3, track3);
         } catch (IOException e) {
	    e.printStackTrace();
	}

		// Track4
	Pattern pattern4 = tt.getPattern4();
   	File track4 = new File("midis/18_MDMD_120.mid");

	try {
     	    mymanager.savePatternToMidi(pattern4, track4);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track5
	Pattern pattern5 = tt.getPattern5();
   	File track5 = new File("midis/19_MDMD_120.mid");

	try {
     	    mymanager.savePatternToMidi(pattern5, track5);
         } catch (IOException e) {
	    e.printStackTrace();
	}
	
	// Track 6
	Pattern pattern6 = tt.getPattern6();
   	File track6 = new File("midis/20_MDMD_120.mid");
	try {
     	    mymanager.savePatternToMidi(pattern6, track6);
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
	    .addLayer("O.O.O.O.")
	    .addLayer(".^.^.^.X");

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

    public Pattern getPattern2() {
	Pattern pattern = new Pattern();
	String voice1 = "C5i G4i C5i D5i Eb5i C6i Eb5i D5i | " +
	    "C5i. Bb4s- Bb4-i C5i Rq Rq | " +
	    "C5i G4i C5i D5i Eb5i C6i Eb5i D5i | " +
	    "C5i. Bb4s- Bb4-i C5i Rq Rq | ";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASS_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i");
		put('^', "[CLOSED_HI_HAT]i ");
		put('*', "[CRASH_CYMBAL_2]i ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .setRhythmKit(MY_RHYTHM_KIT)
	    .addLayer("......XX")
	    .addLayer("O...O...");
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

    
    public Pattern getPattern3() {
	Pattern pattern = new Pattern();
	String voice1 = "Eb5q. C5s*3:2 D5s*3:2 Eb5s*3:2 " +
	    "F5q*3:2 G5q*3:2 F5q*3:2 | " +
	    "Eb5q*3:2 D5q*3:2 Eb5q*3:2 F5q G5q | " +
	    "Eb5q. C5s*3:2 D5s*3:2 Eb5s*3:2 " +
	    "F5q*3:2 G5q*3:2 F5q*3:2 | " +
	    "Eb5h D5h | ";
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
	    .addLayer("O.O.O.O.")
	    .addLayer("..SS..SS");

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
    

    public Pattern getPattern4() {
	
	Pattern pattern = new Pattern();
	String voice1 = "C5i G4i C5i D5i Eb5i C6i Eb5i D5i | " +
	    "C5i. Bb4s- Bb4-i C5i Rq Rq | " +
	    "C5i G4i C5i D5i Eb5i C6i Eb5i D5i | " +
	    "C5i. Bb4s- Bb4-i C5i Rq Rq | " +
	    "Eb5q. C5s*3:2 D5s*3:2 Eb5s*3:2 " +
	    "F5q*3:2 G5q*3:2 F5q*3:2 | " +
	    "Eb5q*3:2 D5q*3:2 Eb5q*3:2 F5q G5q | " +
	    "Eb5q. C5s*3:2 D5s*3:2 Eb5s*3:2 " +
	    "F5q*3:2 G5q*3:2 F5q*3:2 | " +
	    "Eb5h D5h | ";
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
	    .addLayer("O.O.O.O.")
	    .addLayer("..XX..XX");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(8);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(8);
	
        pattern.add("T120 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }

    public Pattern getPattern5() {
	
	Pattern pattern = new Pattern();
	String voice1 = "C5q. D5s Eb5s F5q G5q | " +
	    "A5q. G5s F5s G5q C5i D5i | " + 
	    "Eb5q. F5s Ebs D5q*3:2 Bb4q*3:2 D5q*3:2 | " + 
	    "C5w ";
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
	    .addLayer("O.O.O.O.")
	    .addLayer("..XX..XX");

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
	String voice1 =
	    "C5i G4i C5i D5i Eb5i C6i Eb5i D5i | " +
	    "C5i. Bb4s- Bb4-i C5i Rq Rq | " +
	    "C5i G4i C5i D5i Eb5i C6i Eb5i D5i | " +
	    "C5i. Bb4s- Bb4-i C5i Rq Rq | " + 
	    "Eb5q. C5s*3:2 D5s*3:2 Eb5s*3:2 " +
	    "F5q*3:2 G5q*3:2 F5q*3:2 | " +
	    "Eb5q*3:2 D5q*3:2 Eb5q*3:2 F5q G5q | " +
	    "Eb5q. C5s*3:2 D5s*3:2 Eb5s*3:2 " +
	    "F5q*3:2 G5q*3:2 F5q*3:2 | " +
	    "Eb5h D5h | " +
	    "Eb5h A5q F5q | " +
	    "G5q G5i F5i Eb5q Eb5i D5i | " +
	    "Eb5h F5h | " +
	    "G5w | " +
	    "Eb5q A5q B5q D6q | " +
	    "C6h A5h | " +
	    "F5q G5i F5i E5h | " +
	    "D5w "; //8
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[LOW_TOM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[CLOSED_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("O.O.O.O.")
	    .addLayer("..SS..SS");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(16);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(16);
	
        pattern.add("T120 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }
} 


