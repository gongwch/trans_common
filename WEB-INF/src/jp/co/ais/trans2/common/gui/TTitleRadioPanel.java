package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �^�C�g���t�����W�I�p�l��
 */
public class TTitleRadioPanel extends TTitlePanel {

	/** ���W�I�{�^�� */
	public List<TRadioButton> radios = new LinkedList<TRadioButton>();

	/** �{�^���O���[�v */
	protected ButtonGroup btngrpClass = new ButtonGroup();

	/** �R���e���c */
	protected GridBagConstraints gc = new GridBagConstraints();

	/**
	 * ���W�I�{�^���ǉ�
	 * 
	 * @param word �\������
	 */
	public void addRadioButton(String word) {
		addRadioButton(word, 50);
	}

	public void initComponent() {
		super.initComponent();
		mainPanel.setLayout(new GridBagLayout());
	}

	/**
	 * ���W�I�{�^���ǉ�
	 * 
	 * @param word �\������
	 * @param width ���T�C�Y
	 */
	public void addRadioButton(String word, int width) {

		TRadioButton rdo = new TRadioButton(word);
		TGuiUtil.setComponentSize(rdo, new Dimension(width, 16));
		rdo.setHorizontalAlignment(SwingConstants.CENTER);
		rdo.setMargin(new Insets(0, 0, 0, 0));

		if (radios.isEmpty()) {
			rdo.setSelected(true);
		}

		btngrpClass.add(rdo);

		gc.gridx = radios.size();
		mainPanel.add(rdo, gc);

		radios.add(rdo);

		int twidth = 0;
		for (TRadioButton inrdo : radios) {
			twidth += inrdo.getWidth();
		}

	}

	/**
	 * �y��p�l���̕��ύX
	 * 
	 * @param width ���T�C�Y
	 */
	public void setWidth(int width) {
		TGuiUtil.setComponentWidth(this, width);
	}

	/**
	 * �w��Index�{�^���̑I����Ԃ�ON�ɂ���.
	 * 
	 * @param index �w��Index�{�^��
	 */
	public void setSelectON(int index) {
		for (int i = 0; i < radios.size(); i++) {
			radios.get(i).setSelected(i == index);
		}
	}

	/**
	 * �w��Index�{�^���̏�Ԃ��擾����
	 * 
	 * @param index �w��Index
	 * @return true:�I�����
	 */
	public boolean isSelected(int index) {
		return radios.get(index).isSelected();
	}

	/**
	 * 0�Ԗڃ��W�I��ItemListener�Z�b�g
	 * 
	 * @param listener ���X�i�[
	 */
	public void addItemListener(ItemListener listener) {
		radios.get(0).addItemListener(listener);
	}

	/**
	 * ItemListener�Z�b�g
	 * 
	 * @param index �w��Index
	 * @param listener ���X�i�[
	 */
	public void addItemListener(int index, ItemListener listener) {
		radios.get(index).addItemListener(listener);
	}

	/**
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		for (int i = 0; i < radios.size(); i++) {
			radios.get(i).setEnabled(enabled);
		}
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TRadioButton rdo : radios) {
			rdo.setTabControlNo(tabControlNo);
		}
	}

}
