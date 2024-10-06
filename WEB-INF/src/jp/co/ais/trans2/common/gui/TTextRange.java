package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �e�L�X�g�͈̓t�B�[���h
 * 
 * @author AIS
 */
public class TTextRange extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -5671715054170282481L;

	/** �R���|�[�l���g�i�J�n�j */
	public TLabelField fieldFrom;

	/** �R���|�[�l���g�i�I���j */
	public TLabelField fieldTo;

	/** ���� */
	public static final int height = 20;

	/**
	 * �R���X�g���N�^.
	 */
	public TTextRange() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		fieldFrom = new TLabelField();
		fieldTo = new TLabelField();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setLayout(null);

		// �J�n
		fieldFrom.setLocation(0, 0);
		fieldFrom.setSize(fieldFrom.getFieldSize() + fieldFrom.getLabelSize() + 5, height);
		add(fieldFrom);

		// �I��
		fieldTo.setLabelSize(20);
		fieldTo.setSize(fieldTo.getFieldSize() + fieldTo.getLabelSize() + 5, height);
		fieldTo.setLangMessageID("C01333");
		fieldTo.setLocation(fieldFrom.getWidth(), 0);
		add(fieldTo);

		// �S�̂̃T�C�Y��ݒ�
		setSize();

	}

	/**
	 * �S�̂̃T�C�Y�ݒ�
	 */
	public void setSize() {
		setSize(fieldFrom.getWidth() + fieldTo.getWidth() + 5, 20);
	}

	/**
	 * FROM�ATO�A�S�̂̃T�C�Y���Đݒ�
	 */
	public void resize() {
		fieldFrom.setSize(fieldFrom.getFieldSize() + fieldFrom.getLabelSize() + 5, height);
		fieldTo.setSize(fieldTo.getFieldSize() + fieldTo.getLabelSize() + 5, height);
		fieldTo.setLocation(fieldFrom.getWidth(), 0);
		setSize(fieldFrom.getWidth() + fieldTo.getWidth() + 5, 20);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		fieldFrom.setTabControlNo(tabControlNo);
		fieldTo.setTabControlNo(tabControlNo);
	}

	/**
	 * �uFROM <= TO�v���r����.
	 * 
	 * @return �uFROM <= TO�v�܂��́A�ǂ��炩���u�����N�Ȃ��true.
	 */
	public boolean isSmallerFrom() {
		return Util.isSmallerThen(fieldFrom.getValue(), fieldTo.getValue());
	}

	/**
	 * �R���|�[�l���g�i�J�n�j���擾����
	 * 
	 * @return �R���|�[�l���g�i�J�n�j
	 */
	public TLabelField getFieldFrom() {
		return fieldFrom;
	}

	/**
	 * �R���|�[�l���g�i�I���j���擾����
	 * 
	 * @return �R���|�[�l���g�i�I���j
	 */
	public TLabelField getFieldTo() {
		return fieldTo;
	}

	/**
	 * �ҏW��Ԃ�ݒ肷��
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		fieldFrom.setEditable(isEditable);
		fieldTo.setEditable(isEditable);
	}

	/**
	 * �R���|�[�l���g�i�J�n�j�̒l���擾����
	 * 
	 * @return �R���|�[�l���g�i�J�n�j�̒l
	 */
	public String getValueFrom() {
		return fieldFrom.getValue();
	}

	/**
	 * �R���|�[�l���g�i�I���j�̒l���擾����
	 * 
	 * @return �R���|�[�l���g�i�I���j�̒l
	 */
	public String getValueTo() {
		return fieldTo.getValue();
	}

	/**
	 * �l��ݒ肷��
	 * 
	 * @param valueFrom �R���|�[�l���g�i�J�n�j �ݒ�l
	 * @param valueTo �R���|�[�l���g�i�I���j �ݒ�l
	 */
	public void setValue(String valueFrom, String valueTo) {
		fieldFrom.setValue(valueFrom);
		fieldTo.setValue(valueTo);
	}

	/**
	 * ���x����ݒ肷��
	 * 
	 * @param value ���x���e�L�X�g
	 * @param Width ���x���T�C�Y
	 */
	public void setLabelFrom(String value, int Width) {
		fieldFrom.setLangMessageID(value);
		fieldFrom.setLabelSize(Width);
		resize();
	}

	/**
	 * �t�B�[���h�T�C�Y��ݒ肷��
	 * 
	 * @param size
	 */
	public void setFieldSize(int size) {
		fieldFrom.setFieldSize(size);
		fieldTo.setFieldSize(size);
		resize();
	}

	/**
	 * �ő包����ݒ肷��
	 * 
	 * @param length
	 */
	public void setMaxLength(int length) {
		fieldFrom.setMaxLength(length);
		fieldTo.setMaxLength(length);
	}

	/**
	 * IME��ݒ肷��
	 * 
	 * @param flag
	 */
	public void setImeMode(boolean flag) {
		fieldFrom.setImeMode(flag);
		fieldTo.setImeMode(flag);
	}

	/**
	 * �N���A
	 */
	public void clear() {
		fieldFrom.clear();
		fieldTo.clear();
	}
}
