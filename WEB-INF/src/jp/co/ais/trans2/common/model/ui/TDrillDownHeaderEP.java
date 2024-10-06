package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * ドリルダウンヘッダーEP
 * 
 * @author AIS
 */
public class TDrillDownHeaderEP extends TDrillDownHeader {

	/** 社員コード */
	public TLabelField txEmpCode;

	/** 社員略称 */
	public TLabelField txEmpNames;

	/** 経費等合計 */
	public TLabelField txSettlementSum;

	/** 支払日 */
	public TLabelField txPayDay;

	/** 仮払 通貨コード */
	public TLabelField txTmpCurCode;

	/** 仮払 レート */
	public TLabelField txTmpRate;

	/** 仮払 入力金額 */
	public TLabelField txTmpInAmount;

	/** 仮払 邦貨金額 */
	public TLabelField txTmpKeyAmount;

	/** 支払方法コード */
	public TLabelField txPayMethodCode;

	/** 支払方法略称 */
	public TLabelField txPayMethodNames;

	/** 精算 通貨コード */
	public TLabelField txSettlementCurCode;

	/** 精算 レート */
	public TLabelField txSettlementRate;

	/** 精算 入力金額 */
	public TLabelField txSettlementInAmount;

	/** 精算 邦貨金額 */
	public TLabelField txSettlementKeyAmount;

	/** 銀行名 */
	public TLabelField txBnkName;

	/**
	 * コンストラクタ
	 * 
	 * @param lang 言語コード
	 */
	public TDrillDownHeaderEP(String lang) {
		super(lang);
	}

	/**
	 * コンポーネント初期設定
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		txEmpCode = new TLabelField();
		txEmpNames = new TLabelField();
		txSettlementSum = new TLabelField();
		txPayDay = new TLabelField();
		txTmpCurCode = new TLabelField();
		txTmpRate = new TLabelField();
		txTmpInAmount = new TLabelField();
		txTmpKeyAmount = new TLabelField();
		txPayMethodCode = new TLabelField();
		txPayMethodNames = new TLabelField();
		txSettlementCurCode = new TLabelField();
		txSettlementRate = new TLabelField();
		txSettlementInAmount = new TLabelField();
		txSettlementKeyAmount = new TLabelField();
		txBnkName = new TLabelField();

	}

	/**
	 * コンポーネント配置
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();
		int x = 10;
		int y = txRemarkCode.getY() + txRemarkCode.getHeight() + 10;

		setMaximumSize(new Dimension(0, 190));
		setMinimumSize(new Dimension(0, 190));
		setPreferredSize(new Dimension(0, 190));

		// 社員コード
		txEmpCode.setEditable(false);
		txEmpCode.setLabelSize(65);
		txEmpCode.setFieldSize(80);
		txEmpCode.setLangMessageID("C00246");
		txEmpCode.setLocation(x, y);
		add(txEmpCode);

		// 社員略称
		txEmpNames.setEditable(false);
		txEmpNames.setLabelSize(0);
		txEmpNames.setFieldSize(135);
		txEmpNames.setLocation(x + txEmpCode.getWidth() - 5, y);
		add(txEmpNames);

		// 経費等合計
		txSettlementSum.setEditable(false);
		txSettlementSum.setLabelSize(320);
		txSettlementSum.setFieldSize(80);
		txSettlementSum.setLangMessageID("C04282");
		txSettlementSum.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth(), y);
		add(txSettlementSum);

		// 支払日
		txPayDay.setEditable(false);
		txPayDay.setLabelSize(65);
		txPayDay.setFieldSize(80);
		txPayDay.setLangMessageID("C01098");
		txPayDay.setLocation(x, y + txEmpCode.getHeight() + 1);
		add(txPayDay);

		// 仮払 通貨コード
		txTmpCurCode.setEditable(false);
		txTmpCurCode.setLabelSize(120);
		txTmpCurCode.setFieldSize(40);
		txTmpCurCode.setLangMessageID("C04239");
		txTmpCurCode.getField().setHorizontalAlignment(0);
		txTmpCurCode.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth(), y + txEmpCode.getHeight() + 1);
		add(txTmpCurCode);

		// 仮払 レート
		txTmpRate.setEditable(false);
		txTmpRate.setLabelSize(0);
		txTmpRate.setFieldSize(80);
		txTmpRate.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth() + txTmpCurCode.getWidth() - 5, y
			+ txEmpCode.getHeight() + 1);
		add(txTmpRate);

		// 仮払 入力金額
		txTmpInAmount.setEditable(false);
		txTmpInAmount.setLabelSize(0);
		txTmpInAmount.setFieldSize(80);
		txTmpInAmount.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth() + txTmpCurCode.getWidth()
			+ txTmpRate.getWidth() - 5 * 2, y + txEmpCode.getHeight() + 1);
		add(txTmpInAmount);

		// 仮払 邦貨金額
		txTmpKeyAmount.setEditable(false);
		txTmpKeyAmount.setLabelSize(0);
		txTmpKeyAmount.setFieldSize(80);
		txTmpKeyAmount.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth() + txTmpCurCode.getWidth()
			+ txTmpRate.getWidth() + txTmpInAmount.getWidth() - 5 * 3, y + txEmpCode.getHeight() + 1);
		add(txTmpKeyAmount);

		// 支払方法コード
		txPayMethodCode.setEditable(false);
		txPayMethodCode.setLabelSize(65);
		txPayMethodCode.setFieldSize(30);
		txPayMethodCode.setLangMessageID("C00233");
		txPayMethodCode.setLocation(x, y + txEmpCode.getHeight() * 2 + 1 * 2);
		add(txPayMethodCode);

		// 支払方法略称
		txPayMethodNames.setEditable(false);
		txPayMethodNames.setLabelSize(0);
		txPayMethodNames.setFieldSize(185);
		txPayMethodNames.setLocation(x + txPayMethodCode.getWidth() - 5, y + txEmpCode.getHeight() * 2 + 1 * 2);
		add(txPayMethodNames);

		// 精算 通貨コード
		txSettlementCurCode.setEditable(false);
		txSettlementCurCode.setLabelSize(120);
		txSettlementCurCode.setFieldSize(40);
		txSettlementCurCode.setLangMessageID("C01707");
		txSettlementCurCode.getField().setHorizontalAlignment(0);
		txSettlementCurCode.setLocation(x + txPayMethodCode.getWidth() + txPayMethodNames.getWidth(),
			y + txEmpCode.getHeight() * 2 + 1 * 2);
		add(txSettlementCurCode);

		// 精算 レート
		txSettlementRate.setEditable(false);
		txSettlementRate.setLabelSize(0);
		txSettlementRate.setFieldSize(80);
		txSettlementRate.setLocation(
			x + txPayMethodCode.getWidth() + txPayMethodNames.getWidth() + txSettlementCurCode.getWidth() - 5, y
				+ txEmpCode.getHeight() * 2 + 1 * 2);
		add(txSettlementRate);

		// 精算 入力金額
		txSettlementInAmount.setEditable(false);
		txSettlementInAmount.setLabelSize(0);
		txSettlementInAmount.setFieldSize(80);
		txSettlementInAmount.setLocation(x + txPayMethodCode.getWidth() + txPayMethodNames.getWidth()
			+ txSettlementCurCode.getWidth() + txSettlementRate.getWidth() - 5 * 2, y + txEmpCode.getHeight() * 2 + 1
			* 2);
		add(txSettlementInAmount);

		// 精算 邦貨金額
		txSettlementKeyAmount.setEditable(false);
		txSettlementKeyAmount.setLabelSize(0);
		txSettlementKeyAmount.setFieldSize(80);
		txSettlementKeyAmount.setLocation(x + txPayMethodCode.getWidth() + txPayMethodNames.getWidth()
			+ txSettlementCurCode.getWidth() + txSettlementRate.getWidth() + txSettlementInAmount.getWidth() - 5 * 3, y
			+ txEmpCode.getHeight() * 2 + 1 * 2);
		add(txSettlementKeyAmount);

		// 振出銀行 銀行名
		txBnkName.setEditable(false);
		txBnkName.setLabelSize(65);
		txBnkName.setFieldSize(625);
		txBnkName.setLangMessageID("C01634");
		txBnkName.setLocation(x, y + txEmpCode.getHeight() * 3 + 3);
		add(txBnkName);

		// 透過性セット
		txEmpCode.setOpaque(false);
		txEmpNames.setOpaque(false);
		txSettlementSum.setOpaque(false);
		txPayDay.setOpaque(false);
		txTmpCurCode.setOpaque(false);
		txTmpRate.setOpaque(false);
		txTmpInAmount.setOpaque(false);
		txTmpKeyAmount.setOpaque(false);
		txPayMethodCode.setOpaque(false);
		txPayMethodNames.setOpaque(false);
		txSettlementCurCode.setOpaque(false);
		txSettlementRate.setOpaque(false);
		txSettlementInAmount.setOpaque(false);
		txSettlementKeyAmount.setOpaque(false);
		txBnkName.setOpaque(false);

	}

	/**
	 * 伝票ヘッダーセット
	 * 
	 * @param slip
	 */
	@Override
	public void setSlipHeader(Slip slip) throws TException {
		super.setSlipHeader(slip);
		
		// 伝票ヘッダを取得
		SWK_HDR header = slip.getHeader();

		// 社員コード
		txEmpCode.setValue(Util.avoidNull(header.getSWK_EMP_CODE()));
		// 社員略称
		txEmpNames.setValue(Util.avoidNull(header.getSWK_EMP_NAME_S()));
		// 経費等合計
		txSettlementSum.setValue(NumberFormatUtil.formatNumber(header.getSWK_KEIHI_KIN()));
		// 支払日
		txPayDay.setValue(DateUtil.toYMDString(header.getSWK_SIHA_DATE()));
		// 支払方法コード
		txPayMethodCode.setValue(Util.avoidNull(header.getSWK_HOH_CODE()));
		// 支払方法略称
		txPayMethodNames.setValue(Util.avoidNull(header.getSWK_HOH_NAME()));

		// 伝票の通貨が基軸通貨か
		boolean isKeyCurrency = header.getSWK_CUR_CODE().equals(header.getKEY_CUR_CODE());

		// 仮払伝票か
		if (slip.getSlipType().equals("021")) {
			
			if (!isKeyCurrency) {
				// 外貨の場合
				// 仮払伝票 通貨コード
				txTmpCurCode.setValue(Util.avoidNull(header.getSWK_CUR_CODE()));
				// 仮払伝票 レート
				txTmpRate.setValue(FormatUtil.rateFormat(header.getSWK_CUR_RATE()));
				// 仮払伝票 入力金額
				txTmpInAmount.setValue(FormatUtil.foreingAmountFormat(header.getKEY_CUR_CODE(), header.getSWK_CUR_CODE(), header.getSWK_IN_SIHA_KIN(), header
					.getSWK_CUR_DEC_KETA()));
			} 
			
			// 仮払伝票 邦貨金額
			txTmpKeyAmount.setValue(NumberFormatUtil.formatNumber(header.getSWK_SIHA_KIN(), header.getKEY_CUR_DEC_KETA()));
		
		} 
		// 仮払伝票でなければ精算伝票
		else {
			// 仮払がある場合のみ表示
			if (!DecimalUtil.isNullOrZero(header.getSWK_KARI_KIN())) {
				
				if (!header.getSWK_KARI_CUR_CODE().equals(header.getKEY_CUR_CODE())) {
					// 外貨の場合
					// 仮払 通貨コード
					txTmpCurCode.setValue(Util.avoidNull(header.getSWK_KARI_CUR_CODE()));
					// 仮払 レート
					txTmpRate.setValue(FormatUtil.rateFormat(header.getSWK_KARI_CUR_RATE()));
					// 仮払 入力金額
					txTmpInAmount.setValue(FormatUtil.foreingAmountFormat(header.getKEY_CUR_CODE(), header.getSWK_KARI_CUR_CODE(), header.getSWK_IN_KARI_KIN(), header
						.getSWK_CUR_DEC_KETA()));
				} 
				// 仮払 邦貨金額
				txTmpKeyAmount.setValue(NumberFormatUtil.formatNumber(header.getSWK_KARI_KIN(), header
					.getKEY_CUR_DEC_KETA()));
			}

			// 精算金額 0の場合非表示
			if (!DecimalUtil.isNullOrZero(header.getSWK_SIHA_KIN())) {
				
				if (!isKeyCurrency) {
					// 外貨の場合
					// 精算 通貨コード
					txSettlementCurCode.setValue(Util.avoidNull(header.getSWK_CUR_CODE()));
					// 精算 レート
					txSettlementRate.setValue(FormatUtil.rateFormat(header.getSWK_CUR_RATE()));
					// 精算 入力
					txSettlementInAmount.setValue(FormatUtil.foreingAmountFormat(header.getKEY_CUR_CODE(), header.getSWK_CUR_CODE(), header.getSWK_IN_SIHA_KIN(), header
						.getSWK_CUR_DEC_KETA()));
				} 
				// 精算 邦貨
				txSettlementKeyAmount.setValue(NumberFormatUtil.formatNumber(header.getSWK_SIHA_KIN(), header.getKEY_CUR_DEC_KETA()));
			}
		}

		// 振出銀行 銀行名
		txBnkName.setValue(Util.avoidNull(header.getSWK_BNK_NAME_S()) + " "
			+ Util.avoidNull(header.getSWK_BNK_STN_NAME_S()) + " " // 振出銀行 支店名
			+ Util.avoidNull(TModelUIUtil.getWord(DepositKind.getDepositKindName(header.getSWK_CBK_YKN_KBN()))) + " " // 振出銀行 預金種目
			+ Util.avoidNull(header.getSWK_CBK_CODE())); // 振出銀行 振出銀行口座

	}
}
