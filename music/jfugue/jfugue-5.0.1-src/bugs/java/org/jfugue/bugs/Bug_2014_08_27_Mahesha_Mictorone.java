package org.jfugue.bugs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.MicrotonePreprocessor;

/**
 * Problem: Sounded like first note was being played twice. Investigation showed that changing the tone could make it sound like two different notes were identical
 * Solution: There was a problem in PitchWheelFunction converting integers to bytes. As written, the code would have worked for 8-bit bytes, but MIDI uses
 *  7-bit bytes. Fixed that problem, created MidiTools.getLSB and MidiTools.getMSB that deal specifically with 7-bit bytes.
 *  
 */
public class Bug_2014_08_27_Mahesha_Mictorone {
	public static void main(String[] args) {
		// In the latest JAR, First note is getting played twice, last note is getting skipped when we play microtones.
//		Pattern pattern = new Pattern("I40 T120 m327.0 m348.8");
		Pattern pattern = new Pattern("I40 T120 m392.40000000000003w  m418.56000000000006q  m490.50000000000006w  m523.2q  m558.0800174400001w");
//		Pattern pattern = new Pattern("I40 T120 m261.6  m279.04 m261.6  m279.04  m327.0  m279.04 ");
		
		System.out.println(MicrotonePreprocessor.getInstance().preprocess(pattern.toString(), null));
		new Player().play(pattern);
	}
}
