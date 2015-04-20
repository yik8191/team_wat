package org.jfugue.publish.webexamples;

import java.io.File;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class PatternExample {
    public static void main(String[] args) {
        Pattern pattern = new Pattern();
        pattern.add("T120 V0 I[Piano] D3i E3i F3i G3i A3i F3i A3q | G#3i E3i G#3q G3i Eb3i G3q");
        pattern.add("V1 I[Piano] D3q A3q D3q A3q | D3q A3q D3q A3q");
        
        try {
            MidiFileManager.savePatternToMidi(pattern, new File("mountainking.mid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
