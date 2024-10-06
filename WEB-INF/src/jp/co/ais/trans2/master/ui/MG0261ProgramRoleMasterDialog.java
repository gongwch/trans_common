package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * プログラムロールマスタの編集画面
 * 
 * @author AIS
 */
public class MG0261ProgramRoleMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 8557714357271090392L;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** コード */
	public TLabelField ctrlProgramCode;

	/** 名称 */
	public TLabelField ctrlProgramName;

	/** 略称 */
	public TLabelField ctrlProgramNames;

	/** 検索名称 */
	public TLabelField ctrlProgramNamek;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/** プログラム一覧 */
	public TTable tbl;

	/** 処理権限: 使用不可 */
	public TRadioButton ctrlTblPROC_OFF;

	/** 処理権限: 読取のみ */
	public TRadioButton ctrlTblREAD_ONLY;

	/** 処理権限: 更新可能 */
	public TRadioButton ctrlTblADMIN;

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {
		/** システムコード */
		systemCode,
		/** プログラムコード */
		programCode,
		/** プログラム名称 */
		programNames,
		/** 使用不可 */
		procOff,
		/** 読取のみ */
		readOnly,
		/** 更新可能 */
		admin
	}

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0261ProgramRoleMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlProgramCode = new TLabelField();
		ctrlProgramName = new TLabelField();
		ctrlProgramNames = new TLabelField();
		ctrlProgramNamek = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();

		ctrlTblPROC_OFF = new TRadioButton();
		ctrlTblREAD_ONLY = new TRadioButton();
		ctrlTblADMIN = new TRadioButton();

		tbl = new TTable();
		tbl.addColumn(SC.systemCode, getWord("C00217"), 100);// システム区分
		tbl.addColumn(SC.programCode, getWord("C00818"), 100);// プログラムコード
		tbl.addColumn(SC.programNames, getWord("C00820"), 200);// プログラム略称
		tbl.addColumn(SC.procOff, getWord("使用不可"), 70, ctrlTblPROC_OFF);// 使用不可
		tbl.addColumn(SC.readOnly, getWord("読取のみ"), 70, ctrlTblREAD_ONLY);// 読取のみ
		tbl.addColumn(SC.admin, getWord("更新可能"), 70, ctrlTblADMIN);// 更新可能
	}

	/**
	 * コンポーネント配置
	 */
	@Override
	public void allocateComponents() {

		setSize(730, 600);

		// 確定ボタン
		int x = 580 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 580;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(480, 155));
		pnlBodyTop.setMinimumSize(new Dimension(480, 155));
		pnlBodyTop.setPreferredSize(new Dimension(480, 155));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// システム区分
		x = 10;
		int y = 10;

		// コード
		ctrlProgramCode.setLabelSize(120);
		ctrlProgramCode.setFieldSize(75);
		ctrlProgramCode.setSize(200, 20);
		ctrlProgramCode.setLocation(x, y);
		ctrlProgramCode.setLabelText(getWord("C11154"));
		ctrlProgramCode.setMaxLength(TransUtil.PROGRAM_CODE_LENGTH);
		ctrlProgramCode.setImeMode(false);
		ctrlProgramCode.setAllowedSpace(false);
		pnlBodyTop.add(ctrlProgramCode);

		// 名称
		ctrlProgramName.setLabelSize(120);
		ctrlProgramName.setFieldSize(300);
		ctrlProgramName.setSize(425, 20);
		ctrlProgramName.setLocation(x, y += 25);
		ctrlProgramName.setLabelText(getWord("C11155"));
		ctrlProgramName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlBodyTop.add(ctrlProgramName);

		// 略称
		ctrlProgramNames.setLabelSize(120);
		ctrlProgramNames.setFieldSize(150);
		ctrlProgramNames.setSize(275, 20);
		ctrlProgramNames.setLocation(x, y += 25);
		ctrlProgramNames.setLabelText(getWord("C11156"));
		ctrlProgramNames.setMaxLength(TransUtil.PROGRAM_NAMES_LENGTH);
		pnlBodyTop.add(ctrlProgramNames);

		// 検索名称
		ctrlProgramNamek.setLabelSize(120);
		ctrlProgramNamek.setFieldSize(300);
		ctrlProgramNamek.setSize(425, 20);
		ctrlProgramNamek.setLocation(x, y += 25);
		// ロール検索名称
		ctrlProgramNamek.setLangMessageID("C11157");
		ctrlProgramNamek.setMaxLength(TransUtil.PROGRAM_NAMEK_LENGTH);
		pnlBodyTop.add(ctrlProgramNamek);

		// 開始年月日
		dtBeginDate.setLabelSize(120);
		dtBeginDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(x, y += 25);
		dtBeginDate.setLangMessageID("C00055");
		pnlBodyTop.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(120);
		dtEndDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(x, y += 25);
		dtEndDate.setLangMessageID("C00261");
		pnlBodyTop.add(dtEndDate);

		TPanel pnlPrg = new TPanel();
		pnlPrg.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		pnlBody.add(pnlPrg, gc);

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlPrg.add(tbl, gc);

	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlProgramCode.setTabControlNo(i++);
		ctrlProgramName.setTabControlNo(i++);
		ctrlProgramNames.setTabControlNo(i++);
		ctrlProgramNamek.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}