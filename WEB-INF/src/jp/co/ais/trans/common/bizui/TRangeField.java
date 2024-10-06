package jp.co.ais.trans.common.bizui;

import java.awt.*;
import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;

/**
 * �͈͎w��t�B�[���h(��ʌ��{)
 */
public abstract class TRangeField extends TPanel {

	/** �p�l�� */
	protected TPanel pnlRangeSpecification;

	/** �J�n���x�� */
	protected TLabel lblBeginItem;

	/** �I�����x�� */
	protected TLabel lblEndItem;

	/** �J�n�t�B�[���h */
	protected TButtonField ctrlBeginItem;

	/** �I���t�B�[���h */
	protected TButtonField ctrlEndItem;

	/** �p�l�������̒P��ID */
	protected String panelMsgId = "C03186";

	/** �J�n���x�������P��ID */
	protected String beginLblMsgId = "C01013";

	/** �I�����x�������P��ID */
	protected String endLblMsgId = "C00260";

	/**
	 * ��ʍ\�z
	 */
	protected void init() {

		GridBagConstraints gridBagConstraints;

		pnlRangeSpecification = new TPanel();
		lblBeginItem = new TLabel();
		lblEndItem = new TLabel();
		ctrlBeginItem = createBeginField();
		ctrlEndItem = createEndField();

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlRangeSpecification.setLangMessageID(panelMsgId);
		pnlRangeSpecification.setPreferredSize(new Dimension(480, 85));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlRangeSpecification.add(lblBeginItem, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 10, 5);
		pnlRangeSpecification.add(lblEndItem, gridBagConstraints);

		lblBeginItem.setLangMessageID(beginLblMsgId);
		ctrlBeginItem.setButtonSize(75);
		ctrlBeginItem.setFieldSize(95);
		ctrlBeginItem.setNoticeSize(220);
		ctrlBeginItem.setMaxLength(10);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginItem, gridBagConstraints);

		lblEndItem.setLangMessageID(endLblMsgId);
		ctrlEndItem.setButtonSize(75);
		ctrlEndItem.setFieldSize(95);
		ctrlEndItem.setNoticeSize(220);
		ctrlEndItem.setMaxLength(10);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
		gridBagConstraints.insets = new Insets(0, 0, 10, 10);
		pnlRangeSpecification.add(ctrlEndItem, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		add(pnlRangeSpecification, gridBagConstraints);
	}

	/**
	 * �J�n�t�B�[���h�𐶐�����(Orverride�p)
	 * 
	 * @return �J�n�t�B�[���h
	 */
	protected abstract TButtonField createBeginField();

	/**
	 * �I���t�B�[���h�𐶐�����
	 * 
	 * @return �I���t�B�[���h
	 */
	protected abstract TButtonField createEndField();

	/**
	 * �p�l���̎擾
	 * 
	 * @return TPanel
	 */
	public TPanel getBasePanel() {
		return pnlRangeSpecification;
	}

	/**
	 * �J�n�t�B�[���h�̎擾
	 * 
	 * @return TItemField
	 */
	public TButtonField getBeginField() {
		return ctrlBeginItem;
	}

	/**
	 * �I���t�B�[���h�̎擾
	 * 
	 * @return TItemField
	 */
	public TButtonField getEndField() {
		return ctrlEndItem;
	}

	/**
	 * �J�n���x���̎擾
	 * 
	 * @return TLabel
	 */
	public TLabel getBeginLabel() {
		return lblBeginItem;
	}

	/**
	 * �I�����x���̎擾
	 * 
	 * @return TLabel
	 */
	public TLabel getEndLabel() {
		return lblEndItem;
	}

	/**
	 * �^�u�ړ����ԍ���͈͎w��R���|�[�l���g�S�̂ɐݒ肷��.
	 * 
	 * @param no �^�u��
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		ctrlBeginItem.setTabControlNo(no);
		ctrlEndItem.setTabControlNo(no);
	}

	/**
	 * �J�n�R�[�h���擾
	 * 
	 * @return �J�n�R�[�h
	 */
	public String getBeginCode() {
		return ctrlBeginItem.getField().getText().trim();
	}

	/**
	 * �I���R�[�h���擾
	 * 
	 * @return �I���R�[�h
	 */
	public String getEndCode() {
		return ctrlEndItem.getField().getText().trim();
	}

	/**
	 * �P��擾
	 * 
	 * @param wordId �P��ID
	 * @return �P��
	 */
	public String getWord(String wordId) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		return MessageUtil.getWord(lang, wordId);
	}

}
