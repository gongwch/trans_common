package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.model.ui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 補助科目マスタの編集画面
 */
public class MG0090SubItemMasterDialog extends TDialog {

	/** 固定部門 表示可否 */
	protected static boolean isUseKoteiDep = ClientConfig.isFlagOn("trans.use.sub.item.kotei.dep");

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 科目コード */
	public TItemReference itemReference;

	/** 補助科目コード */
	public TLabelField ctrlSubItemCode;

	/** 補助科目名称 */
	public TLabelField ctrlSubItemName;

	/** 補助科目略称 */
	public TLabelField ctrlSubItemNames;

	/** 補助科目検索名称 */
	public TLabelField ctrlSubItemNamek;

	/** 消費税 */
	public TTaxReference TTaxReference;

	/** 取引先入力フラグ */
	public TLabelComboBox cmbtricodeflg;

	/** 無 */
	public TRadioButton rdoNo;

	/** 有 */
	public TRadioButton rdoYes;

	/** 内訳区分 ButtonGroup */
	public ButtonGroup bgUkm;

	/** 内訳区分 */
	public TPanel pnlUkm;

	/** 固定部門 */
	public TDepartmentReference ctrlFixDepartment;

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
	public MG0090SubItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		itemReference = new TItemReference();
		ctrlSubItemCode = new TLabelField();
		itemReference = new TItemReference();
		ctrlSubItemName = new TLabelField();
		ctrlSubItemNames = new TLabelField();
		ctrlSubItemNamek = new TLabelField();
		ctrlFixDepartment = new TDepartmentReference();
		TTaxReference = new TTaxReference();
		pnlUkm = new TPanel();
		rdoNo = new TRadioButton();
		rdoYes = new TRadioButton();
		cmbtricodeflg = new TLabelComboBox();
		chk = new ItemCheckBox(company);
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(750, 500);

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
		itemReference.setLocation(-5, 10);
		itemReference.btn.setLangMessageID("C00077");
		itemReference.setSize(new Dimension(410, 20));
		pnlBody.add(itemReference);

		// 補助科目コード
		ctrlSubItemCode.setLabelSize(110);
		ctrlSubItemCode.setFieldSize(100);
		ctrlSubItemCode.setSize(215, 20);
		ctrlSubItemCode.setLocation(10, 35);
		ctrlSubItemCode.setLabelText(getWord("C00890"));
		ctrlSubItemCode.setMaxLength(10);
		ctrlSubItemCode.setImeMode(false);
		ctrlSubItemCode.setAllowedSpace(false);
		pnlBody.add(ctrlSubItemCode);

		// 補助科目名称
		ctrlSubItemName.setLabelSize(110);
		ctrlSubItemName.setFieldSize(380);
		ctrlSubItemName.setSize(495, 20);
		ctrlSubItemName.setLocation(10, 60);
		ctrlSubItemName.setLabelText(getWord("C00934"));
		ctrlSubItemName.setMaxLength(40);
		pnlBody.add(ctrlSubItemName);

		// 補助科目略称
		ctrlSubItemNames.setLabelSize(110);
		ctrlSubItemNames.setFieldSize(150);
		ctrlSubItemNames.setSize(265, 20);
		ctrlSubItemNames.setLocation(10, 85);
		ctrlSubItemNames.setLabelText(getWord("C00933"));
		ctrlSubItemNames.setMaxLength(20);
		pnlBody.add(ctrlSubItemNames);

		// 補助科目検索名称
		ctrlSubItemNamek.setLabelSize(110);
		ctrlSubItemNamek.setFieldSize(380);
		ctrlSubItemNamek.setSize(495, 20);
		ctrlSubItemNamek.setLocation(10, 110);
		ctrlSubItemNamek.setLabelText(getWord("C00935"));
		ctrlSubItemNamek.setMaxLength(40);
		pnlBody.add(ctrlSubItemNamek);

		int addY = 0;
		if (isUseKoteiDep) {
			// 固定部門
			ctrlFixDepartment.setLocation(35, ctrlSubItemNamek.getY() + ctrlSubItemNamek.getHeight() + 2);
			ctrlFixDepartment.btn.setLangMessageID("C00732");
			TGuiUtil.setComponentWidth(ctrlFixDepartment.name, 230);
			ctrlFixDepartment.setSize(new Dimension(410, 20));
			pnlBody.add(ctrlFixDepartment);

			addY += ctrlFixDepartment.getHeight();
			setSize(750, 500 + addY);
		}

		// 消費税
		TTaxReference.setLocation(10, 135 + addY);
		TTaxReference.btn.setLangMessageID("C00286");
		TGuiUtil.setComponentWidth(TTaxReference.name, 230);
		TTaxReference.setSize(new Dimension(410, 20));
		pnlBody.add(TTaxReference);

		x = 552;
		int y = 105;

		// 無
		rdoNo.setLangMessageID("C00412");
		rdoNo.setSize(80, 15);
		rdoNo.setLocation(x, y);
		rdoNo.setSelected(true);
		rdoNo.setIndex(0);
		pnlBody.add(rdoNo);

		x += rdoNo.getWidth();

		// 有
		rdoYes.setLangMessageID("C00006");
		rdoYes.setSize(80, 15);
		rdoYes.setLocation(x, y);
		rdoYes.setIndex(1);
		pnlBody.add(rdoYes);

		bgUkm = new ButtonGroup();
		bgUkm.add(rdoNo);
		bgUkm.add(rdoYes);

		// 内訳区分
		pnlUkm.setLangMessageID("C01264");
		pnlUkm.setSize(180, 48);
		pnlUkm.setLocation(540, 85);
		pnlBody.add(pnlUkm);

		// 取引先入力フラグ
		cmbtricodeflg.setLabelSize(100);
		cmbtricodeflg.setComboSize(130);
		cmbtricodeflg.setSize(235, 20);
		cmbtricodeflg.setLocation(98, 165 + addY);
		cmbtricodeflg.setLangMessageID("C01134");
		TriComboBox(cmbtricodeflg.getComboBox());
		pnlBody.add(cmbtricodeflg);

		// ﾁｪｯｸﾎﾞｯｸｽ
		chk.setLocation(30, 200 + addY);
		chk.setSize(700, 500);
		pnlBody.add(chk);

		// 開始年月日
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 350 + addY);
		dtBeginDate.setLangMessageID("C00055");
		pnlBody.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 375 + addY);
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

		itemReference.setTabControlNo(i++);
		ctrlSubItemCode.setTabControlNo(i++);
		ctrlSubItemName.setTabControlNo(i++);
		ctrlSubItemNames.setTabControlNo(i++);
		ctrlSubItemNamek.setTabControlNo(i++);
		if (isUseKoteiDep) {
			// 固定部門
			ctrlFixDepartment.setTabControlNo(i++);
		}
		TTaxReference.setTabControlNo(i++);
		rdoNo.setTabControlNo(i++);
		rdoYes.setTabControlNo(i++);
		cmbtricodeflg.setTabControlNo(i++);
		chk.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}