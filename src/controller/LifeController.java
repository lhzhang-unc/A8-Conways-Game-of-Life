package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import javax.swing.AbstractButton;
import javax.swing.JDialog;

import model.LifeModel;
import view.LifeWidget;
import view.SettingDialog;
import view.Board;

public class LifeController implements ActionListener, MouseListener {

	private LifeWidget _view;
	private LifeModel _model;

	public LifeController(LifeModel model) {

		_model = model;
	}

	public LifeWidget getView() {

		return _view;
	}

	public void setView(LifeWidget view) {

		_view = view;
	}

	public LifeModel getModel() {

		return _model;
	}

	//Switch case statements that trigger methods depending on which button was selected in the view
	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		switch (command) {
		case LifeWidget.RESET_COMMAND:
			resetGame();
			break;
		case LifeWidget.SETTING_COMMAND:
			openSetting();
			break;
		case LifeWidget.ADVANCE_COMMAND:
			//Changes the message to notify the user that the game is running
			_view.updateMessage("The Game is Running, Press Restart to select a new pattern");
			//If the thread is running stop it and then do nothing
			if (_model.isRunning()) {
				stopGame();
				return;
			}
			advanceOneTick();
			break;
		case LifeWidget.RAND_COMMAND:
			randPopulate();
			break;
		case LifeWidget.START_COMMAND:
			//Changes the message to notify the user that the game is running
			_view.updateMessage("The Game is Running, Press Restart to select a new pattern");
			StartStop((AbstractButton) e.getSource());
			break;
		}
	}

	//Stops any ongoing game and opens a setting dialog
	public void openSetting() {

		stopGame();
		if (_model.isThrottled()) {
			_view.involuntaryThrottle();
		}
		JDialog settings = new SettingDialog(this);
		settings.setSize(250, 500);
		settings.setLocationRelativeTo(_view);
		settings.setVisible(true);
	}

	// Resets and clears the game board and allows patterns to once again be set
	public void resetGame() {

		stopGame();
		_model.setSetBoard(true);
		_model.setAliveGrid(new boolean[_model.getBoardHeight()][_model.getBoardWidth()]);
		_model.setAliveSet(new HashSet<Point>());
		_view.resetBoard();
	}

	//Updates the model with information returned by the setting dialog
	public void updateSetting(int boardHeight, int boardWidth, int lowBirth, int highBirth, int lowSurvive, int highSurvive,
			int sleepTimer, boolean torusMode) {

		if (boardHeight != _model.getBoardHeight() || boardWidth != _model.getBoardWidth()) {
			_model.setBoardHeight(boardHeight);
			_model.setBoardWidth(boardWidth);
			_model.setAliveGrid(new boolean[boardHeight][boardWidth]);
			resetGame();
			_view.involuntaryReset();
		}
		_model.setLowBirthThreshold(lowBirth);
		_model.setHighBirthThreshold(highBirth);
		_model.setLowSurviveThreshold(lowSurvive);
		_model.setHighSurviveThreshold(highSurvive);
		_model.setTorusMode(torusMode);
		_model.setSleepTimer(sleepTimer);
		_view.getBoard().repaint();
	}

	//Advances the game one move/tick
	public void advanceOneTick() {
		
		//Stops the thread and/or does nothing if there are no alive spots
		if (_model.getAliveSet().isEmpty()) {
			_view.updateMessage("There are no alive spots");
			stopGame();
			return;
		}

		// Scans board and designates which spots survive
		_model.setSetBoard(false);
		_model.getAliveSet().clear();
		int rowHeight = _model.getRowHeight();
		int rowWidth = _model.getRowWidth();
		
		int heightOffset = _model.getHeightOffset();
		int widthOffset = _model.getWidthOffset();
		for (int i = 0; i < _model.getBoardWidth(); i++) {
			for (int j = 0; j < _model.getBoardHeight(); j++) {
				int numAlive = countPop(i, j);
				if (_model.getAliveGrid()[j][i] && numAlive <= _model.getHighBirthThreshold()
						&& numAlive >= _model.getLowBirthThreshold()) {
					_model.getAliveSet().add(new Point(i * rowWidth + widthOffset, j * rowHeight + heightOffset));
				} else if (!_model.getAliveGrid()[j][i] && numAlive <= _model.getHighSurviveThreshold()
						&& numAlive >= _model.getLowSurviveThreshold()) {
					_model.getAliveSet().add(new Point(i * rowWidth + widthOffset, j * rowHeight + heightOffset));
				} else {
					_model.getAliveSet().remove(new Point(i * rowWidth + widthOffset, j * rowHeight + heightOffset));
				}
			}
		}
		//Repaints the board and updates the array that keeps track of the state of all the cells
		_view.getBoard().repaint();
		for (int i = 0; i < _model.getBoardWidth(); i++) {
			for (int j = 0; j < _model.getBoardHeight(); j++) {
				if (_model.getAliveSet().contains(new Point(i * rowWidth + widthOffset, j * rowHeight + heightOffset))) {
					_model.getAliveGrid()[j][i] = true;
				} else {
					_model.getAliveGrid()[j][i] = false;
				}
			}
		}
	}

	// Counts the number of alive pops around (but not including) a spot. Returns
	// that number
	private int countPop(int x, int y) {

		return isAliveSpot(x - 1, y - 1) + isAliveSpot(x - 1, y + 0) + isAliveSpot(x - 1, y + 1)
				+ isAliveSpot(x + 0, y - 1) + isAliveSpot(x + 0, y + 1) + isAliveSpot(x + 1, y - 1)
				+ isAliveSpot(x + 1, y + 0) + isAliveSpot(x + 1, y + 1);
	}

	// Returns one if the spot is alive, zero if it is dead
	private int isAliveSpot(int x, int y) {

		if (_model.isTorusMode()) {
			if (x >= _model.getBoardWidth()) {
				x -= _model.getBoardWidth();
			} else if (x < 0) {
				x += _model.getBoardWidth();
			}
			if (y >= _model.getBoardHeight()) {
				y -= _model.getBoardHeight();
			} else if (y < 0) {
				y += _model.getBoardHeight();
			}
		}
		if (x >= 0 && x < _model.getBoardWidth() && y >= 0 && y < _model.getBoardHeight()) {
			if (_model.getAliveGrid()[y][x]) {
				return 1;
			}
		}
		return 0;
	}

	// Randomly populates the board with a random number of pops from 0 to board
	// size, and random coordinates
	private void randPopulate() {
		// If the game has already started, do nothing
		if (!_model.isSetBoard()) {
			return;
		}
		Board board = _view.getBoard();
		int rowHeight = _model.getRowHeight();
		int rowWidth = _model.getRowWidth();
		
		int heightOffset = _model.getHeightOffset();
		int widthOffset = _model.getWidthOffset();
		// Resets the board before filling
		resetGame();

		int numPops = _model.getBoardWidth() + (int) (Math.random() * ((_model.getBoardHeight() * _model.getBoardWidth()) - _model.getBoardWidth()));

		for (int i = 0; i < numPops; i++) {
			int x = (int) (Math.random() * (_model.getBoardWidth())) * rowWidth;
			int y = (int) (Math.random() * (_model.getBoardHeight())) * rowHeight;
			_model.getAliveSet().add(new Point(x + widthOffset, y + heightOffset));
			_model.getAliveGrid()[(int) y / rowHeight][(int) x / rowWidth] = true;
		}

		board.repaint();
	}
	
	//Starts or stops the game depending on the state of the toggle button
	private void StartStop(AbstractButton Source) {

		if (Source.getModel().isSelected()) {
			Thread lifeRunner = new LifeRunner(this);
			lifeRunner.start();
		} else {
			stopGame();
		}
	}
	
	//Stops the game
	public void stopGame() {
		_view.deselectStartStop();
		_model.setRunning(false);
	}


	//Runs the thread using a while loop and a flag
	public void runThread() {

		_model.setRunning(true);
		while (_model.isRunning()) {
			advanceOneTick();
			try {
				Thread.sleep(_model.getSleepTimer());
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	//Paints a rectangle in the grid location of the mouse click
	@Override
	public void mouseClicked(MouseEvent e) {
		
		int rowHeight = _model.getRowHeight();
		int rowWidth = _model.getRowWidth();
		
		int heightOffset = _model.getHeightOffset();
		int widthOffset = _model.getWidthOffset();
		
		//Checks whether the game is in the "set pattern" stage or if the mouse is within the bounds of the board
		if (!_model.isSetBoard()) {
			_view.promptReset();
			return;
		}
		else if (e.getX() > _model.getBoardWidth() * rowWidth + widthOffset
				|| e.getY() > _model.getBoardHeight() * rowHeight + heightOffset
				|| e.getX() < widthOffset
				|| e.getY() < heightOffset) {
			return;
		}
		
		int x = (int) ((e.getX() - widthOffset) / rowWidth);
		int y = (int) ((e.getY() - heightOffset) / rowHeight);

		boolean[][] aliveGrid = _model.getAliveGrid();
		HashSet<Point> aliveSet = _model.getAliveSet();
		aliveGrid[y][x] = !aliveGrid[y][x];
		if (aliveGrid[y][x]) {
			aliveSet.add(new Point(x * rowWidth + widthOffset, y * rowHeight + heightOffset));
		} else {
			aliveSet.remove(new Point(x * rowWidth + widthOffset, y * rowHeight + heightOffset));
		}
		_view.getBoard().repaint();
	}

	//The below methods do nothing but still must be implemented due to the interface
	@Override
	public void mousePressed(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Do nothing
	}
}
