package edu.teamWat.rhythmKnights.alpha.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.*;


public class RhythmController {

	static private long period;  // 6000/tempo = length of beat in ms

	/** Length of period in which player can make a valid move */
	static float actionWindowRadius = 0.15f;
	/** Offset to translate intervals in time */
	static float totalOffset = 0f;
	/** Offset from perceived beat in time in the music */
	static float finalActionOffset = 0.5f;

	/** Location of music file */
	public static final String MUSIC_FILE = "music/game2longer.ogg";
	public static final String HIT_FILE = "sounds/hit.ogg";
	public static final String DMG_FILE = "sounds/dmg.ogg";

	/** Have we crossed final action threshold? */
	private static boolean beatComplete;
	/** Is music being played */
	private static boolean begun = false;

	/** Maximum rate at which the beat can drift */
	private static float maxDriftRate;

	/** Music player object */
	static Music music;

	/** Sound effects */
	protected static Sound hitSound;
	protected static Sound dmgSound;


	public static void PreloadContent(AssetManager manager) {
		manager.load(MUSIC_FILE, Music.class);
		manager.load(HIT_FILE, Sound.class);
		manager.load(DMG_FILE, Sound.class);
	}

	public static void LoadContent(AssetManager manager) {
		if (manager.isLoaded(MUSIC_FILE)) {
			music = manager.get(MUSIC_FILE, Music.class);
			music.setLooping(true);
		} else {
			music = null;  // Failed to load
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
		if (music != null) {
			music = null;
			manager.unload(MUSIC_FILE);
		}

		if (hitSound != null) {
			hitSound = null;
			manager.unload(HIT_FILE);
		}

		if (dmgSound != null) {
			dmgSound = null;
			manager.unload(DMG_FILE);
		}
	}

	public static void init() {

		music = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_FILE));
		music.setLooping(true);
	}

	public static void launch(float tempo) {
		period = (long)(60000.0f / tempo);
		if (begun) {
			music.stop();
		}
		maxDriftRate = 60f / (tempo * tempo * actionWindowRadius * actionWindowRadius);
		begun = true;
		music.play();
		totalOffset = 0.3f;
	}

	/**
	 * Takes in time and converts it to beat time, a float between 0 and 1. Then checks if this time is within action
	 * window for a valid beat.
	 */
	public static boolean isWithinActionWindow(long actionTime, float anchor, boolean out) {
		float beatTime = toBeatTime(actionTime) - anchor;
		if (out) System.out.println(totalOffset + " " + beatTime);
		return beatTime < actionWindowRadius || (1.0f - beatTime) < actionWindowRadius;
	}

	/**
	 * Method for checking whether or not it's time to move on to the next beat. If we're past the final action point
	 * but the beat isn't complete, returns true and sets beat to complete. If we're within the action window, sets to
	 * beat to incomplete. Assumes that this method will be called at least once per action window. (If it's not,
	 * something else is wrong). This method should only be used to check whether or not it's time to take care of final
	 * actions. It should not be used to check if the player has moved within the beat. Use isWithinActionWindow() for
	 * that.
	 *
	 * @return returns whether or not it's time to take final actions.
	 */
	public static boolean updateBeat() {
		long time = (long)(music.getPosition() * 1000);
		float beatTime = toBeatTime(time);
		if ((beatTime < actionWindowRadius || (1.0f - beatTime) < actionWindowRadius)) {
			beatComplete = false;
		} else if (beatTime > finalActionOffset && !beatComplete) {
			beatComplete = true;
			return true;
		}
		return false;
	}


	public static void sendCalibrationBeat(long time) {
		float beatTime = toBeatTime(time);
		if (beatTime > 0.5) beatTime--;
		totalOffset += 0.5 * (1 - beatTime) * beatTime;
		if (totalOffset > 0.5) totalOffset--;
	}

	public static float toBeatTime(long time) {
		return (float)((time - (long)(totalOffset * period)) % period) / (float)period;
	}

	public static float getCurrentTime() {
		return (music.getPosition() * 1000f) / (float)period - totalOffset;
	}

	public static float getPosition() {
		return music.getPosition() * 1000f;
	}

	public static void playSuccess() {
		hitSound.play();
	}

	public static void playDamage() {
		dmgSound.play();
	}
}
