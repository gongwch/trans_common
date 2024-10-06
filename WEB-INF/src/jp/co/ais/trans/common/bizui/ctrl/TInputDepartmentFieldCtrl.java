package jp.co.ais.trans.common.bizui.ctrl;

import java.awt.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * ���͕���t�B�[���h�R���g���[��
 */
public class TInputDepartmentFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "TReferenceAuthorityServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �t�B�[���h */
	protected TInputDepartmentField field;

	/** �R�[�h(����) */
	protected String codeLabel = getWord("C00174");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param inputDepartmentField �t�B�[���h
	 */
	public TInputDepartmentFieldCtrl(TInputDepartmentField inputDepartmentField) {

		this.field = inputDepartmentField;
	}

	/**
	 * �������Z�b�g
	 */
	public void setInit() {
		try {
			Map map = new HashMap();
			map = searchInit();

			field.setValue(Util.avoidNull(map.get("bmnCode")));
			field.setNoticeValue(Util.avoidNull(map.get("bmnNameS")));
			int updKen = Integer.valueOf(Util.avoidNull(map.get("updKen")));
			// ���[�U�[�}�X�^.�X�V�������x�� <> 0(��ALL)�̏ꍇ
			if (updKen != 0) {
				// ���͕���{�^�������s��
				field.getButton().setEnabled(false);
				// ���͕���R�[�h�e�L�X�g�{�b�N�X���͕s��
				field.getField().setEditable(false);
			} else {
				// ���͕���{�^��������
				field.getButton().setEnabled(true);
				field.setValue("");
				// ���͕���R�[�h�e�L�X�g�{�b�N�X���͉�
				field.getField().setEditable(true);
				field.setNoticeValue("");

			}

		} catch (TException e) {
			errorHandler(field, e);
		}
	}

	/**
	 * �������擾
	 * 
	 * @return ����
	 * @throws TException
	 */
	protected Map searchInit() throws TException {
		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("FLAG", "INIT");

			if (!request(TARGET_SERVLET)) {
				// ����ɏ�������܂���ł���
				throw new TException(getErrorMessage());
			}

			return getResult();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	// �{�^���������̏���

	/**
	 * ����}�X�^��������
	 * 
	 * @return true:���폈��
	 */
	public boolean search() {
		try {
			// ����}�X�^�ꗗ�̏ꍇ
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.BMN_MST);

			// �����ݒ� Dialog
			setCondition(dialog, getLoginUserCompanyCode());

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				dialog.setCode(String.valueOf(field.getValue()));
				dialog.searchData(false);
			}

			// �����_�C�A���O��\��
			dialog.show();

			field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			boolean isSettled = dialog.isSettle();

			// �m��̏ꍇ
			if (isSettled) {
				String[] info = dialog.getCurrencyInfo();

				// �t�B�[���h�Ɠ���R�[�h���I�΂ꂽ�ꍇ�͏����Ȃ�
				if (field.getValue().equals(info[0])) {
					return false;
				}

				// ����R�[�h
				field.setValue(info[0]);
				// ���嗪��
				field.setNoticeValue(info[1]);

				// ������ݒ�
				setupInfo();
			}

			return isSettled;

		} catch (Exception e) {
			errorHandler(field, e, "E00009");

			return false;
		} finally {
			field.getField().requestFocus();

			field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	// LostFocus���̏���

	/**
	 * ���働�X�g�t�H�[�J�X����<BR>
	 * 
	 * @return true:���폈��
	 */
	public boolean setupInfo() {
		try {
			// ���͕���R�[�h
			String strBmnCode = field.getField().getText();

			if (Util.isNullOrEmpty(strBmnCode)) {

				// ���͕���R�[�h���̃u�����N�ŃZ�b�g����B
				field.setNoticeValue("");

				return true;
			}

			// ���͕���}�X�^���擾
			Map<String, String> map = findBmnMstInfo(getLoginUserCompanyCode(), strBmnCode);

			// ���͕���R�[�h���݂̏ꍇ
			if (!"0".equals(map.get("existFlag"))) {
				// ���̂��Z�b�g
				field.setNoticeValue(Util.avoidNull(map.get("DEP_NAME_S")));

				return true;
			} else {

				if (field.isChekcMode()) {
					// ���݂��܂���B
					showMessage(field, "W00081", (Object) field.getButtonText() + codeLabel);
				}

				field.clearOldText();

				// ���͕���R�[�h���̃u�����N�ŃZ�b�g����B
				field.setNoticeValue("");

				// ���X�g�t�H�[�J�X���擾����B
				field.requestTextFocus();

				return !field.isChekcMode();
			}
		} catch (TException e) {
			errorHandler(this.field, e, e.getMessageID(), e.getMessageArgs());

			return false;
		} catch (Exception e) {
			errorHandler(this.field);

			return false;
		}
	}

	/**
	 * �����ݒ� Dialog
	 * 
	 * @param dialog
	 * @param companyCode ��ЃR�[�h
	 */
	protected void setCondition(REFDialogMasterCtrl dialog, String companyCode) {

		// ��ЃR�[�h
		dialog.setKaiCode(Util.avoidNull(companyCode));
	}

	/**
	 * ����}�X�^�f�[�^������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param strBmnCode ���͕���R�[�h
	 * @return ���͕���}�X�^�f�[�^
	 * @throws IOException
	 * @throws TException
	 */
	protected Map<String, String> findBmnMstInfo(String companyCode, String strBmnCode) throws IOException, TException {

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "DEPARTMENT");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(companyCode));
		// ���͕���R�[�h
		addSendValues("BMN_CODE", (Util.avoidNull(strBmnCode)));

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TException(getErrorMessage());
		}

		return getResult();
	}
}
