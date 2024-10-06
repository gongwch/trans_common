package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �`�[�Y�t�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface SlipAttachmentManager {

	/**
	 * ����̓`�[�̓Y�t����ݒ肷��
	 * 
	 * @param slip �`�[
	 * @return �Y�t���<�t�@�C����, �o�C�i��>
	 * @throws TException
	 */
	public List<SWK_ATTACH> get(Slip slip) throws TException;

	/**
	 * ����̓`�[�̓Y�t����ݒ肷��
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return �Y�t���<�t�@�C����, �o�C�i��>
	 * @throws TException
	 */
	public List<SWK_ATTACH> get(String companyCode, String slipNo) throws TException;

	/**
	 * �`�[�Y�t�̓o�^
	 * 
	 * @param slip �`�[
	 */
	public void entry(Slip slip);

	/**
	 * �Y�t�̓o�^
	 * 
	 * @param entity �Y�t
	 */
	public void entry(SWK_ATTACH entity);

	/**
	 * �Y�t���̍폜
	 * 
	 * @param list List<SWK_ATTACH>
	 */
	public void delete(List<SWK_ATTACH> list);

	/**
	 * ����̓`�[�̓Y�t���̍폜
	 * 
	 * @param companyCode
	 * @param slipNo
	 */
	public void delete(String companyCode, String slipNo);

	/**
	 * ����̓`�[�̓Y�t���̍폜
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 */
	public void delete(String companyCode, String slipNo, Integer sEQ);
}
