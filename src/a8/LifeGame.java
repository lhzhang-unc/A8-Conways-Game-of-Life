package a8;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.LifeController;
import model.LifeModel;
import view.LifeWidget;

public class LifeGame {

	//Used to start the game, outside of MVC
	public static void main(String[] args) {
		
		/* Create top level window. */
		JFrame mainFrame = new JFrame();
		//Prevents resizing the board
		mainFrame.setResizable(false);
		mainFrame.setTitle("Conway's Game of Life");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Create panel for content. Uses BorderLayout. */
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		mainFrame.setContentPane(topPanel);

		//Creates the model, controller, and view in that order
		LifeModel lifeModel = new LifeModel();
		LifeController lifeController = new LifeController(lifeModel);
		LifeWidget LifeWidget = new LifeWidget(lifeController);
		topPanel.add(LifeWidget, BorderLayout.CENTER);

		//Makes the window visible
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
