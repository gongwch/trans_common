package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �Ǘ��}�X�^�_�C�A���O �R���g���[��
 * 
 * @author ookawara
 */
public class MG0230EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �Ǘ��}�X�^�_�C�A���O */
	protected MG0230EditDisplayDialog dialog;

	private boolean isUpdate;

	private static final String TARGET_SERVLET = "MG0230Management6MasterServlet";

	String knrName;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0230EditDisplayDialogCtrl(Frame parent, String titleid) {
		// �Ǘ��}�X�^�_�C�A���O�̏�����
		dialog = createDialog(parent, titleid);

		knrName = CompanyControlHelper230.getInstance(this.getLoginUserCompanyCode()).getKnrName6();
		dialog.ctrlManagementCode.getLabel().setText(knrName + this.getWord("C00174"));
		dialog.ctrlManagementName.getLabel().setText(knrName + this.getWord("C00518"));
		dialog.ctrlManagementAbbreviationName.getLabel().setText(knrName + this.getWord("C00548"));
		dialog.ctrlManagementNameForSearch.getLabel().setText(knrName + this.getWord("C00160"));

		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});

		dialog.ctrlManagementCode.getField().requestFocus();

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	/**
	 * @param parent �e�R���e�i�[
	 * @param titleid �^�C�g��
	 * @return �_�C�A���O
	 */
	protected MG0230EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0230EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * �\��
	 * 
	 * @param isEnabledknrCode �Ǘ��R�[�h�G���A�ҏW��(true)�A�t��(false)
	 */
	void show(boolean isEnabledknrCode) {
		// ��ʂ�\������
		dialog.setVisible(true);
		// ����R�[�h�̐ݒ�
		dialog.ctrlManagementCode.setEditable(isEnabledknrCode);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */
	void setValues(Map map) {
		// �Ǘ��R�[�h�̐ݒ�
		dialog.ctrlManagementCode.setValue((String) map.get("knrCode"));
		// �Ǘ����̂̐ݒ�
		dialog.ctrlManagementName.setValue((String) map.get("knrName"));
		// �Ǘ����̂̐ݒ�
		dialog.ctrlManagementAbbreviationName.setValue((String) map.get("knrName_S"));
		// �Ǘ��������̂̐ݒ�
		dialog.ctrlManagementNameForSearch.setValue((String) map.get("knrName_K"));
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͊Ǘ��R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		// �Ǘ��R�[�h�̐ݒ�
		dialog.ctrlManagementCode.setEditable(isUpdate == false);

		if (isUpdate) {
			dialog.ctrlManagementName.getField().requestFocus(isUpdate);
		}

	}

	/**
	 * ����
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			// �߂�{�^������
			// ��ʂ�߂�
			dialog.setVisible(false);
		}
	}

	boolean checkDialog() {
		// �Ǘ��R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlManagementCode.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", knrName + getWord("C00174"));
			dialog.ctrlManagementCode.requestFocus();
			// �G���[��Ԃ�
			return false;
		}
		// �Ǘ��R�[�h�����݂��Ă��܂�
		if (!isUpdate && checkCode(dialog.ctrlManagementCode.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlManagementCode.requestFocus();
			// �G���[��Ԃ�
			return false;
		}
		// �Ǘ����̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlManagementName.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", knrName + getWord("C00518"));
			dialog.ctrlManagementName.requestFocus();
			// �G���[��Ԃ�
			return false;
		}
		// �Ǘ����̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlManagementAbbreviationName.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", knrName + getWord("C00548"));
			dialog.ctrlManagementAbbreviationName.requestFocus();
			// �G���[��Ԃ�
			return false;
		}
		// �Ǘ��������̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlManagementNameForSearch.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", knrName + getWord("C00160"));
			dialog.ctrlManagementNameForSearch.requestFocus();
			// �G���[��Ԃ�
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

		// �N�����`�F�b�N
		if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.getCalendar().requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		return true;
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	boolean isSettle() {
		// ������ʂ̐ݒ�
		return dialog.isSettle;
	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// �Ǘ��R�[�h�̐ݒ�
		map.put("knrCode", dialog.ctrlManagementCode.getValue());
		// �Ǘ����̂̐ݒ�
		map.put("knrName", dialog.ctrlManagementName.getValue());
		// �Ǘ����̂̐ݒ�
		map.put("knrName_K", dialog.ctrlManagementNameForSearch.getValue());
		// �Ǘ��������̂̐ݒ�
		map.put("knrName_S", dialog.ctrlManagementAbbreviationName.getValue());
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", getLoginUserCompanyCode());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String code) {
		// �Ǘ��R�[�h������
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �Ǘ��R�[�h�̐ݒ�
		addSendValues("knrCode", code);

		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return true;
			}

			// ���ʂ��擾
			List result = super.getResultList();
			// ���ʂ�Ԃ�
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

}

class CompanyControlHelper230 extends TAppletClientBase {

	private static Map instances = Collections.synchronizedMap(new HashMap());

	/**
	 * @param companyCode
	 * @return CompanyControlHelper230
	 */
	public static CompanyControlHelper230 getInstance(String companyCode) {
		if (!instances.containsKey(companyCode)) {
			CompanyControlHelper230 instance = new CompanyControlHelper230(companyCode);
			instances.put(companyCode, instance);
		}
		return (CompanyControlHelper230) instances.get(companyCode);
	}

	/**
	 * @param companyCode
	 */
	public static void reload(String companyCode) {
		instances.remove(companyCode);
	}

	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	private CompanyControlHelper230(String companyCode) {
		addSendValues("flag", "findone");
		addSendValues("kaiCode", companyCode);
		// try {
		// request(TARGET_SERVLET);
		// } catch (Exception ex) {
		// ClientErrorHandler.handledException(ex);
		// }
		//
		// // �T�[�o���̃G���[�ꍇ
		// Map error = getError();
		// if (error.size() > 0) {
		// errorHandler(this.getPanel(), error);
		// }
		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler();
			} else {
				List result = super.getResultList();
				if (result != null && result.size() > 0) {
					List inner = (List) result.get(0);

					knrKbn1 = (String) inner.get(5);
					knrKbn2 = (String) inner.get(6);
					knrKbn3 = (String) inner.get(7);
					knrKbn4 = (String) inner.get(8);
					knrKbn5 = (String) inner.get(9);
					knrKbn6 = (String) inner.get(10);
					knrName1 = (String) inner.get(11);
					knrName2 = (String) inner.get(12);
					knrName3 = (String) inner.get(13);
					knrName4 = (String) inner.get(14);
					knrName5 = (String) inner.get(15);
					knrName6 = (String) inner.get(16);

					cmpHmKbn1 = (String) inner.get(17);
					cmpHmKbn2 = (String) inner.get(18);
					cmpHmKbn3 = (String) inner.get(19);
					cmpHmName1 = (String) inner.get(20);
					cmpHmName2 = (String) inner.get(21);
					cmpHmName3 = (String) inner.get(22);

					kmkName = (String) inner.get(1);
					hkmName = (String) inner.get(2);
					ukmName = (String) inner.get(4);
					ukmKbn = (String) inner.get(3);
				}
			}

			if (Util.isNullOrEmpty(knrKbn1) || "0".equals(knrKbn1) || Util.isNullOrEmpty(knrName1)) {
				knrKbn1 = "0";
				knrName1 = "C01025";
			}
			if (Util.isNullOrEmpty(knrKbn2) || "0".equals(knrKbn2) || Util.isNullOrEmpty(knrName2)) {
				knrKbn2 = "0";
				knrName2 = "C01027";
			}
			if (Util.isNullOrEmpty(knrKbn3) || "0".equals(knrKbn3) || Util.isNullOrEmpty(knrName3)) {
				knrKbn3 = "0";
				knrName3 = "C01029";
			}
			if (Util.isNullOrEmpty(knrKbn4) || "0".equals(knrKbn4) || Util.isNullOrEmpty(knrName4)) {
				knrKbn4 = "0";
				knrName4 = "C01031";
			}
			if (Util.isNullOrEmpty(knrKbn5) || "0".equals(knrKbn5) || Util.isNullOrEmpty(knrName5)) {
				knrKbn5 = "0";
				knrName5 = "C01033";
			}
			if (Util.isNullOrEmpty(knrKbn6) || "0".equals(knrKbn6) || Util.isNullOrEmpty(knrName6)) {
				knrKbn6 = "0";
				knrName6 = "C01035";
			}

			if (Util.isNullOrEmpty(cmpHmKbn1) || "0".equals(cmpHmKbn1) || Util.isNullOrEmpty(cmpHmName1)) {
				cmpHmKbn1 = "0";
				cmpHmName1 = "C01291";
			}
			if (Util.isNullOrEmpty(cmpHmKbn2) || "0".equals(cmpHmKbn2) || Util.isNullOrEmpty(cmpHmName2)) {
				cmpHmKbn2 = "0";
				cmpHmName2 = "C01292";
			}
			if (Util.isNullOrEmpty(cmpHmKbn3) || "0".equals(cmpHmKbn3) || Util.isNullOrEmpty(cmpHmName3)) {
				cmpHmKbn3 = "0";
				cmpHmName3 = "C01293";
			}

			if (Util.isNullOrEmpty(kmkKbn) || "0".equals(kmkKbn) || Util.isNullOrEmpty(kmkName)) {
				kmkKbn = "0";
				kmkName = "C00077";
			}
			if (Util.isNullOrEmpty(hkmKbn) || "0".equals(hkmKbn) || Util.isNullOrEmpty(hkmName)) {
				hkmKbn = "0";
				hkmName = "C00488";
			}
			if (Util.isNullOrEmpty(ukmKbn) || "0".equals(ukmKbn) || Util.isNullOrEmpty(ukmName)) {
				ukmKbn = "0";
				ukmName = "C00025";
			}
		} catch (IOException e) {
			ClientErrorHandler.handledException(e);
		}
	}

	private String knrKbn1;

	private String knrKbn2;

	private String knrKbn3;

	private String knrKbn4;

	private String knrKbn5;

	private String knrKbn6;

	private String knrName1;

	private String knrName2;

	private String knrName3;

	private String knrName4;

	private String knrName5;

	private String knrName6;

	private String cmpHmKbn1;

	private String cmpHmKbn2;

	private String cmpHmKbn3;

	private String cmpHmName1;

	private String cmpHmName2;

	private String cmpHmName3;

	private String kmkName;

	private String hkmName;

	private String ukmName;

	private String kmkKbn = "1";

	private String hkmKbn = "1";

	private String ukmKbn;

	/**
	 * @return String
	 */
	public String getKnrName1() {
		if ("0".equals(knrKbn1)) {
			return this.getWord(knrName1);
		}
		return knrName1;
	}

	/**
	 * @return String
	 */
	public String getKnrName2() {
		if ("0".equals(knrKbn2)) {
			return this.getWord(knrName2);
		}
		return knrName2;
	}

	/**
	 * @return String
	 */
	public String getKnrName3() {
		if ("0".equals(knrKbn3)) {
			return this.getWord(knrName3);
		}
		return knrName3;
	}

	/**
	 * @return String
	 */
	public String getKnrName4() {
		if ("0".equals(knrKbn4)) {
			return this.getWord(knrName4);
		}
		return knrName4;
	}

	/**
	 * @return String
	 */
	public String getKnrName5() {
		if ("0".equals(knrKbn5)) {
			return this.getWord(knrName5);
		}
		return knrName5;
	}

	/**
	 * @return String
	 */
	public String getKnrName6() {
		if ("0".equals(knrKbn6)) {
			return this.getWord(knrName6);
		}
		return knrName6;
	}

	/**
	 * @return String
	 */
	public String getCmpHmName1() {
		if ("0".equals(cmpHmKbn1)) {
			return this.getWord(cmpHmName1);
		}
		return cmpHmName1;
	}

	/**
	 * @return String
	 */
	public String getCmpHmName2() {
		if ("0".equals(cmpHmKbn2)) {
			return this.getWord(cmpHmName2);
		}
		return cmpHmName2;
	}

	/**
	 * @return String
	 */
	public String getCmpHmName3() {
		if ("0".equals(cmpHmKbn3)) {
			return this.getWord(cmpHmName3);
		}
		return cmpHmName3;
	}

	/**
	 * @return String
	 */
	public String getKmkName() {
		if ("0".equals(kmkKbn)) {
			return this.getWord(kmkName);
		}
		return kmkName;
	}

	/**
	 * @return String
	 */
	public String getHkmName() {
		if ("0".equals(hkmKbn)) {
			return this.getWord(hkmName);
		}
		return hkmName;
	}

	/**
	 * @return String
	 */
	public String getUkmName() {
		if ("0".equals(ukmKbn)) {
			return this.getWord(ukmName);
		}
		return ukmName;
	}
}
