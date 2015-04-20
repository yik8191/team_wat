import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import org.jfugue.midi.MidiFileManager;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;


public class SeeMidi {
    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        Pattern pattern = MidiFileManager.loadPatternFromMidi(new File("crabcanon.mid"));
	Player player = new Player();
        player.play(pattern);
        System.out.println(pattern);
    }
}
