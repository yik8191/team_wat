import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiSystem;

import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.pattern.Pattern;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.tools.GetPatternStats;
import org.jfugue.mitools.Rearranger;


public class TestTrack {
    public static void main(String[] args) throws IOException {
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader(isr);
        System.out.print("Enter tempo: \n");
        String tempo = br.readLine();
	int t = Integer.parseInt(tempo);
	Pattern pattern =
	    new Pattern("V0 I[Steel_Drums] Cq Cq Cq Cq ")
	    .setTempo(t)
	    .repeat(2);
	
	MidiFileManager mymanager = new MidiFileManager();
	try {
            mymanager.savePatternToMidi(pattern, new File(tempo + "beat.mid"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	// Player player = new Player();
	// player.play(pattern);
    }
} 


