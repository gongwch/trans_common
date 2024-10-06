package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * ドリルダウンヘッダーAP
 * 
 * @author AIS
 */
public class TDrillDownHeaderAP extends TDrillDownHeader {

	/** 支払先コード */
	public TLabelField txTriCode;

	/** 支払先略称 */
	public TLabelField txTriNames;

	/** 支払日 */
	public TLabelField txSihaDate;

	/** 「定時」or「臨時」 */
	public TLabelField txSihaKbn;

	/** 支払銀行 */
	public TLabelField txHisimukeBnk;

	/** 支払方法コード */
	public TLabelField txHohCode;

	/** 支払方法略称 */
	public TLabelField txHohName;

	/** レート */
	public TLabelField txRate;

	/** 支払金額(外貨) */
	public TLabelField txSihaInKin;

	/** 支払通貨 */
	public TLabelField txCurCode;

	/** 支払金額(基軸通貨) */
	public TLabelField txSihaKin;

	/** 引落銀行口座 */
	public TLabelField txSimukeBnk;

	/**
	 * コンストラクタ
	 * 
	 * @param lang 言語コード
	 */
	public TDrillDownHeaderAP(String lang) {
		super(lang);
	}

	/**
	 * コンポーネントの初期設定
	 */
	@Override
	public void initComponents() {
		super.initComponents();
		txTriCode = new TLabelField();
		txTriNames = new TLabelField();
		txSihaDate = new TLabelField();
		txSihaKbn = new TLabelField();
		txHisimukeBnk = new TLabelField();
		txHohCode = new TLabelField();
		txHohName = new TLabelField();
		txRate = new TLabelField();
		txSihaInKin = new TLabelField();
		txCurCode = new TLabelField();
		txSihaKin = new TLabelField();
		txSimukeBnk = new TLabelField();
	}

	/**
	 * コンポーネントの配置を行います。
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();
		int x = 10;
		int y = txRemarkCode.getY() + txRemarkCode.getHeight() + 10;

		setMaximumSize(new Dimension(0, 175));
		setMinimumSize(new Dimension(0, 175));
		setPreferredSize(new Dimension(0, 175));

		// 支払先(コード)
		txTriCode.setEditable(false);
		txTriCode.setLabelSize(65);
		txTriCode.setFieldSize(80);
		txTriCode.setLangMessageID("C01690");
		txTriCode.setLocation(x, y);
		add(txTriCode);

		// 支払先(略称)
		txTriNames.setEditable(false);
		txTriNames.setLabelSize(0);
		txTriNames.setFieldSize(220);
		txTriNames.setLocation(x + txTriCode.getWidth() - 5, y);
		add(txTriNames);

		// 支払日
		txSihaDate.setEditable(false);
		txSihaDate.setFieldSize(75);
		txSihaDate.setLabelSize(85);
		txSihaDate.setLangMessageID("C01098");
		txSihaDate.getField().setHorizontalAlignment(0);
		txSihaDate.setLocation(txTriNames.getX() + txTriNames.getWidth() + 70, y);
		add(txSihaDate);

		// 支払区分
		txSihaKbn.setEditable(false);
		txSihaKbn.setLabelSize(0);
		txSihaKbn.setFieldSize(40);
		txSihaKbn.getField().setHorizontalAlignment(0);
		txSihaKbn.setLocation(txSihaDate.getX() + txSihaDate.getWidth() - 5, y);
		add(txSihaKbn);

		// 支払銀行
		txHisimukeBnk.setEditable(false);
		txHisimukeBnk.setFieldSize(300);
		txHisimukeBnk.setLabelSize(65);
		txHisimukeBnk.setLangMessageID("C01637");
		txHisimukeBnk.setLocation(x, y + txTriCode.getHeight() + 1);
		add(txHisimukeBnk);

		// 支払方法(コード)
		txHohCode.setEditable(false);
		txHohCode.setFieldSize(25);
		txHohCode.setLabelSize(85);
		txHohCode.setLangMessageID("C00233");
		txHohCode.setLocation(txSihaDate.getX(), y + txSihaDate.getHeight() + 1);
		add(txHohCode);

		// 支払方法略称
		txHohName.setEditable(false);
		txHohName.setLabelSize(0);
		txHohName.setFieldSize(200);
		txHohName.setLocation(txHohCode.getX() + txHohCode.getWidth() - 5, y + txSihaDate.getHeight() + 1);
		add(txHohName);

		// 支払金額(レート)
		txRate.setEditable(false);
		txRate.setFieldSize(100);
		txRate.setLabelSize(65);
		txRate.getField().setHorizontalAlignment(4);
		txRate.setLangMessageID("C00229");
		txRate.setLocation(x, txHisimukeBnk.getY() + txHisimukeBnk.getHeight() + 1);
		add(txRate);

		// 支払金額(外貨)
		txSihaInKin.setEditable(false);
		txSihaInKin.setFieldSize(100);
		txSihaInKin.setLabelSize(0);
		txSihaInKin.getField().setHorizontalAlignment(4);
		txSihaInKin.setLocation(txRate.getX() + txRate.getWidth() - 5, txHisimukeBnk.getY() + txHisimukeBnk.getHeight()
			+ 1);
		add(txSihaInKin);

		// 支払金額(基軸通貨)
		txSihaKin.setEditable(false);
		txSihaKin.setFieldSize(100);
		txSihaKin.setLabelSize(0);
		txSihaKin.getField().setHorizontalAlignment(4);
		txSihaKin.setLocation(txSihaInKin.getX() + txSihaInKin.getWidth() - 5,
			txHisimukeBnk.getY() + txHisimukeBnk.getHeight() + 1);
		add(txSihaKin);

		// 支払金額(通貨)
		txCurCode.setEditable(false);
		txCurCode.setFieldSize(60);
		txCurCode.setLabelSize(0);
		txCurCode.getField().setHorizontalAlignment(0);
		txCurCode.setLocation(txSihaKin.getX() + txSihaKin.getWidth() - 5,
			txHisimukeBnk.getY() + txHisimukeBnk.getHeight() + 1);
		add(txCurCode);

		// 振出銀行口座
		txSimukeBnk.setEditable(false);
		txSimukeBnk.setLangMessageID("C00475");
		txSimukeBnk.setLabelSize(85);
		txSimukeBnk.setFieldSize(225);
		txSimukeBnk.setLocation(txHohCode.getX(), txHohCode.getY() + txHohCode.getHeight() + 1);
		add(txSimukeBnk);

		// 透過性をセット
		txTriCode.setOpaque(false);
		txTriNames.setOpaque(false);
		txSihaDate.setOpaque(false);
		txSihaKbn.setOpaque(false);
		txSNo.setOpaque(false);
		txHisimukeBnk.setOpaque(false);
		txHohCode.setOpaque(false);
		txHohName.setOpaque(false);
		txRate.setOpaque(false);
		txSihaInKin.setOpaque(false);
		txSihaKin.setOpaque(false);
		txCurCode.setOpaque(false);
		txSimukeBnk.setOpaque(false);

	}

	/**
	 * 伝票ヘッダーをセットします。
	 * 
	 * @param slip
	 */
	@Override
	public void setSlipHeader(Slip slip) throws TException {
		super.setSlipHeader(slip);
		// 支払先コード
		txTriCode.setValue(Util.avoidNull(slip.getHeader().getSWK_TRI_CODE()));
		// 支払先略称
		txTriNames.setValue(Util.avoidNull(slip.getHeader().getSWK_TRI_NAME_S()));
		// 支払日
		txSihaDate.setValue(DateUtil.toYMDString(slip.getHeader().getSWK_SIHA_DATE()));
		// 支払区分
		txSihaKbn.setValue(MessageUtil.getWord(lang, PaymentDateType.getPaymentDateTypeName(Util.avoidNull(slip.getHeader().getSWK_SIHA_KBN()))));
		// 支払銀行
		txHisimukeBnk.setValue(Util.avoidNull(slip.getHeader().getSWK_TJK_BNK_NAME_S()) + " "
			+ Util.avoidNull(slip.getHeader().getSWK_TJK_BNK_STN_NAME_S()) + " "
			+ TModelUIUtil.getWord(DepositKind.getDepositKindName(Util.avoidNullAsInt(slip.getHeader().getSWK_TJK_YKN_KBN()))) + " "
			+ Util.avoidNull(slip.getHeader().getSWK_TJK_YKN_NO()));
		// 支払方法コード
		txHohCode.setValue(Util.avoidNull(slip.getHeader().getSWK_HOH_CODE()));
		// 支払方法略称
		txHohName.setValue(Util.avoidNull(slip.getHeader().getSWK_HOH_NAME()));
		// 支払金額(レート)
		txRate.setValue(FormatUtil.rateFormat(slip.getHeader().getKEY_CUR_CODE(), slip.getHeader().getSWK_CUR_CODE(),
			slip.getHeader().getSWK_CUR_RATE()));
		// 支払金額(外貨)
		txSihaInKin.setValue(FormatUtil.foreingAmountFormat(slip.getHeader().getKEY_CUR_CODE(), slip.getHeader()
			.getSWK_CUR_CODE(), slip.getHeader().getSWK_IN_SIHA_KIN(), slip.getHeader().getSWK_CUR_DEC_KETA()));
		// 支払金額(基軸通貨)
		txSihaKin.setValue(NumberFormatUtil.formatNumber(slip.getHeader().getSWK_SIHA_KIN(), slip.getHeader()
			.getKEY_CUR_DEC_KETA()));
		// 支払通貨
		txCurCode.setValue(Util.avoidNull(slip.getHeader().getSWK_CUR_CODE()));
		// 引落銀行口座
		txSimukeBnk.setValue(Util.avoidNull(slip.getHeader().getSWK_CBK_NAME()));
	}
}
