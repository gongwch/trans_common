package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ��s�}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface BankLogic extends CommonLogic {

	/**
	 * ��s�������擾 PK�����i�x�X�R�[�h���O�j
	 * 
	 * @param param
	 * @return ��s���
	 */
	BNK_MST getBankName(BnkMstParameter param);

	/**
	 * ��s������񃊃X�g�擾 �i�x�X�R�[�h���O�j
	 * 
	 * @param param
	 * @return ��s��񃊃X�g
	 */
	List<BNK_MST> getBankList(BnkMstParameter param);

	/**
	 * ��s�x�X���擾 PK����
	 * 
	 * @param param
	 * @return ��s�x�X��񃊃X�g
	 */
	BNK_MST getStnName(BnkMstParameter param);

	/**
	 * ��s�x�X��񃊃X�g�擾
	 * 
	 * @param param
	 * @return ��s�x�X��񃊃X�g
	 */
	List<BNK_MST> getStnList(BnkMstParameter param);

}
