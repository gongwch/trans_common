package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * MG0040���Z�R���g���[���}�X�^
 */
public class MG0040EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** GL�R���g���[���}�X�^�_�C�A���O */
	protected MG0040EditDisplayDialog dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0040GLControlMasterServlet";
	}

	/** �����敪 */

	protected boolean isUpdate;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0040EditDisplayDialogCtrl(Frame parent, String titleid) {
		// GL�R���g���[���}�X�^�_�C�A���O�̏�����
		dialog = new MG0040EditDisplayDialog(parent, true, this, titleid);
		dialog.numClosingAccountsStage.getField().setMaxLength(1);
		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		// ���Z�i�K�敪�̏�����
		dialog.rdoDo.setSelected(true);
		// ���Z�`�[���͋敪�̏�����
		dialog.rdoOneYear.setSelected(true);
		// �������ʎc���\���敪�̏�����
		dialog.rdoVisible.setSelected(true);
		// �]���փ��[�g�敪�̏�����
		dialog.rdoEndMonthRate.setSelected(true);

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
		// ��ЃR�[�h
		dialog.ctrlCompanyCode.setValue((String) map.get("kaiCode"));
		getKaiName((String) map.get("kaiCode"));
		// ���Z�i�K�敪
		String value = (String) map.get("ksdKbn");
		if ("0".equals(value)) {
			dialog.rdoDo.setSelected(false);
			dialog.rdoDonot.setSelected(true);
			dialog.numClosingAccountsStage.getField().setEditable(false);
		} else {
			dialog.rdoDonot.setSelected(false);
			dialog.rdoDo.setSelected(true);
			dialog.numClosingAccountsStage.setValue(value);
		}
		// ���Z�`�[���͋敪
		String i = (String) map.get("ksnNyuKbn");
		if ("0".equals(i)) {
			dialog.rdoOneYear.setSelected(true);
		} else if ("1".equals(i)) {
			dialog.rdoHalfYear.setSelected(true);
		} else if ("2".equals(i)) {
			dialog.rdoQuarter.setSelected(true);
		} else if ("3".equals(i)) {
			dialog.rdoMonthly.setSelected(true);
		}
		// �������ʎc���\���敪
		boolean boo2 = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("mtZanHyjKbn")));
		dialog.rdoVisible.setSelected(boo2);
		dialog.rdoNotVisible.setSelected(!boo2);
		// �]���փ��[�g�敪
		boolean boo3 = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("excRateKbn")));
		dialog.rdoEndMonthRate.setSelected(!boo3);
		dialog.rdoNextMonthBeginRate.setSelected(boo3);

		// �ҏW���[�h�̂Ƃ��͒ʉ݃R�[�h���ҏW�s�ɂȂ�
		isUpdate = "save".equals(map.get("flag"));
		dialog.ctrlCompanyCode.getField().setEditable(!isUpdate);
		dialog.rdoDo.requestFocus();

	}

	boolean checkDialog() {
		// ���Z�i�K�敪
		if (!dialog.rdoDo.isSelected() && !dialog.rdoDonot.isSelected()) {
			showMessage(dialog, "I00002", "C00718");
			dialog.rdoDo.requestFocus();
			return false;
		}
		if (dialog.rdoDo.isSelected() && Util.isNullOrEmpty(dialog.numClosingAccountsStage.getField().getText())) {
			showMessage(dialog, "I00002", "C00718");
			dialog.numClosingAccountsStage.requestFocus();
			return false;
		}

		String value = dialog.numClosingAccountsStage.getValue();
		if (dialog.rdoDo.isSelected() && !Util.isNullOrEmpty(value)
			&& (Integer.parseInt(value) < 1 || 9 < Integer.parseInt(value))) {
			showMessage(dialog, "W00065", 1, 9);
			dialog.numClosingAccountsStage.requestFocus();
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
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", dialog.ctrlCompanyCode.getValue());

		// ���Z�i�K�敪
		String value;
		if (dialog.rdoDo.isSelected()) {
			value = dialog.numClosingAccountsStage.getField().getText();
		} else {
			value = "0";
		}
		map.put("ksdKbn", value);

		String i;
		if (dialog.rdoOneYear.isSelected()) {
			i = "0";
		} else if (dialog.rdoHalfYear.isSelected()) {
			i = "1";
		} else if (dialog.rdoQuarter.isSelected()) {
			i = "2";
		} else {
			i = "3";
		}
		map.put("ksnNyuKbn", i);
		// ���Z�`�[���͋敪

		// �������ʎc���\���敪
		map.put("mtZanHyjKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoNotVisible.isSelected() == false)));
		// �]���փ��[�g�敪
		map.put("excRateKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoEndMonthRate.isSelected() == false)));
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String code) {
		try {
			// �R�[�h������
			if (Util.isNullOrEmpty(code)) {
				return false;
			}
			// ������ʂ̐ݒ�
			addSendValues("flag", "checkcode");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", getLoginUserCompanyCode());

			if (!request(getServletName())) {
				// �T�[�o���̃G���[�ꍇ
				errorHandler(dialog);
			}

			// ���ʂ��擾
			List result = super.getResultList();
			// ���ʂ�Ԃ�
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog);
			return false;
		}
	}

	/**
	 * 
	 */
	public void selectedSet() {
		boolean boo = dialog.rdoDo.isSelected();
		if (boo) {
			dialog.numClosingAccountsStage.setEditable(boo);
		} else {
			dialog.numClosingAccountsStage.setValue("");
			dialog.numClosingAccountsStage.setEditable(boo);
		}
	}

	void getKaiName(String kaiCode) {
		try {
			addSendValues("flag", "findone");
			addSendValues("kaiCode", kaiCode);

			if (!request("MG0010EnvironmentalSettingMasterServlet")) {
				// �T�[�o���̃G���[�ꍇ
				errorHandler(dialog);
			}

			List result = getResultList();
			if (result != null && result.size() > 0) {
				// ��З���
				String name_S = (String) ((List) result.get(0)).get(2);
				dialog.txtCompanyName.setText(name_S);
			}
		} catch (IOException e) {
			errorHandler(dialog);
		}
	}

}
