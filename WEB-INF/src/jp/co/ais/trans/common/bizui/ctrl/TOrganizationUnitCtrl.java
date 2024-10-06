package jp.co.ais.trans.common.bizui.ctrl;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �g�D���j�b�g�R���g���[�� �C���^�[�t�F�[�X<br>
 * ��ЁA����A�A������Ȃǂ̃j�[�Y�ɍ��킹�ăR���g���[���N���X���쐬����.
 */
public abstract class TOrganizationUnitCtrl extends TAppletClientBase {

	/** ���j�b�g */
	protected TOrganizationUnit unit;

	/** �K�w���x���̕\�� */
	protected String[] hierarchicalLabels = getWordList(new String[] { "C00722", "C01751", "C01752", "C01753",
			"C01754", "C01755", "C01756", "C01757", "C01758", "C01759" });

	/**
	 * �R���X�g���N�^
	 * 
	 * @param unit �g�D���j�b�g
	 */
	public TOrganizationUnitCtrl(TOrganizationUnit unit) {

		this.unit = unit;
	}

	/**
	 * �e�R���|�[�l���g�̃��b�Z�[�WID���Z�b�g����.
	 */
	public abstract void initLangMessageID();

	/**
	 * ��ʏ�����Ԃ̍\�z
	 */
	public void initPanel() {
		// �K�w���ِݒ�i�Œ�j
		unit.getHierarchicalLevelComboBox().getComboBox().setModel(hierarchicalLabels);
	}

	/**
	 * ��ʑg�D�𐧌䂷��
	 */
	public abstract void changeUpper();

	/**
	 * �_�C�A���O��\������
	 * 
	 * @param tbuttonField
	 * @param flag
	 */
	public abstract void showRefDialog(TButtonField tbuttonField, String flag);

	/**
	 * �g�D���ރR���{�{�b�N�XEvent�����B<br>
	 * 
	 * @return �������ʃt���O
	 */
	public abstract boolean changeOrgCode();

	/**
	 * �K�w���x���ύX
	 */
	public abstract void changeHierarchicalLevelItem();

	/**
	 * �R�[�h�ɑ΂��閼�̂�ݒ�
	 * 
	 * @param argBtnField �Ώۃt�B�[���h
	 * @param flag ���̂����݂��邩�ǂ���
	 * @return �������ʃt���O
	 */
	public abstract boolean setupName(TButtonField argBtnField, String flag);

	/**
	 * ��ʑg�D�t���O
	 * 
	 * @return ��ʑg�D�t���O
	 */
	public abstract String getUpperOrgFlag();

	/**
	 * �g�D�t���O
	 * 
	 * @return �g�D�t���O
	 */
	public abstract String getOrgFlag();

	/**
	 * �Ȗڑ̌n�R�[�h�̃Z�b�g
	 * 
	 * @param code �Ȗڑ̌n�R�[�h
	 */
	public abstract void setItemSystemCode(String code);
}
