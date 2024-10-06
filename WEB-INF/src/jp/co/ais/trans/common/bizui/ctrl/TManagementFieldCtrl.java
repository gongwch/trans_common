package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �Ǘ��}�X�^�����t�B�[���h�R���g���[��
 */
public class TManagementFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "InformationServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �t�B�[���h */
	protected TManagementField panel;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField �t�B�[���h
	 */
	public TManagementFieldCtrl(TManagementField itemField) {
		this.panel = itemField;
	}

	/**
	 * �Ǘ��}�X�^��������
	 */
	public void managementMouseClicked() {
		try {
			// �����_�C�A���O���Ăяo��
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(panel, REFDialogMasterCtrl.BMN_MST
				+ panel.getManagementType());

			// ��ЃR�[�h
			dialog.setKaiCode(Util.avoidNull(panel.getCompanyCode()));

			// �J�n�Ǘ��R�[�h
			dialog.setBeginCode(Util.avoidNull(panel.getBeginCode()));

			// �I���Ǘ��R�[�h
			dialog.setEndCode(Util.avoidNull(panel.getEndCode()));

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(panel.getField().getValue())) {
				dialog.setCode(String.valueOf(panel.getField().getValue()));
				dialog.searchData(false);
			}

			dialog.show();

			if (dialog.isSettle()) {

				String[] info = dialog.getCurrencyInfo();
				// �Ǘ��R�[�h
				panel.setValue(info[0]);
				// �Ǘ�����
				panel.setNoticeValue(info[1]);
			}

			panel.getField().requestFocus();
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * ���X�g�t�H�[�J�X����<BR>
	 * 
	 * @return true:���폈��
	 */
	public boolean managementLostFocus() {
		try {
			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			String strKnrCode = panel.getValue();
			// ��ЃR�[�h�擾
			TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();
			// �Ǘ����̂̎擾
			String[] name = compInfo.getManageDivNames();

			if (!Util.isNullOrEmpty(strKnrCode)) {
				// �Ǘ�1 �` �Ǘ�6�}�X�^�f�[�^������
				Map<String, String> map = findKnrMst(strKnrCode, panel.getManagementType());
				if (!"0".equals(map.get("existFlag"))) {

					// �擾�����f�[�^�̊J�n���t
					Date strDate = DateUtil.toYMDDate(map.get("STR_DATE"));
					// �擾�����f�[�^�̏I�����t
					Date endDate = DateUtil.toYMDDate(map.get("END_DATE"));
					// ��ƂȂ���t
					Date termDate = DateUtil.toYMDDate(Util.avoidNull(panel.getTermBasisDate()));
					if (!Util.isNullOrEmpty(termDate)) {
						if (termDate.after(endDate) || strDate.after(termDate)) {
							if (!showConfirmMessage(panel, "Q00046", panel.getButton().getText() + getWord("C00174"))) {
								return false;
							}
						}
					}
					// �Ǘ��R�[�h�̗��̂��擾����
					panel.setNoticeValue(Util.avoidNull(map.get("KNR_NAME_S_".concat(Util.avoidNull(panel
						.getManagementType())))));

				} else {
					if (panel.isCheckMode()) {
						// �Ǘ��R�[�h�����݂��܂���B
						showMessage(panel, "W00081", (name[panel.getManagementType() - 1] + getWord("C00174")));
					}
					// �Ǘ����̃u�����N�ŃZ�b�g����B
					panel.setNoticeValue("");
					panel.clearOldText();
					panel.requestTextFocus();
					return !panel.isCheckMode();

				}

			} else {
				// �Ǘ����̃u�����N�ŃZ�b�g����B
				panel.setNoticeValue("");
			}
			return true;
		} catch (Exception ex) {
			errorHandler(panel, ex);
			return false;
		}
	}

	/**
	 * �Ǘ�1 �` �Ǘ�6�}�X�^�f�[�^������
	 * 
	 * @param knrCode �Ǘ�����
	 * @param type �Ǘ��\�t���O
	 * @return �Ǘ�1 �` �Ǘ�6�}�X�^�f�[�^
	 */
	protected Map<String, String> findKnrMst(String knrCode, int type) {
		try {
			// �`�[���t
			String strSlipDate = Util.avoidNull(panel.getTermBasisDate());
			// ���M����p�����[�^��ݒ�
			addSendValues("FLAG", "Knrmst");
			// �Ǘ��R�[�h�^�C�v
			addSendValues("KNR_FLG", Util.avoidNull(type));
			// �`�[���t
			addSendValues("SLIP_DATE", strSlipDate);
			// �Ǘ��R�[�h
			addSendValues("KNR_CODE", knrCode);

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
			}
			return getResult();

		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
			return null;
		}
	}
}
