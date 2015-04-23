package org.jfugue.bugs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;

/**
 * Sound always seems to start off slow
 */
public class Bug_2013_10_08_LPT_PainfulRhythm {
    public static void main(String[] args) {
        Player player = new Player();
        Rhythm rhythm = new Rhythm();
        rhythm.addLayer("O..oO...O..oOO..");
        rhythm.addLayer("..S...S...S...S.");
        rhythm.addLayer("````````````````");
        rhythm.addLayer("...............+");
        Pattern pattern = rhythm.getPattern();
        pattern.repeat(4);
        player.play(pattern);
    }
}
