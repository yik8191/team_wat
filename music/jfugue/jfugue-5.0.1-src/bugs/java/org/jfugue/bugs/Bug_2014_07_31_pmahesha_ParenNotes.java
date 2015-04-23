package org.jfugue.bugs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class Bug_2014_07_31_pmahesha_ParenNotes {
	public static void main(String[] args) {
		Pattern mptn = new Pattern();
        mptn.add("I40 (C C# E F G G# B C6)i");
        mptn.repeat(2);
        System.out.println(mptn.toString());
        
        Player player = new Player();
        System.out.println(player.getStaccatoParser().preprocess(mptn));
	}
}
