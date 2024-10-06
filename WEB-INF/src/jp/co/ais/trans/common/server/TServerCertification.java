package jp.co.ais.trans.common.server;

import java.io.*;

/**
 * �F�؏�Ԃ��Ǘ�����.
 * 
 * @author ais-y numazawa
 */
public class TServerCertification extends Object implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * �F�؏�� true:�F�� false:�F�؂���Ă��Ȃ�
	 */
	private boolean certify = false;

	/**
	 * �F�؂̐ݒ�.
	 * 
	 * @param valid
	 */
	public void setCertified(boolean valid) {
		this.certify = valid;
	}

	/**
	 * �F�؏�ԎQ��.
	 * 
	 * @return true:���F
	 */
	public boolean isCertified() {
		return this.certify;
	}

	// ******************************************
	// impliments sirealizatable
	// ******************************************

	/**
	 * ���� (reflection method)
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {

		// �f�t�H���g�̒��񉻃v���Z�X
		out.defaultWriteObject();

		// �ǉ��̒��񉻏���
		out.writeBoolean(this.certify);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

		// �f�t�H���g�̒��񉻃v���Z�X
		in.defaultReadObject();

		// �ǉ��̒��񉻏���
		this.certify = in.readBoolean();
	}
}
