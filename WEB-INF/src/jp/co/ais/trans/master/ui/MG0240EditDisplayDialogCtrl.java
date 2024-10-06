package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �v���O�����}�X�^�_�C�A���O �R���g���[��
 */
public class MG0240EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �v���O�����}�X�^�_�C�A���O */
	protected MG0240EditDisplayDialog dialog;

	/** REF */
	protected REFDialogCtrl ref1;

	/** �V�X�e���敪�̃f�[�^ */
	private Map sysMap;

	/** �V�Kor�ҏW�t���O */
	private boolean isUpdate;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0240ProgramMasterServlet";
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	// �v���O�����}�X�^�_�C�A���O�̏�����
	public MG0240EditDisplayDialogCtrl(Frame parent, String titleid) {
		dialog = createDialog(parent, titleid);

		ref1 = new REFDialogCtrl(dialog.ctrlParentsProgramCode, dialog.getParentFrame());
		ref1.setColumnLabels("C00818", "C00820", "C00821");
		ref1.setTargetServlet(getServletName());
		ref1.setTitleID(getWord("C02147"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Program");
				return keys;
			}
		});

		dialog.ctrlParentsProgramCode.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlParentsProgramCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlParentsProgramCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlParentsProgramCode.getValue());
					dialog.ctrlParentsProgramCode.getField().clearOldText();
					dialog.ctrlParentsProgramCode.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));

		dialog.ctrlProgramCode.getField().requestFocus();
	}

	/**
	 * @param parent �e�R���e�i�[
	 * @param titleid �^�C�g��
	 * @return �_�C�A���O
	 */
	protected MG0240EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0240EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * �\��
	 * 
	 * @param isEnabledPrgCode �v���O�����R�[�h�G���A�ҏW��(true)�A�t��(false)
	 */
	void show(boolean isEnabledPrgCode) {
		dialog.setVisible(true);
		dialog.ctrlProgramCode.setEditable(isEnabledPrgCode);
	}

	/**
	 * @param data
	 */
	public void setSysMap(Map data) {
		sysMap = data;
		this.fillItemsToComboBox(dialog.ctrlSystemDivision.getComboBox(), sysMap);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */
	void setValues(Map map) {
		boolean isinsert = "insert".equals(map.get("flag"));
		dialog.rdoMenuOnly.setSelected(isinsert);
		dialog.ctrlLoadModuleFileName.setEditable(!isinsert);

		this.setComboBoxSelectedItem(dialog.ctrlSystemDivision.getComboBox(), (String) map.get("sysCode"));

		if (isinsert) {
			return;
		}

		dialog.ctrlProgramCode.setValue((String) map.get("prgCode"));
		dialog.ctrlProgramName.setValue((String) map.get("prgName"));
		dialog.ctrlProgramAbbreviationName.setValue((String) map.get("prgName_S"));
		dialog.ctrlProgramNameForSearch.setValue((String) map.get("prgName_K"));

		dialog.numAuthorityLevel.setValue((String) map.get("ken"));
		dialog.ctrlLoadModuleFileName.setValue((String) map.get("ldName"));

		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("menuKbn")));

		if (!boo) {
			dialog.ctrlLoadModuleFileName.setValue("");
		}

		dialog.ctrlLoadModuleFileName.setEditable(boo);
		dialog.ctrlComment.setValue((String) map.get("com"));
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		dialog.dtEndDate.setValue((Date) map.get("endDate"));
		dialog.ctrlParentsProgramCode.setValue((String) map.get("parentPrgCode"));
		dialog.rdoMenuOnly.setSelected(!boo);
		dialog.rdoScreenHaving.setSelected(boo);

		// �\������
		dialog.ctrlOrderDisplay.setValue((String) map.get("dispIndex"));

		ref1.refreshName();

		// �ҏW���[�h�̂Ƃ��̓v���O�����R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));

		dialog.ctrlSystemDivision.getComboBox().setEnabled(!isUpdate);
		if (isUpdate) {
			dialog.ctrlProgramName.getField().requestFocus();
		}

		dialog.ctrlProgramCode.getField().setEditable(!isUpdate);
		dialog.ctrlParentsProgramCode.getField().setEnabled(true);
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:����NG
	 * @throws TRequestException
	 * @throws IOException
	 */
	private boolean checkDialog() throws TRequestException, IOException {

		// �v���O�����R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlProgramCode.getValue())) {
			showMessage(dialog, "I00002", "C00818");
			dialog.ctrlProgramCode.requestFocus();
			return false;
		}

		if (!isUpdate && checkCode(dialog.ctrlProgramCode.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlProgramCode.requestFocus();
			return false;
		}

		// �V�X�e���敪�`�F�b�N
		if (Util.isNullOrEmpty(this.getComboBoxSelectedValue(dialog.ctrlSystemDivision.getComboBox()))) {
			showMessage(dialog, "I00002", "C00980");
			dialog.ctrlSystemDivision.getComboBox().requestFocus();
			return false;
		}

		// �v���O�������̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlProgramName.getValue())) {
			showMessage(dialog, "I00002", "C00819");
			dialog.ctrlProgramName.requestFocus();
			return false;
		}

		// �v���O�������̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlProgramAbbreviationName.getValue())) {
			showMessage(dialog, "I00002", "C00820");
			dialog.ctrlProgramAbbreviationName.requestFocus();
			return false;
		}

		// �v���O�����������̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlProgramNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00821");
			dialog.ctrlProgramNameForSearch.requestFocus();
			return false;
		}

		// �������x���`�F�b�N
		if (Util.isNullOrEmpty(dialog.numAuthorityLevel.getValue())) {
			showMessage(dialog, "I00002", "C00822");
			dialog.numAuthorityLevel.requestFocus();
			return false;
		}

		// ���[�h���W���[���t�@�C�����`�F�b�N(��ʎ��̂�)
		if (dialog.rdoScreenHaving.isSelected() && Util.isNullOrEmpty(dialog.ctrlLoadModuleFileName.getValue())) {
			showMessage(dialog, "I00002", "C00823");
			dialog.ctrlLoadModuleFileName.requestFocus();
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

		// �J�n�N�������I���N�������傫��
		if (!Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.getCalendar().requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �e�R�[�h
		if (Util.isNullOrEmpty(dialog.ctrlParentsProgramCode.getValue())) {
			showMessage(dialog, "I00002", "C00824");
			dialog.ctrlParentsProgramCode.requestTextFocus();
			return false;
		}

		// �\������
		if (dialog.ctrlOrderDisplay.isEmpty()) {
			showMessage(dialog, "I00002", "C02397");
			dialog.ctrlOrderDisplay.requestFocus();
			return false;
		}

		// ����
		return true;
	}

	/**
	 * ����
	 */
	protected void disposeDialog() {

		// �m��{�^������ �`�F�b�NOK�Ȃ����
		try {
			if (dialog.isSettle && !this.checkDialog()) {
				return;
			}

			dialog.setVisible(false);// ����

		} catch (Exception ex) {
			errorHandler(this.dialog, ex);
		}
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	protected boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	protected Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("sysCode", this.getComboBoxSelectedValue(dialog.ctrlSystemDivision.getComboBox()));
		map.put("prgCode", dialog.ctrlProgramCode.getValue());
		map.put("prgName", dialog.ctrlProgramName.getValue());
		map.put("prgName_S", dialog.ctrlProgramAbbreviationName.getValue());
		map.put("prgName_K", dialog.ctrlProgramNameForSearch.getValue());
		map.put("ken", dialog.numAuthorityLevel.getValue());
		map.put("com", dialog.ctrlComment.getValue());
		map.put("ldName", dialog.ctrlLoadModuleFileName.getValue());
		map.put("strDate", dialog.dtBeginDate.getValue());
		map.put("endDate", dialog.dtEndDate.getValue());
		map.put("parentPrgCode", dialog.ctrlParentsProgramCode.getValue());
		map.put("menuKbn", String.valueOf(dialog.rdoMenuOnly.isSelected() ? 0 : 1));
		// �\�������ǉ�
		map.put("dispIndex", dialog.ctrlOrderDisplay.getField().getText());
		// �[���f�[�^
		map.put("kaiCode", getLoginUserCompanyCode());

		return map;
	}

	/**
	 * ���̓R�[�h�̑��݃`�F�b�N
	 * 
	 * @param code
	 * @return true:���݂���
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected boolean checkCode(String code) throws IOException, TRequestException {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}

		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("prgCode", dialog.ctrlProgramCode.getValue());
		addSendValues("sysCode", this.getComboBoxSelectedValue(dialog.ctrlSystemDivision.getComboBox()));

		if (!request(getServletName())) {
			throw new TRequestException();
		}

		return !getResultList().isEmpty();
	}

	/**
	 * ���j���[�敪����
	 * 
	 * @param bln
	 */
	protected void checkCtrl(Boolean bln) {
		if (!bln) {
			dialog.ctrlLoadModuleFileName.setValue("");
		}
		dialog.ctrlLoadModuleFileName.setEditable(bln);
	}
}
