package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.util.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �g�D���j�b�g�A��Зp�R���g���[��
 */
public class CompanyUnitCtrl extends TOrganizationUnitCtrl {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "InformationServlet";

	protected static final String FLAG_UPPER_COMPANY = "UpCompany";

	protected static final String FLAG_COMPANY = "Company";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �K�w���x�� */
	protected int intLevel = -1;

	/** ��ʉ�к��� */
	protected String strUpCompanyCode = "";

	/** ��ʉ�Ж� */
	protected String strUpCompanyName = "";

	/** Owner Company���� */
	protected String strCompanyCode = "";

	/** Owner Company�� */
	protected String strCompanyName = "";

	/** ���ݑI�𒆂̊J���K�w���x�� */
	protected int nowLevel = 0;

	/** �Ȗڑ̌n�R�[�h */
	protected String itemSystemCode;

	/**
	 * ��ʉ�Ѓt���O
	 * 
	 * @return ��ʉ�Ѓt���O
	 */
	public String getUpperOrgFlag() {
		return FLAG_UPPER_COMPANY;

	}

	/**
	 * Owner Company�t���O
	 * 
	 * @return Owner Company�t���O
	 */
	public String getOrgFlag() {
		return FLAG_COMPANY;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param unit �g�D���j�b�g
	 */
	public CompanyUnitCtrl(TOrganizationUnit unit) {
		super(unit);
	}

	/**
	 * �e�R���|�[�l���g�Ƀ��b�Z�[�WID��ݒ�
	 */
	public void initLangMessageID() {

		// �o�͒P��
		unit.getOutputUnitPanel().setLangMessageID("C01159");
		// �g�D�R�[�h
		unit.getOrganizationComboBox().setLangMessageID("C00335");
		// �K�w���x��
		unit.getHierarchicalLevelComboBox().setLangMessageID("C00060");
		// ��ʉ��
		unit.getUpperCodeField().setLangMessageID("C01487");
		// Owner Company
		unit.getCodeField().setLangMessageID("C01436");
		// ���W�I�{�^��(�܂�)
		unit.getRdoInclude().setLangMessageID("C00461");
		// ���W�I�{�^���i�܂܂Ȃ��j
		unit.getRdoExclude().setLangMessageID("C00460");
		// �z�����
		unit.getSubordinate().setLangMessageID("C01281");
	}

	/**
	 * ����������
	 */
	public void initPanel() {
		super.initPanel();

		try {

			// 1.�g�D�R�[�h�����l�擾
			String[] orgCodes = initOrganizationCode();
			unit.getOrganizationComboBox().getComboBox().setModel(orgCodes);

			// 2.�J�����x���̃f�t�H���g�I��
			setItemSystemCode("00");

		} catch (Exception e) {
			errorHandler(unit, e);
		}
	}

	/**
	 * ��Ђ𐧌䂷��
	 */
	public void changeUpper() {
		// ���.�K�w���x���擾
		int selLevel = unit.getHierarchicalLevel();

		// ���.�K�w���x�� = �J�����x��.�K�w���x���̏ꍇ�A���삵�Ȃ�
		if (selLevel == intLevel) {
			return;
		}

		// ��ʉ�Ђ͉��������͍ς݂̏ꍇ�AOwner Company�����A���̏ꍇ�t
		boolean isEdit = !Util.isNullOrEmpty(unit.getUpperCodeField().getNotice().getValue());

		unit.getCodeField().getButton().setEnabled(isEdit);
		unit.getCodeField().getField().setEditable(isEdit);

		unit.getCodeField().setValue("");
		unit.getCodeField().setNoticeValue("");
	}

	/**
	 * Owner Company���擾����
	 * 
	 * @param tbuttonField
	 * @param btnFlag
	 */
	public void showRefDialog(TButtonField tbuttonField, String btnFlag) {
		try {

			// ���ݒ�}�X�^�ꗗ�̏ꍇ
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(unit, REFDialogMasterCtrl.ENV_MST);

			// ������ЃR�[�h
			dialog.setBaseBmnCode(strCompanyCode);
			// �����K�w����
			dialog.setBaseDpkLvl(String.valueOf(intLevel));
			// �g�D�R�[�h
			dialog.setDpkSsk(unit.getOrganizationCode());
			// �z�����(0:�܂� �A1:�܂܂Ȃ��A2:��ʉ�к��ޑI���̏ꍇ)
			String kbn = FLAG_UPPER_COMPANY.equals(btnFlag) ? "2" : "1";

			dialog.setBmnKbn(kbn);
			// ��ʉ�к���
			dialog.setUpBmnCode(unit.getUpperCodeField().getValue());
			// �K�w����
			dialog.setDpkLvl(String.valueOf(unit.getHierarchicalLevel()));

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(tbuttonField.getValue())) {
				dialog.setCode(String.valueOf(tbuttonField.getValue()));
				dialog.searchData(false);
			}

			dialog.show();

			if (dialog.isSettle()) {
				String[] info = dialog.getCurrencyInfo();
				tbuttonField.setValue(info[0]);
				tbuttonField.setNoticeValue(info[1]);
			}

			tbuttonField.getField().requestFocus(true);

		} catch (Exception e) {
			errorHandler(unit, e);
		}
	}

	/**
	 * �Ȗڑ̌n�R�[�h�̃Z�b�g
	 * 
	 * @param code �Ȗڑ̌n�R�[�h
	 */
	public void setItemSystemCode(String code) {
		this.itemSystemCode = code;

		TComboBox combo = unit.getOrganizationComboBox().getComboBox();
		if (combo.getItemCount() != 0) {
			combo.setSelectedIndex(0);
		}

		changeOrgCode();
	}

	/**
	 * �g�D���ރR���{�{�b�N�XEvent�����B<br>
	 */
	public boolean changeOrgCode() {
		try {

			// �g�D�R�[�h
			String orgCode = unit.getOrganizationCode();

			OrganizationInfo orgInfo = InformationUtil.getOrganizationInfo(itemSystemCode, orgCode);

			intLevel = orgInfo.getDisplayLevel(); // �K�w����
			strUpCompanyCode = orgInfo.getUpperDepartmentCode(); // ��ʉ�к���
			strUpCompanyName = orgInfo.getUpperDepartmentName(); // ��ʉ�Ж�
			strCompanyCode = orgInfo.getDepartmentCode(); // Owner Company����
			strCompanyName = orgInfo.getDepartmentName(); // Owner Company��

			// ��ʊK�w���ِݒu
			if (intLevel > 0) {
				unit.getHierarchicalLevelComboBox().setSelectedIndex(intLevel);
			} else {
				unit.getHierarchicalLevelComboBox().setSelectedIndex(0);
			}

			// ��ʉ�Ђ̏����l�ݒu
			unit.getUpperCodeField().setEditable(false);
			unit.getUpperCodeField().getButton().setEnabled(false);
			unit.getUpperCodeField().setValue(strUpCompanyCode);
			unit.getUpperCodeField().setNoticeValue(strUpCompanyName);

			// Owner Company�̒l�ݒu
			unit.getCodeField().setEditable(false);
			unit.getCodeField().getButton().setEnabled(false);
			unit.getCodeField().setValue(strCompanyCode);
			unit.getCodeField().setNoticeValue(strCompanyName);

			if (intLevel == -1) {
				unit.getCodeField().setEditable(true);
				unit.getCodeField().getButton().setEnabled(true);
			}

			return true;
		} catch (Exception e) {
			errorHandler(unit, e);

			return false;
		}
	}

	/**
	 * �K�w���x���ύX
	 */
	public void changeHierarchicalLevelItem() {

		int level = unit.getHierarchicalLevel();

		if (level < intLevel) {

			// ��ʉ�Ђ̒l��ێ�
			String upperTextValue = unit.getUpperCodeField().getField().getText();
			String upperNoticeValue = unit.getUpperCodeField().getNotice().getText();
			boolean upperBol = unit.getUpperCodeField().isEditable();

			// Owner Company�̒l��ێ�
			String companyTextValue = unit.getCodeField().getField().getText();
			String companyNotice = unit.getCodeField().getNotice().getText();
			boolean companyBol = unit.getCodeField().isEditable();

			// �����ł�
			super.showMessage(unit, "I00016", "C00060");

			// ���O�̃��x���ɖ߂�
			unit.getHierarchicalLevelComboBox().setSelectedIndex(this.nowLevel);

			unit.getUpperCodeField().getField().setText(upperTextValue);
			unit.getUpperCodeField().getNotice().setText(upperNoticeValue);

			unit.getCodeField().getField().setText(companyTextValue);
			unit.getCodeField().getNotice().setText(companyNotice);

			unit.getUpperCodeField().getField().setEditable(upperBol);
			unit.getUpperCodeField().getButton().setEnabled(upperBol);
			unit.getCodeField().getField().setEditable(companyBol);
			unit.getCodeField().getButton().setEnabled(companyBol);

			return;
		}

		// ��ʉ�Аݒ�
		boolean isEditUpper = level > intLevel && level != 0;
		String upperCode = (level == intLevel) ? strUpCompanyCode : "";
		String upperName = (level == intLevel) ? strUpCompanyName : "";

		unit.getUpperCodeField().getField().setEditable(isEditUpper);
		unit.getUpperCodeField().getButton().setEnabled(isEditUpper);
		unit.getUpperCodeField().setValue(upperCode);
		unit.getUpperCodeField().setNoticeValue(upperName);

		// Owner Company�ݒ�
		boolean isEditCompanyt = level == 0 && intLevel != 0;
		String deptCode = (level == intLevel) ? strCompanyCode : "";
		String deptName = (level == intLevel) ? strCompanyName : "";

		unit.getCodeField().getField().setEditable(isEditCompanyt);
		unit.getCodeField().getButton().setEnabled(isEditCompanyt);
		unit.getCodeField().setValue(deptCode);
		unit.getCodeField().setNoticeValue(deptName);

		unit.getUpperCodeField().getField().pushOldText();
		unit.getCodeField().getField().pushOldText();

		this.nowLevel = level;
	}

	/**
	 * ���̂�ݒ肷��<br>
	 * �Y���R�[�h�ɑ΂��闪�̂������ꍇ�A�Ԃ�lfalse
	 * 
	 * @param argBtnField �Ώۃt�B�[���h
	 * @param btnFlag Owner Company����/��ʉ�к��� �t���O
	 */
	public boolean setupName(TButtonField argBtnField, String btnFlag) {

		try {
			// Owner Company�R�[�h
			String strFieldCode = argBtnField.getValue();
			if (Util.isNullOrEmpty(strFieldCode)) {
				// �󕶎��Z�b�g
				argBtnField.setNoticeValue("");
				return true;
			}

			addSendValues("FLAG", "isCode"); // �p�����[�^
			addSendValues("type", btnFlag); // Owner Company����/��ʉ�к��ނ̋敪
			addSendValues("kaiCode", getLoginUserCompanyCode()); // Owner Company�R�[�h
			addSendValues("userId", getLoginUserID()); // ���[�U�R�[�h

			// �g�D����
			String strOrganizationCode = unit.getOrganizationCode();
			addSendValues("organizationCode", strOrganizationCode);

			// ��ʂ̊K�w���ق��擾����
			String hierarchicalLevel = String.valueOf(unit.getHierarchicalLevel());
			addSendValues("panelLevel", hierarchicalLevel);

			// ��ʂ̏�ʉ�ЃR�[�h���擾����
			String strUpCode = String.valueOf(unit.getUpperCodeField().getValue());
			addSendValues("upDepCode", strUpCode);

			addSendValues("Level", Util.avoidNull(intLevel)); // �J�����x��
			addSendValues("depCode", strCompanyCode); // �J����к���
			addSendValues("fieldCode", strFieldCode); // ��ʉ�к���

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				// ����ɏ�������܂���ł���
				errorHandler(unit);

				argBtnField.clearOldText();
				argBtnField.setNoticeValue("");

				return false;
			}

			// �f�[�^�擾
			String name = getResult().get("key");
			if (!Util.isNullOrEmpty(name)) {

				// ���̂��Z�b�g
				argBtnField.setNoticeValue(name);
				if (FLAG_UPPER_COMPANY.equals(btnFlag)) {
					unit.getCodeField().setValue("");
					unit.getCodeField().setNoticeValue("");
				}

				return true;
			} else {

				if (FLAG_UPPER_COMPANY.equals(btnFlag)) {
					super.showMessage(unit, "W00081", "C01487");
				} else if (FLAG_COMPANY.equals(btnFlag)) {
					super.showMessage(unit, "W00081", "C01436");
				}

				argBtnField.clearOldText();
				argBtnField.setNoticeValue("");
				argBtnField.requestTextFocus();

				return false;
			}

		} catch (IOException e) {

			// ����ɏ�������܂���ł���
			errorHandler(unit, e);

			argBtnField.clearOldText();
			argBtnField.setNoticeValue("");

			return false;
		}
	}

	/**
	 * �g�D�R�[�h�����l�擾
	 * 
	 * @return �g�D�R�[�h���X�g
	 */
	protected String[] initOrganizationCode() {
		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("FLAG", "CmpOrganizationCode");

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				// ����ɏ�������܂���ł���
				errorHandler(unit);
			}

			// �g�D���ނ�ݒ肷��
			return StringUtil.toArrayFromDelimitString(getResult().get("orgCodes"));

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(unit, e);
		}
		return null;
	}

}
