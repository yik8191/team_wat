package org.jfugue.bugs;

import org.jfugue.realtime.RealtimePlayer;

/**
 * Problem: A note like "Co-" isn't playing indefinitely. It never sounds unless it's followed by "C-o"
 * Solution: The example given was "I40 Co-". It turns out that the total duration of this pattern is only "o". More needs to be added
 * to the pattern to fill out the duration, and it can't just be rests - "trailing rests are like leading zeros, they just don't make a difference."
 * So, "I40 Co- Rwwww C-o" would work. Or, depending on the application, use the RealtimePlayer to play Co- without knowing when to play C-o.
 */
public class Bug_2014_09_12_Mahesha_OpenEndedNote {
	public static void main(String[] args) {
//		MidiRoundTripRunner.run("V0 I40 C5q- Rw C5-q"); // I40 Co-
		
		try {
			RealtimePlayer rt = new RealtimePlayer();
			rt.play("I40 Do-");
			try {
				Thread.sleep(1600);
			} catch (Exception e) {
				e.printStackTrace();
			}
			rt.play("D-o");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		Sequence sequence = new Player().getSequence(pattern);
//		Track track = sequence.getTracks()[0];
//		for (int i=0; i < track.size(); i++) {
//			MidiEvent event = track.get(i);
//			System.out.println(Integer.toHexString(event.getMessage().getStatus()) + " " + Integer.toHexString(event.getMessage().getMessage()[1]));
//		}
			
	}
}
