package jp.co.ais.trans.common.client.util;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * �N���C�A���g�T�C�h(���)��藘�p�����Ɩ����[�e�B���e�B<br>
 * ���O�C�����[�U�̏������ɓ��삷��
 */
public class ClientUtil {

	/**
	 * ����Ŋz��Ԃ��܂��B
	 * 
	 * @param amount �{�̉��z
	 * @param zeiKbn �ŋ敪(0:���� 1:�O��)
	 * @param zei ����Ń}�X�^Entity
	 * @param cmpMst ��ЃR���g���[���}�X�^Entity
	 * @param curMst �ʉ�
	 * @return ����Ŋz
	 */
	@Deprecated
	public static BigDecimal calculateTaxInside(BigDecimal amount, int zeiKbn, SZEI_MST zei, CMP_MST cmpMst,
		CUR_MST curMst) {

		BigDecimal returnTax = new BigDecimal(0);

		if (zeiKbn == 0) {
			returnTax = amount.multiply(new BigDecimal(zei.getZEI_RATE() / (100 + zei.getZEI_RATE())));
		} else {
			returnTax = amount.multiply(new BigDecimal(zei.getZEI_RATE() / 100));
		}

		if (zei.getUS_KBN() == 1) { // ����ېŁi�������Łj
			// �������Œ[������
			switch (cmpMst.getKU_KBN()) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, curMst.getDEC_KETA());
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, curMst.getDEC_KETA());
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, curMst.getDEC_KETA());
					break;

			}

		} else if (zei.getUS_KBN() == 2) {
			// ��������Œ[������
			switch (cmpMst.getKB_KBN()) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, curMst.getDEC_KETA());
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, curMst.getDEC_KETA());
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, curMst.getDEC_KETA());
					break;

			}
		}

		return returnTax;
	}

	/**
	 * �N�x�擾 ���񌎂ɉ������Ώۓ��t�̔N�x���擾.<br>
	 * ���O�C�����[�U���ɕR�Â����񌎂����ɎZ�o
	 * 
	 * @param day �Ώ۔N����
	 * @return �Ώۓ��t�̔N�x
	 */
	@Deprecated
	public static int getFiscalYear(Date day) {

		// �Ώ۔N�����̔N
		int year = DateUtil.getYear(day);

		// �Ώ۔N�����̌�
		int month = DateUtil.getMonth(day);

		// ���񌎎擾
		int initialMonth =  TLoginInfo.getCompany().getFiscalPeriod().getMonthBeginningOfPeriod();

		if (initialMonth > month) {
			year = year - 1;
		}

		return year;
	}

	/**
	 * ���x�擾
	 * 
	 * @param targetDate �Ώۓ��t
	 * @return ���x
	 */
	@Deprecated
	public static int getFiscalMonth(Date targetDate) {
		// ���x�擾
		return Util.getFiscalMonth(getInitialMonth(), DateUtil.getMonth(targetDate));
	}

	/**
	 * ���񌎎擾<br>
	 * ���O�C�����[�U�̉�Џ��ɕR�Â�����
	 * 
	 * @return ����
	 */
	@Deprecated
	public static int getInitialMonth() {
		return TLoginInfo.getCompany().getFiscalPeriod().getMonthBeginningOfPeriod();
	}

	/**
	 * ��ʉݏ����_�����擾<br>
	 * ���O�C�����[�U�̉�Џ��ɕR�Â���ʉݏ����_����
	 * 
	 * @return �����_����
	 */
	@Deprecated
	public static int getCurrencyDigit() {
		return TClientLoginInfo.getInstance().getCompanyInfo().getBaseCurrencyDigit();
	}

	/**
	 * ���O�C�����[�U����R�[�h�ɕR�Â��P��擾
	 * 
	 * @param wordID �P��ID
	 * @return �P��
	 */
	@Deprecated
	public static String getWord(String wordID) {
		return MessageUtil.getWord(TClientLoginInfo.getInstance().getUserLanguage(), wordID);
	}

	/**
	 * ���O�C�����[�U����R�[�h�ɕR�Â��P�ꃊ�X�g�擾
	 * 
	 * @param wordIDs �P��ID���X�g
	 * @return �P�ꃊ�X�g
	 */
	@Deprecated
	public static String[] getWordList(String[] wordIDs) {
		return MessageUtil.getWordList(TClientLoginInfo.getInstance().getUserLanguage(), wordIDs);
	}

	/**
	 * ���O�C�����[�U����R�[�h�ɕR�Â����b�Z�[�W�擾
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @return ���b�Z�[�W
	 */
	@Deprecated
	public static String getMessage(String messageID) {
		return MessageUtil.convertMessage(TClientLoginInfo.getInstance().getUserLanguage(), messageID);
	}
}
