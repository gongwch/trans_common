package jp.co.ais.trans.common.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * JList�ɁA�^�u���C���^�[�t�F�C�X��ǉ�����ListBox.
 */
public class TListBox extends JList implements TInterfaceTabControl {

	private int tabControlNo = -1;

	private boolean isTabEnabled = true;

	/**
	 * Constructor.
	 */
	public TListBox() {
		super();

		init();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param model ���f��
	 */
	public TListBox(ListModel model) {
		super(model);

		init();
	}

	/**
	 * ��������
	 */
	protected void init() {

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

		TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
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
}
