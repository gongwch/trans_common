package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���[�U�����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class OPUserReferenceController extends TUserReferenceController {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public OPUserReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return ����������Ԃ�
	 */
	@Override
	protected UserSearchCondition getCondition() {
		return condition;
	}

}
