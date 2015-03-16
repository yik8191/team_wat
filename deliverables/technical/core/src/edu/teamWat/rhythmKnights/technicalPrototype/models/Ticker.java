package edu.teamWat.rhythmKnights.technicalPrototype.models;

import java.awt.*;


/** Ticker */ // lol
public class Ticker {

	private TickerAction[] tickerActions;
	private int beat;

	public Ticker(TickerAction[] actions) {
		tickerActions = actions;
		beat = 0;
	}

	public void reset(TickerAction[] actions) {
		beat = 0;
	}

	public void draw(Canvas canvas) {
	}

	public TickerAction getAction() {
		return TickerAction.MOVE;
	}

	public void advance() {
		beat++;
		beat %= tickerActions.length;
	}

	public enum TickerAction {
		MOVE,
		DASH,
		FREEZE,
		FIREBALL
	}
}
