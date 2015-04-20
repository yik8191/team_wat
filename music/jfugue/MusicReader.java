import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MusicReader {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#",
					       "E", "F", "F#", "G", "G#",
					       "A", "A#", "B"};

    public static void main(String[] args) throws Exception {
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader(isr);
        System.out.print("Enter Midi Filename: \n");
        String s = br.readLine();
        Sequence sequence = MidiSystem.getSequence(new File(s));

        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber +
			       ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) { 
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + noteName +
					   octave + " key=" + key +
					   " velocity: " + velocity);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + noteName +
					   octave + " key=" + key +
					   " velocity: " + velocity);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }
            System.out.println();
        }

	// Load instruments from the soundbank
	// into the synthesizer
	// Synthesizer synth = MidiSystem.getSynthesizer();
	// synth.loadAllInstruments(soundbank);

	// Midi player
	// Obtains the default Sequencer connected to a default device.
        // Sequencer sequencer = MidiSystem.getSequencer();
	 
        // Opens the device, indicating that it should now acquire any
        // system resources it requires and become operational.
        // sequencer.open();
 
        // create a stream from a file
        // InputStream is =
	// new BufferedInputStream(new FileInputStream(new File(
	// "crabcanon.mid")));
	 
        // Sets the current sequence on which the sequencer operates.
        // The stream must point to MIDI file data.
        // sequencer.setSequence(is);
	 
        // Starts playback of the MIDI data in the currently loaded sequence.
        // sequencer.start();
    }
}
