package jp.co.ais.trans2.model.releasedfile;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * MG0029ReleasedFile - �����[�X�t�@�C���ꗗ
 * 
 * @author AIS
 */
public class ReleasedFile extends TransferBase {

	/** �t�H���_�� */
	protected String folder = null;

	/** �t�@�C���� */
	protected String file = null;

	/** �X�V���t */
	protected Date update = null;

	/** �T�C�Y */
	protected long size;

	/**
	 * �t�H���_���̎擾
	 * 
	 * @return folder �t�H���_��
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * �t�H���_���̐ݒ�
	 * 
	 * @param folder �t�H���_��
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}

	/**
	 * �t�@�C�����̎擾
	 * 
	 * @return file �t�@�C����
	 */
	public String getFile() {
		return file;
	}

	/**
	 * �t�@�C�����̐ݒ�
	 * 
	 * @param file �t�@�C����
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * �X�V���t�̎擾
	 * 
	 * @return update �X�V���t
	 */
	public Date getUpdate() {
		return update;
	}

	/**
	 * �X�V���t�̐ݒ�
	 * 
	 * @param update �X�V���t
	 */
	public void setUpdate(Date update) {
		this.update = update;
	}

	/**
	 * �T�C�Y�̎擾
	 * 
	 * @return size �T�C�Y
	 */
	public long getSize() {
		return size;
	}

	/**
	 * �T�C�Y�̐ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setSize(long size) {
		this.size = size;
	}
}
