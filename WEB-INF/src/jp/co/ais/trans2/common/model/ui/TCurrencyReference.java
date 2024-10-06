package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * �ʉ݂̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TCurrencyReference extends TReference {

	/** �R���g���[�� */
	protected TCurrencyReferenceController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TCurrencyReference() {
		this(TYPE.BUTTON, "");
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public TCurrencyReference(String title) {
		this(TYPE.BUTTON, title);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type
	 */
	public TCurrencyReference(TYPE type) {
		this(type, "");
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type
	 * @param title �O����ݒ肵�����ꍇ�̃L���v�V����
	 */
	public TCurrencyReference(TYPE type, String title) {
		super(type, title);

		controller = createController();
	}

	/**
	 * �R���g���[��
	 * 
	 * @return �R���g���[��
	 */
	public TCurrencyReferenceController createController() {
		return new TCurrencyReferenceController(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public CurrencySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Currency getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param currency �G���e�B�e�B
	 */
	public void setEntity(Currency currency) {
		controller.setEntity(currency);
	}

	/**
	 * �R�[�h�̒�����Ԃ��B�f�t�H���g���ƈقȂ錟���t�B�[���h��<br>
	 * ���Y���\�b�h��Override����B
	 * 
	 * @return �R�[�h��
	 */
	@Override
	protected int getCodeLength() {
		return TransUtil.CURRENCY_CODE_LENGTH;
	}

	/**
	 * ���݂̓��͒l�ŃG���e�B�e�B���Đݒ肷��
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.<br>
	 * �\�����X�V����
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}
}
