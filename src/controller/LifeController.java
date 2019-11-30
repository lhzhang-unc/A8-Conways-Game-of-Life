package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JDialog;

import model.LifeModel;
import view.LifeWidget;
import view.SettingDialog;
import view.Spot;
import view.SpotBoard;
import view.SpotListener;

public class LifeController implements SpotListener, ActionListener {

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
		
		switch(command) {
			case LifeWidget.RESET_COMMAND:
				resetGame();
				break;
			case LifeWidget.SETTING_COMMAND:
				openSetting();
				break;
			case LifeWidget.ADVANCE_COMMAND:
				//Disables ability to set patterns
				_model.setSetBoard(false);
				advanceOneTick();
				break;
			case LifeWidget.RAND_COMMAND:
				randPopulate();
				break;
			case LifeWidget.START_COMMAND:
				StartStop((AbstractButton) e.getSource());
				break;
		}
	}
	
	public void openSetting() {
		
		_view.deselectStartStop();
		_model.setRunning(false);
		JDialog settings = new SettingDialog(this);
		settings.setSize(200, 400);
		settings.setLocationRelativeTo(_view);
		settings.setVisible(true);
	}

	@Override
	public void spotClicked(Spot spot) {

		// If the game isn't in the "set board" stage (Game has started), do nothing
		if (!_model.isSetBoard()) {
			return;
		}

		// Sets the spot color of the selected spot
		spot.setSpotColor(Color.BLACK);
		spot.toggleSpot();
		spot.unhighlightSpot();

	}

	//Highlights exited spot
	@Override
	public void spotEntered(Spot spot) {

		// If the game isn't in the "set board" stage (Game has started), do nothing
		if (!_model.isSetBoard()) {
			return;
		}

		spot.highlightSpot();
	}

	//Unhighlights exited spot
	@Override
	public void spotExited(Spot spot) {

		spot.unhighlightSpot();
	}

	//Resets and clears the game board and allows patterns to once again be set
	public void resetGame() {

		_view.deselectStartStop();
		_model.setRunning(false);
		_model.setSetBoard(true);
		_view.resetBoard();
	}
	
	public void updateSetting(int boardSize, 
			int lowBirth, int highBirth, int lowSurvive, int highSurvive, 
			int sleepTimer,
			boolean torusMode) {
		
		if (boardSize != _model.getBoardSize()) {
			_model.setBoardSize(boardSize);
			_view.recreateBoard();
		}
		_model.setLowBirthThreshold(lowBirth);
		_model.setHighBirthThreshold(highBirth);
		_model.setLowSurviveThreshold(lowSurvive);
		_model.setHighSurviveThreshold(highSurvive);
		_model.setTorusMode(true);
		_model.setSleepTimer(sleepTimer);
	}
	
	public void advanceOneTick() {
		
		//Scans board and designates which spots survive and which spots die
		SpotBoard board = _view.getBoard();
		_model.setSetBoard(false);
		for (Spot spot : board) {
			int numAlive = countPop(spot);
			if (!spot.isEmpty() && numAlive <= _model.getHighBirthThreshold()
					&& numAlive >= _model.getLowBirthThreshold()) {
				spot.setWillLive(true);
			}
			else if (spot.isEmpty() && numAlive <= _model.getHighSurviveThreshold()
					&& numAlive >= _model.getLowSurviveThreshold()) {
				spot.setWillLive(true);
			}
			else {
				spot.setWillLive(false);
			}
		}
		
		//Culls spots that are meant to die and sets spots meant to live
		for (Spot spot : board) {
			if (spot.getWillLive() && spot.isEmpty()) {
				spot.setSpotColor(Color.BLACK);
				spot.toggleSpot();
			}
			else if (!spot.getWillLive()) {
				spot.clearSpot();
			}
			spot.setWillLive(false);
		}
	}
	
	//Counts the number of alive pops around (but not including) a spot. Returns that number
	private int countPop(Spot spot) {
		
		return isAliveSpot(spot.getSpotX() - 1, spot.getSpotY() - 1)
				+ isAliveSpot(spot.getSpotX() - 1, spot.getSpotY() + 0)
				+ isAliveSpot(spot.getSpotX() - 1, spot.getSpotY() + 1)
				+ isAliveSpot(spot.getSpotX() + 0, spot.getSpotY() - 1)
				+ isAliveSpot(spot.getSpotX() + 0, spot.getSpotY() + 1)
				+ isAliveSpot(spot.getSpotX() + 1, spot.getSpotY() - 1)
				+ isAliveSpot(spot.getSpotX() + 1, spot.getSpotY() + 0)
				+ isAliveSpot(spot.getSpotX() + 1, spot.getSpotY() + 1);
	}
	
	//Returns one if the spot is alive, zero if it is dead
	private int isAliveSpot(int x, int y) {
		
		int boardSize = _view.getBoard().getSpotHeight();
		
		if (_model.isTorusMode()) {
			if (x >= boardSize) {
				x -= boardSize;
			}
			else if (x < 0) {
				x += boardSize;
			}
			if (y >= boardSize) {
				y -= boardSize;
			}
			else if (y < 0) {
				y += boardSize;
			}
		}
		if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
			if (!_view.getBoard().getSpotAt(x, y).isEmpty()) {
				return 1;
			}
		}
		return 0;
	}
	
	//Randomly populates the board with a random number of pops from 0 to board size, and random coordinates
	private void randPopulate() {
		//If the game has already started, do nothing
		if (!_model.isSetBoard()) {
			return;
		}
		SpotBoard board = _view.getBoard();
		//Resets the board before filling
		resetGame();
		
		int numPops = (int) (Math.random() * (_model.getBoardSize() * _model.getBoardSize()));
		List<Integer> randCoord = new ArrayList<Integer>();
		
		for (int i = 0; i < numPops; i++) {
			int coord = (int) (Math.random() * (_model.getBoardSize() * _model.getBoardSize()));
			randCoord.add(coord); 
		}
		
		int counter = 0;
		for (Spot s : board) {
			if(randCoord.contains(counter) && s.isEmpty()) {
				s.setSpotColor(Color.BLACK);
				s.toggleSpot();
			}
			counter++;
		}
	}
	
	private void StartStop(AbstractButton Source) {
		
		if (Source.getModel().isSelected()) {
			System.out.println("Start");
			Thread lifeRunner = new LifeRunner(this);
			lifeRunner.start();
		}
		else {
			System.out.println("Stop");
			_model.setRunning(false);
		}
	}
	
	public void runThread() {
		
		_model.setRunning(true);
		while(_model.isRunning()) {
			advanceOneTick();
			
			try {
				Thread.sleep(_model.getSleepTimer());
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
}
