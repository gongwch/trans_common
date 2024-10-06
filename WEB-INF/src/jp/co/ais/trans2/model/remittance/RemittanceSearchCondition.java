package jp.co.ais.trans2.model.remittance;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �����ړI�̌�������
 */
public class RemittanceSearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �����ړI�R�[�h */
	protected String code = null;

	/** �����ړI���� */
	protected String name = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** �����ړI���J�i */
	protected String kana = null;

	/** �J�ilike */
	protected String kanaLike = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** ��������like */
	protected String namekLike = null;

	/** ���ێ��x�R�[�h */
	protected String balanceCode = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RemittanceSearchCondition clone() {
		try {
			return (RemittanceSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return ��ЃR�[�h��߂�
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode ��ЃR�[�h��ݒ肷��
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return �����ړI�R�[�h��߂�
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code �����ړI�R�[�h��ݒ肷��
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return �����ړI���̂�߂�
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name �����ړI���̂�ݒ肷��
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return codeLike��߂�
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike codeLike��ݒ肷��
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * @return �����ړI���J�i��߂�
	 */
	public String getKana() {
		return kana;
	}

	/**
	 * @param kana �����ړI���J�i��ݒ肷��
	 */
	public void setKana(String kana) {
		this.kana = kana;
	}

	/**
	 * @return kanaLike��߂�
	 */
	public String getNameLike() {
		return kanaLike;
	}

	/**
	 * @param kanaLike kanaLike��ݒ肷��
	 */
	public void setKanaLike(String kanaLike) {
		this.kanaLike = kanaLike;
	}

	/**
	 * @return codeFrom��߂�
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFrom��ݒ肷��
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeTo��߂�
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeTo��ݒ肷��
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return ���̂����܂���߂�
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike ���̂����܂���ݒ肷��
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * @return ���ێ��x�R�[�h��߂�
	 */
	public String getBalanceCode() {
		return balanceCode;
	}

	/**
	 * @param balanceCode ���ێ��x�R�[�h��ݒ肷��
	 */
	public void setBalanceCode(String balanceCode) {
		this.balanceCode = balanceCode;
	}
}
