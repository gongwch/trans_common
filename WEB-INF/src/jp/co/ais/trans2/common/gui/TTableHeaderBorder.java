package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.border.*;

/**
 * ヘッダボーダー
 */
public class TTableHeaderBorder implements Border {

	public Insets getBorderInsets(Component c) {
		return new Insets(3, 1, 3, 1);
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

		Color columnColor = TTable.columnColor;

		int red = columnColor.getRed() - 50;
		if (red < 0) {
			red = 0;
		}

		int green = columnColor.getGreen() - 50;
		if (green < 0) {
			green = 0;
		}

		int blue = columnColor.getBlue() - 50;
		if (blue < 0) {
			blue = 0;
		}

		g.setColor(new Color(red, green, blue));
		g.fillRect(x + width - 2, y, 1, height);

		red = columnColor.getRed() + 80;
		if (red > 255) {
			red = 255;
		}

		green = columnColor.getGreen() + 80;
		if (green > 255) {
			green = 255;
		}

		blue = columnColor.getBlue() + 80;
		if (blue > 255) {
			blue = 255;
		}

		g.setColor(new Color(red, green, blue));
		g.fillRect(x + width - 1, y, 1, height);

		red = columnColor.getRed() - 20;
		if (red < 0) {
			red = 0;
		}

		green = columnColor.getGreen() - 20;
		if (green < 0) {
			green = 0;
		}

		blue = columnColor.getBlue() - 20;
		if (blue < 0) {
			blue = 0;
		}

		g.setColor(new Color(red, green, blue));
		g.fillRect(x, height - 2, width, 2);

	}
}
