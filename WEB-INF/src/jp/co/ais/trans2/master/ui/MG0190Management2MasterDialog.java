package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.define.TransUtil;
import jp.co.ais.trans2.model.company.*;

/**
 * 管理２マスタの編集画面
 * 
 * @author AIS
 */
public class MG0190Management2MasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = -6478739587157723331L;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** コード */
	public TLabelField ctrlManagementCode;

	/** 名称 */
	public TLabelField ctrlManagementName;

	/** 略称 */
	public TLabelField ctrlManagementNames;

	/** 検索名称 */
	public TLabelField ctrlManagementNamek;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0190Management2MasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlManagementCode = new TLabelField();
		ctrlManagementName = new TLabelField();
		ctrlManagementNames = new TLabelField();
		ctrlManagementNamek = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
	}

	/**
	 * コンポーネント配置
	 */
	@Override
	public void allocateComponents() {

		setSize(520, 280);

		// 確定ボタン
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		String mngt = "";
		if (Util.isNullOrEmpty(company.getAccountConfig().getManagement2Name())) {
			mngt = getShortWord("C01025");
		} else {
			mngt = company.getAccountConfig().getManagement2Name();
		}

		// コード
		ctrlManagementCode.setLabelSize(120);
		ctrlManagementCode.setFieldSize(75);
		ctrlManagementCode.setSize(200, 20);
		ctrlManagementCode.setLocation(10, 10);

		ctrlManagementCode.setLabelText(mngt + getWord("C00174"));
		ctrlManagementCode.setMaxLength(TransUtil.MANAGEMENT1_CODE_LENGTH);
		ctrlManagementCode.setImeMode(false);
		ctrlManagementCode.setAllowedSpace(false);
		pnlBody.add(ctrlManagementCode);

		// 名称
		ctrlManagementName.setLabelSize(120);
		ctrlManagementName.setFieldSize(300);
		ctrlManagementName.setSize(425, 20);
		ctrlManagementName.setLocation(10, 40);
		ctrlManagementName.setLabelText(mngt + getShortWord("C00518"));
		ctrlManagementName.setMaxLength(TransUtil.MANAGEMENT1_NAME_LENGTH);
		pnlBody.add(ctrlManagementName);

		// 略称
		ctrlManagementNames.setLabelSize(120);
		ctrlManagementNames.setFieldSize(150);
		ctrlManagementNames.setSize(275, 20);
		ctrlManagementNames.setLocation(10, 70);
		ctrlManagementNames.setLabelText(mngt + getShortWord("C00548"));
		ctrlManagementNames.setMaxLength(TransUtil.MANAGEMENT1_NAMES_LENGTH);
		pnlBody.add(ctrlManagementNames);

		// 検索名称
		ctrlManagementNamek.setLabelSize(120);
		ctrlManagementNamek.setFieldSize(300);
		ctrlManagementNamek.setSize(425, 20);
		ctrlManagementNamek.setLocation(10, 100);
		ctrlManagementNamek.setLabelText(mngt + getShortWord("C00160"));
		ctrlManagementNamek.setMaxLength(TransUtil.MANAGEMENT1_NAMEK_LENGTH);
		pnlBody.add(ctrlManagementNamek);

		// 開始年月日
		dtBeginDate.setLabelSize(120);
		dtBeginDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 130);
		dtBeginDate.setLangMessageID("C00055");
		pnlBody.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(120);
		dtEndDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 160);
		dtEndDate.setLangMessageID("C00261");
		pnlBody.add(dtEndDate);
	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlManagementCode.setTabControlNo(i++);
		ctrlManagementName.setTabControlNo(i++);
		ctrlManagementNames.setTabControlNo(i++);
		ctrlManagementNamek.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}