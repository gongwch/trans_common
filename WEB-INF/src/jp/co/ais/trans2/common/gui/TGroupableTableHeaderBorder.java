package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.border.*;

/**
 * 結合ヘッダーボーダー
 */
public class TGroupableTableHeaderBorder implements Border {

	public Insets getBorderInsets(Component c) {
		return new Insets(3, 1, 3, 1);
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

		int red = TTable.columnColor.getRed() - 50;
		if (red < 0) {
			red = 0;
		}

		int green = TTable.columnColor.getGreen() - 50;
		if (green < 0) {
			green = 0;
		}

		int blue = TTable.columnColor.getBlue() - 50;
		if (blue < 0) {
			blue = 0;
		}

		g.setColor(new Color(red, green, blue));
		g.fillRect(x + width - 2, y, 1, height);
		g.drawLine(x, height - 2, width - 2, height - 2);

		red = TTable.columnColor.getRed() + 80;
		if (red > 255) {
			red = 255;
		}

		green = TTable.columnColor.getGreen() + 80;
		if (green > 255) {
			green = 255;
		}

		blue = TTable.columnColor.getBlue() + 80;
		if (blue > 255) {
			blue = 255;
		}

		g.setColor(new Color(red, green, blue));
		g.fillRect(x + width - 1, y, 1, height);
		g.drawLine(x, height - 1, width - 2, height - 1);

		// red = TTable.columnColor.getRed() - 20;
		// if (red < 0) {
		// red = 0;
		// }
		//
		// green = TTable.columnColor.getGreen() - 20;
		// if (green < 0) {
		// green = 0;
		// }
		//
		// blue = TTable.columnColor.getBlue() - 20;
		// if (blue < 0) {
		// blue = 0;
		// }
		//
		// g.setColor(new Color(red, green, blue));
		// g.drawLine(x, height - 1, width - 2, height - 1);

		// System.out.println("getClipBounds at " + g.getClipBounds());
		// System.out.println("draw line at " + new Point(x, height - 1) + new Point(width - 2, height - 1));

	}
}