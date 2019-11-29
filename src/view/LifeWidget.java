package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.LifeController;
import model.LifeModel;
import model.SpotBoardTheme;

public class LifeWidget extends JPanel {

	private SpotBoardImpl _board;
	private JLabel _message; // Holds message to be displayed
	private LifeController _controller;
	private SpotBoardTheme _theme;

	public static final String RESET_COMMAND = "RESET_COMMAND";
	public static final String SETTING_COMMAND = "SETTING_COMMAND";
	public static final String ADVANCE_COMMAND = "ADVANCE_COMMAND";

	public LifeWidget(LifeController controller) {

		_message = new JLabel();
		_controller = controller;
		_controller.setView(this);

		/* Create SpotBoard and message label. */
		_theme = new SpotBoardTheme(Color.gray, Color.darkGray, Color.black, Color.yellow);
		LifeModel model = _controller.getModel();
		_board = new SpotBoardImpl(model.getBoardSize(), model.getBoardSize(), _theme);

		/* Set layout and place SpotBoard at center. */
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);

		JPanel topMessagePanel = new JPanel();
		topMessagePanel.setLayout(new FlowLayout());
		topMessagePanel.add(_message);

		/* Create subpanel for message area and reset button. */
		JPanel bottomMessagePanel = new JPanel();
		bottomMessagePanel.setLayout(new FlowLayout());

		/* Reset button. Add ourselves as the action listener. */
		JButton resetButton = new JButton("Restart");
		resetButton.setActionCommand(RESET_COMMAND);
		resetButton.addActionListener(_controller);
		bottomMessagePanel.add(resetButton);

		JButton settingButton = new JButton("Settings");
		settingButton.setActionCommand(SETTING_COMMAND);
		settingButton.addActionListener(_controller);
		bottomMessagePanel.add(settingButton);
		
		JButton advanceButton = new JButton("Advance 1 Tick");
		advanceButton.setActionCommand(ADVANCE_COMMAND);
		advanceButton.addActionListener(_controller);
		bottomMessagePanel.add(advanceButton);

		/* Add subpanel in south area of layout. */

		add(topMessagePanel, BorderLayout.NORTH);
		add(bottomMessagePanel, BorderLayout.SOUTH);

		/*
		 * Add ourselves as a spot listener for all of the spots on the spot board.
		 */
		_board.addSpotListener(_controller);

		/* Reset game. */
		_controller.resetGame();
	}

	public void updateMessage(String text) {

		_message.setText(text);
	}

	public SpotBoard getBoard() {

		return _board;
	}

	public void resetBoard() {
		/*
		 * Clear all spots on board. Uses the fact that SpotBoard implements
		 * Iterable<Spot> to do this in a for-each loop. Sets all the spot colors to a
		 * non-playable color
		 */

		for (Spot spot : _board) {
			spot.clearSpot();
			spot.setSpotColor(Color.gray);
		}

		/* Display game start message. */

		_message.setText("Welcome to Conway's Game of Life.");
	}

	public void recreateBoard() {

		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		LifeModel model = _controller.getModel();
		_board.redraw(model.getBoardSize());
		_board.addSpotListener(_controller);
		this.setCursor(Cursor.getDefaultCursor());

	}

}
