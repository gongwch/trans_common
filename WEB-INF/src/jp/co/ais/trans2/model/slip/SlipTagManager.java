package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �`�[�tⳋ@�\�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface SlipTagManager {

	/**
	 * ����̓`�[�̕tⳋ@�\����ݒ肷��
	 * 
	 * @param slip �`�[
	 * @return �tⳋ@�\���<�t�@�C����, �o�C�i��>
	 * @throws TException
	 */
	public List<SWK_TAG> get(Slip slip) throws TException;

	/**
	 * ����̓`�[�̕tⳋ@�\����ݒ肷��
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return �tⳋ@�\���<�t�@�C����, �o�C�i��>
	 * @throws TException
	 */
	public List<SWK_TAG> get(String companyCode, String slipNo) throws TException;

	/**
	 * �`�[�tⳋ@�\�̓o�^
	 * 
	 * @param slip �`�[
	 */
	public void entry(Slip slip);

	/**
	 * �tⳋ@�\�̓o�^
	 * 
	 * @param entity �tⳋ@�\
	 */
	public void entry(SWK_TAG entity);

	/**
	 * �tⳋ@�\���̍폜
	 * 
	 * @param list List<SWK_TAG>
	 */
	public void delete(List<SWK_TAG> list);

	/**
	 * ����̓`�[�̕tⳋ@�\���̍폜
	 * 
	 * @param companyCode
	 * @param slipNo
	 */
	public void delete(String companyCode, String slipNo);

	/**
	 * ����̓`�[�̕tⳋ@�\���̍폜
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 */
	public void delete(String companyCode, String slipNo, Integer sEQ);
}
