package org.jfugue.bugs;

import org.jfugue.player.Player;

public class Bug_2014_02_02_Thomas_Craemer_Microtones {
    public static void main(String[] args) {
        String micro1 = "Rt m440.0q Rt m521.4814814814815q Rt m537.7777777777778q Rt"
                + "556.875";
        Player player = new Player();
        player.play(micro1);
    }
}

