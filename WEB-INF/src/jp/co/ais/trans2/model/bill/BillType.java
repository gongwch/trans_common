package jp.co.ais.trans2.model.bill;

import java.util.Date;
import jp.co.ais.trans.common.dt.TransferBase;

/**
 * �����敪�}�X�^���
 * 
 * @author AIS
 */
public class BillType extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 1201636486328477363L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �����敪 */
	protected String code = null;

	/** �������t�H�[�}�b�g */
	protected String format = null;

	/** ���� */
	protected String name = null;

	/** �������� */
	protected String namek = null;

	/** ���׌��� */
	protected int detailCount = 1;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** �o�^���t */
	protected Date inpDate;

	/** �X�V���t */
	protected Date updDate;

	/** �v���O����ID */
	protected String prgId;

	/** ���[�U�[ID */
	protected String usrId;

	/**
	 * code���擾����B
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * code��ݒ肷��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * companyCode���擾����B
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * dateFrom���擾����B
	 * 
	 * @return Date dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * dateFrom��ݒ肷��B
	 * 
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * dateTo���擾����B
	 * 
	 * @return Date dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * dateTo��ݒ肷��B
	 * 
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * form���擾����B
	 * 
	 * @return String form
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * form��ݒ肷��B
	 * 
	 * @param form
	 */
	public void setFormat(String form) {
		this.format = form;
	}

	/**
	 * inpDate���擾����B
	 * 
	 * @return Date inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * inpDate��ݒ肷��B
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * name���擾����B
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name��ݒ肷��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * namek���擾����B
	 * 
	 * @return String namek
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * namek��ݒ肷��B
	 * 
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * prgId���擾����B
	 * 
	 * @return String prgId
	 */
	public String getPrgId() {
		return prgId;
	}

	/**
	 * prgId��ݒ肷��B
	 * 
	 * @param prgId
	 */
	public void setPrgId(String prgId) {
		this.prgId = prgId;
	}

	/**
	 * updDate���擾����B
	 * 
	 * @return Date updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * updDate��ݒ肷��B
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * usrId���擾����B
	 * 
	 * @return String usrId
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * usrId��ݒ肷��B
	 * 
	 * @param usrId
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	/**
	 * ���׌����̎擾
	 * 
	 * @return detailCount ���׌���
	 */
	public int getDetailCount() {
		return detailCount;
	}

	/**
	 * ���׌����̐ݒ�
	 * 
	 * @param detailCount ���׌���
	 */
	public void setDetailCount(int detailCount) {
		this.detailCount = detailCount;
	}

}
