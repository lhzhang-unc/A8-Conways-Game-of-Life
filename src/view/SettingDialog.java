package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import controller.LifeController;

public class SettingDialog extends JDialog implements ActionListener{

	private LifeController _controller;
	private JSlider _sizeSlider;

	public SettingDialog(LifeController controller) {

		super();

		_controller = controller;

		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);

		_sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, 10);

		//Turn on labels at major tick marks.
		_sizeSlider.setMajorTickSpacing(70);
		_sizeSlider.setMinorTickSpacing(10);
		_sizeSlider.setPaintTicks(true);
		_sizeSlider.setPaintLabels(true);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);

		// Add buttons to the frame (and spaces between buttons)
		panel.add(new JLabel("Dimensions"));
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(_sizeSlider);
		panel.add(Box.createRigidArea(new Dimension(0, 60)));
		panel.add(okButton);

		this.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.setVisible(false);
		_controller.updateSetting(_sizeSlider.getValue());
	}
}
