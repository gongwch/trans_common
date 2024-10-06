package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �`�F�b�N�{�b�N�X�p�l��
 * 
 * @param <T> Enum
 */
public class TEnumCheckBoxPanel<T extends TEnumRadio> extends TPanel {

	/** �`�F�b�N�{�b�N�X */
	public Map<T, TCheckBox> list = new LinkedHashMap<T, TCheckBox>();

	/** �R���e���c */
	protected GridBagConstraints gc = new GridBagConstraints();

	/** �`����� */
	public int alignment = SwingConstants.HORIZONTAL;

	/** ���]�� */
	public int leftMargin = 0;

	/** �`�F�b�N�{�b�N�X�̍���(�f�t�H���g��16�ŁA16���s����) */
	public int height = 0;

	/**
	 * �R���X�g���N�^(��)
	 */
	public TEnumCheckBoxPanel() {
		this(SwingConstants.HORIZONTAL);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param alignment ��:SwingConstants.HORIZONTAL �c:SwingConstants.VERTICAL
	 */
	public TEnumCheckBoxPanel(int alignment) {
		this(alignment, 0);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param alignment ��:SwingConstants.HORIZONTAL �c:SwingConstants.VERTICAL
	 * @param leftMargin ���]��
	 */
	public TEnumCheckBoxPanel(int alignment, int leftMargin) {
		this(alignment, leftMargin, 0);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param alignment ��:SwingConstants.HORIZONTAL �c:SwingConstants.VERTICAL
	 * @param leftMargin ���]��
	 * @param height �`�F�b�N�{�b�N�X�̍���(�f�t�H���g��16�ŁA16���s����)
	 */
	public TEnumCheckBoxPanel(int alignment, int leftMargin, int height) {
		TGuiUtil.setComponentSize(this, new Dimension(0, 20));

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
	public TEnumCheckBoxPanel(String title) {
		this();

		// �^�C�g��
		this.setLangMessageID(title);
	}

	/**
	 * �`�F�b�N�{�b�N�X�ǉ�
	 * 
	 * @param e Enum
	 * @param width ���T�C�Y
	 * @return TCheckBox
	 */
	public TCheckBox addCheckBox(T e, int width) {
		return addCheckBox(e, e.getName(), width);
	}

	/**
	 * �`�F�b�N�{�b�N�X�ǉ�
	 * 
	 * @param e Enum
	 * @param name ���̎w��
	 * @param width ���T�C�Y
	 * @return TCheckBox
	 */
	public TCheckBox addCheckBox(T e, String name, int width) {

		TCheckBox chk = new TCheckBox(name);
		TGuiUtil.setComponentSize(chk, new Dimension(width, 16 + height));
		chk.setHorizontalAlignment(SwingConstants.LEFT);
		chk.setVerticalAlignment(SwingConstants.CENTER);
		chk.setMargin(new Insets(0, leftMargin, 0, 0));

		if (list.isEmpty()) {
			chk.setSelected(true);
		}

		list.put(e, chk);

		if (alignment == SwingConstants.HORIZONTAL) {
			// ��
			gc.gridx = list.size();
			this.add(chk, gc);

			int twidth = 0;
			for (TCheckBox inrdo : list.values()) {
				twidth += inrdo.getWidth();
			}

			setWidth(leftMargin + twidth + 10);

		} else {
			// �c
			gc.gridy = list.size();
			this.add(chk, gc);

			int twidth = 0;
			int theight = 0;
			for (TCheckBox inrdo : list.values()) {
				twidth = Math.max(inrdo.getWidth(), twidth);
				theight += inrdo.getHeight();
			}

			setWidth(leftMargin + twidth + 10);
			setHeight(theight + 30);
		}

		return chk;
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
		for (TCheckBox rdo : list.values()) {
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
		list.get(e).setLangMessageID(word);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TCheckBox chk : list.values()) {
			chk.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * �w��Index�{�^���̑I����Ԃ�ON�ɂ���.
	 * 
	 * @param arr �w��Enum
	 */
	public void setCheckON(T... arr) {
		for (Entry<T, TCheckBox> entry : list.entrySet()) {
			for (T e : arr) {
				if (entry.getKey().equals(e)) {
					entry.getValue().setSelected(true);
					break;
				}
			}
		}
	}

	/**
	 * �I��Enum�̖߂�
	 * 
	 * @return �I��Enum(null�\)
	 */
	public List<T> getChecked() {
		List<T> resultList = new ArrayList<T>();
		for (Entry<T, TCheckBox> entry : list.entrySet()) {
			if (entry.getValue().isSelected()) {
				resultList.add(entry.getKey());
			}
		}

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
	}

	/**
	 * �Ώ�Enum���I�𔻒�
	 * 
	 * @param e
	 * @return true:�Ώ�Enum���I��
	 */
	public boolean isChecked(T e) {
		for (Entry<T, TCheckBox> entry : list.entrySet()) {
			if (entry.getKey().equals(e) && entry.getValue().isSelected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �w��Enum�̃`�F�b�N�{�b�N�X�̎擾
	 * 
	 * @param e Enum
	 * @return �w��Enum�̃`�F�b�N�{�b�N�X
	 */
	public TCheckBox getCheckBox(T e) {
		return list.get(e);
	}

	/**
	 * ItemListener�Z�b�g<br>
	 * �S�`�F�b�N�{�b�N�X����
	 * 
	 * @param listener ���X�i�[
	 */
	public void addItemListener(ItemListener listener) {
		for (TCheckBox rdo : list.values()) {
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
		list.get(e).addItemListener(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (TCheckBox chk : list.values()) {
			chk.setEnabled(enabled);
		}
	}
}
