package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �����d��Ȗڂ̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TAutomaticJournalizingItemReference extends TReference {

	/** �R���g���[�� */
	protected TAutomaticJournalizingItemReferenceController controller;

	/**
	 * �R���X�g���N�^�[
	 */
	public TAutomaticJournalizingItemReference() {

		// �R���g���[������
		controller = createController();

	}

	/**
	 * �R���g���[���̍쐬
	 * 
	 * @return �R���g���[��
	 */
	protected TAutomaticJournalizingItemReferenceController createController() {
		return new TAutomaticJournalizingItemReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public AutomaticJournalItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	public AutomaticJournalItem getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param autoJnlItem �G���e�B�e�B
	 */
	public void setEntity(AutomaticJournalItem autoJnlItem) {
		controller.setEntity(autoJnlItem);
	}

}
