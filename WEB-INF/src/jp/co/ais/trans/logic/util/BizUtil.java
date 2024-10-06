package jp.co.ais.trans.logic.util;

import java.math.*;
import java.text.*;
import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bill.*;

/**
 * TRANS�Ɩ����ʃ��[�e�B���e�B
 * 
 * @version 1.0
 * @author AIS Y.NAGAHASHI
 */
public class BizUtil {

	/**
	 * ���񌎂��擾����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ����
	 */
	public static int getInitialMonth(String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ��ЃR���g���[���}�X�^�����񌎎擾
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}
		return cmpMst.getCMP_KISYU().intValue();
	}

	/**
	 * ���Z�`�[���͉\������
	 * 
	 * @param date �Ώۓ��t
	 * @param companyCode ��ЃR�[�h
	 * @return ���Z�`�[���͉\���̏ꍇ��TRUE
	 */
	public static boolean isKsnMonth(Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		int i;
		boolean judge = false;

		// ���ߐ���e�[�u�����N�x�擾
		SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);
		SIM_CTL simCtl = simdao.getSIM_CTLByIKaicode(companyCode);

		if (simCtl == null) {
			throw new TEnvironmentException("W00084", "C10258", companyCode);
		}

		int year = simCtl.getNENDO();

		// ��ЃR���g���[���}�X�^�����񌎎擾
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int initialMonth = cmpMst.getCMP_KISYU().intValue();

		// ���Z�R���g���[���}�X�^��茈�Z�`�[���͋敪�擾
		GL_CTL_MSTDao glctldao = (GL_CTL_MSTDao) container.getComponent(GL_CTL_MSTDao.class);
		GL_CTL_MST glCtlMst = glctldao.getGL_CTL_MSTByIKaicode(companyCode);

		if (glCtlMst == null) {
			throw new TEnvironmentException("W00084", "C00142", companyCode);
		}

		int ksnNyuKbn = glCtlMst.getKSN_NYU_KBN();

		Date ksndate = new GregorianCalendar(year, initialMonth - 1, 1).getTime();

		ksndate = DateUtil.addMonth(ksndate, -1);

		switch (ksnNyuKbn) {
			case 0:
				// �N���Z
				ksndate = DateUtil.addYear(ksndate, 1);
				if (DateUtil.getMonth(date) == DateUtil.getMonth(ksndate)) {
					judge = true;
				}
				break;

			case 1:
				// �������Z
				for (i = 1; i < 3; i++) {
					ksndate = DateUtil.addMonth(ksndate, 6);
					if (DateUtil.getMonth(date) == DateUtil.getMonth(ksndate)) {
						judge = true;
					}
				}
				break;

			case 2:
				// �l�������Z
				for (i = 1; i < 5; i++) {
					ksndate = DateUtil.addMonth(ksndate, 3);
					if (DateUtil.getMonth(date) == DateUtil.getMonth(ksndate)) {
						judge = true;
					}
				}
				break;

			default:
				judge = true;
				break;

		}

		// �����łȂ��ꍇ�A���Z�`�[���͕s�\
		if (DateUtil.getDay(date) != DateUtil.getLastDay(DateUtil.getYear(date), DateUtil.getMonth(date))) {
			judge = false;
		}

		return judge;

	}

	/**
	 * ���߂̌��Z���������擾
	 * 
	 * @param date ���ݓ��t
	 * @param companyCode ��ЃR�[�h
	 * @return ���߂̌��Z������
	 */
	public static String getKsnDate(Date date, String companyCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		int i;

		// ���ߐ���e�[�u�����N�x�擾
		SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);
		SIM_CTL simCtl = simdao.getSIM_CTLByIKaicode(companyCode);

		if (simCtl == null) {
			throw new TEnvironmentException("W00084", "C10258", companyCode);
		}

		int year = simCtl.getNENDO();

		// ��ЃR���g���[���}�X�^�����񌎎擾
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int initialMonth = cmpMst.getCMP_KISYU().intValue();

		// ���Z�R���g���[���}�X�^��茈�Z�`�[���͋敪�擾
		GL_CTL_MSTDao glctldao = (GL_CTL_MSTDao) container.getComponent(GL_CTL_MSTDao.class);
		GL_CTL_MST glCtlMst = glctldao.getGL_CTL_MSTByIKaicode(companyCode);

		if (glCtlMst == null) {
			throw new TEnvironmentException("W00084", "C00142", companyCode);
		}

		int ksnNyuKbn = glCtlMst.getKSN_NYU_KBN();

		Date ksndate = new GregorianCalendar(year, initialMonth - 1, 1).getTime();

		ksndate = DateUtil.addMonth(ksndate, -1);

		// ��̌��Z�`�[���t��
		boolean isNextKsnDate = true;

		// �ꎞ�ۑ��p
		Date ksndateTmp = new Date();

		// ���Z�敪
		int ksndivision = simCtl.getKSN_KBN();

		// ���Z�i�K�敪
		int ksnDdivision = getAccountStageDivision(companyCode);

		// ���Z�敪�ƌ��Z�i�K�敪���r
		// isKsnMonth�̎d�l�ɍ��킹�邽�߁A�����������߂�(addMonth��+1,addDay��-1)
		if (ksndivision < ksnDdivision) {
			isNextKsnDate = false;
		}

		switch (ksnNyuKbn) {
			case 0:
				// �N���Z
				ksndate = DateUtil.addYear(ksndate, 1);
				break;

			case 1:
				// �������Z
				for (i = 1; i < 3; i++) {
					ksndateTmp = DateUtil.addMonth(ksndate, 6);

					if (DateUtil.getMonth(ksndateTmp) <= DateUtil.getMonth(date)) {
						ksndate = ksndateTmp;
					} else {
						if (isNextKsnDate) {
							ksndate = ksndateTmp;
						}
						break;
					}
				}
				break;

			case 2:
				// �l�������Z
				for (i = 1; i < 5; i++) {
					ksndateTmp = DateUtil.addMonth(ksndate, 3);

					if (DateUtil.getMonth(ksndateTmp) <= DateUtil.getMonth(date)) {
						ksndate = ksndateTmp;
					} else {
						if (isNextKsnDate) {
							ksndate = ksndateTmp;
						}
						break;
					}
				}
				break;

			default:
				break;

		}

		return DateUtil.toYMDString(DateUtil.getLastDate(ksndate));
	}

	/**
	 * ��ʉ݊��Z.<br>
	 * �w��R�[�h�����Ƀ}�X�^������擾
	 * 
	 * @param money �O�݋��z
	 * @param rate �O�݃��[�g
	 * @param baseCurrencyCode ��ʉ݃R�[�h
	 * @param foreignCurrencyCode �O�݃R�[�h
	 * @param companyCode ��ЃR�[�h
	 * @return ��ʉ݂Ɋ��Z�������z
	 */
	public static BigDecimal convertToBaseCurrency(BigDecimal money, double rate, String baseCurrencyCode,
		String foreignCurrencyCode, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ��ЃR���g���[���}�X�^
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST companyInfo = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (companyInfo == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		// �ʉ݃}�X�^
		CUR_MSTDao curdao = (CUR_MSTDao) container.getComponent(CUR_MSTDao.class);

		// ��ʉ�
		CUR_MST baseCurrencyInfo = curdao.getCUR_MST(companyCode, baseCurrencyCode);

		if (baseCurrencyInfo == null) {
			throw new TEnvironmentException("W00084", "C01985", baseCurrencyCode);
		}

		// �O��
		CUR_MST foreignCurrencyInfo = curdao.getCUR_MST(companyCode, foreignCurrencyCode);

		if (foreignCurrencyInfo == null) {
			throw new TEnvironmentException("W00084", "C01985", foreignCurrencyCode);
		}

		return convertToBaseCurrency(money, rate, baseCurrencyInfo, foreignCurrencyInfo, companyInfo);
	}

	/**
	 * ��ʉ݊��Z
	 * 
	 * @param money �O�݋��z
	 * @param rate �O�݃��[�g
	 * @param baseCurrencyInfo ��ʉݏ��(�G���e�B�e�B)
	 * @param foreignCurrencyInfo �O�ݏ��(�G���e�B�e�B)
	 * @param companyInfo ��Џ��(�G���e�B�e�B)
	 * @return ��ʉ݂Ɋ��Z�������z
	 */
	public static BigDecimal convertToBaseCurrency(BigDecimal money, double rate, CUR_MST baseCurrencyInfo,
		CUR_MST foreignCurrencyInfo, CMP_MST companyInfo) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		CurrencyConvert logic = (CurrencyConvert) container.getComponent(CurrencyConvert.class);
		return logic.getAmountToBase(money, rate, baseCurrencyInfo, foreignCurrencyInfo, companyInfo);

	}

	/**
	 * �O�݊��Z.<br>
	 * �w��R�[�h�����Ƀ}�X�^������擾
	 * 
	 * @param money �M�݋��z
	 * @param rate �O�݃��[�g
	 * @param foreignCurrencyCode �O�݃R�[�h
	 * @param companyCode ��ЃR�[�h
	 * @return �O�ݒʉ݂Ɋ��Z�������z
	 */
	public static BigDecimal convertToForeignCurrency(BigDecimal money, double rate, String foreignCurrencyCode,
		String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ��ЃR���g���[���}�X�^
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST companyInfo = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (companyInfo == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		// �O��
		CUR_MSTDao curdao = (CUR_MSTDao) container.getComponent(CUR_MSTDao.class);
		CUR_MST foreignCurrencyInfo = curdao.getCUR_MST(companyCode, foreignCurrencyCode);

		if (foreignCurrencyInfo == null) {
			throw new TEnvironmentException("W00084", "C01985", foreignCurrencyCode);
		}

		return convertToForeignCurrency(money, rate, foreignCurrencyInfo, companyInfo);
	}

	/**
	 * �O�݊��Z
	 * 
	 * @param money �M�݋��z
	 * @param rate �O�݃��[�g
	 * @param foreignCurrencyInfo �O�ݏ��(�G���e�B�e�B)
	 * @param companyInfo ��Џ��(�G���e�B�e�B)
	 * @return �O�ݒʉ݂Ɋ��Z�������z
	 */
	public static BigDecimal convertToForeignCurrency(BigDecimal money, double rate, CUR_MST foreignCurrencyInfo,
		CMP_MST companyInfo) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		CurrencyConvert logic = (CurrencyConvert) container.getComponent(CurrencyConvert.class);
		return logic.getAmountToForeign(money, rate, foreignCurrencyInfo, companyInfo);

	}

	/**
	 * �Ώۓ��t���_�ł̃��[�g�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date �Ώۓ��t
	 * @param companyCode ��ЃR�[�h
	 * @return ���[�g(Double)
	 */
	public static double getCurrencyRate(String currencyCode, Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// �Ώۓ��t����ŐV�̃��[�g�擾
		RATE_MSTDao ratedao = (RATE_MSTDao) container.getComponent(RATE_MSTDao.class);
		List ratemst = ratedao.getNewRate(companyCode, currencyCode, date);

		if (ratemst.isEmpty()) {
			return -1;
		}

		return ((RATE_MST) ratemst.get(0)).getCUR_RATE().doubleValue();
	}

	/**
	 * �Ώۓ��t���_�ł̃��[�g�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date �Ώۓ��t
	 * @param companyCode ��ЃR�[�h
	 * @return ���[�g(BigDecimal)
	 */
	public static BigDecimal getCurrencyRateDecimal(String currencyCode, Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// �Ώۓ��t����ŐV�̃��[�g�擾
		RATE_MSTDao ratedao = (RATE_MSTDao) container.getComponent(RATE_MSTDao.class);
		List ratemst = ratedao.getNewRate(companyCode, currencyCode, date);

		if (ratemst.isEmpty()) {
			return new BigDecimal("1");
		}

		return ((RATE_MST) ratemst.get(0)).getCUR_RATE();
	}

	/**
	 * �Ώےʉ݂̏����_�ȉ������擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param companyCode ��ЃR�[�h
	 * @return �����_�ȉ�����
	 */
	public static int getCurrencyDigit(String currencyCode, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// �ʉ݃}�X�^��菬���_�ȉ������擾
		CUR_MSTDao curdao = (CUR_MSTDao) container.getComponent(CUR_MSTDao.class);
		CUR_MST curMst = curdao.getCUR_MST(companyCode, currencyCode);

		if (curMst == null) {
			throw new TEnvironmentException("W00084", "C01985", currencyCode);
		}

		return curMst.getDEC_KETA();
	}

	/**
	 * �Ώےʉ݂̏����_�ȉ������擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param companyCode ��ЃR�[�h
	 * @return �����_�ȉ�����
	 */
	public static CUR_MST getCurrency(String currencyCode, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// �ʉ݃}�X�^��菬���_�ȉ������擾
		CUR_MSTDao curdao = (CUR_MSTDao) container.getComponent(CUR_MSTDao.class);
		CUR_MST curMst = curdao.getCUR_MST(companyCode, currencyCode);

		if (curMst == null) {
			throw new TEnvironmentException("W00084", "C01985", currencyCode);
		}

		return curMst;
	}

	/**
	 * ��ʉ݂̏����_�ȉ������擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ��ʉ� �����_�ȉ�����
	 */
	public static int getBaseCurrencyDigit(String companyCode) {
		return getCurrencyDigit(getBaseCurrency(companyCode), companyCode);
	}

	/**
	 * ��ʉ݃R�[�h�擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ��ʉ݃R�[�h
	 */
	public static String getBaseCurrency(String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ��ЃR���g���[���}�X�^����ʉ݃R�[�h�擾
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		return cmpMst.getCUR_CODE();
	}

	/**
	 * ����Ōv�Z(����)
	 * 
	 * @param money �Ώۋ��z
	 * @param taxRate �Ń��[�g(%)
	 * @param division ����d���敪
	 * @param digit �����_�ȉ�����
	 * @param companyCode ��ЃR�[�h
	 * @return ����
	 */
	public static BigDecimal calculateTaxInside(BigDecimal money, double taxRate, int division, int digit,
		String companyCode) {

		// ��Ѓ}�X�^��艼�����Œ[�������A��������Œ[���������擾
		int fractionTempPay = 0;
		int fractionTempTake = 0;

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ��ЃR���g���[���}�X�^����ʉ݃R�[�h�擾
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		fractionTempPay = cmpMst.getKB_KBN();
		fractionTempTake = cmpMst.getKU_KBN();

		double tax;

		tax = money.doubleValue() * taxRate / (100 + taxRate);
		BigDecimal returnTax = new BigDecimal(tax);

		if (division == 1) { // ����ېŁi�������Łj
			switch (fractionTempTake) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, digit);
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, digit);
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, digit);
					break;

			}

		} else if (division == 2) {
			switch (fractionTempPay) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, digit);
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, digit);
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, digit);
					break;

			}
		}

		return returnTax;
	}

	/**
	 * ����Ōv�Z(�O��)
	 * 
	 * @param money �Ώۋ��z
	 * @param taxRate �Ń��[�g(%)
	 * @param division ����d���敪
	 * @param digit �����_�ȉ�����
	 * @param companyCode ��ЃR�[�h
	 * @return �O��
	 */
	public static BigDecimal calculateTaxOutside(BigDecimal money, double taxRate, int division, int digit,
		String companyCode) {
		// ��Ѓ}�X�^��艼�����Œ[�������A��������Œ[���������擾
		int fractionTempPay = 0;
		int fractionTempTake = 0;

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ��ЃR���g���[���}�X�^����ʉ݃R�[�h�擾
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		fractionTempPay = cmpMst.getKB_KBN();
		fractionTempTake = cmpMst.getKU_KBN();

		double tax;
		tax = money.doubleValue() * taxRate / 100;
		BigDecimal returnTax = new BigDecimal(tax);

		if (division == 1) { // ����ېŁi�������Łj
			switch (fractionTempTake) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, digit);
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, digit);
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, digit);
					break;

			}

		} else if (division == 2) {
			switch (fractionTempPay) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, digit);
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, digit);
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, digit);
					break;

			}
		}

		return returnTax;
	}

	/**
	 * �N�x�擾 ���񌎂ɉ������Ώۓ��t�̔N�x���擾
	 * 
	 * @param ymd �Ώ۔N����
	 * @param companyCode ��ЃR�[�h
	 * @return �Ώۓ��t�̔N�x
	 * @throws ParseException
	 */
	public static int getFiscalYear(String ymd, String companyCode) throws ParseException {
		return getFiscalYear(DateUtil.toYMDDate(ymd), companyCode);
	}

	/**
	 * �N�x�擾 ���񌎂ɉ������Ώۓ��t�̔N�x���擾
	 * 
	 * @param date �Ώ۔N����
	 * @param companyCode ��ЃR�[�h
	 * @return �Ώۓ��t�̔N�x
	 */
	public static int getFiscalYear(Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// �Ώ۔N�����̔N
		int year = DateUtil.getYear(date);

		// �Ώ۔N�����̌�
		int month = DateUtil.getMonth(date);

		// ��ЃR���g���[���}�X�^�����񌎎擾
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int initialMonth = cmpMst.getCMP_KISYU().intValue();

		if (initialMonth > month) {
			year = year - 1;
		}

		return year;
	}

	/**
	 * ���x�擾 ���񌎂ɉ������Ώۓ��t�̌��x���擾
	 * 
	 * @param ymd �Ώ۔N����
	 * @param companyCode ��ЃR�[�h
	 * @return �Ώۓ��t�̌��x
	 * @throws ParseException
	 */
	public static int getFiscalMonth(String ymd, String companyCode) throws ParseException {

		return getFiscalMonth(DateUtil.toYMDDate(ymd), companyCode);
	}

	/**
	 * ���x�擾 ���񌎂ɉ������Ώۓ��t�̌��x���擾
	 * 
	 * @param date �Ώ۔N����
	 * @param companyCode ��ЃR�[�h
	 * @return �Ώۓ��t�̌��x
	 */
	public static int getFiscalMonth(Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// �Ώ۔N�����̌�
		int month = DateUtil.getMonth(date);

		// ��ЃR���g���[���}�X�^�����񌎎擾
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int initialMonth = cmpMst.getCMP_KISYU().intValue();

		if (month >= initialMonth) {
			month = month - initialMonth + 1;
		} else {
			month = month + 12 - initialMonth + 1;
		}

		return month;
	}

	/**
	 * ���ߔN���擾 ���ߔN���֘A�̒l���擾����B <br>
	 * ���x�͒��ߌ����牽�����ڂ��擾���������w��(0�Ȃ�Β��ߌ�)�B
	 * 
	 * @param monthFromCloseDate ���x
	 * @param companyCode ��ЃR�[�h
	 * @return �Ώ۔N������("yyyy/mm/dd"�`��)
	 */
	public static String getCloseDate(int monthFromCloseDate, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		int addMonth; // ���Z����
		int simmonth; // ���ߌ�
		Date simStartdate; // ��P���J�n��
		Date date = null;

		try {
			// ���ߐ���e�[�u�������ߌ��A��P���J�n���擾
			SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);
			SIM_CTL sim = simdao.getSIM_CTLByIKaicode(companyCode);
			simmonth = sim.getSIM_MON();
			simStartdate = sim.getSIM_STR_DATE();

			// ���������l
			addMonth = simmonth + monthFromCloseDate;

			// �Ώۓ��t�擾
			date = DateUtil.addDay(DateUtil.addMonth(simStartdate, addMonth), -1);

		} catch (Exception e) {
			// no error
		}

		return DateUtil.toYMDString(date);
	}

	/**
	 * ���ߔN���擾�i���Z�i�K�j ���ߔN���֘A�̒l���擾����B(���Z�i�K���l���������ߔN�����擾) <br>
	 * ���x�͒��ߌ����牽�����ڂ��擾���������w��(0�Ȃ�Β��ߌ�)�B
	 * 
	 * @param monthFromCloseDate ���x
	 * @param companyCode ��ЃR�[�h
	 * @return �Ώ۔N������("yyyy/mm/dd"�`��)
	 */
	public static String getCloseDateForAccountStage(int monthFromCloseDate, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ���ߐ���e�[�u�������ߌ��A��P���J�n���A���Z�敪�擾
		SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);
		SIM_CTL simCtl = simdao.getSIM_CTLByIKaicode(companyCode);

		if (simCtl == null) {
			throw new TEnvironmentException("W00084", "C10258", companyCode);
		}

		// ���ߌ�
		int simmonth = simCtl.getSIM_MON();
		// ��P���J�n��
		Date simStartdate = simCtl.getSIM_STR_DATE();
		// ���Z�敪
		int ksndivision = simCtl.getKSN_KBN();

		// ���Z����: ���������l
		int addMonth = simmonth + monthFromCloseDate;

		// ���Z�i�K�敪
		int ksnDdivision = getAccountStageDivision(companyCode);

		// ���Z�敪�ƌ��Z�i�K�敪���r
		// isKsnMonth�̎d�l�ɍ��킹�邽�߁A�����������߂�(addMonth��+1,addDay��-1)
		if (ksndivision < ksnDdivision && !(simmonth == 0 && ksndivision == 0)
			&& isKsnMonth(DateUtil.addDay(DateUtil.addMonth(simStartdate, simmonth - 1 + 1), -1), companyCode)) {
			addMonth = addMonth - 1;
		}

		// �Ώۓ��t�擾
		Date date = DateUtil.addDay(DateUtil.addMonth(simStartdate, addMonth), -1);
		return DateUtil.toYMDString(date);
	}

	/**
	 * ���Z�i�K�敪�̎擾.<br>
	 * ���Z�R���g���[���}�X�^���
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ���Z�i�K�敪
	 */
	public static int getAccountStageDivision(String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ���Z�R���g���[���}�X�^��茈�Z�`�[���͋敪�擾
		GL_CTL_MSTDao glctldao = (GL_CTL_MSTDao) container.getComponent(GL_CTL_MSTDao.class);

		GL_CTL_MST glCtlMst = glctldao.getGL_CTL_MSTByIKaicode(companyCode);

		if (glCtlMst == null) {
			throw new TEnvironmentException("W00084", "C00142", companyCode);
		}

		// ���Z�i�K�敪
		return glCtlMst.getKSD_KBN();
	}

	/**
	 * ��s�`�[���͉\���t�̃`�F�b�N<br>
	 * Company.FiscalPeriod�𗘗p
	 * 
	 * @param date �`�F�b�N�Ώۓ��t(yyyy/mm/dd)
	 * @param closeDate ���ߔN����(����)(yyyy/mm/dd)
	 * @return ��s�`�[���͉\/�s�\ true/false
	 * @throws ParseException
	 */
	@Deprecated
	public static boolean isSlipDateGoAhead(String date, String closeDate) throws ParseException {
		// �`�F�b�N�Ώۓ��t�����ߔN����(����)����Ȃ�΁A��s�`�[���͉\���t�ł͂Ȃ�
		return !(DateUtil.toYMDDate(date).after(DateUtil.addYear(DateUtil.toYMDDate(closeDate), 1)));
	}

	/**
	 * �`�[�ԍ��擾 �����̔ԃ}�X�^���ŏI�`�[�ԍ����擾����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param depCode ���͕���
	 * @param systemDivision �V�X�e���敪
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @param increase ����(1�܂���2)
	 * @return �`�[�ԍ�
	 */
	public static String getSlipNo(String companyCode, String userCode, String depCode, String systemDivision,
		String slipDate, int increase) {

		return getSlipNo(companyCode, userCode, depCode, systemDivision, slipDate, "", increase);

	}

	/**
	 * �`�[�ԍ��擾 �����̔ԃ}�X�^���ŏI�`�[�ԍ����擾����(�`�[��ʒǉ�)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param depCode ���͕���
	 * @param systemDivision �V�X�e���敪
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @param slipType �`�[���
	 * @param increase ����(1�܂���2)
	 * @return �`�[�ԍ�
	 */
	public static String getSlipNo(String companyCode, String userCode, String depCode, String systemDivision,
		String slipDate, String slipType, int increase) {

		String slipNo = "";

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ��Ѓ}�X�^���擾(�����̔ԕ������A�����ݒ荀��1�`3)
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int keta = cmpMst.getAUTO_NO_KETA();
		String prifix = "";

		String jid1 = cmpMst.getJID_1();
		String jid2 = cmpMst.getJID_2();
		String jid3 = cmpMst.getJID_3();

		// �����̔ԃR���g���[�����擾(�ŏI�ԍ�)
		AutoControl logic = (AutoControl) container.getComponent(AutoControl.class);

		if (systemDivision.equals("SPT")) {
			keta = 7;
		} else if (systemDivision.equals("TIJ")) {
			keta = 7;
		} else if (systemDivision.equals("NYU")) {
			keta = 4;
		} else {

			// �v���t�B�b�N�X
			prifix = logic.getPrefix("", companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());

			prifix += logic.getAutoSetting(jid1, companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());
			prifix += logic.getAutoSetting(jid2, companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());
			prifix += logic.getAutoSetting(jid3, companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());

			// �T�t�B�b�N�X
			prifix += logic.getSuffix("", companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());
		}

		if (Util.isNullOrEmpty(prifix)) {
			prifix = systemDivision;
		}

		int lastno = logic.getAutoNumber(companyCode, userCode, prifix, increase);

		String zero = "";
		for (int i = 0; i < keta; i++) {
			zero = zero + "0";
		}

		slipNo = prifix + StringUtil.rightBX((zero + lastno), keta);

		return slipNo;
	}

	/**
	 * �������ԍ��擾 �����̔ԃ}�X�^���ŏI�������ԍ����擾����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipDate �`�[���t
	 * @param userCode ���[�U�[�R�[�h
	 * @param depCode ���͕���
	 * @param bean �������j�}�X�^
	 * @param increase ����(1�܂���2)
	 * @return �������ԍ�
	 */
	public static String getARInvoiceNo(String prifixWord, String companyCode, String slipDate, String userCode, String depCode,
		ReceivePolicy bean, int increase) {

		String invNo = "";

		int keta = bean.getInvNoDigit(); // ����
		String prifix = "";

		InvoiceNoAdopt jid1 = bean.getInvoiceNoAdopt1();
		InvoiceNoAdopt jid2 = bean.getInvoiceNoAdopt2();
		InvoiceNoAdopt jid3 = bean.getInvoiceNoAdopt3();
		String jid1Name = bean.getInvNo1Name();
		String jid2Name = bean.getInvNo2Name();
		String jid3Name = bean.getInvNo3Name();

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// �����̔ԃR���g���[�����擾(�ŏI�ԍ�)
		ARAutoControlDao logic = (ARAutoControlDao) container.getComponent(ARAutoControlDao.class);
		// �v���t�B�b�N�X
		prifix = logic.getPrefix("", companyCode, userCode, depCode, slipDate);
		prifix += logic.getAutoSetting(jid1, jid1Name, companyCode, userCode, depCode, slipDate);
		prifix += logic.getAutoSetting(jid2, jid2Name, companyCode, userCode, depCode, slipDate);
		prifix += logic.getAutoSetting(jid3, jid3Name, companyCode, userCode, depCode, slipDate);

		// �T�t�B�b�N�X
		prifix += logic.getSuffix("", companyCode, userCode, depCode, slipDate);

		if (prifix.length() + keta > 20) {
			return "";
		}

		String prifix2 = prifixWord + prifix;

		int lastno = logic.getAutoNumber(companyCode, userCode, prifix2, increase);

		String zero = "";
		for (int i = 0; i < keta; i++) {
			zero = zero + "0";
		}

		invNo = prifix + StringUtil.rightBX((zero + lastno), keta);

		return invNo;
	}

	/**
	 * �g���I�y��p�FID�擾 �����̔ԃe�[�u�����ŏIID���擾����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param usrCode
	 * @param prgCode
	 * @param targetDate ���
	 * @param prifix �v���t�B�b�N�X
	 * @param digit ���������̌���
	 * @return OP�Ɩ�ID
	 * @throws TException
	 */
	public static String getAutoId(String companyCode, String usrCode, String prgCode, Date targetDate, String prifix,
		int digit) throws TException {

		String id = "";

		S2Container container = SingletonS2ContainerFactory.getContainer();
		OPAutoControlDao logic = (OPAutoControlDao) container.getComponent(OPAutoControlDao.class);

		int lastno = logic.getAutoId(companyCode, usrCode, prgCode, prifix, 1); // �����̔Ԓl�擾

		id = prifix + DateUtil.toY2MPlainString(targetDate); // yyMM

		String zero = "";
		for (int i = 0; i < digit; i++) {
			zero = zero + "0";
		}

		id = id + StringUtil.rightBX((zero + lastno), digit);

		return id;
	}

	/**
	 * �����̔ԃ}�X�^���ŏI�ԍ����擾����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param prifix ����L�[
	 * @param increase ����(1�܂���2)
	 * @return �ŏI�ԍ�
	 */
	public static int getAutoNumber(String companyCode, String userCode, String prifix, int increase) {

		// �����̔ԃR���g���[�����擾(�ŏI�ԍ�)
		S2Container container = SingletonS2ContainerFactory.getContainer();
		AutoControl logic = (AutoControl) container.getComponent(AutoControl.class);

		return logic.getAutoNumber(companyCode, userCode, prifix, increase);
	}

	/**
	 * �����̔ԃ}�X�^���ŏI�ԍ��𕶎���Ŏ擾����.<br>
	 * �w�肳�ꂽ�����ɖ����Ȃ��ꍇ�́A������0��t�^����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param prifix ����L�[
	 * @param increase ����(1�܂���2)
	 * @param keta ����
	 * @return �ŏI�ԍ�
	 */
	public static String getAutoNumber(String companyCode, String userCode, String prifix, int increase, int keta) {
		int lastno = getAutoNumber(companyCode, userCode, prifix, increase);

		return StringUtil.rightBX(NumberFormatUtil.zeroFill(lastno, keta), keta);
	}

	/**
	 * �v���O�����r���J�n <br>
	 * �o�b�`�R���g���[���}�X�^�ւ̓o�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param batId �o�b�`ID
	 * @param userId ���[�U�[ID
	 * @return �r������/���s true/false
	 */
	public static boolean isProgramLockOn(String companyCode, String batId, String userId) {
		return isProgramLockOn(companyCode, batId, batId, userId);
	}

	/**
	 * �v���O�����r���J�n <br>
	 * �o�b�`�R���g���[���}�X�^�ւ̓o�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param batId �o�b�`ID
	 * @param prgId �v���O����ID
	 * @param userId ���[�U�[ID
	 * @return �r������/���s true/false
	 */
	public static boolean isProgramLockOn(String companyCode, String batId, String prgId, String userId) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		try {
			// �o�b�`�R���g���[������
			BAT_CTLDao batdao = (BAT_CTLDao) container.getComponent(BAT_CTLDao.class);

			BAT_CTL batMe = batdao.getBAT_CTLById(companyCode, batId, prgId, userId);

			if (!Util.isNullOrEmpty(batMe)) {
				// ���ɔr���ς݂̏ꍇ
				return true;
			}

			BAT_CTL bat = batdao.getBatCtlByPK(companyCode, batId);

			if (bat != null) {
				// �Y���̂��̂�����������ɔr������Ă���
				return false;
			}

			// �o�b�`�R���g���[���o�^
			BAT_CTL batCTL = new BAT_CTL();
			Date sysDate = Util.getCurrentDate();

			batCTL.setKAI_CODE(companyCode);
			batCTL.setBAT_ID(batId);
			batCTL.setBAT_STR_DATE(sysDate);
			batCTL.setBAT_END_DATE(sysDate);
			batCTL.setINP_DATE(sysDate);
			batCTL.setUPD_DATE(sysDate);
			batCTL.setPRG_ID(prgId);
			batCTL.setUSR_ID(userId);

			batdao.insert(batCTL);

		} catch (Exception e) {
			ServerErrorHandler.handledException(e);
			return false;

		}

		return true;
	}

	/**
	 * �v���O�����r������ <br>
	 * �o�b�`�R���g���[���}�X�^�̓o�^������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param batId �o�b�`�R���g���[��ID
	 * @param userId ���[�UID
	 * @return �r����������/���s true/false
	 */
	public static boolean isProgramLockOff(String companyCode, String batId, String userId) {
		return isProgramLockOff(companyCode, batId, batId, userId);
	}

	/**
	 * �v���O�����r������ <br>
	 * �o�b�`�R���g���[���}�X�^�̓o�^������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param batId �o�b�`�R���g���[��ID
	 * @param prgId �v���O����ID
	 * @param userId ���[�UID
	 * @return �r����������/���s true/false
	 */
	public static boolean isProgramLockOff(String companyCode, String batId, String prgId, String userId) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		try {
			// �o�b�`�R���g���[������
			BAT_CTLDao batdao = (BAT_CTLDao) container.getComponent(BAT_CTLDao.class);
			BAT_CTL batCTL = batdao.getBAT_CTLById(companyCode, batId, prgId, userId);

			// �Y���̂��̂��Ȃ���������ɔr����������Ă���
			if (batCTL == null) {
				return true;
			}

			batdao.deleteById(companyCode, batId, prgId, userId);

			return true;

		} catch (Exception e) {
			ServerErrorHandler.handledException(e);
			return false;
		}
	}

	/**
	 * �o�[���Z�o ���ߓ��A�㌎�A�x�����������ɏo�[��(�x�����^�����\���)���Z�o
	 * 
	 * @param baseDate ���(yyyy/mm/dd)
	 * @param closeDay ���ߌ�
	 * @param nextMonth �㌎
	 * @param cashDay �x����
	 * @return �o�[��
	 * @throws ParseException
	 */
	public static String getBalanceDate(String baseDate, int closeDay, int nextMonth, int cashDay)
		throws ParseException {

		Date date = DateUtil.toYMDDate(baseDate);

		// ���(�`�[���t)�̓����擾
		int baseday = DateUtil.getDay(date);

		// 29���ȍ~�͑S�Č�������(��99)�ɕϊ�
		if (closeDay > 29) {
			closeDay = 99;
		}

		// ��r ���ߓ��𒴂������̏ꍇ�A�������
		if (closeDay < baseday) {
			date = DateUtil.addMonth(date, 1);
		}

		// �㌎�����炷
		date = DateUtil.addMonth(date, nextMonth);

		// ��������
		// 29���ȍ~�͑S�Č�������
		if (cashDay > 28) {
			date = DateUtil.getLastDate(DateUtil.getYear(date), DateUtil.getMonth(date));
		} else {
			date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
		}

		return DateUtil.toYMDString(date);
	}

	/**
	 * �o�[�������l�Z�o <br>
	 * �����������Ɏx�����^�����\��������l���擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param customerCode �����R�[�h
	 * @param customerConditionCode ���������R�[�h(�x�������[�h�̏ꍇ�̂ݕK�{)
	 * @param baseDate �`�[���t(�v���)
	 * @param division �敪(0�F�x�����A1�F�����\���)
	 * @return �o�[��
	 * @throws ParseException
	 */
	public static String getInitialBalanceDate(String companyCode, String customerCode, String customerConditionCode,
		String baseDate, int division) throws ParseException {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		String jcDate;
		String jcMon;
		String jpDate;
		String sihakbn = "s";

		if (division == 0) {
			TRI_SJ_MSTDao trisjdao = (TRI_SJ_MSTDao) container.getComponent(TRI_SJ_MSTDao.class);
			List trisjlist = trisjdao.getTriSjDateRangeInfo(companyCode, customerCode, customerConditionCode,
				DateUtil.toYMDDate(baseDate));

			if (trisjlist.size() == 0) {
				return null;
			}

			jcDate = ((TRI_SJ_MST) trisjlist.get(0)).getSJC_DATE();
			jcMon = ((TRI_SJ_MST) trisjlist.get(0)).getSJR_MON();
			jpDate = ((TRI_SJ_MST) trisjlist.get(0)).getSJP_DATE();
			sihakbn = ((TRI_SJ_MST) trisjlist.get(0)).getSIHA_KBN();
		} else {
			TRI_MSTDao tridao = (TRI_MSTDao) container.getComponent(TRI_MSTDao.class);
			List trilist = tridao.getTriDateRangeInfo(companyCode, customerCode, DateUtil.toYMDDate(baseDate));

			if (trilist.size() == 0) {
				return null;
			}

			jcDate = ((TRI_MST) trilist.get(0)).getNJ_C_DATE();
			jcMon = ((TRI_MST) trilist.get(0)).getNJ_R_MON();
			jpDate = ((TRI_MST) trilist.get(0)).getNJ_P_DATE();

			jcDate = Util.isNullOrEmpty(jcDate) ? "0" : jcDate;
			jcMon = Util.isNullOrEmpty(jcMon) ? "0" : jcMon;
			jpDate = Util.isNullOrEmpty(jpDate) ? "0" : jpDate;

		}

		if ("00".equals(Util.avoidNull(sihakbn))) {
			// �Վ��̏ꍇ�A�x�������Ɋ֌W�����A�J�����_�[�}�X�^����
			AP_CAL_MSTDao caldao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);
			List callist = caldao.getApCalBankInfo(companyCode, DateUtil.toYMDDate(baseDate), 1, 0);

			if (callist.size() == 0) {
				return null;
			}

			return DateUtil.toYMDString(((AP_CAL_MST) callist.get(0)).getCAL_DATE());

		} else {
			// �x������/���������������n��
			return getBalanceDate(baseDate, Integer.parseInt(jcDate), Integer.parseInt(jcMon), Integer.parseInt(jpDate));
		}
	}

	/**
	 * ���ߖ����c�Ɠ��擾<br>
	 * �Ώۓ����疢���́A�ł����߂̋�s�c�Ɠ��A�Վ��x���Ώۓ��A�Ј��x���Ώۓ����擾����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param baseDate ���
	 * @param division �敪
	 * @return ���߂̑Ώۓ��i�J�����_�[���Ȃ��ꍇ�A�G���[�̏ꍇ��null�j
	 * @Deprecated Date�^�����̕��𗘗p���邱��.
	 */
	public static Date getNextBusinessDay(String companyCode, String baseDate, int division) {
		try {
			return getNextBusinessDay(companyCode, DateUtil.toYMDDate(baseDate), division);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ���ߖ����c�Ɠ��擾<br>
	 * �Ώۓ����疢���́A�ł����߂̋�s�c�Ɠ��A�Վ��x���Ώۓ��A�Ј��x���Ώۓ����擾����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param baseDate ���
	 * @param division �敪
	 * @return ���߂̑Ώۓ��i�J�����_�[���Ȃ��ꍇ�A�G���[�̏ꍇ��null�j
	 */
	public static Date getNextBusinessDay(String companyCode, Date baseDate, int division) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			AP_CAL_MSTDao dao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);
			AP_CAL_MST entity;

			switch (division) {
				case 0:
					// ��s�c�Ɠ�
					entity = dao.getNearBusinessDayInfo(companyCode, baseDate);
					break;

				case 1:
					// �Վ��x���Ώۓ�
					entity = dao.getNearTempPayDayInfo(companyCode, baseDate);
					break;

				case 2:
					// �Ј��x���Ώۓ�
					entity = dao.getNearEmpPayDayInfo(companyCode, baseDate);
					break;

				default:
					return null;
			}

			// �Ώۓ����Ȃ��ꍇ��null��Ԃ�
			if (entity == null) {
				return null;

			} else {
				return entity.getCAL_DATE();
			}

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ���߉ߋ��c�Ɠ��擾<br>
	 * �Ώۓ�����ߋ��́A�ł����߂̋�s�c�Ɠ��A�Վ��x���Ώۓ��A�Ј��x���Ώۓ����擾����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param baseDate ���
	 * @param division �敪
	 * @return ���߂̑Ώۓ��i�J�����_�[���Ȃ��ꍇ�A�G���[�̏ꍇ��null�j
	 */
	public static Date getPreviousBusinessDay(String companyCode, String baseDate, int division) {
		try {
			return getPreviousBusinessDay(companyCode, DateUtil.toYMDDate(baseDate), division);

		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * ���߉ߋ��c�Ɠ��擾<br>
	 * �Ώۓ�����ߋ��́A�ł����߂̋�s�c�Ɠ��A�Վ��x���Ώۓ��A�Ј��x���Ώۓ����擾����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param baseDate ���
	 * @param division �敪
	 * @return ���߂̑Ώۓ��i�J�����_�[���Ȃ��ꍇ�A�G���[�̏ꍇ��null�j
	 */
	public static Date getPreviousBusinessDay(String companyCode, Date baseDate, int division) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			AP_CAL_MSTDao dao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);
			AP_CAL_MST entity;

			switch (division) {
				case 0:
					// ��s�c�Ɠ�
					entity = dao.getNearPreBusinessDayInfo(companyCode, baseDate);
					break;

				case 1:
					// �Վ��x���Ώۓ�
					entity = dao.getNearPreTempPayDayInfo(companyCode, baseDate);
					break;

				case 2:
					// �Ј��x���Ώۓ�
					entity = dao.getNearPreEmpPayDayInfo(companyCode, baseDate);
					break;

				default:
					return null;
			}

			// �Ώۓ����Ȃ��ꍇ��null��Ԃ�
			if (entity == null) {
				return null;

			} else {
				return entity.getCAL_DATE();
			}

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * �w����̋�s�J�����_�[���o�^����Ă��邩�ǂ���
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param strDate �w���
	 * @return <li>true : �o�^����Ă��� <li>false: �o�^����Ă��Ȃ�
	 */
	public static boolean isRegisteredCalender(String companyCode, String strDate) {

		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();
			AP_CAL_MSTDao dao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);

			Date date = DateUtil.toYMDDate(strDate);

			AP_CAL_MST bean = dao.getAP_CAL_MST(companyCode, date);

			return bean != null;

		} catch (ParseException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �U�������擾����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param baseDate �x���\���
	 * @param paymentDivision �Վ�:10/�莞
	 * @return �U����
	 */
	public static String getDateTransferToAccount(String companyCode, String baseDate, String paymentDivision) {
		try {
			Date date = getDateTransferToAccount(companyCode, DateUtil.toYMDDate(baseDate), paymentDivision);
			return DateUtil.toYMDString(date);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * �U�������擾����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param baseDate �x���\���
	 * @param paymentDivision �Վ�:10/�莞
	 * @return �U����
	 */
	public static Date getDateTransferToAccount(String companyCode, Date baseDate, String paymentDivision) {
		Date dateTransferToAccount = null;

		// �莞�̏ꍇ�͎x�����j�}�X�^�����ɐU�������擾����
		if ("10".equals(paymentDivision)) {
			// �x�����j�}�X�^�̐ݒ�l���擾
			S2Container container = SingletonS2ContainerFactory.getContainer();
			AP_SHH_MSTDao shhDao = (AP_SHH_MSTDao) container.getComponent(AP_SHH_MSTDao.class);
			AP_SHH_MST apShhMstbean = shhDao.getAP_SHH_MST(companyCode);

			if (Util.isNullOrEmpty(apShhMstbean)) {
				// �ݒ肻�̂��̂��Ȃ��ꍇ�͐U���� = �x���\���
				dateTransferToAccount = baseDate;
			} else {
				// �x���\���̏ꍇ�͐U���� = �x���\���
				if (isPayBusinessDate(companyCode, baseDate, 0)) {
					dateTransferToAccount = baseDate;
				} else {
					// ��s�x�Ɠ��̏ꍇ�͑O��or�����̎w����擾���ăZ�b�g
					int day = DateUtil.getDay(baseDate);
					switch (day) {
						case 1:
							// �x���\����̓��t��1���A����1���̎w�肪�\�ȏꍇ
							if (apShhMstbean.getSHH_SIHA_1() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_1() == 0) {
									// �O��
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_1() == 1) {
									// ����
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 5:
							// �x���\����̓��t��5���A����5���̎w�肪�\�ȏꍇ
							if (apShhMstbean.getSHH_SIHA_5() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_5() == 0) {
									// �O��
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_5() == 1) {
									// ����
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 10:
							// �x���\����̓��t��10���A����10���̎w�肪�\�ȏꍇ
							if (apShhMstbean.getSHH_SIHA_10() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_10() == 0) {
									// �O��
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_10() == 1) {
									// ����
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 15:
							// �x���\����̓��t��15���A����15���̎w�肪�\�ȏꍇ
							if (apShhMstbean.getSHH_SIHA_15() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_15() == 0) {
									// �O��
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_15() == 1) {
									// ����
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 20:
							// �x���\����̓��t��20���A����20���̎w�肪�\�ȏꍇ
							if (apShhMstbean.getSHH_SIHA_20() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_20() == 0) {
									// �O��
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_20() == 1) {
									// ����
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 25:
							// �x���\����̓��t��25���A����25���̎w�肪�\�ȏꍇ
							if (apShhMstbean.getSHH_SIHA_25() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_25() == 0) {
									// �O��
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_25() == 1) {
									// ����
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						default:
							// �x���\����̓��t���������A���������̎w�肪�\�ȏꍇ
							if (baseDate.equals(DateUtil.getLastDate(baseDate)) && apShhMstbean.getSHH_SIHA_30() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_30() == 0) {
									// �O��
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_30() == 1) {
									// ����
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
					}
				}
			}
			// �x�����j�ɐݒ�ł��Ȃ����t�̏ꍇ�͐U���� = �x���\���
			if (Util.isNullOrEmpty(dateTransferToAccount)) {
				dateTransferToAccount = baseDate;
			}
			// �Վ��̏ꍇ�͐U���� = �x���\���
		} else {
			dateTransferToAccount = baseDate;
		}
		return dateTransferToAccount;
	}

	/**
	 * ��t�ԍ��擾<br>
	 * �����̔Ԃ�����t�ԍ����擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @return ���ݓ��t�� YYYYMMDD�t�H�[�}�b�g + ##(�A��)�i�擾�ł��Ȃ��ꍇ��null�j
	 */
	public static String getAcceptanceNo(String companyCode, String userCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {

			Date currentDate = Util.getCurrentDate();
			Date currentYMDDate = DateUtil.toYMDDate(currentDate);

			// ���ݓ��t���v���t�B�b�N�X�ɂ����ő�ԍ����擾
			AP_UKE_CTLDao dao = (AP_UKE_CTLDao) container.getComponent(AP_UKE_CTLDao.class);
			AP_UKE_CTL entity = dao.getAP_UKE_CTLByKaicodeutkdate(companyCode, currentYMDDate);

			int lastNo = 1;
			if (entity == null) {
				// ���݂��Ȃ���΁A�ԍ��F1��INSERT
				entity = (AP_UKE_CTL) container.getComponent(AP_UKE_CTL.class);
				entity.setKAI_CODE(companyCode);
				entity.setUTK_DATE(currentYMDDate);
				entity.setUTK_LAST_NO(lastNo);
				entity.setINP_DATE(currentDate);
				entity.setUPD_DATE(null);
				entity.setPRG_ID("AP0130");
				entity.setUSR_ID(userCode);

				dao.insert(entity);

			} else {
				// ���݂���΁A�ԍ��F�ԍ� + 1��UPDATE
				lastNo = entity.getUTK_LAST_NO() + 1;
				entity.setUTK_LAST_NO(lastNo);
				entity.setUPD_DATE(currentDate);
				entity.setUSR_ID(userCode);

				dao.update(entity);
			}

			String lastUkeNo = Integer.toString(lastNo);
			if (lastNo < 10) {
				lastUkeNo = "0" + lastUkeNo;
			}
			return DateUtil.toYMDString(currentDate).replace("/", "").replace("-", "") + lastUkeNo;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <s>�a���ϊ�<br>
	 * �a��(YY)�𐼗�(YYYY)�ɕϊ�<br>
	 * �a��͕�������I(2007�N4�������_�d�l) <br>
	 * </s><br>
	 * DateUtil�𗘗p���邱��.
	 * 
	 * @param JapaneseEra �a��(YY)
	 * @return ����i�擾�ł��Ȃ��ꍇ��null�j
	 * @deprecated DateUtil�𗘗p���邱��.
	 */
	@Deprecated
	public static String convertJapaneseEraToChristianEra(String JapaneseEra) {
		try {
			return Integer.toString(Integer.parseInt(JapaneseEra) + 1988);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <s>�a���ϊ��i�����Ή��j<br>
	 * �a��(YY)�𐼗�(YYYY)�ɕϊ�<br>
	 * </s><br>
	 * DateUtil�𗘗p���邱��.
	 * 
	 * @param JapaneseEra �a��(YY)
	 * @param gengou �����敪 <li>0:���a <li>1:����
	 * @return ����i�擾�ł��Ȃ��ꍇ��null�j
	 * @deprecated DateUtil�𗘗p���邱��.
	 */
	@Deprecated
	public static String convertJapaneseEraToChristianEra(String JapaneseEra, int gengou) {
		try {
			if (gengou == 0) {
				return Integer.toString(Integer.parseInt(JapaneseEra) + 1925);
			} else {
				return Integer.toString(Integer.parseInt(JapaneseEra) + 1988);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * �R�[�h�r���J�n<br>
	 * �R�[�h�ł̔r���J�n
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param division �����敪
	 * @param code �r���R�[�h
	 * @param gyoNo �sNo
	 * @param userCode ���[�U�[ID
	 * @param prgCode �v���O����ID
	 * @return �r�����b�N����/���s true/false
	 */
	public static boolean isCodeLockOn(String companyCode, String division, String code, String gyoNo, String userCode,
		String prgCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);

			HAITA_CTL entity = dao.getHAITA_CTLByKaicodeShorikbnTricodeGyono(companyCode, division, code, gyoNo);

			if (entity == null) {
				entity = (HAITA_CTL) container.getComponent(HAITA_CTL.class);
				entity.setKAI_CODE(companyCode);
				entity.setTRI_CODE(code);
				entity.setSHORI_KBN(division);
				entity.setGYO_NO(gyoNo);
				entity.setINP_DATE(Util.getCurrentDate());
				entity.setUSR_ID(userCode);
				entity.setPRG_ID(prgCode);

				dao.insert(entity);
			} else {
				// ���ɔr�����������Ă�����
				return false;
			}

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * �R�[�h�r�������J�n<br>
	 * �R�[�h�ł̔r���A���݃`�F�b�N�����ɋ����I�ɊJ�n����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param division �����敪
	 * @param code �r���R�[�h
	 * @param gyoNo �sNo
	 * @param userCode ���[�U�[ID
	 * @param prgCode �v���O����ID
	 * @return �r�����b�N����/���s true/false
	 */
	public static boolean isCodeLockOnCompulsion(String companyCode, String division, String code, String gyoNo,
		String userCode, String prgCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);
			HAITA_CTL entity = new HAITA_CTL();

			entity = (HAITA_CTL) container.getComponent(HAITA_CTL.class);
			entity.setKAI_CODE(companyCode);
			entity.setTRI_CODE(code);
			entity.setSHORI_KBN(division);
			entity.setGYO_NO(gyoNo);
			entity.setINP_DATE(Util.getCurrentDate());
			entity.setUSR_ID(userCode);
			entity.setPRG_ID(prgCode);

			dao.insert(entity);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �R�[�h�r������<br>
	 * ���[�U�R�[�h�ł̔r������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[ID
	 * @return �r�����b�N��������/���s true/false
	 */
	public static boolean isCodeLockOff(String companyCode, String userCode) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);

			dao.deleteLockUser(companyCode, userCode);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �R�[�h�r������<br>
	 * ���[�U�R�[�h�A�v���O����ID�ł̔r������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[ID
	 * @param prgCode �v���O����ID
	 * @return �r�����b�N��������/���s true/false
	 */
	public static boolean isCodeLockOff(String companyCode, String userCode, String prgCode) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);

			dao.deleteByUsrPrg(companyCode, userCode, prgCode);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �C�ӂ̃R�[�h�r������<br>
	 * �R�[�h�ł̔r������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param code �R�[�h
	 * @param division �敪
	 * @param gyoNo �s�ԍ�
	 * @param userCode ���[�U�[ID
	 * @return �r�����b�N��������/���s true/false
	 */
	public static boolean isCodeLockOffOption(String companyCode, String code, String division, String gyoNo,
		String userCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);

			HAITA_CTL haita = new HAITA_CTL();

			haita.setKAI_CODE(companyCode);
			haita.setTRI_CODE(code);
			haita.setSHORI_KBN(division);
			haita.setGYO_NO(gyoNo);
			haita.setUSR_ID(userCode);

			dao.delete(haita);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �������ʎc���\���̔���
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return true: �\������Afalse: �\�����Ȃ�
	 */
	public static boolean isLedgerEachDayBalanceView(String companyCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ���Z�R���g���[���}�X�^��茈�Z�`�[���͋敪�擾
		GL_CTL_MSTDao glctldao = (GL_CTL_MSTDao) container.getComponent(GL_CTL_MSTDao.class);

		GL_CTL_MST glCtlMst = glctldao.getGL_CTL_MSTByIKaicode(companyCode);

		if (glCtlMst == null) {
			throw new TEnvironmentException("W00084", "C00142", companyCode);
		}

		// ���Z�i�K�敪
		return 1 == glCtlMst.getMT_ZAN_HYJ_KBN();
	}

	/**
	 * �v���Ђ��t�։�Ђ�����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param appropriateCompanyCode �t�։�ЃR�[�h
	 * @return true: �t�։�ЁAfalse: �t�։�Ђł͂Ȃ�
	 */
	public static boolean isAppropriateCompanyReplace(String companyCode, String appropriateCompanyCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		KTK_MSTDao dao = (KTK_MSTDao) container.getComponent(KTK_MSTDao.class);

		KTK_MST mst = dao.getKTK_MSTByKaicodeKtkkaicode(companyCode, appropriateCompanyCode);

		if (mst == null) {
			return false;
		}

		return true;
	}

	/**
	 * �x�������������(��s�x�������l���j
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param date �x����
	 * @param division �敪(0:�莞/1:�Վ�)
	 * @return true: �x������Afalse: �x�����Ȃ�
	 */
	public static boolean isPayBusinessDate(String companyCode, Date date, int division) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		AP_SHH_MSTDao dao = (AP_SHH_MSTDao) container.getComponent(AP_SHH_MSTDao.class);
		AP_SHH_MST mst = dao.getAP_SHH_MST(companyCode);

		// �ݒ肻�̂��̂��Ȃ��ꍇ�͐������Ȃ�
		if (mst == null) {
			return true;
		}

		AP_CAL_MSTDao calDao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);
		AP_CAL_MST calBean = new AP_CAL_MST();

		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);

		Date nextMonthDate = date;
		int nextmonth = DateUtil.getMonth(DateUtil.addMonth(nextMonthDate, 1));

		// �x��������t���X�g
		List<Date> dayList = new LinkedList<Date>();

		String notExistsMsg = "W01028";
		String ym = "";

		// �莞�x���̏ꍇ
		if (division == 0) {
			// 1��
			if (mst.getSHH_SIHA_1() == 1) {

				if (mst.getSHH_BNK_KBN_1() == 0) {

					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, nextmonth, 1));
					if (calBean != null) {
						dayList.add(calBean.getCAL_DATE());
					} else {
						ym = DateUtil.toYMString(DateUtil.getDate(year, nextmonth, 1));
						throw new TRuntimeException(notExistsMsg, ym);
					}

					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 1));

				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 1));
				}

				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 1));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// �T��
			if (mst.getSHH_SIHA_5() == 1) {

				if (mst.getSHH_BNK_KBN_5() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 5));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 5));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 5));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// �P�O��
			if (mst.getSHH_SIHA_10() == 1) {

				if (mst.getSHH_BNK_KBN_10() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 10));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 10));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 10));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// �P�T��
			if (mst.getSHH_SIHA_15() == 1) {

				if (mst.getSHH_BNK_KBN_15() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 15));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 15));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 15));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// �Q�O��
			if (mst.getSHH_SIHA_20() == 1) {

				if (mst.getSHH_BNK_KBN_20() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 20));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 20));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 20));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// �Q�T��
			if (mst.getSHH_SIHA_25() == 1) {

				if (mst.getSHH_BNK_KBN_25() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 25));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 25));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 25));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// �R�O��
			if (mst.getSHH_SIHA_30() == 1) {

				if (mst.getSHH_BNK_KBN_30() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getLastDate(date));
				} else {

					calBean = calDao.getNearBusinessDayInfo(companyCode,
						DateUtil.getLastDate(DateUtil.addMonth(date, -1)));
					if (calBean != null) {
						dayList.add(calBean.getCAL_DATE());
					} else {
						ym = DateUtil.toYMString(DateUtil.addMonth(date, -1));
						throw new TRuntimeException(notExistsMsg, ym);
					}

					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getLastDate(date));

				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(date);
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// �x�����`�F�b�N
			for (Date payable : dayList) {
				if (date.equals(payable)) {
					return true;
				}
			}

			// �Վ��x���̏ꍇ
		} else {

			if (date.equals(BizUtil.getNextBusinessDay(companyCode, date, 1))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * �x�������������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param date �x����
	 * @param division �敪(0:�莞/1:�Վ�)
	 * @return true: �x������Afalse: �x�����Ȃ�
	 */
	public static boolean isPayDate(String companyCode, Date date, int division) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		AP_SHH_MSTDao dao = (AP_SHH_MSTDao) container.getComponent(AP_SHH_MSTDao.class);
		AP_SHH_MST mst = dao.getAP_SHH_MST(companyCode);

		// �ݒ肻�̂��̂��Ȃ��ꍇ�͐������Ȃ�
		if (mst == null) {
			return true;
		}

		// �����擾
		int day = DateUtil.getDay(date);
		// ���������擾
		int lastDay = DateUtil.getDay(DateUtil.getLastDate(date));

		if (division == 0) {
			// �莞�x���̏ꍇ
			if (mst.getSHH_SIHA_1() == 1) {
				if (day == 1) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_5() == 1) {
				if (day == 5) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_10() == 1) {
				if (day == 10) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_15() == 1) {
				if (day == 15) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_20() == 1) {
				if (day == 20) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_25() == 1) {
				if (day == 25) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_30() == 1) {
				if (day == lastDay) {
					return true;
				}
			}
		} else {

			// �Վ��x���̏ꍇ
			if (date.equals(BizUtil.getNextBusinessDay(companyCode, date, 1))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * �`�[�^�C�g���擾 <br>
	 * �擾�ł��Ȃ��ꍇ��NULL��Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param denSyuCode �`�[��ʃR�[�h
	 * @return �`�[�^�C�g��
	 */
	public static String getSlipTitle(String companyCode, String denSyuCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		DEN_SYU_MSTDao dao = (DEN_SYU_MSTDao) container.getComponent(DEN_SYU_MSTDao.class);

		DEN_SYU_MST mst = dao.getDEN_SYU_MSTByKaicodeDensyucode(companyCode, denSyuCode);

		if (mst == null) {
			return null;
		}

		return mst.getDEN_SYU_NAME_K();
	}

	/**
	 * ����Ȗڂ̐ݒ�
	 * 
	 * @param cmpCode ��ЃR�[�h
	 * @param denNo �`�[�ԍ�
	 */
	public static void setOtherItem(String cmpCode, String denNo) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		SWK_DTLDao SwkDtlDao = (SWK_DTLDao) container.getComponent(SWK_DTLDao.class);

		// �敪�t���O
		int dcKbn = 0;
		String kmkCode = "";
		String hkmCode = "";
		String ukmCode = "";
		String depCode = "";

		// �ؕ��������擾
		int numDebitCount = SwkDtlDao.getSwkDtlByKeta(cmpCode, denNo, dcKbn);
		// 1:N�̏ꍇ
		if (numDebitCount == 1) {

			dcKbn = 0;
			SWK_DTL SwkDtlKariItem = SwkDtlDao.getSwkDtlByKaicodeSwkden(cmpCode, denNo, dcKbn);
			kmkCode = SwkDtlKariItem.getSWK_KMK_CODE();
			hkmCode = SwkDtlKariItem.getSWK_HKM_CODE();
			ukmCode = SwkDtlKariItem.getSWK_UKM_CODE();
			depCode = SwkDtlKariItem.getSWK_DEP_CODE();

			dcKbn = 1;
			// ����Ȗڂ��X�V����
			SwkDtlDao.updateSwkDtlByCoItem(cmpCode, denNo, dcKbn, kmkCode, hkmCode, ukmCode, depCode);

		}

		dcKbn = 1;
		// �ݕ��������擾
		int numCreditCount = SwkDtlDao.getSwkDtlByKeta(cmpCode, denNo, dcKbn);
		// N:1�̏ꍇ
		if (numCreditCount == 1) {

			dcKbn = 1;
			SWK_DTL SwkDtlKariItem = SwkDtlDao.getSwkDtlByKaicodeSwkden(cmpCode, denNo, dcKbn);
			kmkCode = SwkDtlKariItem.getSWK_KMK_CODE();
			hkmCode = SwkDtlKariItem.getSWK_HKM_CODE();
			ukmCode = SwkDtlKariItem.getSWK_UKM_CODE();
			depCode = SwkDtlKariItem.getSWK_DEP_CODE();

			dcKbn = 0;
			// ����Ȗڂ��X�V����
			SwkDtlDao.updateSwkDtlByCoItem(cmpCode, denNo, dcKbn, kmkCode, hkmCode, ukmCode, depCode);

		}
	}

	/**
	 * ��������擾����
	 * 
	 * @param cmpCode
	 * @return �����
	 */
	public static Date getInitialDate(String cmpCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);

		return simdao.getSIM_CTLByIKaicode(cmpCode).getSIM_STR_DATE();

	}

	/**
	 * �����d��Ȗڂ��擾����.<br>
	 * �⏕�A���󂪖����ꍇ��NULL.
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCnt �Ȗڐ���敪
	 * @return �ȖڃR�[�h[0:�ȖڃR�[�h, 1:�⏕�ȖڃR�[�h, 2:����ȖڃR�[�h 3:�v�㕔��R�[�h]
	 */
	public static String[] getAutoSwkItem(String kaiCode, int kmkCnt) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		SWK_KMK_MSTDao dao = (SWK_KMK_MSTDao) container.getComponent(SWK_KMK_MSTDao.class);

		SWK_KMK_MST entity = dao.getSWK_KMK_MSTByKaicodeKmkcnt(kaiCode, kmkCnt);

		String[] kmkArrays = new String[4];

		if (entity != null) {
			kmkArrays[0] = entity.getKMK_CODE();
			kmkArrays[1] = entity.getHKM_CODE();
			kmkArrays[2] = entity.getUKM_CODE();
			kmkArrays[3] = entity.getDEP_CODE();
		}

		return kmkArrays;
	}

	/**
	 * �ȖڃR�[�h�ɑ΂��鑽�ʉݑΉ��L���𔻒肷��.<br>
	 * �����f�[�^���̉Ȗڂ��m�F����ꍇ�A���񔻒肷��ƒx���Ȃ�ׁA<br>
	 * �����ȖڃR�[�h�ɑ΂��Ă͓��m�F���Ȃ��悤�ɌĂяo�����ōl�����邱��.
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param itemCode �ȖڃR�[�h
	 * @return true: ���ʉ݉Afalse: ���ʉݕs��
	 */
	public static boolean isMultiCurrency(String compCode, String itemCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		KMK_MSTDao dao = (KMK_MSTDao) container.getComponent(KMK_MSTDao.class);
		KMK_MST bean = dao.getKMK_MSTByKaicodeKmkcode(compCode, itemCode);

		if (bean == null) {
			throw new TEnvironmentException("W00084", "C00077", compCode, itemCode);
		}

		return bean.getMCR_FLG() == 1;
	}
}
