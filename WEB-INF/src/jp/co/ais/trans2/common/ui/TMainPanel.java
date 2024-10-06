package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

import jp.co.ais.plaf.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * Main�p�l��
 * 
 * @author AIS
 */
public abstract class TMainPanel extends TPanelBusiness {

	/** serialVersionUID */
	private static final long serialVersionUID = -3260639567895368281L;

	/** �w�b�_�[ */
	protected TPanel pnlHeader = null;

	/** ���E�� */
	protected JSeparator sep;

	/** ��Ɨ̈� */
	protected TPanel pnlBody = null;

	/**
	 * �e��ʃC���X�^���X�����L�[���B<br>
	 * �ۑ��A�������Ɏg�p����B
	 */
	protected String keyName = null;

	/** GridBagConstraints */
	protected GridBagConstraints gc = null;

	/** ��Џ�� */
	protected Company company = null;

	protected final int HEADER_LEFT_X = 30;

	protected final int HEADER_Y = 10;

	protected final int HEADER_MARGIN_X = 5;

	public TMainPanel() {
		this(null);
	}

	public TMainPanel(Company company) {
		this.company = company;
		init();
		initComponents();
		allocateComponents();
		setTabIndex();
		initPanel();
	}

	/**
	 * �w�b�_�p�l���w�i�̃O���f�[�V����
	 */
	class TPanelHeader extends TPanel {

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
	 * ���C���p�l���w�i�̃O���f�[�V����
	 */
	class TPanelBody extends TPanel {

		/** �O���f�[�V�����n�_�F */
		protected Color startColor = Color.white;

		/** �O���f�[�V�����n�_�F */
		protected Color endColor;

		@Override
		public void paintComponent(Graphics g) {

			if (TGradientPaint.isFlat) {
				super.paintComponent(g);
				return;
			}

			endColor = getBackground();

			Graphics2D g2 = (Graphics2D) g;

			// ��(��)�ˉ�(�F)
			TGradientPaint gradient = new TGradientPaint(getWidth() / 4, 0.0f, startColor, getWidth() / 4,
				getHeight() / 4, endColor);
			g2.setPaint(gradient);

			g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

		}

	}

	/**
	 * ������
	 */
	protected void init() {

		gc = new GridBagConstraints();

		setLayout(new GridBagLayout());

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
		sep.setMaximumSize(new Dimension(800, 3));
		sep.setMinimumSize(new Dimension(800, 3));
		sep.setPreferredSize(new Dimension(800, 3));
		gc.gridy = 1;
		add(sep, gc);

		// �{�f�B�̈�
		pnlBody = new TPanelBody();
		pnlBody.setLayout(new GridBagLayout());
		gc.gridy = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBody, gc);

		gc = new GridBagConstraints();
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
	 * @return keyName��߂��܂��B
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param keyName keyName��ݒ肵�܂��B
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

}
