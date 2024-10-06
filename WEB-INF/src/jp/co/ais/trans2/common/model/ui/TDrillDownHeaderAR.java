package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * ドリルダウンヘッダーAR
 * 
 * @author AIS
 */
public class TDrillDownHeaderAR extends TDrillDownHeader {

	/** 取引先コード */
	public TLabelField txTriCode;

	/** 取引先略称 */
	public TLabelField txTriNames;

	/** 入金日 */
	public TLabelField txArDate;

	/** 請求通貨 */
	public TLabelField txCurCode;

	/** 請求金額 */
	public TLabelField txArKin;

	/** 銀行口座 */
	public TLabelField txKoza;

	/**
	 * コンストラクタ
	 * 
	 * @param lang 言語コード
	 */
	public TDrillDownHeaderAR(String lang) {
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
		txArDate = new TLabelField();
		txCurCode = new TLabelField();
		txArKin = new TLabelField();
		txKoza = new TLabelField();
	}

	/**
	 * コンポーネントの配置を行います。
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();
		int x = 10;
		int y = txRemarkCode.getY() + txRemarkCode.getHeight() + 10;

		setMaximumSize(new Dimension(0, 155));
		setMinimumSize(new Dimension(0, 155));
		setPreferredSize(new Dimension(0, 155));

		// 取引先(コード)
		txTriCode.setEditable(false);
		txTriCode.setLabelSize(65);
		txTriCode.setFieldSize(80);
		txTriCode.setLangMessageID("C00408");
		txTriCode.setLocation(x, y);
		add(txTriCode);

		// 取引先(略称)
		txTriNames.setEditable(false);
		txTriNames.setLabelSize(0);
		txTriNames.setFieldSize(215);
		txTriNames.setLocation(x + txTriCode.getWidth() - 5, y);
		add(txTriNames);

		// 入金日
		txArDate.setEditable(false);
		txArDate.setLabelSize(80);
		txArDate.setFieldSize(75);
		txArDate.setLangMessageID("C01273");
		txArDate.getField().setHorizontalAlignment(0);
		txArDate.setLocation(txTriNames.getX() + txTriNames.getWidth() + 10, y);
		add(txArDate);

		// 請求金額(通貨)
		txCurCode.setEditable(false);
		txCurCode.setLabelSize(65);
		txCurCode.setFieldSize(60);
		txCurCode.getField().setHorizontalAlignment(0);
		txCurCode.setLangMessageID("C01689");
		txCurCode.setLocation(x, txTriCode.getY() + txTriCode.getHeight() + 1);
		add(txCurCode);

		// 請求金額
		txArKin.setEditable(false);
		txArKin.setLabelSize(0);
		txArKin.setFieldSize(235);
		txArKin.getField().setHorizontalAlignment(4);
		txArKin.setLocation(txCurCode.getX() + txCurCode.getWidth() - 5, txTriCode.getY() + txTriCode.getHeight() + 1);
		add(txArKin);

		// 銀行口座
		txKoza.setEditable(false);
		txKoza.setLangMessageID("C00857");
		txKoza.setLabelSize(80);
		txKoza.setFieldSize(225);
		txKoza.setLocation(txArDate.getX(), txArDate.getY() + txArDate.getHeight() + 1);
		add(txKoza);

		// 透過性をセット
		txTriCode.setOpaque(false);
		txTriNames.setOpaque(false);
		txArDate.setOpaque(false);
		txSNo.setOpaque(false);
		txCurCode.setOpaque(false);
		txArKin.setOpaque(false);
		txKoza.setOpaque(false);
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
		// 入金日
		txArDate.setValue(DateUtil.toYMDString(slip.getHeader().getSWK_AR_DATE()));
		// 請求金額(通貨)
		txCurCode.setValue(Util.avoidNull(slip.getHeader().getSWK_CUR_CODE()));
		// 請求金額
		txArKin.setValue(NumberFormatUtil.formatNumber(slip.getHeader().getSWK_IN_KIN(), slip.getHeader()
			.getSWK_CUR_DEC_KETA()));
		// 銀行口座
		txKoza.setValue(Util.avoidNull(slip.getHeader().getSWK_CBK_NAME()));
	}

}
