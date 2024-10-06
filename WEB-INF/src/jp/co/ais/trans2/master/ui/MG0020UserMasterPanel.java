package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ユーザーマスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class MG0020UserMasterPanel extends TMainPanel {

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

	/** ユーザー範囲検索 */
	public TUserReferenceRange ctrlUserRange;

	/** 有効期間切れを表示するか */
	public TCheckBox chkOutputTermEnd;

	/** 管理1マスタ一覧 */
	public TTable tbl;

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** コード */
		code,
		/** 名称 */
		name,
		/** 略称 */
		names,
		/** 検索名称 */
		namek,
		/** プログラムロールコード */
		program_roleCode,
		/** プログラムロール略称 */
		program_roleNames,
		/** ユーザロールコード */
		user_roleCode,
		/** ユーザロール略称 */
		user_roleNames,
		/** 承認権限ロールコード */
		aprv_roleCode,
		/** 承認権限ロール略称 */
		aprv_roleNames,
		/** INV.SIGNER DEPT */
		signerDept,
		/** INV.SIGNER TITLE */
		signerTitle,
		/** INV.SIGNER NAME */
		signerName,
		/** INV.SIGN FILE NAME */
		signFileName,
		/** EMAIL ADDRESS */
		eMailAddress,
		/** 更新権限区分 */
		updateAuthority,
		/** 決算伝票入力区分 */
		isAccountant,
		/** 社員コード */
		employeeCode,
		/** 社員略称 */
		employeeNames,
		/** 部門コード */
		departmentCode,
		/** 部門略称 */
		departmentNames,
		/** 言語コード */
		language,
		/** FROM */
		dateFrom,
		/** TO */
		dateTo
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
		ctrlUserRange = new TUserReferenceRange();
		chkOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.code, getWord("C00589"), 100);// ユーザーコード
		tbl.addColumn(SC.name, getWord("C00691"), 200);// ユーザー名称
		tbl.addColumn(SC.names, getWord("C00692"), 200);// ユーザー略称
		tbl.addColumn(SC.namek, getWord("C00693"), 200);// ユーザー検索名称
		tbl.addColumn(SC.program_roleCode, getWord("C11159"), 200);// プログラムロールコード
		tbl.addColumn(SC.program_roleNames, getWord("C11213"), 200);// プログラムロール略称
		tbl.addColumn(SC.user_roleCode, getWord("C11161"), 100);// ユーザーロールコード
		tbl.addColumn(SC.user_roleNames, getWord("C11214"), 200);// ユーザーロール略称
		tbl.addColumn(SC.aprv_roleCode, getWord("C11941"), 100);// 承認権限ロールコード
		tbl.addColumn(SC.aprv_roleNames, getWord("C11944"), 200);// 承認権限ロール略称
		if (MG0020UserMasterDialog.USE_BL_SIGNER) {
			tbl.addColumn(SC.signerDept, getWord("CM0074"), 200);// INV.SIGNER DEPT
			tbl.addColumn(SC.signerTitle, getWord("CM0075"), 200);// INV.SIGNER TITLE
			tbl.addColumn(SC.signerName, getWord("CM0076"), 200);// INV.SIGNER NAME
		} else {
			tbl.addColumn(SC.signerDept, "", -1, false);// INV.SIGNER DEPT
			tbl.addColumn(SC.signerTitle, "", -1, false);// INV.SIGNER TITLE
			tbl.addColumn(SC.signerName, "", -1, false);// INV.SIGNER NAME
		}
		if (MG0020UserMasterDialog.USE_SIGNER_ATTACH) {
			tbl.addColumn(SC.signFileName, getWord("SIGN"), 200);// INV.SIGN FILE NAME
		} else {
			tbl.addColumn(SC.signFileName, "", -1, false);// INV.SIGN FILE NAME
		}
		tbl.addColumn(SC.eMailAddress, getWord("COP065"), 200);// E-MAIL
		tbl.addColumn(SC.updateAuthority, getWord("C00170"), 150);// 更新権限レベル
		tbl.addColumn(SC.isAccountant, getWord("C01056"), 150);// 決算伝票入力区分
		tbl.addColumn(SC.employeeCode, getWord("C00697"), 100);// 社員コード
		tbl.addColumn(SC.employeeNames, getWord("C00808"), 200);// 社員略称
		tbl.addColumn(SC.departmentCode, getWord("C02043"), 100);// 所属部門コード
		tbl.addColumn(SC.departmentNames, getWord("C11215"), 200);// 所属部門略称
		tbl.addColumn(SC.language, getWord("C00699"), 100);// 言語コード
		tbl.addColumn(SC.dateFrom, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, getWord("C00261"), 100, SwingConstants.CENTER);
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

		// ユーザー検索範囲
		ctrlUserRange.setLocation(20, 20);
		pnlSearchCondition.add(ctrlUserRange);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
		pnlSearchCondition.add(chkOutputTermEnd);

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
		int i = 0;
		ctrlUserRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
