package jp.co.ais.trans2.common.objsave;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��ԕۑ�Main�p�l��
 * 
 * @author AIS
 */
public abstract class TMainObjectSavePanel extends TMainPanel {

	/** true:�ꎞ�ۑ��@�\�L�� */
	public static boolean isUseObjectSave = ClientConfig.isFlagOn("trans.use.temp.save");

	/** ���C���p�l�� */
	public TDnDPanel pnlDnDMain;

	/** �ۑ��p���X�g */
	public TDnDList lstDnDSave;

	/**
	 * �R���X�g���N�^.
	 */
	public TMainObjectSavePanel() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company
	 */
	public TMainObjectSavePanel(Company company) {
		super(company);
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {
		super.init();

		if (!isUseObjectSave) {
			// �����̏ꍇ�A�����s�v
			return;
		}

		removeAll();

		// �ۑ��p�p�l��
		this.pnlDnDMain = createDnDPanel();
		this.lstDnDSave = createDnDList();

		pnlDnDMain.setMinimumSize(new Dimension(0, 0));
		lstDnDSave.setMinimumSize(new Dimension(0, 0));

		lstDnDSave.setPreferredSize(new Dimension(50, 800));

		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlDnDMain, lstDnDSave);
		jSplitPane.setContinuousLayout(true);
		jSplitPane.setOneTouchExpandable(true);
		jSplitPane.setDividerSize(6);
		jSplitPane.setDividerLocation(2000);

		gc = new GridBagConstraints();

		pnlDnDMain.setLayout(new GridBagLayout());

		// �w�b�_�[�̈�
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlDnDMain.add(pnlHeader, gc);

		// ���E��
		gc.gridy = 1;
		pnlDnDMain.add(sep, gc);

		// �{�f�B�̈�
		gc.gridy = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlDnDMain.add(pnlBody, gc);

		setLayout(new BorderLayout());
		this.add(jSplitPane, BorderLayout.CENTER);
	}

	/**
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);
	}

	/**
	 * DnDList����
	 * 
	 * @return DnDList
	 */
	protected TDnDList createDnDList() {
		return new TDnDList();
	}

	/**
	 * DnDPanel����
	 * 
	 * @return DnDPanel
	 */
	protected TDnDPanel createDnDPanel() {
		return new TDnDPanel();
	}

	/**
	 * �ۑ��p���X�i�[�ݒ�
	 * 
	 * @param listener ���X�i�[
	 */
	public void addSaveListener(TObjectSaveListener listener) {
		if (!isUseObjectSave) {
			// �����̏ꍇ�A�����s�v
			return;
		}

		listener.setPanel(this);
		listener.readObjectList();
	}
}
