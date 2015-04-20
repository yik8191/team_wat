package org.jfugue.bugs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.StaccatoParser;

/**
 * Problem: Pattern test=new Pattern("m327.0  m348.8 ( m392.4/0.25  m413.393  m392.4) /0.5 m348.8 "); results in an error
 * Solution: The first element in the parens, m392.4/0.25, cannot have a duration, or else you get m392.4/0.25/0.5 after expansion
 */
public class Bug_2015_02_20_Shana_DefaultAndMicrotone {
	public static void main(String[] args) {
		Player myPlayer = new Player();
		myPlayer.play( ":DEFAULT(duration=.25)");  
		Pattern test=new Pattern("m327.0  m348.8 ( m392.4  m413.393  m392.4) /0.5 m348.8 ");
		String a = new StaccatoParser().preprocess(test);
		System.out.println(a);
		myPlayer.play(a);
	}
}
