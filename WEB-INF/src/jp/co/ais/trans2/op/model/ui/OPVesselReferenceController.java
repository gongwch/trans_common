package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * Vessel�̌����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class OPVesselReferenceController extends TVesselReferenceController {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public OPVesselReferenceController(TReference field) {
		super(field);
	}

	/**
	 * �����������擾����
	 * 
	 * @return ��������
	 */
	@Override
	protected VesselSearchCondition getCondition() {
		return condition;
	}

	/**
	 * Vessel���擾����
	 * 
	 * @param condition_ ��������
	 * @return Vessel���
	 */
	@Override
	protected List<Vessel> getList(VesselSearchCondition condition_) {
		try {
			List<Vessel> list = OPLoginUtil.getVesselList(condition_);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
