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

package org.jfugue.tour;

import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

public class TwelveBarBlues {
	public static void main(String[] args) {
		Player player = new Player();
		
		ChordProgression cp = new ChordProgression("I7 I7 I7 I7 IV7 IV7 I7 I7 V7 IV7 I7 I7");
		new Player().play(cp.eachChordAs("$0i $1i $2i $0'6bi $3i $0'6bi $2i $1i")); 
//		try {
//			Sequence sequence = player.getSequence(cp.eachChordAs("T150 $0i $1i $2i $0'6i $3i $0'6i $2i $1i"));
//			MidiFileManager.save(sequence, new File("twelvebar.mid"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
