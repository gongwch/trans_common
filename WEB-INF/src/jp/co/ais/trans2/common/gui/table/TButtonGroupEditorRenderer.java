package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * ���W�I�{�^���O���[�v�p�Z���G�f�B�^�E�����_��
 */
public class TButtonGroupEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	/** �G�f�B�^�p�R���|�[�l���g */
	protected final RadioButtonPanel editor;

	/** �����_���p�R���|�[�l���g */
	protected final RadioButtonPanel renderer;

	/** �{�^���O���[�v ���� */
	protected final String[] items;

	/**  */
	protected TTable tbl;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param items �{�^���O���[�v����
	 * @param tbl
	 */
	public TButtonGroupEditorRenderer(String[] items, TTable tbl) {
		this(items, false);
		this.tbl = tbl;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param items �{�^���O���[�v����
	 * @param isViewTitle �^�C�g����\�����邩�ǂ���<br>
	 *            �{�^�������\���̏ꍇ��False
	 */
	public TButtonGroupEditorRenderer(String[] items, boolean isViewTitle) {

		super();
		this.items = items;

		// �G�f�B�^�p�R���|�[�l���g�̃C���X�^���X��
		this.editor = new RadioButtonPanel(isViewTitle, items);

		// �����_���p�R���|�[�l���g�̃C���X�^���X��
		this.renderer = new RadioButtonPanel(isViewTitle, items);
	}

	/**
	 * �{�^���������̏���
	 * 
	 * @param comp �G�f�B�^ OR �����_��
	 * @param value �I��l
	 */
	protected void setSelectedButton(RadioButtonPanel comp, Object value) {

		// �I��l�ƈ�v���郉�W�I�{�^�����Z���N�g��Ԃɂ���
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(value)) {
				comp.buttons[i].setSelected(true);
			}
		}
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		setSelectedButton(renderer, value);

		// �F�̐ݒ�
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		return renderer;
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		setSelectedButton(editor, value);

		// �w�i�F�̐ݒ�
		editor.setBackground(tbl.getBackgroundColor(row, isSelected, isSelected));
		editor.setForeground(tbl.getSelectedFontColor());

		return editor;
	}

	/**
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		return editor.group.getSelection().getActionCommand();
	}

	/**
	 * �G�f�B�^�E�����_���p�R���|�[�l���g
	 */
	class RadioButtonPanel extends JPanel {

		/** �{�^���O���[�v */
		public final ButtonGroup group = new ButtonGroup();

		/** �{�^�����X�g */
		public final JRadioButton[] buttons;

		/**
		 * �R���X�g���N�^
		 * 
		 * @param isViewTitle �^�C�g����\�����邩�ǂ���<br>
		 *            �{�^�������\���̏ꍇ��False
		 * @param items �\������(RadioButton�̐�)
		 */
		public RadioButtonPanel(boolean isViewTitle, String... items) {

			this.setLayout(new GridBagLayout());
			this.setFocusable(false);

			// �\�����ڂ̐������A���W�I�{�^�����C���X�^���X������
			buttons = new JRadioButton[items.length];
			for (int i = 0; i < items.length; i++) {

				JRadioButton radio = new JRadioButton();

				// True�̏ꍇ�̓{�^���^�C�g����ݒ�
				if (isViewTitle) {
					radio.setText(Util.avoidNull(items[i]));
				}

				radio.setOpaque(false);
				radio.setFocusable(false);

				if (i == 0) {
					radio.setSelected(true);
				}

				radio.setActionCommand(items[i]);

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = i;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 5, 0, 5);
				this.add(radio, gbc);
				buttons[i] = radio;
				group.add(radio);

			}

		}
	}
}