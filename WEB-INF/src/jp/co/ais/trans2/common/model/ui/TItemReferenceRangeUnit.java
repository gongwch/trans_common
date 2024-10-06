package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗڔ͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TItemReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** �͈̓t�B�[���h */
	public TItemReferenceRange ctrlItemReferenceRange;

	/** �ȖڌʑI�� */
	public TItemOptionalSelector ctrlItemOptionalSelector;

	/** */
	public TItemReferenceRangeUnit() {
		super(false);
	}

	/**
	 * @param border �{�[�_�[��\������ꍇtrue
	 */
	public TItemReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * @param border �{�[�_�[��\������ꍇtrue
	 * @param title �^�C�g���\�����ǂ���
	 */
	public TItemReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	@Override
	public void initComponents() {
		ctrlItemReferenceRange = new TItemReferenceRange();
		ctrlItemOptionalSelector = new TItemOptionalSelector();
	}

	/**
	 * 
	 */
	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlItemOptionalSelector;
	}

	/**
	 * 
	 */
	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlItemReferenceRange;
	}

	/**
	 * �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �J�n�t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public Item getEntityFrom() {
		return ctrlItemReferenceRange.getEntityFrom();
	}

	/**
	 * �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �I���t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public Item getEntityTo() {
		return ctrlItemReferenceRange.getEntityTo();
	}

	@Override
	public String getBorderTitle() {
		// �I��
		return TLoginInfo.getCompany().getAccountConfig().getItemName() + TModelUIUtil.getWord("C00324");
	}

}
