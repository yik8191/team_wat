package edu.teamWat.rhythmKnights.alpha.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.*;
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
//		if (manager.isLoaded(MUSIC_FILE)) {
//			music = manager.get(MUSIC_FILE, Music.class);
//			music.setLooping(true);
//		} else {
//			music = null;  // Failed to load
//		}

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
//		if (music != null) {
//			music = null;
//			manager.unload(MUSIC_FILE);
//		}

		if (hitSound != null) {
			hitSound = null;
			manager.unload(HIT_FILE);
		}

		if (dmgSound != null) {
			dmgSound = null;
			manager.unload(DMG_FILE);
		}
	}
//
//	public static void init() {
//
//		music = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_FILE));
//		music.setLooping(true);
//	}

	public static void init(String filename, Ticker ticker) throws Exception{
		File midiFile = new File(filename);
		sequence = MidiSystem.getSequence(midiFile);
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
		for (int i = 0; i < numActions; i++) {
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

//		for (int i = 0; i < tempTickTimes.size(); i++) {
//			tickTimes[i] = (tempTickTimes.get(i) + totalOffset) % trackLength;
//			completedTicks[i] = false;
//			tickerActions[i] = tempTickerActions.get(i);
//			playerActions[i] = PlayerController.CONTROL_NO_ACTION;
//		}

		InputStream is = new BufferedInputStream(new FileInputStream(midiFile));

		sequencer.setSequence(is);
		sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
	}


	public static void launch() {
		sequencer.stop();
		sequencer.start();
	}

//	public static void launch(float tempo, File midiFile, Ticker ticker) throws Exception {
//
//		sequence = MidiSystem.getSequence(new File("music/144beat.mid"));
//
//		Track track = sequence.getTracks()[0];
//
//		ArrayList<Long> tempTickTimes = new ArrayList<Long>();
//
//		for (int i = 0; i < track.size(); i++) {
//			MidiEvent event = track.get(i);
//			MidiMessage message = event.getMessage();
//			if (message instanceof ShortMessage) {
//				ShortMessage sm = (ShortMessage)message;
//				if (sm.getCommand() == NOTE_ON) {
//					tempTickTimes.add(event.getTick());
//				}
//			}
//		}
//
//		long[] tickTimes = new long[tempTickTimes.size()];
//		for (int i = 0; i < tempTickTimes.size(); i++) {
//			tickTimes[i] = tempTickTimes.get(i);
//		}
//
//		sequencer = MidiSystem.getSequencer();
//		sequencer.open();
//
//		InputStream inputStream = new BufferedInputStream(new FileInputStream(new File("music/game2.midi")));
//
//		sequencer.setSequence(inputStream);
//		sequencer.start();
//		sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

//		System.out.println(sequencer.getTickPosition());
//		System.out.println(sequencer.getTickLength());

//		period = 60.0f / tempo;
//		if (begun) {
//			music.stop();
//		}
////		maxDriftRate = 60f / (tempo * tempo * actionWindowRadius * actionWindowRadius);
//		begun = true;
//		music.play();
//		music.setVolume(0);
//		startTime = TimeUtils.millis();
//		totalOffset = -0.7f;
//	}

//	/**
//	 * Takes in time and converts it to beat time, a float between 0 and 1. Then checks if this time is within action
//	 * window for a valid beat.
//	 */
//	public static boolean isWithinActionWindow(float actionTime, float anchor, boolean out) {
//		float beatTime = toBeatTime(actionTime) - anchor;
////		if (out) System.out.println(totalOffset + " " + beatTime);
//		return beatTime < actionWindowRadius || (1.0f - beatTime) < actionWindowRadius;
//	}

//	/**
//	 * Method for checking whether or not it's time to move on to the next beat. If we're past the final action point
//	 * but the beat isn't complete, returns true and sets beat to complete. If we're within the action window, sets to
//	 * beat to incomplete. Assumes that this method will be called at least once per action window. (If it's not,
//	 * something else is wrong). This method should only be used to check whether or not it's time to take care of final
//	 * actions. It should not be used to check if the player has moved within the beat. Use isWithinActionWindow() for
//	 * that.
//	 *
//	 * @return returns whether or not it's time to take final actions.
//	 */
//	public static boolean updateBeat() {
//		float beatTime = toBeatTime(music.getPosition());
//		if ((beatTime < actionWindowRadius || (1.0f - beatTime) < actionWindowRadius)) {
//			beatComplete = false;
//		} else if (beatTime > finalActionOffset && !beatComplete) {
//			beatComplete = true;
//			return true;
//		}
//		return false;
//	}

//	public static void sendCalibrationBeat(float time) {
//		float beatTime = toBeatTime(time);
//		if (beatTime > 0.5) beatTime--;
//		totalOffset += 0.5 * (1 - beatTime) * beatTime;
//		if (totalOffset > 0.5) totalOffset--;
//	}

//	public static float toBeatTime(float time) {
//		return ((time - totalOffset * period) % period) / period;
//	}

//	public static float getCurrentTime() {
//		return (music.getPosition()) / period;
//	}

//	public static float getPosition() {
////		System.out.println(music.getPosition() + " " + (float)TimeUtils.timeSinceMillis(startTime)/1000f);
//		return music.getPosition();
//	}

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
}
