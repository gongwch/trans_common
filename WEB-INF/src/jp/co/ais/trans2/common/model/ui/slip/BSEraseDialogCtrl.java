package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.event.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.slip.BSEraseDialog.SC;
import jp.co.ais.trans2.model.bs.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * BS消込用ダイアログコントローラ
 */
public class BSEraseDialogCtrl extends TController {

	/** 「Cancel」が選択された場合にクラスメソッドから返される値 */
	public static final int CANCEL_OPTION = 0;

	/** 「確定」が選択された場合にクラスメソッドから返される値 */
	public static final int OK_OPTION = 1;

	/** true:BS勘定は月末日を利用＜Client＞ */
	public static boolean isBsTermLastDay = ClientConfig.isFlagOn("trans.slip.bs.term.lastday");

	/** 指示画面 */
	public BSEraseDialog dialog;

	/** 親Container */
	protected Container parent;

	/** 基軸通貨コード */
	protected String keyCurrency = null;

	/** 基軸通貨 小数点桁数 */
	protected int keyDigit = 0;

	/** 結果 */
	protected int option = CANCEL_OPTION;

	/** 現在選択中通貨コード */
	protected String nowCurrencyCode = null;

	/** 既に消込選択しているデータ */
	protected List<SWK_DTL> nowEraseList = new ArrayList<SWK_DTL>();

	/** 既に消込選択しているデータのキー */
	protected List<String> nowEraseKeyList = new ArrayList<String>();

	/** 基準伝票日付 */
	protected Date baseDate;

	/** 現状編集中伝票番号 */
	protected String currentSlipNo;

	/** 会社情報 */
	protected Company company = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親コンポーネント
	 * @param prgInfo プログラム情報
	 */
	public BSEraseDialogCtrl(Container parent, TClientProgramInfo prgInfo) {
		this(parent, prgInfo, null);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親コンポーネント
	 * @param prgInfo プログラム情報
	 * @param company 会社情報
	 */
	public BSEraseDialogCtrl(Container parent, TClientProgramInfo prgInfo, Company company) {
		this.parent = parent;
		this.prgInfo = prgInfo;
		this.company = company;

		// ダイアログ初期化
		this.dialog = createView();

		// 指示画面を初期化する
		initView();

		// イベント設定
		addDialogEvent();
	}

	/**
	 * ダイアログ生成
	 * 
	 * @return ダイアログ
	 */
	protected BSEraseDialog createView() {
		if (parent instanceof TPanel) {
			return new BSEraseDialog(getCompany(), ((TPanel) parent).getParentFrame());
		} else if (parent instanceof TDialog) {
			return new BSEraseDialog(getCompany(), ((TDialog) parent));
		} else if (parent instanceof TFrame) {
			return new BSEraseDialog(getCompany(), ((TFrame) parent));
		}

		return null;
	}

	@Override
	public BSEraseDialog getView() {
		return dialog;
	}

	/**
	 * 会社コードを戻します。<br>
	 * 計上会社指定された場合、計上会社コードを返す
	 * 
	 * @return 会社コード
	 */
	@Override
	public String getCompanyCode() {
		if (company != null) {
			return company.getCode();
		}
		return super.getCompanyCode();
	}

	/**
	 * 画面を初期化する
	 */
	protected void initView() {

		// 計上会社
		Company kcompany = company != null ? company : getCompany();

		// 伝票日付(空白不可)
		dialog.ctrlSlipDate.setAllowableBlank(false);

		// 基軸通貨コード
		keyCurrency = kcompany.getAccountConfig().getKeyCurrency().getCode();

		// 基軸通貨 小数点桁数
		keyDigit = kcompany.getAccountConfig().getKeyCurrency().getDecimalPoint();

		// 合計値
		dialog.ctrlDrInputTotal.setNumber(BigDecimal.ZERO);
		dialog.ctrlCrInputTotal.setNumber(BigDecimal.ZERO);
		dialog.ctrlDrInputTotal.setDecimalPoint(keyDigit);
		dialog.ctrlCrInputTotal.setDecimalPoint(keyDigit);

		dialog.ctrlDrTotal.setNumber(BigDecimal.ZERO);
		dialog.ctrlCrTotal.setNumber(BigDecimal.ZERO);
		dialog.ctrlDrTotal.setDecimalPoint(keyDigit);
		dialog.ctrlCrTotal.setDecimalPoint(keyDigit);

		// 計上会社(使用不可)
		dialog.ctrlKCompany.setEntity(kcompany);
		dialog.ctrlKCompany.setEditable(false);

		// 科目条件 BS勘定消込区分=消込対象
		dialog.ctrlItemGroup.getSearchCondition().getItemCondition().setBsCalculateErase(true);

		// 検索条件制御
		dialog.ctrlDepartment.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlItemGroup.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlCustomer.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlEmployee.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage1.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage2.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage3.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage4.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage5.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage6.getSearchCondition().setCompanyCode(getCompanyCode());

		// 管理１～６制御
		AccountConfig conf = kcompany.getAccountConfig();

		// 名称セット
		dialog.ctrlItemGroup.ctrlItemReference.btn.setText(conf.getItemName());
		dialog.ctrlItemGroup.ctrlSubItemReference.btn.setText(conf.getSubItemName());
		dialog.ctrlItemGroup.ctrlDetailItemReference.btn.setText(conf.getDetailItemName());
		dialog.ctrlManage1.btn.setText(conf.getManagement1Name());
		dialog.ctrlManage2.btn.setText(conf.getManagement2Name());
		dialog.ctrlManage3.btn.setText(conf.getManagement3Name());
		dialog.ctrlManage4.btn.setText(conf.getManagement4Name());
		dialog.ctrlManage5.btn.setText(conf.getManagement5Name());
		dialog.ctrlManage6.btn.setText(conf.getManagement6Name());

		dialog.ctrlManage1.setVisible(conf.isUseManagement1());
		dialog.ctrlManage2.setVisible(conf.isUseManagement2());
		dialog.ctrlManage3.setVisible(conf.isUseManagement3());
		dialog.ctrlManage4.setVisible(conf.isUseManagement4());
		dialog.ctrlManage5.setVisible(conf.isUseManagement5());
		dialog.ctrlManage6.setVisible(conf.isUseManagement6());
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addDialogEvent() {

		// 検索
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doSearch();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 確定
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doSettle();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 取消
		dialog.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doCancel();
			}
		});

		dialog.tbl.setAdapter(new TTableAdapter() {

			/**
			 * 値が変更された場合に呼び出される
			 * 
			 * @param before 変更前の値
			 * @param after 変更後の値
			 * @param row 行
			 * @param column 列
			 */
			@Override
			public void changedValueAt(Object before, Object after, int row, int column) {

				if (column != SC.CHECK.ordinal()) {
					// チェックボックス列以外、処理なし
					return;
				}

				// 選択行に応じて、合計行を再計算する。
				summary();

			}
		});

		dialog.chk.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// 選択行に応じて、合計行を再計算する。
				summary();
			}

		});

	}

	/**
	 * クラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getManagerClass() {
		return BSEraseManager.class;
	}

	/**
	 * 表示
	 * 
	 * @return 結果
	 */
	public int show() {
		// 表示
		dialog.setVisible(true);

		return option;
	}

	/**
	 * 検索処理
	 */
	protected void doSearch() {
		try {
			if (!checkInput()) {
				return;
			}

			// テーブル初期化
			dialog.tbl.removeRow();

			// 借方金額合計、貸方金額合計初期化
			dialog.ctrlDrInputTotal.setNumber(BigDecimal.ZERO);
			dialog.ctrlCrInputTotal.setNumber(BigDecimal.ZERO);
			dialog.ctrlDrTotal.setNumber(BigDecimal.ZERO);
			dialog.ctrlCrTotal.setNumber(BigDecimal.ZERO);

			// データ抽出
			BSEraseCondition condition = getCondition();

			List<SWK_DTL> list = (List<SWK_DTL>) request(getManagerClass(), "get", condition);

			if (list == null || list.isEmpty()) {
				showMessage(dialog, "I00022");
				dialog.ctrlSlipDate.requestFocus();
				return;
			}

			// データが存在する場合、スプレッドに取得データを設定
			for (SWK_DTL bean : list) {
				if (isSkip(bean)) {
					continue;
				}

				dialog.tbl.addRow(getRow(bean));
			}

			if (dialog.tbl.getRowCount() == 0) {
				showMessage(dialog, "I00022");
				dialog.ctrlSlipDate.requestFocus();
				return;
			}

			// 行選択
			dialog.tbl.requestFocus();
			dialog.tbl.setRowSelection(0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * スプレッド反映対象外かどうか
	 * 
	 * @param bean
	 * @return true:既に編集中仕訳、除外
	 */
	protected boolean isSkip(SWK_DTL bean) {

		for (SWK_DTL eraseDtl : nowEraseList) {
			if (Util.equals(getKey(eraseDtl), getKey(bean))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	protected boolean checkInput() {

		Date termDate = getTermDate(baseDate);

		// 伝票日付を超える日付チェック
		if (baseDate != null && !Util.isSmallerThenByYMD(dialog.ctrlSlipDate.getValue(), termDate)) {
			// I00090:伝票日付 ＜＝ {1} にしてください。
			showMessage("I00090", "C00599", DateUtil.toYMDString(termDate));
			dialog.ctrlSlipDate.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * BS勘定可能の期限日の取得
	 * 
	 * @param value
	 * @return 期限日
	 */
	protected Date getTermDate(Date value) {
		if (isBsTermLastDay) {
			return DateUtil.getLastDate(value);
		}
		return value;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected BSEraseCondition getCondition() {
		BSEraseCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode()); // 会社コード
		condition.setSlipDate(dialog.ctrlSlipDate.getValue()); // 伝票日付
		condition.setItem(dialog.ctrlItemGroup.getEntity()); // 科目
		condition.setCustomerCode(dialog.ctrlCustomer.getCode()); // 取引先
		condition.setDepartmentCode(dialog.ctrlDepartment.getCode());// 部門
		condition.setEmployeeCode(dialog.ctrlEmployee.getCode()); // 社員
		condition.setManegement1Code(dialog.ctrlManage1.getCode()); // 管理1コード
		condition.setManegement2Code(dialog.ctrlManage2.getCode()); // 管理2コード
		condition.setManegement3Code(dialog.ctrlManage3.getCode()); // 管理3コード
		condition.setManegement4Code(dialog.ctrlManage4.getCode()); // 管理4コード
		condition.setManegement5Code(dialog.ctrlManage5.getCode()); // 管理5コード
		condition.setManegement6Code(dialog.ctrlManage6.getCode()); // 管理6コード
		condition.setHm1(dialog.ctrlNonAcDetails.getValue(1)); // 非会計明細1
		condition.setHm2(dialog.ctrlNonAcDetails.getValue(2)); // 非会計明細2
		condition.setHm3(dialog.ctrlNonAcDetails.getValue(3)); // 非会計明細3
		condition.setExcludeSlipNo(currentSlipNo); // 現状編集中伝票番号
		condition.setCurrencyCode(nowCurrencyCode); // 現在選択中通貨コード

		// 編集中の消込伝票番号、行番号
		for (SWK_DTL bs : nowEraseList) {
			condition.add(bs.getSWK_DEN_NO(), bs.getSWK_GYO_NO());
		}
		return condition;
	}

	/**
	 * 検索条件生成
	 * 
	 * @return condition
	 */
	protected BSEraseCondition createCondition() {
		return new BSEraseCondition();
	}

	/**
	 * テーブルに設定する配列データを取得する
	 * 
	 * @param bean
	 * @return 行データ
	 */
	protected List<Object> getRow(SWK_DTL bean) {
		// 小数点桁数
		int digit = bean.getCUR_DEC_KETA();

		List<Object> list = new ArrayList<Object>(SC.values().length);

		String inputAmount = NumberFormatUtil.formatNumber(bean.getSWK_IN_KIN(), digit);
		String rate = NumberFormatUtil.formatNumber(bean.getSWK_CUR_RATE(), 4);
		String amount = NumberFormatUtil.formatNumber(bean.getSWK_KIN(), keyDigit);

		list.add(bean);
		list.add(false);
		list.add(bean.getSWK_TRI_CODE());
		list.add(bean.getSWK_TRI_NAME_S());
		list.add(bean.getSWK_DEP_CODE());
		list.add(bean.getSWK_DEP_NAME_S());
		list.add(bean.getSWK_KMK_CODE());
		list.add(bean.getSWK_KMK_NAME_S());
		list.add(bean.getSWK_HKM_CODE());
		list.add(bean.getSWK_HKM_NAME_S());
		list.add(bean.getSWK_UKM_CODE());
		list.add(bean.getSWK_UKM_NAME_S());
		list.add(DateUtil.toYMDString(bean.getSWK_DEN_DATE()));
		list.add(bean.getSWK_DEN_NO());
		list.add(bean.getSWK_GYO_NO());
		list.add(bean.getSWK_CUR_CODE());
		list.add(SC.SWK_CUR_RATE.ordinal(), rate);
		list.add(bean.isDR() ? inputAmount : null);
		list.add(bean.isDR() ? amount : null);
		list.add(bean.isDR() ? null : inputAmount);
		list.add(bean.isDR() ? null : amount);
		list.add(bean.getSWK_GYO_TEK());

		list.add(bean.getSWK_EMP_CODE()); // 社員コード
		list.add(bean.getSWK_EMP_NAME_S()); // 社員略称
		list.add(bean.getSWK_KNR_CODE_1()); // 管理１コード
		list.add(bean.getSWK_KNR_NAME_S_1()); // 管理１略称
		list.add(bean.getSWK_KNR_CODE_2()); // 管理２コード
		list.add(bean.getSWK_KNR_NAME_S_2()); // 管理２略称
		list.add(bean.getSWK_KNR_CODE_3()); // 管理３コード
		list.add(bean.getSWK_KNR_NAME_S_3()); // 管理３略称
		list.add(bean.getSWK_KNR_CODE_4()); // 管理４コード
		list.add(bean.getSWK_KNR_NAME_S_4()); // 管理４略称
		list.add(bean.getSWK_KNR_CODE_5()); // 管理５コード
		list.add(bean.getSWK_KNR_NAME_S_5()); // 管理５略称
		list.add(bean.getSWK_KNR_CODE_6()); // 管理６コード
		list.add(bean.getSWK_KNR_NAME_S_6()); // 管理６略称
		list.add(bean.getSWK_HM_1()); // 非会計明細１
		list.add(bean.getSWK_HM_2()); // 非会計明細２
		list.add(bean.getSWK_HM_3()); // 非会計明細３

		return list;
	}

	/**
	 * 確定処理
	 */
	protected void doSettle() {

		try {

			List<SWK_DTL> list = getSelectedList();

			if (list.isEmpty()) {
				// I00043:一覧からデータを選択してください。
				showMessage("I00043");
				dialog.tbl.requestFocus();
				return;
			}

			// 排他
			BSEraseCondition condition = new BSEraseCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setKCompany(dialog.ctrlKCompany.getEntity());
			condition.setExcludeSlipNo(currentSlipNo); // 現状編集中伝票番号
			for (SWK_DTL bs : list) {
				condition.add(bs.getSWK_DEN_NO(), bs.getSWK_GYO_NO());
			}

			request(getManagerClass(), "lock", condition);

			// 閉じる
			this.option = OK_OPTION;

			dialog.dispose();

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 取消ボタン押下
	 */
	protected void doCancel() {
		this.option = CANCEL_OPTION;

		dialog.dispose();
	}

	/**
	 * 合計を計算する
	 */
	protected void summary() {

		// 変数初期化
		BigDecimal drInput = BigDecimal.ZERO; // DR入力金額合計
		BigDecimal crInput = BigDecimal.ZERO; // CR入力金額合計
		int digit = 0;
		BigDecimal dr = BigDecimal.ZERO; // DR合計
		BigDecimal cr = BigDecimal.ZERO; // CR合計

		List<SWK_DTL> list = getSelectedList();

		// 選択行の取得
		for (SWK_DTL dtl : list) {
			// 入力金額－消込済金額(入力通貨) : 単純加算
			drInput = drInput.add(dtl.getDebitInputAmount());
			crInput = crInput.add(dtl.getCreditInputAmount());
			digit = Math.max(digit, dtl.getCUR_DEC_KETA());
			// 基軸金額－消込済金額(基軸通貨)
			dr = dr.add(dtl.getDebitKeyAmount());
			cr = cr.add(dtl.getCreditKeyAmount());
		}

		// 合計値の設定
		dialog.ctrlDrInputTotal.setNumber(drInput);
		dialog.ctrlCrInputTotal.setNumber(crInput);
		dialog.ctrlDrInputTotal.setDecimalPoint(digit);
		dialog.ctrlCrInputTotal.setDecimalPoint(digit);
		dialog.ctrlDrTotal.setNumber(dr);
		dialog.ctrlCrTotal.setNumber(cr);

	}

	/**
	 * 選択行データの取得
	 * 
	 * @return BS消込対象リスト
	 */
	public List<SWK_DTL> getSelectedList() {
		List<SWK_DTL> list = new ArrayList<SWK_DTL>();

		for (int i = 0; i < dialog.tbl.getRowCount(); i++) {

			Boolean isChecked = (Boolean) dialog.tbl.getRowValueAt(i, SC.CHECK);

			if (isChecked) {
				list.add((SWK_DTL) dialog.tbl.getRowValueAt(i, SC.BEAN));
			}
		}

		return list;
	}

	/**
	 * 既に消込選択していた行をセット
	 * 
	 * @param eraseList 消込行情報
	 */
	public void setNowEraseList(List<SWK_DTL> eraseList) {
		this.nowEraseList = eraseList;

		if (eraseList != null) {
			this.nowEraseKeyList.clear();
			for (SWK_DTL dtl : eraseList) {
				nowEraseKeyList.add(getKey(dtl));
			}
		}
	}

	/**
	 * 仕訳の判定キーの取得
	 * 
	 * @param dtl
	 * @return 仕訳の判定キー
	 */
	protected String getKey(SWK_DTL dtl) {
		return dtl.getSWK_DEN_NO() + "<>" + dtl.getSWK_GYO_NO();
	}

	/**
	 * 伝票日付を設定する。（初期値）
	 * 
	 * @param slipDate 伝票日付
	 */
	public void setBaseDate(Date slipDate) {
		this.baseDate = slipDate;
		dialog.ctrlSlipDate.setValue(slipDate);
	}

	/**
	 * 科目-補助-内訳を設定する。（初期値）
	 * 
	 * @param bean
	 */
	public void setItem(Item bean) {
		dialog.ctrlItemGroup.setEntity(bean);
	}

	/**
	 * 取引先を設定する。（初期値）
	 * 
	 * @param bean
	 */
	public void setCustomer(Customer bean) {
		dialog.ctrlCustomer.setEntity(bean);
	}

	/**
	 * 部門を設定する。（初期値）
	 * 
	 * @param bean
	 */
	public void setDepartment(Department bean) {
		dialog.ctrlDepartment.setEntity(bean);
	}

	/**
	 * 現状編集中伝票番号の設定
	 * 
	 * @param slipNo 伝票番号
	 */
	public void setCurrentSlipNo(String slipNo) {
		this.currentSlipNo = slipNo;
	}

	/**
	 * 現状編集中通貨コードの設定
	 * 
	 * @param currencyCode 通貨コード
	 */
	public void setCurrentCurrencyCode(String currencyCode) {
		this.nowCurrencyCode = currencyCode;
	}

}
