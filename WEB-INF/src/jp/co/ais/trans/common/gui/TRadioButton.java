package jp.co.ais.trans.common.gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * JRadioButton�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ�����RadioButton. TTable�̃Z���ɃZ�b�g�ł���悤�ɒǉ�
 */
public class TRadioButton extends JRadioButton implements TInterfaceLangMessageID, TInterfaceTabControl,
	TTableComponent {

	/** �V���A��UID */
	private static final long serialVersionUID = -2066831109211249798L;

	private String langMessageID = null;

	private int tabControlNo = -1;

	private boolean isTabEnabled = true;

	/** �ݒ�Index�ԍ� */
	private int index = -1;

	/** �ėpINDEX�L�[ */
	protected int rowIndex;

	/** Column�L�[ */
	protected int colIndex;

	/** �{�^���O���[�v */
	public List<Integer> groupList;

	/**
	 * �R���X�g���N�^�[
	 */
	public TRadioButton() {
		this("");

		groupList = new ArrayList<Integer>();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param index �C���f�b�N�X�ԍ�
	 */
	public TRadioButton(int index) {
		this("");

		this.index = index;
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param langMessageID �P��ID
	 */
	public TRadioButton(String langMessageID) {
		this(langMessageID, false);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param langMessageID �P��ID
	 * @param selected �I�����
	 */
	public TRadioButton(String langMessageID, boolean selected) {
		super(langMessageID, selected);

		this.langMessageID = langMessageID;

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
	private void handleKeyPressed(KeyEvent evt) {

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
	 * @param e
	 */
	public void addGroup(Enum e) {
		groupList.add(e.ordinal());
	}

	/**
	 * �N���A
	 */
	public void clearGroup() {
		groupList.clear();
	}

	/**
	 * @return �O���[�v
	 */
	public List<Integer> getGroup() {
		return groupList;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new RadioButtonRenderer();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {
		return new RadioButtonEditor(this);
	}

	/**
	 * @return ��ԍ�
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * ��ԍ�
	 * 
	 * @param index
	 */
	public void setColIndex(int index) {
		colIndex = index;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getRowIndex()
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setRowIndex(int)
	 */
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getDefaultCellRendererHorizontalAlignment()
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#isTableCellEditor()
	 */
	public boolean isTableCellEditor() {
		return false;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setTableCellEditor(boolean)
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		//
	}

}
