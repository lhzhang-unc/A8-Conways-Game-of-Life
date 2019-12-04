package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

		// Creates the board and adds the controller as a listener
		_board = new Board(_controller);
		_board.addMouseListener(_controller);

		// Sets the layout and puts the board in the center
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);

		// Creates and arranges the top setting/message bar/panel
		JPanel topMessagePanel = new JPanel();
		topMessagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topMessagePanel.setLayout(new BorderLayout());
		topMessagePanel.add(_message, BorderLayout.WEST);

		JButton settingButton = new JButton("Settings");
		settingButton.setActionCommand(SETTING_COMMAND);
		settingButton.addActionListener(_controller);
		topMessagePanel.add(settingButton, BorderLayout.EAST);

		// Creates and arranges the bottom button bar/panel
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

		// Adds the top and bottom bars
		add(topMessagePanel, BorderLayout.NORTH);
		add(bottomButtonPanel, BorderLayout.SOUTH);

		// Tells the controller to reset the game
		_controller.resetGame();
	}

	// Updates the displayed message with the text passed in as a parameter
	public void updateMessage(String text) {

		_message.setText(text);
	}

	public Board getBoard() {

		return _board;
	}

	// Resets the board and message
	public void resetBoard() {

		_board.repaint();
		_message.setText("Welcome to Conway's Game of Life. Set your Pattern");
	}

	// Deselects the Start/Stop toggle button
	public void deselectStartStop() {
		_startStopButton.setSelected(false);
	}

	// Gives the user a prompt to rest the game if needed
	public void promptReset() {
		_controller.stopGame();
		JOptionPane.showMessageDialog(this, "Please press the Restart Button to add a new pattern");
	}

	//Tells user why the board was reset
	public void involuntaryReset() {

		JOptionPane.showMessageDialog(this, "The size change prompted the game to reset");
	}
	
	//Shows message telling user why the delay is now longer
	public void involuntaryThrottle() {

		JOptionPane.showMessageDialog(this, "The Thread Delay was lengthened due to Concurrent Execution Exception(s)");
	}

}
