package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �^�C�g������p�l��
 */
public class TTitlePanel extends TPanel {

	/** �R���e���cPanel */
	public TPanel mainPanel = new TPanel();

	/** �^�C�g��Panel */
	public TImagePanel titlePanel = new TImagePanel();

	/** �^�C�g��Label */
	public TLabel titleLabel = new TLabel();

	/** �\�����邩�ǂ��� */
	public boolean isTitleVisible = false;

	/**
	 * �R���X�g���N�^.
	 */
	public TTitlePanel() {
		super();
		initComponent();
	}

	/**
	 * ��ʍ\�z
	 */
	public void initComponent() {

		GridBagConstraints gridBagConstraints;

		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.lightGray));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		titlePanel = new TImagePanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		titlePanel.setMaximumSize(new Dimension(0, 20));
		titlePanel.setMinimumSize(new Dimension(0, 20));
		titlePanel.setPreferredSize(new Dimension(0, 20));

		// �w�i�}��ݒ肷��
		setImage();

		add(titlePanel, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		titlePanel.add(titleLabel, gridBagConstraints);

		mainPanel = new TPanel();
		mainPanel.setMaximumSize(new Dimension(0, 0));
		mainPanel.setMinimumSize(new Dimension(0, 0));
		mainPanel.setPreferredSize(new Dimension(0, 0));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		add(mainPanel, gridBagConstraints);
		mainPanel.setLayout(null);
		mainPanel.setOpaque(true);
		mainPanel.setBackground(Color.WHITE);

	}

	@Override
	public void setLangMessageID(String langMessageID) {

		titleLabel.setLangMessageID("  "
			+ MessageUtil.getWord(TClientLoginInfo.getInstance().getUserLanguage(), langMessageID));

		setTitleVisible(true);

	}

	/**
	 * mainPanel���擾����
	 * 
	 * @return mainPanel
	 */
	public TPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * �w�i�}�̐ݒ�
	 */
	public void setImage() {

		String prefix = "title_";
		if (TGradientPaint.isFlat) {
			prefix = "title_flat_";
		}

		try {
			LookAndFeelColor color = TLoginInfo.getTitleColor();

			// �^�C�g�������̐F
			switch (color) {
				case White:
					titleLabel.setForeground(Color.black);
					break;

				default:
					titleLabel.setForeground(Color.white);
					break;
			}

			String fileName = "images/" + prefix + color.toString().toLowerCase() + ".png";
			titlePanel.setImageIcon(ResourceUtil.getImage(TTitlePanel.class, fileName));

		} catch (Exception e) {
			titlePanel.setImageIcon(ResourceUtil.getImage(TTitlePanel.class, "images/" + prefix + "white.png"));
		}
	}

	/**
	 * �^�C�g���\���t���O�ݒ�iL&F�ύX����p�Atrue�F�^�C�g���ύX false�F�ύX���Ȃ��j
	 * 
	 * @param isTitleVisible
	 */
	public void setTitleVisible(boolean isTitleVisible) {
		this.isTitleVisible = isTitleVisible;
	}

	@Override
	public Component add(Component comp) {

		if (comp instanceof JComponent) {
			((JComponent) comp).setOpaque(false);
		}
		return mainPanel.add(comp);
	}
}
