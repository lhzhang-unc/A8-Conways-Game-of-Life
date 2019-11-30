package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JPanel;

public class SpotBoardImpl extends JPanel implements SpotBoard {

	private static final int DEFAULT_SCREEN_WIDTH = 500;
	private static final int DEFAULT_SCREEN_HEIGHT = 500;
	protected int _height;
	protected int _width;
	protected SpotBoardTheme _theme;
	protected Spot[][] _spots;
	protected Dimension _preferredSize;

	public SpotBoardImpl(int width, int height, SpotBoardTheme theme) {
		if (width < 1 || height < 1 || width > 500 || height > 500) {
			throw new IllegalArgumentException("Illegal spot board geometry");
		}
		if (theme == null) {
			throw new IllegalArgumentException("The theme was never defined");
		}
		_theme = theme;

		initialize(width, height);
	}

	private void initialize(int width, int height) {
		_height = height;
		_width = width;


		setLayout(new GridLayout(height, width));
		_spots = new Spot[width][height];

		_preferredSize = new Dimension(DEFAULT_SCREEN_WIDTH / width, DEFAULT_SCREEN_HEIGHT / height);

		for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
				Color bg = _theme.getlightBGColor();
				_spots[x][y] = new JSpot(bg, _theme.getSpotColor(), _theme.getHighLightColor(), this, x, y);
				((JSpot) _spots[x][y]).setPreferredSize(_preferredSize);
				add(((JSpot) _spots[x][y]));
			}
		}
	}

	// Getters for SpotWidth and SpotHeight properties

	@Override
	public int getSpotWidth() {
		
		return _spots.length;
	}

	@Override
	public int getSpotHeight() {
		
		return _spots[0].length;
	}

	// Lookup method for Spot at position (x,y)

	@Override
	public Spot getSpotAt(int x, int y) {
		if (x < 0 || x >= getSpotWidth() || y < 0 || y >= getSpotHeight()) {
			throw new IllegalArgumentException("Illegal spot coordinates");
		}

		return _spots[x][y];
	}

	public void setSpotAt(int x, int y, Spot spot) {
		if (x < 0 || x >= getSpotWidth() || y < 0 || y >= getSpotHeight()) {
			throw new IllegalArgumentException("Illegal spot coordinates");
		}
		_spots[x][y] = spot;
	}

	// Convenience methods for (de)registering spot listeners.

	@Override
	public void addSpotListener(SpotListener spot_listener) {
		for (int x = 0; x < getSpotWidth(); x++) {
			for (int y = 0; y < getSpotHeight(); y++) {
				_spots[x][y].addSpotListener(spot_listener);
			}
		}
	}

	@Override
	public void removeSpotListener(SpotListener spot_listener) {
		for (int x = 0; x < getSpotWidth(); x++) {
			for (int y = 0; y < getSpotHeight(); y++) {
				_spots[x][y].removeSpotListener(spot_listener);
			}
		}
	}

	@Override
	public Iterator<Spot> iterator() {
		return new SpotBoardIterator(this);
	}

	public void redraw(int size) {
		
		removeAll();
		
		revalidate();
		repaint();
		initialize(size, size);
	}

}
