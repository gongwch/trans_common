package jp.co.ais.trans.common.client;

import java.io.*;

import jp.co.ais.trans.common.log.*;

/**
 * ���j���[(�v���O����)�؂�ւ��ʒm
 */
public class TRequestProgram extends TRequestServiceBase {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param access ���N�G�X�g
	 */
	public TRequestProgram(TRequestBase access) {
		super(access, "program");
	}

	/**
	 * �J�n
	 * 
	 * @param prgCode
	 * @return true:����
	 * @throws IOException
	 */
	public boolean start(String prgCode) throws IOException {
		return this.request("start", prgCode);
	}

	/**
	 * �I���i�v���O�����w��j
	 * 
	 * @param prgCode
	 * @return true:����
	 * @throws IOException
	 */
	public boolean endPrg(String prgCode) throws IOException {
		return this.request("endPrg", prgCode);
	}

	/**
	 * �I��
	 * 
	 * @param prgCode
	 * @return true:����
	 * @throws IOException
	 */
	public boolean end(String prgCode) throws IOException {
		return this.request("end", prgCode);
	}

	/**
	 * �T�[�r�X�Ăяo��.
	 * 
	 * @param status
	 * @param prgCode
	 * @return true:����
	 * @throws IOException
	 */
	private boolean request(String status, String prgCode) throws IOException {

		super.access.addSendValues("status", status);
		super.access.addSendValues("prgCode", prgCode);

		if (!super.request()) {
			ClientLogger.error("request error:" + access.getErrorMessage());
			return false;
		}

		if (!super.access.isNoDataResponse()) {
			return false;
		}

		return true;
	}

}