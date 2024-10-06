package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.config.*;

/**
 * ��s�����t�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TBankFieldCtrl extends TAppletClientBase {

	/** ��s���� �t�B�[���h */
	protected TBankField field;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MG0140BankMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �L�����ԃt���O */
	public boolean termBasisDateFlag = true;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField (��ʁj
	 */
	public TBankFieldCtrl(TBankField itemField) {
		this.field = itemField;
	}

	/**
	 * ���[�X�g�t�H�[�J�X�̑Ή�
	 * 
	 * @return boolean
	 */
	public boolean setBankNotice() {

		try {
			String code = field.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// �Ј��R�[�h�Ńf�[�^�擾
			BNK_MST bnkMst = getFieldDataList(code);

			// ���ʖ����̏ꍇ
			if (bnkMst == null) {
				showMessage(field, "W00081", "C00779");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				return false;
			}

			if (termBasisDateFlag) {
				// �L�����Ԃ̃t���O
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
					Date strDate = bnkMst.getSTR_DATE();
					Date endDate = bnkMst.getEND_DATE();

					if (termDate.after(endDate) || strDate.after(termDate)) {
						// �Θb�_�C�A���O���J���A���s�̉ۂ�₤
						if (!showConfirmMessage(field, "Q00025", "C00246")) {
							field.requestTextFocus();
							return false;
						}
					}
				}
			}

			// �ڍ׃t�B�[���h�ɒl��ݒ�
			field.setNoticeValue(bnkMst.getBNK_NAME_S());

			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param code �Ј��R�[�h
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	protected BNK_MST getFieldDataList(String code) throws TException {

		try {
			BnkMstParameter param = new BnkMstParameter();
			param.setBnkCode(code);

			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "nameBank"); // �敪flag
			addSendObject(param); // �����p�����[�^
			if (!request(TARGET_SERVLET)) {
				// �N���C�A���g ��M��̓G���[�B
				throw new TException("S00002");
			}

			BNK_MST bnkmst = (BNK_MST) getResultObject();
			if (bnkmst == null) {
				return null;
			}

			return bnkmst;

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * �_�C�A���O�\��
	 */
	public void showSearchDialog() {

		try {
			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.BNK_BNK, initParam);
			searchdialog.setServerName("BNK_BNK_MST"); // ����Flag

			// �����p�����[�^
			BnkMstParameter param = new BnkMstParameter();
			param.setBnkCodeBegin(field.getBeginCode());
			param.setBnkCodeEnd(field.getEndCode());
			param.setTermBasisDate(DateUtil.toYMDDate(field.getTermBasisDate()));
			searchdialog.setParamObject(param);

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			showDialog(searchdialog);

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}

	}

	/**
	 * ����dialog�����l�̃Z�b�g
	 * 
	 * @return param �^�C�g��
	 */
	protected Map<String, String> getSearchDialogParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("top", getWord("C00124") + getWord("C00010")); // ��s�ꗗ
		param.put("code", "C00779"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C00781"); // ��s����
		param.put("nameK", "C00829"); // ��s��������

		return param;
	}

	/**
	 * �����_�C�A���O�\��<BR>
	 * 
	 * @param dialog �_�C�A���O
	 * @return boolean : true �m��̏ꍇ false ����̏ꍇ
	 */
	protected boolean showDialog(REFDialogMasterCtrl dialog) {
		// �m��̏ꍇ
		if (dialog.isSettle()) {
			String[] info = dialog.getCurrencyInfo();
			field.setValue(info[0]); // �R�[�h���Z�b�g����
			field.setNoticeValue(info[1]); // ���̂��Z�b�g����
			field.getField().requestFocus();
			return true;
		}
		field.getField().requestFocus();
		return false;
	}

}
