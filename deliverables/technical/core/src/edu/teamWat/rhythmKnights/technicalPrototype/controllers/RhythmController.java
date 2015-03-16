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
			startTime = TimeUtils.millis();
			begun = true;
			music.play();
		}
	}

	public static BeatState getBeatRegion() {
		long time = TimeUtils.timeSinceMillis(startTime);
		float beatTime = (float)(time - (long)(totalOffset * period) % period) / (float)period;
		if (beatTime < actionWindowRadius || (1.0f - beatTime) > actionWindowRadius) {
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
