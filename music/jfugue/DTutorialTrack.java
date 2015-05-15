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
 * This class is for generating tracks 10 through 14
 * @author Austin Liu
 * @version JFugue 5.01
 */
public class DTutorialTrack {

    public static void main(String[] args) throws Exception {
	Player player = new Player();
        DTutorialTrack tt = new DTutorialTrack();
	MidiFileManager mymanager = new MidiFileManager();
	
	// Track D-1
	Pattern pattern1 = tt.getPattern1();
   	File track1 = new File("midis/10_DDDD_60.mid");

	try {
     	    mymanager.savePatternToMidi(pattern1, track1);
         } catch (IOException e) {
	    e.printStackTrace();
	}
	// player.play(pattern1);
	
	// Track D-2
	Pattern pattern2 = tt.getPattern2();
   	File track2 = new File("midis/11_DDDD_72.mid");

	try {
     	    mymanager.savePatternToMidi(pattern2, track2);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track3
	Pattern pattern3 = tt.getPattern3();
   	File track3 = new File("midis/12_DDDD_80.mid");

	try {
     	    mymanager.savePatternToMidi(pattern3, track3);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track4
	Pattern pattern4 = tt.getPattern4();
   	File track4 = new File("midis/13_MMMD_80.mid");

	try {
     	    mymanager.savePatternToMidi(pattern4, track4);
         } catch (IOException e) {
	    e.printStackTrace();
	}

	// Track5
	Pattern pattern5 = tt.getPattern5();
   	File track5 = new File("midis/14_MMMD_96.mid");

	try {
     	    mymanager.savePatternToMidi(pattern5, track5);
         } catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public Pattern getPattern1() {
	Pattern pattern = new Pattern();
	String voice1 = "A4i B4i C5q Ri E5i C5q B4q G#4q Rq Rq " +
	    "A4i B4i C5q Ri E5i C5q B4h Rq Rq ";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASS_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("^.^.^.^.")
	    .addLayer(".X.X.X.X");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	Pattern bpattern = new Pattern("A2q A2q A2q A2q ")
	    .repeat(4);
	
        pattern.add("T60 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }


    public Pattern getPattern2() {
	
	Pattern pattern = new Pattern();
	String voice1 = "E4i A4i C5q Ri B4i A4q G#4q E4q Rq Rq " +
	    "E4i A4i C5q Ri B4i A4q G#4h Rq Rq ";
	String voice2 = "A3q A3q A3q C4q B3q G#3q Rq Rq " +
	    "A3q A3q A3q A3q E3h Rq Rq ";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASS_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern hpattern = new Pattern(voice1);
	Pattern lpattern = new Pattern(voice2);
				       
	Rhythm rhythm = new Rhythm()
	    .addLayer("..^^..^^")
	    .addLayer(".S.S.S.S")
	    .addLayer("X.X.X.X.");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(4);
	
        pattern.add("T72 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + hpattern);
	pattern.add("V2 I[Piano] X[Volume_Coarse]=192 " + lpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }

    
    public Pattern getPattern3() {
	
	Pattern pattern = new Pattern();
	String voice1 = "A4i B4i C5q Ri E5i C4q B4q G#4q Rq Rq " +
	    "A4i B4i C5q Ri E5i C4q B4h Rq Rq " +
	    "A4i B4i C5q Ri E5i C4q B4q G#4q Rq Rq " +
	    "A4i B4i C5q Ri E5i C4q B4h Rq Rq " +
	    "E4i A4i C5q Ri B4i A4q G#4q E4q Rq Rq " +
	    "D4i E4i F4q Ri E4i D4q C4h Rq Rq " +	    
	    "B3i C4i D4q Ri E4i F4q E4q A3q Rq Rq " +
	    "B3i C4i D4q Ri C4i B4q A4h Rq Rq ";
	String voice2 =
	    "A3q A3q A3q C4q G#3q E3q Rq Rq " +
	    "A3q A3q A3q C4q G#3h     Rq Rq " +
	    "A3q A3q A3q C4q G#3q E3q Rq Rq " +
	    "A3q A3q A3q C4q G#3h     Rq Rq " +
	    "A3w             E3q  E3q Rq Rq " +
	    "F3w             A3h      Rh    " +
	    "D3w             E3q  A2q Rh    " +
	    "G#3h.       B3i. A3s A3h Rh    ";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASS_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[PEDAL_HI_HAT]s [PEDAL_HI_HAT]s ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern hpattern = new Pattern(voice1);

	Pattern lpattern = new Pattern(voice2);
				       
	Rhythm rhythm = new Rhythm()
	    .addLayer("..^^..^^")
	    .addLayer("O...O...") 
	    .addLayer("XXXXXXXX");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(16);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(16);
	
        pattern.add("T80 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + hpattern);
	pattern.add("V2 I[Piano] X[Volume_Coarse]=192 " + lpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }

    
    public Pattern getPattern4() {
	Pattern pattern = new Pattern();
	String voice1 = "A4s A4s A4s A4s F5s F5s F5s F5s " +
	    "E5s E5s E5s E5s A5s A5s A5s A5s " +
	    "A5s A5s A5s A5s F5s F5s F5s F5s " +
	    "E5s E5s E5s E5s A4s A4s A4s A4s " +
	    "E5s E5s E5s E5s F5s F5s F5s F5s " +
	    "D5s D5s D5s D5s E5s E5s E5s E5s " +
	    "E5s E5s E5s E5s F5s F5s F5s F5s " +
	    "D5s E5s E5s D5s E5s E5s D5s E5s ";
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[ACOUSTIC_BASS_DRUM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[CRASH_CYMBAL_2]i ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer("........")
	    .addLayer("X.X.X.X.");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	Pattern bpattern = new Pattern("C4q C4q C4q C4q ")
	    .repeat(4);
	
        pattern.add("T80 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }

    
    public Pattern getPattern5() {
	Pattern pattern = new Pattern();
	String voice1 = "A4s A4s  Rs  Rs F5s F5s  Rs  Rs " +
	    "E5s E5s  Rs  Rs A5s A5s  Rs  Rs " +
	    "A5s A5s  Rs  Rs F5s F5s  Rs  Rs " +
	    "E5s E5s  Rs  Rs A4s A4s  Rs  Rs " +
	    "B4s  Rs  Rs B4s C5s  Rs  Rs C5s " +
	    "B4s  Rs  Rs B4s B4s  Rs E5s  Rs " +
	    "B4s  Rs  Rs B4s C5s  Rs  Rs C5s " +
	    "B4s  Rs  Rs B4s E5s  Rs E4s  Rs " ;
	String voice2 = "";

	Map<Character, String> MY_RHYTHM_KIT =
	    new HashMap<Character, String>() {{
		put('.', "Ri ");
		put('O', "[LOW_FLOOR_TOM]i ");
		put('S', "[ACOUSTIC_SNARE]i ");
		put('s', "[ACOUSTIC_SNARE]s [ACOUSTIC_SNARE]s ");
		put('^', "[PEDAL_HI_HAT]i ");
		put('*', "[CRASH_CYMBAL_2]t Rt Rs ");
		put('X', "[HAND_CLAP]i ");
	    }};

	Pattern mpattern = new Pattern(voice1);				       
	
	Rhythm rhythm = new Rhythm()
	    .addLayer(".s....XX")
	    .addLayer("O.O.O.O.");

	Pattern rpattern = rhythm.getPattern();
	rpattern.repeat(4);

	Pattern bpattern = new Pattern("C2q C2q C2q C2q ")
	    .repeat(4);
	
        pattern.add("T96 V0 I[Marimba] " +
		    "X[Volume_Coarse]=192 X[Volume_Fine]=0 " + bpattern);
	pattern.add("V1 I[Piano] X[Volume_Coarse]=192 " + mpattern);
        pattern.add("V9 " +  rpattern);

        return pattern;
    }
    
} 


