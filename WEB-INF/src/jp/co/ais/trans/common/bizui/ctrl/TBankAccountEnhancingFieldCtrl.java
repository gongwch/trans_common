package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ��s�����t�B�[���h�g���R���g���[��
 */
public class TBankAccountEnhancingFieldCtrl extends TBankAccountFieldCtrl {

	private TBankAccountEnhancingField field;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MP0030BankAccountMasterServlet";

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field
	 */
	public TBankAccountEnhancingFieldCtrl(TBankAccountEnhancingField field) {
		super(field);
		this.field = field;
	}

	/**
	 * �f�t�H���g�̎x�����������擾
	 */
	public void setDefaultReceivedAccount() {
		// �x�������}�X�^�f�[�^������
		Map<String, String> map = getDefaultReceivedAccount();

		field.setValue(Util.avoidNull(map.get("cbkCode")));
		field.setNoticeValue(Util.avoidNull(map.get("cbkName")));

	}

	/**
	 * �x�����s�A�����ԍ�������
	 * 
	 * @return map �x�����s�A�����ԍ�
	 */
	private Map<String, String> getDefaultReceivedAccount() {
		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "defaultReceivedAccount");
			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(field);
			}

			return getResult();
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(field, e, "E00009");
			return null;
		}
	}
}
