package jp.co.ais.trans2.common.model.ui.slip;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �Y�t�{�^��
 */
public class TAttachButton extends TImageButton {

	/** �e�p�l�� */
	public TPanel parent = null;

	/** �e�p�l�� */
	public TDialog dialog = null;

	/** �R���g���[�� */
	public TAttachButtonCtrl controller = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param parent �e�p�l��
	 */
	public TAttachButton(TPanel parent) {

		this.parent = parent;
		controller = createController();

		allocateComponents();
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param dialog �e�_�C�A���O
	 */
	public TAttachButton(TDialog dialog) {

		this.dialog = dialog;
		controller = createController();

		allocateComponents();
	}

	/**
	 * �R���|�[�l���g������
	 */
	protected void allocateComponents() {
		setSize(16, 16);
		setFocusable(false);
	}

	/**
	 * �R���g���[���̍쐬
	 * 
	 * @return �R���g���[��
	 */
	protected TAttachButtonCtrl createController() {
		return new TAttachButtonCtrl(this);
	}

	/**
	 * �R���g���[���̎擾
	 * 
	 * @return �R���g���[��
	 */
	public TAttachButtonCtrl getController() {
		return controller;
	}

	/**
	 * @param icon
	 */
	@Override
	public void setIconType(IconType icon) {
		super.setIconType(icon);
		this.repaint();
	}

	/**
	 * �Y�t�t�@�C���̎擾
	 * 
	 * @return files �Y�t�t�@�C��
	 */
	public List<SWK_ATTACH> getAttachments() {
		return controller.getAttachments();
	}

	/**
	 * �Y�t�t�@�C���̐ݒ�
	 * 
	 * @param attachments �Y�t�t�@�C�����
	 */
	public void setAttachments(List<SWK_ATTACH> attachments) {
		controller.setAttachments(attachments);
	}

	/**
	 * �Y�t�t�@�C���̍폜
	 * 
	 * @param list �Y�t�t�@�C�����
	 */
	public void removeAttachment(List<SWK_ATTACH> list) {
		controller.removeAttachment(list);
	}

	/**
	 * �t�@�C���ǉ�
	 * 
	 * @param filename �L�[
	 * @param file �o�C�i��
	 */
	public void addFile(String filename, File file) {
		controller.addFile(filename, file);
	}

	/**
	 * �t�@�C���ǉ�<br>
	 * File���ڑ��M���g���Ă�������
	 * 
	 * @param filename �L�[
	 * @param data �o�C�i��
	 */
	@Deprecated
	public void addFile(String filename, byte[] data) {
		controller.addFile(filename, data);
	}

	/**
	 * �N���A����
	 */
	public void clear() {
		controller.clear();
	}

}
