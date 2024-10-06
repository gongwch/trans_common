package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ���O�C���`�F�b�NDao
 */
public interface PCI_CHK_CTLDao {

	/** Bean��` */
	public static final Class BEAN = PCI_CHK_CTL.class;

	/**
	 * �S�f�[�^�擾
	 * 
	 * @return �f�[�^���X�g
	 */
	public List getAllPCI_CHK_CTL();

	/** �w�肳�ꂽ�f�[�^�̎擾 */
	public static final String getPCI_CHK_CTLByKaicodeUsrid_ARGS = "KAI_CODE, USR_ID";

	/**
	 * ��ЃR�[�h�A���[�UID����Ƀf�[�^�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrID ���[�UID
	 * @return �f�[�^
	 */
	public PCI_CHK_CTL getPCI_CHK_CTLByKaicodeUsrid(String kaiCode, String usrID);

	/** �N�G������ */
	public static final String getPCIListByCompanyCode_ARGS = "companyCode";

	/**
	 * ��ЃR�[�h�Ń��[�U�r�����X�g���擾
	 * 
	 * @param companyCode
	 * @return ���[�U�r�����X�g
	 */
	public List<PCI_CHK_CTL> getPCIListByCompanyCode(String companyCode);

	/** ���O�C�����[�U�[���擾SQL */
	public static final String getCount_SQL = "SELECT COUNT(*) FROM PCI_CHK_CTL WHERE KAI_CODE = ?";

	/**
	 * ���O�C�����[�U�[���̎擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return ���O�C�����[�U�[��
	 */
	public int getCount(String kaiCode);

	/**
	 * �V�K�ǉ�
	 * 
	 * @param dto
	 */
	public void insert(PCI_CHK_CTL dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(PCI_CHK_CTL dto);

	/** ���O�C���`�F�b�N�폜SQL */
	public String delete_SQL = "DELETE FROM PCI_CHK_CTL WHERE KAI_CODE = ? AND USR_ID = ?";

	/**
	 * ���O�C���`�F�b�N�폜
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode ���[�U�R�[�h
	 */
	public void delete(String kaiCode, String usrCode);

}
