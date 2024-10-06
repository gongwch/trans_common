package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ���W�I�{�^���p�l��
 * 
 * @param <T> Enum
 */
public class TEnumRadioPanel<T extends TEnumRadio> extends TPanel {

	/** ���W�I�{�^�� */
	public Map<T, TRadioButton> radios = new LinkedHashMap<T, TRadioButton>();

	/** �{�^���O���[�v */
	protected ButtonGroup btngrpClass = new ButtonGroup();

	/** �R���e���c */
	protected GridBagConstraints gc = new GridBagConstraints();

	/** �`����� */
	public int alignment = SwingConstants.HORIZONTAL;

	/** ���]�� */
	public int leftMargin = 0;

	/** ���W�I�{�^���̍���(�f�t�H���g��16�ŁA16���s����) */
	public int height = 0;

	/**
	 * �R���X�g���N�^(��)
	 */
	public TEnumRadioPanel() {
		this(SwingConstants.HORIZONTAL);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param alignment ��:SwingConstants.HORIZONTAL �c:SwingConstants.VERTICAL
	 */
	public TEnumRadioPanel(int alignment) {
		this(alignment, 0);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param alignment ��:SwingConstants.HORIZONTAL �c:SwingConstants.VERTICAL
	 * @param leftMargin ���]��
	 */
	public TEnumRadioPanel(int alignment, int leftMargin) {
		this(alignment, leftMargin, 0);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param alignment ��:SwingConstants.HORIZONTAL �c:SwingConstants.VERTICAL
	 * @param leftMargin ���]��
	 * @param height ���W�I�{�^���̍���(�f�t�H���g��16�ŁA16���s����)
	 */
	public TEnumRadioPanel(int alignment, int leftMargin, int height) {
		TGuiUtil.setComponentSize(this, new Dimension(0, 45));

		this.alignment = alignment;
		this.leftMargin = leftMargin;
		this.height = height;

		this.setLayout(new GridBagLayout());

		gc.insets = new Insets(0, 0, 0, 0);
		if (alignment == SwingConstants.HORIZONTAL) {
			// ��
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
		} else {
			// �c
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.NORTHWEST;
		}

	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param title �^�C�g��
	 */
	public TEnumRadioPanel(String title) {
		this();

		// �^�C�g��
		this.setLangMessageID(title);
	}

	/**
	 * ���W�I�{�^���ǉ�
	 * 
	 * @param e Enum
	 * @param width ���T�C�Y
	 */
	public void addRadioButton(T e, int width) {

		TRadioButton rdo = new TRadioButton(e.getName());
		TGuiUtil.setComponentSize(rdo, new Dimension(width, 16 + height));
		rdo.setHorizontalAlignment(SwingConstants.LEFT);
		rdo.setVerticalAlignment(SwingConstants.CENTER);
		rdo.setMargin(new Insets(0, leftMargin, 0, 0));

		if (radios.isEmpty()) {
			rdo.setSelected(true);
		}

		btngrpClass.add(rdo);
		radios.put(e, rdo);

		if (alignment == SwingConstants.HORIZONTAL) {
			// ��
			gc.gridx = radios.size();
			this.add(rdo, gc);

			int twidth = 0;
			for (TRadioButton inrdo : radios.values()) {
				twidth += inrdo.getWidth();
			}

			setWidth(leftMargin + twidth + 10);
		} else {
			// �c
			gc.gridy = radios.size();
			this.add(rdo, gc);

			int twidth = 0;
			int theight = 0;
			for (TRadioButton inrdo : radios.values()) {
				twidth = Math.max(inrdo.getWidth(), twidth);
				theight += inrdo.getHeight();
			}

			setWidth(leftMargin + twidth + 10);
			setHeight(theight + 30);
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
	 * �y��p�l���̍����ύX
	 * 
	 * @param height �����T�C�Y
	 */
	public void setHeight(int height) {
		TGuiUtil.setComponentHeight(this, height);
	}

	/**
	 * �����񂹂̐ݒ�
	 * 
	 * @param alignment ��
	 */
	public void setHorizontalAlignment(int alignment) {
		for (TRadioButton rdo : radios.values()) {
			rdo.setHorizontalAlignment(alignment);
		}
	}

	/**
	 * �\�������ʎw��
	 * 
	 * @param e Enum
	 * @param word �\������
	 */
	public void setLangMessageID(T e, String word) {
		radios.get(e).setLangMessageID(word);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TRadioButton rdo : radios.values()) {
			rdo.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * �w��Index�{�^���̑I����Ԃ�ON�ɂ���.
	 * 
	 * @param e �w��Enum
	 */
	public void setSelectON(T e) {
		for (Entry<T, TRadioButton> entry : radios.entrySet()) {
			entry.getValue().setSelected(entry.getKey().equals(e));
		}
	}

	/**
	 * �I��Enum�̖߂�
	 * 
	 * @return �I��Enum(null�\)
	 */
	public T getSelected() {
		for (Entry<T, TRadioButton> entry : radios.entrySet()) {
			if (entry.getValue().isSelected()) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * �w��Enum�̃��W�I�{�^���̎擾
	 * 
	 * @param e Enum
	 * @return �w��Enum�̃��W�I�{�^��
	 */
	public TRadioButton getRadioButton(T e) {
		return radios.get(e);
	}

	/**
	 * ItemListener�Z�b�g<br>
	 * �S���W�I�{�^������
	 * 
	 * @param listener ���X�i�[
	 */
	public void addItemListener(ItemListener listener) {
		for (TRadioButton rdo : radios.values()) {
			rdo.addItemListener(listener);
		}
	}

	/**
	 * ItemListener�Z�b�g
	 * 
	 * @param e �w��Enum
	 * @param listener ���X�i�[
	 */
	public void addItemListener(T e, ItemListener listener) {
		radios.get(e).addItemListener(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (TRadioButton radio : radios.values()) {
			radio.setEnabled(enabled);
		}
	}
}
