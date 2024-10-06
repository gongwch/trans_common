package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * �E�v�}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface MemoLogic extends CommonLogic {

	/**
	 * �E�v�R�[�h�ŏڍׂ��擾
	 * 
	 * @param keys
	 * @return �E�v���
	 */
	public abstract List getREFItems(Map keys);

	/**
	 * �p�����[�^�����œE�v��񃊃X�g�K��
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param tekCode �E�v�R�[�h
	 * @param sName ����
	 * @param kName ��������
	 * @param memoType �s�E�v�^�C�v
	 * @param slipType �`�[�E�v�^�C�v
	 * @param termBasisDate �L������
	 * @return �E�v���X�g
	 */
	public abstract List<Object> refDailog(String kaiCode, String tekCode, String sName, String kName, int memoType,
		String slipType, Timestamp termBasisDate);

}
