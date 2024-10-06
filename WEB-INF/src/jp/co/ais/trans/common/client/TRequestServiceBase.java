package jp.co.ais.trans.common.client;

import java.io.*;

/**
 * ���ʒʐM�T�[�r�X�̊�N���X
 */
public class TRequestServiceBase {

	private final static String SERVICE_NAME = "common/";

	/** server access object. */
	protected TRequestBase access = null;

	protected String entry = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param access ���N�G�X�g�N���X
	 * @param entry �G���g���[
	 */
	protected TRequestServiceBase(TRequestBase access, String entry) {
		super();
		this.access = access;
		this.entry = entry;
	}

	/**
	 * set access object.
	 * 
	 * @param access ���N�G�X�g�N���X
	 */
	public void setRequestBase(TRequestBase access) {
		this.access = access;
	}

	/**
	 * request
	 * 
	 * @return false:���s
	 * @throws IOException
	 */
	protected boolean request() throws IOException {

		if (this.access == null) return false;

		boolean b = access.request(SERVICE_NAME + entry);

		return b;
	}
}
