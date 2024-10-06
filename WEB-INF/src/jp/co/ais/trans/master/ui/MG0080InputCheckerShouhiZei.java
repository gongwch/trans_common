package jp.co.ais.trans.master.ui;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ����ŁA���̓t���O�g�����`�F�b�N�̂��߂̃N���X
 */
public class MG0080InputCheckerShouhiZei extends PanelAndDialogCtrlBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0130ConsumptionTaxMasterServlet";

	private TDialog dialog;

	/**
	 * constructor. <br>
	 * �G���[���b�Z�[�W�o�͂̂��߂̐edialog��n��
	 * 
	 * @param dialog
	 */
	public MG0080InputCheckerShouhiZei(TDialog dialog) {
		super();
		this.dialog = dialog;
	}

	/**
	 * ���͑g�����`�F�b�N
	 * 
	 * @param code ����ŃR�[�h
	 * @param flagUriage ����ېœ��̓t���O
	 * @param flagShiire �d���ېœ��̓t���O
	 * @return boolean
	 */
	public boolean isValid(String code, boolean flagUriage, boolean flagShiire) {

		// ����ŃR�[�h������
		if (Util.isNullOrEmpty(code)) {
			return true;
		}

		// �����Ƃ�ON�Ȃ�`�F�b�N�̕K�v�Ȃ�
		if (flagUriage && flagShiire) {
			return true;
		}

		// ********************
		// request to server.
		// ********************
		try {
			// ������ʂ̐ݒ�
			addSendValues("flag", "findone");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", TClientLoginInfo.getInstance().getCompanyCode());
			// ����ŃR�[�h�̐ݒ�
			addSendValues("zeiCode", code);

			// ���M
			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return false;
			}

			// ���ʂ��擾
			List<List<String>> result = super.getResultList();
			// ���ʂ�Ԃ�

			if (result.size() <= 0) {
				// ����ɏ�������܂���ł���
				errorHandler(dialog, "E00009");
				return false;
			}

			List<String> list = result.get(0);

			// list[5]:����d���敪
			if (list.size() < 6) {
				// ����ɏ�������܂���ł���
				errorHandler(dialog, "E00009");
				return false;
			}

			int usKbn = Util.avoidNullAsInt(list.get(5));

			// ***************************
			// ����ŃR�[�h�ƉېŃt���O�̊֘A�`�F�b�N
			// ***************************

			// ����/�d���敪 1:���� 2:�d��
			if (usKbn == 1 && !flagUriage || usKbn == 2 && !flagShiire) {
				// ����ŃR�[�h�ƉېŃt���O�̊֘A������Ă��܂��B
				errorHandler(dialog, "W00220");
				return false;
			}
			return true;
		} catch (IOException ex) {
			// ����ɏ�������܂���ł���
			errorHandler(dialog, ex, "E00009");
			return false;
		}
	}
}
