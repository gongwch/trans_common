package jp.co.ais.trans.common.client;

/**
 * �񓯊��ʐM���̏����ʒm�p���X�i�[
 */
public interface TAsyncListener {

	/**
	 * �ʐM��Ăяo��
	 * 
	 * @param isSuccess �ʐM�������� true:����
	 */
	void after(boolean isSuccess);
}
