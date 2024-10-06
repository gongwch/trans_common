package jp.co.ais.trans.common.info;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * ���O�C�����[�U�̉�Џ��
 */
public class TCompanyInfo implements Serializable {

	/** ���v���׋敪: �g�p���Ȃ� */
	public static final int NON_ACCOUNTING_DETAIL_NONE = 0;

	/** ���v���׋敪: ���l */
	public static final int NON_ACCOUNTING_DETAIL_NUMBER = 1;

	/** ���v���׋敪: ������ */
	public static final int NON_ACCOUNTING_DETAIL_CHAR = 2;

	/** ���v���׋敪: ���t */
	public static final int NON_ACCOUNTING_DETAIL_DATE = 3;

	/** ���[�g���Z�[�������敪: �؎� */
	public static final int RATE_CF_DIV_ROUND_DOWN = 0;

	/** ���[�g���Z�[�������敪: �l�̌ܓ� */
	public static final int RATE_CF_DIV_HALF_ADJUST = 2;

	/** ��ЃR�[�h */
	private String kaiCode = "";

	/** ��Ж� */
	private String kaiName = "";

	/** ��З��� */
	private String kaiNameS = "";

	/** ��Дw�i�F */
	private String foreColor = "";

	/** �Ȗږ��� */
	private String itemName = "";

	/** �⏕�Ȗږ��� */
	private String subItemName = "";

	/** ����ȖڊǗ� �g�p���� */
	private boolean isUseBreakDownItem;

	/** ����Ȗږ��� */
	private String breakDownItemName = "";

	/** �Ǘ��敪1�`6 �g�p���� */
	private boolean[] isUseManageDivs = { true, true, true, true, true, true };

	/** �Ǘ��敪1�`6 ���� */
	private String[] manageDivNames = { "", "", "", "", "", "" };

	/** ���v���׋敪1�`3 */
	private int[] nonAccountingDetailDivs = { 1, 1, 1 };

	/** ���v����1�`3 ���� */
	private String[] nonAccountingDetailNames = { "", "", "", };

	/** ���� */
	private int beginningOfPeriodMonth = 4;

	/** ��ʉ݃R�[�h */
	private String baseCurrencyCode = "";

	/** �������ʎc���\����\�����邩�ǂ��� */
	private boolean isLedgerEachDayBalanceView;

	/** �ʉ݃R�[�h�����_���� */
	private Map<String, String> currencyMap = new TreeMap<String, String>();

	/** ���̑��f�[�^(�J�X�^�}�C�Y�p) */
	private Map<String, String> otherData = new HashMap<String, String>(0);

	/** ���[�g���Z�[�������敪 */
	private int rateKbn;

	/** ������̏����l */
	private int printDef;

	/** �`�[����敪 */
	private int printKbn;

	/** ���ڈ���敪 */
	private int directPrintKbn;

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return this.kaiCode;
	}

	/**
	 * ��ЃR�[�h�擾setter
	 * 
	 * @param kaiCode ��ЃR�[�h
	 */
	public void setCompanyCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	/**
	 * ��Ж��擾
	 * 
	 * @return ��Ж��擾
	 */
	public String getCompanyName() {
		return this.kaiName;
	}

	/**
	 * ��Ж��擾setter
	 * 
	 * @param kaiName ��Ж��擾
	 */
	public void setCompanyName(String kaiName) {
		this.kaiName = kaiName;
	}

	/**
	 * ��З��̎擾
	 * 
	 * @return ��З��̎擾
	 */
	public String getCompanyNameS() {
		return this.kaiNameS;
	}

	/**
	 * ��З��̎擾setter
	 * 
	 * @param kaiNameS ��З��̎擾
	 */
	public void setCompanyNameS(String kaiNameS) {
		this.kaiNameS = kaiNameS;
	}

	/**
	 * ��Дw�i�F
	 * 
	 * @return ��Дw�i�F
	 */
	public String getForeColor() {
		return this.foreColor;
	}

	/**
	 * ��Дw�i�Fsetter
	 * 
	 * @param foreColor ��Дw�i�F
	 */
	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}

	/**
	 * �Ȗږ���.<br>
	 * �u�Ȗځv�ȊO�̖��̂��g�p����ꍇ
	 * 
	 * @return �Ȗږ���.
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * �Ȗږ���
	 * 
	 * @param itemName �Ȗږ���
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * �⏕�Ȗږ���<br>
	 * �⏕�ȊO�̖��̂��g�p����ꍇ
	 * 
	 * @return �⏕�Ȗږ���
	 */
	public String getSubItemName() {
		return this.subItemName;
	}

	/**
	 * �⏕�Ȗږ���<br>
	 * �⏕�ȊO�̖��̂��g�p����ꍇ
	 * 
	 * @param subItemName �⏕�Ȗږ���
	 */
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	/**
	 * ����ȖڊǗ�(true:�g�p����, false:�g�p���Ȃ�)<br>
	 * ����Ȗ�Ͻ����g�p���Ȃ��ꍇ�A�e�`�[���͉�ʂ�����̓t�B�[���h�������A���[�o�͎��̕\�����������B
	 * 
	 * @return ����Ȗڔ���
	 */
	public boolean isUseBreakDownItem() {
		return this.isUseBreakDownItem;
	}

	/**
	 * ����ȖڊǗ� 0:�g�p���Ȃ�, 1:�g�p����
	 * 
	 * @param breakDownItem ����ȖڊǗ�
	 */
	public void setBreakDownItem(int breakDownItem) {
		isUseBreakDownItem = (0 != breakDownItem);
	}

	/**
	 * ����Ȗږ���
	 * 
	 * @param breakDownItemName ����Ȗږ���
	 */
	public void setBreakDownItemName(String breakDownItemName) {
		this.breakDownItemName = breakDownItemName;
	}

	/**
	 * ����Ȗږ���<br>
	 * ����ȊO�̖��̂��g�p����ꍇ
	 * 
	 * @return ����Ȗږ���
	 */
	public String getBreakDownItemName() {
		return this.breakDownItemName;
	}

	/**
	 * �Ǘ��敪1�`6 �g�p����(0:�g�p���Ȃ�, 1:�g�p����)
	 * 
	 * @param divs �Ǘ��敪1�`6 �g�p����
	 */
	public void setManageDivs(int[] divs) {
		for (int i = 0; i < divs.length; i++) {
			this.isUseManageDivs[i] = (0 != divs[i]);
		}
	}

	/**
	 * �Ǘ��敪1�`6 �g�p���� <br>
	 * �Ǘ��敪Ͻ����g�p���Ȃ��ꍇ�A�e�`�[���͉�ʂ�����̓t�B�[���h�������A���[�o�͎��̕\�����������B
	 * 
	 * @return �Ǘ��敪1�`6 �g�p����
	 */
	public boolean[] isUseManageDivs() {
		return this.isUseManageDivs;
	}

	/**
	 * �Ǘ�����1�`6
	 * 
	 * @param names �Ǘ�����1�`6
	 */
	public void setManageDivNames(String[] names) {
		this.manageDivNames = names;
	}

	/**
	 * �Ǘ�����1�`6 <br>
	 * �e�`�[���͉�ʂ̓���̨���ނɕ\�����A���[�o�͎��̷��߼�ݖ��ɕ\������B
	 * 
	 * @return �Ǘ�����1�`6
	 */
	public String[] getManageDivNames() {
		return this.manageDivNames;
	}

	/**
	 * ���v���ז��敪1�`3<br>
	 * 0:�g�p���Ȃ� 1:���l 2:���� 3:���t
	 * 
	 * @return ���v���ז��敪1�`3
	 */
	public int[] getNonAccountingDetailDivs() {
		return this.nonAccountingDetailDivs;
	}

	/**
	 * ���v���ז��敪1�`3<br>
	 * 0:�g�p���Ȃ� 1:���l 2:���� 3:���t
	 * 
	 * @param nonAccountingDetailDivs ���v���ז��敪1�`3
	 */
	public void setNonAccountingDetailDivs(int[] nonAccountingDetailDivs) {
		this.nonAccountingDetailDivs = nonAccountingDetailDivs;
	}

	/**
	 * ���v���ז���1�`3
	 * 
	 * @param names ���v���ז���1�`3
	 */
	public void setNonAccountingDetailNames(String[] names) {
		this.nonAccountingDetailNames = names;
	}

	/**
	 * ���v���ז���1�`3<br>
	 * �e�`�[���͉�ʂ̓���̨���ނɕ\�����A���[�o�͎��̷��߼�ݖ��ɕ\������B
	 * 
	 * @return ���v���ז���1�`3
	 */
	public String[] getNonAccountingDetailNames() {
		return this.nonAccountingDetailNames;
	}

	/**
	 * ����
	 * 
	 * @param beginningOfPeriodMonth
	 */
	public void setBeginningOfPeriodMonth(Integer beginningOfPeriodMonth) {
		this.beginningOfPeriodMonth = beginningOfPeriodMonth.intValue();
	}

	/**
	 * ����<br>
	 * �e�������\�̉�v����\������ۂɁA���������Nmm�� - ��Аݗ��N�i��Аݗ�����yyyy�j||����(mm) + 1�N +1���� =��yyyy��mm���x�Ƃ���
	 * 
	 * @return ����
	 */
	public int getBeginningOfPeriodMonth() {
		return this.beginningOfPeriodMonth;
	}

	/**
	 * ��ʉ݃R�[�h
	 * 
	 * @param baseCurrencyCode �ʉ݃R�[�h
	 */
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	/**
	 * ��ʉ݃R�[�h
	 * 
	 * @return ��ʉ݃R�[�h
	 */
	public String getBaseCurrencyCode() {
		return this.baseCurrencyCode;
	}

	/**
	 * ��ʉ݃R�[�h�����_����
	 * 
	 * @return ��ʉ݃R�[�h�����_����
	 */
	public int getBaseCurrencyDigit() {
		return getCurrencyDigit(this.baseCurrencyCode);
	}

	/**
	 * �������ʎc���\���̔���
	 * 
	 * @return true: �\������Afalse: �\�����Ȃ�
	 */
	public boolean isLedgerEachDayBalanceView() {
		return this.isLedgerEachDayBalanceView;
	}

	/**
	 * �������ʎc���\���̔���
	 * 
	 * @param isView �\�����f
	 */
	public void setLedgerEachDayBalanceView(boolean isView) {
		this.isLedgerEachDayBalanceView = isView;
	}

	/**
	 * ���[�g���Z�[�������敪.
	 * 
	 * @param rateKbn �萔:RATE_CF_DIV_XXX(0:�؎� 2:�l�̌ܓ�)
	 */
	public void setRateConversionFraction(int rateKbn) {
		this.rateKbn = rateKbn;
	}

	/**
	 * ���[�g���Z�[�������敪
	 * 
	 * @return 0:�؎� 2:�l�̌ܓ�
	 */
	public int getRateConversionFraction() {
		return this.rateKbn;
	}

	/**
	 * �w��ʉ݂ɑ΂��鏬���_������Ԃ�.
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @return �����_����
	 */
	public int getCurrencyDigit(String currencyCode) {

		if (!this.currencyMap.containsKey(currencyCode)) {
			return 0;
		}

		return Integer.parseInt(currencyMap.get(currencyCode));
	}

	/**
	 * �w��ʉ݂ɑ΂��鏬���_������ݒ�
	 * 
	 * @param map key:�ʉ݃R�[�h value:�����_����
	 */
	public void setCurrencyDigits(Map map) {
		this.currencyMap = map;
	}

	/**
	 * ������̏����l
	 * 
	 * @return ������̏����l
	 */
	public int getPrintDef() {
		return printDef;
	}

	/**
	 * @param printDef
	 */
	public void setPrintDef(int printDef) {
		this.printDef = printDef;
	}

	/**
	 * �`�[����敪
	 * 
	 * @return �`�[����敪
	 */
	public int getPrintKbn() {
		return printKbn;
	}

	/**
	 * �`�[����敪
	 * 
	 * @param printKbn �`�[����敪
	 */
	public void setPrintKbn(int printKbn) {
		this.printKbn = printKbn;
	}

	/**
	 * ���̑��f�[�^�̒ǉ�(�J�X�^�}�C�Y�p)
	 * 
	 * @param key �L�[
	 * @param value �l
	 */
	public void addData(String key, String value) {
		this.otherData.put(key, value);
	}

	/**
	 * ������ϊ�
	 * 
	 * @return �l�̕�����\��
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder("[");
		buff.append(Util.safeNull(kaiCode)).append("/");
		buff.append(Util.safeNull(kaiName)).append("/");
		buff.append(Util.safeNull(foreColor)).append("/");
		buff.append(Util.safeNull(itemName)).append("/");
		buff.append(Util.safeNull(subItemName)).append("/");
		buff.append(Util.safeNull(isUseBreakDownItem)).append("/");
		buff.append(Util.safeNull(breakDownItemName)).append("/");
		buff.append(StringUtil.toCommaString(isUseManageDivs)).append("/");
		buff.append(StringUtil.toCommaString(nonAccountingDetailDivs)).append("/");
		buff.append(StringUtil.toCommaString(nonAccountingDetailNames)).append("/");
		buff.append(String.valueOf(beginningOfPeriodMonth)).append("/");
		buff.append(Util.safeNull(baseCurrencyCode)).append("/");
		buff.append(String.valueOf(getBaseCurrencyDigit())).append("/");
		buff.append(Util.safeNull(isLedgerEachDayBalanceView));
		buff.append(String.valueOf(rateKbn));
		buff.append(String.valueOf(printDef));
		buff.append(String.valueOf(printKbn));
		buff.append(String.valueOf(directPrintKbn));
		buff.append("]");

		return buff.toString();
	}

	/**
	 * �f�[�^��String��Map�`���ɕϊ�
	 * 
	 * @return Map
	 */
	public Map<String, String> toStringMap() {

		Map<String, String> map = new TreeMap<String, String>();
		map.put("kaiCode", this.kaiCode); // ��ЃR�[�h
		map.put("kaiName", this.kaiName); // ��Ж�
		map.put("kaiNameS", this.kaiNameS); // ��З���
		map.put("foreColor", this.foreColor); // �w�i�F�ݒ�

		map.put("itemName", this.itemName); // �Ȗږ�
		map.put("subItemName", this.subItemName); // �⏕�Ȗږ�
		map.put("bdItemFlag", BooleanUtil.toString(this.isUseBreakDownItem)); // ����Ȗڗ��p�敪
		map.put("bdItemName", this.breakDownItemName); // ����Ȗږ�
		map.put("baseCurrency", this.baseCurrencyCode); // ��ʉ�

		// �Ǘ��敪1�`6�̎g�p����
		map.put("mgDivs", StringUtil.toDelimitString(this.isUseManageDivs));

		// �Ǘ��敪1�`6�̖���
		map.put("mgDivNames", StringUtil.toHTMLEscapeString(this.manageDivNames));

		// ���v���׋敪1�`3
		map.put("nonAcDetailDivs", StringUtil.toDelimitString(this.nonAccountingDetailDivs));

		// ���v���ז���1�`3
		map.put("nonAcDetailNames", StringUtil.toHTMLEscapeString(this.nonAccountingDetailNames));

		// ����
		map.put("beginningOfPeriodMonth", String.valueOf(this.beginningOfPeriodMonth));

		// �������ʎc���\��
		map.put("isLedgerEDBView", BooleanUtil.toString(this.isLedgerEachDayBalanceView));

		// ���[�g���Z�[�������敪
		map.put("rateKbn", String.valueOf(this.rateKbn));

		// ������̏����l
		map.put("printDef", String.valueOf(this.printDef));

		// �`�[����敪
		map.put("printKbn", String.valueOf(this.printKbn));

		// ���ڈ���敪
		map.put("directKbn", String.valueOf(this.directPrintKbn));

		// ���̑��f�[�^
		map.putAll(otherData);

		return map;
	}

	/**
	 * �f�[�^�𔽉f����.
	 * 
	 * @param map �f�[�^
	 */
	public void reflect(Map<String, String> map) {
		this.otherData = map;

		this.kaiCode = map.get("kaiCode"); // ��ЃR�[�h
		this.kaiName = map.get("kaiName"); // ��Ж�
		this.kaiNameS = map.get("kaiNameS"); // ��З���
		this.foreColor = map.get("foreColor"); // �w�i�F

		this.itemName = map.get("itemName"); // �u�Ȗځv����
		this.subItemName = map.get("subItemName"); // �u�⏕�Ȗځv����
		this.isUseBreakDownItem = BooleanUtil.toBoolean(map.get("bdItemFlag")); // ����Ȗڗ��p�敪
		this.breakDownItemName = map.get("bdItemName"); // �u����Ȗځv����
		this.baseCurrencyCode = map.get("baseCurrency"); // ��ʉ�

		// �Ǘ��敪1�`6�̎g�p����
		String mgDivs = map.get("mgDivs");
		this.isUseManageDivs = StringUtil.toBooleanArrayFromDelimitString(mgDivs);

		// �Ǘ��敪1�`6�̖���
		String mgDivNames = map.get("mgDivNames");
		this.manageDivNames = StringUtil.toArrayFromHTMLEscapeString(mgDivNames);

		// ���v���׋敪
		String nonAcDetailDivs = map.get("nonAcDetailDivs");
		this.nonAccountingDetailDivs = StringUtil.toIntArrayFromDelimitString(nonAcDetailDivs);

		// ���v���ז���
		String nonAcDetailNames = map.get("nonAcDetailNames");
		this.nonAccountingDetailNames = StringUtil.toArrayFromHTMLEscapeString(nonAcDetailNames);

		// ����
		try {
			this.beginningOfPeriodMonth = Integer.parseInt(map.get("beginningOfPeriodMonth"));
		} catch (NumberFormatException e) {
			this.beginningOfPeriodMonth = 4;
		}

		// �������ʎc���\���̔���
		this.isLedgerEachDayBalanceView = BooleanUtil.toBoolean(map.get("isLedgerEDBView"));

		// ���[�g���Z�[�������敪
		this.rateKbn = Integer.parseInt(map.get("rateKbn"));

		// ������̏����l
		this.printDef = Integer.parseInt(map.get("printDef"));

		// �`�[����敪
		this.printKbn = Integer.parseInt(map.get("printKbn"));

		// ���ڈ���敪
		this.directPrintKbn = Util.avoidNullAsInt(map.get("directKbn"));
	}

	/**
	 * �w��L�[�ɑ΂����Џ���Ԃ�.(�J�X�^�}�C�Y�p)
	 * 
	 * @param key �L�[
	 * @return ��Џ��
	 */
	public String getData(String key) {
		return this.otherData.get(key);
	}

	/**
	 * ���ڈ���敪 �擾
	 * 
	 * @return ���ڈ���敪
	 */
	public int getDirectPrintKbn() {
		return directPrintKbn;
	}

	/**
	 * ���ڈ���敪 �ݒ�
	 * 
	 * @param directPrintKbn ���ڈ���敪
	 */
	public void setDirectPrintKbn(int directPrintKbn) {
		this.directPrintKbn = directPrintKbn;
	}

}
