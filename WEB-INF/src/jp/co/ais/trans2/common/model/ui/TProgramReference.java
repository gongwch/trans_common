package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.program.ProgramSearchCondition;

/**
 * �v���O�����̌����R���|�[�l���g
 * @author AIS
 *
 */
public class TProgramReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 6380044260658661264L;

	/** �R���g���[�� */
	protected TProgramReferenceController controller;

	/**
	 * 
	 *
	 */
	public TProgramReference() {

		// �R���g���[������
		controller = new TProgramReferenceController(this);
		
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * @return ��������
	 */
	public ProgramSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

}
