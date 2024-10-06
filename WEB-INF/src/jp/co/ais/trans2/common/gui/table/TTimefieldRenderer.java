package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * TZ�ҏW�p�Z�������_��
 */
public class TTimefieldRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	protected final TTimeField renderer;

	/** �X�v���b�h�̃t�H���g���g�� */
	protected boolean useTableFont = false;

	/** �N���� */
	protected static final String BASE_YMD = "2000/01/01 ";

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TTimefieldRenderer(TTimeField renderer, TTable table) {

		super(table);

		// �����_���p�R���|�[�l���g�̐ݒ�
		this.renderer = renderer;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		TTimeField txt = new TTimeField();
		txt.setTableCellEditor(true);

		txt.setBorder(BorderFactory.createEmptyBorder());

		if (this.isUseTableFont()) {
			txt.setFont(table.getFont());
		}

		// �l�̐ݒ�
		setValue(txt, value);

		// �F�̐ݒ�
		txt.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}

		// �{�[�_�[�ݒ�
		setBorder(txt, isSelected, hasFocus);

		return txt;
	}

	/**
	 * �l�̐ݒ�
	 * 
	 * @param txt
	 * @param value
	 */
	protected void setValue(TTimeField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();
		}
		try {
			String hm = Util.avoidNull(value);
			// HH:mm �`���ȊO�̓G���[�Ƃ���B
			if (hm.length() != 5) {
				return;
			}

			Date date = null;
			date = DateUtil.toYMDHMDate(BASE_YMD + hm);
			txt.setText(DateUtil.toHMString(date));
		} catch (ParseException e) {
			return;
		}
	}

	/**
	 * �X�v���b�h�̃t�H���g���g���̎擾
	 * 
	 * @return useTableFont �X�v���b�h�̃t�H���g���g��
	 */
	public boolean isUseTableFont() {
		return useTableFont;
	}

	/**
	 * �X�v���b�h�̃t�H���g���g���̐ݒ�
	 * 
	 * @param useTableFont �X�v���b�h�̃t�H���g���g��
	 */
	public void setUseTableFont(boolean useTableFont) {
		this.useTableFont = useTableFont;
	}

}