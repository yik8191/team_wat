/*
 * JFugue, an Application Programming Interface (API) for Music Programming
 * http://www.jfugue.org
 *
 * Copyright (C) 2003-2014 David Koelle
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jfugue.experiments;

import java.util.HashMap;
import java.util.Map;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class FunWithSegments {
    public static void main(String[] args) {
        Pattern p1 = new Pattern("V1 (E+A)q    V2 (A3+C)q");
        Pattern p2 = new Pattern("V1 Fi. C5s   V2 F3minq");
        
        Pattern p3 = new Pattern(p1, p1);

        System.out.println(p3);
        
        Player player = new Player();
//        player.play(p3);
        
        player.play("V1 (E+A)q  V2 (A3+C)q    V1 (E+A)q     V2 (A3+C)q");

//        player.play("V1 (E+A)q    V1 (E+A)q    V1 (E+A)q    V1 Fi. C5s  V1 (E+A)q  V1 Fi. C5s   V1 (E+A)q   V1 (E+A)q "+    
//"V2 (A3+C)q   V2 (A3+C)q   V2 (A3+C)q   V2 F3minq   V2 (A3+C)q V2 F3minq    V2 (A3+C)q  V2 (A3+C)q");

        Map<String, ExperimentalPattern> patternMap = new HashMap<String, ExperimentalPattern>();
        patternMap.put("p1", new ExperimentalPattern("V1 (E+A) V2 (A3+C)").setDuration("q"));
        patternMap.put("p2", new ExperimentalPattern("V1 (E+A)/1.0 V2 (A3+C)/1.0").setDuration("q")); // The 1.0's are with respect to the set duration. IOW, /0.5 with "q" would be an i.

        Pattern pattern = new Pattern("p1 p1 p1 p2 | p1 p2 p1 p1");
        player.play(pattern);
    }

}
