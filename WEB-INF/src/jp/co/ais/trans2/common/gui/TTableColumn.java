package jp.co.ais.trans2.common.gui;

import javax.swing.*;
import javax.swing.table.*;

/**
 * TTable�̃J�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TTableColumn {

	/** �^�C�g�� */
	protected String title = null;

	/** �� */
	protected int width = 0;

	/** ����������align */
	protected int horizontalAlign = SwingConstants.LEFT;

	/** ������ */
	protected int verticalAlign = SwingConstants.CENTER;

	/** �������s */
	protected boolean autoWordwrap = false;

	/** �������s���L���A�e�L�X�g�񂹁A�f�t�H���g�F���� */
	protected boolean textAlignCenter = false;

	/** enum */
	protected Enum e;

	/** �\��/��\�� */
	protected boolean visible = true;

	/** Component */
	protected TTableComponent component = null;

	/** ���f��e�[�u���J���� */
	protected TableColumn column;

	/**
	 * �R���X�g���N�^�[
	 */
	public TTableColumn() {
		// �����Ȃ�
	}

	/**
	 * ��̃^�C�g���A�����w�肵�ăC���X�^���X�𐶐�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 */
	public TTableColumn(Enum e, String title, int width) {
		this(e, title, width, SwingConstants.LEFT);
	}

	/**
	 * ��̃^�C�g���A�����w�肵�ăC���X�^���X�𐶐�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param isVisible �\��/��\��
	 */
	public TTableColumn(Enum e, String title, int width, boolean isVisible) {
		this(e, title, width, SwingConstants.LEFT, isVisible);
	}

	/**
	 * ��̃^�C�g���A���A���������\���ʒu���w�肵�ăC���X�^���X�𐶐�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 */
	public TTableColumn(Enum e, String title, int width, int horizontalAlign) {
		this(e, title, width, horizontalAlign, null);
	}

	/**
	 * ��̃^�C�g���A���A���������\���ʒu�A�\��/��\�����w�肵�ăC���X�^���X�𐶐�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 * @param visible �\��/��\��
	 */
	public TTableColumn(Enum e, String title, int width, int horizontalAlign, boolean visible) {
		this(e, title, width, horizontalAlign, null, visible);
	}

	/**
	 * ��̃^�C�g���A���A���������\���ʒu�A�Z�b�g����R���|�[�l���g���w�肵�ăC���X�^���X�𐶐�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 * @param component �R���|�[�l���g
	 */
	public TTableColumn(Enum e, String title, int width, int horizontalAlign, TTableComponent component) {

		this(e, title, width, horizontalAlign, component, true);
	}

	/**
	 * ��̃^�C�g���A���A���������\���ʒu�A�Z�b�g����R���|�[�l���g�A�\����\�����w�肵�ăC���X�^���X�𐶐�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 * @param component �R���|�[�l���g
	 * @param visible �\�����邩
	 */
	public TTableColumn(Enum e, String title, int width, int horizontalAlign, TTableComponent component, boolean visible) {
		this.e = e;
		this.title = title;
		this.width = width;
		this.horizontalAlign = horizontalAlign;
		this.component = component;
		this.visible = visible;
	}

	/**
	 * Enum
	 * 
	 * @return e Enum
	 */
	public Enum getE() {
		return e;
	}

	/**
	 * �^�C�g����߂��܂��B
	 * 
	 * @return �^�C�g��
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title �^�C�g����ݒ肵�܂��B
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * �\��/��\��
	 * 
	 * @return true:�\��
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * �\��/��\��
	 * 
	 * @param visible true:�\��
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return ����߂��܂��B
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width ����ݒ肵�܂��B
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return horizontalAlign��߂��܂��B
	 */
	public int getHorizontalAlign() {
		return horizontalAlign;
	}

	/**
	 * @param horizontalAlign horizontalAlign��ݒ肵�܂��B
	 */
	public void setHorizontalAlign(int horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}

	/**
	 * �����񂹂̎擾
	 * 
	 * @return verticalAlignment ������
	 */
	public int getVerticalAlign() {
		return verticalAlign;
	}

	/**
	 * �����񂹂̐ݒ�
	 * 
	 * @param verticalAlign ������
	 */
	public void setVerticalAlign(int verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	/**
	 * �������s�̎擾
	 * 
	 * @return autoWordwrap �������s
	 */
	public boolean isAutoWordwrap() {
		return autoWordwrap;
	}

	/**
	 * �������s�̐ݒ�
	 * 
	 * @param autoWordwrap �������s
	 */
	public void setAutoWordwrap(boolean autoWordwrap) {
		this.autoWordwrap = autoWordwrap;
	}

	/**
	 * �������s���L���A�e�L�X�g�񂹁A�f�t�H���g�F�����̎擾
	 * 
	 * @return textAlignCenter �������s���L���A�e�L�X�g�񂹁A�f�t�H���g�F����
	 */
	public boolean isTextAlignCenter() {
		return textAlignCenter;
	}

	/**
	 * �������s���L���A�e�L�X�g�񂹁A�f�t�H���g�F�����̐ݒ�
	 * 
	 * @param textAlignCenter �������s���L���A�e�L�X�g�񂹁A�f�t�H���g�F����
	 */
	public void setTextAlignCenter(boolean textAlignCenter) {
		this.textAlignCenter = textAlignCenter;
	}

	/**
	 * �R���|�[�l���g
	 * 
	 * @return �R���|�[�l���g
	 */
	public TTableComponent getComponent() {
		return component;
	}

	/**
	 * �R���|�[�l���g
	 * 
	 * @param component �R���|�[�l���g
	 */
	public void setComponent(TTableComponent component) {
		this.component = component;
	}

	/**
	 * ���f��e�[�u���J����
	 * 
	 * @return column ���f��e�[�u���J����
	 */
	public TableColumn getColumn() {
		return column;
	}

	/**
	 * ���f��e�[�u���J����
	 * 
	 * @param column ���f��e�[�u���J����
	 */
	public void setColumn(TableColumn column) {
		this.column = column;
	}
}
