package jp.co.ais.trans.logic.system;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �����[�X�t�@�C���ꗗ�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface ReleasedFileLogic {

	/**
	 * �����[�X�t�@�C�����X�g���擾
	 * 
	 * @param rootPath �t�@�C��PATH
	 * @return List �����[�X�t�@�C�����X�g
	 */
	List<ReleasedFileObject> getReleasedFileList(String rootPath);

}
