package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;

/**
 * TButtonに、Icon画像を追加したButton.
 */
public class TImageButton extends TButton {

	/** true:削除タイプボタンは赤字 */
	protected static boolean useRedForDeleteButton = ClientConfig.isFlagOn("trans.use.del.button.red");

	/** Icon */
	protected IconType iconType = null;

	/**
	 * TImageButton.
	 */
	public TImageButton() {
		super();
	}

	/**
	 * TImageButton.
	 * 
	 * @param icon
	 */
	public TImageButton(IconType icon) {
		setIconType(icon);
	}

	/**
	 * @param icon
	 */
	public void setIconType(IconType icon) {
		this.iconType = icon;
		this.setIcon(getImageIcon(iconType));

		if (IconType.DELETE == icon && useRedForDeleteButton) {
			// 削除の場合、ボタン文字色は赤
			this.setForeground(Color.red);
		}
	}

	/**
	 * @param icon
	 * @return ImageIcon
	 */
	protected ImageIcon getImageIcon(IconType icon) {
		return ResourceUtil.getImage(this.getClass(), icon.getIconName());
	}

	/**
	 * @return IconType
	 */
	public IconType getIconType() {
		return this.iconType;
	}

}
