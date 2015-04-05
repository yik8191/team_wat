package edu.teamWat.rhythmKnights.alpha.music;

import java.io.File;
import java.io.IOException;

import org.jfugue.player.Player;
import org.jfugue.pattern.Pattern;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.rhythm.Rhythm;

/**
 * This class plays the first game track
 * @author Austin Liu
 * @version JFugue 5.01
 */
public class GameTrack1 {

	/*public static void main(String[] args) {
		GameTrack1 gt1 = new GameTrack1();
		Pattern pattern = gt1.getPattern();
		MidiFileManager mymanager = new MidiFileManager();

		Player player = new Player();
		player.play(pattern);

		try {
			mymanager.savePatternToMidi(pattern, new File("track1.mid"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/
	public GameTrack1() {
		
	}

	public Pattern getPattern() {
		Pattern pattern = new Pattern();
		String voice1 = "G5i C5i G5i C5i G5i C5i Ab5i C5i "
				+ "G5i C5i G5i C5i G5i C5i Ab5i C5i "
				+ "Ab5i C5i G5i C5i F5i C5i Eb5i D5i "
				+ "Ab5i C5i G5i C5i F5i C5i Eb5i D5i "
				+ "G5i C5i G5i B5i D6i C6i Ab5i C6i "
				+ "G5i C5i G5i B5i D6i C6i Ab5i C6i "
				+ "B5i G5i B5i D6i G6i Ab6i G6i G5i "
				+ "B6i F6i Ab6i D6i F6i B5i D6i G5i ";

		String voice2 = "C4i Ri Ri Ri Ri Ri D4i Eb4i "
				+ "C4i Ri Ri Ri Ri Ri D4i Eb4i "
				+ "C4i Ri Ri Ri Ab3i Ri G3i Ri "
				+ "C4i Ri Ri Ri Ab3i Ri G3i Ri "
				+ "C4i Ri Ri Ri Ri Ri D4i Eb4i "
				+ "C4i Ri Ri Ri Ri Ri D4i Eb4i "
				+ "D4i Ri Ri Ri B3i Ri D4i Ri "
				+ "Ab3i Ri F3i Ri Ab3i Ri G3i Ri ";

		Rhythm rhythm = new Rhythm().addLayer("O..oO..oO..oO..o") // This is
																	// Layer 0
				// .addLayer("..S...S...S...S.")
				// .addLayer("````````````````")
				// .addLayer("...............+") // This is Layer 3
				// Replace Layer 3 with this string on the 4th (count from 0)
				// measure
				.setLength(4); // Set the length of the rhythm to 4 measures

		Pattern rpattern = rhythm.getPattern();

		pattern.add("T144 V0 " + voice1);
		pattern.add("V1 " + voice2);
		pattern.add(rpattern);

		return pattern;
	}

}
