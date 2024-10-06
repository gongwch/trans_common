package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TComboBox.TTextValue;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ��I���F����\���F�R���g���[��
 * 
 * @author AIS
 */
public class TManagementComboMultiUnitController extends TController {

	/** �t�B�[���h */
	protected TManagementComboMultiUnit field;

	/**
	 * @param field �t�B�[���h
	 */
	public TManagementComboMultiUnitController(TManagementComboMultiUnit field) {
		this.field = field;
		init();
	}

	/**
	 * �R���{�{�b�N�X�̏�����
	 */
	protected void init() {
		// ������Ԑݒ�
		setSelectedAngles(field.angles);
	}

	@Override
	public void start() {
		//

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �Ǘ�������Z�߂ĕԂ�
	 * 
	 * @return �Ǘ�������Z�߂ĕԂ�
	 */
	public List<ManagementAngle> getSelectedAngles() {
		List<ManagementAngle> list = new ArrayList<ManagementAngle>();
		for (TComboBox cbo : field.combos) {
			list.add((ManagementAngle) cbo.getSelectedItemValue());
		}
		return list;
	}

	/**
	 * @return true:�g�p�\�̊Ǘ����ڂ��I�����ꂽ����
	 */
	public boolean isLastSelected() {
		for (TComboBox cbo : field.combos) {
			if (!cbo.isEnabled()) {
				continue;
			}

			ManagementAngle angle = (ManagementAngle) cbo.getSelectedItemValue();
			if (ManagementAngle.NONE == angle) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * �g�p�\�̊Ǘ����ڂɃt�H�[�J�X�𓖂Ă�
	 */
	public void requestLastFocus() {
		for (TComboBox cbo : field.combos) {
			if (!cbo.isEnabled()) {
				continue;
			}

			cbo.requestFocus();
			return;
		}
	}

	/**
	 * �Ǘ�������ݒ肷��
	 * 
	 * @param list �Ǘ�����
	 */
	public void setSelectedAngles(List<ManagementAngle> list) {
		if (list == null || list.isEmpty()) {
			return;
		}

		field.angles = list;

		boolean canDrillDown = false;

		for (int i = 0; i < Math.min(field.combos.size(), field.combos.size()); i++) {
			ManagementAngle angle = field.angles.get(i);
			TComboBox cbo = field.combos.get(i);

			if (ManagementAngle.NONE != angle) {
				// �I���Ȃ��ȊO�͑I��ΏۈȊO���N���A
				cbo.removeAllItems();
				cbo.addItem(createTTextValue(angle));
				cbo.setEnabled(false);
			} else {

				cbo.removeAllItems();
				initComboBox(cbo);

				canDrillDown = true;
			}
		}

		// �{�^������
		field.btn.setEnabled(canDrillDown);
	}

	/**
	 * �Ǘ�������ݒ肷��
	 * 
	 * @param conditions �Ǘ�����
	 */
	public void setSelectedAngleConditions(List<ManagementAngleSearchCondition> conditions) {
		List<ManagementAngle> list = new ArrayList<ManagementAngle>();
		for (ManagementAngleSearchCondition cond : conditions) {
			list.add(cond.getManagementAngle());
		}

		setSelectedAngles(list);
	}

	/**
	 * �R���{�{�b�N�X������
	 * 
	 * @param cbo
	 */
	protected void initComboBox(TComboBox cbo) {
		Company company = getCompany();
		AccountConfig config = company.getAccountConfig();

		// �����Ȃ�
		cbo.addItem(createTTextValue(ManagementAngle.NONE));

		// �����
		if (!field.angles.contains(ManagementAngle.CUSTOMER)) {
			cbo.addItem(createTTextValue(ManagementAngle.CUSTOMER));
		}

		// �Ј�
		if (!field.angles.contains(ManagementAngle.EMPLOYEE)) {
			cbo.addItem(createTTextValue(ManagementAngle.EMPLOYEE));
		}

		// �Ǘ�1
		if (config.isUseManagement1() && !field.angles.contains(ManagementAngle.MANAGEMENT1)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT1));
		}

		// �Ǘ�2
		if (config.isUseManagement2() && !field.angles.contains(ManagementAngle.MANAGEMENT2)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT2));
		}

		// �Ǘ�3
		if (config.isUseManagement3() && !field.angles.contains(ManagementAngle.MANAGEMENT3)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT3));
		}

		// �Ǘ�4
		if (config.isUseManagement4() && !field.angles.contains(ManagementAngle.MANAGEMENT4)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT4));
		}

		// �Ǘ�5
		if (config.isUseManagement5() && !field.angles.contains(ManagementAngle.MANAGEMENT5)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT5));
		}

		// �Ǘ�6
		if (config.isUseManagement6() && !field.angles.contains(ManagementAngle.MANAGEMENT6)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT6));
		}
	}

	/**
	 * @param angle
	 * @return �R���{�{�b�N�X�I����
	 */
	protected TTextValue createTTextValue(ManagementAngle angle) {
		return new TTextValue(getAngleName(angle), angle);
	}

	/**
	 * @param angle
	 * @return �\����
	 */
	public static String getAngleName(ManagementAngle angle) {

		if (angle == null) {
			return "";
		}

		Company company = TLoginInfo.getCompany();
		AccountConfig config = company.getAccountConfig();
		String lang = TClientLoginInfo.getInstance().getUserLanguage();

		switch (angle) {
			case NONE:
				return MessageUtil.getWord(lang, "C01748");
			case DEPARTMENT:
				return MessageUtil.getWord(lang, "C00467");
			case CUSTOMER:
				return MessageUtil.getWord(lang, "C00408");
			case SUMCUSTOMER:
				return MessageUtil.getWord(lang, "C01149");
			case EMPLOYEE:
				return MessageUtil.getWord(lang, "C00246");
			case MANAGEMENT1:
				return config.getManagement1Name();
			case MANAGEMENT2:
				return config.getManagement2Name();
			case MANAGEMENT3:
				return config.getManagement3Name();
			case MANAGEMENT4:
				return config.getManagement4Name();
			case MANAGEMENT5:
				return config.getManagement5Name();
			case MANAGEMENT6:
				return config.getManagement6Name();
		}
		return "";
	}

}
