package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �v���O�����}�X�^�r�W�l�X���W�b�N
 * 
 * @author �גJ
 */
public interface ProgramLogic {

	/**
	 * ��ЃR�[�h�Ńv���O�������X�g���K��
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return List<PRG_MST> �v���O����list
	 */
	public List<PRG_MST> searchProgramList(String kaiCode);

}
