package model;

import java.awt.Point;
import java.util.HashSet;

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
	private boolean[][] _aliveGrid;
	private HashSet<Point> _aliveSet;
	private static final int DEFAULT_SCREEN_WIDTH = 500;
	private static final int DEFAULT_SCREEN_HEIGHT = 500;
	private int _rowHeight;
	private int _rowWidth;
	private HashSet<Point> _highlightSet;

	public LifeModel() {

		setBoardSize(10);
		_setBoard = true;
		_lowBirthThreshold = 2;
		_highBirthThreshold = 3;
		_lowSurviveThreshold = 3;
		_highSurviveThreshold = 3;
		_torusMode = false;
		setSleepTimer(10);
		_aliveGrid = new boolean[_boardSize][_boardSize];
		_aliveSet = new HashSet<Point>();
		setHighlightSet(new HashSet<Point>());
	}

	public int getBoardSize() {
		return _boardSize;
	}

	public void setBoardSize(int boardSize) {
		_boardSize = boardSize;
		_rowHeight = (int) (LifeModel.getDefaultScreenHeight() / boardSize);
		_rowWidth = (int) (LifeModel.getDefaultScreenWidth() / boardSize);
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

	public static int getDefaultScreenWidth() {
		return DEFAULT_SCREEN_WIDTH;
	}

	public static int getDefaultScreenHeight() {
		return DEFAULT_SCREEN_HEIGHT;
	}

	public boolean[][] getAliveGrid() {
		return _aliveGrid;
	}

	public void setAliveGrid(boolean[][] aliveGrid) {
		_aliveGrid = aliveGrid;
	}

	public HashSet<Point> getAliveSet() {
		return _aliveSet;
	}

	public void setAliveSet(HashSet<Point> aliveSet) {
		_aliveSet = aliveSet;
	}

	public int getRowHeight() {
		return _rowHeight;
	}

	public int getRowWidth() {
		return _rowWidth;
	}

	public HashSet<Point> getHighlightSet() {
		return _highlightSet;
	}

	public void setHighlightSet(HashSet<Point> highlightSet) {
		_highlightSet = highlightSet;
	}

}
