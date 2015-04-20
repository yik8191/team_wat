package org.jfugue.subs;


import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;


/**
 * (C) 2014 Ethan (trumpetpundit@gmail.com) - see email from 8/28/14)
 */
public class BehindBlueEyes {

    
// *Intro V1 C1 V2 C2 *GS1 *interlude *GS2 *power Chorus (C3) *verse (V3) *conclusion (choral)
    
    
    // Behind Blue Eyes
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException, Exception {
         Player player = new Player();
         Pattern melody = new Pattern();
         Pattern chords = new Pattern();
         Pattern wrapper = new Pattern();
         Pattern bass = new Pattern();
         Pattern bass2 = new Pattern();
         Pattern metronome = new Pattern();
         Pattern vocalHarmony = new Pattern();
         
         
         //**MELODY
         //I54, T115
         //each line is 4 bars
         //verse 1
        Pattern melodyVerseOne = new Pattern();
        melody.add("T115 V0 I54");
        
        melodyVerseOne.add("Bi B B Ai gi Bihi bi bi bi Aqi F#ih Rhi F#i Ai Bi");
        melodyVerseOne.add("Aqit Ehtq Rhs Gi Aqi+C#6qi Bi+D6i Aih+C#6ih Rhqi B5i");
        melodyVerseOne.add("Bi B B Ai gi Bihi bi bi bi Aqi F#ih Rhi F#i Ai Bi");
        melodyVerseOne.add("Aqit Ehtq Rhs Gi Aqi+C#6qi Bi+D6i Aih+C#6ih Rh B D6");
        
        //chorus 1
        Pattern melodyChorusOne = new Pattern();
        melodyChorusOne.add("E6hqi F#6s E6s D6qi D6i D6qi D6i A Bhqq Rq B D6");
        melodyChorusOne.add("E6h D6h Ahq Ai Eiw Rh B D6");
        melodyChorusOne.add("E6h D6hh D6 E6i Aii Gi Aq Bh Rq Ri Ei Gq Ai Aiq F#hq Rq F#+F#4 F#s+F#4s Gs+G4s Eii+E4ii Di+D4i Eww+B4ww+E4ww Rw Rw");
        //Verse 2 with backup singers
        Pattern melodyVerseTwo = new Pattern("");
        melodyVerseTwo.add("Bi B B Ai gi Bihi bi bi bi Aqi F#ih Rhi F#i Ai Bi");
        melodyVerseTwo.add("Aqit Ehtq Rhs Gi Aqi+C#6qi Bi+D6i Aih+C#6ih Rhqi B5i");
        melodyVerseTwo.add("Bi B B Ai gi Bihi bi bi bi Aqi F#ih Rhi F#i Ai Bi");
        melodyVerseTwo.add("Aqit Ehtq Rhs Gi Aqi+C#6qi Bi+D6i Aih+C#6ih Rh B D6");
        //chorus 2 with backup singers
        Pattern melodyChorusTwo = new Pattern("");
        melodyChorusTwo.add("I81 E6hqi F#6s G6s F#6s E6s D6q D6i D6qi D6i A Bhqq Ri Ai B D6");
        melodyChorusTwo.add("E6h Bq D6q Bs Ahis Ai Eiw Rh B D6");
        melodyChorusTwo.add("E6h D6hh D6 E6i As Bs Ai Gi Aq Bh Rq Ri Ei Gq Ai Aiq F#hq Rq F#+F#4 F#s+F#4s Gs+G4s Eii+E4ii Di+D4i Eww+B4ww+E4ww Gi Awhqi I54");
       
        Pattern melodySoloOne = new Pattern();
        melodySoloOne.add("Rw Rw Rw Rw");
        Pattern melodySoloTwo = new Pattern();
        melodySoloTwo.add("Rw Rw Rw Rh I81 B D6");
        
       Pattern melodyConclusion = new Pattern();
        melodyConclusion.add("T90 E6hqi F#6s E6s D6qi D6i D6qi D6i A Bhqq Rq B D6");
        melodyConclusion.add("E6h D6h Ahq Ai Eiw Rh B D6");
        melodyConclusion.add("E6h D6hh D6 E6i Aii Gi Aq Bh Rq Ri Ei Gq Ai Aiq F#hq Rq A B D#6 E6ww");
        
        melody.add(melodyVerseOne);
        melody.add(melodyChorusOne);
        melody.add(melodyVerseTwo);
        melody.add(melodyChorusTwo);
        melody.add(melodySoloOne);
        melody.add(melodySoloTwo);
        melody.add(melodyChorusTwo);
        melody.add(melodyVerseTwo);
        melody.add(melodyConclusion);
        
        //**CHORDS
        //verse 1
        chords.add("V1 I4");
        Pattern chordsVerse = new Pattern();
        chordsVerse.add("Eminw Gmajw Dmajww Cmaj6ww Amajw Rw Eminw Gmajw Dmajww Cmaj6ww Amajw Rw"); 
        //chorus 1
        Pattern chordsChorus = new Pattern();
        chordsChorus.add("V1 Cmajw Dmajw Gmajww Cmajw Dmajw Eminww Bminww Cmajww Dmajww Amajww Rw Rw");
         //verse 2 same as verse 1
       
        //chorus 2 same as chorus 1
       
        Pattern chordsSoloOne = new Pattern();
        chordsSoloOne.add("Emajw Bmajh Amajh Emajw Bmajh Amaj Gmaj");
        Pattern chordsSoloTwo = new Pattern();
        chordsSoloTwo.add("Emajw Bmajh Amajh Emajw Bmajh Amaj Gmaj");
        Pattern chordsConclusion = new Pattern();
        
        
        
        
        chords.add(chordsVerse);
        chords.add(chordsChorus);
        chords.add(chordsVerse);
        chords.add(chordsChorus);
        chords.add(chordsSoloOne);
        chords.add(chordsSoloTwo);
        chords.add(chordsChorus);
        chords.add(chordsVerse);
        chords.add(chordsConclusion);
        
        //**BASS
        //initial wait
        Pattern bassStart = new Pattern();
        bass.add("V2 I33");
        bassStart.add("Rw");
        bassStart.repeat(15);
        bassStart.add("Rh A3i G3i E3i D3i ");
        
         //chorus
        Pattern bassChorus = new Pattern();
        bassChorus.add("C3h G3q C3q D3hqi F#3i G3hqi D3i G3hq G3q C3h G3q C3q D3h A3q D3q E2hqi E2i E3qi E2i E3q D3q B2hqi F#3i B2w");
        bassChorus.add("C3w C4i A3q G3q E3i C3i C#3i D3hqi A3i D3hi F#3i G3i G2i A2hqi A2i A3hi A2q A2i A3hi A2q A2i A3hi A2i D3q ");
        //verse
        Pattern bassVerse = new Pattern();
        bassVerse.add("E3w G3hqi A2i D3wqi A3ii D3i D3i E3i C3hqi G3i C3h G3i A3i A2i B2i A3hqi A2i A3hi A2i D3q E3w G3w D3w D4i B3i A3i F#3i E3i D3i D3i B2i");
        bassVerse.add("C3hqi G3i C3h G3q A3q A2h A2i A2i A2q A3i A3i A3q A3i G3i E3i D3i");
        //Guitar solo section
        Pattern bassSoloOne = new Pattern();
        bassSoloOne.add("E3qi E3i E3qi B3i B3qi A3i A3qi E3i E3qi E3i E3qi B3i B3qi A3i A3qi E3i");
        
        Pattern bassSoloTwo = new Pattern();
        bassSoloTwo.add("D3qi D3i D3qi B3i B3qi A3i A3s A3qs E3i E3qi E3i E3qi B3i B3qi A3i A3qi E3s E3s");
        
      
        Pattern bassConclusion = new Pattern();
        bassConclusion.add("T90 Rw Rw Rw Rw Rw Rw Rw Rw Rw Rw Rw Rw Rw Rq A2 B2 D#3 E3ww");
        
        bass.add(bassStart);
        bass.add(bassChorus);
        bass.add(bassVerse);
        bass.add(bassChorus);
        bass.add(bassSoloOne);
        bass.add(bassSoloTwo);
        bass.add(bassChorus);
        bass.add(bassVerse);
        bass.add(bassConclusion);
        
        //**Vocal Harmony
        //I53?
        vocalHarmony.add("V3 I73");
        Pattern vocalHarmonyVerseOne = new Pattern();
        vocalHarmonyVerseOne.add("Rw");
        vocalHarmonyVerseOne.repeat(16);
        Pattern vocalHarmonyChorusOne = new Pattern();
        vocalHarmonyChorusOne.add("Rw");
        vocalHarmonyChorusOne.repeat(18);
        Pattern vocalHarmonyVerseTwo = new Pattern();
        vocalHarmonyVerseTwo.add("Rw");
        vocalHarmonyVerseTwo.repeat(16);
        Pattern vocalHarmonyChorusTwo = new Pattern();
        vocalHarmonyChorusTwo.add("Rw");
        vocalHarmonyChorusTwo.repeat(18);
        Pattern vocalHarmonySoloOne = new Pattern();
        vocalHarmonySoloOne.add("Rw");
        vocalHarmonySoloOne.repeat(4);
        Pattern vocalHarmonySoloTwo = new Pattern();
        vocalHarmonySoloTwo.add("Rw");
        vocalHarmonySoloTwo.repeat(4);
        Pattern vocalHarmonyChorusThree = new Pattern();
        vocalHarmonyChorusThree.add("E5h E5q B5+D6 B5w+D6w A5+D6 B5w+E6w Rq B5h+D6h");
        vocalHarmonyChorusThree.add("E6h B5h+D6h A5h+D6h B5h+D6h E5w+B5w+E5w Rh B5h+D6h");
        vocalHarmonyChorusThree.add("E6h B5h+D6h A5q+D6q D4q+D5q D5h+G5h A5w+D6w B5w+E6w A5h+D6h F#5w+A5w E5w+B5w A5w+D5w B5wh+D5wh E7w");
        
        
        Pattern vocalHarmonyVerseThree = new Pattern();
        vocalHarmonyVerseThree.add("B6w+D6w E6w+B6w A6w+D6w B6w+D6w A6w+E6w A6w+E6w A6w+C#6w F#6w+A6w");
        vocalHarmonyVerseThree.add("B6w+D6w E6w+B6w A6w+D6w B6w+D6w A6w+E6w A6w+E6w A6w+C#6w F#6h+A6h B5+D6 D6+F#6");
        
        
        /*
        melodyVerseTwo.add("Bi B B Ai gi Bihi bi bi bi Aqi F#ih Rhi F#i Ai Bi");
        melodyVerseTwo.add("Aqit Ehtq Rhs Gi Aqi+C#6qi Bi+D6i Aih+C#6ih Rhqi B5i");
        melodyVerseTwo.add("Bi B B Ai gi Bihi bi bi bi Aqi F#ih Rhi F#i Ai Bi");
        melodyVerseTwo.add("Aqit Ehtq Rhs Gi Aqi+C#6qi Bi+D6i Aih+C#6ih Rh B D6");
        */
        Pattern vocalHarmonyConclusion = new Pattern();
        vocalHarmonyConclusion.add("T90 E6w+G6w D6w+F#w A5w+D6w B5w+E6w");
        vocalHarmonyConclusion.add("E6h+B6h D6h+A6h A5hq+F#6hq A5i+D6i B5iw+E6iw Rh B5h+D6h");
        vocalHarmonyConclusion.add("B5h+E6h A5w+D6w D6h+A6h E6+G6 D6+F#6 B5hq+E6hq Rww E5+A5 B5+D6 B5+D#6 B5ww+Eww");
        
        
        
        //a b d# e
        
        
        /*
         melodyConclusion.add("T90 E6hqi F#6s E6s D6qi D6i D6qi D6i A Bhqq Rq B D6");
        melodyConclusion.add("E6h D6h Ahq Ai Eiw Rh B D6");
        melodyConclusion.add("E6h D6hh D6 E6i Aii Gi Aq Bh Rq Ri Ei Gq Ai Aiq F#hq Rq A B D#6 E6ww");
        */
        
        
        
        
        vocalHarmony.add(vocalHarmonyVerseOne);
        vocalHarmony.add(vocalHarmonyChorusOne);
        vocalHarmony.add(vocalHarmonyVerseTwo);
        vocalHarmony.add(vocalHarmonyChorusTwo);
        vocalHarmony.add(vocalHarmonySoloOne);
        vocalHarmony.add(vocalHarmonySoloTwo);
        vocalHarmony.add(vocalHarmonyChorusThree);
        vocalHarmony.add(vocalHarmonyVerseThree);
        vocalHarmony.add(vocalHarmonyConclusion);
        //**GUITAR I63?
        Pattern guitar = new Pattern();
        guitar.add("V4 I81");
        
        Pattern guitarVerse = new Pattern();
        guitarVerse.add("Rw");
        guitarVerse.repeat(16);
        
        Pattern guitarChorus = new Pattern();
        guitarChorus.add("Rw");
        guitarChorus.repeat(18);
        
        Pattern guitarSoloOne = new Pattern();
        guitarSoloOne.add("A4i C5i D5i E5q G5i E5s D5s C5s D5s E5i A4s B4s G4i E4q Rh E5q G5i D5s E5i G5s D5s E5i G5s D5i D5s E5s C5s B4s G4s F#4s E4i G4i D5i E5i");
        
        
        Pattern guitarSoloTwo = new Pattern();
        guitarSoloTwo.add("Ri E5i G5i B5i D6s E6s D6s C6s D6s E6s D6s C6s D6s E6s D6s C6s D6s E6s D6s C6s G6s A6s G6s E6s G6s A6s G6s F6s G6s A6s B6s C7s D7i E7i D7s E7s D7s B6s A6s G6s F#6s E6s B5i D6i G5i F#5i E5q D5q");
        
        Pattern guitarConclusion = new Pattern();
        
        guitar.add(guitarVerse);
        guitar.add(guitarChorus);
        guitar.add(guitarVerse);
        guitar.add(guitarChorus);
        guitar.add(guitarSoloOne);
        guitar.add(guitarSoloTwo);
        guitar.add(guitarChorus);
        guitar.add(guitarVerse);
        guitar.add(guitarConclusion);
      
        //***BASS TWO
        Pattern bassTwo = new Pattern();
        bassTwo.add("V5 I38");
        Pattern bassTwoVerseOne = new Pattern();
        bassTwoVerseOne.add("Rw");
        bassTwoVerseOne.repeat(16);
        Pattern bassTwoChorusOne = new Pattern();
        bassTwoChorusOne.add("Rw");
        bassTwoChorusOne.repeat(18);
        Pattern bassTwoVerseTwo = new Pattern();
         bassTwoVerseTwo.add("Rw");
        bassTwoVerseTwo.repeat(16);
        Pattern bassTwoChorusTwo = new Pattern();
        bassTwoChorusTwo.add("Rw");
        bassTwoChorusTwo.repeat(18);
        Pattern bassTwoSoloOne = new Pattern();
        bassTwoSoloOne.add("Rw");
        bassTwoSoloOne.repeat(4);
        Pattern bassTwoSoloTwo = new Pattern();
        bassTwoSoloTwo.add("Rw");
        bassTwoSoloTwo.repeat(4);
        //same spot as chorus 2 rep. for everyone else
        Pattern bassTwoChorusThree = new Pattern();
        bassTwoChorusThree.add("E3hqi F#3s E3s D3qi D3i D3qi D3i A2 B2hqq Rq B2 D3");
        bassTwoChorusThree.add("E3h D3h A2hq A2i E2iw Rh B2 D3");
        bassTwoChorusThree.add("E3h D3hh D3 E3i A2ii G2i A2q B2h Rq Ri E3i G3q A3i A3iq F#3hq Rq F#2+F#3 F#2s+F#3s G2s+G3s E2ii+E3ii D2i+D3i E2ww+E3ww G3i A3whqi");
      
        Pattern bassTwoVerseThree = new Pattern();
        bassTwoVerseThree.add("B2i B2 B2 A2i g2i B2ihi b2i b2i b2i A2qi F#2ih Rhi F#2i A2i B2i");
        bassTwoVerseThree.add("A2qit E3htq Rhs G2i A2qi B2i A2ih Rhqi B2i");
        bassTwoVerseThree.add("B2i B2 B2 A2i G2i B2ihi B2i B2i B2i A2qi F#3ih Rhi F#3i A2i B2i");
        bassTwoVerseThree.add("A2qit E3htq Rhs G2i A2qi B2i A2ih Rh B2 D3");
        Pattern bassTwoConclusion = new Pattern();
        
        
        bassTwo.add(bassTwoVerseOne);
        bassTwo.add(bassTwoChorusOne);
        bassTwo.add(bassTwoVerseTwo);
        bassTwo.add(bassTwoChorusTwo);
        bassTwo.add(bassTwoSoloOne);
        bassTwo.add(bassTwoSoloTwo);
        bassTwo.add(bassTwoChorusThree);
        bassTwo.add(bassTwoVerseThree);
        bassTwo.add(bassTwoConclusion);
        
        //***TRUMPET
        Pattern trumpet = new Pattern();
        trumpet.add("V6 I63");
        Pattern trumpetVerseOne = new Pattern();
        trumpetVerseOne.add("Rw");
        trumpetVerseOne.repeat(16);
        Pattern trumpetChorusOne = new Pattern();
        trumpetChorusOne.add("Rw");
        trumpetChorusOne.repeat(18);
        Pattern trumpetVerseTwo = new Pattern();
        trumpetVerseTwo.add("Rw");
        trumpetVerseTwo.repeat(15);
        trumpetVerseTwo.add("Rh B4+D5 D5+G5");
        //add stuff here
        Pattern trumpetChorusTwo = new Pattern();
        trumpetChorusTwo.add("E5h+B5h E5+G5 G5+B5 D5qi+G5qi D5i+G5i D5qi+G5qi D5i+G5i A4+C5 B4hqq+D5hqq Rq B4+D5 D5+G5");
        trumpetChorusTwo.add("E5h+B5h B4h+D5h A4hq+D5hq D5i+F#5i E4iw+G5iw Rh B4+D5 D5+G5");
        trumpetChorusTwo.add("E5qi+B5qi F#5s+C6s E5s+B5s D5w+Aw D5+A5 E5i+B5i A4q+D5q G4i+B4i A4q+D5q B4h+E5h Rq Ri E5i+B5i G5q+D6q A5i+E6i A5iq+E6iq F#5hq+A5hq+D6hq Rq F#5+F#6 F#5s+F#6s G5s+G6s E5ii+E6ii D5i+D6i E5ww+B5ww+E6ww G6i+B6i A6whqi+D7whqi");
        
        
        
        /*
          melodyChorusTwo.add("E6hqi F#6s G6s F#6s E6s D6q D6i D6qi D6i A Bhqq Ri Ai B D6");
        melodyChorusTwo.add("E6h Bq D6q Bs Ahis Ai Eiw Rh B D6");
        melodyChorusTwo.add("E6h D6hh D6 E6i As Bs Ai Gi Aq Bh Rq Ri Ei Gq Ai Aiq F#hq Rq F#+F#4 F#s+F#4s Gs+G4s Eii+E4ii Di+D4i Eww+B4ww+E4ww Gi Awhqi");
       
        */
        Pattern trumpetSoloOne = new Pattern();
        trumpetSoloOne.add("Rw");
        trumpetSoloOne.repeat(4);
        Pattern trumpetSoloTwo = new Pattern();
        trumpetSoloTwo.add("Rw");
        trumpetSoloTwo.repeat(3);
        trumpetSoloTwo.add("Rh B4+D5 D5+G5");
        //add stuff here
        Pattern trumpetChorusThree = new Pattern();
        trumpetChorusThree.add("E5h+B5h E5+G5 G5+B5 D5qi+G5qi D5i+G5i D5qi+G5qi D5i+G5i A4+C5 B4hqq+D5hqq Rq B4+D5 D5+G5");
        trumpetChorusThree.add("E5h+B5h B4h+D5h A4hq+D5hq D5i+F#5i E4iw+G5iw Rh B4+D5 D5+G5");
        trumpetChorusThree.add("E5qi+B5qi F#5s+C6s E5s+B5s D5w+Aw D5+A5 E5i+B5i A4q+D5q G4i+B4i A4q+D5q B4h+E5h Rq Ri E5i+B5i G5q+D6q A5i+E6i A5iq+E6iq F#5hq+A5hq+D6hq Rq F#5+F#6 F#5s+F#6s G5s+G6s E5ii+E6ii D5i+D6i E5ww+B5ww+E6ww G6i+B6i A6whqi+D7whqi");
        
        Pattern trumpetVerseThree = new Pattern();
        trumpetVerseThree.add("Rw");
        trumpetVerseThree.repeat(16);
        Pattern trumpetConclusion = new Pattern();
        
        trumpet.add(trumpetVerseOne)
            .add(trumpetChorusOne)
            .add(trumpetVerseTwo)
            .add(trumpetChorusTwo)
            .add(trumpetSoloOne);
        trumpet.add(trumpetSoloTwo);
        trumpet.add(trumpetChorusThree);
        trumpet.add(trumpetVerseThree);
        trumpet.add(trumpetConclusion);
        
         metronome.add("V15 B7 C7 C7 C7");
         metronome.repeat(18);
         metronome.add("B7w");
        
         wrapper.add(melody);
         wrapper.add(chords);
         wrapper.add(bass);
         wrapper.add(bassTwo);
         wrapper.add(vocalHarmony);
         wrapper.add(guitar);
         wrapper.add(trumpet);
         //wrapper.add(metronome);
         
         System.out.println(wrapper);
         //player.play(wrapper);
         Sequence sequence = player.getSequence(wrapper);
         MidiFileManager.save(sequence, new File("BehindBlueEyes.mid"));
         
    }
    
}
