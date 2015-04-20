package org.jfugue.bugs.midivisualizer;

import org.jfugue.parser.ParserListener;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

//Methods for Realtime-Midievents
public class MidiNoteListener implements ParserListener{
	
	private MV_Model model;
	
	public MidiNoteListener(MV_Model model){
		this.model = model;
	}
	
	//if key is played, set keycolor
	@Override
	public void onNoteParsed(Note note) {
		System.out.println("Octave: " + note.getValue() / 12 + " Key: " 
		+ note.getPositionInOctave() + " Duration: " + note.getDuration()
		+ " isFirstNote: "+note.isFirstNote()+"  isHarmonicNote: "+note.isHarmonicNote()+"  isMelodicNote: "+note.isMelodicNote());
		int octave = note.getValue() / 12;
		int key = note.getPositionInOctave();
		int velocity = note.getOnVelocity();
		model.midiNotesChanged(octave, key, velocity, true);
	}

	//Unused Methods
	@Override
	public void beforeParsingStarts() {
	}

	@Override
	public void afterParsingFinished() {
	}

	@Override
	public void onTrackChanged(byte track) {
		System.out.println("Track changed to "+track);
	}

	@Override
	public void onLayerChanged(byte layer) {
	}

	@Override
	public void onInstrumentParsed(byte instrument) {
	}

	@Override
	public void onTempoChanged(int tempoBPM) {
	}

	@Override
	public void onKeySignatureParsed(byte key, byte scale) {
	}

	@Override
	public void onTimeSignatureParsed(byte numerator, byte powerOfTwo) {
	}

	@Override
	public void onBarLineParsed(long id) {
	}

	@Override
	public void onTrackBeatTimeBookmarked(String timeBookmarkId) {
	}

	@Override
	public void onTrackBeatTimeBookmarkRequested(String timeBookmarkId) {
		System.out.println("Bookmark requested "+timeBookmarkId);
	}

	@Override
	public void onTrackBeatTimeRequested(double time) {
		System.out.println("Time requested "+time);
	}

	@Override
	public void onPitchWheelParsed(byte lsb, byte msb) {
	}

	@Override
	public void onChannelPressureParsed(byte pressure) {
	}

	@Override
	public void onPolyphonicPressureParsed(byte key, byte pressure) {
	}

	@Override
	public void onSystemExclusiveParsed(byte... bytes) {
	}

	@Override
	public void onControllerEventParsed(byte controller, byte value) {
	}

	@Override
	public void onLyricParsed(String lyric) {
	}

	@Override
	public void onMarkerParsed(String marker) {
	}

	@Override
	public void onFunctionParsed(String id, Object message) {
	}

	@Override
	public void onChordParsed(Chord chord) {
	}
}
