package jp.co.ais.trans2.model.language;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ����R�[�h�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface LanguageManager {

	/**
	 * �w������ɊY�����錾���Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����镔��g�D���
	 * @throws TException
	 */
	public List<Language> getLanguage(LanguageSearchCondition condition) throws TException;

}
