package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.code.*;

/**
 * Code�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TCodeReference extends TReference {

	/** �R���g���[�� */
	protected TCodeReferenceController controller;

	/** true:���q���[�h�Afalse:�O�q���[�h */
	protected boolean local = false;

	/** �敪 */
	protected OPCodeDivision div = null;

	/** �R�[�h�w�� */
	protected String[] codes = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeReference(OPCodeDivision div, String... codes) {
		this(false, div, codes);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param local true:���q���[�h�Afalse:�O�q���[�h
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeReference(boolean local, OPCodeDivision div, String... codes) {
		super();

		this.local = local;
		this.div = div;
		this.codes = codes;

		initController();
	}

	/**
	 * true:���q���[�h�Afalse:�O�q���[�h�̎擾
	 * 
	 * @return local true:���q���[�h�Afalse:�O�q���[�h
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * true:���q���[�h�Afalse:�O�q���[�h�̐ݒ�
	 * 
	 * @param local true:���q���[�h�Afalse:�O�q���[�h
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	/**
	 * @return �R�[�h�敪
	 */
	public OPCodeDivision getCodeDivision() {
		return div;
	}

	/**
	 * �R�[�h�敪�w��
	 * 
	 * @param codeDiv �敪
	 */
	public void setCodeDivision(OPCodeDivision codeDiv) {
		this.div = codeDiv;
	}

	/**
	 * @return �w��R�[�h
	 */
	public String[] getCodes() {
		return codes;
	}

	/**
	 * �R�[�h�w��
	 * 
	 * @param arr �w��R�[�h
	 */
	public void setCodes(String... arr) {
		this.codes = arr;
	}

	/**
	 * �R���g���[������
	 */
	protected void initController() {
		// �R���g���[������
		if (controller == null) {
			controller = new TCodeReferenceController(this);
		}
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	@Override
	protected void initComponents() {

		// �g���I�y���ʑΉ�
		if (isLabelMode()) {
			// �����I�Ƀ��x���ɂ���
			this.type = TYPE.LABEL;
		}

		super.initComponents();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {

		// �g���I�y���ʑΉ�
		if (isLabelMode()) {
			OPGuiUtil.allocateComponents(this);
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * @return true:���x�����[�h
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	public OP_CODE_MST getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param bean �G���e�B�e�B
	 */
	public void setEntity(OP_CODE_MST bean) {
		controller.setEntity(bean);
	}

}
