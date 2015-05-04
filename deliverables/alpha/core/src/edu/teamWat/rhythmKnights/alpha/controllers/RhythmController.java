package edu.teamWat.rhythmKnights.alpha.controllers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;

import edu.teamWat.rhythmKnights.alpha.models.Ticker;

import javax.sound.midi.*;

import java.io.*;
import java.util.ArrayList;


public class RhythmController {

//	static private float period;  // 6000/tempo = length of beat in ms

	/** Length of period in which player can make a valid move */
	static float actionWindowRadius = 0.15f;
	/** Offset to translate intervals in time */
	private static long totalOffset;
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


	/**
	 * Reads in a midi file and a ticker. Creates an array of actions that the player may take. The array of actions is
	 * looped once the end of the song is reached.
	 * @param audiohandle handle of the midi file loaded with libgdx
	 * @param ticker ticker object to be used with this level
	 * @throws Exception
	 */
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

		tickTimes = new long[numActions];
		completedTicks = new boolean[numActions];
		tickerActions = new Ticker.TickerAction[numActions];
		playerActions = new int[numActions];

		for (int i = 0; i < tempTickTimes.size(); i++) {
			tickTimes[i] = tempTickTimes.get(i);
			completedTicks[i] = false;
			tickerActions[i] = tempTickerActions.get(i);
			playerActions[i] = InputController.CONTROL_NO_ACTION;
		}

		ticker.period = tickTimes[ticker.numExpandedActions];

		totalOffset =(long)(250 * ((float)sequence.getTickLength() / (float)(sequence.getMicrosecondLength() / 1000.0f)));

		InputStream is = audiohandle.read();

		sequencer.setSequence(is);
		sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
	}

	/**
	 * Start or restart the music playback.
	 */
	public static void launch() {
		sequencer.stop();
		sequencer.start();
	}

	/**
	 * Returns the current position in the midi track, offset by the stupid java midi playback offset
	 * @return the current position in the midi track
	 */
	public static long getSequencePosition() {
		return (sequencer.getTickPosition() - totalOffset + trackLength) % trackLength;
	}

	public static void playSuccess() {
//		hitSound.play();
	}

	public static void playDamage() {
		dmgSound.play();
	}

	/** Returns whether or not the player completed the ith action. */
	public static boolean getCompleted(int i) {
		return completedTicks[i];
	}

	/** Sets the ith action complete or incomplete*/
	public static void setCompleted(int i, boolean complete) {
		completedTicks[i] = complete;
	}

	/** Gets the ith action the player must take */
	public static Ticker.TickerAction getTickerAction(int i) {
		return tickerActions[i];
	}

	/** Gets the time in midi ticks of the ith action the player must take */
	public static long getTick(int i) {
		i = (i + numActions) % numActions;
		return tickTimes[i];
	}

	/** Returns the closest action to the specified midi tick time that happens BEFORE the spceificified time */
	public static int getClosestEarlierActionIndex(long tick) {
		for (int i = numActions - 1; i > -1; i--) {
			if (tickTimes[i] < tick) return i;
		}
		return 0;
	}

	/** Returns what action the player took during when they were supposed to do the ith action, specified by the code in InputController */
	public static int getPlayerAction(int i) {
		i = (i + numActions) % numActions;
		return playerActions[i];
	}

	/** Sets the ith action the player took, specified by the rhythm controller */
	public static void setPlayerAction(int i, int code) {
		playerActions[i] = code;
	}

	/** Gets the length of the midi track in midi ticks */
	public static long getTrackLength() {
		return trackLength;
	}

	/** Clears the next action after the specified action. This ensures that we don't accidentally loop around to completed actions.
	 *  Clears both actions of a dash.
	 * @param i the action whose FOLLOWING action should be cleared.
	 */
	public static void clearNextAction(int i) {
		int j = (i+1) % numActions;
		completedTicks[j] = false;
		playerActions[j] = PlayerController.CONTROL_NO_ACTION;
		if (tickerActions[j] == Ticker.TickerAction.FIREBALL || tickerActions[j] == Ticker.TickerAction.DASH) {
			clearNextAction(j+1);
		}
	}


	/** Takes in the ith action that a player must take and determines which action in the ticker it corresponds to. */
	public static int convertToTickerBeatNumber(int actionIndex, Ticker ticker) {
		if (tickerActions[actionIndex] == Ticker.TickerAction.DASH2 || tickerActions[actionIndex] == Ticker.TickerAction.FIREBALL2) {
			actionIndex = (actionIndex - 1/* + numActions*/) % numActions;
		}

		int countDown = (actionIndex + ticker.numExpandedActions) % ticker.numExpandedActions;

		for (int i = 0; i < ticker.tickerActions.length - 1; i++) {
			if (countDown == 0) return i;
			if (ticker.tickerActions[i] == Ticker.TickerAction.FIREBALL || ticker.tickerActions[i] == Ticker.TickerAction.DASH) {
				countDown -= 2;
			} else {
				countDown -= 1;
			}
		}
		if (countDown == 0) return ticker.tickerActions.length - 1;

		return -1;
	}

	/** Stops the music! :O*/
    public static void stopMusic(){
        sequencer.stop();
    }
}
