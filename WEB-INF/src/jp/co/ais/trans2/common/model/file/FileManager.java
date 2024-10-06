package jp.co.ais.trans2.common.model.file;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.file.*;

/**
 * �t�@�C���Ǘ��C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface FileManager {

	/**
	 * �t�@�C���̗������Ƃ�
	 * 
	 * @param file �t�@�C��
	 * @throws TException
	 */
	public void recordFile(TFile file) throws TException;

	/**
	 * �w��t�@�C���̍ŏI�捞������Ԃ�
	 * 
	 * @param file �t�@�C��
	 * @return file
	 * @throws TException
	 */
	public TFile getLastRecordFile(TFile file) throws TException;

	/**
	 * �w��f�[�^���G�N�Z���ŏo��
	 * 
	 * @param companyCode
	 * @param key
	 * @return �捞�����f�[�^
	 * @throws TException
	 */
	public byte[] getExcel(String companyCode, String key) throws TException;

}
