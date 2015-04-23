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

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

public class FunWithChords {
	public static void main(String[] args) {

		ChordProgression cp = new ChordProgression("I7 IV7 V7 I7");
//		Pattern p1 = new Pattern("V0 I[SYNTH_STRINGS_1] "+ cp.eachChordAs("$_wwww"));
//		Pattern p2 = new Pattern("V1 I[PIANO] "+ cp.eachChordAs("$0h $1h | $2h $3h | $0h $1h | $2h $3h"));
//		Pattern p3 = new Pattern("V2 I[ALTO_SAX] "+ cp.eachChordAs("$1q $1q $1q $1q | $3q $1q $2q $0q | $3q $1q $2q $0q | $0q $0q $0q $0q"));
//		Pattern p4 = new Pattern("V3 I[DULCIMER] "+ cp.eachChordAs("$_w $_^w $_^^w $_^^^w"));
//		Pattern p5 = new Pattern(p1, p2, p3, p4).repeat(4);

		
		Pattern p5 = new Pattern("V0 I[PIANO] "+cp.eachChordAs("$_i $_i Ri $_i"));
		new Player().play(p5.repeat(4));
		
//		try {
//			Sequence sequence = player.getSequence(p5);
//			MidiFileManager.save(sequence, new File("2014-08-07_FunWithChords.mid"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
