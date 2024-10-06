package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �Ȗڂ̕\�����x���I���R���|�[�l���g
 * 
 * @author AIS
 */
public class TItemLevelChooser extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 197804963583733563L;

	/** �Ȗ� */
	public TRadioButton rdoItem;

	/** �⏕�Ȗ� */
	public TRadioButton rdoSubItem;

	/** ����Ȗ� */
	public TRadioButton rdoDetailItem;

	/** �\������ */
	protected int direction = SwingConstants.VERTICAL;

	/** �^�C�g���\�� */
	protected boolean title = false;

	/** �R���g���[�� */
	protected TItemLevelChooserController controller;

	/**
	 * 
	 *
	 */
	public TItemLevelChooser() {
		this(SwingConstants.VERTICAL);
	}

	/**
	 * @param direction �^�C�v
	 */
	public TItemLevelChooser(int direction) {

		this(direction, false);

	}

	/**
	 * @param title �^�C�g�����\�����ǂ���
	 */
	public TItemLevelChooser(boolean title) {
		this(SwingConstants.VERTICAL, title);
	}

	/**
	 * @param direction �\������
	 * @param title �^�C�g�����\�����ǂ���
	 */
	public TItemLevelChooser(int direction, boolean title) {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents(direction, title);

		// �����ݒ�
		this.direction = direction;

		// �^�C�g���\��
		this.title = title;

		// �R���g���[������
		controller = new TItemLevelChooserController(this);

	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		rdoItem = new TRadioButton();
		rdoSubItem = new TRadioButton();
		rdoDetailItem = new TRadioButton();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 * 
	 * @param direction1 �z�u����
	 * @param title1 �^�C�g���\�����ǂ�
	 */
	protected void allocateComponents(int direction1, boolean title1) {

		if (title1) {
			setLangMessageID("C00906"); // �\�����x��
		} else {
			setBorder(TBorderFactory.createTitledBorder(TModelUIUtil.getWord("C00906"))); // �\�����x��
			this.titlePanel.setSize(0, 0);
			this.titlePanel.setVisible(false);
		}

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoItem);
		bg.add(rdoSubItem);
		bg.add(rdoDetailItem);

		if (direction1 == SwingConstants.VERTICAL) {

			setSize(130, 85);

			// �Ȗ�
			rdoItem.setSize(110, 20);
			rdoItem.setLangMessageID("C00077"); // �Ȗ�
			if (title1) {
				rdoItem.setLocation(15, 5);
			} else {
				rdoItem.setLocation(15, 15);
			}
			add(rdoItem);

			// �⏕�Ȗ�
			rdoSubItem.setSize(110, 20);
			rdoSubItem.setLangMessageID("C00488"); // �⏕�Ȗ�
			if (title1) {
				rdoSubItem.setLocation(15, 25);
			} else {
				rdoSubItem.setLocation(15, 35);
			}
			add(rdoSubItem);

			// ����Ȗ�
			rdoDetailItem.setSize(110, 20);
			rdoDetailItem.setLangMessageID("C00025"); // ����Ȗ�
			if (title1) {
				rdoDetailItem.setLocation(15, 45);
			} else {
				rdoDetailItem.setLocation(15, 55);
			}
			add(rdoDetailItem);

		} else {

			this.setLayout(new GridBagLayout());

			GridBagConstraints gc = new GridBagConstraints();
			int top = 5;

			this.mainPanel.setLayout(null);
			setSize(310, 50);

			// �Ȗ�
			TGuiUtil.setComponentSize(rdoItem, 90, 20);
			rdoItem.setLangMessageID("C00077"); // �Ȗ�
			if (!title1) {
				top = 0;
			}
			gc.insets = new Insets(top, 0, 0, 0);
			gc.gridx = 0;
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
			add(rdoItem, gc);

			// �⏕�Ȗ�
			TGuiUtil.setComponentSize(rdoSubItem, 90, 20);
			rdoSubItem.setLangMessageID("C00488"); // �⏕�Ȗ�
			gc.insets = new Insets(top, 0, 0, 0);
			gc.gridx = 1;
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
			add(rdoSubItem, gc);

			// ����Ȗ�
			TGuiUtil.setComponentSize(rdoDetailItem, 80, 20);
			rdoDetailItem.setLangMessageID("C00025"); // ����Ȗ�
			gc.insets = new Insets(top, 0, 0, 0);
			gc.gridx = 2;
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
			add(rdoDetailItem, gc);
		}

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoItem.setTabControlNo(tabControlNo);
		rdoSubItem.setTabControlNo(tabControlNo);
		rdoDetailItem.setTabControlNo(tabControlNo);
	}

	/**
	 * �\���̕�����Ԃ�(�c/��)
	 * 
	 * @return �\���̕���(�c/��)
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * �I�����ꂽ�Ȗڃ��x����Ԃ�
	 * 
	 * @return �I�����ꂽ�Ȗڃ��x��
	 */
	public ItemLevel getItemLevel() {
		return controller.getItemLevel();
	}

	/**
	 * �Ȗڃ��x�����Z�b�g����
	 * 
	 * @param itemLevel �Ȗڃ��x��
	 */
	public void setItemLevel(ItemLevel itemLevel) {
		controller.setItemLevel(itemLevel);
	}

	/**
	 * �^�C�g���\�����ǂ�����Ԃ�
	 * 
	 * @return �\�����ǂ���
	 */
	public boolean getTitle() {
		return title;
	}

	/**
	 * Enabled����
	 */
	@Override
	public void setEnabled(boolean bln) {
		rdoItem.setEnabled(bln);
		rdoSubItem.setEnabled(bln);
		rdoDetailItem.setEnabled(bln);
	}

}
