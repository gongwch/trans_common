package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * 
 */
public class MG0120EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �E�v�}�X�^�_�C�A���O */
	protected MG0120EditDisplayDialog dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0120MemoMasterServlet";
	}

	/** �����敪 */
	protected boolean isUpdate = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleID
	 */
	MG0120EditDisplayDialogCtrl(Frame parent, String titleID) {
		// �E�v�}�X�^�_�C�A���O�̏�����
		dialog = new MG0120EditDisplayDialog(parent, true, this, titleID);

		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});
		// �E�v�敪�̐ݒ�
		dialog.rdoSlipMemo.setSelected(true);
		dialog.rdoSlipMemo.requestFocus();

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	// map�̏�����
	private Map dataKbnMap;

	/**
	 * @param map
	 */
	public void setDataKbnMap(Map map) {
		this.dataKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlDataDivision.getComboBox(), dataKbnMap);
	}

	/**
	 * �\��
	 */
	void show() {
		// ��ʂ�\������
		dialog.setVisible(true);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */

	void setValues(Map map) {
		// �ȖڃR�[�h�̐ݒ�
		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("tekKbn")));
		dialog.rdoSlipMemo.setSelected(!boo);
		dialog.rdoRowMemo.setSelected(boo);
		// �ް��敪�̐ݒ�
		this.setComboBoxSelectedItem(dialog.ctrlDataDivision.getComboBox(), (String) map.get("dataKbn"));
		// �E�v�R�[�h�̐ݒ�
		dialog.ctrlMemoCode.setValue((String) map.get("tekCode"));
		// �E�v���̂̐ݒ�
		dialog.ctrlMemoName.setValue((String) map.get("tekName"));
		// �E�v�������̂̐ݒ�
		dialog.ctrlMemoNameForSearch.setValue((String) map.get("tekName_K"));
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͓E�v�R�[�h�A�E�v�敪�Ƃ��ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		if (isUpdate) {
			dialog.ctrlDataDivision.getComboBox().requestFocus();
		}

		dialog.ctrlMemoCode.setEditable(!isUpdate);
		dialog.rdoSlipMemo.setEnabled(!isUpdate);
		dialog.rdoRowMemo.setEnabled(!isUpdate);
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

		// �E�v�敪�`�F�b�N

		if (!dialog.rdoSlipMemo.isSelected() && !dialog.rdoRowMemo.isSelected()) {
			showMessage(dialog, "I00003", "C00568");
			dialog.rdoSlipMemo.requestFocus();
			return false;
		}

		// �f�[�^�敪

		if (Util.isNullOrEmpty(dialog.ctrlDataDivision.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C00567");
			dialog.ctrlDataDivision.getComboBox().requestFocus();
			return false;
		}

		// �E�v�R�[�h

		if (Util.isNullOrEmpty(dialog.ctrlMemoCode.getValue())) {
			showMessage(dialog, "I00002", "C00564");
			dialog.ctrlMemoCode.requestFocus();
			return false;
		}

		if (!isUpdate
			&& checkCode(String.valueOf(BooleanUtil.toInt(dialog.rdoRowMemo.isSelected())), dialog.ctrlMemoCode
				.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlMemoCode.requestFocus();
			return false;
		}
		// �E�v���̃`�F�b�N

		if (Util.isNullOrEmpty(dialog.ctrlMemoName.getValue())) {
			showMessage(dialog, "I00002", "C00565");
			dialog.ctrlMemoName.requestFocus();
			return false;
		}

		// �E�v�������̃`�F�b�N

		if (Util.isNullOrEmpty(dialog.ctrlMemoNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00566");
			dialog.ctrlMemoNameForSearch.requestFocus();
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
		if (!Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue())) {
			showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.getCalendar().requestFocus();
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
		// �E�v�敪�̐ݒ�
		map.put("tekKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoRowMemo.isSelected())));
		// �ް��敪�̐ݒ�
		map.put("dataKbn", this.getComboBoxSelectedValue(dialog.ctrlDataDivision.getComboBox()));
		// �E�v�R�[�h�̐ݒ�
		map.put("tekCode", dialog.ctrlMemoCode.getValue());
		// �E�v���̂̐ݒ�
		map.put("tekName", dialog.ctrlMemoName.getValue());
		// �E�v�������̂̐ݒ�
		map.put("tekName_K", dialog.ctrlMemoNameForSearch.getValue());
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String tekKbn, String tekCode) {
		try {
			// �E�v�敪������
			if (Util.isNullOrEmpty(tekKbn) && Util.isNullOrEmpty(tekCode)) {
				return false;
			}
			// ������ʂ̐ݒ�
			addSendValues("flag", "checkcode");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// �E�v�敪�̐ݒ�
			addSendValues("tekKbn", tekKbn);
			// �E�v�R�[�h�̐ݒ�
			addSendValues("tekCode", tekCode);

			if (!request(getServletName())) {
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
