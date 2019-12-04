package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
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

		//Paints board background
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
			//Creates a duplicate of the AliveSet in the model to reduce concurrent modification
			ArrayList<Point> aliveList = new ArrayList<>(model.getAliveSet());
			for (int i = 0; i < aliveList.size(); i++) {
				Point p = aliveList.get(i);
				g.fillRect(p.x, p.y, rowWidth, rowHeight);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addMouseListener(MouseAdapter listener) {

		this.addMouseListener(listener);
	}

	public void removeMouseListener() {

		this.removeMouseListener();
	}

}
