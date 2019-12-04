package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import controller.LifeController;
import model.LifeModel;

public class SettingDialog extends JDialog implements ActionListener {

	private LifeController _controller;
	private JSpinner _heightSpinner;
	private JSpinner _widthSpinner;
	private JSpinner _lowBirthSpinner;
	private JSpinner _highBirthSpinner;
	private JSpinner _lowSurviveSpinner;
	private JSpinner _highSurviveSpinner;
	private JSpinner _sleepSpinner;
	private JCheckBox _torusMode;

	public SettingDialog(LifeController controller) {

		super();

		_controller = controller;
		setTitle("Settings");
		LifeModel model = _controller.getModel();
		int lowBirthThreshold = model.getLowBirthThreshold();
		int highBirthThreshold = model.getHighBirthThreshold();
		int lowSurviveThreshold = model.getLowSurviveThreshold();
		int highSurviveThreshold = model.getHighSurviveThreshold();

		//Creates amd sets the layout of the setting panel
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		//Creates the number models for the JSpinners
		_heightSpinner = new JSpinner(new SpinnerNumberModel(model.getBoardHeight(), // initial value
				10, // min
				500, // max
				1));
		_widthSpinner = new JSpinner(new SpinnerNumberModel(model.getBoardWidth(), // initial value
				10, // min
				500, // max
				1));
		_lowBirthSpinner = new JSpinner(new SpinnerNumberModel(lowBirthThreshold, // initial value
				0, // min
				6, // max
				1)); // step
		_highBirthSpinner = new JSpinner(new SpinnerNumberModel(highBirthThreshold, // initial value
				0, // min
				6, // max
				1));
		_lowSurviveSpinner = new JSpinner(new SpinnerNumberModel(lowSurviveThreshold, // initial value
				0, // min
				6, // max
				1));
		_highSurviveSpinner = new JSpinner(new SpinnerNumberModel(highSurviveThreshold, // initial value
				0, // min
				6, // max
				1));
		_sleepSpinner = new JSpinner(new SpinnerNumberModel(model.getSleepTimer(), // initial value
				0, // min
				1000, // max
				1));
		//Instantiates the torus mode checkbox
		_torusMode = new JCheckBox("Torus Mode");

		_torusMode.setSelected(model.isTorusMode());

		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);

		// Add Labels, JSpinners, and JCheckboxes to the frame (and spaces between objects)
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(new JLabel("Height: (10-500)"));
		panel.add(_heightSpinner);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(new JLabel("Width: (10-500)"));
		panel.add(_widthSpinner);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(new JLabel("Low Birth Threshold:"));
		panel.add(_lowBirthSpinner);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(new JLabel("High Birth Threshold:"));
		panel.add(_highBirthSpinner);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(new JLabel("Low Survive Threshold:"));
		panel.add(_lowSurviveSpinner);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(new JLabel("High Survive Threshold:"));
		panel.add(_highSurviveSpinner);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(new JLabel("Sleep Length (10-1000ms):"));
		panel.add(_sleepSpinner);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(_torusMode);

		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(okButton);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		this.add(panel);
	}

	//If the new parameters are acceptable, they are passed to the controller to update the model
	//Otherwise, they are rejected and the user is prompted as needed
	@Override
	public void actionPerformed(ActionEvent e) {

		int height = (int) _heightSpinner.getValue();
		int width = (int) _widthSpinner.getValue();
		int lowBirth = (int) _lowBirthSpinner.getValue();
		int highBirth = (int) _highBirthSpinner.getValue();
		int lowSurvive = (int) _lowSurviveSpinner.getValue();
		int highSurvive = (int) _highSurviveSpinner.getValue();
		int sleepTimer = (int) _sleepSpinner.getValue();
		if (lowBirth <= highBirth && lowSurvive <= highSurvive) {
			this.setVisible(false);
			_controller.updateSetting(height, width, lowBirth, highBirth, lowSurvive, highSurvive, sleepTimer,
					_torusMode.isSelected());
		} else {
			JOptionPane.showMessageDialog(this, "Your lower values are higher than they are supposed to be", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
