package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �`�[���t�t�B�[���h�̃R���g���[��
 * 
 * @author AIS
 */
public class TSlipDateController extends TController {

	/** �`�[���t�t�B�[���h */
	protected TSlipDate slipDate;

	/** TCallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/**
	 * @param slipDate �`�[���t�t�B�[���h
	 */
	public TSlipDateController(TSlipDate slipDate) {
		this.slipDate = slipDate;
		init();
	}

	/**
	 * ������
	 */
	protected void init() {
		//
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * ���ݓ��t���擾����B�i��s�`�[���t�l������j
	 * 
	 * @return ���ݓ��t
	 */
	public Date getInitDate() {

		// ���� > ��s�`�[���t�̏ꍇ ��s�`�[���t
		// ���� <= ��s�`�[���t�̏ꍇ ����
		Date priorOverDate = getCompany().getFiscalPeriod().getPriorOverDate();
		Date currentDate = Util.getCurrentDate();

		if (currentDate.compareTo(priorOverDate) != 1) {
			return currentDate;
		} else {
			return priorOverDate;
		}
	}

	/**
	 * ������1�����Z�b�g����B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 */
	public void setFirstDateOfCurrentPeriod() {
		slipDate.setValue(getCompany().getFiscalPeriod().getFirstDateOfCurrentPeriod());
	}

	/**
	 * �����̖������Z�b�g����B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 */
	public void setLastDateOfCurrentPeriod() {
		slipDate.setValue(getCompany().getFiscalPeriod().getLastDateOfCurrentPeriod());
	}

	/**
	 * ������1�����Z�b�g����B<br>
	 * ���Z�i�K���l������B
	 */
	public void setFirstDateOfCurrentPeriodOfSettlement() {
		slipDate.setValue(getCompany().getFiscalPeriod().getFirstDateOfCurrentPeriodOfSettlement());
	}

	/**
	 * �����̖������Z�b�g����B<br>
	 * ���Z�i�K���l������B
	 */
	public void setLastDateOfCurrentPeriodOfSettlement() {
		slipDate.setValue(getCompany().getFiscalPeriod().getLastDateOfCurrentPeriodOfSettlement());
	}

	/**
	 * ��������Z�b�g����
	 */
	public void setDateBeginningOfPeriod() {
		slipDate.setValue(getCompany().getFiscalPeriod().getDateBeginningOfPeriod());
	}

	/**
	 * ���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 * 
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosed() {

		Date closedDate = getCompany().getFiscalPeriod().getLastDateOfClosedPeriod();
		if (!Util.isNullOrEmpty(slipDate.getValue())) {
			return (closedDate.compareTo(slipDate.getValue()) >= 0);

		}
		return false;
	}

	/**
	 * ���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * 
	 * @param settlementStage ���Z�i�K
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosed(int settlementStage) {

		return isClosed(getCompany(), settlementStage);
	}

	/**
	 * �w���Ђ���ɁA���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * 
	 * @param company ���
	 * @param settlementStage ���Z�i�K
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosed(Company company, int settlementStage) {

		Date closedDate = company.getFiscalPeriod().getLastDateOfClosedPeriod();

		Date date = slipDate.getValue();

		if (date != null && slipDate.getCalendarType() == TPopupCalendar.TYPE_YM) {
			// �N���w��Ȃ�A�����ɕϊ�
			date = DateUtil.getLastDate(date);
		}

		if (date != null) {
			if (closedDate.compareTo(date) > 0) {
				return true;

			} else if (closedDate.compareTo(date) == 0
				&& settlementStage <= company.getFiscalPeriod().getSettlementStage()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * ���Z�i�K�͍l������B
	 * 
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosedPeriodOfSettlement() {
		return isClosedPeriodOfSettlement(getCompany());
	}

	/**
	 * �w���Ђ���ɁA���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * ���Z�i�K�͍l������B
	 * 
	 * @param company ���
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosedPeriodOfSettlement(Company company) {
		return isClosed(company, company.getFiscalPeriod().getMaxSettlementStage());
	}

	/**
	 * ��s�`�[���͓��t�𒴂��Ă��Ȃ�����Ԃ��B<br>
	 * �����Ă���ꍇtrue�A�����Ă��Ȃ��ꍇfalse
	 * 
	 * @return ��s�`�[���͓��t�𒴂��Ă���ꍇtrue�A���Ȃ��ꍇfalse
	 */
	public boolean isPriorOver() {
		return isPriorOver(getCompany());
	}

	/**
	 * �w���Ђ���ɁA��s�`�[���͓��t�𒴂��Ă��Ȃ�����Ԃ��B<br>
	 * �����Ă���ꍇtrue�A�����Ă��Ȃ��ꍇfalse
	 * 
	 * @param company ���
	 * @return ��s�`�[���͓��t�𒴂��Ă���ꍇtrue�A���Ȃ��ꍇfalse
	 */
	public boolean isPriorOver(Company company) {
		Date date = company.getFiscalPeriod().getPriorOverDate();
		if (!Util.isNullOrEmpty(slipDate.getValue())) {
			return (slipDate.getValue().compareTo(date) > 0);
		}

		return false;
	}

	/**
	 * ���Z�\���t(���Z��������)����Ԃ�
	 * 
	 * @return true(���Z��) / false(�ʏ팎)
	 */
	public boolean isSettlementDate() {

		Date date = slipDate.getValue();

		if (date != null && slipDate.getCalendarType() == TPopupCalendar.TYPE_YM) {
			// �N���w��Ȃ�A�����ɕϊ�
			date = DateUtil.getLastDate(date);
		}

		return getCompany().getFiscalPeriod().isSettlementMonth(date)
			&& DateUtil.getDay(date) == DateUtil.getLastDay(date);
	}

	/**
	 * �R�[���o�b�N���X�i�[�o�^
	 * 
	 * @param listener �R�[���o�b�N���X�i�[
	 */
	public void addCallBackListener(TCallBackListener listener) {
		if (callBackListenerList == null) {
			callBackListenerList = new ArrayList<TCallBackListener>();

			slipDate.setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!slipDate.isValueChanged()) {
						return true;
					}

					changedValue();

					return true;
				}
			});

		}

		callBackListenerList.add(listener);
	}

	/**
	 * �R�[���o�b�N���X�i�[�N���A
	 */
	public void clearCallBackListenerList() {
		if (callBackListenerList != null) {
			this.callBackListenerList.clear();
		}
	}

	/**
	 * @return �R�[���o�b�N���X�i�[��߂��܂��B
	 */
	public List<TCallBackListener> getCallBackListenerList() {
		return callBackListenerList;
	}

	/**
	 * @param callBackListenerList �R�[���o�b�N���X�i�[��ݒ肵�܂��B
	 */
	public void setCallBackListenerList(List<TCallBackListener> callBackListenerList) {
		this.callBackListenerList = callBackListenerList;
	}

	/**
	 * �`�[���t�̕ύX�C�x���g
	 */
	public void changedValue() {
		if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
			for (TCallBackListener listener : callBackListenerList) {
				listener.after();
				listener.after(true);
				listener.afterVerify(true);
			}
		}
	}
}
