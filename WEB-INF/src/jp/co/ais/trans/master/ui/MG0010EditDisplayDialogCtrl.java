package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * ���ݒ�}�X�^DialogCtrl
 */
public class MG0010EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ���ݒ�}�X�^�_�C�A���O */
	protected MG0010EditDisplayDialog dialog;

	protected boolean isUpdate;

	private static final String TARGET_SERVLET = "MG0010EnvironmentalSettingMasterServlet";

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleID
	 */
	MG0010EditDisplayDialogCtrl(Frame parent, String titleID) {
		dialog = new MG0010EditDisplayDialog(parent, true, this, titleID);

		dialog.txtBackgroundColor.setEnabled(true);
		dialog.txtBackgroundColor.setEditable(false);

		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		dialog.ctrlCompanyCode.requestTextFocus();

		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));
	}

	/**
	 * �\��
	 * 
	 * @param isEnabledknrCode ��ЃR�[�h�G���A�ҏW��(true)�A�t��(false)
	 */
	void show(boolean isEnabledknrCode) {
		dialog.setVisible(true);
		dialog.ctrlCompanyCode.setEditable(isEnabledknrCode);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */

	void setValues(Map map) {
		dialog.ctrlCompanyCode.setValue((String) map.get("kaiCode"));
		dialog.ctrlCompanyName.setValue((String) map.get("kaiName"));
		dialog.ctrlCompanyAbbreviatedName.setValue((String) map.get("kaiName_S"));
		dialog.ctrlAddress1.setValue((String) map.get("jyu1"));
		dialog.ctrlAddress2.setValue((String) map.get("jyu2"));
		dialog.ctrlAddressKana.setValue((String) map.get("jyuKana"));
		dialog.ctrlPostcode.setValue((String) map.get("zip"));
		dialog.ctrlTelephoneNumber.setValue((String) map.get("tel"));
		dialog.ctrlFaxNumber.setValue((String) map.get("fax"));
		if ((map.get("foreColor")).toString().trim().length() < 7) {
			showMessage(dialog, "W00188", (map.get("foreColor")).toString());
			String color1 = "#FFFFFF";
			dialog.txtBackgroundColor.setValue(color1);
			String r = color1.substring(1, 3);
			int red = Integer.parseInt(r, 16);
			String g = color1.substring(3, 5);
			int green = Integer.parseInt(g, 16);
			String b = color1.substring(5, 7);
			int blue = Integer.parseInt(b, 16);
			Color color = new Color(red, green, blue);
			dialog.txtBackgroundColor.setBackground(color);
		} else {
			dialog.txtBackgroundColor.setValue(map.get("foreColor"));
			String r = ((String) map.get("foreColor")).substring(1, 3);
			int red = Integer.parseInt(r, 16);
			String g = ((String) map.get("foreColor")).substring(3, 5);
			int green = Integer.parseInt(g, 16);
			String b = ((String) map.get("foreColor")).substring(5, 7);
			int blue = Integer.parseInt(b, 16);
			Color color = new Color(red, green, blue);
			dialog.txtBackgroundColor.setBackground(color);
		}
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͊Ǘ��R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));

		dialog.ctrlCompanyCode.setEditable(isUpdate == false);

		if (isUpdate) {
			dialog.ctrlCompanyName.getField().requestFocus();
		}

	}

	boolean checkDialog() {

		// "��ЃR�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlCompanyCode.getValue())) {
			showMessage(dialog, "I00002", "C00596");
			dialog.ctrlCompanyCode.requestFocus();
			return false;
		}

		if (!Util.isNullOrEmpty(dialog.ctrlCompanyCode.getValue())) {
			if (!isUpdate && checkCode(dialog.ctrlCompanyCode.getValue())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlCompanyCode.requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// ��Ж��̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlCompanyName.getValue())) {
			showMessage(dialog, "I00002", "C00685");
			dialog.ctrlCompanyName.requestFocus();

			return false;
		}

		// ��З��̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlCompanyAbbreviatedName.getValue())) {
			showMessage(dialog, "I00002", "C00686");
			dialog.ctrlCompanyAbbreviatedName.requestFocus();
			return false;
		}

		// �J�n�N����
		if (Util.isNullOrEmpty(dialog.dtBeginDate.getValue())) {
			super.showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// �I���N����
		if (Util.isNullOrEmpty(dialog.dtEndDate.getValue())) {
			super.showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.requestFocus();
			return false;

		}

		// �J�n�N����,�I���N�����`�F�b�N
		if ((!Util.isNullOrEmpty(dialog.dtBeginDate.getValue()) && !Util.isNullOrEmpty(dialog.dtEndDate.getValue()))
			&& !Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue())) {
			super.showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// �w�i�F�`�F�b�N
		if (Util.isNullOrEmpty(dialog.txtBackgroundColor.getValue())) {
			showMessage(dialog, "I00002", "C00428");
			dialog.btnBackgroundColor.requestFocus();
			return false;
		}

		return true;

	}

	/**
	 * ����
	 */
	void disposeDialog() {
		// �G���[������ꍇ�ɂ̓_�C�A���O����Ȃ�
		if (dialog.isSettle) {

			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			dialog.setVisible(false);
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

		map.put("kaiCode", dialog.ctrlCompanyCode.getValue());
		map.put("kaiName", dialog.ctrlCompanyName.getValue());
		map.put("kaiName_S", dialog.ctrlCompanyAbbreviatedName.getValue());
		map.put("jyu1", dialog.ctrlAddress1.getValue());
		map.put("jyu2", dialog.ctrlAddress2.getValue());
		map.put("jyuKana", dialog.ctrlAddressKana.getValue());
		map.put("zip", dialog.ctrlPostcode.getValue());
		map.put("tel", dialog.ctrlTelephoneNumber.getValue());
		map.put("fax", dialog.ctrlFaxNumber.getValue());
		map.put("foreColor", dialog.txtBackgroundColor.getValue());
		map.put("strDate", dialog.dtBeginDate.getValue());
		map.put("endDate", dialog.dtEndDate.getValue());

		return map;
	}

	boolean checkCode(String code) {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", code);

		// ���M
		try {
			request(TARGET_SERVLET);
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(dialog, e, "E00009");
		}

		List result = super.getResultList();
		return (result.size() > 0);
	}
}
