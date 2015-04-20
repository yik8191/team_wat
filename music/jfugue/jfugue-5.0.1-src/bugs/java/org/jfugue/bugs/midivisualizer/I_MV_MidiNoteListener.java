package org.jfugue.bugs.midivisualizer;

//called if new midi-key is played
public interface I_MV_MidiNoteListener {
	
	public void midiNoteChanged(int octave, int key, int velocity, boolean activity);
}
