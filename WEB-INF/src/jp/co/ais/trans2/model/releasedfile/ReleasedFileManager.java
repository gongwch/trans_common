package jp.co.ais.trans2.model.releasedfile;

import jp.co.ais.trans.common.except.*;

/**
 * MG0029ReleasedFileMaster - �����[�X�t�@�C���ꗗ - Interface Class
 * 
 * @author AIS
 */
public interface ReleasedFileManager {

	/**
	 * Excel�o��
	 * 
	 * @return �����[�X�t�@�C���ꗗ
	 * @throws TException
	 */
	public byte[] getExcel() throws TException;

}