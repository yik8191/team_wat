/*
 * JFugue, an Application Programming Interface (API) for Music Programming
 * http://www.jfugue.org
 *
 * Copyright (C) 2003-2013 David Koelle
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.jfugue.bugs;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;


//The algorithm generates 3 tracks, these are V0, V1 and V2. V0 is the lead track, V1 are chords and V2 is the bass. The rhythm of V1 and V2 are exactly the same. First V1 and V2 are generated, and then V0 with the following method:
//
//1. Take the next note from V1
//2. Append notes to V0 until it fills the duration of the note taken from V1
//
//The software does this until it reaches the last note of V1, so the V0 track is complete. BUT: 
//
// - Somehow when I export it to a MIDI, and open it with any MIDI editor (example MuseScore or Cakewalk), the notes are much shorter than they're supposed to be, for example: Where should be a whole note, there is only an 8th, and so on.
// - And when playing the MIDI, V1 and V2 are together, but V0 is ahead of the other two tracks. (But they should be together at every note from V1 or V2).
//
//Do you have any idea what the problem should be?
//
//An example:
//
//This musicString geerates the attached midi sequence.
//
//MusicString (You can see that the next few durations from V0 always fills the next V1 duration if you sum up):
public class Bug_2013_05_10_ImreTanacs 
{
	public static void main(String[] args) {
		 Pattern pattern = new Pattern("T[GRAVE]  V1 I[Piano] C5majW G4maj^I G4majH. F4maj^I G4maj^^W C4maj^^H. F4maj^^I G4maj^^I G5majQ F4maj^Q G4maj^^H C4maj^^W F4maj^^W F5majH G4maj^^I G4majI C4maj^^I G4maj^I V0 I0 G5S C5Q E5Q E5Q G5I E5S C5S D5S G5H G5I B5S D5S C5I F5I F5Q D5I B5Q. B5I G5Q E5S E5I E5I C5S G5S C5S C5I B5S G5S G5Q C5Q F5S B5Q. B5S G5W A5W A5Q A5I F5I F5S B5S D5S B5S E5S E5S C5I V2 I0 C3W G3I G3H. F3I G3W C3H. F3I G3I G3Q F3Q G3H C3W F3W F3H G3I G3I C3I G3I");
		 Player player = new Player();
		 player.play(pattern);
//		 try {
//			 MidiFileManager.savePatternToMidi(pattern, new File("test.mid"));
//		 } catch (IOException e) {
//			 e.printStackTrace();
//		 }
	}
}
