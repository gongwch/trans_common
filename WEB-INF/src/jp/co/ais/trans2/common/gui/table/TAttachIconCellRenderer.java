package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �X�v���b�h�p�Y�t�\���}�[�NCellRenderer
 */
public class TAttachIconCellRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	protected TAttachIcon icon;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TAttachIconCellRenderer(TAttachIcon renderer, TTable table) {
		super(table);
		this.icon = renderer;
		this.icon.setHorizontalAlignment(SwingConstants.CENTER);
		this.icon.setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		// �F�̐ݒ�
		this.icon.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (isSelected) {
			this.icon.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}

		// �{�[�_�[�ݒ�
		setBorder(icon, isSelected, hasFocus);

		if (value instanceof Boolean) {
			if (!(Boolean) value) {
				TAttachIcon noIcon = icon;
				noIcon.setIcon(null);
				return noIcon;
			} else {
				icon.setIcon(new TImageButton(IconType.ATTACHMENT_COMPLETE).getIcon());
			}
		}
		return icon;
	}
}