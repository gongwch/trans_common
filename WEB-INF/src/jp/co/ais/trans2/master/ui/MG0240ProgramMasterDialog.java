package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * プログラムマスタの編集画面
 * 
 * @author AIS
 */
public class MG0240ProgramMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 8557714357271090391L;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** システム区分 */
	public TSystemClassificationReference ctrlSystem;

	/** コード */
	public TLabelField ctrlProgramCode;

	/** 名称 */
	public TLabelField ctrlProgramName;

	/** 略称 */
	public TLabelField ctrlProgramNames;

	/** 検索名称 */
	public TLabelField ctrlProgramNamek;

	/** コメント */
	public TLabelField ctrlComment;

	/** ロードモジュールファイル名 */
	public TLabelField ctrlModuleName;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0240ProgramMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlSystem = new TSystemClassificationReference();
		ctrlProgramCode = new TLabelField();
		ctrlProgramName = new TLabelField();
		ctrlProgramNames = new TLabelField();
		ctrlProgramNamek = new TLabelField();
		ctrlComment = new TLabelField();
		ctrlModuleName = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(510, 320);

		// 確定ボタン
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// システム区分
		x = 50;
		int y = 10;
		ctrlSystem.setLocation(x + 45, y);
		ctrlSystem.getController().setShow3rdColumn(false);
		pnlBody.add(ctrlSystem);

		// コード
		ctrlProgramCode.setLabelSize(120);
		ctrlProgramCode.setFieldSize(75);
		ctrlProgramCode.setSize(200, 20);
		ctrlProgramCode.setLocation(x, y += 25);
		ctrlProgramCode.setLabelText(getWord("C00818"));
		ctrlProgramCode.setMaxLength(TransUtil.PROGRAM_CODE_LENGTH);
		ctrlProgramCode.setImeMode(false);
		ctrlProgramCode.setAllowedSpace(false);
		pnlBody.add(ctrlProgramCode);

		// 名称
		ctrlProgramName.setLabelSize(120);
		ctrlProgramName.setFieldSize(300);
		ctrlProgramName.setSize(425, 20);
		ctrlProgramName.setLocation(x, y += 25);
		ctrlProgramName.setLabelText(getWord("C00819"));
		ctrlProgramName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlBody.add(ctrlProgramName);

		// 略称
		ctrlProgramNames.setLabelSize(120);
		ctrlProgramNames.setFieldSize(150);
		ctrlProgramNames.setSize(275, 20);
		ctrlProgramNames.setLocation(x, y += 25);
		ctrlProgramNames.setLabelText(getWord("C00820"));
		ctrlProgramNames.setMaxLength(TransUtil.PROGRAM_NAMES_LENGTH);
		pnlBody.add(ctrlProgramNames);

		// 検索名称
		ctrlProgramNamek.setLabelSize(120);
		ctrlProgramNamek.setFieldSize(300);
		ctrlProgramNamek.setSize(425, 20);
		ctrlProgramNamek.setLocation(x, y += 25);
		ctrlProgramNamek.setLabelText(TModelUIUtil.getShortWord("C00821"));
		ctrlProgramNamek.setMaxLength(TransUtil.PROGRAM_NAMEK_LENGTH);
		pnlBody.add(ctrlProgramNamek);

		// コメント
		ctrlComment.setLabelSize(120);
		ctrlComment.setFieldSize(300);
		ctrlComment.setSize(425, 20);
		ctrlComment.setLocation(x, y += 25);
		ctrlComment.setLabelText(getWord("C00183"));
		ctrlComment.setMaxLength(TransUtil.PROGRAM_COMMENT_LENGTH);
		pnlBody.add(ctrlComment);

		// ロードモジュールファイル名
		ctrlModuleName.setLabelSize(160);
		ctrlModuleName.setFieldSize(300);
		ctrlModuleName.setSize(465, 20);
		ctrlModuleName.setLocation(x - 40, y += 25);
		ctrlModuleName.setLabelText(getWord("C00823"));
		ctrlModuleName.setMaxLength(TransUtil.PROGRAM_LOADMODULE_LENGTH);
		ctrlModuleName.setImeMode(false);
		pnlBody.add(ctrlModuleName);

		// 開始年月日
		dtBeginDate.setLabelSize(120);
		dtBeginDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(x, y += 25);
		dtBeginDate.setLangMessageID("C00055");
		pnlBody.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(120);
		dtEndDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(x, y += 25);
		dtEndDate.setLangMessageID("C00261");
		pnlBody.add(dtEndDate);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlSystem.setTabControlNo(i++);
		ctrlProgramCode.setTabControlNo(i++);
		ctrlProgramName.setTabControlNo(i++);
		ctrlProgramNames.setTabControlNo(i++);
		ctrlProgramNamek.setTabControlNo(i++);
		ctrlComment.setTabControlNo(i++);
		ctrlModuleName.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
