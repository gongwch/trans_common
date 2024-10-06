package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �t�H���_�Q��
 * 
 * @author nagahashi
 */
public class TFolderSettingField extends TButtonField {

	/** �t�@�C���`���[�U�[ */
	protected JFileChooser openChooser;

	/** �t�H���_�I����̃C�x���g���X�i */
	protected FolderSelectListener folderSelectListener = null;

	/**
	 * /** �R���X�g���N�^
	 */
	public TFolderSettingField() {
		this(new JFileChooser());
	}

	/**
	 * �R���X�g���N�^(�O���w��p)
	 * 
	 * @param chooser �t�@�C���`���[�U�[
	 */
	public TFolderSettingField(JFileChooser chooser) {
		super();

		this.openChooser = chooser;

		initComponents();
	}

	/**
	 * �\�z
	 */
	protected void initComponents() {

		// �`���[�U�[�̐ݒ�
		refleshChooser();

		// �T�C�Y�����l
		this.setButtonSize(100);
		this.setFieldSize(400);

		this.setLangMessageID("C10298");

		// ����
		this.setNoticeVisible(false);
		this.getField().setEditable(false);
		this.getField().setAllowedSpace(true);
		this.getField().setImeMode(true);

		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnActionPerformed();
			}
		});
	}

	/**
	 * Chooser�ݒ�
	 */
	protected void refleshChooser() {
		openChooser.setMultiSelectionEnabled(false);
		openChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		openChooser.setAcceptAllFileFilterUsed(false);
	}

	/**
	 * �{�^���������̏���
	 */
	protected void btnActionPerformed() {
		if (!new File(this.getValue()).exists()) {
			refleshChooser();
		}

		// �O��w�肵���f�B���N�g����ݒ肷��
		openChooser.setCurrentDirectory(new File(this.getValue()));

		int status = openChooser.showOpenDialog(TGuiUtil.getParentFrameOrDialog(this));

		if (status == JFileChooser.APPROVE_OPTION) {
			String folderName = openChooser.getSelectedFile().getAbsolutePath();
			this.setValue(folderName);
		}

		if (folderSelectListener != null) {
			folderSelectListener.folderSelected(status);
		}

	}

	/**
	 * �I���t�@�C���擾
	 * 
	 * @return �t�@�C��
	 */
	public File getSelectedFile() {
		if (isEmpty()) {
			return null;
		}

		return new File(getValue());
	}

	/**
	 * �t�@�C���`���[�U�[��ݒ�
	 * 
	 * @param openChooser
	 */
	public void setOpenChooser(JFileChooser openChooser) {
		this.openChooser = openChooser;
	}

	/**
	 * �t�@�C���`���[�U�[��Ԃ�
	 * 
	 * @return openChooser
	 */
	public JFileChooser getOpenChooser() {
		return this.openChooser;
	}

	/**
	 * �t�H���_�I����̃C�x���g���X�i��getter
	 * 
	 * @return �t�H���_�I����C�x���g���X�i
	 */
	public FolderSelectListener getFolderSelectListener() {
		return folderSelectListener;
	}

	/**
	 * �t�H���_�I����̃C�x���g���X�i��setter
	 * 
	 * @param folderSelectListener �t�H���_�I����C�x���g���X�i
	 */
	public void setFolderSelectListener(FolderSelectListener folderSelectListener) {
		this.folderSelectListener = folderSelectListener;
	}

}
