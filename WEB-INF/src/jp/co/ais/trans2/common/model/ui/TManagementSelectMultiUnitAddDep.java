package jp.co.ais.trans2.common.model.ui;

import java.util.*;

/**
 * �Ǘ��I���F�Ǘ��I��+�͈͌���+�C�ӑI�� * 3�F���C��
 * 
 * @author AIS
 */
public class TManagementSelectMultiUnitAddDep extends TManagementSelectMultiUnit {

	/** �Ǘ��I���t�B�[���h */
	public List<TManagementSelectUnitAddDep> ctrlManagementSelectUnits;

	/**
	 * �R���X�g���N�^
	 */
	public TManagementSelectMultiUnitAddDep() {
		super(FIELD_COUNT);
	}

	/**
	 * �R���g���[������
	 * 
	 * @return controller
	 */
	@Override
	protected TManagementSelectMultiUnitAddDepController createController() {
		return new TManagementSelectMultiUnitAddDepController(this);
	}

	/**
	 * �R���|�[�l���g������
	 * 
	 * @param fieldCount
	 */
	@Override
	public void initComponents(int fieldCount) {
		ctrlManagementSelectUnits = new ArrayList<TManagementSelectUnitAddDep>();
		for (int i = 0; i < fieldCount; i++) {
			ctrlManagementSelectUnits.add(new TManagementSelectUnitAddDep());
		}
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	@Override
	public void allocateComponents() {

		setLangMessageID("C10834"); // �Ǘ��I��

		int y = 5;
		for (TManagementSelectUnitAddDep unit : ctrlManagementSelectUnits) {
			unit.setLocation(15, y);
			add(unit);
			y = y + unit.getHeight() + 5;
		}

		setSize(360, y + 25);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	@Override
	public void setTabControlNo(int tabControlNo) {
		for (TManagementSelectUnitAddDep unit : ctrlManagementSelectUnits) {
			unit.setTabControlNo(tabControlNo);
		}
	}

}