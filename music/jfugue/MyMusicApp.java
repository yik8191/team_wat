import java.io.File;
import java.io.IOException;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiSystem;

import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.pattern.Pattern;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.tools.GetPatternStats;
import org.jfugue.mitools.Rearranger;


public class MyMusicApp {
    public static void main(String[] args) {
	System.out.println("Enter tempo: ");
	String input = System.console().readline();
	int tempo = Integer.parseInt(input);
	Pattern pattern =
	    new Pattern("V0 I[Steel_Drums] Cq Cq Cq Cq")
	    .setTempo(tempo)
	    .repeat(2);
	
	MidiFileManager mymanager = new MidiFileManager();
	try {
            mymanager.savePatternToMidi(pattern, new File(tempo + "beat.mid"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	Player player = new Player();
	player.play(pattern);
    }
} 


