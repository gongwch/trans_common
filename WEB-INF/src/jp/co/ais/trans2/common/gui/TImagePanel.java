package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ”wŒi}‚ ‚éƒpƒlƒ‹
 */
public class TImagePanel extends TPanel {

	/** imageIcon */
	protected ImageIcon imageIcon = null;

	/**
	 * imageIcon‚ğæ“¾‚·‚é
	 * 
	 * @return imageIcon
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	/**
	 * imageIcon‚ğİ’è‚·‚é
	 * 
	 * @param imageIcon
	 */
	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), null);

	}
}
