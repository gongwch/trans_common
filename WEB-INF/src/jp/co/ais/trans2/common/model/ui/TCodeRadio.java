package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.code.*;

/**
 * �R�[�hRadio
 */
public class TCodeRadio extends TPanel {

	/** ���W�I�{�^�� */
	public Map<String, TRadioButton> radios = new HashMap<String, TRadioButton>();

	/** entities */
	public Map<String, OP_CODE_MST> entities = new HashMap<String, OP_CODE_MST>();

	/** �{�^���O���[�v */
	protected ButtonGroup btngrpClass = new ButtonGroup();

	/** ���ʐݒ� */
	protected GridBagConstraints gc = new GridBagConstraints();

	/** �`����� */
	public int alignment = SwingConstants.HORIZONTAL;

	/** ���]�� */
	public int leftMargin = 0;

	/** ���W�I�{�^���̍���(�f�t�H���g��16�ŁA16���s����) */
	public int height = 0;

	/** ���W�I�{�^���̕� */
	public int width = 0;

	/** true:�������������� */
	public boolean autoAdjustment = false;

	/** �R���g���[�� */
	protected TCodeRadioController controller;

	/** true:���q���[�h�Afalse:�O�q���[�h */
	protected boolean local = false;

	/** �敪 */
	protected OPCodeDivision div = null;

	/** �R�[�h�w�� */
	protected String[] codes = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param width ��
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeRadio(int width, OPCodeDivision div, String... codes) {
		this(SwingConstants.HORIZONTAL, 0, 0, width, false, div, codes);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param autoAdjustment true:��������������
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeRadio(boolean autoAdjustment, OPCodeDivision div, String... codes) {
		this(SwingConstants.HORIZONTAL, 0, 0, 0, autoAdjustment, div, codes);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param local
	 * @param autoAdjustment true:��������������
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeRadio(boolean local, boolean autoAdjustment, OPCodeDivision div, String... codes) {
		this(local, SwingConstants.HORIZONTAL, 0, 0, 0, autoAdjustment, div, codes);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param alignment ��:SwingConstants.HORIZONTAL �c:SwingConstants.VERTICAL
	 * @param leftMargin ���]��
	 * @param height ���W�I�{�^���̍���(�f�t�H���g��16�ŁA16���s����)
	 * @param width ��
	 * @param autoAdjustment true:��������������
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeRadio(int alignment, int leftMargin, int height, int width, boolean autoAdjustment, OPCodeDivision div,
		String... codes) {
		this(false, alignment, leftMargin, height, width, autoAdjustment, div, codes);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param local true:���q���[�h�Afalse:�O�q���[�h
	 * @param alignment ��:SwingConstants.HORIZONTAL �c:SwingConstants.VERTICAL
	 * @param leftMargin ���]��
	 * @param height ���W�I�{�^���̍���(�f�t�H���g��16�ŁA16���s����)
	 * @param width ��
	 * @param autoAdjustment true:��������������
	 * @param div �敪
	 * @param codes �R�[�h�w��
	 */
	public TCodeRadio(boolean local, int alignment, int leftMargin, int height, int width, boolean autoAdjustment,
		OPCodeDivision div, String... codes) {

		this.local = local;
		this.alignment = alignment;
		this.leftMargin = leftMargin;
		this.height = height;
		this.width = width;
		this.autoAdjustment = autoAdjustment;
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
	protected TCodeRadioController createController() {
		return new TCodeRadioController(this);
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

		this.setLayout(new GridBagLayout());

		gc.insets = new Insets(0, 0, 0, 0);
		if (alignment == SwingConstants.HORIZONTAL) {
			// ��
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
		} else {
			// �c
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.NORTHWEST;
		}
	}

	/**
	 * @return controller
	 */
	public TCodeRadioController getController() {
		return controller;
	}

	/**
	 * �N���A
	 */
	protected void clear() {
		radios.clear();
		btngrpClass = new ButtonGroup();
		this.removeAll();

		System.gc();
	}

	/**
	 * ���W�I�{�^���ǉ�
	 * 
	 * @param bean
	 * @param title �\������
	 */
	public void addRadio(OP_CODE_MST bean, String title) {
		addRadio(bean, title, this.width);
	}

	/**
	 * ���W�I�{�^���ǉ�
	 * 
	 * @param bean
	 * @param title �\������
	 * @param w �w�蕝
	 */
	public void addRadio(OP_CODE_MST bean, String title, int w) {

		TRadioButton rdo = new TRadioButton(title);
		TGuiUtil.setComponentSize(rdo, new Dimension(w, 16 + height));
		rdo.setHorizontalAlignment(SwingConstants.LEFT);
		rdo.setVerticalAlignment(SwingConstants.CENTER);
		rdo.setMargin(new Insets(0, leftMargin, 0, 0));

		if (radios.isEmpty()) {
			rdo.setSelected(true);
		}

		btngrpClass.add(rdo);
		radios.put(bean.getCode(), rdo);
		entities.put(bean.getCode(), bean);

		if (alignment == SwingConstants.HORIZONTAL) {
			// ��
			gc.gridx = radios.size();
			this.add(rdo, gc);

			int twidth = 0;
			for (TRadioButton inrdo : radios.values()) {
				twidth += inrdo.getWidth();
			}

			setWidth(leftMargin + twidth + 10);
		} else {
			// �c
			gc.gridy = radios.size();
			this.add(rdo, gc);

			int twidth = 0;
			int theight = 0;
			for (TRadioButton inrdo : radios.values()) {
				twidth = Math.max(inrdo.getWidth(), twidth);
				theight += inrdo.getHeight();
			}

			setWidth(leftMargin + twidth + 10);
			setHeight(theight + 30);
		}
	}

	/**
	 * �y��p�l���̕��ύX
	 * 
	 * @param width ���T�C�Y
	 */
	public void setWidth(int width) {
		TGuiUtil.setComponentWidth(this, width);
	}

	/**
	 * �y��p�l���̍����ύX
	 * 
	 * @param height �����T�C�Y
	 */
	public void setHeight(int height) {
		TGuiUtil.setComponentHeight(this, height);
	}

	/**
	 * �����񂹂̐ݒ�
	 * 
	 * @param alignment ��
	 */
	public void setHorizontalAlignment(int alignment) {
		for (TRadioButton rdo : radios.values()) {
			rdo.setHorizontalAlignment(alignment);
		}
	}

	/**
	 * true:���������������̎擾
	 * 
	 * @return autoAdjustment true:��������������
	 */
	public boolean isAutoAdjustment() {
		return autoAdjustment;
	}

	/**
	 * �\�������ʎw��
	 * 
	 * @param code
	 * @param word �\������
	 */
	public void setLangMessageID(String code, String word) {
		TRadioButton rdo = getRadio(code);
		if (rdo != null) {
			rdo.setLangMessageID(word);
		}
	}

	/**
	 * @param code
	 * @return Radio
	 */
	public TRadioButton getRadio(String code) {
		TRadioButton rdo = radios.get(code);
		if (rdo != null) {
			return rdo;
		}
		return null;
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TRadioButton rdo : radios.values()) {
			rdo.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * �w��Index�{�^���̑I����Ԃ�ON�ɂ���.
	 * 
	 * @param code
	 */
	public void setSelectON(String code) {
		for (Entry<String, TRadioButton> entry : radios.entrySet()) {
			entry.getValue().setSelected(entry.getKey().equals(code));
		}
	}

	/**
	 * �w��Index�{�^���̑I����Ԃ�ON�ɂ���.
	 * 
	 * @param num
	 */
	public void setSelectON(int num) {
		String code = Integer.toString(num);
		setSelectON(code);
	}

	/**
	 * �I��Radio�̖߂�
	 * 
	 * @return �I��Radio(null�\)
	 */
	public OP_CODE_MST getSelected() {
		for (Entry<String, TRadioButton> entry : radios.entrySet()) {
			if (entry.getValue().isSelected()) {
				return entities.get(entry.getKey());
			}
		}
		return null;
	}

	/**
	 * �I��Radio�̖߂�
	 * 
	 * @return �I��Radio(null�\)
	 */
	public String getCode() {
		OP_CODE_MST bean = getSelected();
		if (bean != null) {
			return bean.getCode();
		}
		return null;
	}

	/**
	 * �I��Radio�̖߂�<br>
	 * �L���Ȑ��l�̏ꍇ�A���l��Ԃ�
	 * 
	 * @return �I��Radio(null�̏ꍇ��-1)
	 */
	public int getInt() {
		OP_CODE_MST bean = getSelected();
		if (bean != null && !Util.isNullOrEmpty(bean.getCode()) && Util.isNumber(bean.getCode())) {
			return DecimalUtil.toInt(bean.getCode());
		}
		return -1;
	}

	/**
	 * ItemListener�Z�b�g<br>
	 * �S���W�I�{�^������
	 * 
	 * @param listener ���X�i�[
	 */
	public void addItemListener(ItemListener listener) {
		for (TRadioButton rdo : radios.values()) {
			rdo.addItemListener(listener);
		}
	}

	/**
	 * ItemListener�Z�b�g
	 * 
	 * @param code
	 * @param listener ���X�i�[
	 */
	public void addItemListener(String code, ItemListener listener) {
		radios.get(code).addItemListener(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (TRadioButton radio : radios.values()) {
			radio.setEnabled(enabled);
		}
	}

	/**
	 * �T�C�Y���Z�b�g
	 */
	public void resizePanel() {
		if (radios.size() > 0) {
			int totalWidth = radios.size() * this.width;
			TGuiUtil.setComponentSize(this, totalWidth, 20 + this.height);
		}
	}

	/**
	 * �T�C�Y���Z�b�g
	 * 
	 * @param totalWidth
	 */
	public void resizePanel(int totalWidth) {
		TGuiUtil.setComponentSize(this, totalWidth, 20 + this.height);
	}
}
