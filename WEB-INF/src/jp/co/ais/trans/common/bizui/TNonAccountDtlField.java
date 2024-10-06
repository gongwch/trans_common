package jp.co.ais.trans.common.bizui;

import java.awt.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 非会計明細フィールド
 * 
 * @author ais
 */
public class TNonAccountDtlField extends TPanel {

	private static final long serialVersionUID = 1L;

	/** コントロールクラス */
	protected TNonAccountDtlFieldCtrl ctrl;

	/** ベースパネル */
	protected TPanel pnlBase;

	/** 非会計明細数値 */
	protected TLabelNumericField[] ctrlNums = new TLabelNumericField[3];

	/** 非会計明細文字 */
	protected TLabelField[] ctrlTexts = new TLabelField[3];

	/** 非会計明細日付 */
	protected TLabelPopupCalendar[] ctrlDates = new TLabelPopupCalendar[3];

	/** 会社コード */
	private String companyCode;

	/** 非会計明細１区分 */
	private int hmDivision1;

	/** 非会計明細２区分 */
	private int hmDivision2;

	/** 非会計明細３区分 */
	private int hmDivision3;

	/** フィールドサイズ */
	private final static int fieldSize = 90;

	/** ラベルサイズ */
	private final static int labelSize = 65;

	/** 入力最大桁数 */
	private final static int maxLength = 10;

	/**
	 * コンストラクタ
	 */
	public TNonAccountDtlField() {
		super();
		this.ctrl = new TNonAccountDtlFieldCtrl(this);
		initComponents();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param code 会社コード
	 */
	public TNonAccountDtlField(String code) {
		super();
		this.companyCode = code;
		this.ctrl = new TNonAccountDtlFieldCtrl(this);
		initComponents();
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {

		GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		pnlBase.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlBase, new Dimension(180, 80));

		CMP_MST cmp = ctrl.getCmpData();

		if (Util.isNullOrEmpty(cmp)) {
			return;
		}

		setHmDivision1(cmp.getCMP_HM_KBN_1() == 4 ? 3 : cmp.getCMP_HM_KBN_1());
		setHmDivision2(cmp.getCMP_HM_KBN_2() == 4 ? 3 : cmp.getCMP_HM_KBN_2());
		setHmDivision3(cmp.getCMP_HM_KBN_3() == 4 ? 3 : cmp.getCMP_HM_KBN_3());

		// 非会計明細１-------------------------------------------------------------------
		setHMField(1, cmp.getCMP_HM_KBN_1(), cmp.getCMP_HM_NAME_1());

		// 非会計明細２-------------------------------------------------------------------
		setHMField(2, cmp.getCMP_HM_KBN_2(), cmp.getCMP_HM_NAME_2());

		// 非会計明細３-------------------------------------------------------------------
		setHMField(3, cmp.getCMP_HM_KBN_3(), cmp.getCMP_HM_NAME_3());

		gridBagConstraints = new GridBagConstraints();
		add(pnlBase, gridBagConstraints);

	}

	/**
	 * 非会計明細フィールド構成
	 * 
	 * @param num 明細番号
	 * @param hmKbn フィールド区分
	 * @param hmName フィールド名称
	 */
	protected void setHMField(int num, int hmKbn, String hmName) {

		switch (hmKbn) {
			case 1: // 数値
				ctrlNums[num - 1] = new TLabelNumericField();

				ctrlNums[num - 1].setFieldSize(fieldSize);
				ctrlNums[num - 1].setLabelSize(labelSize);
				ctrlNums[num - 1].setLabelText(hmName);
				ctrlNums[num - 1].setMaxLength(maxLength);

				GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = num;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.insets = new Insets(0, 5, 0, 10);
				pnlBase.add(ctrlNums[num - 1], gridBagConstraints);
				break;

			case 2: // 文字
				ctrlTexts[num - 1] = new TLabelField();
				ctrlTexts[num - 1].setFieldSize(fieldSize);
				ctrlTexts[num - 1].setLabelSize(labelSize);
				ctrlTexts[num - 1].setLabelText(hmName);
				ctrlTexts[num - 1].setMaxLength(maxLength);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = num;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.insets = new Insets(0, 5, 0, 10);
				pnlBase.add(ctrlTexts[num - 1], gridBagConstraints);
				break;

			case 3: // 日付(YYYY/MM/DD)
				ctrlDates[num - 1] = new TLabelPopupCalendar();

				ctrlDates[num - 1].setLabelHAlignment(3);
				ctrlDates[num - 1].setLabelSize(labelSize);
				ctrlDates[num - 1].setLabelText(hmName);
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = num;
				gridBagConstraints.gridwidth = 5;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.insets = new Insets(0, 5, 0, 10);
				pnlBase.add(ctrlDates[num - 1], gridBagConstraints);
				break;

			case 4: // 日付(YYYY/MM/DD HH:MM)
				ctrlDates[num - 1] = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMDHM);
				ctrlDates[num - 1].setCalendarSize(105);
				ctrlDates[num - 1].setLabelHAlignment(3);
				ctrlDates[num - 1].setLabelSize(labelSize);
				ctrlDates[num - 1].setLabelText(hmName);
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = num;
				gridBagConstraints.gridwidth = 5;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.insets = new Insets(0, 5, 0, 10);
				pnlBase.add(ctrlDates[num - 1], gridBagConstraints);
				break;

			default:
				break;
		}
	}

	/**
	 * 会社コード取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードセット
	 * 
	 * @param value
	 */
	public void setCompanyCode(String value) {
		companyCode = value;
	}

	/**
	 * 非会計明細１区分取得
	 * 
	 * @return 非会計明細１区分
	 */
	public int getHmDivision1() {
		return hmDivision1;
	}

	/**
	 * 非会計明細１区分セット
	 * 
	 * @param value
	 */
	public void setHmDivision1(int value) {
		hmDivision1 = value;
	}

	/**
	 * 非会計明細２区分取得
	 * 
	 * @return 非会計明細２区分
	 */
	public int getHmDivision2() {
		return hmDivision2;
	}

	/**
	 * 非会計明細２区分セット
	 * 
	 * @param value
	 */
	public void setHmDivision2(int value) {
		hmDivision2 = value;
	}

	/**
	 * 非会計明細３区分取得
	 * 
	 * @return 非会計明細３区分
	 */
	public int getHmDivision3() {
		return hmDivision3;
	}

	/**
	 * 非会計明細３区分セット
	 * 
	 * @param value
	 */
	public void setHmDivision3(int value) {
		hmDivision3 = value;
	}

	/**
	 * 非会計明細１の値取得
	 * 
	 * @return 非会計明細１
	 */
	public String getHm1Value() {
		switch (getHmDivision1()) {
			case 1:
				return ctrlNums[0].getValue();
			case 2:
				return ctrlTexts[0].getValue();
			case 3:
				return Util.avoidNull(ctrlDates[0].getValue());
			default:
				return null;
		}
	}

	/**
	 * 非会計明細１セット
	 * 
	 * @param value
	 */
	public void setHm1Value(String value) {
		switch (getHmDivision1()) {
			case 1:
				ctrlNums[0].setValue(value);
				break;
			case 2:
				ctrlTexts[0].setValue(value);
				break;
			case 3:
				ctrl.setToDateComp(ctrlDates[0], value);
				break;
		}
	}

	/**
	 * 非会計明細２の値取得
	 * 
	 * @return 非会計明細２
	 */
	public String getHm2Value() {
		switch (getHmDivision2()) {
			case 1:
				return ctrlNums[1].getValue();
			case 2:
				return ctrlTexts[1].getValue();
			case 3:
				return Util.avoidNull(ctrlDates[1].getValue());
			default:
				return null;
		}
	}

	/**
	 * 非会計明細２セット
	 * 
	 * @param value
	 */
	public void setHm2Value(String value) {
		switch (getHmDivision2()) {
			case 1:
				ctrlNums[1].setValue(value);
				break;
			case 2:
				ctrlTexts[1].setValue(value);
				break;
			case 3:
				ctrl.setToDateComp(ctrlDates[1], value);
				break;
		}
	}

	/**
	 * 非会計明細３の値取得
	 * 
	 * @return 非会計明細３
	 */
	public String getHm3Value() {
		switch (getHmDivision3()) {
			case 1:
				return ctrlNums[2].getValue();
			case 2:
				return ctrlTexts[2].getValue();
			case 3:
				return Util.avoidNull(ctrlDates[2].getValue());
			default:
				return null;
		}
	}

	/**
	 * 非会計明細３セット
	 * 
	 * @param value
	 */
	public void setHm3Value(String value) {
		switch (getHmDivision3()) {
			case 1:
				ctrlNums[2].setValue(value);
				break;
			case 2:
				ctrlTexts[2].setValue(value);
				break;
			case 3:
				ctrl.setToDateComp(ctrlDates[2], value);
				break;
		}
	}

	/**
	 * 非会計明細1の画面制御
	 * 
	 * @param editableHm1
	 */
	public void setEditableHm1(boolean editableHm1) {
		switch (getHmDivision1()) {
			case 1:
				ctrlNums[0].setEditable(editableHm1);
				break;
			case 2:
				ctrlTexts[0].setEditable(editableHm1);
				break;
			case 3:
				ctrlDates[0].setEnabled(editableHm1);
				ctrlDates[0].setEditable(editableHm1);
				break;
		}
	}

	/**
	 * 非会計明細2の画面制御
	 * 
	 * @param editableHm2
	 */
	public void setEditableHm2(boolean editableHm2) {
		switch (getHmDivision2()) {
			case 1:
				ctrlNums[1].setEditable(editableHm2);
				break;
			case 2:
				ctrlTexts[1].setEditable(editableHm2);
				break;
			case 3:
				ctrlDates[1].setEnabled(editableHm2);
				ctrlDates[1].setEditable(editableHm2);
				break;
		}
	}

	/**
	 * 非会計明細3の画面制御
	 * 
	 * @param editableHm3
	 */
	public void setEditableHm3(boolean editableHm3) {
		switch (getHmDivision3()) {
			case 1:
				ctrlNums[2].setEditable(editableHm3);
				break;
			case 2:
				ctrlTexts[2].setEditable(editableHm3);
				break;
			case 3:
				ctrlDates[2].setEnabled(editableHm3);
				ctrlDates[2].setEditable(editableHm3);
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
	 * @param no
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
}
