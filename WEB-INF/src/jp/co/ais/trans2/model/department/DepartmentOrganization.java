package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * ����g�D�K�wEntity
 * 
 * @author AIS
 */
public class DepartmentOrganization extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** �g�D�R�[�h */
	protected String code;

	/** �g�D���� */
	protected String name = null;

	/** ����R�[�h */
	protected String depCode;

	/** ���喼�� */
	protected String depName;

	/** ���嗪�� */
	protected String depNames;

	/** ���x�� */
	protected int level;

	/** ���x���O */
	protected String level0;

	/** ���x���O���� */
	protected String level0Name;

	/** ���x���P */
	protected String level1;

	/** ���x���P���� */
	protected String level1Name;

	/** ���x���Q */
	protected String level2;

	/** ���x���Q���� */
	protected String level2Name;

	/** ���x���R */
	protected String level3;

	/** ���x���R���� */
	protected String level3Name;

	/** ���x���S */
	protected String level4;

	/** ���x���S���� */
	protected String level4Name;

	/** ���x���T */
	protected String level5;

	/** ���x���T���� */
	protected String level5Name;

	/** ���x���U */
	protected String level6;

	/** ���x���U���� */
	protected String level6Name;

	/** ���x���V */
	protected String level7;

	/** ���x���V���� */
	protected String level7Name;

	/** ���x���W */
	protected String level8;

	/** ���x���W���� */
	protected String level8Name;

	/** ���x���X */
	protected String level9;

	/** ���x���X���� */
	protected String level9Name;

	/** �o�^���t */
	protected Date inpDate;

	/** �g�D���x��0���� */
	protected String DPK_LVL_0_NAME = null;

	/** �g�D���x��1���� */
	protected String DPK_LVL_1_NAME = null;

	/** �g�D���x��2���� */
	protected String DPK_LVL_2_NAME = null;

	/** �g�D���x��3���� */
	protected String DPK_LVL_3_NAME = null;

	/** �g�D���x��4���� */
	protected String DPK_LVL_4_NAME = null;

	/** �g�D���x��5���� */
	protected String DPK_LVL_5_NAME = null;

	/** �g�D���x��6���� */
	protected String DPK_LVL_6_NAME = null;

	/** �g�D���x��7���� */
	protected String DPK_LVL_7_NAME = null;

	/** �g�D���x��8���� */
	protected String DPK_LVL_8_NAME = null;

	/** �g�D���x��9���� */
	protected String DPK_LVL_9_NAME = null;

	/**
	 * ��ЃR�[�h��߂��܂��B
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肵�܂��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �g�D�R�[�h��߂��܂��B
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * �g�D�R�[�h��ݒ肵�܂��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * �g�D���̂̎擾
	 * 
	 * @return name �g�D����
	 */
	public String getName() {
		return name;
	}

	/**
	 * �g�D���̂̐ݒ�
	 * 
	 * @param name �g�D����
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ����R�[�h��߂��܂��B
	 * 
	 * @return depCode
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * ����R�[�h��ݒ肵�܂��B
	 * 
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * ���喼�̂�߂��܂��B
	 * 
	 * @return depName
	 */
	public String getDepName() {
		return depName;
	}

	/**
	 * ���喼�̂�ݒ肵�܂��B
	 * 
	 * @param depName
	 */
	public void setDepName(String depName) {
		this.depName = depName;
	}

	/**
	 * ���嗪�̂�߂��܂��B
	 * 
	 * @return depNames
	 */
	public String getDepNames() {
		return depNames;
	}

	/**
	 * ���嗪�̂�ݒ肵�܂��B
	 * 
	 * @param depNames
	 */
	public void setDepNames(String depNames) {
		this.depNames = depNames;
	}

	/**
	 * ���x����߂��܂��B
	 * 
	 * @return level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * ���x����ݒ肵�܂��B
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * ���x���O��߂��܂��B
	 * 
	 * @return level0
	 */
	public String getLevel0() {
		return level0;
	}

	/**
	 * ���x���O��ݒ肵�܂��B
	 * 
	 * @param level0
	 */
	public void setLevel0(String level0) {
		this.level0 = level0;
	}

	/**
	 * ���x���O���̂�߂��܂��B
	 * 
	 * @return level0Name
	 */
	public String getLevel0Name() {
		return level0Name;
	}

	/**
	 * ���x���O���̂�ݒ肵�܂��B
	 * 
	 * @param level0Name
	 */
	public void setLevel0Name(String level0Name) {
		this.level0Name = level0Name;
	}

	/**
	 * ���x���P��߂��܂��B
	 * 
	 * @return level1
	 */
	public String getLevel1() {
		return level1;
	}

	/**
	 * ���x���P��ݒ肵�܂��B
	 * 
	 * @param level1
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	/**
	 * ���x���P���̂�߂��܂��B
	 * 
	 * @return level1Name
	 */
	public String getLevel1Name() {
		return level1Name;
	}

	/**
	 * ���x���P���̂�ݒ肵�܂��B
	 * 
	 * @param level1Name
	 */
	public void setLevel1Name(String level1Name) {
		this.level1Name = level1Name;
	}

	/**
	 * ���x���Q��߂��܂��B
	 * 
	 * @return level2
	 */
	public String getLevel2() {
		return level2;
	}

	/**
	 * ���x���Q��ݒ肵�܂��B
	 * 
	 * @param level2
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	/**
	 * ���x���Q���̂�߂��܂��B
	 * 
	 * @return level2Name
	 */
	public String getLevel2Name() {
		return level2Name;
	}

	/**
	 * ���x���Q���̂�ݒ肵�܂��B
	 * 
	 * @param level2Name
	 */
	public void setLevel2Name(String level2Name) {
		this.level2Name = level2Name;
	}

	/**
	 * ���x���R��߂��܂��B
	 * 
	 * @return level3
	 */
	public String getLevel3() {
		return level3;
	}

	/**
	 * ���x���R��ݒ肵�܂��B
	 * 
	 * @param level3
	 */
	public void setLevel3(String level3) {
		this.level3 = level3;
	}

	/**
	 * ���x���R���̂�߂��܂��B
	 * 
	 * @return level3Name
	 */
	public String getLevel3Name() {
		return level3Name;
	}

	/**
	 * ���x���R���̂�ݒ肵�܂��B
	 * 
	 * @param level3Name
	 */
	public void setLevel3Name(String level3Name) {
		this.level3Name = level3Name;
	}

	/**
	 * ���x���S��߂��܂��B
	 * 
	 * @return level4
	 */
	public String getLevel4() {
		return level4;
	}

	/**
	 * ���x���S��ݒ肵�܂��B
	 * 
	 * @param level4
	 */
	public void setLevel4(String level4) {
		this.level4 = level4;
	}

	/**
	 * ���x���S���̂�߂��܂��B
	 * 
	 * @return level4Name
	 */
	public String getLevel4Name() {
		return level4Name;
	}

	/**
	 * ���x���S���̂�ݒ肵�܂��B
	 * 
	 * @param level4Name
	 */
	public void setLevel4Name(String level4Name) {
		this.level4Name = level4Name;
	}

	/**
	 * ���x���T��߂��܂��B
	 * 
	 * @return level5
	 */
	public String getLevel5() {
		return level5;
	}

	/**
	 * ���x���T��ݒ肵�܂��B
	 * 
	 * @param level5
	 */
	public void setLevel5(String level5) {
		this.level5 = level5;
	}

	/**
	 * ���x���T���̂�߂��܂��B
	 * 
	 * @return level5Name
	 */
	public String getLevel5Name() {
		return level5Name;
	}

	/**
	 * ���x���T���̂�ݒ肵�܂��B
	 * 
	 * @param level5Name
	 */
	public void setLevel5Name(String level5Name) {
		this.level5Name = level5Name;
	}

	/**
	 * ���x���U��߂��܂��B
	 * 
	 * @return level6
	 */
	public String getLevel6() {
		return level6;
	}

	/**
	 * ���x���U��ݒ肵�܂��B
	 * 
	 * @param level6
	 */
	public void setLevel6(String level6) {
		this.level6 = level6;
	}

	/**
	 * ���x���U���̂�߂��܂��B
	 * 
	 * @return level6Name
	 */
	public String getLevel6Name() {
		return level6Name;
	}

	/**
	 * ���x���U���̂�ݒ肵�܂��B
	 * 
	 * @param level6Name
	 */
	public void setLevel6Name(String level6Name) {
		this.level6Name = level6Name;
	}

	/**
	 * ���x���V��߂��܂��B
	 * 
	 * @return level7
	 */
	public String getLevel7() {
		return level7;
	}

	/**
	 * ���x���V��ݒ肵�܂��B
	 * 
	 * @param level7
	 */
	public void setLevel7(String level7) {
		this.level7 = level7;
	}

	/**
	 * ���x���V���̂�߂��܂��B
	 * 
	 * @return level7Name
	 */
	public String getLevel7Name() {
		return level7Name;
	}

	/**
	 * ���x���V���̂�ݒ肵�܂��B
	 * 
	 * @param level7Name
	 */
	public void setLevel7Name(String level7Name) {
		this.level7Name = level7Name;
	}

	/**
	 * ���x���W��߂��܂��B
	 * 
	 * @return level8
	 */
	public String getLevel8() {
		return level8;
	}

	/**
	 * ���x���W��ݒ肵�܂��B
	 * 
	 * @param level8
	 */
	public void setLevel8(String level8) {
		this.level8 = level8;
	}

	/**
	 * ���x���W���̂�߂��܂��B
	 * 
	 * @return level8Name
	 */
	public String getLevel8Name() {
		return level8Name;
	}

	/**
	 * ���x���W���̂�ݒ肵�܂��B
	 * 
	 * @param level8Name
	 */
	public void setLevel8Name(String level8Name) {
		this.level8Name = level8Name;
	}

	/**
	 * ���x���X��߂��܂��B
	 * 
	 * @return level9
	 */
	public String getLevel9() {
		return level9;
	}

	/**
	 * ���x���X��ݒ肵�܂��B
	 * 
	 * @param level9
	 */
	public void setLevel9(String level9) {
		this.level9 = level9;
	}

	/**
	 * ���x���X���̂�߂��܂��B
	 * 
	 * @return level9Name
	 */
	public String getLevel9Name() {
		return level9Name;
	}

	/**
	 * ���x���X���̂�ݒ肵�܂��B
	 * 
	 * @param level9Name
	 */
	public void setLevel9Name(String level9Name) {
		this.level9Name = level9Name;
	}

	/**
	 * �o�^���t��߂��܂��B
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * * �o�^���t��ݒ肵�܂��B
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * ���x��0���̂̎擾
	 * 
	 * @return DPK_LVL_0_NAME ���x��0����
	 */
	public String getDPK_LVL_0_NAME() {
		return DPK_LVL_0_NAME;
	}

	/**
	 * ���x��0���̂̐ݒ�
	 * 
	 * @param DPK_LVL_0_NAME ���x��0����
	 */
	public void setDPK_LVL_0_NAME(String DPK_LVL_0_NAME) {
		this.DPK_LVL_0_NAME = DPK_LVL_0_NAME;
	}

	/**
	 * �g�D���x��1���̂̎擾
	 * 
	 * @return DPK_LVL_1_NAME �g�D���x��1����
	 */
	public String getDPK_LVL_1_NAME() {
		return DPK_LVL_1_NAME;
	}

	/**
	 * �g�D���x��1���̂̐ݒ�
	 * 
	 * @param DPK_LVL_1_NAME �g�D���x��1����
	 */
	public void setDPK_LVL_1_NAME(String DPK_LVL_1_NAME) {
		this.DPK_LVL_1_NAME = DPK_LVL_1_NAME;
	}

	/**
	 * �g�D���x��2���̂̎擾
	 * 
	 * @return DPK_LVL_2_NAME �g�D���x��2����
	 */
	public String getDPK_LVL_2_NAME() {
		return DPK_LVL_2_NAME;
	}

	/**
	 * �g�D���x��2���̂̐ݒ�
	 * 
	 * @param DPK_LVL_2_NAME �g�D���x��2����
	 */
	public void setDPK_LVL_2_NAME(String DPK_LVL_2_NAME) {
		this.DPK_LVL_2_NAME = DPK_LVL_2_NAME;
	}

	/**
	 * �g�D���x��3���̂̎擾
	 * 
	 * @return DPK_LVL_3_NAME �g�D���x��3����
	 */
	public String getDPK_LVL_3_NAME() {
		return DPK_LVL_3_NAME;
	}

	/**
	 * �g�D���x��3���̂̐ݒ�
	 * 
	 * @param DPK_LVL_3_NAME �g�D���x��3����
	 */
	public void setDPK_LVL_3_NAME(String DPK_LVL_3_NAME) {
		this.DPK_LVL_3_NAME = DPK_LVL_3_NAME;
	}

	/**
	 * �g�D���x��4���̂̎擾
	 * 
	 * @return DPK_LVL_4_NAME �g�D���x��4����
	 */
	public String getDPK_LVL_4_NAME() {
		return DPK_LVL_4_NAME;
	}

	/**
	 * �g�D���x��4���̂̐ݒ�
	 * 
	 * @param DPK_LVL_4_NAME �g�D���x��4����
	 */
	public void setDPK_LVL_4_NAME(String DPK_LVL_4_NAME) {
		this.DPK_LVL_4_NAME = DPK_LVL_4_NAME;
	}

	/**
	 * �g�D���x��5���̂̎擾
	 * 
	 * @return DPK_LVL_5_NAME �g�D���x��5����
	 */
	public String getDPK_LVL_5_NAME() {
		return DPK_LVL_5_NAME;
	}

	/**
	 * �g�D���x��5���̂̐ݒ�
	 * 
	 * @param DPK_LVL_5_NAME �g�D���x��5����
	 */
	public void setDPK_LVL_5_NAME(String DPK_LVL_5_NAME) {
		this.DPK_LVL_5_NAME = DPK_LVL_5_NAME;
	}

	/**
	 * �g�D���x��6���̂̎擾
	 * 
	 * @return DPK_LVL_6_NAME �g�D���x��6����
	 */
	public String getDPK_LVL_6_NAME() {
		return DPK_LVL_6_NAME;
	}

	/**
	 * �g�D���x��6���̂̐ݒ�
	 * 
	 * @param DPK_LVL_6_NAME �g�D���x��6����
	 */
	public void setDPK_LVL_6_NAME(String DPK_LVL_6_NAME) {
		this.DPK_LVL_6_NAME = DPK_LVL_6_NAME;
	}

	/**
	 * �g�D���x��7���̂̎擾
	 * 
	 * @return DPK_LVL_7_NAME �g�D���x��7����
	 */
	public String getDPK_LVL_7_NAME() {
		return DPK_LVL_7_NAME;
	}

	/**
	 * �g�D���x��7���̂̐ݒ�
	 * 
	 * @param DPK_LVL_7_NAME �g�D���x��7����
	 */
	public void setDPK_LVL_7_NAME(String DPK_LVL_7_NAME) {
		this.DPK_LVL_7_NAME = DPK_LVL_7_NAME;
	}

	/**
	 * �g�D���x��8���̂̎擾
	 * 
	 * @return DPK_LVL_8_NAME �g�D���x��8����
	 */
	public String getDPK_LVL_8_NAME() {
		return DPK_LVL_8_NAME;
	}

	/**
	 * �g�D���x��8���̂̐ݒ�
	 * 
	 * @param DPK_LVL_8_NAME �g�D���x��8����
	 */
	public void setDPK_LVL_8_NAME(String DPK_LVL_8_NAME) {
		this.DPK_LVL_8_NAME = DPK_LVL_8_NAME;
	}

	/**
	 * �g�D���x��9���̂̎擾
	 * 
	 * @return DPK_LVL_9_NAME �g�D���x��9����
	 */
	public String getDPK_LVL_9_NAME() {
		return DPK_LVL_9_NAME;
	}

	/**
	 * �g�D���x��9���̂̐ݒ�
	 * 
	 * @param DPK_LVL_9_NAME �g�D���x��9����
	 */
	public void setDPK_LVL_9_NAME(String DPK_LVL_9_NAME) {
		this.DPK_LVL_9_NAME = DPK_LVL_9_NAME;
	}

}
