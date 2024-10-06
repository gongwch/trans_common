package jp.co.ais.trans2.common.model.report;

import jp.co.ais.trans2.define.*;

/**
 * �`�[���C�A�E�g�̒�`
 * 
 * @author AIS
 */
public class SlipLayoutDefine {

	/** �`�[��ʃR�[�h */
	protected String slipType = null;

	/** �`�[�̎�ށi�f�[�^�敪�j */
	protected SlipKind slipKind = null;

	/** �f�[�^�敪�̃R�[�h */
	protected String dataDivision = null;

	/** �w�b�_�[�P���` */

	/** �w�b�_�[�F�`�[���t */
	protected String headerSlipDateWord = "C00599";

	/** �w�b�_�[�F���͕��� */
	protected String headerDepartmentNameWord = "C01280";

	/** �w�b�_�[�F���͎� */
	protected String headerEmployeeNameWord = "C01278";

	/** �w�b�_�[�F�`�[�ԍ� */
	protected String headerSlipNoWord = "C00605";

	/** �w�b�_�[�F�C�� */
	protected String headerUpdateWord = "C01760";

	/** �w�b�_�[�F�� */
	protected String headerUpdateCountWord = "C01761";

	/** �w�b�_�[�F�؜ߔԍ� */
	protected String headerVoucherNoWord = "C01178";

	/** �w�b�_�[�F�`�[�E�v */
	protected String headerSlipRemarkWord = "C00569";

	/** �w�b�_�[�F��ʉ� */
	protected String headerKeyCurrencyWord = "C00907";

	/** �w�b�_�[�F����敪 */
	protected String headerAccountBookNameWord = "C10961";

	/** �w�b�_�[�F���F��1 */
	protected String headerAcceptStamp1Word = "C01762";

	/** �w�b�_�[�F���F��2 */
	protected String headerAcceptStamp2Word = "C11436";

	/** �w�b�_�[�F���F��3 */
	protected String headerAcceptStamp3Word = "C11437";

	/** ���גP���` */

	/** ���ׁF�ؕ����z */
	protected String detailDrAmountWord = "C01557";

	/** ���ׁF�ݕ����z */
	protected String detailCrAmountWord = "C01559";

	/** ���ׁF�s�E�v */
	protected String detailRowRemarkWord = "C00119";

	/** ���ׁF�v�㕔��/�����/�Ј� */
	protected String detailCustomerWord = "C11071";

	/** ���ׁF�ʉ� */
	protected String detailCurrencyWord = "C00371";

	/** ���ׁF������/�� */
	protected String detailTaxWord = "C11435";

	/** ���ׁF�Ǘ�1/2/3 */
	protected String detailManagement1To3Word = "C11072";

	/** ���ׁF�Ǘ�4/5/6 */
	protected String detailManagement4To6Word = "C11073";

	/** ���ׁF���v����1/2/3 */
	protected String detailNonAccountWord = "C01763";

	/** ���ׁF���v */
	protected String detailSumWord = "C00165";

	/** ���ׁF�O�݌v1 */
	protected String detailForeign1Word = "C01764";

	/** ���ׁF�O�݌v2 */
	protected String detailForeign2Word = "C01765";

	/** ���ׁF�O�݌v3 */
	protected String detailForeign3Word = "C11608";

	/** ���ׁF�O�݌v4 */
	protected String detailForeign4Word = "C11609";

	/** �񕝒�` */

	/** �s�����F�ύX�ɂ�郌�C�A�E�g�����K�v */
	protected double rowHeight = 0.50d;

	/** �w�b�_�[�^�C�g���� */
	protected double headerTitleWidth = 2.5d;

	/** �w�b�_�[���啝 */
	protected double headerDepartmentWidth = 9.0d;

	/** ��s�� */
	protected double detailRestWidth = 5.85d;

	/** �s�ԍ��� */
	protected double detailRowNoWidth = 0.7d;

	/** �Ȗڕ� */
	protected double detailItemWidth = 4.6d;

	/** ���z�� */
	protected double detailAmountWidth = 3.10d;

	/** �ʉݕ� */
	protected double detailCurrencyWidth = 1.8d;

	/** �E�v�� */
	protected double detailRemarkWidth = 4.5d;

	/** ����敝 */
	protected double detailCustomerWidth = 3.6d;

	/** �Ǘ�1�`3�� */
	protected double detailManagement1To3Width = 2.6d;

	/** �Ǘ�4�`6�� */
	protected double detailManagement4To6Width = 2.6d;

	/** ���v���ו� */
	protected double detailNonAccountWidth = 2.25d;

	/** ������` */

	/** ����敪���� */
	protected int headerAccountBookNameLength = 12;

	/** �C���񐔌��� */
	protected int headerUpdateCountLength = 3;

	/** ���F��1���� */
	protected int headerAcceptStamp1Length = 6;

	/** ���F��2���� */
	protected int headerAcceptStamp2Length = 6;

	/** ���F��3���� */
	protected int headerAcceptStamp3Length = 6;

	/** �Ȗڌ��� */
	protected int detailItemLength = 28;

	/** ����Ō��� */
	protected int detailTaxLength = 10;

	/** �E�v���� */
	protected int detailRemarkLength = 27;

	/** ����挅�� */
	protected int detailCustomerLength = 21;

	/** �Ǘ�1�`3���� */
	protected int detailManagement1To3Length = 15;

	/** �Ǘ�4�`6���� */
	protected int detailManagement4To6Length = 15;

	/** ���v���׌��� */
	protected int detailNonAccountLength = 16;

	/** �w�b�_�[�F���͕��包�� */
	protected int headerDepartmentNameLength = 55;

	/** �w�b�_�[�ǉ�������` */
	/** �w�b�_�[�F���o���`�[�̃��[�g�� */
	protected double headerRateWidth = 2.80d;

	/** �w�b�_�[�F���o���`�[�̋��z�� */
	protected double headerAmountWidth = 3.10d;

	/** �w�b�_�[�F���o���`�[�̒ʉݕ� */
	protected double headerCurrencyWidth = 1.8d;

	/** �w�b�_�[�F���v��̃��[�g�� */
	protected double headerPayRateWidth = 2.80d;

	/** �w�b�_�[�F���v��̋��z�� */
	protected double headerPayAmountWidth = 3.10d;

	/** �w�b�_�[�F���v��̒ʉݕ� */
	protected double headerPayCurrencyWidth = 1.8d;

	/** �w�b�_�[�F���v��̎x������ */
	protected double headerPayDateWidth = 2.00d;

	/** �w�b�_�[�F���v��̎x���敪�� */
	protected double headerPayDateAndTypeWidth = 1.20d;

	/** �w�b�_�[�F���v��̃��[�g�� */
	protected double headerSaleRateWidth = 2.80d;

	/** �w�b�_�[�F���v��̋��z�� */
	protected double headerSaleAmountWidth = 3.10d;

	/** �w�b�_�[�F���v��̒ʉݕ� */
	protected double headerSaleCurrencyWidth = 1.8d;

	/** �w�b�_�[�F�o��Z�̃��[�g�� */
	protected double headerSettlementRateWidth = 2.30d;

	/** �w�b�_�[�F�o��Z�̋��z�� */
	protected double headerSettlementAmountWidth = 3.00d;

	/** �w�b�_�[�F�o��Z�̒ʉݕ� */
	protected double headerSettlementCurrencyWidth = 0.70d;

	/** �w�b�_�[�F�Ј������̃��[�g�� */
	protected double headerTempRateWidth = 2.30d;

	/** �w�b�_�[�F�Ј������̋��z�� */
	protected double headerTempAmountWidth = 3.00d;

	/** �w�b�_�[�F�Ј������̒ʉݕ� */
	protected double headerTempCurrencyWidth = 0.70d;

	/** �w�b�_�[�F�����`�[�̓������z */
	protected String headerInputCashflowAmountWord = "C00666";

	/** �w�b�_�[�F�o���`�[�̏o�����z */
	protected String headerOutputCashflowAmountWord = "C02387";

	/** �w�b�_�[�F���v��̎x���� */
	protected String headerPayCustomerWord = "C01690";

	/** �w�b�_�[�F���v��̎x����/�x�����@ */
	protected String headerPayDateAndTypeWord = "C10893";

	/** �w�b�_�[�F���v��̎x������ */
	protected String headerPayAccountWord = "C01639";

	/** �w�b�_�[�F���v��̐U�o��s */
	protected String headerPayOwnBankWord = "C01634";

	/** �w�b�_�[�F���v��̎x�����z */
	protected String headerPayAmountWord = "C00229";

	/** �w�b�_�[�F�Ј������̎Ј� */
	protected String headerTempEmployeeWord = "C00246";

	/** �w�b�_�[�F�Ј������̉������z */
	protected String headerTempAmountWord = "C04239";

	/** �w�b�_�[�F�Ј������̎x���� */
	protected String headerTempPaymentDateWord = "C01098";

	/** �w�b�_�[�F�Ј������̎x�����@ */
	protected String headerTempPaymentMethodWord = "C00233";

	/** �w�b�_�[�F�Ј������̐U�o��s */
	protected String headerTempBankWord = "C01634";

	/** �w�b�_�[�F�Ј������̐��Z�\��� */
	protected String headerTempDueDateWord = "C04235";

	/** �w�b�_�[�F���v��̎���� */
	protected String headerSaleCustomerWord = "C00408";

	/** �w�b�_�[�F���v��̓����\��� */
	protected String headerSaleDateWord = "C01273";

	/** �w�b�_�[�F���v��̐U������ */
	protected String headerSaleBankAccountWord = "C00683";

	/** �w�b�_�[�F���v��̐������z */
	protected String headerSaleAmountWord = "C01689";

	/** �w�b�_�[�F�o��Z�̎Ј� */
	protected String headerSettlementEmployeeWord = "C00246";

	/** �w�b�_�[�F�o��Z�̌o����v */
	protected String headerSettlementAmountSumWord = "C04282";

	/** �w�b�_�[�F�o��Z�̎x���� */
	protected String headerSettlementPaymentDateWord = "C01098";

	/** �w�b�_�[�F�o��Z�̉������z */
	protected String headerSettlementPrevAmountWord = "C04239";

	/** �w�b�_�[�F�o��Z�̎x�����@ */
	protected String headerSettlementPayMethodWord = "C00233";

	/** �w�b�_�[�F�o��Z�̐��Z���z */
	protected String headerSettlementAmountWord = "C01707";

	/** �w�b�_�[�F�o��Z�̐U�o��s */
	protected String headerSettlementBankWord = "C01634";

	/** �w�b�_�[�`�[�E�v�R�[�h��\�� */
	protected boolean notShowHeaderSlipRemarkCode = false;

	/** �w�b�_�[���͕���R�[�h��\�� */
	protected boolean notShowHeaderDepartmentCode = false;

	/** �w�b�_�[���͎Ј��R�[�h��\�� */
	protected boolean notShowHeaderEmployeeCode = false;

	/** ����R�[�h��\�� */
	protected boolean notShowDepartmentCode = false;

	/** �ȖڃR�[�h��\�� */
	protected boolean notShowItemCode = false;

	/** �⏕�ȖڃR�[�h��\�� */
	protected boolean notShowSubItemCode = false;

	/** ����ȖڃR�[�h��\�� */
	protected boolean notShowDetailItemCode = false;

	/** �����R�[�h��\�� */
	protected boolean notShowCustomerCode = false;

	/** �Ј��R�[�h��\�� */
	protected boolean notShowEmployeeCode = false;

	/** ����ŃR�[�h��\�� */
	protected boolean notShowTaxCode = false;

	/** �Ǘ�1�R�[�h��\�� */
	protected boolean notShowManagementCode1 = false;

	/** �Ǘ�2�R�[�h��\�� */
	protected boolean notShowManagementCode2 = false;

	/** �Ǘ�3�R�[�h��\�� */
	protected boolean notShowManagementCode3 = false;

	/** �Ǘ�4�R�[�h��\�� */
	protected boolean notShowManagementCode4 = false;

	/** �Ǘ�5�R�[�h��\�� */
	protected boolean notShowManagementCode5 = false;

	/** �Ǘ�6�R�[�h��\�� */
	protected boolean notShowManagementCode6 = false;

	/**
	 * �`�[��ʃR�[�h�̎擾
	 * 
	 * @return slipType �`�[��ʃR�[�h
	 */
	public String getSlipType() {
		return slipType;
	}

	/**
	 * �`�[��ʃR�[�h�̐ݒ�
	 * 
	 * @param slipType �`�[��ʃR�[�h
	 */
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}

	/**
	 * �`�[�̎�ށi�f�[�^�敪�j�̎擾
	 * 
	 * @return slipKind �`�[�̎�ށi�f�[�^�敪�j
	 */
	public SlipKind getSlipKind() {
		return slipKind;
	}

	/**
	 * �`�[�̎�ށi�f�[�^�敪�j�̐ݒ�
	 * 
	 * @param slipKind �`�[�̎�ށi�f�[�^�敪�j
	 */
	public void setSlipKind(SlipKind slipKind) {
		this.slipKind = slipKind;
	}

	/**
	 * �f�[�^�敪�̃R�[�h�̎擾
	 * 
	 * @return dataDivision �f�[�^�敪�̃R�[�h
	 */
	public String getDataDivision() {
		return dataDivision;
	}

	/**
	 * �f�[�^�敪�̃R�[�h�̐ݒ�
	 * 
	 * @param dataDivision �f�[�^�敪�̃R�[�h
	 */
	public void setDataDivision(String dataDivision) {
		this.dataDivision = dataDivision;
	}

	/**
	 * �w�b�_�[�F�`�[���t�̎擾
	 * 
	 * @return headerSlipDateWord �w�b�_�[�F�`�[���t
	 */
	public String getHeaderSlipDateWord() {
		return headerSlipDateWord;
	}

	/**
	 * �w�b�_�[�F�`�[���t�̐ݒ�
	 * 
	 * @param headerSlipDateWord �w�b�_�[�F�`�[���t
	 */
	public void setHeaderSlipDateWord(String headerSlipDateWord) {
		this.headerSlipDateWord = headerSlipDateWord;
	}

	/**
	 * �w�b�_�[�F���͕���̎擾
	 * 
	 * @return headerDepartmentNameWord �w�b�_�[�F���͕���
	 */
	public String getHeaderDepartmentNameWord() {
		return headerDepartmentNameWord;
	}

	/**
	 * �w�b�_�[�F���͕���̐ݒ�
	 * 
	 * @param headerDepartmentNameWord �w�b�_�[�F���͕���
	 */
	public void setHeaderDepartmentNameWord(String headerDepartmentNameWord) {
		this.headerDepartmentNameWord = headerDepartmentNameWord;
	}

	/**
	 * �w�b�_�[�F���͎҂̎擾
	 * 
	 * @return headerEmployeeNameWord �w�b�_�[�F���͎�
	 */
	public String getHeaderEmployeeNameWord() {
		return headerEmployeeNameWord;
	}

	/**
	 * �w�b�_�[�F���͎҂̐ݒ�
	 * 
	 * @param headerEmployeeNameWord �w�b�_�[�F���͎�
	 */
	public void setHeaderEmployeeNameWord(String headerEmployeeNameWord) {
		this.headerEmployeeNameWord = headerEmployeeNameWord;
	}

	/**
	 * �w�b�_�[�F�`�[�ԍ��̎擾
	 * 
	 * @return headerSlipNoWord �w�b�_�[�F�`�[�ԍ�
	 */
	public String getHeaderSlipNoWord() {
		return headerSlipNoWord;
	}

	/**
	 * �w�b�_�[�F�`�[�ԍ��̐ݒ�
	 * 
	 * @param headerSlipNoWord �w�b�_�[�F�`�[�ԍ�
	 */
	public void setHeaderSlipNoWord(String headerSlipNoWord) {
		this.headerSlipNoWord = headerSlipNoWord;
	}

	/**
	 * �w�b�_�[�F�C���̎擾
	 * 
	 * @return headerUpdateWord �w�b�_�[�F�C��
	 */
	public String getHeaderUpdateWord() {
		return headerUpdateWord;
	}

	/**
	 * �w�b�_�[�F�C���̐ݒ�
	 * 
	 * @param headerUpdateWord �w�b�_�[�F�C��
	 */
	public void setHeaderUpdateWord(String headerUpdateWord) {
		this.headerUpdateWord = headerUpdateWord;
	}

	/**
	 * �w�b�_�[�F��̎擾
	 * 
	 * @return headerUpdateCountWord �w�b�_�[�F��
	 */
	public String getHeaderUpdateCountWord() {
		return headerUpdateCountWord;
	}

	/**
	 * �w�b�_�[�F��̐ݒ�
	 * 
	 * @param headerUpdateCountWord �w�b�_�[�F��
	 */
	public void setHeaderUpdateCountWord(String headerUpdateCountWord) {
		this.headerUpdateCountWord = headerUpdateCountWord;
	}

	/**
	 * �w�b�_�[�F�؜ߔԍ��̎擾
	 * 
	 * @return headerVoucherNoWord �w�b�_�[�F�؜ߔԍ�
	 */
	public String getHeaderVoucherNoWord() {
		return headerVoucherNoWord;
	}

	/**
	 * �w�b�_�[�F�؜ߔԍ��̐ݒ�
	 * 
	 * @param headerVoucherNoWord �w�b�_�[�F�؜ߔԍ�
	 */
	public void setHeaderVoucherNoWord(String headerVoucherNoWord) {
		this.headerVoucherNoWord = headerVoucherNoWord;
	}

	/**
	 * �w�b�_�[�F�`�[�E�v�̎擾
	 * 
	 * @return headerSlipRemarkWord �w�b�_�[�F�`�[�E�v
	 */
	public String getHeaderSlipRemarkWord() {
		return headerSlipRemarkWord;
	}

	/**
	 * �w�b�_�[�F�`�[�E�v�̐ݒ�
	 * 
	 * @param headerSlipRemarkWord �w�b�_�[�F�`�[�E�v
	 */
	public void setHeaderSlipRemarkWord(String headerSlipRemarkWord) {
		this.headerSlipRemarkWord = headerSlipRemarkWord;
	}

	/**
	 * �w�b�_�[�F��ʉ݂̎擾
	 * 
	 * @return headerKeyCurrencyWord �w�b�_�[�F��ʉ�
	 */
	public String getHeaderKeyCurrencyWord() {
		return headerKeyCurrencyWord;
	}

	/**
	 * �w�b�_�[�F��ʉ݂̐ݒ�
	 * 
	 * @param headerKeyCurrencyWord �w�b�_�[�F��ʉ�
	 */
	public void setHeaderKeyCurrencyWord(String headerKeyCurrencyWord) {
		this.headerKeyCurrencyWord = headerKeyCurrencyWord;
	}

	/**
	 * �w�b�_�[�F����敪�̎擾
	 * 
	 * @return headerAccountBookNameWord �w�b�_�[�F����敪
	 */
	public String getHeaderAccountBookNameWord() {
		return headerAccountBookNameWord;
	}

	/**
	 * �w�b�_�[�F����敪�̐ݒ�
	 * 
	 * @param headerAccountBookNameWord �w�b�_�[�F����敪
	 */
	public void setHeaderAccountBookNameWord(String headerAccountBookNameWord) {
		this.headerAccountBookNameWord = headerAccountBookNameWord;
	}

	/**
	 * �w�b�_�[�F���F��1�̎擾
	 * 
	 * @return headerAcceptStamp1Word �w�b�_�[�F���F��1
	 */
	public String getHeaderAcceptStamp1Word() {
		return headerAcceptStamp1Word;
	}

	/**
	 * �w�b�_�[�F���F��1�̐ݒ�
	 * 
	 * @param headerAcceptStamp1Word �w�b�_�[�F���F��1
	 */
	public void setHeaderAcceptStamp1Word(String headerAcceptStamp1Word) {
		this.headerAcceptStamp1Word = headerAcceptStamp1Word;
	}

	/**
	 * �w�b�_�[�F���F��2�̎擾
	 * 
	 * @return headerAcceptStamp2Word �w�b�_�[�F���F��2
	 */
	public String getHeaderAcceptStamp2Word() {
		return headerAcceptStamp2Word;
	}

	/**
	 * �w�b�_�[�F���F��2�̐ݒ�
	 * 
	 * @param headerAcceptStamp2Word �w�b�_�[�F���F��2
	 */
	public void setHeaderAcceptStamp2Word(String headerAcceptStamp2Word) {
		this.headerAcceptStamp2Word = headerAcceptStamp2Word;
	}

	/**
	 * �w�b�_�[�F���F��3�̎擾
	 * 
	 * @return headerAcceptStamp3Word �w�b�_�[�F���F��3
	 */
	public String getHeaderAcceptStamp3Word() {
		return headerAcceptStamp3Word;
	}

	/**
	 * �w�b�_�[�F���F��3�̐ݒ�
	 * 
	 * @param headerAcceptStamp3Word �w�b�_�[�F���F��3
	 */
	public void setHeaderAcceptStamp3Word(String headerAcceptStamp3Word) {
		this.headerAcceptStamp3Word = headerAcceptStamp3Word;
	}

	/**
	 * ���ׁF�ؕ����z�̎擾
	 * 
	 * @return detailDrAmountWord ���ׁF�ؕ����z
	 */
	public String getDetailDrAmountWord() {
		return detailDrAmountWord;
	}

	/**
	 * ���ׁF�ؕ����z�̐ݒ�
	 * 
	 * @param detailDrAmountWord ���ׁF�ؕ����z
	 */
	public void setDetailDrAmountWord(String detailDrAmountWord) {
		this.detailDrAmountWord = detailDrAmountWord;
	}

	/**
	 * ���ׁF�ݕ����z�̎擾
	 * 
	 * @return detailCrAmountWord ���ׁF�ݕ����z
	 */
	public String getDetailCrAmountWord() {
		return detailCrAmountWord;
	}

	/**
	 * ���ׁF�ݕ����z�̐ݒ�
	 * 
	 * @param detailCrAmountWord ���ׁF�ݕ����z
	 */
	public void setDetailCrAmountWord(String detailCrAmountWord) {
		this.detailCrAmountWord = detailCrAmountWord;
	}

	/**
	 * ���ׁF�s�E�v�̎擾
	 * 
	 * @return detailRowRemarkWord ���ׁF�s�E�v
	 */
	public String getDetailRowRemarkWord() {
		return detailRowRemarkWord;
	}

	/**
	 * ���ׁF�s�E�v�̐ݒ�
	 * 
	 * @param detailRowRemarkWord ���ׁF�s�E�v
	 */
	public void setDetailRowRemarkWord(String detailRowRemarkWord) {
		this.detailRowRemarkWord = detailRowRemarkWord;
	}

	/**
	 * ���ׁF�v�㕔��/�����/�Ј��̎擾
	 * 
	 * @return detailCustomerWord ���ׁF�v�㕔��/�����/�Ј�
	 */
	public String getDetailCustomerWord() {
		return detailCustomerWord;
	}

	/**
	 * ���ׁF�v�㕔��/�����/�Ј��̐ݒ�
	 * 
	 * @param detailCustomerWord ���ׁF�v�㕔��/�����/�Ј�
	 */
	public void setDetailCustomerWord(String detailCustomerWord) {
		this.detailCustomerWord = detailCustomerWord;
	}

	/**
	 * ���ׁF�ʉ݂̎擾
	 * 
	 * @return detailCurrencyWord ���ׁF�ʉ�
	 */
	public String getDetailCurrencyWord() {
		return detailCurrencyWord;
	}

	/**
	 * ���ׁF�ʉ݂̐ݒ�
	 * 
	 * @param detailCurrencyWord ���ׁF�ʉ�
	 */
	public void setDetailCurrencyWord(String detailCurrencyWord) {
		this.detailCurrencyWord = detailCurrencyWord;
	}

	/**
	 * ���ׁF������/�ł̎擾
	 * 
	 * @return detailTaxWord ���ׁF������/��
	 */
	public String getDetailTaxWord() {
		return detailTaxWord;
	}

	/**
	 * ���ׁF������/�ł̐ݒ�
	 * 
	 * @param detailTaxWord ���ׁF������/��
	 */
	public void setDetailTaxWord(String detailTaxWord) {
		this.detailTaxWord = detailTaxWord;
	}

	/**
	 * ���ׁF�Ǘ�1/2/3�̎擾
	 * 
	 * @return detailManagement1To3Word ���ׁF�Ǘ�1/2/3
	 */
	public String getDetailManagement1To3Word() {
		return detailManagement1To3Word;
	}

	/**
	 * ���ׁF�Ǘ�1/2/3�̐ݒ�
	 * 
	 * @param detailManagement1To3Word ���ׁF�Ǘ�1/2/3
	 */
	public void setDetailManagement1To3Word(String detailManagement1To3Word) {
		this.detailManagement1To3Word = detailManagement1To3Word;
	}

	/**
	 * ���ׁF�Ǘ�4/5/6�̎擾
	 * 
	 * @return detailManagement4To6Word ���ׁF�Ǘ�4/5/6
	 */
	public String getDetailManagement4To6Word() {
		return detailManagement4To6Word;
	}

	/**
	 * ���ׁF�Ǘ�4/5/6�̐ݒ�
	 * 
	 * @param detailManagement4To6Word ���ׁF�Ǘ�4/5/6
	 */
	public void setDetailManagement4To6Word(String detailManagement4To6Word) {
		this.detailManagement4To6Word = detailManagement4To6Word;
	}

	/**
	 * ���ׁF���v����1/2/3�̎擾
	 * 
	 * @return detailNonAccountWord ���ׁF���v����1/2/3
	 */
	public String getDetailNonAccountWord() {
		return detailNonAccountWord;
	}

	/**
	 * ���ׁF���v����1/2/3�̐ݒ�
	 * 
	 * @param detailNonAccountWord ���ׁF���v����1/2/3
	 */
	public void setDetailNonAccountWord(String detailNonAccountWord) {
		this.detailNonAccountWord = detailNonAccountWord;
	}

	/**
	 * ���ׁF���v�̎擾
	 * 
	 * @return detailSumWord ���ׁF���v
	 */
	public String getDetailSumWord() {
		return detailSumWord;
	}

	/**
	 * ���ׁF���v�̐ݒ�
	 * 
	 * @param detailSumWord ���ׁF���v
	 */
	public void setDetailSumWord(String detailSumWord) {
		this.detailSumWord = detailSumWord;
	}

	/**
	 * ���ׁF�O�݌v1�̎擾
	 * 
	 * @return detailForeign1Word ���ׁF�O�݌v1
	 */
	public String getDetailForeign1Word() {
		return detailForeign1Word;
	}

	/**
	 * ���ׁF�O�݌v1�̐ݒ�
	 * 
	 * @param detailForeign1Word ���ׁF�O�݌v1
	 */
	public void setDetailForeign1Word(String detailForeign1Word) {
		this.detailForeign1Word = detailForeign1Word;
	}

	/**
	 * ���ׁF�O�݌v2�̎擾
	 * 
	 * @return detailForeign2Word ���ׁF�O�݌v2
	 */
	public String getDetailForeign2Word() {
		return detailForeign2Word;
	}

	/**
	 * ���ׁF�O�݌v2�̐ݒ�
	 * 
	 * @param detailForeign2Word ���ׁF�O�݌v2
	 */
	public void setDetailForeign2Word(String detailForeign2Word) {
		this.detailForeign2Word = detailForeign2Word;
	}

	/**
	 * ���ׁF�O�݌v3�̎擾
	 * 
	 * @return detailForeign3Word ���ׁF�O�݌v3
	 */
	public String getDetailForeign3Word() {
		return detailForeign3Word;
	}

	/**
	 * ���ׁF�O�݌v3�̐ݒ�
	 * 
	 * @param detailForeign3Word ���ׁF�O�݌v3
	 */
	public void setDetailForeign3Word(String detailForeign3Word) {
		this.detailForeign3Word = detailForeign3Word;
	}

	/**
	 * ���ׁF�O�݌v4�̎擾
	 * 
	 * @return detailForeign4Word ���ׁF�O�݌v4
	 */
	public String getDetailForeign4Word() {
		return detailForeign4Word;
	}

	/**
	 * ���ׁF�O�݌v4�̐ݒ�
	 * 
	 * @param detailForeign4Word ���ׁF�O�݌v4
	 */
	public void setDetailForeign4Word(String detailForeign4Word) {
		this.detailForeign4Word = detailForeign4Word;
	}

	/**
	 * �s�����̎擾<br>
	 * �ύX�ɂ�郌�C�A�E�g�����K�v
	 * 
	 * @return rowHeight �s����
	 */
	public double getRowHeight() {
		return rowHeight;
	}

	/**
	 * �s�����̐ݒ�<br>
	 * �ύX�ɂ�郌�C�A�E�g�����K�v
	 * 
	 * @param rowHeight �s����
	 */
	public void setRowHeight(double rowHeight) {
		this.rowHeight = rowHeight;
	}

	/**
	 * �w�b�_�[�^�C�g�����̎擾
	 * 
	 * @return headerTitleWidth �w�b�_�[�^�C�g����
	 */
	public double getHeaderTitleWidth() {
		return headerTitleWidth;
	}

	/**
	 * �w�b�_�[�^�C�g�����̐ݒ�
	 * 
	 * @param headerTitleWidth �w�b�_�[�^�C�g����
	 */
	public void setHeaderTitleWidth(double headerTitleWidth) {
		this.headerTitleWidth = headerTitleWidth;
	}

	/**
	 * �w�b�_�[���啝�̎擾
	 * 
	 * @return headerDepartmentWidth �w�b�_�[���啝
	 */
	public double getHeaderDepartmentWidth() {
		return headerDepartmentWidth;
	}

	/**
	 * �w�b�_�[���啝�̐ݒ�
	 * 
	 * @param headerDepartmentWidth �w�b�_�[���啝
	 */
	public void setHeaderDepartmentWidth(double headerDepartmentWidth) {
		this.headerDepartmentWidth = headerDepartmentWidth;
	}

	/**
	 * ��s���̎擾
	 * 
	 * @return detailRestWidth ��s��
	 */
	public double getDetailRestWidth() {
		return detailRestWidth;
	}

	/**
	 * ��s���̐ݒ�
	 * 
	 * @param detailRestWidth ��s��
	 */
	public void setDetailRestWidth(double detailRestWidth) {
		this.detailRestWidth = detailRestWidth;
	}

	/**
	 * �s�ԍ����̎擾
	 * 
	 * @return detailRowNoWidth �s�ԍ���
	 */
	public double getDetailRowNoWidth() {
		return detailRowNoWidth;
	}

	/**
	 * �s�ԍ����̐ݒ�
	 * 
	 * @param detailRowNoWidth �s�ԍ���
	 */
	public void setDetailRowNoWidth(double detailRowNoWidth) {
		this.detailRowNoWidth = detailRowNoWidth;
	}

	/**
	 * �Ȗڕ��̎擾
	 * 
	 * @return detailItemWidth �Ȗڕ�
	 */
	public double getDetailItemWidth() {
		return detailItemWidth;
	}

	/**
	 * �Ȗڕ��̐ݒ�
	 * 
	 * @param detailItemWidth �Ȗڕ�
	 */
	public void setDetailItemWidth(double detailItemWidth) {
		this.detailItemWidth = detailItemWidth;
	}

	/**
	 * ���z���̎擾
	 * 
	 * @return detailAmountWidth ���z��
	 */
	public double getDetailAmountWidth() {
		return detailAmountWidth;
	}

	/**
	 * ���z���̐ݒ�
	 * 
	 * @param detailAmountWidth ���z��
	 */
	public void setDetailAmountWidth(double detailAmountWidth) {
		this.detailAmountWidth = detailAmountWidth;
	}

	/**
	 * �ʉݕ��̎擾
	 * 
	 * @return detailCurrencyWidth �ʉݕ�
	 */
	public double getDetailCurrencyWidth() {
		return detailCurrencyWidth;
	}

	/**
	 * �ʉݕ��̐ݒ�
	 * 
	 * @param detailCurrencyWidth �ʉݕ�
	 */
	public void setDetailCurrencyWidth(double detailCurrencyWidth) {
		this.detailCurrencyWidth = detailCurrencyWidth;
	}

	/**
	 * �E�v���̎擾
	 * 
	 * @return detailRemarkWidth �E�v��
	 */
	public double getDetailRemarkWidth() {
		return detailRemarkWidth;
	}

	/**
	 * �E�v���̐ݒ�
	 * 
	 * @param detailRemarkWidth �E�v��
	 */
	public void setDetailRemarkWidth(double detailRemarkWidth) {
		this.detailRemarkWidth = detailRemarkWidth;
	}

	/**
	 * ����敝�̎擾
	 * 
	 * @return detailCustomerWidth ����敝
	 */
	public double getDetailCustomerWidth() {
		return detailCustomerWidth;
	}

	/**
	 * ����敝�̐ݒ�
	 * 
	 * @param detailCustomerWidth ����敝
	 */
	public void setDetailCustomerWidth(double detailCustomerWidth) {
		this.detailCustomerWidth = detailCustomerWidth;
	}

	/**
	 * �Ǘ�1�`3���̎擾
	 * 
	 * @return detailManagement1To3Width �Ǘ�1�`3��
	 */
	public double getDetailManagement1To3Width() {
		return detailManagement1To3Width;
	}

	/**
	 * �Ǘ�1�`3���̐ݒ�
	 * 
	 * @param detailManagement1To3Width �Ǘ�1�`3��
	 */
	public void setDetailManagement1To3Width(double detailManagement1To3Width) {
		this.detailManagement1To3Width = detailManagement1To3Width;
	}

	/**
	 * �Ǘ�4�`6���̎擾
	 * 
	 * @return detailManagement4To6Width �Ǘ�4�`6��
	 */
	public double getDetailManagement4To6Width() {
		return detailManagement4To6Width;
	}

	/**
	 * �Ǘ�4�`6���̐ݒ�
	 * 
	 * @param detailManagement4To6Width �Ǘ�4�`6��
	 */
	public void setDetailManagement4To6Width(double detailManagement4To6Width) {
		this.detailManagement4To6Width = detailManagement4To6Width;
	}

	/**
	 * ���v���ו��̎擾
	 * 
	 * @return detailNonAccountWidth ���v���ו�
	 */
	public double getDetailNonAccountWidth() {
		return detailNonAccountWidth;
	}

	/**
	 * ���v���ו��̐ݒ�
	 * 
	 * @param detailNonAccountWidth ���v���ו�
	 */
	public void setDetailNonAccountWidth(double detailNonAccountWidth) {
		this.detailNonAccountWidth = detailNonAccountWidth;
	}

	/**
	 * ����敪�����̎擾
	 * 
	 * @return headerAccountBookNameLength ����敪����
	 */
	public int getHeaderAccountBookNameLength() {
		return headerAccountBookNameLength;
	}

	/**
	 * ����敪�����̐ݒ�
	 * 
	 * @param headerAccountBookNameLength ����敪����
	 */
	public void setHeaderAccountBookNameLength(int headerAccountBookNameLength) {
		this.headerAccountBookNameLength = headerAccountBookNameLength;
	}

	/**
	 * �C���񐔌����̎擾
	 * 
	 * @return headerUpdateCountLength �C���񐔌���
	 */
	public int getHeaderUpdateCountLength() {
		return headerUpdateCountLength;
	}

	/**
	 * �C���񐔌����̐ݒ�
	 * 
	 * @param headerUpdateCountLength �C���񐔌���
	 */
	public void setHeaderUpdateCountLength(int headerUpdateCountLength) {
		this.headerUpdateCountLength = headerUpdateCountLength;
	}

	/**
	 * ���F��1�����̎擾
	 * 
	 * @return headerAcceptStamp1Length ���F��1����
	 */
	public int getHeaderAcceptStamp1Length() {
		return headerAcceptStamp1Length;
	}

	/**
	 * ���F��1�����̐ݒ�
	 * 
	 * @param headerAcceptStamp1Length ���F��1����
	 */
	public void setHeaderAcceptStamp1Length(int headerAcceptStamp1Length) {
		this.headerAcceptStamp1Length = headerAcceptStamp1Length;
	}

	/**
	 * ���F��2�����̎擾
	 * 
	 * @return headerAcceptStamp2Length ���F��2����
	 */
	public int getHeaderAcceptStamp2Length() {
		return headerAcceptStamp2Length;
	}

	/**
	 * ���F��2�����̐ݒ�
	 * 
	 * @param headerAcceptStamp2Length ���F��2����
	 */
	public void setHeaderAcceptStamp2Length(int headerAcceptStamp2Length) {
		this.headerAcceptStamp2Length = headerAcceptStamp2Length;
	}

	/**
	 * ���F��3�����̎擾
	 * 
	 * @return headerAcceptStamp3Length ���F��3����
	 */
	public int getHeaderAcceptStamp3Length() {
		return headerAcceptStamp3Length;
	}

	/**
	 * ���F��3�����̐ݒ�
	 * 
	 * @param headerAcceptStamp3Length ���F��3����
	 */
	public void setHeaderAcceptStamp3Length(int headerAcceptStamp3Length) {
		this.headerAcceptStamp3Length = headerAcceptStamp3Length;
	}

	/**
	 * �Ȗڌ����̎擾
	 * 
	 * @return detailItemLength �Ȗڌ���
	 */
	public int getDetailItemLength() {
		return detailItemLength;
	}

	/**
	 * �Ȗڌ����̐ݒ�
	 * 
	 * @param detailItemLength �Ȗڌ���
	 */
	public void setDetailItemLength(int detailItemLength) {
		this.detailItemLength = detailItemLength;
	}

	/**
	 * ����Ō����̎擾
	 * 
	 * @return detailTaxLength ����Ō���
	 */
	public int getDetailTaxLength() {
		return detailTaxLength;
	}

	/**
	 * ����Ō����̐ݒ�
	 * 
	 * @param detailTaxLength ����Ō���
	 */
	public void setDetailTaxLength(int detailTaxLength) {
		this.detailTaxLength = detailTaxLength;
	}

	/**
	 * �E�v�����̎擾
	 * 
	 * @return detailRemarkLength �E�v����
	 */
	public int getDetailRemarkLength() {
		return detailRemarkLength;
	}

	/**
	 * �E�v�����̐ݒ�
	 * 
	 * @param detailRemarkLength �E�v����
	 */
	public void setDetailRemarkLength(int detailRemarkLength) {
		this.detailRemarkLength = detailRemarkLength;
	}

	/**
	 * ����挅���̎擾
	 * 
	 * @return detailCustomerLength ����挅��
	 */
	public int getDetailCustomerLength() {
		return detailCustomerLength;
	}

	/**
	 * ����挅���̐ݒ�
	 * 
	 * @param detailCustomerLength ����挅��
	 */
	public void setDetailCustomerLength(int detailCustomerLength) {
		this.detailCustomerLength = detailCustomerLength;
	}

	/**
	 * �Ǘ�1�`3�����̎擾
	 * 
	 * @return detailManagement1To3Length �Ǘ�1�`3����
	 */
	public int getDetailManagement1To3Length() {
		return detailManagement1To3Length;
	}

	/**
	 * �Ǘ�1�`3�����̐ݒ�
	 * 
	 * @param detailManagement1To3Length �Ǘ�1�`3����
	 */
	public void setDetailManagement1To3Length(int detailManagement1To3Length) {
		this.detailManagement1To3Length = detailManagement1To3Length;
	}

	/**
	 * �Ǘ�4�`6�����̎擾
	 * 
	 * @return detailManagement4To6Length �Ǘ�4�`6����
	 */
	public int getDetailManagement4To6Length() {
		return detailManagement4To6Length;
	}

	/**
	 * �Ǘ�4�`6�����̐ݒ�
	 * 
	 * @param detailManagement4To6Length �Ǘ�4�`6����
	 */
	public void setDetailManagement4To6Length(int detailManagement4To6Length) {
		this.detailManagement4To6Length = detailManagement4To6Length;
	}

	/**
	 * ���v���׌����̎擾
	 * 
	 * @return detailNonAccountLength ���v���׌���
	 */
	public int getDetailNonAccountLength() {
		return detailNonAccountLength;
	}

	/**
	 * ���v���׌����̐ݒ�
	 * 
	 * @param detailNonAccountLength ���v���׌���
	 */
	public void setDetailNonAccountLength(int detailNonAccountLength) {
		this.detailNonAccountLength = detailNonAccountLength;
	}

	/**
	 * �w�b�_�[�F���͕��包���̎擾
	 * 
	 * @return headerDepartmentNameLength �w�b�_�[�F���͕��包��
	 */
	public int getHeaderDepartmentNameLength() {
		return headerDepartmentNameLength;
	}

	/**
	 * �w�b�_�[�F���͕��包���̐ݒ�
	 * 
	 * @param headerDepartmentNameLength �w�b�_�[�F���͕��包��
	 */
	public void setHeaderDepartmentNameLength(int headerDepartmentNameLength) {
		this.headerDepartmentNameLength = headerDepartmentNameLength;
	}

	/**
	 * �w�b�_�[�F���o���`�[�̃��[�g���̎擾
	 * 
	 * @return headerRateWidth �w�b�_�[�F���o���`�[�̃��[�g��
	 */
	public double getHeaderRateWidth() {
		return headerRateWidth;
	}

	/**
	 * �w�b�_�[�F���o���`�[�̃��[�g���̐ݒ�
	 * 
	 * @param headerRateWidth �w�b�_�[�F���o���`�[�̃��[�g��
	 */
	public void setHeaderRateWidth(double headerRateWidth) {
		this.headerRateWidth = headerRateWidth;
	}

	/**
	 * �w�b�_�[�F���o���`�[�̋��z���̎擾
	 * 
	 * @return headerAmountWidth �w�b�_�[�F���o���`�[�̋��z��
	 */
	public double getHeaderAmountWidth() {
		return headerAmountWidth;
	}

	/**
	 * �w�b�_�[�F���o���`�[�̋��z���̐ݒ�
	 * 
	 * @param headerAmountWidth �w�b�_�[�F���o���`�[�̋��z��
	 */
	public void setHeaderAmountWidth(double headerAmountWidth) {
		this.headerAmountWidth = headerAmountWidth;
	}

	/**
	 * �w�b�_�[�F���o���`�[�̒ʉݕ��̎擾
	 * 
	 * @return headerCurrencyWidth �w�b�_�[�F���o���`�[�̒ʉݕ�
	 */
	public double getHeaderCurrencyWidth() {
		return headerCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F���o���`�[�̒ʉݕ��̐ݒ�
	 * 
	 * @param headerCurrencyWidth �w�b�_�[�F���o���`�[�̒ʉݕ�
	 */
	public void setHeaderCurrencyWidth(double headerCurrencyWidth) {
		this.headerCurrencyWidth = headerCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F���v��̃��[�g���̎擾
	 * 
	 * @return headerPayRateWidth �w�b�_�[�F���v��̃��[�g��
	 */
	public double getHeaderPayRateWidth() {
		return headerPayRateWidth;
	}

	/**
	 * �w�b�_�[�F���v��̃��[�g���̐ݒ�
	 * 
	 * @param headerPayRateWidth �w�b�_�[�F���v��̃��[�g��
	 */
	public void setHeaderPayRateWidth(double headerPayRateWidth) {
		this.headerPayRateWidth = headerPayRateWidth;
	}

	/**
	 * �w�b�_�[�F���v��̋��z���̎擾
	 * 
	 * @return headerPayAmountWidth �w�b�_�[�F���v��̋��z��
	 */
	public double getHeaderPayAmountWidth() {
		return headerPayAmountWidth;
	}

	/**
	 * �w�b�_�[�F���v��̋��z���̐ݒ�
	 * 
	 * @param headerPayAmountWidth �w�b�_�[�F���v��̋��z��
	 */
	public void setHeaderPayAmountWidth(double headerPayAmountWidth) {
		this.headerPayAmountWidth = headerPayAmountWidth;
	}

	/**
	 * �w�b�_�[�F���v��̒ʉݕ��̎擾
	 * 
	 * @return headerPayCurrencyWidth �w�b�_�[�F���v��̒ʉݕ�
	 */
	public double getHeaderPayCurrencyWidth() {
		return headerPayCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F���v��̒ʉݕ��̐ݒ�
	 * 
	 * @param headerPayCurrencyWidth �w�b�_�[�F���v��̒ʉݕ�
	 */
	public void setHeaderPayCurrencyWidth(double headerPayCurrencyWidth) {
		this.headerPayCurrencyWidth = headerPayCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F���v��̎x�������̎擾
	 * 
	 * @return headerPayDateWidth �w�b�_�[�F���v��̎x������
	 */
	public double getHeaderPayDateWidth() {
		return headerPayDateWidth;
	}

	/**
	 * �w�b�_�[�F���v��̎x�������̐ݒ�
	 * 
	 * @param headerPayDateWidth �w�b�_�[�F���v��̎x������
	 */
	public void setHeaderPayDateWidth(double headerPayDateWidth) {
		this.headerPayDateWidth = headerPayDateWidth;
	}

	/**
	 * �w�b�_�[�F���v��̎x���敪���̎擾
	 * 
	 * @return headerPayDateAndTypeWidth �w�b�_�[�F���v��̎x���敪��
	 */
	public double getHeaderPayDateAndTypeWidth() {
		return headerPayDateAndTypeWidth;
	}

	/**
	 * �w�b�_�[�F���v��̎x���敪���̐ݒ�
	 * 
	 * @param headerPayDateAndTypeWidth �w�b�_�[�F���v��̎x���敪��
	 */
	public void setHeaderPayDateAndTypeWidth(double headerPayDateAndTypeWidth) {
		this.headerPayDateAndTypeWidth = headerPayDateAndTypeWidth;
	}

	/**
	 * �w�b�_�[�F���v��̃��[�g���̎擾
	 * 
	 * @return headerSaleRateWidth �w�b�_�[�F���v��̃��[�g��
	 */
	public double getHeaderSaleRateWidth() {
		return headerSaleRateWidth;
	}

	/**
	 * �w�b�_�[�F���v��̃��[�g���̐ݒ�
	 * 
	 * @param headerSaleRateWidth �w�b�_�[�F���v��̃��[�g��
	 */
	public void setHeaderSaleRateWidth(double headerSaleRateWidth) {
		this.headerSaleRateWidth = headerSaleRateWidth;
	}

	/**
	 * �w�b�_�[�F���v��̋��z���̎擾
	 * 
	 * @return headerSaleAmountWidth �w�b�_�[�F���v��̋��z��
	 */
	public double getHeaderSaleAmountWidth() {
		return headerSaleAmountWidth;
	}

	/**
	 * �w�b�_�[�F���v��̋��z���̐ݒ�
	 * 
	 * @param headerSaleAmountWidth �w�b�_�[�F���v��̋��z��
	 */
	public void setHeaderSaleAmountWidth(double headerSaleAmountWidth) {
		this.headerSaleAmountWidth = headerSaleAmountWidth;
	}

	/**
	 * �w�b�_�[�F���v��̒ʉݕ��̎擾
	 * 
	 * @return headerSaleCurrencyWidth �w�b�_�[�F���v��̒ʉݕ�
	 */
	public double getHeaderSaleCurrencyWidth() {
		return headerSaleCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F���v��̒ʉݕ��̐ݒ�
	 * 
	 * @param headerSaleCurrencyWidth �w�b�_�[�F���v��̒ʉݕ�
	 */
	public void setHeaderSaleCurrencyWidth(double headerSaleCurrencyWidth) {
		this.headerSaleCurrencyWidth = headerSaleCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F�o��Z�̃��[�g���̎擾
	 * 
	 * @return headerSettlementRateWidth �w�b�_�[�F�o��Z�̃��[�g��
	 */
	public double getHeaderSettlementRateWidth() {
		return headerSettlementRateWidth;
	}

	/**
	 * �w�b�_�[�F�o��Z�̃��[�g���̐ݒ�
	 * 
	 * @param headerSettlementRateWidth �w�b�_�[�F�o��Z�̃��[�g��
	 */
	public void setHeaderSettlementRateWidth(double headerSettlementRateWidth) {
		this.headerSettlementRateWidth = headerSettlementRateWidth;
	}

	/**
	 * �w�b�_�[�F�o��Z�̋��z���̎擾
	 * 
	 * @return headerSettlementAmountWidth �w�b�_�[�F�o��Z�̋��z��
	 */
	public double getHeaderSettlementAmountWidth() {
		return headerSettlementAmountWidth;
	}

	/**
	 * �w�b�_�[�F�o��Z�̋��z���̐ݒ�
	 * 
	 * @param headerSettlementAmountWidth �w�b�_�[�F�o��Z�̋��z��
	 */
	public void setHeaderSettlementAmountWidth(double headerSettlementAmountWidth) {
		this.headerSettlementAmountWidth = headerSettlementAmountWidth;
	}

	/**
	 * �w�b�_�[�F�o��Z�̒ʉݕ��̎擾
	 * 
	 * @return headerSettlementCurrencyWidth �w�b�_�[�F�o��Z�̒ʉݕ�
	 */
	public double getHeaderSettlementCurrencyWidth() {
		return headerSettlementCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F�o��Z�̒ʉݕ��̐ݒ�
	 * 
	 * @param headerSettlementCurrencyWidth �w�b�_�[�F�o��Z�̒ʉݕ�
	 */
	public void setHeaderSettlementCurrencyWidth(double headerSettlementCurrencyWidth) {
		this.headerSettlementCurrencyWidth = headerSettlementCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F�Ј������̃��[�g���̎擾
	 * 
	 * @return headerTempRateWidth �w�b�_�[�F�Ј������̃��[�g��
	 */
	public double getHeaderTempRateWidth() {
		return headerTempRateWidth;
	}

	/**
	 * �w�b�_�[�F�Ј������̃��[�g���̐ݒ�
	 * 
	 * @param headerTempRateWidth �w�b�_�[�F�Ј������̃��[�g��
	 */
	public void setHeaderTempRateWidth(double headerTempRateWidth) {
		this.headerTempRateWidth = headerTempRateWidth;
	}

	/**
	 * �w�b�_�[�F�Ј������̋��z���̎擾
	 * 
	 * @return headerTempAmountWidth �w�b�_�[�F�Ј������̋��z��
	 */
	public double getHeaderTempAmountWidth() {
		return headerTempAmountWidth;
	}

	/**
	 * �w�b�_�[�F�Ј������̋��z���̐ݒ�
	 * 
	 * @param headerTempAmountWidth �w�b�_�[�F�Ј������̋��z��
	 */
	public void setHeaderTempAmountWidth(double headerTempAmountWidth) {
		this.headerTempAmountWidth = headerTempAmountWidth;
	}

	/**
	 * �w�b�_�[�F�Ј������̒ʉݕ��̎擾
	 * 
	 * @return headerTempCurrencyWidth �w�b�_�[�F�Ј������̒ʉݕ�
	 */
	public double getHeaderTempCurrencyWidth() {
		return headerTempCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F�Ј������̒ʉݕ��̐ݒ�
	 * 
	 * @param headerTempCurrencyWidth �w�b�_�[�F�Ј������̒ʉݕ�
	 */
	public void setHeaderTempCurrencyWidth(double headerTempCurrencyWidth) {
		this.headerTempCurrencyWidth = headerTempCurrencyWidth;
	}

	/**
	 * �w�b�_�[�F�����`�[�̓������z�̎擾
	 * 
	 * @return headerInputCashflowAmountWord �w�b�_�[�F�����`�[�̓������z
	 */
	public String getHeaderInputCashflowAmountWord() {
		return headerInputCashflowAmountWord;
	}

	/**
	 * �w�b�_�[�F�����`�[�̓������z�̐ݒ�
	 * 
	 * @param headerInputCashflowAmountWord �w�b�_�[�F�����`�[�̓������z
	 */
	public void setHeaderInputCashflowAmountWord(String headerInputCashflowAmountWord) {
		this.headerInputCashflowAmountWord = headerInputCashflowAmountWord;
	}

	/**
	 * �w�b�_�[�F�o���`�[�̏o�����z�̎擾
	 * 
	 * @return headerOutputCashflowAmountWord �w�b�_�[�F�o���`�[�̏o�����z
	 */
	public String getHeaderOutputCashflowAmountWord() {
		return headerOutputCashflowAmountWord;
	}

	/**
	 * �w�b�_�[�F�o���`�[�̏o�����z�̐ݒ�
	 * 
	 * @param headerOutputCashflowAmountWord �w�b�_�[�F�o���`�[�̏o�����z
	 */
	public void setHeaderOutputCashflowAmountWord(String headerOutputCashflowAmountWord) {
		this.headerOutputCashflowAmountWord = headerOutputCashflowAmountWord;
	}

	/**
	 * �w�b�_�[�F���v��̎x����̎擾
	 * 
	 * @return headerPayCustomerWord �w�b�_�[�F���v��̎x����
	 */
	public String getHeaderPayCustomerWord() {
		return headerPayCustomerWord;
	}

	/**
	 * �w�b�_�[�F���v��̎x����̐ݒ�
	 * 
	 * @param headerPayCustomerWord �w�b�_�[�F���v��̎x����
	 */
	public void setHeaderPayCustomerWord(String headerPayCustomerWord) {
		this.headerPayCustomerWord = headerPayCustomerWord;
	}

	/**
	 * �w�b�_�[�F���v��̎x����/�x�����@�̎擾
	 * 
	 * @return headerPayDateAndTypeWord �w�b�_�[�F���v��̎x����/�x�����@
	 */
	public String getHeaderPayDateAndTypeWord() {
		return headerPayDateAndTypeWord;
	}

	/**
	 * �w�b�_�[�F���v��̎x����/�x�����@�̐ݒ�
	 * 
	 * @param headerPayDateAndTypeWord �w�b�_�[�F���v��̎x����/�x�����@
	 */
	public void setHeaderPayDateAndTypeWord(String headerPayDateAndTypeWord) {
		this.headerPayDateAndTypeWord = headerPayDateAndTypeWord;
	}

	/**
	 * �w�b�_�[�F���v��̎x�������̎擾
	 * 
	 * @return headerPayAccountWord �w�b�_�[�F���v��̎x������
	 */
	public String getHeaderPayAccountWord() {
		return headerPayAccountWord;
	}

	/**
	 * �w�b�_�[�F���v��̎x�������̐ݒ�
	 * 
	 * @param headerPayAccountWord �w�b�_�[�F���v��̎x������
	 */
	public void setHeaderPayAccountWord(String headerPayAccountWord) {
		this.headerPayAccountWord = headerPayAccountWord;
	}

	/**
	 * �w�b�_�[�F���v��̐U�o��s�̎擾
	 * 
	 * @return headerPayOwnBankWord �w�b�_�[�F���v��̐U�o��s
	 */
	public String getHeaderPayOwnBankWord() {
		return headerPayOwnBankWord;
	}

	/**
	 * �w�b�_�[�F���v��̐U�o��s�̐ݒ�
	 * 
	 * @param headerPayOwnBankWord �w�b�_�[�F���v��̐U�o��s
	 */
	public void setHeaderPayOwnBankWord(String headerPayOwnBankWord) {
		this.headerPayOwnBankWord = headerPayOwnBankWord;
	}

	/**
	 * �w�b�_�[�F���v��̎x�����z�̎擾
	 * 
	 * @return headerPayAmountWord �w�b�_�[�F���v��̎x�����z
	 */
	public String getHeaderPayAmountWord() {
		return headerPayAmountWord;
	}

	/**
	 * �w�b�_�[�F���v��̎x�����z�̐ݒ�
	 * 
	 * @param headerPayAmountWord �w�b�_�[�F���v��̎x�����z
	 */
	public void setHeaderPayAmountWord(String headerPayAmountWord) {
		this.headerPayAmountWord = headerPayAmountWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̎Ј��̎擾
	 * 
	 * @return headerTempEmployeeWord �w�b�_�[�F�Ј������̎Ј�
	 */
	public String getHeaderTempEmployeeWord() {
		return headerTempEmployeeWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̎Ј��̐ݒ�
	 * 
	 * @param headerTempEmployeeWord �w�b�_�[�F�Ј������̎Ј�
	 */
	public void setHeaderTempEmployeeWord(String headerTempEmployeeWord) {
		this.headerTempEmployeeWord = headerTempEmployeeWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̉������z�̎擾
	 * 
	 * @return headerTempAmountWord �w�b�_�[�F�Ј������̉������z
	 */
	public String getHeaderTempAmountWord() {
		return headerTempAmountWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̉������z�̐ݒ�
	 * 
	 * @param headerTempAmountWord �w�b�_�[�F�Ј������̉������z
	 */
	public void setHeaderTempAmountWord(String headerTempAmountWord) {
		this.headerTempAmountWord = headerTempAmountWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̎x�����̎擾
	 * 
	 * @return headerTempPaymentDateWord �w�b�_�[�F�Ј������̎x����
	 */
	public String getHeaderTempPaymentDateWord() {
		return headerTempPaymentDateWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̎x�����̐ݒ�
	 * 
	 * @param headerTempPaymentDateWord �w�b�_�[�F�Ј������̎x����
	 */
	public void setHeaderTempPaymentDateWord(String headerTempPaymentDateWord) {
		this.headerTempPaymentDateWord = headerTempPaymentDateWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̎x�����@�̎擾
	 * 
	 * @return headerTempPaymentMethodWord �w�b�_�[�F�Ј������̎x�����@
	 */
	public String getHeaderTempPaymentMethodWord() {
		return headerTempPaymentMethodWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̎x�����@�̐ݒ�
	 * 
	 * @param headerTempPaymentMethodWord �w�b�_�[�F�Ј������̎x�����@
	 */
	public void setHeaderTempPaymentMethodWord(String headerTempPaymentMethodWord) {
		this.headerTempPaymentMethodWord = headerTempPaymentMethodWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̐U�o��s�̎擾
	 * 
	 * @return headerTempBankWord �w�b�_�[�F�Ј������̐U�o��s
	 */
	public String getHeaderTempBankWord() {
		return headerTempBankWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̐U�o��s�̐ݒ�
	 * 
	 * @param headerTempBankWord �w�b�_�[�F�Ј������̐U�o��s
	 */
	public void setHeaderTempBankWord(String headerTempBankWord) {
		this.headerTempBankWord = headerTempBankWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̐��Z�\����̎擾
	 * 
	 * @return headerTempDueDateWord �w�b�_�[�F�Ј������̐��Z�\���
	 */
	public String getHeaderTempDueDateWord() {
		return headerTempDueDateWord;
	}

	/**
	 * �w�b�_�[�F�Ј������̐��Z�\����̐ݒ�
	 * 
	 * @param headerTempDueDateWord �w�b�_�[�F�Ј������̐��Z�\���
	 */
	public void setHeaderTempDueDateWord(String headerTempDueDateWord) {
		this.headerTempDueDateWord = headerTempDueDateWord;
	}

	/**
	 * �w�b�_�[�F���v��̎����̎擾
	 * 
	 * @return headerSaleCustomerWord �w�b�_�[�F���v��̎����
	 */
	public String getHeaderSaleCustomerWord() {
		return headerSaleCustomerWord;
	}

	/**
	 * �w�b�_�[�F���v��̎����̐ݒ�
	 * 
	 * @param headerSaleCustomerWord �w�b�_�[�F���v��̎����
	 */
	public void setHeaderSaleCustomerWord(String headerSaleCustomerWord) {
		this.headerSaleCustomerWord = headerSaleCustomerWord;
	}

	/**
	 * �w�b�_�[�F���v��̓����\����̎擾
	 * 
	 * @return headerSaleDateWord �w�b�_�[�F���v��̓����\���
	 */
	public String getHeaderSaleDateWord() {
		return headerSaleDateWord;
	}

	/**
	 * �w�b�_�[�F���v��̓����\����̐ݒ�
	 * 
	 * @param headerSaleDateWord �w�b�_�[�F���v��̓����\���
	 */
	public void setHeaderSaleDateWord(String headerSaleDateWord) {
		this.headerSaleDateWord = headerSaleDateWord;
	}

	/**
	 * �w�b�_�[�F���v��̐U�������̎擾
	 * 
	 * @return headerSaleBankAccountWord �w�b�_�[�F���v��̐U������
	 */
	public String getHeaderSaleBankAccountWord() {
		return headerSaleBankAccountWord;
	}

	/**
	 * �w�b�_�[�F���v��̐U�������̐ݒ�
	 * 
	 * @param headerSaleBankAccountWord �w�b�_�[�F���v��̐U������
	 */
	public void setHeaderSaleBankAccountWord(String headerSaleBankAccountWord) {
		this.headerSaleBankAccountWord = headerSaleBankAccountWord;
	}

	/**
	 * �w�b�_�[�F���v��̐������z�̎擾
	 * 
	 * @return headerSaleAmountWord �w�b�_�[�F���v��̐������z
	 */
	public String getHeaderSaleAmountWord() {
		return headerSaleAmountWord;
	}

	/**
	 * �w�b�_�[�F���v��̐������z�̐ݒ�
	 * 
	 * @param headerSaleAmountWord �w�b�_�[�F���v��̐������z
	 */
	public void setHeaderSaleAmountWord(String headerSaleAmountWord) {
		this.headerSaleAmountWord = headerSaleAmountWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̎Ј��̎擾
	 * 
	 * @return headerSettlementEmployeeWord �w�b�_�[�F�o��Z�̎Ј�
	 */
	public String getHeaderSettlementEmployeeWord() {
		return headerSettlementEmployeeWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̎Ј��̐ݒ�
	 * 
	 * @param headerSettlementEmployeeWord �w�b�_�[�F�o��Z�̎Ј�
	 */
	public void setHeaderSettlementEmployeeWord(String headerSettlementEmployeeWord) {
		this.headerSettlementEmployeeWord = headerSettlementEmployeeWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̌o����v�̎擾
	 * 
	 * @return headerSettlementAmountSumWord �w�b�_�[�F�o��Z�̌o����v
	 */
	public String getHeaderSettlementAmountSumWord() {
		return headerSettlementAmountSumWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̌o����v�̐ݒ�
	 * 
	 * @param headerSettlementAmountSumWord �w�b�_�[�F�o��Z�̌o����v
	 */
	public void setHeaderSettlementAmountSumWord(String headerSettlementAmountSumWord) {
		this.headerSettlementAmountSumWord = headerSettlementAmountSumWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̎x�����̎擾
	 * 
	 * @return headerSettlementPaymentDateWord �w�b�_�[�F�o��Z�̎x����
	 */
	public String getHeaderSettlementPaymentDateWord() {
		return headerSettlementPaymentDateWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̎x�����̐ݒ�
	 * 
	 * @param headerSettlementPaymentDateWord �w�b�_�[�F�o��Z�̎x����
	 */
	public void setHeaderSettlementPaymentDateWord(String headerSettlementPaymentDateWord) {
		this.headerSettlementPaymentDateWord = headerSettlementPaymentDateWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̉������z�̎擾
	 * 
	 * @return headerSettlementPrevAmountWord �w�b�_�[�F�o��Z�̉������z
	 */
	public String getHeaderSettlementPrevAmountWord() {
		return headerSettlementPrevAmountWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̉������z�̐ݒ�
	 * 
	 * @param headerSettlementPrevAmountWord �w�b�_�[�F�o��Z�̉������z
	 */
	public void setHeaderSettlementPrevAmountWord(String headerSettlementPrevAmountWord) {
		this.headerSettlementPrevAmountWord = headerSettlementPrevAmountWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̎x�����@�̎擾
	 * 
	 * @return headerSettlementPayMethodWord �w�b�_�[�F�o��Z�̎x�����@
	 */
	public String getHeaderSettlementPayMethodWord() {
		return headerSettlementPayMethodWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̎x�����@�̐ݒ�
	 * 
	 * @param headerSettlementPayMethodWord �w�b�_�[�F�o��Z�̎x�����@
	 */
	public void setHeaderSettlementPayMethodWord(String headerSettlementPayMethodWord) {
		this.headerSettlementPayMethodWord = headerSettlementPayMethodWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̐��Z���z�̎擾
	 * 
	 * @return headerSettlementAmountWord �w�b�_�[�F�o��Z�̐��Z���z
	 */
	public String getHeaderSettlementAmountWord() {
		return headerSettlementAmountWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̐��Z���z�̐ݒ�
	 * 
	 * @param headerSettlementAmountWord �w�b�_�[�F�o��Z�̐��Z���z
	 */
	public void setHeaderSettlementAmountWord(String headerSettlementAmountWord) {
		this.headerSettlementAmountWord = headerSettlementAmountWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̐U�o��s�̎擾
	 * 
	 * @return headerSettlementBankWord �w�b�_�[�F�o��Z�̐U�o��s
	 */
	public String getHeaderSettlementBankWord() {
		return headerSettlementBankWord;
	}

	/**
	 * �w�b�_�[�F�o��Z�̐U�o��s�̐ݒ�
	 * 
	 * @param headerSettlementBankWord �w�b�_�[�F�o��Z�̐U�o��s
	 */
	public void setHeaderSettlementBankWord(String headerSettlementBankWord) {
		this.headerSettlementBankWord = headerSettlementBankWord;
	}

	/**
	 * �w�b�_�[�`�[�E�v�R�[�h��\���̎擾
	 * 
	 * @return notShowHeaderSlipRemarkCode �w�b�_�[�`�[�E�v�R�[�h��\��
	 */
	public boolean isNotShowHeaderSlipRemarkCode() {
		return notShowHeaderSlipRemarkCode;
	}

	/**
	 * �w�b�_�[�`�[�E�v�R�[�h��\���̐ݒ�
	 * 
	 * @param notShowHeaderSlipRemarkCode �w�b�_�[�`�[�E�v�R�[�h��\��
	 */
	public void setNotShowHeaderSlipRemarkCode(boolean notShowHeaderSlipRemarkCode) {
		this.notShowHeaderSlipRemarkCode = notShowHeaderSlipRemarkCode;
	}

	/**
	 * �w�b�_�[���͕���R�[�h��\���̎擾
	 * 
	 * @return notShowHeaderDepartmentCode �w�b�_�[���͕���R�[�h��\��
	 */
	public boolean isNotShowHeaderDepartmentCode() {
		return notShowHeaderDepartmentCode;
	}

	/**
	 * �w�b�_�[���͕���R�[�h��\���̐ݒ�
	 * 
	 * @param notShowHeaderDepartmentCode �w�b�_�[���͕���R�[�h��\��
	 */
	public void setNotShowHeaderDepartmentCode(boolean notShowHeaderDepartmentCode) {
		this.notShowHeaderDepartmentCode = notShowHeaderDepartmentCode;
	}

	/**
	 * �w�b�_�[���͎Ј��R�[�h��\���̎擾
	 * 
	 * @return notShowHeaderEmployeeCode �w�b�_�[���͎Ј��R�[�h��\��
	 */
	public boolean isNotShowHeaderEmployeeCode() {
		return notShowHeaderEmployeeCode;
	}

	/**
	 * �w�b�_�[���͎Ј��R�[�h��\���̐ݒ�
	 * 
	 * @param notShowHeaderEmployeeCode �w�b�_�[���͎Ј��R�[�h��\��
	 */
	public void setNotShowHeaderEmployeeCode(boolean notShowHeaderEmployeeCode) {
		this.notShowHeaderEmployeeCode = notShowHeaderEmployeeCode;
	}

	/**
	 * ����R�[�h��\���̎擾
	 * 
	 * @return notShowDepartmentCode ����R�[�h��\��
	 */
	public boolean isNotShowDepartmentCode() {
		return notShowDepartmentCode;
	}

	/**
	 * ����R�[�h��\���̐ݒ�
	 * 
	 * @param notShowDepartmentCode ����R�[�h��\��
	 */
	public void setNotShowDepartmentCode(boolean notShowDepartmentCode) {
		this.notShowDepartmentCode = notShowDepartmentCode;
	}

	/**
	 * �ȖڃR�[�h��\���̎擾
	 * 
	 * @return notShowItemCode �ȖڃR�[�h��\��
	 */
	public boolean isNotShowItemCode() {
		return notShowItemCode;
	}

	/**
	 * �ȖڃR�[�h��\���̐ݒ�
	 * 
	 * @param notShowItemCode �ȖڃR�[�h��\��
	 */
	public void setNotShowItemCode(boolean notShowItemCode) {
		this.notShowItemCode = notShowItemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h��\���̎擾
	 * 
	 * @return notShowSubItemCode �⏕�ȖڃR�[�h��\��
	 */
	public boolean isNotShowSubItemCode() {
		return notShowSubItemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h��\���̐ݒ�
	 * 
	 * @param notShowSubItemCode �⏕�ȖڃR�[�h��\��
	 */
	public void setNotShowSubItemCode(boolean notShowSubItemCode) {
		this.notShowSubItemCode = notShowSubItemCode;
	}

	/**
	 * ����ȖڃR�[�h��\���̎擾
	 * 
	 * @return notShowDetailItemCode ����ȖڃR�[�h��\��
	 */
	public boolean isNotShowDetailItemCode() {
		return notShowDetailItemCode;
	}

	/**
	 * ����ȖڃR�[�h��\���̐ݒ�
	 * 
	 * @param notShowDetailItemCode ����ȖڃR�[�h��\��
	 */
	public void setNotShowDetailItemCode(boolean notShowDetailItemCode) {
		this.notShowDetailItemCode = notShowDetailItemCode;
	}

	/**
	 * �����R�[�h��\���̎擾
	 * 
	 * @return notShowCustomerCode �����R�[�h��\��
	 */
	public boolean isNotShowCustomerCode() {
		return notShowCustomerCode;
	}

	/**
	 * �����R�[�h��\���̐ݒ�
	 * 
	 * @param notShowCustomerCode �����R�[�h��\��
	 */
	public void setNotShowCustomerCode(boolean notShowCustomerCode) {
		this.notShowCustomerCode = notShowCustomerCode;
	}

	/**
	 * �Ј��R�[�h��\���̎擾
	 * 
	 * @return notShowEmployeeCode �Ј��R�[�h��\��
	 */
	public boolean isNotShowEmployeeCode() {
		return notShowEmployeeCode;
	}

	/**
	 * �Ј��R�[�h��\���̐ݒ�
	 * 
	 * @param notShowEmployeeCode �Ј��R�[�h��\��
	 */
	public void setNotShowEmployeeCode(boolean notShowEmployeeCode) {
		this.notShowEmployeeCode = notShowEmployeeCode;
	}

	/**
	 * ����ŃR�[�h��\���̎擾
	 * 
	 * @return notShowTaxCode ����ŃR�[�h��\��
	 */
	public boolean isNotShowTaxCode() {
		return notShowTaxCode;
	}

	/**
	 * ����ŃR�[�h��\���̐ݒ�
	 * 
	 * @param notShowTaxCode ����ŃR�[�h��\��
	 */
	public void setNotShowTaxCode(boolean notShowTaxCode) {
		this.notShowTaxCode = notShowTaxCode;
	}

	/**
	 * �Ǘ�1�R�[�h��\���̎擾
	 * 
	 * @return notShowManagementCode1 �Ǘ�1�R�[�h��\��
	 */
	public boolean isNotShowManagementCode1() {
		return notShowManagementCode1;
	}

	/**
	 * �Ǘ�1�R�[�h��\���̐ݒ�
	 * 
	 * @param notShowManagementCode1 �Ǘ�1�R�[�h��\��
	 */
	public void setNotShowManagementCode1(boolean notShowManagementCode1) {
		this.notShowManagementCode1 = notShowManagementCode1;
	}

	/**
	 * �Ǘ�2�R�[�h��\���̎擾
	 * 
	 * @return notShowManagementCode2 �Ǘ�2�R�[�h��\��
	 */
	public boolean isNotShowManagementCode2() {
		return notShowManagementCode2;
	}

	/**
	 * �Ǘ�2�R�[�h��\���̐ݒ�
	 * 
	 * @param notShowManagementCode2 �Ǘ�2�R�[�h��\��
	 */
	public void setNotShowManagementCode2(boolean notShowManagementCode2) {
		this.notShowManagementCode2 = notShowManagementCode2;
	}

	/**
	 * �Ǘ�3�R�[�h��\���̎擾
	 * 
	 * @return notShowManagementCode3 �Ǘ�3�R�[�h��\��
	 */
	public boolean isNotShowManagementCode3() {
		return notShowManagementCode3;
	}

	/**
	 * �Ǘ�3�R�[�h��\���̐ݒ�
	 * 
	 * @param notShowManagementCode3 �Ǘ�3�R�[�h��\��
	 */
	public void setNotShowManagementCode3(boolean notShowManagementCode3) {
		this.notShowManagementCode3 = notShowManagementCode3;
	}

	/**
	 * �Ǘ�4�R�[�h��\���̎擾
	 * 
	 * @return notShowManagementCode4 �Ǘ�4�R�[�h��\��
	 */
	public boolean isNotShowManagementCode4() {
		return notShowManagementCode4;
	}

	/**
	 * �Ǘ�4�R�[�h��\���̐ݒ�
	 * 
	 * @param notShowManagementCode4 �Ǘ�4�R�[�h��\��
	 */
	public void setNotShowManagementCode4(boolean notShowManagementCode4) {
		this.notShowManagementCode4 = notShowManagementCode4;
	}

	/**
	 * �Ǘ�5�R�[�h��\���̎擾
	 * 
	 * @return notShowManagementCode5 �Ǘ�5�R�[�h��\��
	 */
	public boolean isNotShowManagementCode5() {
		return notShowManagementCode5;
	}

	/**
	 * �Ǘ�5�R�[�h��\���̐ݒ�
	 * 
	 * @param notShowManagementCode5 �Ǘ�5�R�[�h��\��
	 */
	public void setNotShowManagementCode5(boolean notShowManagementCode5) {
		this.notShowManagementCode5 = notShowManagementCode5;
	}

	/**
	 * �Ǘ�6�R�[�h��\���̎擾
	 * 
	 * @return notShowManagementCode6 �Ǘ�6�R�[�h��\��
	 */
	public boolean isNotShowManagementCode6() {
		return notShowManagementCode6;
	}

	/**
	 * �Ǘ�6�R�[�h��\���̐ݒ�
	 * 
	 * @param notShowManagementCode6 �Ǘ�6�R�[�h��\��
	 */
	public void setNotShowManagementCode6(boolean notShowManagementCode6) {
		this.notShowManagementCode6 = notShowManagementCode6;
	}

}
