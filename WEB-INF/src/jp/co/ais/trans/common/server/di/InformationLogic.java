package jp.co.ais.trans.common.server.di;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���擾���W�b�N
 */
public interface InformationLogic {

	/**
	 * ��ЃR�[�h
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode);

	/**
	 * ���[�U�R�[�h
	 * 
	 * @param userCode ���[�U�R�[�h
	 */
	public void setUserCode(String userCode);

	/**
	 * ����P�ʂ̑g�D�R�[�h���擾
	 * 
	 * @return �g�D�R�[�h���X�g
	 */
	public String[] getOrganizationCodeList();
	
	/**
	 * ��ВP�ʂ̑g�D�R�[�h���擾
	 * 
	 * @return �g�D�R�[�h���X�g
	 */
	public String[] getCmpOrganizationCodeList();

	/**
	 * �J�����x�������擾����
	 * 
	 * @param kmkCode �Ȗڑ̌n�R�[�h
	 * @param orgCode �g�D�R�[�h
	 * @return �J�����x��
	 * @throws TException
	 */
	public Map getIndicationLevelData(String kmkCode, String orgCode) throws TException;

	/**
	 * �Ȗړ��������擾����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @param ukmCode ����ȖڃR�[�h
	 * @return ItemUnionData �Ȗړ������f�[�^
	 */
	public ItemUnionData getItemUnionData(String kaiCode, String kmkCode, String hkmCode, String ukmCode);
	
	/**
	 * �Ȗڏ����擾����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCode �ȖڃR�[�h
	 * @return KMK_MST �Ȗڏ��
	 */
	public KMK_MST getItemDataBean(String kaiCode, String kmkCode);

	/**
	 * �⏕�Ȗڏ����擾����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @return HKM_MST �⏕�Ȗڏ��
	 */
	public HKM_MST getSubItemDataBean(String kaiCode, String kmkCode, String hkmCode);

	/**
	 * ����Ȗڏ����擾����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @param ukmCode ����ȖڃR�[�h
	 * @return UKM_MST ����Ȗڏ��
	 */
	public UKM_MST getBreakDownItemDataBean(String kaiCode, String kmkCode, String hkmCode, String ukmCode);
	
	/**
	 * �Ȗڏ����擾����
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @return �Ȗڏ��
	 * @throws TException
	 */
	public Map getItemData(String kmkCode) throws TException;

	/**
	 * �⏕�Ȗڏ����擾����
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @return �⏕�Ȗڏ��
	 * @throws TException
	 */
	public Map getSubItemData(String kmkCode, String hkmCode) throws TException;

	/**
	 * ����Ȗڏ����擾����
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @param ukmCode ����ȖڃR�[�h
	 * @return ����Ȗڏ��
	 * @throws TException
	 */
	public Map getBreakDownItemData(String kmkCode, String hkmCode, String ukmCode) throws TException;

	/**
	 * ���̂��擾����B<BR>
	 * 
	 * @param strKaiCode ��ЃR�[�h
	 * @param strDpkSsk ��ʓ��͂̑g�D����
	 * @param strDepCode ��ʓ��͂̕��庰��
	 * @param intpanelLevel ���嗪�̂��������ɁA��ʓ��͂����K�w���فB ��ʕ��嗪�̂��������ɁA��ʓ��͂����K�w���x��-1
	 * @param intkjlLvl ��ʕ��嗪�̂��������ɁA�J�����x���i�������̃��x���j ���嗪�̂��������ɁA��ʓ��͂����K�w���x��-1
	 * @param strkjlDepCode ��ʕ��嗪�̂��������ɁA�J�����庰�ށi�������̕���R�[�h�j�B ���嗪�̂��������ɁA��ʓ��͂�����ʕ���R�[�h
	 * @param strType �g�D�����^�C�v ���� or ���
	 * @return ���嗪��
	 */
	public String organizationSearchNameS(String strKaiCode, String strDpkSsk, String strDepCode, Integer intpanelLevel,
			Integer intkjlLvl, String strkjlDepCode, String strType);
	
	/**
	 * ��Ѓ}�X�^�f�[�^�擾<BR>
	 * 
	 * @param kaiCode ���
	 * @return CMP_MST �f�[�^���X�g
	 */
	public CMP_MST getCmpMstDeta(String kaiCode);
	
	/**
	 * ���ߐ���e�[�u���f�[�^������
	 * 
	 * @param kaiCode
	 * @param year
	 * @param month
	 * @return ���ߐ���e�[�u���f�[�^
	 */
	public SIM_CTL findSimCtl(String kaiCode, int year, int month);
	
	/**
	 * GL�R���g���[������
	 * 
	 * @param strKaiCode
	 *            ��ЃR�[�h
	 * @return GL�R���g���[��
	 */
	public GL_CTL_MST findGlCtlMstInfo(String strKaiCode);
	
	/**
	 * �ʉ݃}�X�^����
	 * 
	 * @param strKaiCode
	 *            ��ЃR�[
	 * @param strCurCode
	 *            �ʉ݃R�[�h
	 * @param slipDate
	 *            �`�[���t
	 * @return �ʉ݃}�X�^���X�g
	 */
	public CUR_MST findCurMstInfo(String strKaiCode, String strCurCode,
			Date slipDate);

}
