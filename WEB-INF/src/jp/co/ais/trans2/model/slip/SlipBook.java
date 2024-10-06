package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �`�[Book
 * 
 * @author AIS
 */
public class SlipBook extends LedgerBook {

	/** ��Џ�� */
	protected Company company;

	/** �J�n�y�[�W�ԍ� */
	protected int startPageNo = 0;

	/** ���y�[�W�� */
	protected int totalPageCount = 0;

	/**
	 * �R���X�g���N�^�[
	 */
	public SlipBook() {
		// 1P��7�s�\��
		setMaxRowCount(7);
	}

	/**
	 * @return ���
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company ���
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * �J�n�y�[�W�ԍ��̎擾
	 * 
	 * @return startPageNo �J�n�y�[�W�ԍ�
	 */
	public int getStartPageNo() {
		return startPageNo;
	}

	/**
	 * �J�n�y�[�W�ԍ��̐ݒ�
	 * 
	 * @param startPageNo �J�n�y�[�W�ԍ�
	 */
	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}

	/**
	 * ���y�[�W���̎擾
	 * 
	 * @return totalPageCount ���y�[�W��
	 */
	public int getTotalPageCount() {
		return totalPageCount;
	}

	/**
	 * ���y�[�W���̐ݒ�
	 * 
	 * @param totalPageCount ���y�[�W��
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

}
