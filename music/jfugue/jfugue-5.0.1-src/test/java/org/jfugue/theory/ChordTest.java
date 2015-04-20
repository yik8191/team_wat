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

package org.jfugue.theory;

import static org.junit.Assert.assertTrue;

import org.jfugue.pattern.Pattern;
import org.junit.Before;
import org.junit.Test;

public class ChordTest 
{
	@Before
	public void setUp() { }
	
    @Test
    public void testCreateChordByString() {
        Chord chord = new Chord("Cmaj");
        Pattern pattern = chord.getPattern();
        assertTrue(pattern.toString().equalsIgnoreCase("C4MAJ"));
    }

    @Test
    public void testCreateChordByIntervals() {
        Chord chord = new Chord(new Note("D5h"), new Intervals("1 3 5"));
        Pattern pattern = chord.getPattern();
        assertTrue(pattern.toString().equalsIgnoreCase("D5MAJh"));
    }
    
    @Test
    public void testChordInversionByNumber() {
    	Chord chord = new Chord("C4maj");
    	chord.setInversion(1);
    	Note[] notes = chord.getNotes();
    	assertTrue(notes[0].getValue() == 60);
    	assertTrue(notes[1].getValue() == 52);
    	assertTrue(notes[2].getValue() == 55);

    	chord.setInversion(2);
    	notes = chord.getNotes();
    	assertTrue(notes[0].getValue() == 60);
    	assertTrue(notes[1].getValue() == 64);
    	assertTrue(notes[2].getValue() == 55);
    }

    @Test
    public void testChordInversionByBass() {
    	Chord chord = new Chord("C4maj");
    	chord.setBassNote("E");
    	Note[] notes = chord.getNotes();
    	assertTrue(notes[0].getValue() == 60);
    	assertTrue(notes[1].getValue() == 52);
    	assertTrue(notes[2].getValue() == 55);

    	chord.setBassNote("G");
    	notes = chord.getNotes();
    	assertTrue(notes[0].getValue() == 60);
    	assertTrue(notes[1].getValue() == 64);
    	assertTrue(notes[2].getValue() == 55);
    }

}
