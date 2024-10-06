package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;

/**
 * �^�u�y�C��
 */
@Deprecated
public class TTabbedPane extends JTabbedPane implements TInterfaceLangMessageID, TInterfaceTabControl {

	/** �^�C�g��ID(���X�g�`���ŕۑ�) */
	private String langMessageID = null;

	/** �^�u�� */
	private int tabControlNo = -1;

	/** �^�u�ړ��E�t�� */
	private boolean isTabEnabled = true;

	/**
	 * �R���X�g���N�^
	 * 
	 * @see javax.swing.JTabbedPane#JTabbedPane()
	 */
	public TTabbedPane() {
		super();

		this.init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @see javax.swing.JTabbedPane#JTabbedPane(int,int)
	 * @param tabPlacement
	 * @param tabLayoutPolicy
	 */
	public TTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);

		this.init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @see javax.swing.JTabbedPane#JTabbedPane(int)
	 * @param tabPlacement
	 */
	public TTabbedPane(int tabPlacement) {
		super(tabPlacement);

		this.init();
	}

	/**
	 * �R���X�g���N�^.<br>
	 * �w�肳�ꂽ���b�Z�[�WID����TPanel��ǉ�����.<br>
	 * ����𗘗p�����ꍇ�A�^�u�ɃR���|�[�l���g��z�u����ۂ́A<br>
	 * getComponent(index)�Ŏ����I�ɒǉ����ꂽTPanel���擾���Ĕz�u���邱�ƂƂȂ�B <br>
	 * <br>
	 * ��) <br>
	 * TTabbedPane tabPane = new TTabbedPane(new String[] { &quot;C00001&quot;, &quot;C00002&quot;, &quot;C00003&quot;
	 * }); <br>
	 * TPanel pnlBase = (TPanel) tabPane.getComponent(0);
	 * 
	 * @param langMessageIDs ���b�Z�[�WID���X�g
	 */
	public TTabbedPane(String[] langMessageIDs) {
		super();

		for (int i = 0; i < langMessageIDs.length; i++) {
			add(langMessageIDs[i], new TPanel());
		}

		setLangMessageIDs(langMessageIDs);

		this.init();
	}

	/**
	 * ����������
	 */
	private void init() {

		// �t�@���N�V�����L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});
	}

	/**
	 * �t�@���N�V�����L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 */
	private void handleKeyPressed(KeyEvent evt) {

		// �{�^����EnterKey�Ŏ��s���邽�߁A�ړ����Ȃ��B
		// TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
	}

	/**
	 * @deprecated setLangMessageIDs(String[])�𗘗p
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/**
	 * �^�u�^�C�g���̒P��ID���X�g��ݒ�
	 * 
	 * @param langMessageIDs ���b�Z�[�WID���X�g
	 */
	public void setLangMessageIDs(String[] langMessageIDs) {
		this.langMessageID = StringUtil.toDelimitString(langMessageIDs);
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
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#isTabEnabled()
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
	 * InputVerifier�Ή�
	 * 
	 * @see javax.swing.JTabbedPane#setSelectedIndex(int)
	 */
	@Override
	public void setSelectedIndex(int index) {
		Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

		// if no tabs are selected
		// -OR- the current focus owner is me
		// -OR- I request focus from another component and get it
		// then proceed with the tab switch
		if (getSelectedIndex() == -1 || comp == this || requestFocus(false)) {
			super.setSelectedIndex(index);
		}
	}

	/**
	 * ��ڃ^�u��I������
	 */
	public void selectFirstTab() {
		super.setSelectedIndex(0);
	}

}
