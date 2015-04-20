package org.jfugue.theory;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.parser.ParserListenerAdapter;
import org.staccato.StaccatoParser;

public class ChordProgression_NewFunctionality {

    public static ChordProgression fromChords(String chordString) {
        return ChordProgression_NewFunctionality.fromChords(chordString, new Key("C"));
    }

    public static ChordProgression fromChords(String chordString, Key key) {
        StaccatoParser parser = new StaccatoParser();
        ChordProgressionParserListener listener = new ChordProgressionParserListener();
        parser.addParserListener(listener);
        parser.parse(chordString);
        return ChordProgression_NewFunctionality.fromChords(listener.getChords(), key);
    }

   public static ChordProgression fromChords(List<Chord> chords) {
       return ChordProgression_NewFunctionality.fromChords(chords, new Key("C"));
   }
   
   public static ChordProgression fromChords(List<Chord> chords, Key key) {
       StringBuilder buddy = new StringBuilder();
       for (Chord chord : chords) {
           System.out.println(chord);
           System.out.println(chord.getRoot().getPositionInOctave());
           System.out.println(key.getRoot().getPositionInOctave());
           int index = chord.getRoot().getPositionInOctave() + key.getRoot().getPositionInOctave();
           String numeral = ChordProgression_NewFunctionality.ROMAN_NUMERALS[index];
           if (chord.isMinor()) { numeral = numeral.toLowerCase(); }
           buddy.append(numeral);
           buddy.append(" ");
       }
       return new ChordProgression(buddy.toString().trim()).setKey(key); 
    }

   
   public static String[] ROMAN_NUMERALS = new String[] { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

}

class ChordProgressionParserListener extends ParserListenerAdapter {
    List<Chord> chords;
    
    public ChordProgressionParserListener() {
        super();
        chords = new ArrayList<Chord>();
    }
    
    @Override
    public void onChordParsed(Chord chord) {
        chords.add(chord);
    }
    
    public List<Chord> getChords() {
        return chords;
    }
}