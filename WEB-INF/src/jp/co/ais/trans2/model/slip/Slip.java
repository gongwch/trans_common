package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.user.*;

/**
 * �`�[ �S�Ă̓`�[�͓��Y�N���X���p������<br>
 * �`�[�́A�u�`�[�w�b�_�[�v�ƕ����́u�`�[���ׁv����\�������B
 */
public class Slip extends TransferBase implements Cloneable {

	/** �r���t���O */
	public static class SHR_KBN {

		/** 0:�ʏ� */
		public static final int NON_LOCKED = 0;

		/** 1:�r�� */
		public static final int LOCKED = 1;
	}

	/** ���Z�敪 */
	public static class KSN_KBN {

		/** 0:�ʏ� */
		public static final int NOMAL = 0;
	}

	/** ��Њԕt�֓`�[�敪 */
	public static class TUKE_KBN {

		/** 0:�ʏ� */
		public static final int NOMAL = 0;

		/** 1:�t�֐�`�[ */
		public static final int TRANSFER_SLIP = 1;
	}

	/** ��� */
	protected Company company = null;

	/** �`�[�w�b�_�[ */
	protected SWK_HDR header;

	/** ���׍s */
	protected List<SWK_DTL> details;

	/** �U�ߓ��t */
	protected Date reverseDate = null;

	/** �U�ߓ`�[���ǂ��� */
	protected boolean reversingType = false;

	/** �D�����Ẫ`�F�b�N���s���� */
	protected boolean checksFleetMvmt = false;

	/** true:���V�X�e������f�[�^ */
	protected boolean fromOtherSystem = false;

	/**
	 * ����Ŏd����N�[�ς݂��B<br>
	 * true�̏ꍇ�ASlipManager.buildSlip�ɂ����āA����Ŏd����N�[���Ȃ��B
	 */
	protected boolean journalizedTax = false;

	/** true:�K�{�`�F�b�N�s�v(buildBaseSlip�̎�) */
	protected boolean avoidReuiredItemNULL = false;

	/**
	 * �R���X�g���N�^.
	 */
	public Slip() {
		this.header = createHeader();
		this.details = new ArrayList<SWK_DTL>();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param header �w�b�_
	 */
	public Slip(SWK_HDR header) {
		this.header = header;
		this.details = new ArrayList<SWK_DTL>();
	}

	/**
	 * �w�b�_�쐬
	 * 
	 * @return �w�b�_
	 */
	protected SWK_HDR createHeader() {
		return new SWK_HDR();
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Slip clone() {
		try {
			Slip slip = (Slip) super.clone();
			slip.header = this.header.clone();
			slip.details = new ArrayList<SWK_DTL>();

			for (SWK_DTL dtl : details) {
				slip.addDetail(dtl.clone());
			}

			return slip;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append("<");
		buff.append(header.getKAI_CODE()).append("/");
		buff.append(DateUtil.toYMDString(header.getSWK_DEN_DATE())).append("/");
		buff.append(header.getSWK_DEN_NO()).append("/");
		buff.append(header.getSWK_ADJ_KBN());
		buff.append(">\n");
		buff.append("-- header --").append("\n");
		buff.append(header).append("\n");
		buff.append("-- details --").append("\n");
		for (SWK_DTL dtl : getDetails()) {
			buff.append(dtl).append("\n");
		}

		return buff.toString();
	}

	/**
	 * ��ЃR�[�h��ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		header.setKAI_CODE(companyCode);
	}

	/**
	 * ��ЃR�[�h���擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return header.getKAI_CODE();
	}

	/**
	 * �`�[�ԍ�getter
	 * 
	 * @return �`�[�ԍ�
	 */
	public String getSlipNo() {
		return header.getSWK_DEN_NO();
	}

	/**
	 * �`�[�ԍ�setter
	 * 
	 * @param denNo �`�[�ԍ�
	 */
	public void setSlipNo(String denNo) {
		this.header.setSWK_DEN_NO(denNo);
	}

	/**
	 * �����O�`�[�ԍ�getter
	 * 
	 * @return �����O�`�[�ԍ�
	 */
	public String getReversingSlipNo() {
		return header.getSWK_BEFORE_DEN_NO();
	}

	/**
	 * �����O�`�[�ԍ�setter
	 * 
	 * @param denNo �����O�`�[�ԍ�
	 */
	public void setReversingSlipNo(String denNo) {
		this.header.setSWK_BEFORE_DEN_NO(denNo);
	}

	/**
	 * �U�ߓ`�[���ǂ���
	 * 
	 * @param reversingType true:�U�ߓ`�[
	 */
	public void setReversing(boolean reversingType) {
		this.reversingType = reversingType;
	}

	/**
	 * �U�ߓ`�[���ǂ���
	 * 
	 * @return true:�U�ߓ`�[
	 */
	public boolean isReversingSlip() {
		return this.reversingType;
	}

	/**
	 * �t�֐�`�[���ǂ���
	 * 
	 * @return true:�t�֐�`�[
	 */
	public boolean isTransferSlip() {
		return header.getSWK_TUKE_KBN() == TUKE_KBN.TRANSFER_SLIP;
	}

	/**
	 * ���Z�`�[���ǂ���
	 * 
	 * @return true:���Z�Afalse:�ʏ�
	 */
	public boolean isClosingSlip() {
		return this.header.getSWK_KSN_KBN() > 0;
	}

	/**
	 * �`�[���t���擾
	 * 
	 * @return �`�[���t
	 */
	public Date getSlipDate() {
		return header.getSWK_DEN_DATE();
	}

	/**
	 * �`�[���t���Z�b�g
	 * 
	 * @param date �`�[���t
	 */
	public void setSlipDate(Date date) {
		this.header.setSWK_DEN_DATE(date);
	}

	/**
	 * ���Z�i�K
	 * 
	 * @return ���Z�i�K
	 */
	public int getSettlementStage() {
		return this.header.getSWK_KSN_KBN();
	}

	/**
	 * �E�v�R�[�h��Ԃ�
	 * 
	 * @return �E�v�R�[�h
	 */
	public String getRemarksCode() {
		return this.header.getSWK_TEK_CODE();
	}

	/**
	 * �E�v�R�[�h���Z�b�g
	 * 
	 * @param code �E�v�R�[�h
	 */
	public void setRemarksCode(String code) {
		this.header.setSWK_TEK_CODE(code);
	}

	/**
	 * �E�v
	 * 
	 * @return �E�v
	 */
	public String getRemarks() {
		return header.getSWK_TEK();
	}

	/**
	 * �`�[�E�v��ݒ�
	 * 
	 * @param remarks �`�[�E�v
	 */
	public void setRemarks(String remarks) {
		header.setSWK_TEK(remarks);
	}

	/**
	 * �`�[��ʂ̎擾
	 * 
	 * @return �`�[���
	 */
	public String getSlipType() {
		return this.header.getSWK_DEN_SYU();
	}

	/**
	 * �`�[�w�b�_
	 * 
	 * @return �`�[�w�b�_
	 */
	public SWK_HDR getHeader() {
		return header;
	}

	/**
	 * �`�[�w�b�_�̃Z�b�g
	 * 
	 * @param hdr �`�[�w�b�_
	 */
	public void setHeader(SWK_HDR hdr) {
		this.header = hdr;
	}

	/**
	 * ���׍sgetter
	 * 
	 * @return ���׍s
	 */
	public List<SWK_DTL> getDetails() {
		return details;
	}

	/**
	 * ������w�肵�Ė��׍s�擾
	 * 
	 * @param type ����
	 * @return ���׍s
	 */
	public List<SWK_DTL> getDetails(CurrencyType type) {
		List<SWK_DTL> list = new ArrayList();

		for (SWK_DTL dtl : this.details) {
			if (type == dtl.getCurrencyType()) {
				list.add(dtl);
			}
		}

		return list;
	}

	/**
	 * �ݎ؂��w�肵�Ė��׍s�擾
	 * 
	 * @param dc �ݎ�
	 * @return ���׍s
	 */
	public List<SWK_DTL> getDetails(Dc dc) {
		List<SWK_DTL> list = new ArrayList();

		for (SWK_DTL dtl : this.details) {
			if (dc == dtl.getDC()) {
				list.add(dtl);
			}
		}

		return list;
	}

	/**
	 * ����A�ݎ؂��w�肵�Ė��׍s�擾
	 * 
	 * @param type ����
	 * @param dc �ݎ�
	 * @return ���׍s
	 */
	public List<SWK_DTL> getDetails(CurrencyType type, Dc dc) {
		List<SWK_DTL> list = new ArrayList();

		for (SWK_DTL dtl : this.details) {
			if (type == dtl.getCurrencyType() && dc == dtl.getDC()) {
				list.add(dtl);
			}
		}

		return list;
	}

	/**
	 * ���א�
	 * 
	 * @return ���א�
	 */
	public int getDetailCount() {
		return details.size();
	}

	/**
	 * �`�[���ׂ̃Z�b�g
	 * 
	 * @param dlist �`�[����
	 */
	public void setDetails(List<SWK_DTL> dlist) {
		this.details = dlist;
	}

	/**
	 * �`�[���ׂ̃t�@�N�g��<br>
	 * �`�[���׃N���X�𐶐����ĕԂ�.
	 * 
	 * @return SWK_DTL
	 */
	public SWK_DTL createDetail() {
		SWK_DTL dtl = new SWK_DTL();
		dtl.setKAI_CODE(getCompanyCode()); // ��ЃR�[�h
		dtl.setSWK_DEN_NO(getSlipNo()); // �`�[�ԍ�
		dtl.setSWK_DEN_DATE(getSlipDate()); // �`�[���t
		dtl.setDEN_SYU_CODE(header.getSWK_DEN_SYU()); // �`�[��ʃR�[�h
		dtl.setSWK_DATA_KBN(header.getSWK_DATA_KBN()); // �f�[�^�敪
		dtl.setSWK_UPD_KBN(header.getSWK_UPD_KBN()); // �X�V�敪
		dtl.setSWK_KSN_KBN(header.getSWK_KSN_KBN()); // ���Z�i�K
		dtl.setSWK_ADJ_KBN(header.getSWK_ADJ_KBN()); // IFRS�����敪

		return dtl;
	}

	/**
	 * �`�[���׍s��ǉ�����
	 * 
	 * @param detail �`�[���׍s
	 */
	public void addDetail(SWK_DTL detail) {
		details.add(detail);
	}

	/**
	 * �`�[���׍s��ǉ�����
	 * 
	 * @param list �`�[���׍s
	 */
	public void addDetails(List<SWK_DTL> list) {
		details.addAll(list);
	}

	/**
	 * �`�[���׍s��ǉ�����
	 * 
	 * @param detail �`�[���׍s
	 */
	public void removeDetail(SWK_DTL detail) {
		details.remove(detail);
	}

	/**
	 * �`�[���׍s���N���A����.
	 */
	public void clearDetail() {
		details.clear();
	}

	/**
	 * �`�[�̎��
	 * 
	 * @return �`�[�̎��
	 */
	public SlipKind getSlipKind() {
		return SlipKind.get(header.getSWK_DATA_KBN());
	}

	/**
	 * ����ݒ�
	 * 
	 * @param book ����
	 */
	public void setAccountBook(AccountBook book) {
		getHeader().setSWK_ADJ_KBN(book.value);
	}

	/**
	 * ����擾
	 * 
	 * @return ����
	 */
	public AccountBook getAccountBook() {
		return AccountBook.get(getHeader().getSWK_ADJ_KBN());
	}

	/**
	 * �����ʉ�/�@�\�ʉ݂���Ԃ��B<br>
	 * �������ASlip���ɂǂ��炩����������݂��Ȃ����Ƃ�O��Ƃ���B
	 * 
	 * @return �����ʉ�/�@�\�ʉ݂�(���ׂ�1�s�ڂ̒l)
	 */
	public CurrencyType getCurrencyType() {
		if (getDetails() == null || getDetails().isEmpty()) {
			return null;
		}
		return getDetails().get(0).getCurrencyType();
	}

	/**
	 * �`�[�̃^�C�g��
	 * 
	 * @return �^�C�g��
	 */
	public String getTitle() {
		return header.getSWK_DEN_SYU_NAME_K();
	}

	/**
	 * �ؕ��̍��v���z(��ʉ�)��Ԃ��B
	 * 
	 * @return �ؕ��̍��v���z(��ʉ�)
	 */
	public BigDecimal getDebitKeyAmount() {

		BigDecimal amount = BigDecimal.ZERO;
		for (SWK_DTL detail : this.details) {
			amount = amount.add(detail.getDebitKeyAmount());
		}

		return amount;
	}

	/**
	 * �ݕ��̍��v���z(��ʉ�)��Ԃ��B
	 * 
	 * @return �ݕ��̍��v���z(��ʉ�)
	 */
	public BigDecimal getCreditKeyAmount() {
		BigDecimal amount = BigDecimal.ZERO;
		for (SWK_DTL detail : this.details) {
			amount = amount.add(detail.getCreditKeyAmount());
		}

		return amount;
	}

	/**
	 * �ؕ��̊O�ݍ��v���z��Ԃ��B<br>
	 * �������ɒʉ݃C���f�b�N�X�Ƃ��A�w��C���f�b�N�X�l���Ȃ��ꍇ��null���Ԃ�.
	 * 
	 * @param index �C���f�b�N�X
	 * @return ���v���z
	 */
	public BigDecimal getDebitForeignAmountAt(int index) {

		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String key = detail.getSWK_CUR_CODE();
				BigDecimal amount = map.get(key);

				if (amount == null) {
					amount = BigDecimal.ZERO;
				}

				map.put(key, amount.add(detail.getDebitInputAmount()));

			}

		}

		BigDecimal[] array = map.values().toArray(new BigDecimal[map.size()]);

		if (array.length > index) {
			return array[index];

		}
		return null;
	}

	/**
	 * �ؕ��̊O�ݍ��v���z�̈ꗗ��Ԃ��B
	 * 
	 * @return ���v���z
	 */
	public BigDecimal[] getDebitForeignAmountList() {

		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String key = detail.getSWK_CUR_CODE();
				BigDecimal amount = map.get(key);

				if (amount == null) {
					amount = BigDecimal.ZERO;
				}

				map.put(key, amount.add(detail.getDebitInputAmount()));

			}

		}

		if (map.size() == 0) {
			return null;
		}

		BigDecimal[] array = map.values().toArray(new BigDecimal[map.size()]);

		return array;

	}

	/**
	 * �ݕ��̊O�ݍ��v���z��Ԃ��B<br>
	 * �������ɒʉ݃C���f�b�N�X�Ƃ��A�w��C���f�b�N�X�l���Ȃ��ꍇ��null���Ԃ�.
	 * 
	 * @param index �C���f�b�N�X
	 * @return ���v���z
	 */
	public BigDecimal getCreditForeignAmountAt(int index) {

		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String key = detail.getSWK_CUR_CODE();
				BigDecimal amount = map.get(key);

				if (amount == null) {
					amount = BigDecimal.ZERO;
				}

				map.put(key, amount.add(detail.getCreditInputAmount()));
			}
		}

		BigDecimal[] array = map.values().toArray(new BigDecimal[map.size()]);

		if (array.length > index) {
			return array[index];

		}
		return null;
	}

	/**
	 * �ݕ��̊O�ݍ��v���z�̈ꗗ��Ԃ��B<br>
	 * 
	 * @return ���v���z
	 */
	public BigDecimal[] getCreditForeignAmountList() {

		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String key = detail.getSWK_CUR_CODE();
				BigDecimal amount = map.get(key);

				if (amount == null) {
					amount = BigDecimal.ZERO;
				}

				map.put(key, amount.add(detail.getCreditInputAmount()));
			}
		}

		if (map.size() == 0) {
			return null;
		}

		BigDecimal[] array = map.values().toArray(new BigDecimal[map.size()]);
		return array;

	}

	/**
	 * �w��Index�O�݂�Ԃ�.(�R�[�h�ƌ����̂�)<br>
	 * �������ɊO�݃C���f�b�N�X�Ƃ��A�w��C���f�b�N�X�l���Ȃ��ꍇ��null���Ԃ�.
	 * 
	 * @param index �C���f�b�N�X
	 * @return �O�݃G���e�B�e�B
	 */
	public Currency getForeignCurrencyAt(int index) {

		Map<String, Currency> map = new LinkedHashMap<String, Currency>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String code = detail.getSWK_CUR_CODE();
				Currency currency = map.get(code);

				if (currency == null) {
					currency = new Currency();
					currency.setCode(code);
					currency.setDecimalPoint(detail.getCUR_DEC_KETA());
				}

				map.put(code, currency);
			}
		}

		Currency[] array = map.values().toArray(new Currency[map.size()]);

		if (array.length > index) {
			return array[index];

		}
		return null;
	}

	/**
	 * �`�[���ɂ���O�݂𔭐����ɕ��ׂ����X�g��Ԃ�
	 * 
	 * @return �O�݃��X�g
	 */
	public Currency[] getForeignCurrencyList() {

		Map<String, Currency> map = new LinkedHashMap<String, Currency>();

		Currency baseCurrency = getBaseCurrency();

		for (SWK_DTL detail : this.details) {

			if (!baseCurrency.getCode().equals(detail.getSWK_CUR_CODE())) {

				String code = detail.getSWK_CUR_CODE();
				Currency currency = map.get(code);

				if (currency == null) {
					currency = new Currency();
					currency.setCode(code);
					currency.setDecimalPoint(detail.getCUR_DEC_KETA());
				}

				map.put(code, currency);
			}
		}

		if (map.size() == 0) {
			return null;
		}

		Currency[] array = map.values().toArray(new Currency[map.size()]);
		return array;
	}

	/**
	 * ����ȖځE���蕔����Z�b�g����B
	 */
	public void matchingItem() {
		// �ʏ�Ƌ@�\�ʉݎd��͕ʁX�ő���ݒ�
		List<SWK_DTL> nomalList = new ArrayList<SWK_DTL>();
		List<SWK_DTL> funcList = new ArrayList<SWK_DTL>();

		for (SWK_DTL dtl : details) {
			(dtl.isFunctionalCurrency() ? funcList : nomalList).add(dtl);
		}

		matchingItem(nomalList);
		matchingItem(funcList);
	}

	/**
	 * ����ȖځE���蕔����Z�b�g����B
	 * 
	 * @param list �Ώۖ��׃��X�g
	 */
	public void matchingItem(List<SWK_DTL> list) {
		// �Ώۂ�U�蕪��
		List<SWK_DTL> drList = new ArrayList<SWK_DTL>();
		List<SWK_DTL> crList = new ArrayList<SWK_DTL>();

		List<SWK_DTL> drTaxList = new ArrayList<SWK_DTL>(); // ����Ŏd��DR
		List<SWK_DTL> crTaxList = new ArrayList<SWK_DTL>(); // ����Ŏd��CR

		for (SWK_DTL dtl : list) {

			// ����Ŏd��
			if (dtl.isTaxJornal()) {
				(dtl.isDR() ? drTaxList : crTaxList).add(dtl);
				continue;
			}

			(dtl.isDR() ? drList : crList).add(dtl);
		}

		// ������̎擾
		String[] drKmk = new String[3];
		String[] crKmk = new String[3];
		String drDept = null;
		String crDept = null;

		// �ؕ��Ȗڂ�2�ȏ゠��ꍇ�͂Ȃ�
		if (drList.size() == 1) {
			SWK_DTL drDtl = drList.get(0);
			drKmk = new String[] { drDtl.getSWK_KMK_CODE(), drDtl.getSWK_HKM_CODE(), drDtl.getSWK_UKM_CODE() };
			drDept = drDtl.getSWK_DEP_CODE();
		}

		// �ݕ��Ȗڂ�2�ȏ゠��ꍇ�͂Ȃ�
		if (crList.size() == 1) {
			SWK_DTL crDtl = crList.get(0);
			crKmk = new String[] { crDtl.getSWK_KMK_CODE(), crDtl.getSWK_HKM_CODE(), crDtl.getSWK_UKM_CODE() };
			crDept = crDtl.getSWK_DEP_CODE();
		}

		// ���ׂ̐ݒ�
		for (SWK_DTL dtl : drList) {
			dtl.setSWK_AT_KMK_CODE(crKmk[0]); // ����ȖڃR�[�h
			dtl.setSWK_AT_HKM_CODE(crKmk[1]); // ����⏕�R�[�h
			dtl.setSWK_AT_UKM_CODE(crKmk[2]); // �������R�[�h
			dtl.setSWK_AT_DEP_CODE(crDept); // ���蕔��R�[�h
		}

		for (SWK_DTL dtl : crList) {
			dtl.setSWK_AT_KMK_CODE(drKmk[0]); // ����ȖڃR�[�h
			dtl.setSWK_AT_HKM_CODE(drKmk[1]); // ����⏕�R�[�h
			dtl.setSWK_AT_UKM_CODE(drKmk[2]); // �������R�[�h
			dtl.setSWK_AT_DEP_CODE(drDept); // ���蕔��R�[�h
		}

		// ����Ŏd��ւ̐ݒ�
		for (SWK_DTL dtl : drTaxList) {
			dtl.setSWK_AT_KMK_CODE(crKmk[0]); // ����ȖڃR�[�h
			dtl.setSWK_AT_HKM_CODE(crKmk[1]); // ����⏕�R�[�h
			dtl.setSWK_AT_UKM_CODE(crKmk[2]); // �������R�[�h
			dtl.setSWK_AT_DEP_CODE(crDept); // ���蕔��R�[�h
		}

		for (SWK_DTL dtl : crTaxList) {
			dtl.setSWK_AT_KMK_CODE(drKmk[0]); // ����ȖڃR�[�h
			dtl.setSWK_AT_HKM_CODE(drKmk[1]); // ����⏕�R�[�h
			dtl.setSWK_AT_UKM_CODE(drKmk[2]); // �������R�[�h
			dtl.setSWK_AT_DEP_CODE(drDept); // ���蕔��R�[�h
		}
	}

	/**
	 * �e���ׂɏ����Z�b�g����
	 */
	public void synchDetails() {

		// ���ד�������
		String compCode = getCompanyCode();
		Date slipDate = getSlipDate();
		String slipNo = getSlipNo();
		String seiNo = header.getSWK_SEI_NO();

		String denSyu = header.getSWK_DEN_SYU();
		String dataKbn = header.getSWK_DATA_KBN();
		int updKbn = header.getSWK_UPD_KBN();
		int ksnKbn = header.getSWK_KSN_KBN();

		Date inputDate = header.getINP_DATE();
		Date updateDate = header.getUPD_DATE();
		String userID = header.getUSR_ID();
		String prgID = header.getPRG_ID();

		for (SWK_DTL dtl : getDetails()) {

			// �w�b�_�����p��
			dtl.setKAI_CODE(compCode); // ��ЃR�[�h
			dtl.setSWK_DEN_NO(slipNo); // �`�[�ԍ�
			dtl.setSWK_DEN_DATE(slipDate); // �`�[���t

			dtl.setDEN_SYU_CODE(denSyu); // �`�[��ʃR�[�h
			dtl.setSWK_DATA_KBN(dataKbn); // �f�[�^�敪
			dtl.setSWK_UPD_KBN(updKbn); // �X�V�敪
			dtl.setSWK_KSN_KBN(ksnKbn); // ���Z�i�K

			dtl.setSWK_SEI_NO(seiNo); // �؜ߔԍ�

			dtl.setINP_DATE(inputDate); // �o�^���t
			dtl.setUPD_DATE(updateDate); // �X�V���t
			dtl.setUSR_ID(userID); // ���[�UID
			dtl.setPRG_ID(prgID); // �v���O����ID
		}
	}

	/**
	 * �e���ׂɏ����Z�b�g����
	 * 
	 * @param fiscalYear �N�x
	 * @param fiscalMonth ���x
	 */
	public void synchDetails(int fiscalYear, int fiscalMonth) {

		synchDetails();

		for (SWK_DTL dtl : getDetails()) {
			dtl.setSWK_NENDO(fiscalYear); // �N�x
			dtl.setSWK_TUKIDO(fiscalMonth); // ���x
		}
	}

	/**
	 * �ԓ`�[�ɂ���(���z���])
	 * 
	 * @return �ԓ`�[
	 */
	public Slip toCancelSlip() {
		Slip clone = this.clone();

		// ���z���] �w�b�_�[
		SWK_HDR hdr = clone.getHeader();

		// ���͋��z
		if (hdr.getSWK_IN_KIN() != null) {
			hdr.setSWK_IN_KIN(hdr.getSWK_IN_KIN().negate());
		}

		// �M�݋��z
		if (hdr.getSWK_KIN() != null) {
			hdr.setSWK_KIN(hdr.getSWK_KIN().negate());
		}

		// �x�����͋��z
		if (hdr.getSWK_IN_SIHA_KIN() != null) {
			hdr.setSWK_IN_SIHA_KIN(hdr.getSWK_IN_SIHA_KIN().negate());
		}

		// �x���M�݋��z
		if (hdr.getSWK_SIHA_KIN() != null) {
			hdr.setSWK_SIHA_KIN(hdr.getSWK_SIHA_KIN().negate());
		}

		// �o����͋��z
		if (hdr.getSWK_IN_KEIHI_KIN() != null) {
			hdr.setSWK_IN_KEIHI_KIN(hdr.getSWK_IN_KEIHI_KIN().negate());
		}

		// �o��M�݋��z
		if (hdr.getSWK_KEIHI_KIN() != null) {
			hdr.setSWK_KEIHI_KIN(hdr.getSWK_KEIHI_KIN().negate());
		}

		for (SWK_DTL dtl : clone.getDetails()) {
			// ���z���]
			dtl.setSWK_KIN(dtl.getSWK_KIN().negate());
			dtl.setSWK_IN_KIN(dtl.getSWK_IN_KIN().negate());

			if (dtl.getSWK_ZEI_KIN() != null) {
				dtl.setSWK_ZEI_KIN(dtl.getSWK_ZEI_KIN().negate());
			}
			if (dtl.getSWK_IN_ZEI_KIN() != null) {
				dtl.setSWK_IN_ZEI_KIN(dtl.getSWK_IN_ZEI_KIN().negate());
			}
		}

		return clone;
	}

	/**
	 * �t�`�[�ɂ���(�ݎؔ��])
	 * 
	 * @return �t�`�[
	 */
	public Slip toReverseSlip() {
		Slip clone = this.clone();

		// ���z���] �w�b�_�[
		SWK_HDR hdr = clone.getHeader();

		// ���͋��z
		if (hdr.getSWK_IN_KIN() != null) {
			hdr.setSWK_IN_KIN(hdr.getSWK_IN_KIN().negate());
		}

		// �M�݋��z
		if (hdr.getSWK_KIN() != null) {
			hdr.setSWK_KIN(hdr.getSWK_KIN().negate());
		}

		// �x�����͋��z
		if (hdr.getSWK_IN_SIHA_KIN() != null) {
			hdr.setSWK_IN_SIHA_KIN(hdr.getSWK_IN_SIHA_KIN().negate());
		}

		// �x���M�݋��z
		if (hdr.getSWK_SIHA_KIN() != null) {
			hdr.setSWK_SIHA_KIN(hdr.getSWK_SIHA_KIN().negate());
		}

		// �o����͋��z
		if (hdr.getSWK_IN_KEIHI_KIN() != null) {
			hdr.setSWK_IN_KEIHI_KIN(hdr.getSWK_IN_KEIHI_KIN().negate());
		}

		// �o��M�݋��z
		if (hdr.getSWK_KEIHI_KIN() != null) {
			hdr.setSWK_KEIHI_KIN(hdr.getSWK_KEIHI_KIN().negate());
		}

		for (SWK_DTL dtl : clone.getDetails()) {
			// �ݎؔ��]
			dtl.setDC(dtl.getDC() == Dc.DEBIT ? Dc.CREDIT : Dc.DEBIT);
		}

		return clone;
	}

	/**
	 * ���Y�`�[�̒���̌��Z�ʉ݂�Ԃ��B<br>
	 * ��������̏ꍇ�A��ʉ݁A�@�\�ʉݒ���̏ꍇ�͋@�\�ʉ݁B
	 * 
	 * @return ���Z�ʉ�
	 */
	public Currency getBaseCurrency() {

		List<SWK_DTL> list = getDetails();
		if (list == null || list.isEmpty()) {
			return null;
		}
		Currency currency = new Currency();
		if (list.get(0).isFunctionalCurrency()) {
			currency.setCode(header.getFUNC_CUR_CODE());
			currency.setDecimalPoint(header.getFUNC_CUR_DEC_KETA());
		} else {
			currency.setCode(header.getKEY_CUR_CODE());
			currency.setDecimalPoint(header.getKEY_CUR_DEC_KETA());
		}

		return currency;

	}

	/**
	 * �U�ߓ��t���擾����B
	 * 
	 * @return Date �U�ߓ��t
	 */
	public Date getReverseDate() {
		return reverseDate;
	}

	/**
	 * �U�ߓ��t��ݒ肷��B
	 * 
	 * @param reverseDate �U�ߓ��t
	 */
	public void setReverseDate(Date reverseDate) {
		this.reverseDate = reverseDate;
	}

	/**
	 * ����Ŏd����N�[�ς݂���Ԃ��B<br>
	 * true�̏ꍇ�ASlipManager.buildSlip�ɂ����āA����Ŏd����N�[���Ȃ��B
	 * 
	 * @return ����Ŏd����N�[�ς݂�
	 */
	public boolean isJournalizedTax() {
		return journalizedTax;
	}

	/**
	 * ����Ŏd����N�[�ς݂���ݒ肷��B<br>
	 * true�̏ꍇ�ASlipManager.buildSlip�ɂ����āA����Ŏd����N�[���Ȃ��B
	 * 
	 * @param journalizedTax ����Ŏd����N�[�ς݂�
	 */
	public void setJournalizedTax(boolean journalizedTax) {
		this.journalizedTax = journalizedTax;
	}

	/**
	 * �V�K�쐬�`�[���ǂ���
	 * 
	 * @return true:�V�K
	 */
	public boolean isNew() {
		if (header == null) {
			return true;
		}

		return header.getINP_DATE() == null && header.getSWK_INP_DATE() == null;
	}

	/**
	 * ���getter
	 * 
	 * @return ���
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * ���setter
	 * 
	 * @param company ���
	 */
	public void setCompany(Company company) {
		this.company = company;
		getHeader().setKAI_CODE(company.getCode());

		AccountConfig config = company.getAccountConfig();
		header.setKEY_CUR_CODE(config.getKeyCurrency().getCode());
		header.setKEY_CUR_DEC_KETA(config.getKeyCurrency().getDecimalPoint());
		header.setFUNC_CUR_CODE(config.getFunctionalCurrency().getCode());
		header.setFUNC_CUR_DEC_KETA(config.getFunctionalCurrency().getDecimalPoint());
	}

	/**
	 * ���ׂɍs�ԍ����Z�b�g
	 */
	public void setRowNo() {
		if (details != null) {
			// �ʏ�Ƌ@�\�ʉݎd��͕ʂ�
			int nNo = 1;
			int fNo = 1;
			for (SWK_DTL dtl : details) {
				dtl.setSWK_GYO_NO(dtl.isFunctionalCurrency() ? fNo++ : nNo++);
			}
		}
	}

	/**
	 * ��v�����Z�b�g
	 */
	public void setFiscalPeriod() {
		if (company != null) {
			setFiscalPeriod(company.getFiscalPeriod());
		}
	}

	/**
	 * ��v�����Z�b�g
	 * 
	 * @param fp ��v����
	 */
	public void setFiscalPeriod(FiscalPeriod fp) {
		if (fp == null || getSlipDate() == null) {
			return;
		}

		int year = fp.getFiscalYear(getHeader().getSWK_DEN_DATE());
		int month = fp.getFiscalMonth(getHeader().getSWK_DEN_DATE());
		if (details != null) {
			for (SWK_DTL detail : details) {
				detail.setSWK_NENDO(year);
				detail.setSWK_TUKIDO(month);
			}
		}
	}

	/**
	 * �`�[�E�v���A���ׂ��u�����N�̏ꍇ�ɖ��ׂɃZ�b�g����
	 */
	public void setHeaderRemarkToDetail() {
		SWK_HDR header_ = getHeader();
		if (header_ == null) {
			return;
		}
		if (details != null) {
			for (SWK_DTL detail : details) {
				if (Util.isNullOrEmpty(detail.getSWK_GYO_TEK())) {
					detail.setSWK_GYO_TEK(header_.getSWK_TEK());
				}
			}
		}
	}

	/**
	 * �N�[�ҏ��setter
	 * 
	 * @param user
	 */
	public void setIssuer(User user) {
		SWK_HDR header_ = getHeader();
		if (header_ != null) {
			// ��t����
			header_.setSWK_UKE_DEP_CODE(user.getDepartment().getCode());
			header_.setSWK_UKE_DEP_NAME(user.getDepartment().getName());
			header_.setSWK_UKE_DEP_NAME_S(user.getDepartment().getNames());
			// �˗�����
			header_.setSWK_IRAI_DEP_CODE(user.getDepartment().getCode());
			header_.setSWK_IRAI_DEP_NAME(user.getDepartment().getName());
			header_.setSWK_IRAI_DEP_NAME_S(user.getDepartment().getNames());
			// �˗��Ј�
			header_.setSWK_IRAI_EMP_CODE(user.getEmployee().getCode());
			header_.setSWK_IRAI_EMP_NAME(user.getEmployee().getName());
			header_.setSWK_IRAI_EMP_NAME_S(user.getEmployee().getNames());
		}
	}

	/**
	 * �`�[���(�f�[�^�敪�܂�)���Z�b�g
	 * 
	 * @param slipType �`�[���
	 */
	public void setSlipType(SlipType slipType) {
		header.setSlipType(slipType);

		for (SWK_DTL dtl : details) {

			if (slipType == null) {
				dtl.setDEN_SYU_CODE(null);
				dtl.setSWK_DATA_KBN(null);
			} else {
				dtl.setDEN_SYU_CODE(slipType.getCode());
				dtl.setSWK_DATA_KBN(slipType.getDataType());
			}
		}
	}

	/**
	 * �`�[�̐��^
	 */
	public void buildSlip() {

		// 1.�w�b�_�[�l�𖾍ׂɃV���N��
		synchDetails();
		setHeaderRemarkToDetail();

		// 2.����Ȗڂ̐ݒ�
		matchingItem();

		// 3.�s�ԍ�
		setRowNo();

		// 4.�N�x�A���x�̃Z�b�g
		setFiscalPeriod();
	}

	/**
	 * ����ʂɕ�������.<br>
	 * ���ׂ�������΋�̃��X�g
	 * 
	 * @return �����Slip���X�g
	 */
	public List<Slip> divide() {
		try {
			List<SWK_DTL> keycList = new ArrayList<SWK_DTL>();
			List<SWK_DTL> funcList = new ArrayList<SWK_DTL>();

			for (SWK_DTL dtl : details) {
				(dtl.isFunctionalCurrency() ? funcList : keycList).add(dtl);
			}

			List<Slip> slipList = new ArrayList<Slip>();

			// ��ʉ�
			if (!keycList.isEmpty()) {
				Slip slip = (Slip) super.clone();
				slip.header = this.header.clone();
				slip.details = keycList;
				slipList.add(slip);
			}

			// �@�\�ʉ�
			if (!funcList.isEmpty()) {
				Slip slip = (Slip) super.clone();
				slip.header = this.header.clone();
				slip.details = funcList;
				slipList.add(slip);
			}

			return slipList;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * Slip�̒��ɕ������낪���݂���ꍇ�A�w�蒠��A�w��ʉݒ��낾���̓`�[��Ԃ��B
	 * 
	 * @param accountBook ����
	 * @param currencyType �ʉ�
	 * @return ����ʓ`�[
	 */
	public Slip getSlip(AccountBook accountBook, CurrencyType currencyType) {
		try {
			Slip slip = (Slip) super.clone();
			slip.setHeader(this.getHeader());

			List<SWK_DTL> list = new ArrayList<SWK_DTL>();

			for (SWK_DTL detail : this.details) {

				if (accountBook != detail.getAccountBook() && accountBook != AccountBook.BOTH) {
					continue;
				}

				if (currencyType != detail.getCurrencyType()) {
					continue;
				}

				list.add(detail);
			}

			slip.setDetails(list);

			return slip;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * true:�K�{�`�F�b�N�s�v�̎擾
	 * 
	 * @return avoidReuiredItemNULL true:�K�{�`�F�b�N�s�v
	 */
	public boolean isAvoidReuiredItemNULL() {
		return avoidReuiredItemNULL;
	}

	/**
	 * true:�K�{�`�F�b�N�s�v�̐ݒ�
	 * 
	 * @param avoidReuiredItemNULL true:�K�{�`�F�b�N�s�v
	 */
	public void setAvoidReuiredItemNULL(boolean avoidReuiredItemNULL) {
		this.avoidReuiredItemNULL = avoidReuiredItemNULL;
	}

	/**
	 * �D�����Ẫ`�F�b�N���s����
	 * 
	 * @return true:�s��
	 */
	public boolean isChecksFleetMvmt() {
		return this.checksFleetMvmt;
	}

	/**
	 * �D�����Ẫ`�F�b�N���s����
	 * 
	 * @param isCheckFleetMvmt true:�s��
	 */
	public void setChecksFleetMvmt(boolean isCheckFleetMvmt) {
		this.checksFleetMvmt = isCheckFleetMvmt;
	}

	/**
	 * true:���V�X�e������f�[�^�̎擾
	 * 
	 * @return fromOtherSystem true:���V�X�e������f�[�^
	 */
	public boolean isFromOtherSystem() {
		return fromOtherSystem;
	}

	/**
	 * true:���V�X�e������f�[�^�̐ݒ�
	 * 
	 * @param fromOtherSystem true:���V�X�e������f�[�^
	 */
	public void setFromOtherSystem(boolean fromOtherSystem) {
		this.fromOtherSystem = fromOtherSystem;
	}

}
