package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �����X�V��������
 * 
 * @author TRANS(D) YF.CONG
 */
public class MonthDataSearchCondition extends TransferBase {

	/** ��ЃR�[�h���X�g */
	protected List<String> companyCodeList;

	/**
	 * ��ЃR�[�h���X�g�̎擾
	 * 
	 * @return companyCodeList ��ЃR�[�h���X�g
	 */
	public List<String> getCompanyCodeList() {
		return companyCodeList;
	}

	/**
	 * ��ЃR�[�h���X�g�̐ݒ�
	 * 
	 * @param companyCodeList ��ЃR�[�h���X�g
	 */
	public void setCompanyCodeList(List<String> companyCodeList) {
		this.companyCodeList = companyCodeList;
	}

}
