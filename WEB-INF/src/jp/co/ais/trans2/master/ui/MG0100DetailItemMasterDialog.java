package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.model.ui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 内訳科目マスタの編集画面
 */
public class MG0100DetailItemMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 科目コード */
	public TItemReference ctrlItem;

	/** 補助科目コード */
	public TSubItemReference ctrlSubItem;

	/** 内訳科目コード */
	public TLabelField ctrlDetailItemCode;

	/** 内訳科目名称 */
	public TLabelField ctrlDetailItemName;

	/** 内訳科目略称 */
	public TLabelField ctrlDetailItemNames;

	/** 内訳科目検索名称 */
	public TLabelField ctrlDetailItemNamek;

	/** 消費税 */
	public TTaxReference TTaxReference;

	/** 取引先入力フラグ */
	public TLabelComboBox cmbtricodeflg;

	/** チェック */
	public ItemCheckBox chk;

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
	public MG0100DetailItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlItem = new TItemReference();
		ctrlSubItem = new TSubItemReference();
		ctrlDetailItemCode = new TLabelField();
		ctrlDetailItemName = new TLabelField();
		ctrlDetailItemNames = new TLabelField();
		ctrlDetailItemNamek = new TLabelField();
		TTaxReference = new TTaxReference();
		cmbtricodeflg = new TLabelComboBox();
		chk = new ItemCheckBox(company);
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(750, 525);

		// 確定ボタン
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 250, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 250, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// 科目コード
		ctrlItem.setLocation(-5, 10);
		ctrlItem.btn.setLangMessageID("C00077");
		ctrlItem.setSize(new Dimension(410, 20));
		pnlBody.add(ctrlItem);

		// 補助科目コード
		ctrlSubItem.setLocation(-5, 35);
		ctrlSubItem.btn.setLangMessageID("C00487");
		ctrlSubItem.setSize(new Dimension(410, 20));
		pnlBody.add(ctrlSubItem);

		// 内訳科目コード
		ctrlDetailItemCode.setLabelSize(110);
		ctrlDetailItemCode.setFieldSize(100);
		ctrlDetailItemCode.setSize(215, 20);
		ctrlDetailItemCode.setLocation(10, 60);
		ctrlDetailItemCode.setLabelText(getWord("C00876"));
		ctrlDetailItemCode.setMaxLength(10);
		ctrlDetailItemCode.setImeMode(false);
		ctrlDetailItemCode.setAllowedSpace(false);
		pnlBody.add(ctrlDetailItemCode);

		// 内訳科目名称
		ctrlDetailItemName.setLabelSize(110);
		ctrlDetailItemName.setFieldSize(380);
		ctrlDetailItemName.setSize(495, 20);
		ctrlDetailItemName.setLocation(10, 85);
		ctrlDetailItemName.setLabelText(getWord("C00877"));
		ctrlDetailItemName.setMaxLength(40);
		pnlBody.add(ctrlDetailItemName);

		// 内訳科目略称
		ctrlDetailItemNames.setLabelSize(110);
		ctrlDetailItemNames.setFieldSize(150);
		ctrlDetailItemNames.setSize(265, 20);
		ctrlDetailItemNames.setLocation(10, 110);
		ctrlDetailItemNames.setLabelText(getWord("C00878"));
		ctrlDetailItemNames.setMaxLength(20);
		pnlBody.add(ctrlDetailItemNames);

		// 内訳科目検索名称
		ctrlDetailItemNamek.setLabelSize(110);
		ctrlDetailItemNamek.setFieldSize(380);
		ctrlDetailItemNamek.setSize(495, 20);
		ctrlDetailItemNamek.setLocation(10, 135);
		ctrlDetailItemNamek.setLabelText(getWord("C00879"));
		ctrlDetailItemNamek.setMaxLength(40);
		pnlBody.add(ctrlDetailItemNamek);

		// 消費税
		TTaxReference.setLocation(10, 160);
		TTaxReference.btn.setLangMessageID("C00286");
		TGuiUtil.setComponentWidth(TTaxReference.name, 230);
		TTaxReference.setSize(new Dimension(410, 20));
		pnlBody.add(TTaxReference);

		// 取引先入力フラグ
		cmbtricodeflg.setLabelSize(100);
		cmbtricodeflg.setComboSize(130);
		cmbtricodeflg.setSize(235, 20);
		cmbtricodeflg.setLocation(98, 190);
		cmbtricodeflg.setLangMessageID("C01134");
		TriComboBox(cmbtricodeflg.getComboBox());
		pnlBody.add(cmbtricodeflg);

		// ﾁｪｯｸﾎﾞｯｸｽ
		chk.setLocation(30, 225);
		chk.setSize(700, 500);
		pnlBody.add(chk);

		// 開始年月日
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 380);
		dtBeginDate.setLangMessageID("C00055");
		pnlBody.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 410);
		dtEndDate.setLangMessageID("C00261");
		pnlBody.add(dtEndDate);
	}

	/**
	 * 取引先入力フラグコンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void TriComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(CustomerType.NONE, getWord(CustomerType.getName(CustomerType.NONE)));
		comboBox.addTextValueItem(CustomerType.CUSTOMER, getWord(CustomerType.getName(CustomerType.CUSTOMER)));
		comboBox.addTextValueItem(CustomerType.VENDOR, getWord(CustomerType.getName(CustomerType.VENDOR)));
		comboBox.addTextValueItem(CustomerType.BOTH, getWord(CustomerType.getName(CustomerType.BOTH)));
		comboBox.setSelectedItemValue(CustomerType.NONE);
	}

	@Override
	public void setTabIndex() {

		int i = 0;

		ctrlItem.setTabControlNo(i++);
		ctrlSubItem.setTabControlNo(i++);
		ctrlDetailItemCode.setTabControlNo(i++);
		ctrlDetailItemName.setTabControlNo(i++);
		ctrlDetailItemNames.setTabControlNo(i++);
		ctrlDetailItemNamek.setTabControlNo(i++);
		TTaxReference.setTabControlNo(i++);
		cmbtricodeflg.setTabControlNo(i++);
		chk.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}