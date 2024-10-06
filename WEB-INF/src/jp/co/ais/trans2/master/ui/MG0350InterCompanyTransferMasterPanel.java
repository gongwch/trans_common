package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.AccountConfig;

/**
 * 会社間付替マスタ
 * 
 * @author AIS
 */
public class MG0350InterCompanyTransferMasterPanel extends TMainPanel {

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 編集ボタン */
	public TImageButton btnModify;

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** エクセルボタン */
	public TImageButton btnExcel;

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 範囲検索 */
	public TCompanyReferenceRange ctrlRange;

	/** 一覧 */
	public TTable tbl;

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		KTK_KAI_CODE,
		KTK_KAI_NAME,
		KTK_DEP_CODE,
		KTK_DEP_NAME,
		KTK_KMK_CODE,
		KTK_KMK_NAME,
		KTK_HKM_CODE,
		KTK_HKM_NAME,
		KTK_UKM_CODE,
		KTK_UKM_NAME,
		KTK_TRI_CODE,
		KTK_TRI_NAME
	}

	/**
	 * コンストラクタ
	 */
	public MG0350InterCompanyTransferMasterPanel() {
		super();
	}

	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		ctrlRange = new TCompanyReferenceRange();

		AccountConfig ac = TLoginInfo.getCompany().getAccountConfig();

		tbl = new TTable();
		tbl.addColumn(SC.KTK_KAI_CODE, getWord("C02050"), 130);//付替会社コード
		tbl.addColumn(SC.KTK_KAI_NAME, getWord("C11150"), 200);//付替会社略称
		tbl.addColumn(SC.KTK_DEP_CODE, getWord("C02051"), 130);//付替計上部門コード
		tbl.addColumn(SC.KTK_DEP_NAME, getWord("C11151"), 200);//付替計上部門略称
		tbl.addColumn(SC.KTK_KMK_CODE, getWord("C00375") + ac.getItemName() + getWord("C00174"), 130);//付替 コード
		tbl.addColumn(SC.KTK_KMK_NAME, getWord("C00375") + ac.getItemName() + getWord("C00548"), 200);//付替 略称
		tbl.addColumn(SC.KTK_HKM_CODE, getWord("C00375") + ac.getSubItemName() + getWord("C00174"), 130);//付替 コード
		tbl.addColumn(SC.KTK_HKM_NAME, getWord("C00375") + ac.getSubItemName() + getWord("C00548"), 200);//付替 略称
		if (ac.isUseDetailItem()) {
			tbl.addColumn(SC.KTK_UKM_CODE, getWord("C00375") + ac.getDetailItemName() + getWord("C00174"), 130);//付替 コード
			tbl.addColumn(SC.KTK_UKM_NAME, getWord("C00375") + ac.getDetailItemName() + getWord("C00548"), 200);//付替 略称
		} else {
			tbl.addColumn(SC.KTK_UKM_CODE, "", -1, false);
			tbl.addColumn(SC.KTK_UKM_NAME, "", -1, false);			
		}
		tbl.addColumn(SC.KTK_TRI_CODE, getWord("C00786"), 130);//取引先コード
		tbl.addColumn(SC.KTK_TRI_NAME, getWord("C00787"), 200);//取引先略称

	}

	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// 複写ボタン
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 上部
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 範囲検索
		ctrlRange.setLocation(20, 20);
		pnlSearchCondition.add(ctrlRange);

		// 下部
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlRange.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
