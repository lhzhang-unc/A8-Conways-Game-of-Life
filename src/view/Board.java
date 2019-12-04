package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;

import controller.LifeController;
import model.LifeModel;

public class Board extends JPanel {

	private LifeController _controller;

	public Board(LifeController controller) {
		_controller = controller;
		this.setPreferredSize(new Dimension(LifeModel.getDefaultScreenWidth(), LifeModel.getDefaultScreenHeight()));
	}

	public void paint(Graphics g) {

		LifeModel model = _controller.getModel();
		int rowHeight = model.getRowHeight();
		int rowWidth = model.getRowWidth();
		
		//The actual size of the board after integer division
		int boardHeight = rowHeight * model.getBoardHeight();
		int boardWidth = rowWidth * model.getBoardWidth();
		
		//Allows the board to displayed in the middle of the panel
		int heightOffset = model.getHeightOffset();
		int widthOffset = model.getWidthOffset();

		g.setColor(Color.gray);
		g.drawRect(0, 0, LifeModel.getDefaultScreenWidth(), LifeModel.getDefaultScreenHeight());
		g.setColor(Color.white);
		g.fillRect(0, 0, LifeModel.getDefaultScreenWidth(), LifeModel.getDefaultScreenHeight());

		g.setColor(Color.gray);

		//Draw Columns
		for (int i = 0; i < model.getBoardWidth(); i++) {
			g.drawLine(widthOffset + i * rowWidth, heightOffset, widthOffset + i * rowWidth, heightOffset + boardHeight);
		}

		//Draw Rows
		for (int i = 0; i < model.getBoardHeight(); i++) {
			g.drawLine(widthOffset, heightOffset + i * rowHeight, widthOffset + boardWidth, heightOffset + i * rowHeight);
		}

		g.setColor(Color.BLACK);
				
		try {
			//Fills alive Rectangles
			HashSet<Point> aliveSet = model.getAliveSet();
			for (Point p : aliveSet) {
				g.fillRect(p.x, p.y, rowWidth, rowHeight);
			}
		} catch (Exception e) {
			//Catches Concurrent Execution Exception that occurs when the program can't
			//finish reading the HashSet before it needs to do it again
			//Handles by throttling the thread execution frequency by 1ms each time it occurs
			model.setSleepTimer(model.getSleepTimer() + 1);
			model.setThrottled(true);
			//System.out.println("Concurrent Execution Exception Occurred, Thread slowed by 1ms");
		}

	}

	public void addMouseListener(MouseAdapter listener) {

		this.addMouseListener(listener);
	}

	public void removeMouseListener() {

		this.removeMouseListener();
	}

}
