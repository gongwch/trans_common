package jp.co.ais.trans.common.bizui.ctrl;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �Ȗڑ̌n�t�B�[���h�R���g���[��
 * 
 * @author moriya
 */
public class TItemSystemFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "InformationServlet";

	/** �t�B�[���h */
	protected TItemSystemField panel;

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField �t�B�[���h
	 */
	public TItemSystemFieldCtrl(TItemSystemField itemField) {
		this.panel = itemField;
		// �����̉Ȗڑ̌n���̂��擾����
		getItemSystemInfo("init");

	}

	/**
	 * ��{�Ȗڑ̌n���擾����
	 * 
	 * @param flag ��Ԃ������t���O
	 * @return true:���폈��
	 */
	public boolean getItemSystemInfo(String flag) {
		try {

			// ������ʃ��[�h���̕\��
			if (flag.equals("init")) {
				panel.getField().setValue("00");
			}

			// ��{�Ȗڑ̌n�R�[�h
			String itemSystemCode = flag.equals("init") ? "00" : panel.getField().getText();

			// �Ȗڑ̌n�R�[�h���u�����N�̎��̃��X�g�t�H�[�J�X�̓G���[
			if (Util.isNullOrEmpty(itemSystemCode)) {
				showMessage(panel, "I00002", "C00609");
				panel.setNoticeValue("");
				panel.clearOldText();
				panel.requestTextFocus();
				return false;
			}

			// �t���O
			addSendValues("FLAG", "GetItemSystem");
			// ��{�Ȗڑ̌n�R�[�h
			addSendValues("ITEM_SYSTEM_CODE", itemSystemCode);

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return false;
			}

			// ���ʂ��擾����
			Map resultMap = getResult();

			// ��{�Ȗڑ̌n�����擾����
			String strItemSystemName = Util.avoidNull(resultMap.get("ITEM_SYSTEM_NAME"));
			panel.setNoticeValue(strItemSystemName);

			// �Y���R�[�h�͑��݂��܂���B
			if (Util.isNullOrEmpty(strItemSystemName)) {
				showMessage(panel, "W00081", "C00617");

				panel.setNoticeValue("");
				panel.clearOldText();
				panel.requestTextFocus();

				return false;
			}

			return true;

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
			return false;
		}
	}

	/**
	 * �Ȗڑ̌n�_�C�A���O�̕\��
	 */
	public void showItemDialog() {
		try {
			// �Ȗڑ̌n�}�X�^�ꗗ�̃_�C�A���O���Ă�
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(panel, REFDialogMasterCtrl.KMK_TK_MST);

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(panel.getField().getValue())) {
				dialog.setCode(String.valueOf(panel.getField().getValue()));
				dialog.searchData(false);
			}

			// �_�C�A���O�I�[�v��
			dialog.show();

			if (dialog.isSettle()) {
				String[] info = dialog.getCurrencyInfo();
				panel.setValue(info[0]);
				panel.setNoticeValue(info[1]);
			}
			// �t�H�[�J�X�擾
			panel.getField().requestFocus();

		} catch (Exception e) {
			errorHandler(panel, e);
		}
	}

}
