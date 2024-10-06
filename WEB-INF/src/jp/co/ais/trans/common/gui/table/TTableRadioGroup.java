package jp.co.ais.trans.common.gui.table;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * TTable�pRadio�{�^���O���[�v
 */
// TODO �I���s�ɂȂ����ہA�����F�����]���Ȃ�
public class TTableRadioGroup extends TPanel {

	/** �{�^�����X�g */
	private List<TRadioButton> list;

	/** �I��Index�ԍ� */
	private int index;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �A���X�v���b�h
	 * @param rowIndex �X�v���b�h���̍s�ԍ�
	 */
	public TTableRadioGroup(TTable table, int rowIndex) {
		this(table, rowIndex, 2);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �A���X�v���b�h
	 * @param rowIndex �X�v���b�h���̍s�ԍ�
	 * @param number RadioButton�̐�
	 */
	public TTableRadioGroup(TTable table, int rowIndex, int number) {
		this(table, rowIndex, new String[number]);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �A���X�v���b�h
	 * @param rowIndex �X�v���b�h���̍s�ԍ�
	 * @param words �\������(RadioButton�̐�)
	 */
	public TTableRadioGroup(TTable table, int rowIndex, String... words) {
		list = new ArrayList<TRadioButton>(words.length);

		this.setLayout(new GridBagLayout());
		this.setFocusable(false);
		this.setOpaque(false);

		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i < words.length; i++) {

			TRadioButton radio = new TRadioButton(rowIndex);
			radio.setText(Util.avoidNull(words[i]));
			radio.setOpaque(false);
			radio.setFocusable(false);

			if (i == 0) {
				radio.setSelected(true);
			}

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = i;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 0);
			this.add(radio, gbc);

			group.add(radio);

			list.add(radio);
		}

		SelectedListener listener = new SelectedListener(table);

		for (TRadioButton radio : list) {
			radio.addItemListener(listener);
		}
	}

	/**
	 * �w�胉�W�I�{�^����Index�ԍ��ύX.
	 * 
	 * @param radio �Ώ�Radio�{�^��
	 */
	private void setSelectedIndex(TRadioButton radio) {

		this.index = list.indexOf(radio);
	}

	/**
	 * �w��Index�̃{�^����I����ԂɕύX.<br>
	 * �s���ԍ��w�莞��0�ɐݒ�.
	 * 
	 * @param index �ԍ�
	 */
	public void setSelectedIndex(int index) {
		this.index = (index >= list.size() || index < 0) ? 0 : index;

		list.get(this.index).setSelected(true);
	}

	/**
	 * ���݂̑I���{�^��INDEX���擾.
	 * 
	 * @return �I���{�^��INDEX
	 */
	public int getSelectedIndex() {
		return index;
	}

	/**
	 * ���̃��W�I�{�^���ɑI���ړ�����.
	 */
	public void nextSelected() {

		if (index + 1 >= list.size()) {
			index = 0;

		} else {
			index++;
		}

		list.get(index).setSelected(true);
	}

	/**
	 * �X�v���b�h�p���X�i�[
	 */
	private class SelectedListener implements ItemListener {

		/** �ΏۃX�v���b�h */
		private TTable table;

		/**
		 * �R���X�g���N�^.
		 * 
		 * @param table �ΏۃX�v���b�h
		 */
		public SelectedListener(TTable table) {
			this.table = table;
		}

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent e) {
			TRadioButton radio = (TRadioButton) e.getSource();
			int rowNo = table.isSort() ? table.getCurrentRow() : radio.getIndex();

			table.requestFocusInWindow();
			table.setCurrentCell(rowNo, 0);
			table.setRowSelection(rowNo, rowNo);

			setSelectedIndex(radio);
		}
	}
}
