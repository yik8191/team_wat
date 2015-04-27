import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import org.jfugue.midi.MidiFileManager;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;


public class SeeMidi {
    public static void main(String[] args) throws IOException, InvalidMidiDataException {
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader(isr);
        System.out.print("Enter Midi Filename: \n");
        String s = br.readLine();

        Pattern pattern = MidiFileManager.loadPatternFromMidi(new File(s));
	Player player = new Player();
        player.play(pattern);
        System.out.println(pattern);
    }
}
