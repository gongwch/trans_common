package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.program.SystemDivision;

/**
 * �`�[���
 * 
 * @author AIS
 */
public class SlipType extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ���� */
	protected String names = null;

	/** �������� */
	protected String namek = null;

	/** �f�[�^�敪 */
	protected String dataType = null;

	/** �V�X�e���敪 */
	protected SystemDivision systemDivision = null;

	/** �V�X�e���敪�R�[�h */
	protected String systemDiv = "";

	/** �N�[���̍X�V�敪 */
	protected SlipState slipState = null;

	/** �d��C���^�[�t�F�[�X�敪 */
	protected SlipState journalIfDivision = SlipState.ENTRY;

	/** ����Ōv�Z�敪(���ł�) */
	protected boolean innerConsumptionTaxCalculation = true;

	/** ����P�� */
	protected AcceptUnit acceptUnit = AcceptUnit.SLIPTYPE;

	/** ���V�X�e���敪 */
	protected boolean anotherSystemDivision = false;

	/** ���V�X�e���f�[�^������ɍ̔Ԃ��邩 */
	protected boolean takeNewSlipNo = false;

	/** �U�ߎ���̓`�[��ʃR�[�h */
	protected String reversingCode;

	/** �U�ߎ���̓`�[��� */
	protected SlipType reversingSlipType = null;

	/** �C���{�C�X���x�`�F�b�N */
	protected boolean INV_SYS_FLG = false;

	/**
	 * @return code��߂��܂��B
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code code��ݒ肵�܂��B
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return companyCode��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCode��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return name��߂��܂��B
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name��ݒ肵�܂��B
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return namek��߂��܂��B
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namek��ݒ肵�܂��B
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return names��߂��܂��B
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names names��ݒ肵�܂��B
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * �f�[�^�敪
	 * 
	 * @return �f�[�^�敪
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * �f�[�^�敪
	 * 
	 * @param dataType �f�[�^�敪
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * �V�X�e���敪�R�[�h
	 * 
	 * @return �V�X�e���敪�R�[�h
	 */
	public String getSystemDiv() {
		return systemDiv;
	}

	/**
	 * �V�X�e���敪�R�[�h
	 * 
	 * @param systemDiv �V�X�e���敪�R�[�h
	 */
	public void setSystemDiv(String systemDiv) {
		this.systemDiv = systemDiv;
	}

	/**
	 * ����Ōv�Z�敪(���ł�)
	 * 
	 * @return true:����
	 */
	public boolean isInnerConsumptionTaxCalculation() {
		return innerConsumptionTaxCalculation;
	}

	/**
	 * ����Ōv�Z�敪(���ł�)
	 * 
	 * @param innerConsumptionTaxCalculation true:����
	 */
	public void setInnerConsumptionTaxCalculation(boolean innerConsumptionTaxCalculation) {
		this.innerConsumptionTaxCalculation = innerConsumptionTaxCalculation;
	}

	/**
	 * �X�V�敪getter
	 * 
	 * @return �X�V�敪
	 */
	public SlipState getSlipState() {
		return slipState;
	}

	/**
	 * �X�V�敪setter
	 * 
	 * @param slipState
	 */
	public void setSlipState(SlipState slipState) {
		this.slipState = slipState;
	}

	/**
	 * @return takeNewSlipNo
	 */
	public boolean isTakeNewSlipNo() {
		return takeNewSlipNo;
	}

	/**
	 * @param takeNewSlipNo
	 */
	public void setTakeNewSlipNo(boolean takeNewSlipNo) {
		this.takeNewSlipNo = takeNewSlipNo;
	}

	/**
	 * @return �`�[�ԍ����̔Ԃ������E�����Ȃ�
	 */
	public String getSlipIndexDivisionName() {

		if (takeNewSlipNo == false) {
			return "C02099";// ���Ȃ�
		} else {
			return "C02100";// ����
		}

	}

	/**
	 * @return anotherSystemType
	 */
	public boolean isAnotherSystemDivision() {
		return anotherSystemDivision;
	}

	/**
	 * @param anotherSystemType
	 */
	public void setAnotherSystemDivision(boolean anotherSystemType) {
		this.anotherSystemDivision = anotherSystemType;
	}

	/**
	 * @return String
	 */
	public String getAnotherSystemDivisionName() {

		if (anotherSystemDivision == false) {
			return "C02097";// ���V�X����ΏۊO
		} else {
			return "C02098";// ���V�X����Ώ�
		}
	}

	/**
	 * @return acceptUnit
	 */
	public AcceptUnit isAcceptUnit() {
		return acceptUnit;
	}

	/**
	 * @param acceptUnit
	 */
	public void setAcceptUnit(AcceptUnit acceptUnit) {
		this.acceptUnit = acceptUnit;
	}

	/**
	 * @return journalIfDivision
	 */
	public SlipState getJounalIfDivision() {
		return journalIfDivision;
	}

	/**
	 * @param journalIfDivision
	 */
	public void setJounalIfDivision(SlipState journalIfDivision) {
		this.journalIfDivision = journalIfDivision;
	}

	/**
	 * �V�X�e���敪���擾
	 * 
	 * @return �V�X�e���敪
	 */
	public SystemDivision getSystemDivision() {
		return systemDivision;
	}

	/**
	 * �V�X�e���敪��ݒ�
	 * 
	 * @param systemDivision
	 */
	public void setSystemDivision(SystemDivision systemDivision) {
		this.systemDivision = systemDivision;
	}

	/**
	 * �U�ߎ���̓`�[��ʂ��擾
	 * 
	 * @return �U�ߎ���̓`�[���
	 */
	public SlipType getReversingSlipType() {
		return reversingSlipType;
	}

	/**
	 * �U�ߎ���̓`�[��ʂ�ݒ�
	 * 
	 * @param reversingSlipType
	 */
	public void setReversingSlipType(SlipType reversingSlipType) {
		this.reversingSlipType = reversingSlipType;

		if (reversingSlipType != null) {
			this.reversingCode = reversingSlipType.getCode();
		} else {
			this.reversingCode = null;
		}
	}

	/**
	 * �U�ߎ���̓`�[���
	 * 
	 * @return �U�ߎ���̓`�[���
	 */
	public String getReversingCode() {
		return this.reversingCode;
	}

	/**
	 * �U�ߓ`�[��ʃR�[�h
	 * 
	 * @param reversingCode �U�ߓ`�[��ʃR�[�h
	 */
	public void setReversingCode(String reversingCode) {
		this.reversingCode = reversingCode;
	}
	
	/**
	 * �C���{�C�X���x�`�F�b�N�̎擾
	 * 
	 * @return INV_SYS_FLG �C���{�C�X���x�`�F�b�N
	 */ 
	public boolean isINV_SYS_FLG() { 
	     return INV_SYS_FLG;
	}
	/**
	 * �C���{�C�X���x�`�F�b�N�̐ݒ�
	 * 
	 * @param INV_SYS_FLG �C���{�C�X���x�`�F�b�N
	 */
	public void setINV_SYS_FLG(boolean INV_SYS_FLG) {
	     this.INV_SYS_FLG = INV_SYS_FLG;
	}


}
