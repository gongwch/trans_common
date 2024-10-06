package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �E�v���ʃt�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TSlipMemoFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0120MemoMasterServlet";

	/** field */
	private TSlipMemoField field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField
	 */
	public TSlipMemoFieldCtrl(TSlipMemoField itemField) {
		this.field = itemField;
	}

	/**
	 * ���[�X�g�t�H�[�J�X�̑Ή�
	 * 
	 * @return boolean
	 */
	public boolean slipMomoLostFocus() {
		try {
			// ���͂��ꂽ�l���擾
			String code = field.getValue();

			int type = field.getMemoType();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.clearOldText();
				return true;
			}

			// �E�v�f�[�^�K��
			List list = getFieldDataList(code, type);

			// ���ʖ����̏ꍇ
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C00564");
				field.clearOldText();
				return false;
			}

			// �L�����Ԃ̃t���O
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));
				Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(4));

				if (termDate.after(endDate) || strDate.after(termDate)) {

					if (!showConfirmMessage(field, "Q00025", "C00384")) {
						return false;
					}
				}
			}

			// �l��\������B
			field.setNoticeValue((String) ((List) list.get(0)).get(1));

			return true;

		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}
	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param type �K�p�^�C�v
	 * @param code �R�[�h
	 * @return �ڍ׃��X�g
	 * @throws IOException
	 */
	private List getFieldDataList(String code, int type) throws IOException {

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "refMemo"); // �敪flag

		if (!Util.isNullOrEmpty(field.getCompanyCode())) {
			addSendValues("kaiCode", field.getCompanyCode());
		} else {
			addSendValues("kaiCode", getLoginUserCompanyCode());
		}

		// �E�v�R�[�h �F���͒l
		addSendValues("tekCode", code);
		// �E�v�敪
		addSendValues("tekKbn", String.valueOf(type));

		if (!request(TARGET_SERVLET)) {
			// �N���C�A���g ��M��̓G���[�B
			errorHandler(field, "S00002");
		}

		return getResultList();
	}

	/**
	 * �_�C�A���O�\���ݒ�
	 */
	public void btnActionPerformed() {
		try {
			// �p�����[�^�[�̃Z�b�g
			Map<String, String> initParam = getSearchDialogParam(field.getMemoType());

			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.MEMO_MST, initParam);

			searchdialog.setStartCode(""); // �J�n�R�[�h
			searchdialog.setEndCode(""); // �I���R�[�h
			searchdialog.setServerName("refMemo");

			Map<String, String> otherParam = new HashMap<String, String>();

			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}

			otherParam.put("companyCode", field.getCompanyCode());

			// �f�[�^�^�C�v
			otherParam.put("slipType", field.getSlipDataType());

			// �E�v�敪
			otherParam.put("memoType", String.valueOf(field.getMemoType()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
			searchdialog.setParamMap(otherParam);

			// ���ސݒ�A��������
			if (!Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			// �����_�C�A���O��\��
			searchdialog.show();

			// �m��̏ꍇ
			if (searchdialog.isSettle()) {
				String[] info = searchdialog.getCurrencyInfo();
				// �R�[�h���Z�b�g����
				field.setValue(info[0]);
				// ���̂��Z�b�g����
				field.setNoticeValue(info[1]);
			}

			field.getField().requestFocus();

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}
	}

	/**
	 * �_�C�A���O��ʂ̃^�C�g�����ݒ�
	 * 
	 * @param type �E�v�^�C�v
	 * @return Map �^�C�g��
	 */
	private Map<String, String> getSearchDialogParam(int type) {
		Map<String, String> param = new HashMap<String, String>();

		// �`�[�E�v
		if (type == TSlipMemoField.SLIP_DATA) {
			param.put("top", "C01672"); // top�̃Z�b�g
			param.put("code", "C01652"); // �R�[�h�̃Z�b�g
			param.put("nameS", "C01653"); // ����
			param.put("nameK", "C01654"); // ��������
			// �s�E�v
		} else if (type == TSlipMemoField.MEMO_DATA) {
			param.put("top", "C01673"); // top�̃Z�b�g
			param.put("code", "C01560"); // �R�[�h�̃Z�b�g
			param.put("nameS", "C01655"); // ����
			param.put("nameK", "C01656"); // ��������
		}

		return param;
	}
}
