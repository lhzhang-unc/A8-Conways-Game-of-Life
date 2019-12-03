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
			// Disables ability to set patterns
			_view.updateMessage("The Game is Running, Press Restart to select a new pattern");
			_model.setSetBoard(false);
			advanceOneTick();
			break;
		case LifeWidget.RAND_COMMAND:
			randPopulate();
			break;
		case LifeWidget.START_COMMAND:
			_view.updateMessage("The Game is Running, Press Restart to select a new pattern");
			StartStop((AbstractButton) e.getSource());
			break;
		}
	}

	public void openSetting() {

		_view.deselectStartStop();
		_model.setRunning(false);
		JDialog settings = new SettingDialog(this);
		settings.setSize(250, 400);
		settings.setLocationRelativeTo(_view);
		settings.setVisible(true);
	}

	// Resets and clears the game board and allows patterns to once again be set
	public void resetGame() {

		_view.deselectStartStop();
		_model.setRunning(false);
		_model.setSetBoard(true);
		_model.setAliveGrid(new boolean[_model.getBoardSize()][_model.getBoardSize()]);
		_model.setAliveSet(new HashSet<Point>());
		_view.resetBoard();
	}

	public void updateSetting(int boardSize, int lowBirth, int highBirth, int lowSurvive, int highSurvive,
			int sleepTimer, boolean torusMode) {

		if (boardSize != _model.getBoardSize()) {
			_model.setBoardSize(boardSize);
			_model.setAliveGrid(new boolean[boardSize][boardSize]);
		}
		_model.setLowBirthThreshold(lowBirth);
		_model.setHighBirthThreshold(highBirth);
		_model.setLowSurviveThreshold(lowSurvive);
		_model.setHighSurviveThreshold(highSurvive);
		_model.setTorusMode(torusMode);
		_model.setSleepTimer(sleepTimer);
		_view.getBoard().repaint();
	}

	public void advanceOneTick() {

		// Scans board and designates which spots survive and which spots die
		_model.setSetBoard(false);
		_model.getAliveSet().clear();
		int rowHeight = _model.getRowHeight();
		int rowWidth = _model.getRowWidth();
		for (int i = 0; i < _model.getBoardSize(); i++) {
			for (int j = 0; j < _model.getBoardSize(); j++) {
				int numAlive = countPop(i, j);
				if (_model.getAliveGrid()[j][i] && numAlive <= _model.getHighBirthThreshold()
						&& numAlive >= _model.getLowBirthThreshold()) {
					_model.getAliveSet().add(new Point(i * rowWidth, j * rowHeight));
				} else if (!_model.getAliveGrid()[j][i] && numAlive <= _model.getHighSurviveThreshold()
						&& numAlive >= _model.getLowSurviveThreshold()) {
					_model.getAliveSet().add(new Point(i * rowWidth, j * rowHeight));
				} else {
					_model.getAliveSet().remove(new Point(i * rowWidth, j * rowHeight));
				}
			}
		}
		_view.getBoard().repaint();
		for (int i = 0; i < _model.getBoardSize(); i++) {
			for (int j = 0; j < _model.getBoardSize(); j++) {
				if (_model.getAliveSet().contains(new Point(i * rowWidth, j * rowHeight))) {
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
			if (x >= _model.getBoardSize()) {
				x -= _model.getBoardSize();
			} else if (x < 0) {
				x += _model.getBoardSize();
			}
			if (y >= _model.getBoardSize()) {
				y -= _model.getBoardSize();
			} else if (y < 0) {
				y += _model.getBoardSize();
			}
		}
		if (x >= 0 && x < _model.getBoardSize() && y >= 0 && y < _model.getBoardSize()) {
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
		// Resets the board before filling
		resetGame();

		int numPops = _model.getBoardSize() + (int) (Math.random() * ((_model.getBoardSize() * _model.getBoardSize()) - _model.getBoardSize()));

		for (int i = 0; i < numPops; i++) {
			int x = (int) (Math.random() * (_model.getBoardSize())) * rowWidth;
			int y = (int) (Math.random() * (_model.getBoardSize())) * rowHeight;
			_model.getAliveSet().add(new Point(x, y));
			_model.getAliveGrid()[(int) y / rowHeight][(int) x / rowWidth] = true;
		}

		board.repaint();
	}

	private void StartStop(AbstractButton Source) {

		if (Source.getModel().isSelected()) {
			Thread lifeRunner = new LifeRunner(this);
			lifeRunner.start();
		} else {
			_model.setRunning(false);
		}
	}

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

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int rowHeight = _model.getRowHeight();
		int rowWidth = _model.getRowWidth();
		if (!_model.isSetBoard() 
				|| e.getX() > _model.getBoardSize() * rowWidth
				|| e.getY() > _model.getBoardSize() * rowHeight) {
			return;
		}
		
		int x = (int) (e.getX() / rowWidth);
		int y = (int) (e.getY() / rowHeight);

		boolean[][] aliveGrid = _model.getAliveGrid();
		HashSet<Point> aliveSet = _model.getAliveSet();
		aliveGrid[y][x] = !aliveGrid[y][x];
		if (aliveGrid[y][x]) {
			aliveSet.add(new Point(x * rowWidth, y * rowHeight));
		} else {
			aliveSet.remove(new Point(x * rowWidth, y * rowHeight));
		}
		_view.getBoard().repaint();
	}

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
