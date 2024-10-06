package jp.co.ais.trans2.common.model.file;

import java.util.List;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.file.TFile;

/**
 * �t�@�C�������Ǘ�Dao
 * @author AIS
 *
 */
public interface FileRecordDao {

	/**
	 * �w��t�@�C�����̎捞������Ԃ�
	 * @param file �t�@�C��
	 * @throws TException
	 */
	public List<TFile> getRecordFile(TFile file) throws TException;

	/**
	 * �w��t�@�C�����̍Ō�Ɏ�荞�܂ꂽ������Ԃ�
	 * @param companyCode ��ЃR�[�h
	 * @param key �捞�V�X�e���v���C�}���L�[
	 * @param fileName �t�@�C����
	 * @return �w��t�@�C�����̍Ō�Ɏ�荞�܂ꂽ����
	 * @throws TException
	 */
	public TFile getLastRecordFile(TFile file) throws TException;

	/**
	 * �t�@�C���̗������Ƃ�
	 * @param file
	 * @throws TException
	 */
	public void recordFile(TFile file) throws TException;

}
