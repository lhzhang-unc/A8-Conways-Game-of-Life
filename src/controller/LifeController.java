package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import model.LifeModel;
import model.SpotListener;
import view.LifeWidget;
import view.SettingDialog;
import view.Spot;
import view.SpotBoard;

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
				advanceOneTick();
				break;
		}
	}
	
	public void openSetting() {
		
		JDialog settings = new SettingDialog(this);
		settings.setSize(300, 500);
		settings.setVisible(true);
	}

	@Override
	public void spotClicked(Spot spot) {

		// If the game is already over or the current spot is not empty or if the move
		// isn't valid, do nothing
		if (_model.getGameOver()) {
			return;
		}

		/*
		 * Set up player and next player name strings, and player color as local
		 * variables to be used later.
		 */

		// Sets the spot color of the selected spot
		spot.setSpotColor(Color.BLACK);
		spot.toggleSpot();
		spot.unhighlightSpot();

		
		// If the game has been declared over, display a message according to who won,
		// or if there was a tie.
		// Else, declare that it is the next player's turn.
	}

	@Override
	// Highlight spots only if the spot is a valid place to place a spot
	public void spotEntered(Spot spot) {

		if (_model.getGameOver()) {
			return;
		}

		spot.highlightSpot();
	}

	// Unhighlight the spot that was left, and clear the map of flippable spots
	public void spotExited(Spot spot) {

		spot.unhighlightSpot();
	}

	public void resetGame() {

		_model.setGameWon(null);
		_model.setGameOver(false);
		_model.setNextToPlay(LifeModel.Player.BLACK);
		_model.setWhiteScore(0);
		_model.setBlackScore(0);

		_view.resetBoard();
	}
	
	public void updateSetting(int boardSize) {
		
		_model.setBoardSize(boardSize);
		_view.recreateBoard();
	}
	
	public void advanceOneTick() {
		
		SpotBoard board = _view.getBoard();
		for (Spot spot : board) {
			int numAlive = countPop(spot);
			if (!spot.isEmpty() && (numAlive == 2 || numAlive == 3)) {
				spot.setWillLive(true);
			}
			else if (numAlive == 3) {
				spot.setWillLive(true);
			}
			else {
				spot.setWillLive(false);
			}
		}
		
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
	
	private int countPop(Spot spot) {
		
		int numAlive = isAliveSpot(spot.getSpotX() - 1, spot.getSpotY() - 1)
				+ isAliveSpot(spot.getSpotX() - 1, spot.getSpotY() + 0)
				+ isAliveSpot(spot.getSpotX() - 1, spot.getSpotY() + 1)
				+ isAliveSpot(spot.getSpotX() + 0, spot.getSpotY() - 1)
				+ isAliveSpot(spot.getSpotX() + 0, spot.getSpotY() + 1)
				+ isAliveSpot(spot.getSpotX() + 1, spot.getSpotY() - 1)
				+ isAliveSpot(spot.getSpotX() + 1, spot.getSpotY() + 0)
				+ isAliveSpot(spot.getSpotX() + 1, spot.getSpotY() + 1);
		return numAlive;
	}
	
	private int isAliveSpot(int x, int y) {
		
		int boardSize = _view.getBoard().getSpotHeight();
		
		if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
			if (!_view.getBoard().getSpotAt(x, y).isEmpty()) {
				return 1;
			}
		}
		return 0;
	}
}
