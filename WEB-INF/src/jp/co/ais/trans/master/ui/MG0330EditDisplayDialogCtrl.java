package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �`�[��ʃ}�X�^�_�C�A���O �R���g���[��
 */
public class MG0330EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �ʉ݃}�X�^�_�C�A���O */
	protected MG0330EditDisplayDialog dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0330SlipTypeMasterServlet";
	}

	protected boolean isUpdate = false;

	protected REFDialogCtrl ref1;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleID
	 */
	MG0330EditDisplayDialogCtrl(Frame parent, String titleID) {
		dialog = new MG0330EditDisplayDialog(parent, true, this, titleID);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		ref1 = new REFDialogCtrl(dialog.ctrlSystemDivision, dialog.getParentFrame());
		ref1.setColumnLabels("C00980", "C00833", "C00834");
		ref1.setTargetServlet("MG0320SystemDivisionMasterServlet");
		ref1.setTitleID(getWord("C02354"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "SystemDivision");
				return keys;
			}
		});

		dialog.ctrlSystemDivision.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlSystemDivision.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlSystemDivision.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlSystemDivision.getValue());
					dialog.ctrlSystemDivision.getField().clearOldText();
					dialog.ctrlSystemDivision.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		dialog.ctrlSlipTypeCode.getField().requestFocus();
	}

	private Map dataKbnMap;

	/**
	 * @param map
	 */
	public void setDataKbnMap(Map map) {
		this.dataKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlDataDivision.getComboBox(), dataKbnMap);
	}

	private Map taSysKbnMap;

	/**
	 * @param map
	 */
	public void setTaSysKbnMap(Map map) {
		this.taSysKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlAnotherSystemDivision.getComboBox(), taSysKbnMap);
	}

	private Map datSaiBanFlgMap;

	/**
	 * @param map
	 */
	public void setDatSaiBanFlgMap(Map map) {
		this.datSaiBanFlgMap = map;

		this.fillItemsToComboBox(dialog.ctrlSlipNumberCollectFlag.getComboBox(), datSaiBanFlgMap);
	}

	private Map taniMap;

	/**
	 * @param map
	 */
	public void setTaniMap(Map map) {
		this.taniMap = map;

		this.fillItemsToComboBox(dialog.ctrlUnitReceipt.getComboBox(), taniMap);
	}

	private Map zeiKbnMap;

	/**
	 * @param map
	 */
	public void setZeiKbnMap(Map map) {
		this.zeiKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlConsumptionTaxCalculationDivision.getComboBox(), zeiKbnMap);
	}

	private Map swkInKbnMap;

	/**
	 * @param map
	 */
	public void setSwkInKbnMap(Map map) {
		this.swkInKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlJournalizingInterfaceDivision.getComboBox(), swkInKbnMap);

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
		this.setComboBoxSelectedItem(dialog.ctrlDataDivision.getComboBox(), (String) map.get("dataKbn"));
		this.setComboBoxSelectedItem(dialog.ctrlAnotherSystemDivision.getComboBox(), (String) map.get("taSysKbn"));
		this.setComboBoxSelectedItem(dialog.ctrlSlipNumberCollectFlag.getComboBox(), (String) map.get("datSaiBanFlg"));
		this.setComboBoxSelectedItem(dialog.ctrlUnitReceipt.getComboBox(), (String) map.get("tani"));
		this.setComboBoxSelectedItem(dialog.ctrlConsumptionTaxCalculationDivision.getComboBox(), (String) map
			.get("zeiKbn"));
		this.setComboBoxSelectedItem(dialog.ctrlJournalizingInterfaceDivision.getComboBox(), (String) map
			.get("swkInKbn"));

		dialog.ctrlSlipTypeCode.setValue((String) map.get("denSyuCode"));
		dialog.ctrlSystemDivision.setValue((String) map.get("sysKbn"));
		dialog.ctrlSlipTypeName.setValue((String) map.get("denSyuName"));
		dialog.ctrlSlipTypeAbbreviatedName.setValue((String) map.get("denSyuName_S"));
		dialog.ctrlSlipTypeNameForSearch.setValue((String) map.get("denSyuName_K"));
		// �ҏW���[�h�̂Ƃ��͓E�v�R�[�h�A�E�v�敪�Ƃ��ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));

		dialog.ctrlSlipTypeCode.setEditable(isUpdate == false);

		ref1.refreshName();
		if (isUpdate) {
			dialog.ctrlSystemDivision.getField().requestFocus();
		}

	}

	boolean checkDialog() {
		try {// �`�[��ʃ`�F�b�N
			if (Util.isNullOrEmpty(dialog.ctrlSlipTypeCode.getValue())) {
				showMessage(dialog, "I00002", "C00837");
				dialog.ctrlSlipTypeCode.requestFocus();
				return false;
			}

			if (!isUpdate && checkCode(dialog.ctrlSlipTypeCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlSlipTypeCode.requestFocus();
				return false;
			}

			// �V�X�e���敪�R�[�h
			if (Util.isNullOrEmpty(dialog.ctrlSystemDivision.getValue())) {
				showMessage(dialog, "I00002", "C00217");
				dialog.ctrlSystemDivision.requestTextFocus();
				return false;
			}

			// �`�[��ʖ��̃R�[�h
			if (Util.isNullOrEmpty(dialog.ctrlSlipTypeName.getValue())) {
				showMessage(dialog, "I00002", "C00838");
				dialog.ctrlSlipTypeName.requestFocus();
				return false;
			}
			// �`�[��ʗ���
			if (Util.isNullOrEmpty(dialog.ctrlSlipTypeAbbreviatedName.getValue())) {
				showMessage(dialog, "I00002", "C00839");
				dialog.ctrlSlipTypeAbbreviatedName.requestFocus();
				return false;
			}
			// ���[�^�C�g��
			if (Util.isNullOrEmpty(dialog.ctrlSlipTypeNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C02757");
				dialog.ctrlSlipTypeNameForSearch.requestFocus();
				return false;
			}
			// �ް��敪
			if (Util.isNullOrEmpty(dialog.ctrlDataDivision.getComboBox().getSelectedItem().toString())) {
				showMessage(dialog, "I00002", "C02047");
				dialog.ctrlDataDivision.getComboBox().requestFocus();
				return false;
			}
			// �`�[�ԍ��̔ԕ��@
			if (Util.isNullOrEmpty(dialog.ctrlSlipNumberCollectFlag.getComboBox().getSelectedItem().toString())) {
				showMessage(dialog, "I00002", "C01256");
				dialog.ctrlSlipNumberCollectFlag.getComboBox().requestFocus();
				return false;
			}
			// ���V�X�e���敪
			if (Util.isNullOrEmpty(this.getComboBoxSelectedValue(dialog.ctrlAnotherSystemDivision.getComboBox()))) {
				showMessage(dialog, "I00002", "C01221");
				dialog.ctrlAnotherSystemDivision.getComboBox().requestFocus();
				return false;
			}
			// ����P��
			if (Util.isNullOrEmpty(dialog.ctrlUnitReceipt.getComboBox().getSelectedItem().toString())) {
				showMessage(dialog, "I00002", "C00018");
				dialog.ctrlUnitReceipt.getComboBox().requestFocus();
				return false;
			}
			// ����Ōv�Z�敪
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxCalculationDivision.getComboBox().getSelectedItem()
				.toString())) {
				showMessage(dialog, "I00002", "C00287");
				dialog.ctrlConsumptionTaxCalculationDivision.getComboBox().requestFocus();
				return false;
			}
			// �d��C���^�[�t�F�[�X�敪
			if (Util.isNullOrEmpty(dialog.ctrlJournalizingInterfaceDivision.getComboBox().getSelectedItem().toString())) {
				showMessage(dialog, "I00002", "C00299");
				dialog.ctrlJournalizingInterfaceDivision.getComboBox().requestFocus();
				return false;
			}
			return true;
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
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
		map.put("kaiCode", getLoginUserCompanyCode());
		map.put("dataKbn", this.getComboBoxSelectedValue(dialog.ctrlDataDivision.getComboBox()));
		map.put("taSysKbn", this.getComboBoxSelectedValue(dialog.ctrlAnotherSystemDivision.getComboBox()));
		map.put("datSaiBanFlg", this.getComboBoxSelectedValue(dialog.ctrlSlipNumberCollectFlag.getComboBox()));
		map.put("tani", this.getComboBoxSelectedValue(dialog.ctrlUnitReceipt.getComboBox()));
		map.put("zeiKbn", this.getComboBoxSelectedValue(dialog.ctrlConsumptionTaxCalculationDivision.getComboBox()));
		map.put("swkInKbn", this.getComboBoxSelectedValue(dialog.ctrlJournalizingInterfaceDivision.getComboBox()));
		map.put("denSyuCode", dialog.ctrlSlipTypeCode.getValue());
		map.put("sysKbn", dialog.ctrlSystemDivision.getValue());
		map.put("denSyuName", dialog.ctrlSlipTypeName.getValue());
		map.put("denSyuName_S", dialog.ctrlSlipTypeAbbreviatedName.getValue());
		map.put("denSyuName_K", dialog.ctrlSlipTypeNameForSearch.getValue());

		return map;
	}

	boolean checkCode(String code) throws IOException {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("denSyuCode", code);

		if (!request(getServletName())) {
			errorHandler(dialog);
			return true;
		}

		List result = super.getResultList();
		return (result.size() > 0);
	}
}
