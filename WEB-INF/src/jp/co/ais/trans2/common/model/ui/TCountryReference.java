package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.country.*;

/**
 * �������R���|�[�l���g
 */
public class TCountryReference extends TReference {

	/** �R���g���[�� */
	protected TCountryReferenceController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TCountryReference() {
		this(TYPE.BUTTON, "");
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public TCountryReference(String title) {
		this(TYPE.BUTTON, title);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type
	 */
	public TCountryReference(TYPE type) {
		this(type, "");
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type
	 * @param title �O����ݒ肵�����ꍇ�̃L���v�V����
	 */
	public TCountryReference(TYPE type, String title) {
		super(type, title);

		controller = createController();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	protected TCountryReferenceController createController() {
		return new TCountryReferenceController(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	protected TCountryReferenceController getController() {
		return controller;
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {
		super.allocateComponents();

		TGuiUtil.setComponentSize(code, new Dimension(40, 20));
	}

	/**
	 * �R�[�h�̒�����Ԃ�
	 * 
	 * @return �R�[�h��
	 */
	@Override
	protected int getCodeLength() {
		return 3;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public CountrySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Country getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param entity �G���e�B�e�B
	 */
	public void setEntity(Country entity) {
		controller.setEntity(entity);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}
}
