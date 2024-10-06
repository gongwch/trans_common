package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.model.ui.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目マスタの編集画面
 */
public class MG0080ItemMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 科目コード */
	public TLabelField ctrlItemCode;

	/** 科目名称 */
	public TLabelField ctrlItemName;

	/** 科目略称 */
	public TLabelField ctrlItemNames;

	/** 科目検索名称 */
	public TLabelField ctrlItemNamek;

	/** 科目種別 */
	public TLabelComboBox comKmkshu;

	/** 補助区分 */
	public TLabelComboBox comHjokbn;

	/** 入力 */
	public TRadioButton rdoImput;

	/** 集計 */
	public TRadioButton rdoSum;

	/** 見出 */
	public TRadioButton rdoTitle;

	/** 集計区分 ButtonGroup */
	public ButtonGroup bgSum;

	/** 集計区分 */
	public TPanel pnlSumkbn;

	/** 貸方 */
	public TRadioButton rdoCredit;

	/** 借方 */
	public TRadioButton rdoDebit;

	/** 貸借区分 ButtonGroup */
	public ButtonGroup bgDc;

	/** 貸借区分 */
	public TPanel pnlDckbn;

	/** チェック */
	public TItemStatusUnit chk;

	/** 固定部門 */
	public TDepartmentReference ctrlFixDepartment;

	/** 消費税 */
	public TTaxReference ctrlTax;

	/** GL科目制御区分 */
	public TLabelComboBox cmbcntgl;

	/** AP科目制御区分 */
	public TLabelComboBox cmbcntap;

	/** AR科目制御区分 */
	public TLabelComboBox cmbcntar;

	/** BG科目制御区分 */
	public TLabelComboBox cmbcntbg;

	/** 取引先入力フラグ */
	public TLabelComboBox cmbtricodeflg;

	/** 相殺科目制御区分 */
	public TLabelComboBox cmbcntsousai;

	/** BS勘定消込区分 */
	public TLabelComboBox cmbkesikbn;

	/** 評価替対象フラグ */
	public TLabelComboBox cmbexcflg;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0080ItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlItemCode = new TLabelField();
		ctrlItemName = new TLabelField();
		ctrlItemNames = new TLabelField();
		ctrlItemNamek = new TLabelField();
		comKmkshu = new TLabelComboBox();
		comHjokbn = new TLabelComboBox();
		rdoImput = new TRadioButton();
		rdoSum = new TRadioButton();
		rdoTitle = new TRadioButton();
		pnlSumkbn = new TPanel();
		rdoCredit = new TRadioButton();
		rdoDebit = new TRadioButton();
		pnlDckbn = new TPanel();
		chk = new TItemStatusUnit(company);
		ctrlFixDepartment = new TDepartmentReference();
		ctrlTax = new TTaxReference();
		cmbcntgl = new TLabelComboBox();
		cmbcntap = new TLabelComboBox();
		cmbcntar = new TLabelComboBox();
		cmbcntbg = new TLabelComboBox();
		cmbtricodeflg = new TLabelComboBox();
		cmbcntsousai = new TLabelComboBox();
		cmbkesikbn = new TLabelComboBox();
		cmbexcflg = new TLabelComboBox();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		bgSum = new ButtonGroup();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setSize(790, 610);

		// 確定ボタン
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 300, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 300, HEADER_Y);
		pnlHeader.add(btnClose);
		pnlBody.setLayout(null);

		x = 20;
		int y = 20;

		// 入力
		rdoImput.setLangMessageID("C01275");
		rdoImput.setSize(60, 15);
		rdoImput.setSelected(true);
		rdoImput.setLocation(x, y);
		rdoImput.setIndex(0);

		x += rdoImput.getWidth();

		// 集計
		rdoSum.setLangMessageID("C00255");
		rdoSum.setSize(60, 15);
		rdoSum.setLocation(x, y);
		rdoSum.setIndex(1);

		x += rdoSum.getWidth();

		// 見出
		rdoTitle.setLangMessageID("C00506");
		rdoTitle.setSize(60, 15);
		rdoTitle.setLocation(x, y);
		rdoTitle.setIndex(2);

		bgSum.add(rdoImput);
		bgSum.add(rdoSum);
		bgSum.add(rdoTitle);

		pnlSumkbn.add(rdoImput);
		pnlSumkbn.add(rdoSum);
		pnlSumkbn.add(rdoTitle);

		// 集計区分パネル
		pnlSumkbn.setLayout(null);
		pnlSumkbn.setLangMessageID("C01148");
		pnlSumkbn.setSize(220, 48);
		pnlSumkbn.setLocation(30, 2);
		pnlBody.add(pnlSumkbn);

		// 借方
		rdoDebit.setLangMessageID("C00080");
		rdoDebit.setSize(60, 15);
		rdoDebit.setLocation(24, 20);
		rdoDebit.setSelected(true);
		rdoDebit.setIndex(0);
		pnlBody.add(rdoDebit);

		// 貸方
		rdoCredit.setLangMessageID("C00068");
		rdoCredit.setSize(60, 15);
		rdoCredit.setLocation(rdoDebit.getX() + 60, rdoDebit.getY());
		rdoCredit.setIndex(1);
		pnlBody.add(rdoCredit);

		bgDc = new ButtonGroup();
		bgDc.add(rdoCredit);
		bgDc.add(rdoDebit);

		pnlDckbn.add(rdoDebit);
		pnlDckbn.add(rdoCredit);

		// 貸借区分パネル
		pnlDckbn.setLayout(null);
		pnlDckbn.setLangMessageID("C01226");
		pnlDckbn.setSize(150, 48);
		pnlDckbn.setLocation(260, 2);
		pnlBody.add(pnlDckbn);

		// 科目種別
		comKmkshu.setLabelSize(100);
		comKmkshu.setComboSize(160);
		comKmkshu.setSize(265, 20);
		comKmkshu.setLocation(370, 5);
		comKmkshu.setLangMessageID("C01007");
		KmkshuComboBox(comKmkshu.getComboBox());
		pnlBody.add(comKmkshu);

		// 補助区分
		comHjokbn.setLabelSize(100);
		comHjokbn.setComboSize(160);
		comHjokbn.setSize(265, 20);
		comHjokbn.setLocation(370, 30);
		comHjokbn.setLangMessageID("C01314");
		HjokbnComboBox(comHjokbn.getComboBox());
		pnlBody.add(comHjokbn);

		// 科目コード
		ctrlItemCode.setLabelSize(110);
		ctrlItemCode.setFieldSize(100);
		ctrlItemCode.setSize(215, 20);
		ctrlItemCode.setLocation(10, 65);
		ctrlItemCode.setLangMessageID("C00572");
		ctrlItemCode.setMaxLength(10);
		ctrlItemCode.setImeMode(false);
		ctrlItemCode.setAllowedSpace(false);
		pnlBody.add(ctrlItemCode);

		// 科目名称
		ctrlItemName.setLabelSize(110);
		ctrlItemName.setFieldSize(380);
		ctrlItemName.setSize(495, 20);
		ctrlItemName.setLocation(10, 90);
		ctrlItemName.setLangMessageID("C00700");
		ctrlItemName.setMaxLength(40);
		pnlBody.add(ctrlItemName);

		// 科目略称
		ctrlItemNames.setLabelSize(110);
		ctrlItemNames.setFieldSize(150);
		ctrlItemNames.setSize(265, 20);
		ctrlItemNames.setLocation(10, 115);
		ctrlItemNames.setLangMessageID("C00730");
		ctrlItemNames.setMaxLength(20);
		pnlBody.add(ctrlItemNames);

		// 科目検索名称
		ctrlItemNamek.setLabelSize(110);
		ctrlItemNamek.setFieldSize(380);
		ctrlItemNamek.setSize(495, 20);
		ctrlItemNamek.setLocation(10, 140);
		ctrlItemNamek.setLangMessageID("C00731");
		ctrlItemNamek.setMaxLength(40);
		pnlBody.add(ctrlItemNamek);

		// 固定部門
		ctrlFixDepartment.setLocation(35, 165);
		ctrlFixDepartment.btn.setLangMessageID("C00732");
		TGuiUtil.setComponentWidth(ctrlFixDepartment.name, 230);
		ctrlFixDepartment.setSize(new Dimension(410, 20));
		pnlBody.add(ctrlFixDepartment);

		// 消費税
		ctrlTax.setLocation(10, 190);
		ctrlTax.btn.setLangMessageID("C00286");
		TGuiUtil.setComponentWidth(ctrlTax.name, 230);
		ctrlTax.setSize(new Dimension(410, 20));
		pnlBody.add(ctrlTax);

		// GL科目制御区分
		cmbcntgl.setLabelSize(100);
		cmbcntgl.setComboSize(160);
		cmbcntgl.setSize(265, 20);
		cmbcntgl.setLocation(20, 230);
		cmbcntgl.setLangMessageID("C00968");
		GlComboBox(cmbcntgl.getComboBox());
		pnlBody.add(cmbcntgl);

		// AP科目制御区分
		cmbcntap.setLabelSize(100);
		cmbcntap.setComboSize(160);
		cmbcntap.setSize(265, 20);
		cmbcntap.setLocation(20, 255);
		cmbcntap.setLangMessageID("C00959");
		ApComboBox(cmbcntap.getComboBox());
		pnlBody.add(cmbcntap);

		// AR科目制御区分
		cmbcntar.setLabelSize(100);
		cmbcntar.setComboSize(160);
		cmbcntar.setSize(265, 20);
		cmbcntar.setLocation(20, 280);
		cmbcntar.setLangMessageID("C00960");
		ArComboBox(cmbcntar.getComboBox());
		pnlBody.add(cmbcntar);

		// BG科目制御区分
		cmbcntbg.setLabelSize(100);
		cmbcntbg.setComboSize(160);
		cmbcntbg.setSize(265, 20);
		cmbcntbg.setLocation(20, 305);
		cmbcntbg.setLangMessageID("C00962");
		BgComboBox(cmbcntbg.getComboBox());
		pnlBody.add(cmbcntbg);

		// 取引先入力フラグ
		cmbtricodeflg.setLabelSize(100);
		cmbtricodeflg.setComboSize(160);
		cmbtricodeflg.setSize(265, 20);
		cmbtricodeflg.setLocation(20, 330);
		cmbtricodeflg.setLangMessageID("C01134");
		TriComboBox(cmbtricodeflg.getComboBox());
		pnlBody.add(cmbtricodeflg);

		// 相殺科目制御区分
		cmbcntsousai.setLabelSize(120);
		cmbcntsousai.setComboSize(160);
		cmbcntsousai.setSize(285, 20);
		cmbcntsousai.setLocation(0, 355);
		cmbcntsousai.setLangMessageID("C01217");
		CntsousaiComboBox(cmbcntsousai.getComboBox());
		pnlBody.add(cmbcntsousai);

		// BS勘定消込区分
		cmbkesikbn.setLabelSize(100);
		cmbkesikbn.setComboSize(160);
		cmbkesikbn.setSize(265, 20);
		cmbkesikbn.setLocation(20, 380);
		cmbkesikbn.setLangMessageID("C02078");
		BsKesiComboBox(cmbkesikbn.getComboBox());
		pnlBody.add(cmbkesikbn);

		// 評価替対象フラグ
		cmbexcflg.setLabelSize(100);
		cmbexcflg.setComboSize(160);
		cmbexcflg.setSize(265, 20);
		cmbexcflg.setLocation(20, 405);
		cmbexcflg.setLangMessageID("C01301");
		ExcComboBox(cmbexcflg.getComboBox());
		pnlBody.add(cmbexcflg);

		// ﾁｪｯｸﾎﾞｯｸｽ
		chk.setLocation(550, 65);
		chk.setSize(700, 500);
		pnlBody.add(chk);

		// 開始年月日
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 450);
		dtBeginDate.setLangMessageID("C00055");
		pnlBody.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 475);
		dtEndDate.setLangMessageID("C00261");
		pnlBody.add(dtEndDate);
	}

	/**
	 * 科目種別コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void KmkshuComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(ItemType.DC, getShortWord(ItemType.getName(ItemType.DC)));
		comboBox.addTextValueItem(ItemType.PL, getShortWord(ItemType.getName(ItemType.PL)));
		comboBox.addTextValueItem(ItemType.APPROPRIATION, getWord(ItemType.getName(ItemType.APPROPRIATION)));
		comboBox.addTextValueItem(ItemType.MANUFACTURING_COSTS,
			getShortWord(ItemType.getName(ItemType.MANUFACTURING_COSTS)));
		comboBox.addTextValueItem(ItemType.NON_ACCOUNT, getWord(ItemType.getName(ItemType.NON_ACCOUNT)));
	}

	/**
	 * 補助区分コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void HjokbnComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(null, "");
		comboBox.addTextValueItem(false, getWord("C00412")); // なし
		comboBox.addTextValueItem(true, getWord("C00006")); // あり
		comboBox.setSelectedItemValue(false);
	}

	/**
	 * GL科目制御区分コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void GlComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(GLType.NOMAL, getWord(GLType.getName(GLType.NOMAL)));
		comboBox.addTextValueItem(GLType.FUND, getWord(GLType.getName(GLType.FUND)));
		comboBox.addTextValueItem(GLType.SALES, getWord(GLType.getName(GLType.SALES)));
		comboBox.addTextValueItem(GLType.TEMPORARY, getWord(GLType.getName(GLType.TEMPORARY)));
		comboBox.setSelectedItemValue(GLType.NOMAL);
	}

	/**
	 * AP科目制御区分コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void ApComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(APType.NOMAL, getWord(APType.getName(APType.NOMAL)));
		comboBox.addTextValueItem(APType.DEBIT, getWord(APType.getName(APType.DEBIT)));
		comboBox.addTextValueItem(APType.EMPLOYEE, getShortWord(APType.getName(APType.EMPLOYEE)));
		comboBox.setSelectedItemValue(APType.NOMAL);
	}

	/**
	 * AR科目制御区分コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void ArComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(ARType.NOMAl, getWord(ARType.getName(ARType.NOMAl)));
		comboBox.addTextValueItem(ARType.AR, getWord(ARType.getName(ARType.AR)));
		comboBox.addTextValueItem(ARType.AR_COLLECT, getWord(ARType.getName(ARType.AR_COLLECT)));
		comboBox.setSelectedItemValue(ARType.NOMAl);
	}

	/**
	 * BG科目制御区分コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void BgComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(BGType.NOMAL, getWord(BGType.getName(BGType.NOMAL)));
		comboBox.addTextValueItem(BGType.BG, getShortWord(BGType.getName(BGType.BG)));
		comboBox.setSelectedItemValue(BGType.NOMAL);
	}

	/**
	 * 取引先入力フラグコンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void TriComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(CustomerType.NONE, getWord(CustomerType.getName(CustomerType.NONE)));
		comboBox.addTextValueItem(CustomerType.CUSTOMER, getWord(CustomerType.getName(CustomerType.CUSTOMER)));
		comboBox.addTextValueItem(CustomerType.VENDOR, getWord(CustomerType.getName(CustomerType.VENDOR)));
		comboBox.addTextValueItem(CustomerType.BOTH, getWord(CustomerType.getName(CustomerType.BOTH)));
		comboBox.setSelectedItemValue(CustomerType.NONE);
	}

	/**
	 * 相殺科目制御区分コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void CntsousaiComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(false, getWord("C02099")); // しない
		comboBox.addTextValueItem(true, getWord("C02100")); // する
		comboBox.setSelectedItemValue(false);
	}

	/**
	 * BS勘定消込区分コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void BsKesiComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(false, getWord("C02099")); // しない
		comboBox.addTextValueItem(true, getWord("C02100")); // する
		comboBox.setSelectedItemValue(false);
	}

	/**
	 * 評価替対象フラグコンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void ExcComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(EvaluationMethod.NOT_ISSUE,
			getWord(EvaluationMethod.getName(EvaluationMethod.NOT_ISSUE)));
		comboBox.addTextValueItem(EvaluationMethod.CANCEL, getWord(EvaluationMethod.getName(EvaluationMethod.CANCEL)));
		comboBox.addTextValueItem(EvaluationMethod.NOT_CANCEL,
			getWord(EvaluationMethod.getName(EvaluationMethod.NOT_CANCEL)));
		comboBox.setSelectedItemValue(EvaluationMethod.NOT_ISSUE);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {

		int i = 0;

		rdoImput.setTabControlNo(i++);
		rdoSum.setTabControlNo(i++);
		rdoTitle.setTabControlNo(i++);
		rdoDebit.setTabControlNo(i++);
		rdoCredit.setTabControlNo(i++);
		comKmkshu.setTabControlNo(i++);
		comHjokbn.setTabControlNo(i++);
		ctrlItemCode.setTabControlNo(i++);
		ctrlItemName.setTabControlNo(i++);
		ctrlItemNames.setTabControlNo(i++);
		ctrlItemNamek.setTabControlNo(i++);
		ctrlFixDepartment.setTabControlNo(i++);
		ctrlTax.setTabControlNo(i++);
		cmbcntgl.setTabControlNo(i++);
		cmbcntap.setTabControlNo(i++);
		cmbcntar.setTabControlNo(i++);
		cmbcntbg.setTabControlNo(i++);
		cmbtricodeflg.setTabControlNo(i++);
		cmbcntsousai.setTabControlNo(i++);
		cmbkesikbn.setTabControlNo(i++);
		cmbexcflg.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		chk.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
