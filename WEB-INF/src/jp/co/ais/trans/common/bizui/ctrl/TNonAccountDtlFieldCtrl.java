package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���v���׃t�B�[���h�R���g���[��
 */
public class TNonAccountDtlFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "InformationServlet";

	/** �p�l�� */
	protected TNonAccountDtlField panel;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panel �p�l��
	 */
	public TNonAccountDtlFieldCtrl(TNonAccountDtlField panel) {
		this.panel = panel;
	}

	/**
	 * ��Џ��擾
	 * 
	 * @return CMP_MST�G���e�B�e�B
	 */
	public CMP_MST getCmpData() {

		CMP_MST cmp = new CMP_MST();

		try {
			// �t���O
			addSendValues("FLAG", "CmpInfo");
			// ��ЃR�[�h
			if (Util.isNullOrEmpty(panel.getCompanyCode())) {
				panel.setCompanyCode(super.getLoginUserCompanyCode());
			}

			addSendValues("KAI_CODE", panel.getCompanyCode());

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
			}

			// �f�[�^�擾
			Map<String, String> map = getResult();
			cmp.setCMP_HM_KBN_1(Util.avoidNullAsInt(map.get("CMP_HM_KBN_1")));
			cmp.setCMP_HM_KBN_2(Util.avoidNullAsInt(map.get("CMP_HM_KBN_2")));
			cmp.setCMP_HM_KBN_3(Util.avoidNullAsInt(map.get("CMP_HM_KBN_3")));

			cmp.setCMP_HM_NAME_1(Util.avoidNull(map.get("CMP_HM_NAME_1")));
			cmp.setCMP_HM_NAME_2(Util.avoidNull(map.get("CMP_HM_NAME_2")));
			cmp.setCMP_HM_NAME_3(Util.avoidNull(map.get("CMP_HM_NAME_3")));

		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
		}

		return cmp;
	}

	/**
	 * ���t�̃Z�b�g
	 * 
	 * @param comp �J�����_�[
	 * @param value �l
	 */
	public void setToDateComp(TLabelPopupCalendar comp, String value) {
		try {
			if (comp.getCalendarType() == TPopupCalendar.TYPE_YMDHM) {
				comp.setValue(DateUtil.toYMDHMDate(Util.avoidNull(value)));
			} else {
				comp.setValue(DateUtil.toYMDDate(Util.avoidNull(value)));
			}
		} catch (ParseException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
		}
	}
}
