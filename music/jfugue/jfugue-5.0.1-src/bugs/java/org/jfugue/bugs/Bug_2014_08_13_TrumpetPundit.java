package org.jfugue.bugs;

import java.io.IOException;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

/**
 * Reported problem: Melody and harmony are getting out of sync, JFugue 4.0.3
 * Solution: In JFugue 5, this seems to work fine
 */
public class Bug_2014_08_13_TrumpetPundit {
    public static void main(String[] args) throws IOException {
        Pattern melody = new Pattern();
         Pattern chords = new Pattern();
         Pattern wrapper = new Pattern();
         ///I19
         melody.add("T50 V0 I19 Ri Abs Abs Abi Eb6i F6q. F6i Eb6i. C6s G5q+Bb5q Ebqi+Abqi Abs Abs Abi Eb6i F6qi F6i Eb6is C6s Ghi+Bbhi Eb6i Ab6i G6i F6q. Eb6i F6i. Ab6s Eb6hi Gs Abs C6i F6i Eb6qi C6i Bbq Abh+Ebh Gh Fh Ebh");
         //I81
         chords.add("V1 I81 Rq Ab3q Fminhq Bb3q+Eb4q Ab3q.+C4q. Eb3i+Ab3i Eb3q+Ab3q Fminh Bb3q Eb3hq+Bb3hq Ab3i G3i Db3hq+Fminhq F3hq+C3hq F3q+C3q G3h+Bb3h+Db4h Bb2q+Eb2q+G3q Ab2h+C3h+Eb3h Bb2h+Db3h+G3h F3h+Ab3h+C4h Eb3h+Ab3h+Bb3h");
        
         
         wrapper.add(melody);
         wrapper.add(chords);
         Player player = new Player();
         //player.saveMidi(wrapper, new File("testFile.mid"));
         player.play(wrapper);
        
     }

}
