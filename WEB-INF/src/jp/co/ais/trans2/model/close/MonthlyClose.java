package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * �������ߏ���
 * 
 * @author AIS
 */
public interface MonthlyClose {

	/**
	 * �����̒��߂��s��
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return �������ߌ�̉�v����
	 * @throws TException
	 */
	public FiscalPeriod closeMonthly(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * �����X�V�̎�����s��
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return �������ߑO�̉�v����
	 * @throws TException
	 */
	public FiscalPeriod cancelMonthly(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * ���X�V�̓`�[���c���Ă��Ȃ������`�F�b�N����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return true(���X�V�`�[���c���Ă���) / false(���X�V�`�[���c���Ă��Ȃ�)
	 * @throws TException
	 */
	public boolean isExistsNotClosedSlip(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * �`�[��ʢ01Z�F�O�ݕ]���֊T�Z�`�[������݂��邩�`�F�b�N����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return true(�O�ݕ]���֊T�Z�`�[�����݂���) / false(�O�ݕ]���֊T�Z�`�[�����݂��Ȃ�)
	 * @throws TException
	 */
	public boolean isExistsForeignCurrencyRevaluationSlip(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * �O�ݕ]���֑Ώۂ̊���Ȗڂ��X�V����Ă��邩�`�F�b�N����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return true(�X�V����Ă���) / false(�X�V����Ă��Ȃ�)
	 * @throws TException
	 */
	public boolean isUpdateRevaluationSlip(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * �x���������������Ă��Ȃ��f�[�^���擾����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return �x���������������Ă��Ȃ��f�[�^
	 * @throws TException
	 */
	public List<Message> getNotPaidData(String companyCode, FiscalPeriod fp) throws TException;

	/**
	 * �R���Ǘ������擾����<br>
	 * �c�����c���Ă���D�E����敪�̓`�[���t
	 * 
	 * @return list: �R�����ߏ��bean���X�g<br>
	 *         vessel_code: �D�R�[�h<br>
	 *         oil_type_kbn: ����敪�R�[�h<br>
	 *         den_date: �`�[���t�̔N���̍ŏI���t
	 * @throws TException
	 */
	public List<BMCloseInfo> getBM() throws TException;

	/**
	 * �R���Ǘ������擾����<br>
	 * �c�����c���Ă���D�E����敪�̓`�[���t
	 * 
	 * @param fp
	 * @return list: �R�����ߏ��bean���X�g<br>
	 *         vessel_code: �D�R�[�h<br>
	 *         oil_type_kbn: ����敪�R�[�h<br>
	 *         den_date: �`�[���t�̔N���̍ŏI���t
	 * @throws TException
	 */
	public List<BunkerInfo> getBunker(FiscalPeriod fp) throws TException;

	/****************************************************************************************************************
	 * �ȉ�������ДŃ��\�b�h
	 ****************************************************************************************************************/

	/**
	 * �����X�V�f�[�^���擾����
	 * 
	 * @param condition ��������
	 * @return �����X�V�f�[�^
	 * @throws TException
	 */
	public List<MonthData> getMonthData(MonthDataSearchCondition condition) throws TException;

	/**
	 * �`�[���쐬��CM_FUND_DTL�����݂�����G���[
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v����
	 * @return �`�[���쐬��CM_FUND_DTL�����݂���
	 * @throws TException
	 */
	public boolean isExistsNotSlipByCmFund(String companyCode, FiscalPeriod fp) throws TException;
}
