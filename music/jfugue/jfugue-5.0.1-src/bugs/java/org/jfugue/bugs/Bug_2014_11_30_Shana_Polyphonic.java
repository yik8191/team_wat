package org.jfugue.bugs;

import org.jfugue.player.Player;

/**
 * Just seeing how this works. I've actually never used Polyphonic Pressure before getting a question over email from Shana Kaylan.
 * 
 * Result: The third note in this example sounds much more warblier than the first.
 */
public class Bug_2014_11_30_Shana_Polyphonic {
	public static void main(String[] args) {
		Player player = new Player();
		player.play("m260.0w :POLY(60,0) R m260.0w :POLY(60,64) R m260.0w :POLY(60,120)");		
	}
}
