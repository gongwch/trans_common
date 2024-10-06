package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;

/**
 * �F�I���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TColorController extends TController {

	/** �F�I���R���|�[�l���g */
	protected TColor field;

	/** �F�I���_�C�A���O */
	protected TColorDialog dialog = null;

	/** �{�^���A�F�I���_�C�A���O�̃L���v�V���� */
	protected String caption = "";

	/** �I�𒆂̐F */
	protected Color color = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �F�I���R���|�[�l���g
	 */
	public TColorController(TColor field) {

		this.field = field;

		// ������
		init();

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * ��������
	 */
	protected void init() {

		// �C�x���g��ǉ�����
		addEvent();

		field.ctrlColor.setEditable(false);

	}

	/**
	 * �C�x���g��`
	 */
	protected void addEvent() {

		// �{�^������
		field.btn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btn_Click();
			}
		});

	}

	/**
	 * �_�C�A���O�̏�������
	 */
	protected void initDialog() {

		dialog = new TColorDialog(field.getParentFrame(), true);
		dialog.setTitle(caption);

		// �C�x���g��`
		addDialogEvent();

	}

	/**
	 * �ʑI���_�C�A���O�̃C�x���g��`
	 */
	protected void addDialogEvent() {

		// �m��{�^������
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnSettle_Click();
			}
		});

		// �߂�{�^������
		dialog.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnBack_Click();
			}
		});

	}

	/**
	 * �{�^������
	 */
	protected void btn_Click() {

		// �ʑI���_�C�A���O�擾
		if (dialog == null) {
			initDialog();
		}

		// �F���Z�b�g
		dialog.colorChooser.setColor(color);

		// �\��
		dialog.setVisible(true);

	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {
		color = dialog.colorChooser.getColor();
		setColor(dialog.colorChooser.getColor());
		dialog.setVisible(false);
	}

	/**
	 * [�߂�]�{�^������
	 */
	protected void btnBack_Click() {
		dialog.setVisible(false);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * caption�ݒ�
	 * 
	 * @param caption caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
		if (dialog != null) {
			dialog.setTitle(caption);
		}
		if (field.btn != null) {
			field.btn.setLangMessageID(caption);
		}
	}

	/**
	 * �F���Z�b�g����
	 * 
	 * @param color �F
	 */
	public void setColor(Color color) {
		this.color = color;
		field.ctrlColor.setBackground(color);
	}

	/**
	 * �I�����ꂽ�F��Ԃ�
	 * 
	 * @return �I�����ꂽ�F
	 */
	public Color getColor() {
		return color;
	}

}
