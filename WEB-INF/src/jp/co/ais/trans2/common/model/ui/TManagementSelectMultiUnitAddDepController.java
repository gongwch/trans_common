package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ��I���F�Ǘ��I��+�͈͌���+�C�ӑI�� * 3�F�R���g���[��
 * 
 * @author AIS
 */
public class TManagementSelectMultiUnitAddDepController extends TManagementSelectMultiUnitController {

	/**
	 * @param field �t�B�[���h
	 */
	public TManagementSelectMultiUnitAddDepController(TManagementSelectMultiUnit field) {
		super(field);
	}

	/**
	 * @return field TManagementSelectMultiUnitAddDep
	 */
	public TManagementSelectMultiUnitAddDep getField() {
		return (TManagementSelectMultiUnitAddDep) field;
	}

	/**
	 * �Ǘ�������Z�߂ĕԂ�
	 * 
	 * @return �Ǘ�������Z�߂ĕԂ�
	 */
	@Override
	public List<ManagementAngleSearchCondition> getManagementAngleSearchConditions() {
		List<ManagementAngleSearchCondition> list = new ArrayList<ManagementAngleSearchCondition>();
		for (TManagementSelectUnit unit : getField().ctrlManagementSelectUnits) {
			list.add(unit.getManagementAngleSearchCondition());
		}
		return list;
	}

	/**
	 * �召�`�F�b�N�B�J�n�R�[�h > �I���R�[�h�̃t�B�[���h�������<br>
	 * ���̃C���f�b�N�X��Ԃ��B������Ε�����Ԃ��B
	 * 
	 * @return �J�n�R�[�h > �I���R�[�h�̃t�B�[���h�������<br>
	 *         ���̃C���f�b�N�X��Ԃ��B������Ε�����Ԃ��B
	 */
	@Override
	public int isSmallerFrom() {
		int i = 0;
		for (TManagementSelectUnit unit : getField().ctrlManagementSelectUnits) {
			if (!unit.isSmallerFrom()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * �ォ�珇�ɓ��͂���Ă��邩��Ԃ��B
	 * 
	 * @return �ォ�珇�ɓ��͂���Ă��邩
	 */
	@Override
	public boolean isEnteredAtTheTop() {

		boolean entered = false;
		for (int i = getField().ctrlManagementSelectUnits.size() - 1; i >= 0; i--) {
			if (entered && ManagementAngle.NONE == getField().ctrlManagementSelectUnits.get(i).getManagementAngle()) {
				return false;
			}
			if (ManagementAngle.NONE != getField().ctrlManagementSelectUnits.get(i).getManagementAngle()) {
				entered = true;
			}
		}

		return true;
	}

	/**
	 * �Ǘ��w��ɏd��������΁A�d�����Ă���t�B�[���h�̃C���f�b�N�X��Ԃ��B<br>
	 * �����̏ꍇ�A�d���͂Ȃ�
	 * 
	 * @return �����B����ȊO�͏d�����������t�B�[���h�̔ԍ�
	 */
	@Override
	public int getSameManagementAngleIndex() {

		for (int i = 0; i < getField().ctrlManagementSelectUnits.size(); i++) {

			TManagementSelectUnit unit = getField().ctrlManagementSelectUnits.get(i);

			// �I������Ă���Ǘ�
			ManagementAngle managementAngle = unit.getManagementAngle();
			if (ManagementAngle.NONE != managementAngle) {
				int selectedCount = 0;
				for (TManagementSelectUnit unit2 : getField().ctrlManagementSelectUnits) {
					if (unit2.getManagementAngle() == managementAngle) {
						selectedCount++;
					}
				}
				if (selectedCount != 1) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * �Ǘ����ڒP�ʏ��� �ݒ�ێ�
	 */
	@Override
	public void saveSetting() {
		if (!isUseSaveSetting()) {
			// �L�����Ȃ��ꍇ�A�����s�v
			return;
		}

		// ��������鎞�ɁA�ێ��L�[������΁A���Y�������N���C�A���g�Ɏ���
		if (Util.isNullOrEmpty(getSaveKey())) {
			return;
		}

		int size = getField().ctrlManagementSelectUnits.size();

		List<ManagementAngleSearchCondition> list = getManagementAngleSearchConditions();
		if (list == null || list.isEmpty()) {
			// �����������N���A
			for (int i = 0; i < size; i++) {
				FileUtil.saveTemporaryObject(null, getSaveKey(i));
			}
		} else {
			for (int i = 0; i < size; i++) {
				ManagementAngleSearchCondition condition = list.get(i);
				FileUtil.saveTemporaryObject(condition, getSaveKey(i));
			}
		}

	}

	/**
	 * �Ǘ����ڒP�ʏ����ێ��̐ݒ�ɂ�蕜��
	 */
	@Override
	protected void restoreSetting() {
		if (!isUseSaveSetting()) {
			// �L�����Ȃ��ꍇ�A�����s�v
			return;
		}

		// �ێ��L�[���g���ď���������
		if (!Util.isNullOrEmpty(getSaveKey())) {
			for (int i = 0; i < getField().ctrlManagementSelectUnits.size(); i++) {
				TManagementSelectUnit unit = getField().ctrlManagementSelectUnits.get(i);
				unit.setManagementAngleSearchCondition(getSaveCondition(i));
			}
		}
	}

}
