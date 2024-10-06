package jp.co.ais.trans2.model;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �o�͒P�ʏ����̊�b�N���X
 * 
 * @param <T> �o�͒P�ʊ�b�G���e�B�e�B
 */
public abstract class BasicOutputCondition<T extends TReferable> extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �g�D�R�[�h */
	protected String organizationCode = null;

	/** �K�w���x�� */
	protected int level = 0;

	/** �z���g�D���܂ނ� */
	protected boolean includeUnder = false;

	/** ��ʑg�D */
	protected T superior = null;

	/** �J�n�g�D */
	protected T from = null;

	/** �I���g�D */
	protected T to = null;

	/** �ʑI��g�D */
	protected List<T> optinalEntities = null;


	/**
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return �J�n�g�D
	 */
	public T getFrom() {
		return from;
	}

	/**
	 * �J�n�g�D
	 * 
	 * @param from
	 */
	public void setFrom(T from) {
		this.from = from;
	}

	/**
	 * @return �g�D�R�[�h
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * �g�D�R�[�h
	 * 
	 * @param organizationCode
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return �I���g�D
	 */
	public T getTo() {
		return to;
	}

	/**
	 * �I���g�D
	 * 
	 * @param to
	 */
	public void setTo(T to) {
		this.to = to;
	}

	/**
	 * @return �z���g�D���܂ނ�
	 */
	public boolean isIncludeUnder() {
		return includeUnder;
	}

	/**
	 * �z���g�D���܂ނ�
	 * 
	 * @param includeUnder
	 */
	public void setIncludeUnder(boolean includeUnder) {
		this.includeUnder = includeUnder;
	}

	/**
	 * @return �K�w���x��
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * �K�w���x��
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return �ʑI��g�D
	 */
	public List<T> getOptionalEntities() {
		return optinalEntities;
	}

	/**
	 * �ʑI��g�D
	 * 
	 * @param optionalEntities
	 */
	public void setOptionalEntities(List<T> optionalEntities) {
		this.optinalEntities = optionalEntities;
	}

	/**
	 * @return ��ʑg�D
	 */
	public T getSuperior() {
		return superior;
	}

	/**
	 * ��ʑg�D
	 * 
	 * @param superior
	 */
	public void setSuperior(T superior) {
		this.superior = superior;
	}

	/**
	 * ��ʑg�D�̃��x����Ԃ�
	 * 
	 * @return ��ʑg�D�̃��x��
	 */
	public int getSuperiorLevel() {
		return getLevel() - 1;
	}

	/**
	 * �ʑI�����ꂽ�g�D�̃R�[�h���X�g��Ԃ�
	 * 
	 * @return �ʑI�����ꂽ�g�D�̃R�[�h���X�g
	 */
	public List<String> getOptionalEntitiesCode() {
		if (optinalEntities == null || optinalEntities.isEmpty()) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (T bean : optinalEntities) {
			list.add(bean.getCode());
		}
		return list;
	}

	/**
	 * �e�[�u��ID�̎擾
	 * 
	 * @return tableID �e�[�u��ID
	 */
	public abstract String getTableID();

}
