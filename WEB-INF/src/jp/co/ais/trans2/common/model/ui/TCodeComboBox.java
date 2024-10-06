package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.code.*;

/**
 * �R�[�h�R���{�{�b�N�X
 */
public class TCodeComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TCodeComboBoxController controller;

	/** true:���q���[�h�Afalse:�O�q���[�h */
	protected boolean local = false;

	/** �擪�u�����N�ǉ� */
	protected boolean addBlank = false;

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
	public TCodeComboBox(OPCodeDivision div, String... codes) {
		this(false, div, codes);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param addBlank true:�擪�u�����N�A�C�e���ǉ�
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeComboBox(boolean addBlank, OPCodeDivision div, String... codes) {
		this(false, addBlank, div, codes);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param local true:���q���[�h�Afalse:�O�q���[�h
	 * @param addBlank true:�擪�u�����N�A�C�e���ǉ�
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeComboBox(boolean local, boolean addBlank, OPCodeDivision div, String... codes) {

		this.local = local;
		this.addBlank = addBlank;
		this.div = div;
		this.codes = codes;

		// ������
		init();
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
	 * @return true:�擪�u�����N�A�C�e���ǉ�
	 */
	public boolean isAddBlank() {
		return addBlank;
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

		boolean isChanged = codeDiv != this.div;

		this.div = codeDiv;

		if (isChanged) {
			// �ύX���聨�ēx������
			getController().init();
		}
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

		// �ēx������
		getController().init();
	}

	/**
	 * ������
	 */
	protected void init() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		controller = createController();

	}

	/**
	 * @return �R���g���[��
	 */
	protected TCodeComboBoxController createController() {
		return new TCodeComboBoxController(this);
	}

	/**
	 *
	 */
	protected void initComponents() {
		//
	}

	/**
	 *
	 */
	protected void allocateComponents() {
		// �R�[�h
		setLabelSize(85);
		setComboSize(100);
		setSize(190, 20);
	}

	/**
	 * @return controller
	 */
	public TCodeComboBoxController getController() {
		return controller;
	}

	/**
	 * �I������Ă���l���擾
	 * 
	 * @return �l
	 */
	@Override
	public String getSelectedItemValue() {
		Object value = this.combo.getSelectedItemValue();

		if (value instanceof OP_CODE_MST) {
			return ((OP_CODE_MST) value).getCode();
		} else if (value instanceof AutoCompletable) {
			return ((AutoCompletable) value).getCode();
		} else if (value instanceof TReferable) {
			return ((TReferable) value).getCode();
		}

		return Util.avoidNull(value);
	}

	/**
	 * �I������Ă���l���擾
	 * 
	 * @return �l
	 */
	public OP_CODE_MST getSelectedEntity() {
		Object value = this.combo.getSelectedItemValue();

		if (value instanceof OP_CODE_MST) {
			return (OP_CODE_MST) value;
		}
		String code = null;
		if (value instanceof AutoCompletable) {
			code = ((AutoCompletable) value).getCode();
		} else if (value instanceof TReferable) {
			code = ((TReferable) value).getCode();
		}
		return controller.getCodeMst(code);
	}

	/**
	 * �I�𒆒l�̖߂�
	 * 
	 * @return �I�𒆒l(null�\)
	 */
	public String getCode() {
		return getSelectedItemValue();
	}

	/**
	 * �I�𒆒l�̖��̂�߂�
	 * 
	 * @return �I�𒆒l(null�\)
	 */
	public String getCodeName() {
		OP_CODE_MST code = getSelectedEntity();
		if (code == null) {
			return null;
		}
		return code.getCODE_NAME();
	}

	/**
	 * �I�𒆒l�̖߂�<br>
	 * �L���Ȑ��l�̏ꍇ�A���l��Ԃ�
	 * 
	 * @return �I�𒆒l(null�̏ꍇ��-1)
	 */
	public int getInt() {
		String code = getCode();
		if (!Util.isNullOrEmpty(code) && Util.isNumber(code)) {
			return DecimalUtil.toInt(code);
		}
		return -1;
	}

	/**
	 * �l���w�肵�đI������ύX����
	 * 
	 * @param code OP_CODE_MST.CODE
	 */
	public void setCode(String code) {
		setSelectedValue(code);
	}

	/**
	 * �l���w�肵�đI������ύX����
	 * 
	 * @param no
	 */
	public void setInt(int no) {
		setSelectedValue(Integer.toString(no));
	}

	/**
	 * �l���w�肵�đI������ύX����
	 * 
	 * @param code OP_CODE_MST.CODE
	 */
	public void setSelectedValue(String code) {
		OP_CODE_MST bean = controller.getCodeMst(code);
		this.setSelectedItemValue(bean);
	}

	/**
	 * �l���w�肵�đI������ύX����
	 * 
	 * @param bean OP_CODE_MST
	 */
	public void setSelectedItemValue(OP_CODE_MST bean) {
		super.setSelectedItemValue(bean);
	}

	/**
	 * �l���w�肵�đI������ύX����
	 * 
	 * @param obj OP_CODE_MST
	 * @deprecated �񐄏� ���m�Ɉ�����OP_CODE_MST���w�肷�邱�Ƃ𐄏�
	 */
	@Override
	public void setSelectedItemValue(Object obj) {
		super.setSelectedItemValue(obj);
	}

	/**
	 * �l(�L�[)�E�\��������ǉ�
	 * 
	 * @param text �e�L�X�g
	 * @param value �l
	 * @deprecated �񐄏� AddItem�𐄏�
	 */
	@Override
	public void addTextValueItem(Object value, String text) {
		super.addTextValueItem(value, text);
	}

	/**
	 * �R���{�{�b�N�X�I������ǉ�����
	 * 
	 * @param bean
	 */
	public void addItem(OP_CODE_MST bean) {
		controller.addItem(bean);
	}

	/**
	 * �R���{�{�b�N�X�I������ǉ�����
	 * 
	 * @param code
	 */
	public void addItem(String code) {
		controller.addItem(code);
	}

}
