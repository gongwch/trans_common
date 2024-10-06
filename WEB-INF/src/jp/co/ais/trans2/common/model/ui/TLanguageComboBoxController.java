package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.language.*;

/**
 * ����R�[�h�R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TLanguageComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TLanguageComboBox comboBox;

	/** �������� */
	protected LanguageSearchCondition condition;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TLanguageComboBoxController(TLanguageComboBox comboBox) {

		this.comboBox = comboBox;

		init();

	}

	/**
	 * ������
	 */
	private void init() {

		// ��������������
		initSearchCondition();

		// �R���{�{�b�N�X����
		getList(condition);

	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * ���������̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected LanguageSearchCondition createSearchCondition() {
		return new LanguageSearchCondition();
	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 * 
	 * @param condition
	 */
	@SuppressWarnings("unchecked")
	private void getList(LanguageSearchCondition condition) {

		try {

			List<Language> list =
				(List<Language>) request(getModelClass(), "getLanguage", condition);

			for (Language lang : list) {
				comboBox.getComboBox().addTextValueItem(lang.getCode(), lang.getName());
			}

		} catch (TException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @return Servlet��Class��Ԃ�
	 */
	protected Class getModelClass() {
		return LanguageManager.class;
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

}
