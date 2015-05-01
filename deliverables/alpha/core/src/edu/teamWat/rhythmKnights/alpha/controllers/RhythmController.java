package edu.teamWat.rhythmKnights.alpha.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.jndi.toolkit.ctx.StringHeadTail;

import edu.teamWat.rhythmKnights.alpha.models.Ticker;
import jdk.nashorn.internal.ir.ReturnNode;

import org.jfugue.theory.Note;

import javax.sound.midi.*;
import javax.swing.*;

import java.beans.beancontext.BeanContext;
import java.io.*;
import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RhythmController {

//	static private float period;  // 6000/tempo = length of beat in ms

	/** Length of period in which player can make a valid move */
	static float actionWindowRadius = 0.15f;
	/** Offset to translate intervals in time */
	static long totalOffset = 68;
	/** Offset from perceived beat in time in the music */
//	static float finalActionOffset = 0.5f;

//	static long startTime;

	/** Location of music files */
//	public static final String MUSIC_FILE = "music/game2longer.ogg";
	public static final String HIT_FILE = "sounds/hit.ogg";
	public static final String DMG_FILE = "sounds/dmg.ogg";

	/** Music player objects */
//	static Music music;
	static Sequence sequence;
	static Sequencer sequencer;

	/** Sound effects */
	protected static Sound hitSound;
	protected static Sound dmgSound;

	/** Marker that corresponds to a beat starting in midi*/
	public static final int NOTE_ON = 0x90;

	/** Keeps track of the beats in the music */
	private static long[] tickTimes;
	private static boolean[] completedTicks;
	private static Ticker.TickerAction[] tickerActions;
	private static int[] playerActions;

	/** Track length */
	private static long trackLength;

	/** Total number of actions */
	public static int numActions;

	/** Number of actions translated forward... Java input is very lagging, making this necessary */
	public static int numTranslated;

	public static void PreloadContent(AssetManager manager) {
//		manager.load(MUSIC_FILE, Music.class);
		manager.load(HIT_FILE, Sound.class);
		manager.load(DMG_FILE, Sound.class);
	}

	public static void LoadContent(AssetManager manager) {

		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}

		if (manager.isLoaded(HIT_FILE)) {
			hitSound = manager.get(HIT_FILE, Sound.class);
		} else {
			hitSound = null;	// Failed to load
		}

		if (manager.isLoaded(DMG_FILE)) {
			dmgSound = manager.get(DMG_FILE, Sound.class);
		} else {
			dmgSound = null;	// Failed to load
		}
	}

	public static void UnloadContent(AssetManager manager) {

		if (hitSound != null) {
			hitSound = null;
			manager.unload(HIT_FILE);
		}

		if (dmgSound != null) {
			dmgSound = null;
			manager.unload(DMG_FILE);
		}
	}

	public static void init(FileHandle audiohandle, Ticker ticker) throws Exception{
		sequence = MidiSystem.getSequence(audiohandle.read());
		Track track = sequence.getTracks()[0];
		ArrayList<Long> tempTickTimes = new ArrayList<Long>();
		ArrayList<Ticker.TickerAction> tempTickerActions = new ArrayList<Ticker.TickerAction>();

		MidiEvent tempEvent;
		MidiEvent event = null;
		MidiEvent prevEvent = null;
		boolean addHalfBeat = false;

		for (int i = 0; i < track.size(); i++) {
			tempEvent = track.get(i);
			MidiMessage message = tempEvent.getMessage();
			if (message instanceof ShortMessage) {

//				System.out.println(((ShortMessage)message).getCommand() + " " + tempEvent.getTick());

				if (((ShortMessage)message).getCommand() == NOTE_ON) {
					prevEvent = event;
					event = tempEvent;
					if (addHalfBeat) {
						tempTickTimes.add((event.getTick() + prevEvent.getTick()) / 2);
						addHalfBeat = false;
					}
					tempTickTimes.add(event.getTick());
					switch (ticker.getAction()) {
						case MOVE:
							tempTickerActions.add(Ticker.TickerAction.MOVE);
							break;
						case DASH:
							tempTickerActions.add(Ticker.TickerAction.DASH);
							tempTickerActions.add(Ticker.TickerAction.DASH2);
							addHalfBeat = true;
							break;
						case FIREBALL:
							tempTickerActions.add(Ticker.TickerAction.FIREBALL);
							tempTickerActions.add(Ticker.TickerAction.FIREBALL2);
							addHalfBeat = true;
							break;
					}
					ticker.advance();
				}
			}
		}
		if (addHalfBeat) {
			tempTickTimes.add(event.getTick() + (event.getTick() - prevEvent.getTick()) / 2);
		}

		trackLength = sequence.getTickLength();
//		trackLength = 128 * 32;

		numActions = tempTickTimes.size();

		long[] tempTempTickTimes = new long[numActions];
		tickTimes = new long[numActions];
		completedTicks = new boolean[numActions];
		tickerActions = new Ticker.TickerAction[numActions];
		playerActions = new int[numActions];

		for (int i = 0; i < tempTickTimes.size(); i++) {
			tempTempTickTimes[i] = (tempTickTimes.get(i) + totalOffset) % trackLength;
		}

		numTranslated = 0;
		for (int i = 0; i < numActions - 1; i++) {
			if (tempTempTickTimes[numActions - i - 1] < tempTempTickTimes[numActions - i - 2]) {
				numTranslated = i + 1;
				break;
			}
		}

		for (int i = 0; i < numTranslated; i++) {
			tickTimes[i] = tempTempTickTimes[numActions - numTranslated + i];
			completedTicks[i] = false;
			tickerActions[i] = tempTickerActions.get(numActions - numTranslated + i);
			playerActions[i] = PlayerController.CONTROL_NO_ACTION;
		}

		for (int i = 0; i < numActions - numTranslated; i++) {
			tickTimes[i + numTranslated] = tempTempTickTimes[i];
			completedTicks[i + numTranslated] = false;
			tickerActions[i + numTranslated] = tempTickerActions.get(i);
			playerActions[i + numTranslated] = PlayerController.CONTROL_NO_ACTION;
		}

		InputStream is = audiohandle.read();

		sequencer.setSequence(is);
		sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
	}


	public static void launch() {
		sequencer.stop();
		sequencer.start();
	}

	public static long getSequencePosition() {
		return sequencer.getTickPosition();
	}

	public static void playSuccess() {
//		hitSound.play();
	}

	public static void playDamage() {
		dmgSound.play();
	}

	public static boolean getCompleted(int i) {
		return completedTicks[i];
	}

	public static void setCompleted(int i, boolean complete) {
		completedTicks[i] = complete;
	}

	public static Ticker.TickerAction getTickerAction(int i) {
		return tickerActions[i];
	}

	public static long getTick(int i) {
		i = (i + numActions) % numActions;
		return tickTimes[i];
	}

	public static int getClosestEarlierActionIndex(long tick) {
		for (int i = numActions - 1; i > -1; i--) {
			if (tickTimes[i] < tick) return i;
		}
		return 0;
	}

	public static int getPlayerAction(int i) {
		i = (i + numActions) % numActions;
		return playerActions[i];
	}

	public static void setPlayerAction(int i, int code) {
		playerActions[i] = code;
	}

	public static long getTrackLength() {
		return trackLength;
	}

	public static void clearNextAction(int i) {
		int j = (i+1) % numActions;
		completedTicks[j] = false;
		playerActions[j] = PlayerController.CONTROL_NO_ACTION;
	}

	public static int convertToTickerBeatNumber(int actionIndex, Ticker ticker) {
		if (tickerActions[actionIndex] == Ticker.TickerAction.DASH || tickerActions[actionIndex] == Ticker.TickerAction.FIREBALL) {
			actionIndex = (actionIndex + 1) % numActions;
		}

		int countDown = (actionIndex - numTranslated + ticker.numExpandedActions) % ticker.numExpandedActions;

		for (int i = 0; i < ticker.tickerActions.length; i++) {
			if (countDown == 0) return i;
			if (ticker.tickerActions[i+1] == Ticker.TickerAction.FIREBALL || ticker.tickerActions[i+1] == Ticker.TickerAction.DASH) {
				countDown -= 2;
			} else {
				countDown -= 1;
			}
		}

		return -1;
	}

    public static void stopMusic(){
        sequencer.stop();
        return;
    }
}
