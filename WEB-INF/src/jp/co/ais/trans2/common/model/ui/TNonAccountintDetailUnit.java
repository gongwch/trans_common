package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.text.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 非会計明細フィールド
 * 
 * @author ais
 */
public class TNonAccountintDetailUnit extends TPanel {

	/** ベースパネル */
	protected TPanel pnlBase;

	/** 非会計明細数値 */
	protected TLabelNumericField[] ctrlNums = new TLabelNumericField[3];

	/** 非会計明細文字 */
	protected TLabelField[] ctrlTexts = new TLabelField[3];

	/** 非会計明細日付 */
	protected TLabelPopupCalendar[] ctrlDates = new TLabelPopupCalendar[3];

	/** 非会計明細区分 */
	protected NonAccountingDivision[] divs;

	/** フィールドサイズ */
	protected static final int SIZE_FIELD = 90;

	/** フィールドサイズ 桁数60パターン */
	protected static final int SIZE_FIELD_60 = 480;

	/** ラベルサイズ */
	protected static final int SIZE_LABEL = 80;

	/** 入力最大桁数 */
	protected static final int LENGTH = 10;

	/** 入力最大桁数 60パターン */
	protected static final int LENGTH_60 = 60;

	/** 非会計明細サイズをデフォルト設定にする */
	public boolean isDefaultSize = ClientConfig.isFlagOn("trans.use.non.account.detail.default.size");

	/**
	 * コンストラクタ
	 */
	public TNonAccountintDetailUnit() {
		initComponents();
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {

		setLayout(new BorderLayout());
		if (isDefaultSize) {
			TGuiUtil.setComponentSize(this, new Dimension(480, 60));
		} else {
			TGuiUtil.setComponentSize(this, new Dimension(600, 60));
		}

		pnlBase = new TPanel();
		pnlBase.setLayout(new GridBagLayout());

		AccountConfig conf = TLoginInfo.getCompany().getAccountConfig();

		if (Util.isNullOrEmpty(conf)) {
			return;
		}

		divs = new NonAccountingDivision[] { conf.getNonAccounting1(), conf.getNonAccounting2(),
				conf.getNonAccounting3() };

		// 非会計明細1～3
		setHMField(1, conf.getNonAccounting1Name());
		setHMField(2, conf.getNonAccounting2Name());
		setHMField(3, conf.getNonAccounting3Name());

		add(pnlBase, BorderLayout.NORTH);
	}

	/**
	 * 非会計明細フィールド構成
	 * 
	 * @param num 明細番号
	 * @param name フィールド名称
	 */
	protected void setHMField(int num, String name) {

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = num;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 10);

		switch (divs[num - 1]) {
			case NUMBER: // 数値
				ctrlNums[num - 1] = new TLabelNumericField();
				if (isDefaultSize) {
					ctrlNums[num - 1].setFieldSize(SIZE_FIELD);
				} else {
					ctrlNums[num - 1].setFieldSize(SIZE_FIELD_60);
				}
				ctrlNums[num - 1].setLabelSize(SIZE_LABEL);
				ctrlNums[num - 1].setLabelText(name);
				if (isDefaultSize) {
					ctrlNums[num - 1].setMaxLength(LENGTH);
				} else {
					ctrlNums[num - 1].setMaxLength(LENGTH_60);
				}
				ctrlNums[num - 1].setNumericFormat("#,###.##########");
				ctrlNums[num - 1].setChangeRedOfMinus(true);

				pnlBase.add(ctrlNums[num - 1], gridBagConstraints);
				break;

			case CHAR: // 文字
				ctrlTexts[num - 1] = new TLabelField();
				if (isDefaultSize) {
					ctrlTexts[num - 1].setFieldSize(SIZE_FIELD);
				} else {
					ctrlTexts[num - 1].setFieldSize(SIZE_FIELD_60);
				}
				ctrlTexts[num - 1].setLabelSize(SIZE_LABEL);
				ctrlTexts[num - 1].setLabelText(name);
				if (isDefaultSize) {
					ctrlTexts[num - 1].setMaxLength(LENGTH);
				} else {
					ctrlTexts[num - 1].setMaxLength(LENGTH_60);
				}

				pnlBase.add(ctrlTexts[num - 1], gridBagConstraints);
				break;

			case YMD_DATE: // 日付(YYYY/MM/DD)
				ctrlDates[num - 1] = new TLabelPopupCalendar();
				ctrlDates[num - 1].setLabelSize(SIZE_LABEL);
				ctrlDates[num - 1].setLabelText(name);
				ctrlDates[num - 1].setAllowableBlank(true);
				ctrlDates[num - 1].setValue(null);

				gridBagConstraints.gridwidth = 5;
				pnlBase.add(ctrlDates[num - 1], gridBagConstraints);
				break;

			case YMDHM_DATE: // 日付(YYYY/MM/DD HH:MM)
				ctrlDates[num - 1] = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMDHM);
				// ctrlDates[num - 1].setCalendarSize(105);
				ctrlDates[num - 1].setLabelSize(SIZE_LABEL);
				ctrlDates[num - 1].setLabelText(name);
				ctrlDates[num - 1].setAllowableBlank(true);
				ctrlDates[num - 1].setValue(null);

				gridBagConstraints.gridwidth = 5;
				pnlBase.add(ctrlDates[num - 1], gridBagConstraints);
				break;

			default:
				break;
		}
	}

	/**
	 * 値取得
	 * 
	 * @param num 非会計明細番号
	 * @return 値
	 */
	public String getValue(int num) {

		switch (divs[num - 1]) {
			case NUMBER:
				return ctrlNums[num - 1].getInputText();

			case CHAR:
				return ctrlTexts[num - 1].getValue();

			case YMD_DATE:
				return DateUtil.toYMDString(ctrlDates[num - 1].getValue());

			case YMDHM_DATE:
				return DateUtil.toYMDHMString(ctrlDates[num - 1].getValue());
		}

		return null;
	}

	/**
	 * 値設定
	 * 
	 * @param num 非会計明細番号
	 * @param value 値
	 */
	public void setValue(int num, String value) {
		try {
			switch (divs[num - 1]) {
				case NUMBER:
					ctrlNums[num - 1].setValue(value);
					break;

				case CHAR:
					ctrlTexts[num - 1].setValue(value);
					break;

				case YMD_DATE:
					ctrlDates[num - 1].setValue(DateUtil.toYMDDate(value));
					break;

				case YMDHM_DATE:
					ctrlDates[num - 1].setValue(DateUtil.toYMDHMDate(value));
					break;
			}

		} catch (ParseException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 入力制御
	 * 
	 * @param num 非会計明細番号
	 * @param isEdit true:操作可 false:操作不可
	 */
	public void setEditable(int num, boolean isEdit) {

		switch (divs[num - 1]) {
			case NUMBER:
				ctrlNums[num - 1].setEditable(isEdit);
				break;

			case CHAR:
				ctrlTexts[num - 1].setEditable(isEdit);
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				ctrlDates[num - 1].setEnabled(isEdit);
				ctrlDates[num - 1].setEditable(isEdit);
				break;
		}
	}

	// 以下コンポーネントの取得---------------------------------------------------------

	/**
	 * パネル取得
	 * 
	 * @return パネル
	 */
	public TPanel getPanel() {
		return pnlBase;
	}

	/**
	 * 値設定
	 * 
	 * @param num 非会計明細番号
	 * @return フィールド
	 */
	public TPanel getHmField(int num) {
		switch (divs[num - 1]) {
			case NUMBER:
				return ctrlNums[num - 1];

			case CHAR:
				return ctrlTexts[num - 1];

			case YMD_DATE:
			case YMDHM_DATE:
				return ctrlDates[num - 1];

		}
		return null;
	}

	/**
	 * 非会計明細１コンポーネント取得
	 * 
	 * @return 非会計明細１コンポーネント
	 */
	public TLabelNumericField getHm1Num() {
		return ctrlNums[0];
	}

	/**
	 * 非会計明細１コンポーネント取得
	 * 
	 * @return 非会計明細１コンポーネント
	 */
	public TLabelField getHm1Text() {
		return ctrlTexts[0];
	}

	/**
	 * 非会計明細1コンポーネント取得
	 * 
	 * @return 非会計明細1コンポーネント
	 */
	public TLabelPopupCalendar getHm1Date() {
		return ctrlDates[0];
	}

	/**
	 * 非会計明細２コンポーネント取得
	 * 
	 * @return 非会計明細２コンポーネント
	 */
	public TLabelNumericField getHm2Num() {
		return ctrlNums[1];
	}

	/**
	 * 非会計明細２コンポーネント取得
	 * 
	 * @return 非会計明細２コンポーネント
	 */
	public TLabelField getHm2Text() {
		return ctrlTexts[1];
	}

	/**
	 * 非会計明細２コンポーネント取得
	 * 
	 * @return 非会計明細２コンポーネント
	 */
	public TLabelPopupCalendar getHm2Date() {
		return ctrlDates[1];
	}

	/**
	 * 非会計明細３コンポーネント取得
	 * 
	 * @return 非会計明細３コンポーネント
	 */
	public TLabelNumericField getHm3Num() {
		return ctrlNums[2];
	}

	/**
	 * 非会計明細３コンポーネント取得
	 * 
	 * @return 非会計明細３コンポーネント
	 */
	public TLabelField getHm3Text() {
		return ctrlTexts[2];
	}

	/**
	 * 非会計明細３コンポーネント取得
	 * 
	 * @return 非会計明細３コンポーネント
	 */
	public TLabelPopupCalendar getHm3Date() {
		return ctrlDates[2];
	}

	/**
	 * タブ移動順番号を非会計明細に設定する.
	 * 
	 * @param no タブ番号
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		for (int i = 0; i <= 2; i++) {
			if (ctrlNums[i] != null) {
				ctrlNums[i].setTabControlNo(no);
			}

			if (ctrlTexts[i] != null) {
				ctrlTexts[i].setTabControlNo(no);
			}

			if (ctrlDates[i] != null) {
				ctrlDates[i].setTabControlNo(no);
			}
		}
	}

	/**
	 * 最大入力桁セット
	 * 
	 * @param num 非会計明細番号
	 * @param maxLength 桁数
	 */
	public void setMaxLength(int num, int maxLength) {
		switch (divs[num - 1]) {
			case NUMBER:
				ctrlNums[num - 1].setMaxLength(maxLength);
				break;

			case CHAR:
				ctrlTexts[num - 1].setMaxLength(maxLength);
				break;
		}
	}

	/**
	 * クリア
	 */
	public void clear() {
		setValue(1, null);
		setValue(2, null);
		setValue(3, null);
	}

	/**
	 * クリア
	 * 
	 * @param num 非会計明細番号
	 */
	public void clear(int num) {
		setValue(num, null);
	}

	/**
	 * 入力制御
	 * 
	 * @param isEdit true:入力可
	 */
	public void setEditable(boolean isEdit) {
		this.setEditable(1, isEdit);
		this.setEditable(2, isEdit);
		this.setEditable(3, isEdit);
	}

}
