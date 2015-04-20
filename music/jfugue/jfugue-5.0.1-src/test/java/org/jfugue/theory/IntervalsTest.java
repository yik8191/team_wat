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

import org.junit.Before;
import org.junit.Test;

public class IntervalsTest {
	@Before
	public void setUp() { }
	
	@Test(expected = java.lang.ArrayIndexOutOfBoundsException.class)
	public void testNthInterval() {
    	Intervals intervals = new Intervals("1 3 5");
    	assertTrue(intervals.getNthInterval(0).equals("1"));
    	assertTrue(intervals.getNthInterval(1).equals("3"));
    	assertTrue(intervals.getNthInterval(2).equals("5"));
    	intervals.getNthInterval(3);
	}
	
    @Test
    public void testRotate() {
    	Intervals intervals = new Intervals("1 3 5");
    	intervals.rotate(1);
    	assertTrue(intervals.toString().equals("3 5 1"));
    }

    @Test
    public void testPatternWithRoot() {
    	Intervals intervals = new Intervals("1 3 5");
    	assertTrue(intervals.setRoot("C").getPattern().toString().equalsIgnoreCase("C5 E5 G5"));
    }
    
    @Test
    public void testCreateIntervalsWithWholeSteps() {
    	Intervals intervals = Intervals.createIntervalsFromNotes("C5 E5 G5");
    	assertTrue(intervals.toString().equals("1 3 5"));
    }

    @Test
    public void testCreateIntervalsWithHalfSteps() {
    	Intervals intervals = Intervals.createIntervalsFromNotes("C5 Eb5 G5");
    	assertTrue(intervals.toString().equals("1 b3 5"));
    }
    
    @Test
    public void testEachSequence() {
        Intervals intervals = new Intervals("1 3 5").setRoot("C").eachIntervalAs("q. q h");
        assertTrue(intervals.getPattern().toString().equalsIgnoreCase("C5q. C5q C5h E5q. E5q E5h G5q. G5q G5h"));
    }

    @Test
    public void testAllSequence() {
        Intervals intervals = new Intervals("1 3 5").setRoot("C").allIntervalsAs("q. q h");
        assertTrue(intervals.getPattern().toString().equalsIgnoreCase("C5q. E5q G5h"));
    }

}
