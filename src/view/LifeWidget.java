package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import controller.LifeController;
import model.LifeModel;

public class LifeWidget extends JPanel {

	private JLabel _message; // Holds message to be displayed
	private LifeController _controller;
	private Board _board;

	public static final String RESET_COMMAND = "RESET_COMMAND";
	public static final String RAND_COMMAND = "RAND_COMMAND";
	public static final String SETTING_COMMAND = "SETTING_COMMAND";
	public static final String ADVANCE_COMMAND = "ADVANCE_COMMAND";
	public static final String START_COMMAND = "START_COMMAND";
	public static final String STOP_COMMAND = "STOP_COMMAND";

	JToggleButton _startStopButton;

	public LifeWidget(LifeController controller) {

		_message = new JLabel();
		_controller = controller;
		_controller.setView(this);

		/* Create Board and message label. */

		_board = new Board(_controller);
		_board.addMouseListener(_controller);

		/* Set layout and place SpotBoard at center. */
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);

		JPanel topMessagePanel = new JPanel();
		topMessagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topMessagePanel.setLayout(new BorderLayout());
		topMessagePanel.add(_message, BorderLayout.WEST);

		JButton settingButton = new JButton("Settings");
		settingButton.setActionCommand(SETTING_COMMAND);
		settingButton.addActionListener(_controller);
		topMessagePanel.add(settingButton, BorderLayout.EAST);

		/* Create subpanel for message area and reset button. */
		JPanel bottomButtonPanel = new JPanel();
		bottomButtonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottomButtonPanel.setLayout(new FlowLayout());

		JButton randButton = new JButton("Randomly Fill");
		randButton.setActionCommand(RAND_COMMAND);
		randButton.addActionListener(_controller);
		bottomButtonPanel.add(randButton);

		/* Reset button. Add ourselves as the action listener. */
		JButton resetButton = new JButton("Restart");
		resetButton.setActionCommand(RESET_COMMAND);
		resetButton.addActionListener(_controller);
		bottomButtonPanel.add(resetButton);

		JButton advanceButton = new JButton("Advance 1 Tick");
		advanceButton.setActionCommand(ADVANCE_COMMAND);
		advanceButton.addActionListener(_controller);
		bottomButtonPanel.add(advanceButton);

		_startStopButton = new JToggleButton("Start/Stop");
		_startStopButton.setActionCommand(START_COMMAND);
		_startStopButton.addActionListener(_controller);
		bottomButtonPanel.add(_startStopButton);

		/* Add subpanel in south area of layout. */

		add(topMessagePanel, BorderLayout.NORTH);
		add(bottomButtonPanel, BorderLayout.SOUTH);

		/* Reset game. */
		_controller.resetGame();
	}

	public void updateMessage(String text) {

		_message.setText(text);
	}

	public Board getBoard() {

		return _board;
	}

	public void resetBoard() {

		_board.repaint();
		_message.setText("Welcome to Conway's Game of Life. Set your Pattern");
	}

	public void deselectStartStop() {
		_startStopButton.setSelected(false);
	}

}
