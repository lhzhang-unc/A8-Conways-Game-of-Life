package view;

import java.awt.Color;

public class SpotBoardTheme {

	private Color _lightBGColor;
	private Color _darkBGColor;
	private Color _spotColor;
	private Color _highlightColor;

	public SpotBoardTheme(Color lightBGColor, Color darkBGColor, Color spotColor, Color highlightColor) {

		if (lightBGColor == null || darkBGColor == null || spotColor == null || highlightColor == null) {
			throw new IllegalArgumentException("A Color is null");
		}

		_lightBGColor = lightBGColor;
		_darkBGColor = darkBGColor;
		_spotColor = spotColor;
		_highlightColor = highlightColor;
	}

	public SpotBoardTheme(Color bgColor, Color spotColor, Color highlightColor) {

		this(bgColor, bgColor, spotColor, highlightColor);
	}

	public Color getlightBGColor() {

		return _lightBGColor;
	}

	public Color getDarkBGColor() {

		return _darkBGColor;
	}

	public Color getSpotColor() {

		return _spotColor;
	}

	public Color getHighLightColor() {

		return _highlightColor;
	}
}
