package model;

import java.awt.Point;
import java.util.HashSet;

public class LifeModel {

	private int _boardSize;
	private int _boardHeight;
	private int _boardWidth;
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
	private int _heightOffset;
	private int _widthOffset;

	public LifeModel() {

		setBoardHeight(10);
		setBoardWidth(10);
		_setBoard = true;
		_lowBirthThreshold = 2;
		_highBirthThreshold = 3;
		_lowSurviveThreshold = 3;
		_highSurviveThreshold = 3;
		_torusMode = false;
		setSleepTimer(10);
		_aliveGrid = new boolean[_boardSize][_boardSize];
		_aliveSet = new HashSet<Point>();
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

	public int getBoardHeight() {
		return _boardHeight;
	}

	public void setBoardHeight(int boardHeight) {
		_boardHeight = boardHeight;
		_rowHeight = (int) (LifeModel.getDefaultScreenHeight() / boardHeight);
		_heightOffset = (int) ((getDefaultScreenHeight() - (_rowHeight * boardHeight)) / 2);
	}

	public int getBoardWidth() {
		return _boardWidth;
	}

	public void setBoardWidth(int boardWidth) {
		_boardWidth = boardWidth;
		_rowWidth = (int) (LifeModel.getDefaultScreenWidth() / boardWidth);
		_widthOffset = (int) ((getDefaultScreenWidth() - (_rowWidth * boardWidth)) / 2);
	}

	public int getHeightOffset() {
		return _heightOffset;
	}

	public int getWidthOffset() {
		return _widthOffset;
	}

}
