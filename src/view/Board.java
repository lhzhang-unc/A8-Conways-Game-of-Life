package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.util.HashSet;

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
		
		int boardHeight = rowHeight * model.getBoardHeight();
		int boardWidth = rowWidth * model.getBoardWidth();

		g.setColor(Color.gray);
		g.drawRect(0, 0, LifeModel.getDefaultScreenWidth(), LifeModel.getDefaultScreenHeight());
		g.setColor(Color.white);
		g.fillRect(0, 0, LifeModel.getDefaultScreenWidth(), LifeModel.getDefaultScreenHeight());

		g.setColor(Color.gray);

		for (int i = 0; i < model.getBoardWidth(); i++) {
			g.drawLine(i * rowWidth, 0, i * rowWidth, boardHeight);
		}

		for (int i = 0; i < model.getBoardHeight(); i++) {
			g.drawLine(0, i * rowHeight, boardWidth, i * rowHeight);
		}

		g.setColor(Color.BLACK);
		try {
			HashSet<Point> aliveSet = model.getAliveSet();
			for (Point p : aliveSet) {
				g.fillRect(p.x, p.y, rowWidth, rowHeight);
			}
		} catch (Exception e) {
			//No
		}
//		g.setColor(Color.BLACK);
//		HashSet<Point> aliveSet = model.getAliveSet();
//		for (Point p : aliveSet) {
//			g.fillRect(p.x, p.y, rowWidth, rowHeight);
//		}

	}

	public void addMouseListener(MouseAdapter listener) {

		this.addMouseListener(listener);
	}

	public void removeMouseListener() {

		this.removeMouseListener();
	}

}
