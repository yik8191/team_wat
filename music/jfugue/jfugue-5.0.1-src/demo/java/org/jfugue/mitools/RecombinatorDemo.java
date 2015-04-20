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

package org.jfugue.mitools;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

public class RecombinatorDemo {
	public static void main(String[] args) {
		ChordProgression chordProgression = new ChordProgression("I IV V I").setKey("Cmaj");
		String[] durationStrings = new String[] { "s", "s", "q", "s" };
		
		Recombinator r = new Recombinator(chordProgression.toStringArray(), durationStrings);
		Pattern pattern = r.recombine("$0$1", Recombinator.LoopBehavior.LOOP_BOTH).repeat(4);
		
		Player player = new Player();
		player.play(pattern);
	}
}
