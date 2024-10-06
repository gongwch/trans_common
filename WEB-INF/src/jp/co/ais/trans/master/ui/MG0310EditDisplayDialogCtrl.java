package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �ʉ݃��[�g�}�X�^ �_�C�A���O�R���g���[��
 */
public class MG0310EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ���[�g�}�X�^�_�C�A���O */
	protected MG0310EditDisplayDialog dialog;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0310RateMasterServlet";

	/** �����敪 */
	private boolean isUpdate;

	protected REFDialogCtrl ref1;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0310EditDisplayDialogCtrl(Frame parent, String titleid) {
		// ���[�g�}�X�^�_�C�A���O�̏�����
		dialog = new MG0310EditDisplayDialog(parent, true, this, titleid);
		// �t�H�[�J�X�̐ݒ�
		dialog.ctrlCurrency.getField().requestFocus();

		ref1 = new REFDialogCtrl(dialog.ctrlCurrency, dialog.getParentFrame());
		ref1.setColumnLabels("C00665", "C00881", "C00882");
		ref1.setTargetServlet("MG0300CurrencyMasterServlet");
		ref1.setTitleID(getWord("C01985"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				// map�̏�����
				Map keys = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Currency");
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		dialog.ctrlCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlCurrency.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlCurrency.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlCurrency.getValue());
					dialog.ctrlCurrency.getField().clearOldText();
					dialog.ctrlCurrency.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// fix bug: �m��{�^���������� UI �`�F�b�N�����s�̏ꍇ�́A
		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		try {
			dialog.dtOutLineBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		} catch (Exception e) {
			errorHandler(dialog, e, "E00009");
		}
	}

	/**
	 * �\��
	 * 
	 * @param isEnabledCurCode �ʉ݃R�[�h�G���A�ҏW��(true)�A�t��(false)
	 */
	void show(boolean isEnabledCurCode) {
		// ��ʂ�\������
		dialog.setVisible(true);
		// �ʉ݃R�[�h�̐ݒ�
		dialog.ctrlCurrency.setEnabled(isEnabledCurCode);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */
	void setValues(Map map) {
		// �ʉ݃R�[�h�̐ݒ�
		dialog.ctrlCurrency.setValue((String) map.get("curCode"));
		// �E�v�J�n���t�̐ݒ�
		dialog.dtOutLineBeginDate.setValue((Date) map.get("strDate"));
		// ���[�g�̐ݒ�
		dialog.numRate.setValue(((String) map.get("curRate")).replace(",", ""));
		// �ҏW���[�h�̂Ƃ��͒ʉ݃R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));

		// �ʉ݃{�^���̐ݒ�
		dialog.ctrlCurrency.getButton().setEnabled(!isUpdate);
		// �ʉ݃R�[�h�̐ݒ�
		dialog.ctrlCurrency.getField().setEditable(!isUpdate);
		dialog.dtOutLineBeginDate.getCalendar().setEditable(true);
		ref1.refreshName();

		// ������ʂ̃`�F�b�N
		if (!isUpdate) return;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �ʉ݃R�[�h�̐ݒ�
		addSendValues("beginCurCode", (String) map.get("curCode"));

		if (isUpdate) {
			dialog.dtOutLineBeginDate.setEditable(false);
			dialog.dtOutLineBeginDate.setEnabled(true);
			dialog.numRate.getField().requestFocus();
		}
	}

	boolean checkDialog() {
		// �ʉ݃R�[�h�����̓`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlCurrency.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00665");
			dialog.ctrlCurrency.requestTextFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �E�v�J�n���t�����̓`�F�b�N
		if (dialog.dtOutLineBeginDate.getValue() == null) {
			showMessage(dialog, "I00002", "C01247");
			dialog.dtOutLineBeginDate.getCalendar().requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// ���[�g�����̓`�F�b�N
		if (Util.isNullOrEmpty(dialog.numRate.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C01555");
			dialog.numRate.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// ���[�g����0.0001�`99,999,999,999.9999�ȊO�`�F�b�N
		if (!Util.isNullOrEmpty(dialog.numRate.getValue())) {
			BigDecimal num = new BigDecimal(dialog.numRate.getValue());
			if (num.compareTo(new BigDecimal("0.0001")) < 0 || num.compareTo(new BigDecimal("99999999999.9999")) > 0) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00065", "0.0001", "99,999,999,999.9999");
				dialog.numRate.requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// �E�v�J�n���t�����݂��Ă��܂��`�F�b�N
		if (!isUpdate && checkCode()) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlCurrency.getField().requestFocus();
			// �G���[��Ԃ�
			return false;
		}
		// ������Ԃ�
		return true;
	}

	/**
	 * ����
	 */
	void disposeDialog() {
		// �G���[������ꍇ�ɂ̓_�C�A���O����Ȃ�
		if (dialog.isSettle) {
			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (!this.checkDialog()) return;
			dialog.setVisible(!this.checkDialog());
		} else {
			// ��ʂ�߂�
			dialog.setVisible(false);
		}
	}

	/**
	 * �R�[�h�̃`�F�b�N
	 * 
	 * @return �`�F�b�N����
	 */
	boolean checkCode() {
		try { // ������ʂ̐ݒ�
			addSendValues("flag", "checkcode");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// �ʉ݃R�[�h�̐ݒ�
			addSendValues("curCode", dialog.ctrlCurrency.getValue());
			// �E�v�J�n���t�̐ݒ�
			addSendValues("strDate", DateUtil.toYMDHMSString(dialog.dtOutLineBeginDate.getValue()));

			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return false;
			}

			// ���ʂ��擾
			List result = super.getResultList();
			// ���ʂ�Ԃ�
			return (result.size() > 0);
		} catch (IOException ex) {
			// ����ɏ�������܂���ł���
			super.errorHandler(dialog, ex);
			return false;
		}
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
		// �ʉ݃R�[�h�̐ݒ�
		map.put("curCode", dialog.ctrlCurrency.getValue());
		// �E�v�J�n���t�̐ݒ�
		map.put("strDate", dialog.dtOutLineBeginDate.getValue());
		// ���[�g�̐ݒ�
		map.put("curRate", dialog.numRate.getValue());
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", getLoginUserCompanyCode());
		// ���ʂ�Ԃ�
		return map;
	}
}
