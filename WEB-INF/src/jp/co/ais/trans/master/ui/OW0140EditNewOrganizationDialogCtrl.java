package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * ��ЊK�w�}�X�^ �V�K�g�D�_�C�A���O �R���g���[��
 * 
 * @author ookawara
 */
public class OW0140EditNewOrganizationDialogCtrl extends TAppletClientBase {

	/** DIVESTMEENT�_�C�A���O */
	private OW0140EditNewOrganizationDialog dialog;

	/** �����T�[�u���b�g */
	private static final String TRAGET_SERVLET = "OW0140CompanyHierarchicalMasterServlet";

	private String kaiCode = "";

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleID
	 */
	OW0140EditNewOrganizationDialogCtrl(Frame parent, String titleID) {
		dialog = new OW0140EditNewOrganizationDialog(parent, true, this, titleID);
		dialog.setSize(720, 160);
		kaiCode = getLoginUserCompanyCode();
	}

	/**
	 * �\��
	 */
	void show() {
		dialog.setVisible(true);
	}

	/**
	 * ����
	 */
	void disposeDialog() {
		try {
			// �G���[������ꍇ�ɂ̓_�C�A���O����Ȃ�
			if (dialog.isSettle) {
				if (checkDialog()) {
					dialog.setVisible(false);
				} else {
					dialog.isSettle = false;
				}
			} else {
				dialog.setVisible(false);
			}
		} catch (IOException e) {
			// errorHandler(dialog.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	boolean isSettle() {
		return dialog.isSettle;
	}

	boolean checkDialog() throws IOException {
		// �g�D�R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlOrganizationCode.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00335");
			dialog.ctrlOrganizationCode.requestTextFocus();
			return false;
		}
		// ���x���O�R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlLevel0.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00748");
			dialog.ctrlLevel0.requestTextFocus();
			return false;
		}
		if (isHavedOrgCode(this.kaiCode, (dialog.ctrlOrganizationCode.getValue()).trim())) {

			dialog.ctrlOrganizationCode.setValue("");
			dialog.ctrlOrganizationCode.requestTextFocus();
			return false;
		}
		return true;
	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("orgCode", dialog.ctrlOrganizationCode.getValue());
		map.put("lvl0Code", dialog.ctrlLevel0.getValue());
		map.put("lvl0Name", dialog.ctrlLevel0.getNoticeValue());

		return map;
	}

	boolean doOwnerCompanyLostFocus(TButtonField bfield) {

		try {
			if (Util.isNullOrEmpty(bfield.getValue())) {
				bfield.setNoticeValue(""); // �󕶎��Z�b�g
				return true;
			}

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			String code = bfield.getValue();

			if (Util.isNullOrEmpty(code)) {
				bfield.setNoticeValue(""); // �󕶎��Z�b�g
				bfield.clearOldText();

				return true;
			}

			addSendValues("flag", "findCompanyName");
			addSendValues("kaiCode", code);

			// ���M

			if (!request(TRAGET_SERVLET)) {
				// �x�����b�Z�[�W�\���i�Y���R�[�h�͑��݂��܂���j
				errorHandler(dialog);

				bfield.clearOldText();
				bfield.requestTextFocus();

				return false;
			}

			// ���̂��Z�b�g
			bfield.setNoticeValue(Util.avoidNull(getResult().get("name")));

			return true;

		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(dialog, e, "E00009");

			bfield.setValue("");
			bfield.setNoticeValue("");

			return false;
		}
	}

	/**
	 * �J�n�i�I���j��������
	 * 
	 * @param bfield
	 */
	void doOwnerCompanyClick(TButtonField bfield) {
		try {
			REFDialogMasterCtrl dialogRef = new REFDialogMasterCtrl(this.dialog, REFDialogMasterCtrl.ENV_MST);

			// ���ސݒ�A��������
			if (!Util.isNullOrEmpty(bfield.getValue())) {
				dialogRef.setCode(bfield.getValue());
				dialogRef.searchData(false);
			}

			dialogRef.show();

			if (dialogRef.isSettle()) {
				String[] info = dialogRef.getCurrencyInfo();
				bfield.setValue(info[0]);
				bfield.setNoticeValue(info[1]);
			}
			bfield.requestTextFocus();

		} catch (Exception e) {
			errorHandler(dialog, e, "E00009");
		}
	}

	/**
	 * @param compCode
	 * @param sskCode
	 * @return boolean
	 * @throws IOException
	 */
	boolean isHavedOrgCode(String compCode, String sskCode) throws IOException {
		super.clearSendValues();
		super.addSendValues("flag", "getSskCode");

		super.addSendValues("kaiCode", compCode);
		super.addSendValues("sskCode", sskCode);

		if (!request(TRAGET_SERVLET)) {
			errorHandler(dialog);
		}
		return false;

	}

}
