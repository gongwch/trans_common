package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �ʉ݃}�X�^�_�C�A���O �R���g���[��
 */
public class MG0300EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �ʉ݃}�X�^�_�C�A���O */
	protected MG0300EditDisplayDialog dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0300CurrencyMasterServlet";
	}

	protected boolean isUpdate = false;

	/**
	 * �R���X�g���N�^
	 */
	MG0300EditDisplayDialogCtrl() {
		// �����Ȃ�
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0300EditDisplayDialogCtrl(Frame parent, String titleid) {
		dialog = new MG0300EditDisplayDialog(parent, true, this, titleid);

		dialog.ctrlCurrencyCode.getField().requestFocus();

		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		try {
			dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
			dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));
		} catch (Exception e) {
			errorHandler(dialog, e, "E00009");
		}
		dialog.ctrlCurrencyCode.getField().requestFocus();

	}

	/**
	 * �\��
	 */
	void show() {
		dialog.setVisible(true);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */

	void setValues(Map map) {
		dialog.ctrlCurrencyCode.setValue((String) map.get("curCode"));
		dialog.ctrlCurrencyName.setValue((String) map.get("curName"));
		dialog.ctrlCurrencyAbbreviatedName.setValue((String) map.get("curName_S"));
		dialog.ctrlCurrencyNameForSearch.setValue((String) map.get("curName_K"));
		dialog.numDecimalPoint.setValue((String) map.get("decKeta"));
		dialog.numRatecoefficient.setValue((String) map.get("ratePow"));
		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("convKbn")));
		dialog.rdoCalculateMultiplication.setSelected(!boo);
		dialog.rdoCalculateDivision.setSelected(boo);

		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͒ʉ݃R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlCurrencyCode.setEditable(!isUpdate);
		if (isUpdate) {
			dialog.ctrlCurrencyName.getField().requestFocus();
		}
	}

	boolean checkDialog() {
		try {
			// �R�[�h
			if (Util.isNullOrEmpty(dialog.ctrlCurrencyCode.getValue())) {
				showMessage(dialog, "I00002", "C00665");
				dialog.ctrlCurrencyCode.requestFocus();
				return false;
			}

			if (!isUpdate && checkCode(dialog.ctrlCurrencyCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlCurrencyCode.requestFocus();
				return false;
			}

			if (Util.isNullOrEmpty(dialog.ctrlCurrencyName.getValue())) {
				showMessage(dialog, "I00002", "C00880");
				dialog.ctrlCurrencyName.requestFocus();
				return false;
			}

			if (Util.isNullOrEmpty(dialog.ctrlCurrencyAbbreviatedName.getValue())) {
				showMessage(dialog, "I00002", "C00881");
				dialog.ctrlCurrencyAbbreviatedName.requestFocus();
				return false;
			}

			if (Util.isNullOrEmpty(dialog.ctrlCurrencyNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00882");
				dialog.ctrlCurrencyNameForSearch.requestFocus();
				return false;
			}

			if (Util.isNullOrEmpty(dialog.numRatecoefficient.getValue())) {
				showMessage(dialog, "I00002", "C00896");
				dialog.numRatecoefficient.requestFocus();
				return false;
			}
			if (Util.isNullOrEmpty(dialog.numDecimalPoint.getValue())) {
				showMessage(dialog, "I00002", "C00884");
				dialog.numDecimalPoint.requestFocus();
				return false;
			}
			String valueDP = dialog.numDecimalPoint.getValue();
			if (!Util.isNullOrEmpty(valueDP) && (Integer.parseInt(valueDP) < 0 || 4 < Integer.parseInt(valueDP))) {
				showMessage(dialog, "W00065", 0, 4);
				dialog.numDecimalPoint.requestFocus();
				return false;
			}
			String valueR = dialog.numRatecoefficient.getValue();
			if (!Util.isNullOrEmpty(valueR) && (Integer.parseInt(valueR) < -9 || 9 < Integer.parseInt(valueR))) {
				showMessage(dialog, "W00065", -9, 9);
				dialog.numRatecoefficient.requestFocus();
				return false;
			}

			// �J�n�N����
			if (dialog.dtBeginDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00055");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}
			// �I���N����
			if (dialog.dtEndDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00261");
				dialog.dtEndDate.getCalendar().requestFocus();
				return false;
			}

			// �J�n�N���������I���N�����ɂ��Ă�������
			if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
				showMessage(dialog, "W00035", "");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}

			return true;
		} catch (IOException e) {
			ClientErrorHandler.handledException(e);
			return false;
		}
	}

	/**
	 * ����
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (!this.checkDialog()) return;
			dialog.setVisible(!this.checkDialog());
		} else {
			dialog.setVisible(false);// ����
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

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("curCode", dialog.ctrlCurrencyCode.getValue());
		map.put("curName", dialog.ctrlCurrencyName.getValue());
		map.put("curName_S", dialog.ctrlCurrencyAbbreviatedName.getValue());
		map.put("curName_K", dialog.ctrlCurrencyNameForSearch.getValue());
		map.put("decKeta", dialog.numDecimalPoint.getValue());
		map.put("ratePow", dialog.numRatecoefficient.getValue());
		map.put("convKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoCalculateDivision.isSelected())));
		map.put("strDate", dialog.dtBeginDate.getValue());
		map.put("endDate", dialog.dtEndDate.getValue());

		return map;
	}

	boolean checkCode(String code) throws IOException {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("curCode", code);

		if (!request(getServletName())) {
			errorHandler(dialog);
			return true;
		}
		List result = super.getResultList();
		return (result.size() > 0);
	}
}
