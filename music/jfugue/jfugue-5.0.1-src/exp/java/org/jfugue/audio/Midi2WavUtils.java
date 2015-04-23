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
package org.jfugue.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.jfugue.midi.PatchProvider;
import org.jfugue.pattern.PatternProducer;
import org.jfugue.player.Player;
import org.jfugue.player.SequencerManager;

/**
 * This utility generates a WAV file from the given MIDI sequence. 
 * You can also provide a soundbank file and specific patches in the soundbank to load.
 */
public class Midi2WavUtils 
{
    // Private class, no instantiation
    private Midi2WavUtils() { }
    
    /**
     * Helper function, not part of the API
     * TODO: getSequenceFromPattern probably not necessary - most same as player.getSequence
     */
    private static Sequence getSequenceFromPattern(PatternProducer pattern) throws MidiUnavailableException, InvalidMidiDataException {
        SequencerManager.getInstance().setSequencer(MidiSystem.getSequencer(false));
        Player player = new Player();
        return player.getSequence(pattern);        
    }

    /**
     * Creates a WAV file based on the Pattern, using the sounds from the specified soundbank; 
     * to prevent memory problems, this method asks for an array of patches (instruments) to load. 
     */
    public static void createWavFile(PatternProducer pattern, File outputFile) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        createWavFile(AudioUtils.DEFAULT_PATCH_PROVIDER, getSequenceFromPattern(pattern), outputFile);
    }

    /**
     * Creates a WAV file based on the Pattern, using the sounds from the specified soundbank; 
     * to prevent memory problems, this method asks for an array of patches (instruments) to load. 
     */
    public static void createWavFile(PatchProvider patchProvider, PatternProducer pattern, File outputFile) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        createWavFile(patchProvider, getSequenceFromPattern(pattern), outputFile);
    }
    
    /**
     * Creates a WAV file based on the Pattern, using the sounds from the specified soundbank; 
     * to prevent memory problems, this method asks for an array of patches (instruments) to load. 
     */
    public static void createWavFile(Sequence sequence, File outputFile) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        createWavFile(AudioUtils.DEFAULT_PATCH_PROVIDER, sequence, outputFile);
    }
    
    /**
     * Creates a WAV file based on the Sequence, using the sounds from the specified soundbank; 
     * to prevent memory problems, this method asks for an array of patches (instruments) to load.
     */
    public static void createWavFile(PatchProvider patchProvider, Sequence sequence, File outputFile) throws MidiUnavailableException, InvalidMidiDataException, IOException
    {
        // Open the Synthesizer and load the requested instruments
        AudioInputStream stream = AudioUtils.getAudioInputStream(sequence, patchProvider, AudioUtils.DEFAULT_AUDIO_FORMAT, AudioUtils.DEFAULT_INFO);
        try {
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, outputFile);
        }
        finally {
            stream.close();
        }        
    }
}