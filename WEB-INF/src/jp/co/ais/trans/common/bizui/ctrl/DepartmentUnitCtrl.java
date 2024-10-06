package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.util.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �g�D���j�b�g�A����p�R���g���[��
 */
public class DepartmentUnitCtrl extends TOrganizationUnitCtrl {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "InformationServlet";

	protected static final String FLAG_UPPER_DEP = "UpDep";

	protected static final String FLAG_DEP = "Dep";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �K�w���x�� */
	protected int intLevel = -1;

	/** ��ʕ��庰�� */
	protected String strUpDepCode = "";

	/** ��ʕ��喼 */
	protected String strUpDepName = "";

	/** ���庰�� */
	protected String strDepCode = "";

	/** ���喼 */
	protected String strDepName = "";

	/** ���ݑI�𒆂̊J���K�w���x�� */
	protected int nowLevel = 0;

	/** �Ȗڑ̌n�R�[�h */
	protected String itemSystemCode;

	/**
	 * ��ʕ���t���O
	 * 
	 * @return ��ʕ���t���O
	 */
	public String getUpperOrgFlag() {
		return FLAG_UPPER_DEP;

	}

	/**
	 * ����t���O
	 * 
	 * @return ����t���O
	 */
	public String getOrgFlag() {
		return FLAG_DEP;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param unit �g�D���j�b�g
	 */
	public DepartmentUnitCtrl(TOrganizationUnit unit) {
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
		// ��ʕ���
		unit.getUpperCodeField().setLangMessageID("C00719");
		// ����
		unit.getCodeField().setLangMessageID("C00467");
		// ���W�I�{�^��(�܂�)
		unit.getRdoInclude().setLangMessageID("C00461");
		// ���W�I�{�^���i�܂܂Ȃ��j
		unit.getRdoExclude().setLangMessageID("C00460");
		// �z������
		unit.getSubordinate().setLangMessageID("C00904");
	}

	/**
	 * ����������
	 */
	public void initPanel() {
		super.initPanel();

		try {
			// 1.�g�D�R�[�hcomboBox�쐬
			String[] orgCodes = InformationUtil.getOrganizationCodeList();
			unit.getOrganizationComboBox().getComboBox().setModel(orgCodes);

			// 2.�J�����x���̃f�t�H���g�I��
			setItemSystemCode("00");

		} catch (Exception e) {
			errorHandler(unit, e);
		}
	}

	/**
	 * ����𐧌䂷��
	 */
	public void changeUpper() {
		// ���.�K�w���x���擾
		int selLevel = unit.getHierarchicalLevel();

		// ���.�K�w���x�� = �J�����x��.�K�w���x���̏ꍇ�A���삵�Ȃ�
		if (selLevel == intLevel) {
			return;
		}

		// ��ʕ���͉��������͍ς݂̏ꍇ�A���剟���A���̏ꍇ�t
		boolean isEdit = !Util.isNullOrEmpty(unit.getUpperCodeField().getNotice().getValue());

		unit.getCodeField().getButton().setEnabled(isEdit);
		unit.getCodeField().getField().setEditable(isEdit);

		unit.getCodeField().setValue("");
		unit.getCodeField().setNoticeValue("");
	}

	/**
	 * ������擾����
	 * 
	 * @param tbuttonField
	 * @param btnFlag
	 */
	public void showRefDialog(TButtonField tbuttonField, String btnFlag) {
		try {

			// ����}�X�^�ꗗ�̏ꍇ
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(unit, REFDialogMasterCtrl.BMN_MST);

			// ��������R�[�h
			dialog.setBaseBmnCode(strDepCode);
			// �����K�w����
			dialog.setBaseDpkLvl(String.valueOf(intLevel));
			// �g�D�R�[�h
			dialog.setDpkSsk(unit.getOrganizationCode());
			// �z������(0:�܂� �A1:�܂܂Ȃ��A2:��ʕ��庰�ޑI���̏ꍇ)
			String kbn = FLAG_UPPER_DEP.equals(btnFlag) ? "2" : "1";

			dialog.setBmnKbn(kbn);
			// ��ʕ��庰��
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
			strUpDepCode = orgInfo.getUpperDepartmentCode(); // ��ʕ��庰��
			strUpDepName = orgInfo.getUpperDepartmentName(); // ��ʕ��喼
			strDepCode = orgInfo.getDepartmentCode(); // ���庰��
			strDepName = orgInfo.getDepartmentName(); // ���喼

			// ��ʊK�w���ِݒu
			if (intLevel > 0) {
				unit.getHierarchicalLevelComboBox().setSelectedIndex(intLevel);
			} else {
				unit.getHierarchicalLevelComboBox().setSelectedIndex(0);
			}

			// ��ʕ���̏����l�ݒu
			unit.getUpperCodeField().setEditable(false);
			unit.getUpperCodeField().getButton().setEnabled(false);
			unit.getUpperCodeField().setValue(strUpDepCode);
			unit.getUpperCodeField().setNoticeValue(strUpDepName);

			// ����̒l�ݒu
			unit.getCodeField().setEditable(false);
			unit.getCodeField().getButton().setEnabled(false);
			unit.getCodeField().setValue(strDepCode);
			unit.getCodeField().setNoticeValue(strDepName);

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

			// ��ʕ���̒l��ێ�
			String upperTextValue = unit.getUpperCodeField().getField().getText();
			String upperNoticeValue = unit.getUpperCodeField().getNotice().getText();
			boolean upperBol = unit.getUpperCodeField().isEditable();

			// ����̒l��ێ�
			String departTextValue = unit.getCodeField().getField().getText();
			String departNotice = unit.getCodeField().getNotice().getText();
			boolean departBol = unit.getCodeField().isEditable();

			// �����ł�
			super.showMessage(unit, "I00016", "C00060");

			// ���O�̃��x���ɖ߂�
			unit.getHierarchicalLevelComboBox().setSelectedIndex(this.nowLevel);

			unit.getUpperCodeField().getField().setText(upperTextValue);
			unit.getUpperCodeField().getNotice().setText(upperNoticeValue);

			unit.getCodeField().getField().setText(departTextValue);
			unit.getCodeField().getNotice().setText(departNotice);

			unit.getUpperCodeField().getField().setEditable(upperBol);
			unit.getUpperCodeField().getButton().setEnabled(upperBol);
			unit.getCodeField().getField().setEditable(departBol);
			unit.getCodeField().getButton().setEnabled(departBol);

			return;
		}

		// ��ʕ���ݒ�
		boolean isEditUpper = level > intLevel && level != 0;
		String upperCode = (level == intLevel) ? strUpDepCode : "";
		String upperName = (level == intLevel) ? strUpDepName : "";

		unit.getUpperCodeField().getField().setEditable(isEditUpper);
		unit.getUpperCodeField().getButton().setEnabled(isEditUpper);
		unit.getUpperCodeField().setValue(upperCode);
		unit.getUpperCodeField().setNoticeValue(upperName);

		// ����ݒ�
		boolean isEditDept = level == 0 && intLevel != 0;
		String deptCode = (level == intLevel) ? strDepCode : "";
		String deptName = (level == intLevel) ? strDepName : "";

		unit.getCodeField().getField().setEditable(isEditDept);
		unit.getCodeField().getButton().setEnabled(isEditDept);
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
	 * @param btnFlag ���庰��/��ʕ��庰�� �t���O
	 */
	public boolean setupName(TButtonField argBtnField, String btnFlag) {

		try {
			// ����R�[�h
			String strFieldCode = argBtnField.getValue();
			if (Util.isNullOrEmpty(strFieldCode)) {
				// �󕶎��Z�b�g
				argBtnField.setNoticeValue("");
				return true;
			}

			addSendValues("FLAG", "isCode"); // �p�����[�^
			addSendValues("type", btnFlag); // ���庰��/��ʕ��庰�ނ̋敪
			addSendValues("kaiCode", getLoginUserCompanyCode()); // ��ЃR�[�h
			addSendValues("userId", getLoginUserID()); // ���[�U�R�[�h

			// �g�D����
			String strOrganizationCode = unit.getOrganizationCode();
			addSendValues("organizationCode", strOrganizationCode);

			// ��ʂ̊K�w���ق��擾����
			String hierarchicalLevel = String.valueOf(unit.getHierarchicalLevel());
			addSendValues("panelLevel", hierarchicalLevel);

			// ��ʂ̏�ʕ��庰Ă��擾����
			String strUpCode = String.valueOf(unit.getUpperCodeField().getValue());
			addSendValues("upDepCode", strUpCode);

			addSendValues("Level", Util.avoidNull(intLevel)); // �J�����x��
			addSendValues("depCode", strDepCode); // �J�����庰��
			addSendValues("fieldCode", strFieldCode); // ��ʕ��庰��

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
				if (FLAG_UPPER_DEP.equals(btnFlag)) {
					unit.getCodeField().setValue("");
					unit.getCodeField().setNoticeValue("");
				}

				return true;
			} else {

				if (FLAG_UPPER_DEP.equals(btnFlag)) {
					super.showMessage(unit, "W00081", "C00719");
				} else if (FLAG_DEP.equals(btnFlag)) {
					super.showMessage(unit, "W00081", "C00467");
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

}
