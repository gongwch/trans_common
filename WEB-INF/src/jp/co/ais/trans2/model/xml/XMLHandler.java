package jp.co.ais.trans2.model.xml;

import org.xml.sax.helpers.*;

/**
 * �n���h��
 */
public abstract class XMLHandler extends DefaultHandler {

	/**
	 * �ϊ���f�[�^��Ԃ�
	 * 
	 * @return �ϊ���f�[�^
	 */
	protected abstract Object get();

}
