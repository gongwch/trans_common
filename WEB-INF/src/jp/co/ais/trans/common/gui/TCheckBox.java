package jp.co.ais.trans.common.gui;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * JCheckBox�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ�����CheckBox.
 */
public class TCheckBox extends JCheckBox implements TInterfaceLangMessageID, TInterfaceTabControl, TTableComponent {

	/**  */
	protected String langMessageID = null;

	/**  */
	protected int tabControlNo = -1;

	/**  */
	protected boolean isTabEnabled = true;

	/** �ݒ�Index�ԍ� */
	protected int index = -1;

	/** Enter�ł̃t�H�[�J�X�ړ����\�� */
	protected boolean isEnterFocusable = true;

	/** TTable��index */
	protected int modelIndex = -1;

	/** TableCellEditor���p�� */
	protected boolean tableCellEditor = false;

	/**
	 * �R���X�g���N�^.<br>
	 * ���I����Ԃō\�z.
	 */
	public TCheckBox() {
		this("");
	}

	/**
	 * �R���X�g���N�^.<br>
	 * ���I����Ԃō\�z.
	 * 
	 * @param langMessageID �P��ID
	 */
	public TCheckBox(String langMessageID) {
		this(langMessageID, false);
	}

	/**
	 * �R���X�g���N�^.<br>
	 * ���I����Ԃō\�z.
	 * 
	 * @param index �C���f�b�N�X�ԍ�
	 */
	public TCheckBox(int index) {
		this(false, index);
	}

	/**
	 * �R���X�g���N�^.<br>
	 * ���I����Ԃō\�z.
	 * 
	 * @param selected �I�����
	 * @param index �C���f�b�N�X�ԍ�
	 */
	public TCheckBox(boolean selected, int index) {
		this(selected);

		this.index = index;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param selected �I�����
	 */
	public TCheckBox(boolean selected) {
		this("", selected);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param selected �I�����
	 * @param langMessageID �P��ID
	 */
	public TCheckBox(String langMessageID, boolean selected) {
		super(langMessageID, selected);

		this.langMessageID = langMessageID;

		enableInputMethods(false);

		// �t�@���N�V�����L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});

		setOpaque(false);
	}

	/**
	 * �t�@���N�V�����L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	protected void handleKeyPressed(KeyEvent evt) {

		TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * 
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	/**
	 * �ݒ肳�ꂽIndex��Ԃ�.<br>
	 * �ݒ肳��Ă��Ȃ��ꍇ��-1.
	 * 
	 * @return �C���f�b�N�X�ԍ�
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * �C���f�b�N�X�ԍ���ݒ肷��.
	 * 
	 * @param index �C���f�b�N�X�ԍ�
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TCheckBoxRenderer(tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TCheckBoxEditor(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getRowIndex()
	 */
	public int getRowIndex() {
		return getIndex();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setRowIndex(int)
	 */
	public void setRowIndex(int rowIndex) {
		setIndex(rowIndex);

	}

	/**
	 * Enter�Ńt�H�[�J�X�ړ��\���ǂ���
	 * 
	 * @return true:�t�H�[�J�X�ړ��\
	 */
	public boolean isEnterFocusable() {
		return isEnterFocusable;
	}

	/**
	 * Enter�Ńt�H�[�J�X�ړ��\���ǂ���
	 * 
	 * @param isEnterFocusable true:�t�H�[�J�X�ړ��\
	 */
	public void setEnterFocusable(boolean isEnterFocusable) {
		this.isEnterFocusable = isEnterFocusable;
	}

	/**
	 * ����������align getter
	 * 
	 * @return ����������align
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	/**
	 * TTable��index
	 * 
	 * @return TTable��index
	 */
	public int getModelIndex() {
		return modelIndex;
	}

	/**
	 * TTable��index
	 * 
	 * @param modelIndex TTable��index
	 */
	public void setModelIndex(int modelIndex) {
		this.modelIndex = modelIndex;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#isTableCellEditor()
	 */
	public boolean isTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setTableCellEditor(boolean)
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		this.tableCellEditor = tableCellEditor;
	}
}
