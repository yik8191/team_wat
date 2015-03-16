package edu.teamWat.rhythmKnights.technicalPrototype.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.utils.TimeUtils;

public class RhythmController{

	static private long period;

	static final float actionWindowRadius = 0.125f;
	static final float totalOffset = 0;
	static final float finalActionOffset = 0.5f;

	private static boolean beatComplete;
	private static boolean begun = false;

	private static long startTime;
	static Music music;

	private RhythmController() {};

	public static void init() {
		music = Gdx.audio.newMusic(Gdx.files.internal("music/game2.wav"));
		music.setLooping(true);
	}

	public static void launch(float tempo) {
		if (!begun) {
			period = (long)(60000.0f / tempo);
			begun = true;
			music.play();
			startTime = TimeUtils.millis();
		}
	}

	public static boolean isWithinActionWindow(long actionTime) {
		float beatTime = (float)(actionTime - (long)(totalOffset * period) % period) / (float)period;
		return beatTime < actionWindowRadius || (1.0f - beatTime) < actionWindowRadius;
	}

	/**
	 * Method for checking whether or not it's time to move on to the next beat.
	 * If we're past the final action point but the beat isn't complete, returns true and sets beat to complete.
	 * If we're within the action window, sets to beat to incomplete.
	 * Assumes that this method will be called at least once per action window. (If it's not, something else is wrong).
	 * This method should only be used to check whether or not it's time to take care of final actions. It should not be
	 * used to check if the player has moved within the beat. Use isWithinActionWindow() for that.
	 *
	 * @return returns whether or not it's time to take final actions.
	 */
	public static boolean updateBeat() {
		long time = TimeUtils.timeSinceMillis(startTime);
		float beatTime = (float)(time - (long)(totalOffset * period) % period) / (float)period;
		if (beatTime > finalActionOffset && !beatComplete) {
			beatComplete = true;
			return true;
		}

		return false;
	}

	@Deprecated
	public static BeatState getBeatRegion() {
		long time = TimeUtils.timeSinceMillis(startTime);
		float beatTime = (float)(time - (long)(totalOffset * period) % period) / (float)period;
		if (beatTime < actionWindowRadius || (1.0f - beatTime) < actionWindowRadius) {
			beatComplete = false;
			return BeatState.PlayerAction;
		} else if (beatTime > finalActionOffset && !beatComplete){
			beatComplete = true;
			return BeatState.FinalAction;
		} else {
			return BeatState.None;
		}
	}

	public static void reset() {
		startTime = TimeUtils.millis();
		music.stop();
		music.play();
	}

	public enum BeatState {
		PlayerAction,
		FinalAction,
		None
	}
}
