package org.jfugue.bugs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.StaccatoParser;

public class Bug_2014_08_20_Mahesha_Parens {
    public static void main(String[] args) {
        Pattern mptn = new Pattern();
        mptn.add("I40 (C C# E F R C C# E F C C# E F G G# B C6)i");
        StaccatoParser p = new StaccatoParser();
        System.out.println(p.preprocess(mptn));
        new Player().play(mptn);
    }
}
