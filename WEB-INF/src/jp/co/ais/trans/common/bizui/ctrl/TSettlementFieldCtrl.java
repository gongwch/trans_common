package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���Z�t�B�[���h�R���g���[��
 */
public class TSettlementFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "InformationServlet";

	/** �p�l�� */
	private TSettlementField panel;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panel �p�l��
	 */
	public TSettlementFieldCtrl(TSettlementField panel) {
		this.panel = panel;
	}

	/**
	 * �u���Z�d��v�`�F�b�N���̏���
	 * 
	 * @return false:�`�F�b�N�s��
	 */
	public boolean chkClosingEntryMouseClicked() {
		try {
			// �`�F�b�N��
			if (panel.isSelected()) {
				// �`�[���t�����͂���Ă��Ȃ��ꍇ

				if ("".equals(DateUtil.toYMDString(panel.getSlipDate()))) {
					super.showMessage(panel, "W00034", "C00599");
					return false;
				}

				// ���Z�i�K�t���O
				addSendValues("FLAG", "ACCOUNT_STAGE");
				// ��ЃR�[�h
				addSendValues("KAI_CODE", super.getLoginUserCompanyCode());
				// �`�[���t
				String strSlipDate = DateUtil.toYMDString(DateUtil.toYMDDate(panel.getSlipDate()));
				addSendValues("SLIP_DATE", strSlipDate);

				// �T�[�u���b�g�̐ڑ���
				if (!request(TARGET_SERVLET)) {
					errorHandler(panel);
				}

				// �f�[�^�擾
				Map<String, String> map = getResult();
				int strAccountStage = Util.avoidNullAsInt(map.get("KSN_KBN"));
				int strMaxAccountStage = Util.avoidNullAsInt(map.get("KSD_KBN"));

				if (strMaxAccountStage < strAccountStage) {
					showMessage(panel, "W00139");
				} else {
					// �`�[���t�̌��x�ɂ����錈�Z�i�K���擾���w����ʂɕ\������B
					panel.setValue(strAccountStage);
				}
			} else {// ���Z�i�K��\��
				panel.setValue(null);
			}

		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
		}

		return true;
	}
}
