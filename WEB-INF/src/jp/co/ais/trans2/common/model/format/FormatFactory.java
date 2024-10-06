package jp.co.ais.trans2.common.model.format;

import jp.co.ais.trans.common.config.*;

/**
 * �Ɩ��t�H�[�}�b�^�̃t�@�N�g��
 * 
 * @author AIS
 */
public class FormatFactory {

	/**
	 * �O�݂̃t�H�[�}�b�g��Ԃ�
	 * 
	 * @return �O�݂̃t�H�[�}�b�g
	 */
	public static ForeignAmountFormat getForeignAmountFormat() {

		try {
			if (ServerConfig.isFlagOn("trans.foreign.amount.no.brackets")) {
				// ���ʂȂ��ꍇ�A�ʏ�O�݃t�H�[�}�b�^��Ԃ�
				return new NormalForeignAmountFormat();
			}
		} catch (Throwable e) {
			// �����Ȃ�
		}

		return new ForeignAmountFormat();
	}

	/**
	 * ���[�g�̃t�H�[�}�b�g��Ԃ�
	 * 
	 * @return ���[�g�̃t�H�[�}�b�g
	 */
	public static RateFormat getRateFormat() {
		return new RateFormat();
	}

	/**
	 * �ʉ݂̃t�H�[�}�b�g��Ԃ�
	 * 
	 * @return �ʉ݂̃t�H�[�}�b�g
	 */
	public static CurrencyFormat getCurrencyFormat() {
		return new CurrencyFormat();
	}

	/**
	 * ���Z�i�K�̃t�H�[�}�b�g��Ԃ�
	 * 
	 * @return ���Z�i�K�̃t�H�[�}�b�g
	 */
	public static SettlementLevelFormat getSettlementLevelFormat() {
		return new SettlementLevelFormat();
	}
}
