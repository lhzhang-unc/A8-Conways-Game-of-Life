package model;

public class LifeModel {

	private int _boardSize;
	private boolean _setBoard;
	private int _lowBirthThreshold;
	private int _highBirthThreshold;
	private int _lowSurviveThreshold;
	private int _highSurviveThreshold;
	private boolean _torusMode;
	private boolean _running;
	private int _sleepTimer;


	public LifeModel() {
		
		_boardSize = 10;
		_setBoard = true;
		_lowBirthThreshold = 2;
		_highBirthThreshold = 3;
		_lowSurviveThreshold = 3;
		_highSurviveThreshold = 3;
		_torusMode = false;
		setSleepTimer(10);
	}

	public int getBoardSize() {
		return _boardSize;
	}

	public void setBoardSize(int boardSize) {
		_boardSize = boardSize;
	}

	public boolean isSetBoard() {
		return _setBoard;
	}

	public void setSetBoard(boolean setBoard) {
		_setBoard = setBoard;
	}

	public int getLowBirthThreshold() {
		return _lowBirthThreshold;
	}

	public void setLowBirthThreshold(int lowBirthThreshold) {
		_lowBirthThreshold = lowBirthThreshold;
	}

	public int getHighBirthThreshold() {
		return _highBirthThreshold;
	}

	public void setHighBirthThreshold(int highBirthThreshold) {
		_highBirthThreshold = highBirthThreshold;
	}

	public int getLowSurviveThreshold() {
		return _lowSurviveThreshold;
	}

	public void setLowSurviveThreshold(int surviveThreshold) {
		_lowSurviveThreshold = surviveThreshold;
	}

	public int getHighSurviveThreshold() {
		return _highSurviveThreshold;
	}

	public void setHighSurviveThreshold(int highSurviveThreshold) {
		_highSurviveThreshold = highSurviveThreshold;
	}

	public boolean isTorusMode() {
		return _torusMode;
	}

	public void setTorusMode(boolean torusMode) {
		_torusMode = torusMode;
	}

	public boolean isRunning() {
		return _running;
	}

	public void setRunning(boolean running) {
		_running = running;
	}

	public int getSleepTimer() {
		return _sleepTimer;
	}

	public void setSleepTimer(int sleepTimer) {
		_sleepTimer = sleepTimer;
	}

	

}
