package jp.co.ais.trans2.common.model.format;

/**
 * �ʏ�O�݂̃t�H�[�}�b�^(���ʂȂ�)
 * 
 * @author AIS
 */
public class NormalForeignAmountFormat extends ForeignAmountFormat {

	/**
	 * @return �O�ݐ擪
	 */
	@Override
	protected String getPrefix() {
		return "";
	}

	/**
	 * @return �O�ݖ�
	 */
	@Override
	protected String getSuffix() {
		return "";
	}
}
