package jp.co.ais.trans.master.entity;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���`�[���� �����p�����[�^
 */
public class ApSlipParameter extends TransferBase {

	/** �\�[�g �x���� �A�`�[�ԍ��A�`�[���t */
	public static final String SORT�QSIHA_DATE = "SWK_SIHA_DATE,DEN_NO";

	/** �\�[�g(��Њԕt��) ��ЃR�[�h �A�`�[���t�A�`�[�ԍ��A�s�ԍ� */
	public static final String SORT_TUKEKAE = "KAI_CODE,DEN_DATE,DEN_NO,SWK_GYO_NO";

	/** �f�[�^�敪 �Ј����� */
	public static final String DATA_KBN_EMP_KARI = "21";

	/** �f�[�^�敪 �o��Z */
	public static final String DATA_KBN_ADJUSTMENT = "22";

	/** �f�[�^�敪 ���۔��c�� */
	public static final String DATA_KBN_ASSOCIATE_EXPENSE = "2E";

	/** �`�[��ʃR�[�h �Ј����� */
	public static final String DEN_SYU_EMP_KARI = "021";

	/** �`�[��ʃR�[�h �o��Z */
	public static final String DEN_SYU_ADJUSTMENT = "022";

	/** �d��C���^�[�t�F�C�X�敪 �o�^ */
	public static final String SWK_IN_KBN_REGIST = "0";

	/** �X�V�敪 �o�^ */
	public static final int UPD_KBN_REGIST = 1;

	/** �X�V�敪 �����F */
	public static final int UPD_KBN_TMP_APPROVE = 2;

	/** �X�V�敪 ���F */
	public static final int UPD_KBN_APPROVE = 3;

	/** �X�V�敪 �X�V */
	public static final int UPD_KBN_UPDATE = 4;

	/** �X�V�敪 ����۔F */
	public static final int UPD_KBN_NOT_APPROVE = 11;

	/** �X�V�敪 �o���۔F */
	public static final int UPD_KBN_NOT_ACOUNT_APPROVE = 12;

	/** �X�V���x�� ���͎� */
	public static final int UPD_LVL_EMP = 1;

	/** �X�V���x�� ���� */
	public static final int UPD_LVL_DEPT = 2;

	/** �x�����ϋ敪 �ʏ� */
	public static final int KESAI_KBN_NOMAL = 0;

	/** �x�������R�[�h �����U�� */
	public static final String NAI_CODE_EMP_UNPAID = "03";

	/** �r���敪 �r���� */
	public static final String HAITA_LOCK = "1";

	/** �r���敪 �ʏ� */
	public static final String HAITA_UNLOCK = "0";

	/** �r���敪 �ʏ�A�܂��͔r���� */
	public static final String HAITA_ALL = "";

	/** ��ЃR�[�h */
	private String kaiCode = "";

	/** �`�[���t */
	private Date denDate;

	/** �x���� */
	private Date sihaDate;

	/** �Ј��R�[�h */
	private String empCode = "";

	/** �˗��� */
	private String iraiEmpCode = "";

	/** �˗��ҁiorNull�����j */
	private String iraiEmpCodeAndNull = "";

	/** �˗�����R�[�h */
	private String iraiDepCode = "";

	/** �f�[�^�敪 */
	private String[] dataKbn;

	/** �`�[��� */
	private String[] denSyuCode;

	/** ��Њԕt�֋敪 */
	private String tukeKbn = "0";

	/** �d��C���^�[�t�F�C�X�敪 */
	private String swkInKbn = "";

	/** �`�[�ԍ� */
	private String denNo = "";

	/** ��t�ԍ� */
	private String utkNo = "";

	/** �X�V�敪 */
	private int[] updKbn;

	/** �x�����z (NOT����) */
	private BigDecimal notSihaKin;

	/** �r���敪 */
	private String shrKbn = "0";

	/** �������Z�`�[�ԍ�(�r�������Őݒ�) */
	private String notHaitaKariCrDenNo = "";

	/** �����\���`�[�ԍ� */
	private String kariDrDenNo = "";

	/** �x�����ϋ敪 */
	private int[] kesaiKbn;

	/** �x�����@�}�X�^.�����R�[�h(�z��) */
	private String[] hohNaiCodes;

	/** �x�����@�}�X�^.�����R�[�h */
	private String hohNaiCode = "";

	/** �Ј���(�����܂�����) */
	private String likeEmpName = "";

	/** �`�[�ԍ�(�����܂�����) */
	private String likeDenNo = "";

	/** �`�[�E�v(�����܂�����) */
	private String likeDenTek = "";

	/** �x�����z(�����܂�����) */
	private String likeSihaKin = "";

	/** �x�����@�R�[�h */
	private String hohCode = "";

	/** ��s�����R�[�h */
	private String cbkCode = "";

	/** �v�㕔��R�[�h */
	private String depCode = "";

	/** �`�[���t(�ȉ�) */
	private Date underDenDate;

	/** ���Z�\���(�ȉ�) */
	private Date underSsyDate;

	/** �\�[�g */
	private String orderBy = "DEN_DATE,DEN_NO,SWK_GYO_NO";

	/** �Ј��R�[�h */
	private String empCodeLike = "";

	/** ����R�[�h */
	private String depCodeLike = "";

	/** �`�[���t�i�J�n�j */
	private Date denDateBegin;

	/** �`�[���t�i�I���j */
	private Date denDateEnd;

	/** �x�����i�J�n�j */
	private Date sihaDateBegin;

	/** �x�����i�I���j */
	private Date sihaDateEnd;

	/**
	 * �f�[�^�敪���擾����
	 * 
	 * @return �f�[�^�敪
	 */
	public String[] getDataKbn() {
		return dataKbn;
	}

	/**
	 * �f�[�^�敪��ݒ肷��
	 * 
	 * @param data_kbn
	 */
	public void setDataKbn(String[] data_kbn) {
		dataKbn = data_kbn;
	}

	/**
	 * �`�[���t���擾����
	 * 
	 * @return �`�[���t
	 */
	public Date getDenDate() {
		return denDate;
	}

	/**
	 * �`�[�f�[�^��ݒ肷��B
	 * 
	 * @param den_dete
	 */
	public void setDenDate(Date den_dete) {
		denDate = den_dete;
	}

	/**
	 * �`�[�ԍ����擾����
	 * 
	 * @return �`�[�ԍ�
	 */
	public String getDenNo() {
		return denNo;
	}

	/**
	 * �`�[�ԍ���ݒ肷��
	 * 
	 * @param den_no
	 */
	public void setDenNo(String den_no) {
		denNo = den_no;
	}

	/**
	 * �Ј��R�[�h���擾����
	 * 
	 * @return �Ј��R�[�h
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * �Ј��R�[�h��ݒ肷��
	 * 
	 * @param emp_code
	 */
	public void setEmpCode(String emp_code) {
		empCode = emp_code;
	}

	/**
	 * �x�����@�����R�[�h��ݒ肷��
	 * 
	 * @return �x�����@�����R�[�h
	 */
	public String getHohNaiCode() {
		return hohNaiCode;
	}

	/**
	 * �x�����@�����R�[�h���擾����
	 * 
	 * @param hoh_nai_code
	 */
	public void setHohNaiCode(String hoh_nai_code) {
		hohNaiCode = hoh_nai_code;
	}

	/**
	 * ��ЃR�[�h���擾����
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKaiCode() {
		return kaiCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��
	 * 
	 * @param kai_code
	 */
	public void setKaiCode(String kai_code) {
		kaiCode = kai_code;
	}

	/**
	 * �x�����ϋ敪���w�肷��B
	 * 
	 * @return �x�����ϋ敪
	 */
	public int[] getKesaiKbn() {
		return kesaiKbn;
	}

	/**
	 * �x�����ϋ敪��ݒ肷��
	 * 
	 * @param kesai_kbn
	 */
	public void setKesaiKbn(int[] kesai_kbn) {
		kesaiKbn = kesai_kbn;
	}

	/**
	 * �Ј������擾����
	 * 
	 * @return �Ј���
	 */
	public String getLikeEmpName() {

		if (Util.isNullOrEmpty(likeEmpName)) {
			return "";
		}

		return DBUtil.getLikeStatement(likeEmpName, DBUtil.UNICODE_CHAR);
	}

	/**
	 * �Ј�����ݒ肷��
	 * 
	 * @param emp_name
	 */
	public void setLikeEmpName(String emp_name) {
		likeEmpName = emp_name;
	}

	/**
	 * �`�[�ԍ�(�����܂�����)���擾����
	 * 
	 * @return �`�[�ԍ�(�����܂�����)
	 */
	public String getLikeDenNo() {

		if (Util.isNullOrEmpty(likeDenNo)) {
			return "";
		}

		return DBUtil.getLikeStatement(likeDenNo, DBUtil.NORMAL_CHAR);
	}

	/**
	 * �`�[�ԍ�(�����܂�����)��ݒ肷��
	 * 
	 * @param like_den_no
	 */
	public void setLikeDenNo(String like_den_no) {
		likeDenNo = like_den_no;
	}

	/**
	 * �E�v(�����܂�����)���擾����
	 * 
	 * @return �E�v(�����܂�����)
	 */
	public String getLikeDenTek() {

		if (Util.isNullOrEmpty(likeDenTek)) {
			return "";
		}

		return DBUtil.getLikeStatement(likeDenTek, DBUtil.UNICODE_CHAR);
	}

	/**
	 * �E�v(�����܂�����)��ݒ肷��
	 * 
	 * @param like_den_tek
	 */
	public void setLikeDenTek(String like_den_tek) {
		likeDenTek = like_den_tek;
	}

	/**
	 * �x�����z(�����܂�����)��ݒ肷��
	 * 
	 * @return �x�����z(�����܂�����)
	 */
	public String getLikeSihaKin() {

		if (Util.isNullOrEmpty(likeSihaKin)) {
			return "";
		}

		return "%" + likeSihaKin + "%";
	}

	/**
	 * @param likeSihaKin
	 */
	public void setLikeSihaKin(String likeSihaKin) {
		this.likeSihaKin = likeSihaKin;
	}

	/**
	 * �x�����z(�����܂�����)���擾����
	 * 
	 * @param kin
	 */
	public void setLikeSihaKinValue(BigDecimal kin) {
		likeSihaKin = kin.toPlainString();
	}

	/**
	 * �x�������擾����
	 * 
	 * @return �x����
	 */
	public Date getSihaDate() {
		return sihaDate;
	}

	/**
	 * �x������ݒ肷��
	 * 
	 * @param siha_date
	 */
	public void setSihaDate(Date siha_date) {
		sihaDate = siha_date;
	}

	/**
	 * �X�V�敪���擾����
	 * 
	 * @return �X�V�敪
	 */
	public int[] getUpdKbn() {
		return updKbn;
	}

	/**
	 * �X�V�敪��ݒ肷��
	 * 
	 * @param upd_kbn
	 */
	public void setUpdKbn(int[] upd_kbn) {
		updKbn = upd_kbn;
	}

	/**
	 * �r���敪���擾����
	 * 
	 * @return �r���敪
	 */
	public String getShrKbn() {
		return shrKbn;
	}

	/**
	 * �r���敪
	 * 
	 * @param shrKbn
	 */
	public void setShrKbn(String shrKbn) {
		this.shrKbn = shrKbn;
	}

	/**
	 * �r���敪
	 * 
	 * @param shrKbn
	 */
	public void setIsShrKbn(boolean shrKbn) {
		this.shrKbn = BooleanUtil.toString(shrKbn);
	}

	/**
	 * �\�[�g���擾����
	 * 
	 * @return �I�[�_�[
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * �\�[�g��ݒ肷��
	 * 
	 * @param orderBy
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * �x�����@�}�X�^.�����R�[�h(�z��)���擾����
	 * 
	 * @return �x�����@�}�X�^.�����R�[�h(�z��)
	 */
	public String[] getHohNaiCodes() {
		return hohNaiCodes;
	}

	/**
	 * �x�����@�}�X�^.�����R�[�h(�z��)��ݒ肷��
	 * 
	 * @param hohNaiCodes
	 */
	public void setHohNaiCodes(String[] hohNaiCodes) {
		this.hohNaiCodes = hohNaiCodes;
	}

	/**
	 * �������Z�`�[�ԍ���ݒ�
	 * 
	 * @return �������Z�`�[�ԍ�(�r�������Őݒ�)
	 */
	public String getNotHaitaKariCrDenNo() {
		return notHaitaKariCrDenNo;
	}

	/**
	 * �������Z�`�[�ԍ���ݒ肷��
	 * 
	 * @param kariCrDenNo
	 */
	public void setNotHaitaKariCrDenNo(String kariCrDenNo) {
		this.notHaitaKariCrDenNo = kariCrDenNo;
	}

	/**
	 * �����\���`�[�ԍ�
	 * 
	 * @return �����\���`�[�ԍ�
	 */
	public String getKariDrDenNo() {
		return kariDrDenNo;
	}

	/**
	 * �����\���`�[�ԍ�
	 * 
	 * @param kariDrDenNo
	 */
	public void setKariDrDenNo(String kariDrDenNo) {
		this.kariDrDenNo = kariDrDenNo;
	}

	/**
	 * �v�㕔��R�[�h���擾����
	 * 
	 * @return �v�㕔��R�[�h
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * �v�㕔��R�[�h��ݒ肷��
	 * 
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * �`�[���t(�ȉ�)���擾����
	 * 
	 * @return �`�[���t�i�ȉ��j
	 */
	public Date getUnderDenDate() {
		return underDenDate;
	}

	/**
	 * �`�[���t(�ȉ�)��ݒ肷��
	 * 
	 * @param underDenDate
	 */
	public void setUnderDenDate(Date underDenDate) {
		this.underDenDate = underDenDate;
	}

	/**
	 * ���Z�\���(�ȉ�)���擾����
	 * 
	 * @return �`�[���t�i�ȉ��j
	 */
	public Date getUnderSsyDate() {
		return underSsyDate;
	}

	/**
	 * ���Z�\���(�ȉ�)��ݒ肷��
	 * 
	 * @param underSsyDate
	 */
	public void setUnderSsyDate(Date underSsyDate) {
		this.underSsyDate = underSsyDate;
	}

	/**
	 * ��t�ԍ����擾����
	 * 
	 * @return ��t�ԍ�
	 */
	public String getUtkNo() {
		return utkNo;
	}

	/**
	 * ��t�ԍ���ݒ肷��
	 * 
	 * @param utkNo
	 */
	public void setUtkNo(String utkNo) {
		this.utkNo = utkNo;
	}

	/**
	 * ��s�����R�[�h���擾����
	 * 
	 * @return ��s����
	 */
	public String getCbkCode() {
		return cbkCode;
	}

	/**
	 * ��s�����R�[�h��ݒ肷��
	 * 
	 * @param cbkCode
	 */
	public void setCbkCode(String cbkCode) {
		this.cbkCode = cbkCode;
	}

	/**
	 * �x�����@�R�[�h���擾����
	 * 
	 * @return �x�����@�R�[�h
	 */
	public String getHohCode() {
		return hohCode;
	}

	/**
	 * �x�����@�R�[�h��ݒ肷��
	 * 
	 * @param hohCode
	 */
	public void setHohCode(String hohCode) {
		this.hohCode = hohCode;
	}

	/**
	 * �x�����z (NOT����)���擾����
	 * 
	 * @return �x�����z (NOT����)
	 */
	public BigDecimal getNotSihaKin() {
		return notSihaKin;
	}

	/**
	 * �x�����z (NOT����)��ݒ肷��
	 * 
	 * @param notSihaKin
	 */
	public void setNotSihaKin(BigDecimal notSihaKin) {
		this.notSihaKin = notSihaKin;
	}

	/**
	 * �`�[��ʃR�[�h���擾����
	 * 
	 * @return �`�[��ʃR�[�h
	 */
	public String[] getDenSyuCode() {
		return denSyuCode;
	}

	/**
	 * �`�[��ʃR�[�h��ݒ肷��
	 * 
	 * @param denSyuCode
	 */
	public void setDenSyuCode(String[] denSyuCode) {
		this.denSyuCode = denSyuCode;
	}

	/**
	 * �d��C���^�[�t�F�C�X�敪���擾����
	 * 
	 * @return �d��C���^�[�t�F�C�X�敪
	 */
	public String getSwkInKbn() {
		return swkInKbn;
	}

	/**
	 * �d��C���^�[�t�F�C�X�敪��ݒ肷��
	 * 
	 * @param swkInKbn
	 */
	public void setSwkInKbn(String swkInKbn) {
		this.swkInKbn = swkInKbn;
	}

	/**
	 * ��Њԕt�֋敪���擾����
	 * 
	 * @return ��Њԕt�֋敪
	 */
	public String getTukeKbn() {
		return tukeKbn;
	}

	/**
	 * ��Њԕt�֋敪��ݒ肷��
	 * 
	 * @param tukeKbn
	 */
	public void setTukeKbn(String tukeKbn) {
		this.tukeKbn = tukeKbn;
	}

	/**
	 * �˗��҃R�[�h���擾����
	 * 
	 * @return �˗��҃R�[�h
	 */
	public String getIraiEmpCode() {
		return iraiEmpCode;
	}

	/**
	 * �˗��҃R�[�h��ݒ肷��
	 * 
	 * @param iraiEmpCode
	 */
	public void setIraiEmpCode(String iraiEmpCode) {
		this.iraiEmpCode = iraiEmpCode;
	}

	/**
	 * �˗�����R�[�h���擾����
	 * 
	 * @return �˗�����R�[�h
	 */
	public String getIraiDepCode() {
		return iraiDepCode;
	}

	/**
	 * �˗�����R�[�h��ݒ肷��
	 * 
	 * @param iraiDepCode
	 */
	public void setIraiDepCode(String iraiDepCode) {
		this.iraiDepCode = iraiDepCode;
	}

	/**
	 * �˗��ҁiorNull�����j���擾����
	 * 
	 * @return �˗��ҁiorNull�����j
	 */
	public String getIraiEmpCodeAndNull() {
		return iraiEmpCodeAndNull;
	}

	/**
	 * �˗��ҁiorNull�����j��ݒ肷��
	 * 
	 * @param iraiEmpCodeAndNull
	 */
	public void setIraiEmpCodeAndNull(String iraiEmpCodeAndNull) {
		this.iraiEmpCodeAndNull = iraiEmpCodeAndNull;
	}

	/**
	 * depCodeLike�擾
	 * 
	 * @return depCodeLike
	 */
	public String getDepCodeLike() {
		if (Util.isNullOrEmpty(depCodeLike)) {
			return "";
		}

		return DBUtil.getLikeStatement(depCodeLike, DBUtil.UNICODE_CHAR);
	}

	/**
	 * depCodeLike�ݒ�
	 * 
	 * @param depCodeLike
	 */
	public void setDepCodeLike(String depCodeLike) {
		this.depCodeLike = depCodeLike;
	}

	/**
	 * empCodeLike�擾
	 * 
	 * @return empCodeLike
	 */
	public String getEmpCodeLike() {

		if (Util.isNullOrEmpty(empCodeLike)) {
			return "";
		}

		return DBUtil.getLikeStatement(empCodeLike, DBUtil.UNICODE_CHAR);
	}

	/**
	 * empCodeLike�ݒ�
	 * 
	 * @param empCodeLike
	 */
	public void setEmpCodeLike(String empCodeLike) {
		this.empCodeLike = empCodeLike;
	}

	/**
	 * denDateBegin�擾
	 * 
	 * @return denDateBegin
	 */
	public Date getDenDateBegin() {
		return denDateBegin;
	}

	/**
	 * denDateBegin�ݒ�
	 * 
	 * @param denDateBegin
	 */
	public void setDenDateBegin(Date denDateBegin) {
		this.denDateBegin = denDateBegin;
	}

	/**
	 * denDateEnd�擾
	 * 
	 * @return denDateEnd
	 */
	public Date getDenDateEnd() {
		return denDateEnd;
	}

	/**
	 * denDateEnd�ݒ�
	 * 
	 * @param denDateEnd
	 */
	public void setDenDateEnd(Date denDateEnd) {
		this.denDateEnd = denDateEnd;
	}

	/**
	 * sihaDateBegin�擾
	 * 
	 * @return sihaDateBegin
	 */
	public Date getSihaDateBegin() {
		return sihaDateBegin;
	}

	/**
	 * sihaDateBegin�ݒ�
	 * 
	 * @param sihaDateBegin
	 */
	public void setSihaDateBegin(Date sihaDateBegin) {
		this.sihaDateBegin = sihaDateBegin;
	}

	/**
	 * sihaDateEnd�擾
	 * 
	 * @return sihaDateEnd
	 */
	public Date getSihaDateEnd() {
		return sihaDateEnd;
	}

	/**
	 * sihaDateEnd�ݒ�
	 * 
	 * @param sihaDateEnd
	 */
	public void setSihaDateEnd(Date sihaDateEnd) {
		this.sihaDateEnd = sihaDateEnd;
	}

}
