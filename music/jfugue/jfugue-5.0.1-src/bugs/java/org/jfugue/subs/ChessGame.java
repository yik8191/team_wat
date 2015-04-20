package org.jfugue.subs;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Sequence;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class ChessGame {
	public static void main(String[] args) throws IOException {
		Pattern pattern = Pattern.fromFile(new File("src/bugs/java/org/jfugue/subs/aChessGame.jfugue"));
		Player player = new Player();
//		player.play(pattern);
		Sequence sequence = player.getSequence(pattern);
		MidiFileManager.save(sequence, new File("aChessGame.mid"));
	}
}
