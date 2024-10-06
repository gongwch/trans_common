package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * BS残高一覧の指示画面
 * 
 * @author AIS
 */
public class BSEraseDialog extends TDialog {

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {
		/** エンティティ */
		BEAN,

		/** 選択チェックボックス */
		CHECK,

		/** 取引先コード */
		SWK_TRI_CODE,

		/** 取引先 */
		SWK_TRI_NAME_S,

		/** 部門コード */
		SWK_DEP_CODE,

		/** 部門 */
		SWK_DEP_NAME_S,

		/** 科目コード */
		SWK_KMK_CODE,

		/** 科目 */
		SWK_KMK_NAME_S,

		/** 補助科目コード */
		SWK_HKM_CODE,

		/** 補助科目 */
		SWK_HKM_NAME_S,

		/** 内訳科目コード */
		SWK_UKM_CODE,

		/** 内訳 */
		SWK_UKM_NAME_S,

		/** 伝票日付 */
		SWK_DEN_DATE,

		/** 伝票番号 */
		SWK_DEN_NO,

		/** 行番号 */
		SWK_GYO_NO,

		/** 通貨 */
		SWK_CUR_CODE,

		/** レート */
		SWK_CUR_RATE,

		/** 借方(外貨) */
		DR_IN_KIN,

		/** 借方 */
		DR_KIN,

		/** 貸方(外貨) */
		CR_IN_KIN,

		/** 貸方 */
		CR_KIN,

		/** 行摘要 */
		SWK_GYO_TEK,

		/** 社員コード */
		SWK_EMP_CODE,

		/** 社員略称 */
		SWK_EMP_NAME_S,

		/** 管理１コード */
		SWK_KNR_CODE_1,

		/** 管理１略称 */
		SWK_KNR_NAME_S_1,

		/** 管理２コード */
		SWK_KNR_CODE_2,

		/** 管理２略称 */
		SWK_KNR_NAME_S_2,

		/** 管理３コード */
		SWK_KNR_CODE_3,

		/** 管理３略称 */
		SWK_KNR_NAME_S_3,

		/** 管理４コード */
		SWK_KNR_CODE_4,

		/** 管理４略称 */
		SWK_KNR_NAME_S_4,

		/** 管理５コード */
		SWK_KNR_CODE_5,

		/** 管理５略称 */
		SWK_KNR_NAME_S_5,

		/** 管理６コード */
		SWK_KNR_CODE_6,

		/** 管理６略称 */
		SWK_KNR_NAME_S_6,

		/** 非会計明細１ */
		SWK_HM_1,

		/** 非会計明細２ */
		SWK_HM_2,

		/** 非会計明細３ */
		SWK_HM_3;
	}

	/** 伝票日付 */
	public TLabelPopupCalendar ctrlSlipDate;

	/** 迄 */
	public TLabel lblSlipDateEnd;

	/** 現在計上会社 */
	public TCompanyReference ctrlKCompany;

	/** 科目・補助・内訳のユニットコンポーネント */
	public TItemGroup ctrlItemGroup;

	/** 取引先指定 */
	public TCustomerReference ctrlCustomer;

	/** 部門指定 */
	public TDepartmentReference ctrlDepartment;

	/** 社員 */
	public TEmployeeReference ctrlEmployee;

	/** 管理1 */
	public TManagement1Reference ctrlManage1;

	/** 管理2 */
	public TManagement2Reference ctrlManage2;

	/** 管理3 */
	public TManagement3Reference ctrlManage3;

	/** 管理4 */
	public TManagement4Reference ctrlManage4;

	/** 管理5 */
	public TManagement5Reference ctrlManage5;

	/** 管理6 */
	public TManagement6Reference ctrlManage6;

	/** 非会計明細 */
	public TNonAccountintDetailUnit ctrlNonAcDetails;

	/** 検索ボタン */
	public TButton btnSearch;

	/** 確定ボタン */
	public TButton btnSettle;

	/** 取消ボタン */
	public TButton btnCancel;

	/** DR入力金額合計 */
	public TLabelNumericField ctrlDrInputTotal;

	/** CR入力金額合計 */
	public TLabelNumericField ctrlCrInputTotal;

	/** DR合計 */
	public TLabelNumericField ctrlDrTotal;

	/** CR合計 */
	public TLabelNumericField ctrlCrTotal;

	/** 通貨コード */
	public TLabelField ctrlCurrencyCode;

	/** スプレッド */
	public TTable tbl;

	/** 選択 */
	public TCheckBox chk;

	/**
	 * コンストラクタ.
	 * 
	 * @param company 会社情報
	 * @param parent 親フレーム
	 */
	public BSEraseDialog(Company company, Frame parent) {
		super(company, parent, true);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param company 会社情報
	 * @param parent 親Dialog
	 */
	public BSEraseDialog(Company company, Dialog parent) {
		super(company, parent, true);
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {
		gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setResizable(true);

		// ボディ領域
		pnlBody = new TPanel();
		pnlBody.setLayout(new GridBagLayout());
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBody, gc);
	}

	@Override
	public void initComponents() {
		ctrlSlipDate = new TLabelPopupCalendar();
		lblSlipDateEnd = new TLabel();
		ctrlKCompany = new TCompanyReference();
		ctrlItemGroup = new TItemGroup();
		ctrlCustomer = new TCustomerReference();
		ctrlDepartment = new TDepartmentReference();

		ctrlEmployee = new TEmployeeReference();
		ctrlManage1 = new TManagement1Reference();
		ctrlManage2 = new TManagement2Reference();
		ctrlManage3 = new TManagement3Reference();
		ctrlManage4 = new TManagement4Reference();
		ctrlManage5 = new TManagement5Reference();
		ctrlManage6 = new TManagement6Reference();
		// TODO:非会計明細
		ctrlNonAcDetails = new TNonAccountintDetailUnit();

		btnSearch = new TButton();
		btnSettle = new TButton();
		btnCancel = new TButton();
		ctrlDrInputTotal = new TLabelNumericField();
		ctrlCrInputTotal = new TLabelNumericField();
		ctrlDrTotal = new TLabelNumericField();
		ctrlCrTotal = new TLabelNumericField();
		ctrlCurrencyCode = new TLabelField();
		tbl = new TTable();
		chk = new TCheckBox();
	}

	@Override
	public void allocateComponents() {

		Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

		setSize(rect.width, 700);
		setTitle(getWord("C04292")); // BS勘定消込一覧

		// 一覧
		tbl.addSpreadSheetSelectChange(btnSettle);
		tbl.setEnterToButton(true);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(10, 10, 0, 10);
		pnlBody.add(tbl, gc);

		// BODY 条件用
		TPanel pnlSearch = new TPanel();
		pnlSearch.setLayout(null);
		TGuiUtil.setComponentSize(pnlSearch, new Dimension(this.getWidth(), 200));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlSearch, gc);

		// 取引先条件
		int x = 40;
		int y = 5;

		// 伝票日付
		ctrlSlipDate.setLangMessageID("C00599");
		ctrlSlipDate.setLabelSize(75);
		ctrlSlipDate.setLocation(x, y);
		pnlSearch.add(ctrlSlipDate);

		// 以前
		lblSlipDateEnd.setSize(80, 20);
		lblSlipDateEnd.setLangMessageID("C00008");
		lblSlipDateEnd.setLocation(ctrlSlipDate.getX() + ctrlSlipDate.getWidth() + 2, y);
		pnlSearch.add(lblSlipDateEnd);

		y += ctrlSlipDate.getHeight();

		// 計上会社
		ctrlKCompany.btn.setLangMessageID("C01052");
		ctrlKCompany.setLocation(x, y);
		pnlSearch.add(ctrlKCompany);

		y += ctrlKCompany.getHeight();

		// 科目範囲
		ctrlItemGroup.setLocation(x, y);
		pnlSearch.add(ctrlItemGroup);

		y += ctrlItemGroup.getHeight();

		// 取引先指定
		ctrlCustomer.setCheckExist(true);
		ctrlCustomer.setLocation(x, y);
		pnlSearch.add(ctrlCustomer);

		y += ctrlCustomer.getHeight();

		// 部門指定
		ctrlDepartment.setCheckExist(true);
		ctrlDepartment.setLocation(x, y);
		pnlSearch.add(ctrlDepartment);

		y = ctrlItemGroup.getY() + 125;

		// 条件ボタン
		btnSearch.setLangMessageID("C00155");// 検索
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, y);
		pnlSearch.add(btnSearch);

		x += btnSearch.getWidth() + HEADER_MARGIN_X;

		// 確定ボタン
		btnSettle.setLangMessageID("C01019");// 確定
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, y);
		pnlSearch.add(btnSettle);

		x += btnSettle.getWidth() + HEADER_MARGIN_X;

		// 取消ボタン
		btnCancel.setLangMessageID("C00405");// 取消
		btnCancel.setShortcutKey(KeyEvent.VK_F12);
		btnCancel.setSize(25, 110);
		btnCancel.setLocation(x, y);
		pnlSearch.add(btnCancel);

		x += btnCancel.getWidth() + 20;

		// 通貨コード表示
		ctrlCurrencyCode.setLangMessageID("C00371"); // 通貨
		ctrlCurrencyCode.setLabelSize(65);
		ctrlCurrencyCode.setFieldSize(35);
		ctrlCurrencyCode.setEditable(false); // 表示のみ
		ctrlCurrencyCode.setLocation(x, y);
		pnlSearch.add(ctrlCurrencyCode);

		x = ctrlDepartment.getX() + ctrlDepartment.getWidth();
		y = ctrlSlipDate.getY();

		// DR入力金額合計
		ctrlDrInputTotal.setLabelSize(165);
		ctrlDrInputTotal.setNumericFormat("#,##0");
		ctrlDrInputTotal.setLangMessageID("C10725");// 借方外貨合計
		ctrlDrInputTotal.setChangeRedOfMinus(true);
		ctrlDrInputTotal.setEditable(false);
		ctrlDrInputTotal.setLocation(x, y);
		pnlSearch.add(ctrlDrInputTotal);

		y += ctrlDrInputTotal.getHeight();

		// DR合計
		ctrlDrTotal.setLabelSize(165);
		ctrlDrTotal.setNumericFormat("#,##0");
		ctrlDrTotal.setLangMessageID("C01126");// 借方金額合計
		ctrlDrTotal.setChangeRedOfMinus(true);
		ctrlDrTotal.setEditable(false);
		ctrlDrTotal.setLocation(x, y);
		pnlSearch.add(ctrlDrTotal);

		x = ctrlDepartment.getX() + ctrlDepartment.getWidth();
		y = ctrlItemGroup.getY() + ctrlDepartment.getHeight();

		// 社員
		ctrlEmployee.setCheckExist(true);
		ctrlEmployee.setLocation(x, y);
		pnlSearch.add(ctrlEmployee);

		y += ctrlEmployee.getHeight();

		// 管理1
		ctrlManage1.setCheckExist(true);
		ctrlManage1.setLocation(x, y);
		pnlSearch.add(ctrlManage1);

		y += ctrlManage1.getHeight();

		// 管理2
		ctrlManage2.setCheckExist(true);
		ctrlManage2.setLocation(x, y);
		pnlSearch.add(ctrlManage2);

		y += ctrlManage2.getHeight();

		// 管理3
		ctrlManage3.setCheckExist(true);
		ctrlManage3.setLocation(x, y);
		pnlSearch.add(ctrlManage3);

		x = ctrlEmployee.getX() + ctrlEmployee.getWidth();
		y = ctrlSlipDate.getY();

		// CR入力金額合計
		ctrlCrInputTotal.setLabelSize(170);
		ctrlCrInputTotal.setNumericFormat("#,##0");
		ctrlCrInputTotal.setLangMessageID("C10726");// 貸方外貨合計
		ctrlCrInputTotal.setChangeRedOfMinus(true);
		ctrlCrInputTotal.setEditable(false);
		ctrlCrInputTotal.setLocation(x, y);
		pnlSearch.add(ctrlCrInputTotal);

		y += ctrlCrInputTotal.getHeight();

		// CR合計
		ctrlCrTotal.setLabelSize(170);
		ctrlCrTotal.setNumericFormat("#,##0");
		ctrlCrTotal.setLangMessageID("C01229");// 貸方金額合計
		ctrlCrTotal.setChangeRedOfMinus(true);
		ctrlCrTotal.setEditable(false);
		ctrlCrTotal.setLocation(x, y);
		pnlSearch.add(ctrlCrTotal);

		x = ctrlEmployee.getX() + ctrlEmployee.getWidth() + 5;
		y = ctrlEmployee.getY();

		// 管理4
		ctrlManage4.setCheckExist(true);
		ctrlManage4.setLocation(x, y);
		pnlSearch.add(ctrlManage4);

		y += ctrlManage4.getHeight();

		// 管理5
		ctrlManage5.setCheckExist(true);
		ctrlManage5.setLocation(x, y);
		pnlSearch.add(ctrlManage5);

		y += ctrlManage5.getHeight();

		// 管理6
		ctrlManage6.setCheckExist(true);
		ctrlManage6.setLocation(x, y);
		pnlSearch.add(ctrlManage6);

		x = ctrlManage6.getX() - 5;
		y += ctrlManage6.getHeight();

		// 非会計明細
		ctrlNonAcDetails.setLocation(x, y);
		pnlSearch.add(ctrlNonAcDetails);

		// スプレッドの初期化
		initSpread();
	}

	/**
	 * スプレッドの初期化
	 */
	protected void initSpread() {

		AccountConfig conf = company.getAccountConfig();
		String kmkName = conf.getItemName();
		String hkmName = conf.getSubItemName();
		String ukmName = conf.getDetailItemName();
		int ukm = conf.isUseDetailItem() ? 60 : -1;

		String lblCustomerCode = getWord("C00786"); // 取引先コード
		String lblCustomerName = getWord("C00408"); // 取引先
		String lblDeptCode = getWord("C00698"); // 部門コード
		String lblDeptName = getWord("C00467"); // 部門
		String lblCode = getWord("C00174"); // コード
		String lblEmpCode = getWord("C00697"); // 社員コード
		String lblEmpName = getWord("C00246"); // 社員

		// 管理1～6
		String mng1Name = conf.getManagement1Name();
		String mng2Name = conf.getManagement2Name();
		String mng3Name = conf.getManagement3Name();
		String mng4Name = conf.getManagement4Name();
		String mng5Name = conf.getManagement5Name();
		String mng6Name = conf.getManagement6Name();
		int mng1 = conf.isUseManagement1() ? 70 : -1;
		int mng2 = conf.isUseManagement2() ? 70 : -1;
		int mng3 = conf.isUseManagement3() ? 70 : -1;
		int mng4 = conf.isUseManagement4() ? 70 : -1;
		int mng5 = conf.isUseManagement5() ? 70 : -1;
		int mng6 = conf.isUseManagement6() ? 70 : -1;

		// 非会計明細
		NonAccountingDivision nad1 = conf.getNonAccounting1();
		int hm1 = 100; // 幅
		int align1 = SwingConstants.LEFT; // 位置
		String hm1Name = conf.getNonAccounting1Name(); // 名称

		switch (nad1) {
			case NONE:
				hm1 = -1;
				break;

			case NUMBER:
				align1 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align1 = SwingConstants.CENTER;
				break;
		}

		NonAccountingDivision nad2 = conf.getNonAccounting2();
		int hm2 = 100;
		int align2 = SwingConstants.LEFT;
		String hm2Name = conf.getNonAccounting2Name();

		switch (nad2) {
			case NONE:
				hm2 = -1;
				break;

			case NUMBER:
				align2 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align2 = SwingConstants.CENTER;
				break;
		}

		NonAccountingDivision nad3 = conf.getNonAccounting3();
		int hm3 = 100;
		int align3 = SwingConstants.LEFT;
		String hm3Name = conf.getNonAccounting3Name();

		switch (nad3) {
			case NONE:
				hm3 = -1;
				break;

			case NUMBER:
				align3 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align3 = SwingConstants.CENTER;
				break;
		}

		// スプレッドの設定
		tbl.addColumn(SC.BEAN, "", -1); // エンティティ
		tbl.addColumn(SC.CHECK, "C00324", 30, chk); // 選択
		tbl.addColumn(SC.SWK_TRI_CODE, lblCustomerCode, 70); // 取引先コード
		tbl.addColumn(SC.SWK_TRI_NAME_S, lblCustomerName, 90); // 取引先
		tbl.addColumn(SC.SWK_DEP_CODE, lblDeptCode, 70); // 部門コード
		tbl.addColumn(SC.SWK_DEP_NAME_S, lblDeptName, 90); // 部門
		tbl.addColumn(SC.SWK_KMK_CODE, kmkName + lblCode, 60); // 科目コード
		tbl.addColumn(SC.SWK_KMK_NAME_S, kmkName, 80); // 科目
		tbl.addColumn(SC.SWK_HKM_CODE, hkmName + lblCode, 60); // 補助科目コード
		tbl.addColumn(SC.SWK_HKM_NAME_S, hkmName, 70); // 補助科目
		tbl.addColumn(SC.SWK_UKM_CODE, ukmName + lblCode, ukm); // 内訳科目コード
		tbl.addColumn(SC.SWK_UKM_NAME_S, ukmName, ukm); // 内訳科目
		tbl.addColumn(SC.SWK_DEN_DATE, "C00599", 75, SwingConstants.CENTER); // 伝票日付
		tbl.addColumn(SC.SWK_DEN_NO, "C00605", 120); // 伝票番号
		tbl.addColumn(SC.SWK_GYO_NO, "C01588", 40, SwingConstants.RIGHT); // 行番号
		tbl.addColumn(SC.SWK_CUR_CODE, "C00371", 30, SwingConstants.CENTER); // 通貨
		tbl.addColumn(SC.SWK_CUR_RATE, "C00556", 55, SwingConstants.RIGHT); // レート
		tbl.addColumn(SC.DR_IN_KIN, "C01556", 85, SwingConstants.RIGHT); // 借方金額(外貨)
		tbl.addColumn(SC.DR_KIN, "C01557", 85, SwingConstants.RIGHT); // 借方金額
		tbl.addColumn(SC.CR_IN_KIN, "C01558", 85, SwingConstants.RIGHT); // 貸方金額(外貨)
		tbl.addColumn(SC.CR_KIN, "C01559", 85, SwingConstants.RIGHT); // 貸方金額
		tbl.addColumn(SC.SWK_GYO_TEK, "C00119", 220); // 行摘要

		tbl.addColumn(SC.SWK_EMP_CODE, lblEmpCode, 70); // 社員コード
		tbl.addColumn(SC.SWK_EMP_NAME_S, lblEmpName, 90); // 社員略称
		tbl.addColumn(SC.SWK_KNR_CODE_1, mng1Name + lblCode, mng1); // 管理１コード
		tbl.addColumn(SC.SWK_KNR_NAME_S_1, mng1Name, mng1); // 管理１略称
		tbl.addColumn(SC.SWK_KNR_CODE_2, mng2Name + lblCode, mng2); // 管理２コード
		tbl.addColumn(SC.SWK_KNR_NAME_S_2, mng2Name, mng2); // 管理２略称
		tbl.addColumn(SC.SWK_KNR_CODE_3, mng3Name + lblCode, mng3); // 管理３コード
		tbl.addColumn(SC.SWK_KNR_NAME_S_3, mng3Name, mng3); // 管理３略称
		tbl.addColumn(SC.SWK_KNR_CODE_4, mng4Name + lblCode, mng4); // 管理４コード
		tbl.addColumn(SC.SWK_KNR_NAME_S_4, mng4Name, mng4); // 管理４略称
		tbl.addColumn(SC.SWK_KNR_CODE_5, mng5Name + lblCode, mng5); // 管理５コード
		tbl.addColumn(SC.SWK_KNR_NAME_S_5, mng5Name, mng5); // 管理５略称
		tbl.addColumn(SC.SWK_KNR_CODE_6, mng6Name + lblCode, mng6); // 管理６コード
		tbl.addColumn(SC.SWK_KNR_NAME_S_6, mng6Name, mng6); // 管理６略称
		tbl.addColumn(SC.SWK_HM_1, hm1Name, hm1, align1); // 非会計明細１
		tbl.addColumn(SC.SWK_HM_2, hm2Name, hm2, align2); // 非会計明細２
		tbl.addColumn(SC.SWK_HM_3, hm3Name, hm3, align3); // 非会計明細３

		tbl.addCheckBoxColumn(SC.CHECK.ordinal());
		tbl.table.enableInputMethods(false); // テーブルのIMEは常にOFF

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlSlipDate.setTabControlNo(i++);
		ctrlKCompany.setTabControlNo(i++);
		ctrlItemGroup.setTabControlNo(i++);
		ctrlCustomer.setTabControlNo(i++);
		ctrlDepartment.setTabControlNo(i++);
		ctrlEmployee.setTabControlNo(i++);
		ctrlManage1.setTabControlNo(i++);
		ctrlManage2.setTabControlNo(i++);
		ctrlManage3.setTabControlNo(i++);
		ctrlManage4.setTabControlNo(i++);
		ctrlManage5.setTabControlNo(i++);
		ctrlManage6.setTabControlNo(i++);
		ctrlNonAcDetails.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
	}
}
