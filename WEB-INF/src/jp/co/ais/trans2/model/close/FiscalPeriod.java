package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.term.*;

/**
 * ��v����Entity<br>
 * ���݂̉�v�N�x�A������񓙂����B
 * 
 * @author AIS
 */
public class FiscalPeriod extends TransferBase {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("nendo=").append(fiscalYear);
		sb.append(",kisyu=").append(monthBeginningOfPeriod);
		sb.append(",closeM=").append(closedMonth);
		return sb.toString();
	}

	/** ��v�N�x */
	protected int fiscalYear = 0;

	/** ���Z�i�K */
	protected int settlementStage = 0;

	/** ���� */
	protected int monthBeginningOfPeriod = 0;

	/** ����� */
	protected Date dateBeginningOfPeriod = null;

	/** ������ */
	protected Date dateEndOfPeriod = null;

	/** ���Z���� */
	protected SettlementTerm settlementTerm = null;

	/** �ő匈�Z�i�K */
	protected int maxSettlementStage = 0;

	/** ���ߌ� */
	protected int closedMonth = 0;

	/** ��s���t����(�P�N) */
	protected int priorOverMonths = 12;

	/**
	 * @return ���Z�i�K��߂��܂��B
	 */
	public int getSettlementStage() {
		return settlementStage;
	}

	/**
	 * @param settlementStage ���Z�i�K��ݒ肵�܂��B
	 */
	public void setSettlementStage(int settlementStage) {
		this.settlementStage = settlementStage;
	}

	/**
	 * @return ��v�N�x��߂��܂��B
	 */
	public int getFiscalYear() {
		return fiscalYear;
	}

	/**
	 * @param fiscalYear ��v�N�x��ݒ肵�܂��B
	 */
	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	/**
	 * @return �������߂��܂��B
	 */
	public Date getDateBeginningOfPeriod() {
		return dateBeginningOfPeriod;
	}

	/**
	 * �w����t�̔N�x�̊������߂��܂��B
	 * 
	 * @param date ���t
	 * @return �w����t�̔N�x�̊����
	 */
	public Date getDateBeginningOfPeriod(Date date) {
		int fiscalYear_ = getFiscalYear(date);
		return DateUtil.getDate(fiscalYear_, getMonthBeginningOfPeriod(), 1);
	}

	/**
	 * �w��N�x�̊������߂��܂��B
	 * 
	 * @param fiscalyear �N�x
	 * @return �w��N�x�̊����
	 */
	public Date getDateBeginningOfPeriod(int fiscalyear) {
		return DateUtil.getDate(fiscalyear, getMonthBeginningOfPeriod(), 1);
	}

	/**
	 * @param dateBeginningOfPeriod �������ݒ肵�܂��B
	 */
	public void setDateBeginningOfPeriod(Date dateBeginningOfPeriod) {
		this.dateBeginningOfPeriod = dateBeginningOfPeriod;
	}

	/**
	 * @return ��������߂��܂��B
	 */
	public Date getDateEndOfPeriod() {
		return dateEndOfPeriod;
	}

	/**
	 * �w����t�̔N�x�̊�������߂��܂��B
	 * 
	 * @param date ���t
	 * @return ��������߂��܂��B
	 */
	public Date getDateEndOfPeriod(Date date) {
		int fiscalYear_ = getFiscalYear(date);
		return DateUtil.getLastDate(fiscalYear_, getMonthBeginningOfPeriod() + 11);
	}

	/**
	 * @param dateEndOfPeriod ��������ݒ肵�܂��B
	 */
	public void setDateEndOfPeriod(Date dateEndOfPeriod) {
		this.dateEndOfPeriod = dateEndOfPeriod;
	}

	/**
	 * ������1����Ԃ��B<br>
	 * ���Z�i�K�͍l������B
	 * 
	 * @return ������1��(���Z�i�K�͍l������)
	 */
	public Date getFirstDateOfCurrentPeriodOfSettlement() {

		// 00�̏ꍇ�͊���
		if (getSettlementStage() == 0 && getClosedMonth() == 0) {
			return getDateBeginningOfPeriod();
		}

		// �ʏ팎�̏ꍇ
		Date date = DateUtil.getDate(getFiscalYear(), getMonthBeginningOfPeriod(), 1);
		date = DateUtil.addMonth(date, getClosedMonth());

		// ���Z���̏ꍇ
		Date settlementDate = DateUtil.getDate(getFiscalYear(), getMonthBeginningOfPeriod(), 1);
		settlementDate = DateUtil.addMonth(settlementDate, getClosedMonth() - 1);

		boolean isSettlementMonth = isSettlementMonth(settlementDate, getSettlementTerm(), getMonthBeginningOfPeriod());

		if (isSettlementMonth && getMaxSettlementStage() != getSettlementStage()) {
			date = DateUtil.addMonth(date, -1);
		}

		return date;

	}

	/**
	 * �����̖�����Ԃ��B<br>
	 * ���Z�i�K�͍l������(���Z�i�K�͍l������)
	 * 
	 * @return �����̖���
	 */
	public Date getLastDateOfCurrentPeriodOfSettlement() {
		Date date = getFirstDateOfCurrentPeriodOfSettlement();
		date = DateUtil.getLastDate(date);
		return date;
	}

	/**
	 * �����̌��Z�i�K��Ԃ�
	 * 
	 * @return AIS
	 */
	public int getCurrentSettlementStage() {

		if (settlementStage == 0 && getClosedMonth() == 0) {
			return 0;
		}

		// ���Z���Ȃ�Ό��Z�i�K���l��
		if (isSettlementMonth(getLastDateOfCurrentPeriodOfSettlement())) {

			int fiscalMonth = getFiscalMonth(getLastDateOfCurrentPeriodOfSettlement());
			if (fiscalMonth == getClosedMonth()) {
				return settlementStage + 1;
			}
			return 0;

		}

		// �ʏ팎�Ȃ��0
		return 0;

	}

	/**
	 * ������1����Ԃ��B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 * 
	 * @return ������1��(���Z�i�K�͍l�����Ȃ�)
	 */
	public Date getFirstDateOfCurrentPeriod() {

		// 00�̏ꍇ�͊���
		if (getSettlementStage() == 0 && getClosedMonth() == 0) {
			return getDateBeginningOfPeriod();
		}

		// �ʏ팎�̏ꍇ
		Date date = DateUtil.getDate(getFiscalYear(), getMonthBeginningOfPeriod(), 1);
		date = DateUtil.addMonth(date, getClosedMonth());

		return date;

	}

	/**
	 * �����̖�����Ԃ��B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 * 
	 * @return �����̖���(���Z�i�K�͍l�����Ȃ�)
	 */
	public Date getLastDateOfCurrentPeriod() {
		Date date = getFirstDateOfCurrentPeriod();
		date = DateUtil.getLastDate(date);
		return date;
	}

	/**
	 * ���ߌ���1����Ԃ��B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 * 
	 * @return ���ߌ���1��(���Z�i�K�͍l�����Ȃ�)
	 */
	public Date getFirstDateOfClosedPeriod() {

		Date date = getFirstDateOfCurrentPeriod();
		date = DateUtil.addMonth(date, -1);

		return date;

	}

	/**
	 * ���ߌ��̖�����Ԃ��B<br>
	 * ���Z�i�K�͍l�����Ȃ��B
	 * 
	 * @return ���ߌ��̖���(���Z�i�K�͍l�����Ȃ�)
	 */
	public Date getLastDateOfClosedPeriod() {
		Date date = getFirstDateOfClosedPeriod();
		date = DateUtil.getLastDate(date);
		return date;
	}

	/**
	 * ���ߌ���1����Ԃ��B<br>
	 * ���Z�i�K�͍l������B
	 * 
	 * @return ���ߌ���1��(���Z�i�K�͍l������)
	 */
	public Date getFirstDateOfClosedPeriodOfSettlement() {

		Date date = getFirstDateOfCurrentPeriodOfSettlement();
		date = DateUtil.addMonth(date, -1);

		return date;

	}

	/**
	 * ���ߌ��̖�����Ԃ��B<br>
	 * ���Z�i�K�͍l������B
	 * 
	 * @return ���ߌ��̖���(���Z�i�K�͍l������)
	 */
	public Date getLastDateOfClosedPeriodOfSettlement() {
		Date date = getFirstDateOfClosedPeriodOfSettlement();
		date = DateUtil.getLastDate(date);
		return date;
	}

	/**
	 * ��s�`�[���͉\���t��Ԃ��B
	 * 
	 * @return ��s�`�[���͉\���t
	 */
	public Date getPriorOverDate() {

		Date date = getFirstDateOfClosedPeriodOfSettlement();
		date = DateUtil.addMonth(date, priorOverMonths);
		date = DateUtil.getLastDate(date);

		return date;
	}

	/**
	 * @return ���񌎂�߂��܂��B
	 */
	public int getMonthBeginningOfPeriod() {
		return monthBeginningOfPeriod;
	}

	/**
	 * @param monthBeginningOfPeriod ���񌎂�ݒ肵�܂��B
	 */
	public void setMonthBeginningOfPeriod(int monthBeginningOfPeriod) {
		this.monthBeginningOfPeriod = monthBeginningOfPeriod;
	}

	/**
	 * ���Z���Ԃ�Ԃ�
	 * 
	 * @return ���Z����
	 */
	public SettlementTerm getSettlementTerm() {
		return settlementTerm;
	}

	/**
	 * ���Z���Ԃ��Z�b�g����
	 * 
	 * @param settlementTerm
	 */
	public void setSettlementTerm(SettlementTerm settlementTerm) {
		this.settlementTerm = settlementTerm;
	}

	/**
	 * ���ߌ���Ԃ�
	 * 
	 * @return ���ߌ�
	 */
	public int getClosedMonth() {
		return closedMonth;
	}

	/**
	 * @param closedMonth
	 */
	public void setClosedMonth(int closedMonth) {
		this.closedMonth = closedMonth;
	}

	/**
	 * �ő匈�Z�i�K
	 * 
	 * @return �ő匈�Z�i�K
	 */
	public int getMaxSettlementStage() {
		return maxSettlementStage;
	}

	/**
	 * �ő匈�Z�i�K
	 * 
	 * @param maxSettlementStage �ő匈�Z�i�K
	 */
	public void setMaxSettlementStage(int maxSettlementStage) {
		this.maxSettlementStage = maxSettlementStage;
	}

	/**
	 * ��s���t�����̎擾
	 * 
	 * @return priorOverMonths ��s���t����
	 */
	public int getPriorOverMonths() {
		return priorOverMonths;
	}

	/**
	 * ��s���t�����̐ݒ�
	 * 
	 * @param priorOverMonths ��s���t����
	 */
	public void setPriorOverMonths(int priorOverMonths) {
		this.priorOverMonths = priorOverMonths;
	}

	/**
	 * �w����t�̔N�x��Ԃ�
	 * 
	 * @param date
	 * @return �w����t�̔N�x
	 */
	public int getFiscalYear(Date date) {

		// �Ώۓ��t�̔N�A�����擾
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);

		if (monthBeginningOfPeriod > month) {
			year = year - 1;
		}

		return year;
	}

	/**
	 * �w����t�̌��x��Ԃ�
	 * 
	 * @param date ���t
	 * @return �w����t�̌��x
	 */
	public int getFiscalMonth(Date date) {

		// �Ώۓ��t�̌����擾
		int month = DateUtil.getMonth(date);

		if (month >= monthBeginningOfPeriod) {
			month = month - monthBeginningOfPeriod + 1;
		} else {
			month = month + 12 - monthBeginningOfPeriod + 1;
		}

		return month;

	}

	/**
	 * ���Z������Ԃ�
	 * 
	 * @param date ���t
	 * @return true(���Z��) / false(�ʏ팎)
	 */
	public boolean isSettlementMonth(Date date) {
		return isSettlementMonth(date, this.settlementTerm, this.monthBeginningOfPeriod);
	}

	/**
	 * �{���Z������Ԃ�
	 * 
	 * @param date ���t
	 * @return true(���Z��) / false(�ʏ팎)
	 */
	public boolean isSettlementMonthForYear(Date date) {
		return isSettlementMonth(date, SettlementTerm.YEAR, this.monthBeginningOfPeriod);
	}

	/**
	 * ���Z������Ԃ�
	 * 
	 * @param date ���t
	 * @param term ���Z�`�[���͋敪
	 * @param monthBeginningOfPeriod_ ����
	 * @return true(���Z��) / false(�ʏ팎)
	 */
	public boolean isSettlementMonth(Date date, SettlementTerm term, int monthBeginningOfPeriod_) {

		int month = DateUtil.getMonth(date);
		boolean rt = false;

		// �N�����Z
		if (SettlementTerm.YEAR == term) {

			int settlementMonth = monthBeginningOfPeriod_ + 11;
			if (settlementMonth > 12) {
				settlementMonth = settlementMonth - 12;
			}
			rt = (month == settlementMonth);

			// �������Z
		} else if (SettlementTerm.HALF == term) {

			int settlementMonth = monthBeginningOfPeriod_ - 1;
			for (int i = 0; i < 2; i++) {
				settlementMonth = settlementMonth + 6;
				if (settlementMonth > 12) {
					settlementMonth = settlementMonth - 12;
				}
				if (month == settlementMonth) {
					rt = true;
				}

			}

			// �l�����Z
		} else if (SettlementTerm.QUARTER == term) {

			int settlementMonth = monthBeginningOfPeriod_ - 1;
			for (int i = 0; i < 4; i++) {
				settlementMonth = settlementMonth + 3;
				if (settlementMonth > 12) {
					settlementMonth = settlementMonth - 12;
				}
				if (month == settlementMonth) {
					rt = true;
				}

			}

			// �������Z
		} else {
			rt = true;
		}

		return rt;

	}

	/**
	 * ���߂��Ă��Ȃ�����Ԃ��B<br>
	 * ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse�B<br>
	 * 
	 * @param date �Ώۓ��t
	 * @param stage ���Z�i�K
	 * @return ���߂��Ă���ꍇtrue�A���߂��Ă��Ȃ��ꍇfalse
	 */
	public boolean isClosed(Date date, int stage) {

		Date closedDate = getLastDateOfClosedPeriod();
		if (closedDate.compareTo(date) > 0) {
			return true;

		} else if (closedDate.compareTo(date) == 0 && stage <= getSettlementStage()) {
			return true;
		}

		return false;
	}

	/**
	 * �����̉�v����Ԃ�
	 * 
	 * @return �����̉�v��
	 */
	public Enum getCurrentAccountTerm() {
		return getAccountTerm(getFirstDateOfCurrentPeriodOfSettlement(), this.settlementTerm);
	}

	/**
	 * �w����̉�v����Ԃ�
	 * 
	 * @param date
	 * @return �w����̉�v��
	 */
	public Enum getAccountTerm(Date date) {

		return getAccountTerm(date, this.settlementTerm);
	}

	/**
	 * �w����̉�v�����A�w��̌��Z�敪�ɉ����ĕԂ�
	 * 
	 * @param date
	 * @param _settlementTerm
	 * @return ���Z�敪�ɑΉ�������v��
	 */
	private Enum getAccountTerm(Date date, SettlementTerm _settlementTerm) {

		int tukido = getFiscalMonth(date);
		return AccountTerm.getCurretTerm(tukido, _settlementTerm);
	}

	/**
	 * �w����̉�v�������݂̉�v���Ɠ���
	 * 
	 * @param date
	 * @return true�F���݂̉�v���ԓ��ł���
	 */
	public boolean isAccountTermEqual(Date date) {
		// �N�x�����ꂩ�A��v��������
		return getFiscalYear(date) == getFiscalYear() && getAccountTerm(date).equals(getCurrentAccountTerm());
	}

	/**
	 * �w���v�N�x�A���x�A���̓��t�̎擾
	 * 
	 * @param fiscalY ��v�N�x
	 * @param fiscalM ��v���x
	 * @param day ��
	 * @return �Ώۓ��t
	 */
	public Date convertDate(int fiscalY, int fiscalM, int day) {

		int year = fiscalY;
		int month = fiscalM + monthBeginningOfPeriod;
		if (month > 12) {
			month -= 12;
			year += 1;
		}

		// �Ώۓ��t���擾
		return DateUtil.getDate(year, month - 1, day);
	}

	/**
	 * ���Y��v����Ԃ�
	 * 
	 * @param strDate �K�p�J�n�N����
	 * @param fiscalY �w��N�x
	 * @return �Ώی��Z��
	 */
	public int getAccountingPeriod(Date strDate, int fiscalY) {

		int strNendo = getFiscalYear(strDate);
		// �Ώی��Z��
		return fiscalY - strNendo + 1;
	}

}
