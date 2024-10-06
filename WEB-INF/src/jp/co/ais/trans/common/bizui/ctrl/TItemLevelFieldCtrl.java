package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �Ȗڃ��x���t�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TItemLevelFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	/** �Ȗڃp�l�� */
	private TItemLevelField field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panel
	 */
	public TItemLevelFieldCtrl(TItemLevelField panel) {
		try {
			this.field = panel;

			initControl();
		} catch (Exception e) {
			errorHandler(panel, e, "E00010");
		}
	}

	/**
	 * ��ʕ\���\��
	 */
	public void initControl() {
		try {

			// ��ЃR���g���[�����K��
			List<List<String>> list = getFieldDataList();

			if (list.isEmpty()) {
				return;
			}

			// ��s�ڂ�ROW
			List<String> dataList = list.get(0);

			field.getItemButton().setText(dataList.get(0));
			field.getSubItemButton().setText(dataList.get(1));
			field.getBreakDownItemButton().setText(dataList.get(3));

			// ����\���A��\��
			field.getBreakDownItemButton().setVisible(BooleanUtil.toBoolean(dataList.get(2)));

		} catch (TException e) {
			errorHandler(field, e);
		}
	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @return List �Ȗڃ��x���ڍ׃��X�g
	 * @throws TException
	 */
	private List<List<String>> getFieldDataList() throws TException {

		String buttonName = "refControl";

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", buttonName); // �敪flag
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}

			if (!request(TARGET_SERVLET)) {
				// �N���C�A���g ��M��̓G���[�B
				errorHandler(field, "S00002");
			}

			return getResultList();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}
}
