package com.leonhartley.uhc.utils;

public enum GameState {
	IDLE (-1),
	STARTING (0),
	IN_PROGRESS (1),
	FINISHING(2),
	FINISHED (3);
	
	private int state;

	GameState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return this.state;
	}
}
