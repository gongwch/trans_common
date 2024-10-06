package jp.co.ais.trans.common.bizui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �X�V�敪�t�B�[���h
 * 
 * @author AIS
 */
public class TUpdateDivisionField extends TPanel {

	/** UID */
	private static final long serialVersionUID = -9181170060878639499L;

	/** �{�^�����C�A�E�g */
	private int layoutType = VERTICAL;

	/** �� */
	public static final int HORIZONTAL = 1;

	/** �c */
	public static final int VERTICAL = 2;

	/** �o�^ */
	protected TCheckBox chkEntry;

	/** ���F */
	protected TCheckBox chkApprove;

	/** �x�[�X�p�l�� */
	protected TPanel pnlBase;

	/**
	 * @return pnlBase��߂��܂��B
	 */
	public TPanel getPnlBase() {
		return pnlBase;
	}

	/**
	 * @param pnlBase pnlBase��ݒ肵�܂��B
	 */
	public void setPnlBase(TPanel pnlBase) {
		this.pnlBase = pnlBase;
	}

	/**
	 * �R���X�g���N�^<br>
	 * ���O�C����ЃR�[�h�A�c�ō\�z.
	 */
	public TUpdateDivisionField() {
		this(VERTICAL);
	}

	/**
	 * �R���X�g���N�^<br>
	 * 
	 * @param buttonLayout �{�^�����C�A�E�g <br>
	 *            TItemLevelField.HORIZONTAL ������ <br>
	 *            TItemLevelField.VERTICAL �c����<br>
	 */
	public TUpdateDivisionField(int buttonLayout) {
		super();

		this.layoutType = buttonLayout;

		initComponents();

	}

	/**
	 * ���C�A�E�g�\��
	 */
	private void initComponents() {

		chkEntry = new TCheckBox();
		chkEntry.setLangMessageID("C01258");

		chkApprove = new TCheckBox();
		chkApprove.setLangMessageID("C01168");

		pnlBase = new TPanel();
		pnlBase.setLayout(new GridBagLayout());
		pnlBase.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlBase.setLangMessageID("C01069");

		GridBagConstraints gridBagConstraints;
		switch (layoutType) {
			case HORIZONTAL:
				// ������
				pnlBase.setMaximumSize(new Dimension(130, 45));
				pnlBase.setMinimumSize(new Dimension(130, 45));
				pnlBase.setPreferredSize(new Dimension(130, 45));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 5, 5, 0);
				pnlBase.add(chkEntry, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 10, 5, 0);
				pnlBase.add(chkApprove, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				add(pnlBase, gridBagConstraints);

				break;

			case VERTICAL:
				// �c����
				pnlBase.setMaximumSize(new Dimension(100, 80));
				pnlBase.setMinimumSize(new Dimension(100, 80));
				pnlBase.setPreferredSize(new Dimension(100, 80));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 10, 5, 30);
				pnlBase.add(chkEntry, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 1;
				gridBagConstraints.insets = new Insets(0, 10, 5, 30);
				pnlBase.add(chkApprove, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				add(pnlBase, gridBagConstraints);

				break;
		}
	}

	/**
	 * �o�^���I������Ă��邩�ǂ���
	 * 
	 * @return true:�I�����
	 */
	public boolean isChkEntrySelected() {
		return chkEntry.isSelected();
	}

	/**
	 * ���F���I������Ă��邩�ǂ���
	 * 
	 * @return true:�I�����
	 */
	public boolean isChkApproveSelected() {
		return chkApprove.isSelected();
	}

	/**
	 * �o�^�̐ݒ�
	 * 
	 * @param bol
	 */
	public void setChkApprove(boolean bol) {
		chkApprove.setSelected(bol);
	}

	/**
	 * ���F�̐ݒ�
	 * 
	 * @param bol
	 */
	public void setChkEntry(boolean bol) {
		chkEntry.setSelected(bol);
	}

	/**
	 * �^�u�ړ����ԍ���ݒ�
	 * 
	 * @param no �^�u���ԍ�
	 */
	public void setTabControlNo(int no) {
		chkEntry.setTabControlNo(no);
		chkApprove.setTabControlNo(no);
	}

	/**
	 * @return chkApprove��߂��܂��B
	 */
	public TCheckBox getChkApprove() {
		return chkApprove;
	}

	/**
	 * @param chkApprove chkApprove��ݒ肵�܂��B
	 */
	public void setChkApprove(TCheckBox chkApprove) {
		this.chkApprove = chkApprove;
	}

	/**
	 * @return chkEntry��߂��܂��B
	 */
	public TCheckBox getChkEntry() {
		return chkEntry;
	}

	/**
	 * @param chkEntry chkEntry��ݒ肵�܂��B
	 */
	public void setChkEntry(TCheckBox chkEntry) {
		this.chkEntry = chkEntry;
	}

}
