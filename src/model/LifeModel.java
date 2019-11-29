package model;

public class LifeModel {

	public enum Player {
		WHITE, BLACK
	};

	public enum End {
		BLACK, WHITE, TIE
	};

	private End _gameWon = null;
	private boolean _gameOver = false;
	private Player _nextToPlay = Player.BLACK;
	private int _whiteScore = 0;
	private int _blackScore = 0;
	
	private int _boardSize = 10;
	

	public LifeModel() {

	}

	public End getGameWon() {

		return _gameWon;
	}

	public void setGameWon(End gameWon) {

		_gameWon = gameWon;
	}

	public boolean getGameOver() {

		return _gameOver;
	}

	public void setGameOver(boolean gameOver) {

		_gameOver = gameOver;
	}

	public Player getNextToPlay() {

		return _nextToPlay;
	}

	public void setNextToPlay(Player nextToPlay) {

		_nextToPlay = nextToPlay;
	}

	public int getWhiteScore() {

		return _whiteScore;
	}

	public void setWhiteScore(int score) {

		_whiteScore = score;
	}

	public int getBlackScore() {

		return _blackScore;
	}

	public void setBlackScore(int score) {

		_blackScore = score;
	}
	
	public int getBoardSize() {
		
		return _boardSize;
	}
	
	public void setBoardSize(int size) {
		
		_boardSize = size;
	}
}
