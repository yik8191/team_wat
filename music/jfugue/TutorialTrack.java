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
 * This class is for generating tracks 01 through 06 (tutorials)
 * @author Austin Liu
 * @version JFugue 5.01
 */
public class TutorialTrack {
    
    public static void main(String[] args) {
        TutorialTrack tt = new TutorialTrack();
	MidiFileManager mymanager = new MidiFileManager();
	
	// Track1
	Pattern pattern1 = tt.getPattern1();
   	File track1 = new File("midis/01_MM_60.mid");

	try {
     	    mymanager.savePatternToMidi(pattern1, track1);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track2
	Pattern pattern2 = tt.getPattern2();
   	File track2 = new File("midis/02_MM_72.mid");

	try {
     	    mymanager.savePatternToMidi(pattern2, track2);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track3
	Pattern pattern3 = tt.getPattern3();
   	File track3 = new File("midis/03_MM_80.mid");

	try {
     	    mymanager.savePatternToMidi(pattern3, track3);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track4
	Pattern pattern4 = tt.getPattern4();
   	File track4 = new File("midis/04_MMMM_96.mid");

	try {
     	    mymanager.savePatternToMidi(pattern4, track4);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track5
	Pattern pattern5 = tt.getPattern5();
   	File track5 = new File("midis/05_MMMM_108.mid");

	try {
     	    mymanager.savePatternToMidi(pattern5, track5);
         } catch (IOException e) {
	    e.printStackTrace();
	}
	
	// Track 6
	Pattern pattern6 = tt.getPattern6();
   	File track6 = new File("midis/06_MMMM_120.mid");
	try {
     	    mymanager.savePatternToMidi(pattern6, track6);
         } catch (IOException e) {
	    e.printStackTrace();
	}
	// Player player = new Player();
	// player.play(pattern6);
    }

    public Pattern getPattern1() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("....O.O.O.O.O.O.")
	    .addLayer("....^^^^^^^^^^^^");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(2);

	Pattern bpattern = new Pattern("Cq Cq")
	    .repeat(8);
	
        pattern.add("T60 V0 I[Xylophone] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V9 " +
		    "X[Volume_Coarse]=240 X[Volume_Fine]=0 " + rpattern);

        return pattern;
    }

    public Pattern getPattern2() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri");
		put('O', "[ACOUSTIC_BASS_DRUM]i");
		put('S', "[ACOUSTIC_SNARE]i");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s");
		put('^', "[PEDAL_HI_HAT]i");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s");
		put('X', "[HAND_CLAP]s [HAND_CLAP]s");
	    }};

	Rhythm rhythm = new Rhythm()
	    .setRhythmKit(MY_RHYTHM_KIT)
	    .addLayer("....O.O.O.O.O.O.")
	    .addLayer("....^*^*^*^*^*^*");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(2);

	Pattern bpattern = new Pattern("Cq Cq")
	    .repeat(8);
	
        pattern.add("T72 V0 I[Xylophone] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V9 " +  rpattern);

	return pattern;
    }

    
    public Pattern getPattern3() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri");
		put('O', "[ACOUSTIC_BASS_DRUM]i");
		put('S', "[ACOUSTIC_SNARE]i");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s");
		put('^', "[PEDAL_HI_HAT]s Rs");
		put('*', "[CRASH_CYMBAL_1]i");
		put('X', "[HAND_CLAP]s [HAND_CLAP]s");
	    }};

	Rhythm rhythm = new Rhythm()
	    .setRhythmKit(MY_RHYTHM_KIT)
	    .addLayer("^^^.^.^^^.^.^^^.^.^^^.^.");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(2);

	// 3 beats at 240 bpm = 80 bpm
	Pattern bpattern = new Pattern("Ch. Ch.")
	    .repeat(4);
	
        pattern.add("T240 V0 I[Xylophone] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V9 " + rpattern);

        return pattern;
    }
    

    public Pattern getPattern4() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri");
		put('O', "[ACOUSTIC_BASS_DRUM]i");
		put('S', "[ACOUSTIC_SNARE]i");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s");
		put('^', "[PEDAL_HI_HAT]i");
		put('*', "[CRASH_CYMBAL_1]i");
		put('X', "[HAND_CLAP]s [HAND_CLAP]s");
	    }};
	
	Rhythm rhythm = new Rhythm()
	    .setRhythmKit(MY_RHYTHM_KIT)
	    .addLayer("O...O...O...O...")
	    .addLayer(".......*.......*")
	    .addLayer("^...^...^...^...")
	    .addLayer("..s...S...s...S.");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(2);

	Pattern bpattern = new Pattern("Cq Cq Cq Cq")
	    .repeat(4);
	
        pattern.add("T96 V0 I[Xylophone] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V9 " + rpattern);

        return pattern;
    }

    public Pattern getPattern5() {
	Pattern pattern = new Pattern();
	String voice1 = "";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri");
		put('O', "[ACOUSTIC_BASS_DRUM]i");
		put('S', "[ACOUSTIC_SNARE]i");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s");
		put('^', "[PEDAL_HI_HAT]i");
		put('*', "[CRASH_CYMBAL_1]i");
		put('X', "[HAND_CLAP]s [HAND_CLAP]s");
	    }};
	
	Rhythm rhythm = new Rhythm()
	    .setRhythmKit(MY_RHYTHM_KIT)
	    .addLayer("O...O...O...O...")
	    .addLayer(".......*.......*")
	    .addLayer("^.^^^.^^^.^^^.^^");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(2);

	Pattern bpattern = new Pattern("Cq Cq Cq Cq")
	    .repeat(4);
	
        pattern.add("T108 V0 I[Xylophone] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V9 " + rpattern);

        return pattern;
    }

    public Pattern getPattern6() {
	Pattern pattern = new Pattern();
	String voice1 = "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "C#4t Rt Rs Rs C#4t Rt C#4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "C#4t Rt Rs Rs C#4t Rt C#4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "C#4t Rt Rs Rs C#4t Rt C#4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "C#4t Rt Rs Rs C#4t Rt C#4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "G4t Rt Rs Rs G4t Rt G4t Rt Rs " +
	    "C#4t Rt Rs Rs C#4t Rt C#4t Rt Rs ";
	String voice2 = "";

	Pattern mpattern = new Pattern(voice1);

	Rhythm rhythm = new Rhythm()
	    .addLayer("");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(8);

	Pattern bpattern = new Pattern("Ci Rq Ci Rq Ci Rq Ci Rq")
	    .repeat(4);
	
        pattern.add("T120 V0 I[Synth_Drum] " +
		    "X[Volume_Coarse]=0 X[Volume_Fine]=0 " + bpattern);
        pattern.add("V1 I[Xylophone] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " + rpattern);

        return pattern;
    }

    
} 


