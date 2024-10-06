package jp.co.ais.trans2.common.ui;

import java.io.*;
import java.util.*;

/**
 * OP LOGIN���
 */
public class OPLoginData implements Serializable {

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** �f�[�^�敪 */
	protected int dataTypeValue = 0;

	/** �ۑ��f�[�^�̃��X�g */
	protected List list = null;

	/**
	 * �ŏI�X�V�����̎擾
	 * 
	 * @return lastUpdateDate �ŏI�X�V����
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * �ŏI�X�V�����̐ݒ�
	 * 
	 * @param lastUpdateDate �ŏI�X�V����
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * �f�[�^�敪�̎擾
	 * 
	 * @return dataTypeValue �f�[�^�敪
	 */
	public int getDataTypeValue() {
		return dataTypeValue;
	}

	/**
	 * �f�[�^�敪�̐ݒ�
	 * 
	 * @param dataTypeValue �f�[�^�敪
	 */
	public void setDataTypeValue(int dataTypeValue) {
		this.dataTypeValue = dataTypeValue;
	}

	/**
	 * �ۑ��f�[�^�̃��X�g�̎擾
	 * 
	 * @return list �ۑ��f�[�^�̃��X�g
	 */
	public List getList() {
		return list;
	}

	/**
	 * �ۑ��f�[�^�̃��X�g�̐ݒ�
	 * 
	 * @param list �ۑ��f�[�^�̃��X�g
	 */
	public void setList(List list) {
		this.list = list;
	}

}
