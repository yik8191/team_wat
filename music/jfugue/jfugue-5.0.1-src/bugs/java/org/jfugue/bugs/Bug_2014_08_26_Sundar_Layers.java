package org.jfugue.bugs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class Bug_2014_08_26_Sundar_Layers {
    public static void main(String[] args) {
//        Pattern pattern = new Pattern("V1 L0 C L1 E L2 G");
//        new Player().play(pattern);
        
        Pattern pattern2 = new Pattern(":CON(100,0) :CON(101,0) :CON(6,1) :CON(7,127) :CON(38,0) L0 C5/0.25 L1 :PW(8192) R/0.025 :PW(9102) R/0.025 :PW(10012) R/0.025 :PW(10922) R/0.025 :PW(11832) R/0.025 :PW(12742) R/0.025 :PW(13652) R/0.025 :PW(14562) R/0.025 :PW(15472) R/0.025 :PW(16382) R/0.025 :CON(100,127) :CON(101,127) V0 L0");
        new Player().play(pattern2);
    }
}
