package jp.co.ais.trans.common.server.di;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �}�X�^�Q�ƃ��W�b�N
 */
public class TReferenceAuthorityLogicImpl implements TReferenceAuthorityLogic {

	/** ����}�X�^�ꗗ */
	private BMN_MSTDao bmnMstDao_;

	/** ���[�U�[�}�X�^ */
	private USR_MSTDao USR_MSTDao_;

	/** �Ј��}�X�^ */
	private EMP_MSTDao EMP_MSTDao_;

	/**
	 * ���͕��嗪�̎擾<BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param depCode �e�L�X�g�t�B�[���h�ɕ�����
	 * @return BMN_MST ����}�X�^bean
	 */
	public BMN_MST getBMN_MSTByKaicodeDepcode(String kaiCode, String depCode) {
		// ����}�X�^bean
		BMN_MST bmnMstBean = null;
		if (!"".equals(depCode)) {
			bmnMstBean = bmnMstDao_.getBmnMstDataByKaiCodeDepCode(kaiCode, depCode);
		}
		return bmnMstBean;
	}

	/**
	 * ���O�C�����[�U�[�̎Ј��R�[�h���擾<BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode �e�L�X�g�t�B�[���h�ɕ�����
	 * @return USR_MST ���[�U�[�}�X�^bean
	 */
	public USR_MST getUSR_MSTByKaicodeUsercode(String kaiCode, String usrCode) {
		// ���[�U�[�}�X�^bean
		USR_MST usrMst = null;
		if (!"".equals(usrCode)) {
			usrMst = USR_MSTDao_.getUSR_MSTByKaicodeUsrcode(kaiCode, usrCode);
		}
		return usrMst;
	}

	/**
	 * ���O�C�����[�U�[�̎Ј����̂��擾<BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param empCode �e�L�X�g�t�B�[���h�ɕ�����
	 * @param depCode ��������R�[�h
	 * @return EMP_MST ���[�U�[�}�X�^
	 */
	public EMP_MST getEMP_MSTByKaiCodeEmpCode(String kaiCode, String empCode, String depCode) {
		// �Ј��}�X�^bean
		EMP_MST empMst = null;
		if (!"".equals(empCode)) {
			empMst = EMP_MSTDao_.searchEmpNamesByUser(kaiCode, empCode, depCode);
		}
		return empMst;
	}

	/**
	 * setBmnMstDao_
	 * 
	 * @param bmnMstDao_
	 */
	public void setBmnMstDao_(BMN_MSTDao bmnMstDao_) {
		this.bmnMstDao_ = bmnMstDao_;
	}

	/**
	 * setUSR_MSTDao_
	 * 
	 * @param dao_
	 */
	public void setUSR_MSTDao_(USR_MSTDao dao_) {
		USR_MSTDao_ = dao_;
	}

	/**
	 * setEMP_MSTDao_
	 * 
	 * @param dao_
	 */
	public void setEMP_MSTDao_(EMP_MSTDao dao_) {
		EMP_MSTDao_ = dao_;
	}
}
