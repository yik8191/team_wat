package org.jfugue.bugs;

import org.jfugue.devtools.DiagnosticParserListener;
import org.jfugue.player.Player;

/** 
 * Problem: When the two play() lines were played, only one voice was heard the second time
 * Solution: Player.reset needed to do a better job at initializing variables. Problem fixed. 
 */
public class Bug_2014_09_12_Mahesha_Harmony {
	public static void main(String[] args) {
		Player player = new Player();
		player.getStaccatoParser().addParserListener(new DiagnosticParserListener());
		
		player.play("V0 I0 C D Ew V1 I40 G A Bw");
        player.play("V0 I0 C D Ew V1 I40 G A Bw");
	}
}
