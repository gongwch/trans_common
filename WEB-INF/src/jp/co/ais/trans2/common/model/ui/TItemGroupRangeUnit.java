package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗ�+�⏕�Ȗ�+����Ȗڔ͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TItemGroupRangeUnit extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** �͈̓t�B�[���h */
	public TItemGroupRange ctrlItemGroupRange;

	/** �ȖڌʑI�� */
	public TItemGroupOptionalSelector ctrlItemGroupOptionalSelector;

	/**
	 * 
	 *
	 */
	public TItemGroupRangeUnit() {

		initComponents();

		allocateComponents();

	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		ctrlItemGroupRange = new TItemGroupRange();
		ctrlItemGroupOptionalSelector = new TItemGroupOptionalSelector();
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	public void allocateComponents() {

		setLayout(null);

		setSize(ctrlItemGroupOptionalSelector.getWidth(), ctrlItemGroupRange.getHeight()
			+ ctrlItemGroupOptionalSelector.getHeight());

		ctrlItemGroupRange.setLocation(0, 0);
		add(ctrlItemGroupRange);

		ctrlItemGroupOptionalSelector.setLocation(0, ctrlItemGroupRange.getHeight());
		add(ctrlItemGroupOptionalSelector);

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlItemGroupRange.setTabControlNo(tabControlNo);
		ctrlItemGroupOptionalSelector.setTabControlNo(tabControlNo);
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(����) / false(�G���[)
	 */
	public boolean isSmallerFrom() {
		return ctrlItemGroupRange.isSmallerFrom();
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)
	 */
	public Item getFromEntity() {
		return ctrlItemGroupRange.getFromEntity();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)
	 */
	public Item getToEntity() {
		return ctrlItemGroupRange.getToEntity();
	}

	/**
	 * �I�����ꂽ�Ȗ�Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ȗ�Entity�ꗗ
	 */
	public List<Item> getEntities() {
		return ctrlItemGroupOptionalSelector.getEntities();
	}

	/**
	 * �J�n�ȖڃR�[�h�Ƀt�H�[�J�X�����킹��
	 */
	public void requestFromFocus() {
		ctrlItemGroupRange.ctrlItemGroupFrom.ctrlItemReference.requestTextFocus();
	}

	/**
	 * �l���N���A����
	 */
	public void clear() {
		ctrlItemGroupRange.clear();
		ctrlItemGroupOptionalSelector.clear();
	}
}
