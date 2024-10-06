package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import jp.co.ais.plaf.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * TDialog2
 */
public abstract class TDialog extends jp.co.ais.trans.common.gui.TDialog {

	/** �_�C�A���O�x�[�X�p�l�� */
	protected TTabbedPanel pnlDialogBase = null;

	/** �w�b�_�[ */
	protected TPanel pnlHeader = null;

	/** ���E�� */
	protected JSeparator sep;

	/** ��Ɨ̈� */
	protected TPanel pnlBody = null;

	/** GridBagConstraints */
	protected GridBagConstraints gc = null;

	/**  */
	protected final int HEADER_LEFT_X = 30;

	/**  */
	protected final int HEADER_Y = 10;

	/**  */
	protected final int HEADER_MARGIN_X = 5;

	/**  */
	protected final int HEADER_MARGIN_RIGHT_X = 15;

	/**  */
	protected final int COMPONENT_MARGIN_Y = 5;

	/**  */
	protected Color editModeColor = new Color(255, 170, 60);

	/** ��Џ�� */
	protected Company company = null;

	/** �����������p�\�ȃp�����[�^ */
	public Object paramObj = null;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public TDialog(Company company, Frame parent, boolean mordal) {
		this(company, parent, mordal, null);
	}

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public TDialog(Company company, Dialog parent, boolean mordal) {
		this(company, parent, mordal, null);
	}

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 * @param paramObj �����������p�\�ȃp�����[�^
	 */
	public TDialog(Company company, Frame parent, boolean mordal, Object paramObj) {
		super(parent, mordal);
		this.company = company;
		this.paramObj = paramObj;

		init();
		initComponents();
		allocateComponents();
		setTabIndex();
		initDialog();
	}

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 * @param paramObj �����������p�\�ȃp�����[�^
	 */
	public TDialog(Company company, Dialog parent, boolean mordal, Object paramObj) {
		super(parent, mordal);
		this.company = company;
		this.paramObj = paramObj;

		init();
		initComponents();
		allocateComponents();
		setTabIndex();
		initDialog();
	}

	/**  */
	public class TPanelHeader extends TPanel {

		/** �O���f�[�V�����n�_�F */
		protected Color startColor = Color.white;

		/** �O���f�[�V�����I�_�F */
		protected Color endColor = new Color(210, 210, 202);

		@Override
		public void paintComponent(Graphics g) {

			if (TGradientPaint.isFlat) {
				endColor = ColorHelper.brighter(getBackground(), 25);
			}

			Graphics2D g2 = (Graphics2D) g;

			TGradientPaint gradient = new TGradientPaint(getWidth() / 2, 0.0f, startColor, getWidth() / 2, getHeight(),
				endColor);
			g2.setPaint(gradient);

			g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

		}

	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TDialog(Frame parent, boolean mordal) {
		this(null, parent, mordal);
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TDialog(Dialog parent, boolean mordal) {
		this(null, parent, mordal);
	}

	/**
	 * ������
	 */
	protected void init() {

		gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setResizable(true);

		// �w�b�_�[�̈�
		pnlHeader = new TPanelHeader();
		pnlHeader.setLayout(null);
		pnlHeader.setMaximumSize(new Dimension(0, 40));
		pnlHeader.setMinimumSize(new Dimension(0, 40));
		pnlHeader.setPreferredSize(new Dimension(0, 40));

		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlHeader, gc);

		// ���E��
		sep = new JSeparator();
		TGuiUtil.setComponentHeight(sep, 2);
		gc.gridy = 1;
		add(sep, gc);

		// �{�f�B�̈�
		pnlBody = new TPanel();
		pnlBody.setLayout(new GridBagLayout());
		gc.gridy = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBody, gc);

	}

	/**
	 * @return �_�C�A���O�x�[�X�p�l��
	 */
	public TTabbedPanel getDialogBasePanel() {
		return null;
	}

	/**
	 * �R���|�[�l���g�̏������B��ɃC���X�^���X�̐������s���܂��B
	 */
	public abstract void initComponents();

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	public abstract void allocateComponents();

	/**
	 * �R���|�[�l���g�Ƀ^�u����ݒ肵�܂��B
	 */
	public abstract void setTabIndex();

	/**
	 * �ҏW���[�h�ɂ���
	 */
	public void setEditMode() {
		// pnlHeader.setBackground(editModeColor);
	}

}
