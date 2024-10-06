package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���ߊǗ��}�l�[�W���̎���
 * 
 * @author AIS
 */
public class CloseManagerImpl extends TModel implements CloseManager {

	/** true:CM_FUND_DTL�o�^���s���� */
	public static boolean isUseCmFund = ServerConfig.isFlagOn("trans.use.cm.fund.entry");

	/**
	 * �w���Ђ̉�v���ߏ���Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return �w���Ђ̉�v���ߏ��
	 * @throws TException
	 */
	public FiscalPeriod getFiscalPeriod(String companyCode) throws TException {
		CloseDao dao = (CloseDao) getComponent(CloseDao.class);
		return dao.getFiscalPeriod(companyCode);
	}

	public int getFiscalYear(Date date, Company company) {
		return company.getFiscalPeriod().getFiscalYear(date);
	}

	/**
	 * �w����t�̌��x��Ԃ�
	 * 
	 * @param date ���t
	 * @param company ��Џ��(���񌎂��Z�b�g����Ă��邱�Ƃ��O��)
	 * @return �w����t�̌��x
	 */
	public int getFiscalMonth(Date date, Company company) {

		return company.getFiscalPeriod().getFiscalMonth(date);

	}

	/**
	 * �w����t�̔N�x�̊������Ԃ�
	 * 
	 * @param date ���t
	 * @param company ��Џ��(���񌎂��Z�b�g����Ă��邱�Ƃ��O��)
	 * @return �w����t�̔N�x�̊����
	 */
	public Date getDateBeginningOfPeriod(Date date, Company company) {

		return company.getFiscalPeriod().getDateBeginningOfPeriod(date);

	}

	/**
	 * �����̒��߂��s��
	 * 
	 * @param date �`�[���t
	 * @param company ���
	 * @throws TException
	 */
	public void closeDaily(Date date, Company company) throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		dc.closeDaily(date, company);
	}

	/**
	 * �Ō�ɓ����X�V�������t��Ԃ�
	 * 
	 * @param company
	 * @return �Ō�ɓ����X�V�������t
	 * @throws TException
	 */
	public Date getLastDailyClosedDate(Company company) throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		return dc.getLastDailyClosedDate(company);
	}

	/**
	 * �����X�V����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v���ԏ��
	 * @return ��v����Entity
	 * @throws TWarningException
	 * @throws TException
	 */
	public FiscalPeriod closeMonthly(String companyCode, FiscalPeriod fp) throws TWarningException, TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// �`�[��ʢ01Z�F�O�ݕ]���֊T�Z�`�[������݂���ꍇ�G���[
		if (mc.isExistsForeignCurrencyRevaluationSlip(companyCode, fp)) {
			// �O�ݕ]���֊T�Z�`�[�����݂��邽�ߎ��s�ł��܂���B
			throw new TWarningException("I01094");
		}

		// ���X�V�f�[�^�����݂���ꍇ�G���[
		if (mc.isExistsNotClosedSlip(companyCode, fp)) {
			// ���X�V�f�[�^���c���Ă��܂��B
			throw new TWarningException("W00201");
		}

		// �O�ݕ]���֑Ώۂ̊���Ȗڂ��X�V����Ă��邩�`�F�b�N
		if (mc.isUpdateRevaluationSlip(companyCode, fp)) {
			// �O�ݕ]���֑Ώۂ̊���Ȗڂ��ǉ��A�܂��͏C������܂����B�O�ݕ]���֓`�[�쐬�����s���Ă��������B
			throw new TWarningException("I01100");
		}

		// �R���Ǘ��󋵂̃`�F�b�N �����ȑO�ɖ����������݂̏ꍇ�G���[
		checkBunkerStatus(fp);

		// �x���������������Ă��Ȃ��f�[�^�����݂���ꍇ�G���[
		List<Message> errList = mc.getNotPaidData(companyCode, fp);
		if (errList != null && !errList.isEmpty()) {
			// I00426:�x���������������Ă��Ȃ��f�[�^���c���Ă��܂��B
			throw new TMessageListException("I00426", errList);
		}

		if (isUseCmFund) {
			// �`�[���쐬��CM_FUND_DTL�����݂�����G���[
			if (mc.isExistsNotSlipByCmFund(companyCode, fp)) {
				// �`�[�����쐬�̋�s�����������ׂ����݂��܂��B\n�����ʎ����J�\�Ŋm�F���Ă��������B
				throw new TWarningException("I01109");
			}
		}
		
		// �������ߎ��s
		fp = mc.closeMonthly(companyCode, fp);

		return fp;

	}

	/**
	 * �R���Ǘ��̃X�e�[�^�X���`�F�b�N����<br>
	 * �G���[��Throw���F�����ȑO�ɔR���Ǘ��ŏ������K�v
	 * 
	 * @param fp ��v���ԏ��
	 * @throws TException
	 */
	protected void checkBunkerStatus(FiscalPeriod fp) throws TException {
		MonthlyClose dao = (MonthlyClose) getComponent(MonthlyClose.class);
		List<BMCloseInfo> list = null;
		List<BunkerInfo> listBunker = null;
		try {
			list = dao.getBM();
			listBunker = dao.getBunker(fp);
		} catch (Exception e) {
			// �G���[�L���b�`�z��F�e�[�u���񑶍�
			// �R���Ǘ��Ȃ��̏ꍇ�`�F�b�N�s�v
			return;
		}

		// �G���[���b�Z�[�W�`�F�b�N�E�\�z
		List<Message> msgs = new ArrayList();
		Date closeDate = DateUtil.getLastDate(DateUtil.addMonth(fp.getFirstDateOfClosedPeriodOfSettlement(), 1));

		// �Ώۃf�[�^�񑶍݂̏ꍇ�A�G���[�����ŏ����I��
		if (list != null) {
			for (BMCloseInfo info : list) {
				Date slipDate = info.getDEN_DATE(); // �`�[���t
				if (slipDate == null) {
					slipDate = closeDate;
				} else {
					slipDate = DateUtil.getLastDate(slipDate);
				}
				// ������p�U�֓��͂������͂��ǂ������`�F�b�N
				if (closeDate.compareTo(slipDate) == 1) {
					// �R���Ǘ��̌�����p�U�֓��͂����s����Ă��܂���B�D�F{0} ����敪�F{1}
					msgs.add(new Message("I00452", info.getVESSEL_NAME(), info.getOIL_TYPE_NAME()));
				}
			}
		}

		// �Ώۃf�[�^�񑶍݂̏ꍇ�A�G���[�����ŏ����I��
		if (listBunker != null) {
			for (BunkerInfo bnkrInf : listBunker) {
				// �R���Ǘ��̌�����p�U�֓��͂����s����Ă��܂���B�D�F{0} ����敪�F{1}
				msgs.add(new Message("I00452", bnkrInf.getVESSEL_NAME(), bnkrInf.getOIL_TYPE_NAME()));
			}
		}

		if (!msgs.isEmpty()) {
			throw new TMessageListException(msgs);
		}
	}

	/**
	 * �����X�V�̎��������B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v���ԏ��
	 * @return ��v����Entity
	 * @throws TWarningException
	 * @throws TException
	 */
	public FiscalPeriod cancelMonthly(String companyCode, FiscalPeriod fp) throws TWarningException, TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// �������ߎ�����s
		fp = mc.cancelMonthly(companyCode, fp);

		return fp;

	}

	/****************************************************************************************************************
	 * �ȉ�������ДŃ��\�b�h
	 ****************************************************************************************************************/

	/**
	 * �����X�V����B
	 * 
	 * @param baseDate �����N����
	 * @param list �����X�V���
	 * @throws TWarningException
	 * @throws TException
	 */
	public List<Message> closeAllDaily(Date baseDate, List<DailyData> list) throws TWarningException, TException {

		Company loginCompany = getCompany();
		User loginUser = getUser();

		try {

			DailyClose dc = (DailyClose) getComponent(DailyClose.class);
			CompanyManager cm = get(CompanyManager.class);

			// ���b�Z�[�W�쐬
			List<Message> msgList = new ArrayList<Message>();

			String msgCmp = getWord("C12111");
			
			for (DailyData dd : list) {

				if (Util.isNullOrEmpty(dd.getCompanyCode())) {
					continue;
				}

				Company cmp = cm.get(dd.getCompanyCode());

				dd.setLastUpdateDate(getNow());
				setCompany(cmp);

				String subTitle = msgCmp + dd.getCompanyCode();

				try {
					// ���s

					// �����X�V���s
					dc.closeDaily(baseDate, cmp);

					// �������b�Z�[�W
					Message msg = new Message();
					msg.setMessage("I00013");
					msg.setSubMessageID(subTitle);
					msgList.add(msg);

				} catch (TException e) {

					// �G���[���b�Z�[�W
					Message msg = new Message();
					msg.setMessage(e.getMessageID(), e.getMessageArgs());
					msg.setSubMessageID(subTitle);
					msgList.add(msg);

				} catch (Exception e) {
					throw new TException(e);
				}
			}

			return msgList;

		} finally {
			setCompany(loginCompany);
			setUser(loginUser);
		}
	}

	/**
	 * �����X�V�f�[�^���擾����
	 * 
	 * @return �����X�V�f�[�^
	 * @throws TException
	 */
	public List<DailyData> getDailyData() throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		List<DailyData> list = dc.getDailyData();

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list;
	}

	/**
	 * �����X�V����B
	 * 
	 * @param list �����X�V���
	 * @throws TWarningException
	 * @throws TException
	 */
	public List<Message> closeAllMonthly(List<MonthData> list) throws TWarningException, TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// ���̃��[�U�[�ɂ��Ώۃ��R�[�h���ύX�`�F�b�N
		if (!checkMonthly(list)) {
			throw new TException("W00229");
		}

		List<Message> msgList = new ArrayList<Message>();

		String msgCmp = getWord("C12111");
		
		for (MonthData monthData : list) {

			Message msg = new Message();
			String subTitle = msgCmp + monthData.getCompanyCode();

			String companyCode = monthData.getCompanyCode();
			FiscalPeriod fp = monthData.getCompany().getFiscalPeriod();
			
			// �`�[��ʢ01Z�F�O�ݕ]���֊T�Z�`�[������݂���ꍇ�G���[
			if (mc.isExistsForeignCurrencyRevaluationSlip(companyCode, fp)) {
				// �O�ݕ]���֊T�Z�`�[�����݂��邽�ߎ��s�ł��܂���B
				msg.setMessage("I01094");
				msg.setSubMessageID(subTitle);
				msgList.add(msg);
				continue;
			}

			// ���X�V�f�[�^�����݂���ꍇ�G���[
			if (mc.isExistsNotClosedSlip(companyCode, fp)) {
				// ���X�V�̃f�[�^���c���Ă��܂��B
				msg.setMessage("W00201");
				msg.setSubMessageID(subTitle);
				msgList.add(msg);
				continue;
			}

			// �O�ݕ]���֑Ώۂ̊���Ȗڂ��X�V����Ă��邩�`�F�b�N
			if (mc.isUpdateRevaluationSlip(companyCode, fp)) {
				// �O�ݕ]���֑Ώۂ̊���Ȗڂ��ǉ��A�܂��͏C������܂����B�O�ݕ]���֓`�[�쐬�����s���Ă��������B
				msg.setMessage("I01100");
				msg.setSubMessageID(subTitle);
				msgList.add(msg);
				continue;
			}

			// �R���Ǘ��󋵂̃`�F�b�N �����ȑO�ɖ����������݂̏ꍇ�G���[
			checkBunkerStatus(fp);

			// �x���������������Ă��Ȃ��f�[�^�����݂���ꍇ�G���[
			List<Message> errList = mc.getNotPaidData(companyCode, fp);
			if (errList != null && !errList.isEmpty()) {
				// I00426:�x���������������Ă��Ȃ��f�[�^���c���Ă��܂��B
				msg.setMessage("I00426", errList);
				msg.setSubMessageID(subTitle);
				msgList.add(msg);
				continue;
			}


			if (isUseCmFund) {
				// �`�[���쐬��CM_FUND_DTL�����݂�����G���[
				if (mc.isExistsNotSlipByCmFund(companyCode, fp)) {
					// �`�[�����쐬�̋�s�����������ׂ����݂��܂��B\n�����ʎ����J�\�Ŋm�F���Ă��������B
					msg.setMessage("I01109");
					msg.setSubMessageID(subTitle);
					msgList.add(msg);
					continue;
				}
			}

			// �������ߎ��s
			mc.closeMonthly(monthData.getCompanyCode(), monthData.getCompany().getFiscalPeriod());

			// �������b�Z�[�W
			msg.setMessage("I00013");
			msg.setSubMessageID(subTitle);
			msgList.add(msg);

		}
		return (msgList);
	}

	/**
	 * �����X�V�f�[�^���擾����
	 * 
	 * @param condition ��������
	 * @return �����X�V�f�[�^
	 * @throws TException
	 */
	public List<MonthData> getMonthData(MonthDataSearchCondition condition) throws TException {
		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);
		List<MonthData> list = mc.getMonthData(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list;
	}

	/**
	 * ���̃��[�U�[�ɂ��Ώۃ��R�[�h���ύX�`�F�b�N
	 * 
	 * @param oldList �����X�V���
	 * @return true:���Ȃ��Afalse:�Ώۃ��R�[�h���Ⴄ��Ђ���
	 * @throws TException
	 */
	public boolean checkMonthly(List<MonthData> oldList) throws TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// ���̃��[�U�[�ɂ��Ώۃ��R�[�h���ύX����Ă��܂��B�ēx�A�������s���Ă��������B
		List<String> companyCodeList = new ArrayList<String>();

		for (MonthData monthData : oldList) {
			companyCodeList.add(monthData.getCompanyCode());
		}

		MonthDataSearchCondition condition = new MonthDataSearchCondition();
		condition.setCompanyCodeList(companyCodeList);
		List<MonthData> newList = mc.getMonthData(condition);

		if (newList == null || newList.isEmpty()) {
			return false;
		}

		for (MonthData oldData : oldList) {

			for (MonthData newData : newList) {

				if (newData.getCompanyCode().equals(oldData.getCompanyCode())
					&& (newData.getEntry() != oldData.getEntry() || newData.getDeny() != oldData.getDeny() || newData
						.getApprove() != oldData.getApprove())) {
					// ������Ђ̑Ώۃ��R�[�h�l���Ⴄ�ꍇ�A�G���[�߂�
					return false;

				}

			}
		}

		return true;

	}

	/**
	 * �����X�V�̎��������B
	 * 
	 * @param list �����X�V���
	 * @throws TWarningException
	 * @throws TException
	 */
	public void cancelAllMonthly(List<MonthData> list) throws TWarningException, TException {

		MonthlyClose mc = (MonthlyClose) getComponent(MonthlyClose.class);

		// ���̃��[�U�[�ɂ��Ώۃ��R�[�h���ύX�`�F�b�N
		if (!checkMonthly(list)) {
			throw new TException("W00229");
		}

		for (MonthData monthData : list) {
			// ����������s
			mc.cancelMonthly(monthData.getCompanyCode(), monthData.getCompany().getFiscalPeriod());
		}
	}
	
	/**
	 * �u REVALUATION_CTL�v��STATUS_KBN���X�V����
	 * 
	 * @param date
	 * @param companyCode
	 * @throws TException
	 */
	public void checkStatus(Date date, String companyCode) throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		dc.checkStatus(date, companyCode);
	}

	/**
	 * �O�ݕ]���֓`�[�̑��݃`�F�b�N
	 * 
	 * @param companyCode
	 * @return PROC_YM
	 * @throws TException
	 */
	public List<Date> existSlip(String companyCode) throws TException {
		DailyClose dc = (DailyClose) getComponent(DailyClose.class);
		return dc.existSlip(companyCode);
	}
}
