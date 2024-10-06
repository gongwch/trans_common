package jp.co.ais.trans2.common.gui;

import java.awt.*;

import jp.co.ais.trans.common.gui.TButton;
import jp.co.ais.trans.common.gui.TPanel;
import jp.co.ais.trans.common.gui.TTextField;

/**
 * �F�I���R���|�[�l���g
 * @author AIS
 *
 */
public class TColor extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -4547408225937169080L;

	/** �{�^�� */
	public TButton btn;

	/** �F�\���t�B�[���h */
	public TTextField ctrlColor;

	/** �R���g���[�� */
	protected TColorController controller;

	/**
	 * 
	 *
	 */
	public TColor() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		// �R���g���[��
		controller = new TColorController(this);

	}

	/**
	 * �R���|�[�l���g������������<BR>
	 *
	 */
	protected void initComponents() {
		btn = new TButton();
		ctrlColor = new TTextField();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 *
	 */
	protected void allocateComponents() {

		setLayout(null);

		// �{�^��
		btn.setSize(20, 80);
		btn.setLocation(0, 0);
		add(btn);

		// �F
		ctrlColor.setSize(80, 20);
		ctrlColor.setLocation(80, 0);
		add(ctrlColor);

		int width = btn.getWidth() + ctrlColor.getWidth();

		setSize(width, 20);

	}

	/**
	 * �{�^���A�F�I���_�C�A���O�̃L���v�V�����ݒ�
	 * @param caption �L���v�V����
	 */
	public void setCaption(String caption) {
		controller.setCaption(caption);
	}

	/**
	 * �F���Z�b�g����
	 * @param color �F
	 */
	public void setColor(Color color) {
		controller.setColor(color);
	}

	/**
	 * �I�����ꂽ�F��Ԃ�
	 * @return �I�����ꂽ�F 
	 */
	public Color getColor() {
		return controller.getColor();
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		btn.setTabControlNo(tabControlNo);
	}

}
