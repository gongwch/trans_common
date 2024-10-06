package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.gui.*;

/**
 * Tab�R���|�[�l���g
 * 
 * @author AIS
 */
public class TTabbedPane extends JTabbedPane implements TInterfaceTabControl {

	/** �^�u�̍��� */
	protected int tabHeight = 0;

	/** �^�u�̕� */
	protected int tabWeight = 0;

	/** �^�u�� */
	protected int tabControlNo = -1;

	/** �^�u�ړ��E�t�� */
	protected boolean isTabEnabled = true;

	/** [�~]�{�^����������CallBackListener */
	protected List<CallBackListener> cancelButtonCloseCallBackListener;

	/**
	 * �R���X�g���N�^.
	 */
	public TTabbedPane() {
		super();
		setOpaque(true);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param tabPlacement �^�u�ʒu
	 */
	public TTabbedPane(int tabPlacement) {
		super(tabPlacement);
		setOpaque(true);
	}

	/**
	 * [�~]�{�^����������CallBackListener��ǉ�����
	 * 
	 * @param c CallBackListener
	 */
	public void addCancelButtonCloseCallBackListener(CallBackListener c) {
		if (cancelButtonCloseCallBackListener == null) {
			cancelButtonCloseCallBackListener = new ArrayList<CallBackListener>();
		}
		cancelButtonCloseCallBackListener.add(c);
	}

	/**
	 * �^�u��ǉ�����
	 * 
	 * @param title �^�u�̃^�C�g��
	 * @param content �^�u�ɃZ�b�g����R���|�[�l���g
	 * @param isClosable ����{�^����ǉ����邩
	 */
	public void addTab(String title, final Component content, boolean isClosable) {

		if (!isClosable) {
			super.addTab(title, content);
			return;
		}

		TTabbedTitle tabPanel = new TTabbedTitle(title);
		tabPanel.btn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				if (cancelButtonCloseCallBackListener != null) {
					for (CallBackListener c : cancelButtonCloseCallBackListener) {
						c.before(content);
					}
				}

				// �^�u�����
				removeTabAt(indexOfComponent(content));

				if (cancelButtonCloseCallBackListener != null) {
					for (CallBackListener c : cancelButtonCloseCallBackListener) {
						c.after(content);
					}
				}

			}
		});

		super.addTab("", content);
		setTabComponentAt(getTabCount() - 1, tabPanel);
	}

	/**
	 * @param tabIndex
	 * @return TAB
	 */
	public TTabbedTitle getTab(int tabIndex) {
		return (TTabbedTitle) getTabComponentAt(tabIndex);
	}

	/**
	 * InputVerifier�Ή��Ȃ���
	 * 
	 * @param index
	 */
	public void setSelectedIndexNI(int index) {
		super.setSelectedIndex(index);
	}

	/**
	 * InputVerifier�Ή�
	 * 
	 * @see javax.swing.JTabbedPane#setSelectedIndex(int)
	 */
	@Override
	public void setSelectedIndex(int index) {
		Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

		if (getSelectedIndex() == -1 || comp == this || requestFocus(false)) {
			if (comp != null) {
				comp.requestFocus();
			}

			super.setSelectedIndex(index);
		}
	}

	/**
	 * �^�u����ݒ肷��
	 * 
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * �^�u�����擾����
	 * 
	 * @param no
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * �^�u�ړ��E�t�����擾����
	 * 
	 * @return boolean
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * �^�u�ړ��E�t����ݒ肷��
	 * 
	 * @param bool
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	/**
	 * �^�u�ؑ։\�ɂ���
	 */
	public void switchMode() {
		TTabbedPainSwitchModel switchModel = new TTabbedPainSwitchModel();
		switchModel.accept(this);
	}
}
