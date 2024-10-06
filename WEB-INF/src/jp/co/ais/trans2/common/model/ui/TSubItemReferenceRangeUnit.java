package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �⏕�Ȗڔ͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TSubItemReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** �͈̓t�B�[���h */
	public TSubItemReferenceRange ctrlSubItemReferenceRange;

	/** �⏕�ȖڌʑI�� */
	public TSubItemOptionalSelector ctrlSubItemOptionalSelector;

	/** */
	public TSubItemReferenceRangeUnit() {
		super(false);
	}

	/**
	 * @param border �{�[�_�[��\������ꍇtrue
	 */
	public TSubItemReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * @param border �{�[�_�[��\������ꍇtrue
	 * @param title �^�C�g���\�����ǂ���
	 */
	public TSubItemReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	@Override
	public void initComponents() {
		ctrlSubItemReferenceRange = new TSubItemReferenceRange();
		ctrlSubItemOptionalSelector = new TSubItemOptionalSelector();
	}

	/**
	 * 
	 */
	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlSubItemOptionalSelector;
	}

	/**
	 * 
	 */
	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlSubItemReferenceRange;
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�⏕�Ȗ�Entity��Ԃ�
	 * 
	 * @return �J�n�t�B�[���h�őI�����ꂽ�⏕�Ȗ�Entity
	 */
	public Item getEntityFrom() {
		return ctrlSubItemReferenceRange.getEntityFrom();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�⏕�Ȗ�Entity��Ԃ�
	 * 
	 * @return �I���t�B�[���h�őI�����ꂽ�⏕�Ȗ�Entity
	 */
	public Item getEntityTo() {
		return ctrlSubItemReferenceRange.getEntityTo();
	}

	@Override
	public String getBorderTitle() {
		return TLoginInfo.getCompany().getAccountConfig().getSubItemName() + TModelUIUtil.getWord("C00324"); // �I��
	}

}
