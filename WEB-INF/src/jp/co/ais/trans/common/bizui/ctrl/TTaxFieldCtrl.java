package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * ����ŋ��ʃt�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TTaxFieldCtrl extends TAppletClientBase {

	/** �t�B�[���h */
	private TTaxField field;

	/** �L�����ԃt���O */
	public boolean termBasisDateFlag;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0130ConsumptionTaxMasterServlet";

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField <br>
	 *            ����Ńt�B�[���h
	 */
	public TTaxFieldCtrl(TTaxField itemField) {
		this.field = itemField;
	}

	/**
	 * ���[�X�g�t�H�[�J�X���̏���
	 * 
	 * @return boolean
	 */
	public boolean taxLostFocus() {
		try {
			// ���̓R�[�h���擾����
			String code = field.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.setUsKbnPurchase(false);
				field.setUsKbnSales(false);
				field.setRate(0.0f);
				field.clearOldText();
				return true;
			}

			// ����Ŗ��̂̎擾
			List list = getFieldTaxDataList(code);

			// ���ʖ����̏ꍇ
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C00573");
				field.setNoticeValue("");
				field.clearOldText();
				// ���X�g�t�H�[�J�X���擾����B
				field.requestTextFocus();
				return false;
			}

			if (termBasisDateFlag) {
				// �L�����ԓ��̃f�[�^�����肷��
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {

					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
					Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));
					Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(4));

					if (termDate.after(endDate) || strDate.after(termDate)) {
						if (!showConfirmMessage(field, "Q00025", "C00286")) {
							field.requestTextFocus();
							return false;
						}
					}
				}
			}

			// �Ń��[�g���Z�b�g
			field.setRate(Util.isNullOrEmpty(((List) (list.get(0))).get(5)) ? null : Float.valueOf(Util
				.avoidNull(((List) (list.get(0))).get(5))));

			// ����ŋ敪
			String taxKbn = (String) ((List) (list.get(0))).get(2);

			// �f�[�^�敪���Z�b�g
			if ("0".equals(taxKbn)) {
				field.setUsKbnPurchase(false);
				field.setUsKbnSales(false);
			} else if ("1".equals(taxKbn)) {
				field.setUsKbnPurchase(false);
				field.setUsKbnSales(true);
			} else {
				field.setUsKbnPurchase(true);
				field.setUsKbnSales(false);
			}

			// �f�[�^�敪�ɂ��G���[��\������B
			if (!checkKbn(taxKbn)) {
				showMessage(field, "W01033", "C00286");
				field.setNoticeValue("");
				field.clearOldText();
				// ���X�g�t�H�[�J�X���擾����B
				field.requestTextFocus();
				return false;
			}
			// �t�B�[���h�ɃZ�b�g����
			field.setNoticeValue((String) ((List) list.get(0)).get(1));

			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * ����ŋ敪���\�b�h �d���w��Ή�
	 * 
	 * @param taxKbn <br>
	 *            DB����K����������Ńf�[�^�敪
	 * @return boolean true�F���� false:���s
	 */
	private boolean checkKbn(String taxKbn) {

		boolean isValid = false;
		// �ΏۊO�敪
		if ((!field.isSales()) && (!field.isPurchase()) && (!field.isElseTax())) {
			isValid = true;
		}

		if (field.isElseTax()) {
			if ("0".equals(taxKbn)) {
				isValid = true;
			}
		}
		// ����グ�敪
		if (field.isSales()) {
			if ("1".equals(taxKbn)) {
				isValid = true;
			}
		}
		// �d����敪
		if (field.isPurchase()) {
			if ("2".equals(taxKbn)) {
				isValid = true;
			}
		}
		return isValid;
	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param code �R�[�h
	 * @return �ڍ׃��X�g
	 * @throws IOException
	 */
	protected List getFieldTaxDataList(String code) throws IOException {

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "refTax"); // �敪flag

		if (!Util.isNullOrEmpty(field.getCompanyCode())) {
			addSendValues("kaiCode", field.getCompanyCode());
		} else {
			addSendValues("kaiCode", getLoginUserCompanyCode());
		}

		addSendValues("zeiCode", code);

		if (!request(TARGET_SERVLET)) {
			// �N���C�A���g ��M��̓G���[�B
			errorHandler(field, "S00002");
		}

		return getResultList();
	}

	/**
	 * �{�^���������̏���
	 */
	public void btnActionPerformed() {
		try {
			Map<String, String> param = new HashMap<String, String>();

			param.put("top", "C01675"); // �^�C�g���̃Z�b�g
			param.put("code", "C00573"); // �R�[�h�̃Z�b�g
			param.put("nameS", "C00775"); // ����
			param.put("nameK", "C00828"); // ��������

			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.TAX_MST, param);

			searchdialog.setStartCode(""); // �J�n�R�[�h
			searchdialog.setEndCode(""); // �I���R�[�h
			searchdialog.setServerName("refTax");

			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			otherParam.put("sales", BooleanUtil.toString(field.isSales()));
			otherParam.put("purchase", BooleanUtil.toString(field.isPurchase()));
			otherParam.put("elseTax", BooleanUtil.toString(field.isElseTax()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
			searchdialog.setParamMap(otherParam);

			// ���ސݒ�A��������
			if (!Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

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

}
