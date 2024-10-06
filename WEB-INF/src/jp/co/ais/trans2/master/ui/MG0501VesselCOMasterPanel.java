package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 内航外航区分マスタの指示画面
 * 
 * @author AIS
 */
public class MG0501VesselCOMasterPanel extends TMainPanel {

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** エクセルボタン */
	public TImageButton btnExcel;

	/** 内航船ラベル */
	public TLabel lblUpper;

	/** 内航船一覧 */
	public TTable tblUpper;

	/** 外航船ラベル */
	public TLabel lblLower;

	/** 外航船一覧 */
	public TTable tblLower;

	/** ボタンパネル */
	public TPanel pnlBodyButton;

	/** 内航船追加ボタン */
	public TButton btnCoastalAddVessel;

	/** 内航船削除ボタン */
	public TButton btnCoastalDeleteVessel;

	/** 外航船追加ボタン */
	public TButton btnOceanAddVessel;

	/** 外航船削除ボタン */
	public TButton btnOceanDeleteVessel;

	/** 船情報一覧 */
	public TTable tblRight;

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {
		/** 船コード */
		vesselCode,
		/** 船略称 */
		vesselNames
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	public void initComponents() {

		btnSearch = new TImageButton(IconType.SEARCH);
		btnSettle = new TImageButton(IconType.SETTLE);
		btnExcel = new TImageButton(IconType.EXCEL);
		lblUpper = new TLabel();
		tblUpper = new TTable();
		tblUpper.addColumn(SC.vesselCode, getWord("C11758"), 200);// 船コード
		tblUpper.addColumn(SC.vesselNames, getWord("C11759"), 300);// 船略称
		lblLower = new TLabel();
		tblLower = new TTable();
		tblLower.addColumn(SC.vesselCode, getWord("C11758"), 200);// 船コード
		tblLower.addColumn(SC.vesselNames, getWord("C11759"), 300);// 船略称
		pnlBodyButton = new TPanel();
		btnCoastalAddVessel = new TButton();
		btnCoastalDeleteVessel = new TButton();
		btnOceanAddVessel = new TButton();
		btnOceanDeleteVessel = new TButton();
		tblRight = new TTable();
		tblRight.addColumn(SC.vesselCode, getWord("C11758"), 200);// 船コード
		tblRight.addColumn(SC.vesselNames, getWord("C11759"), 300);// 船略称
	}

	/**
	 * コンポーネント配置
	 */
	@Override
	public void allocateComponents() {

		setSize(850, 600);

		// 検索ボタン
		int x = HEADER_LEFT_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 確定ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// エクセルボタン
		x = x + btnSettle.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x + 10, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 内航船ラベル
		lblUpper.setLangMessageID("C11983");
		TGuiUtil.setComponentSize(lblUpper, 550, 60);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 0);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(lblUpper, gc);

		// 内航船一覧
		tblUpper.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		TGuiUtil.setComponentSize(tblUpper, 550, 350);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 0);
		gc.weightx = 0.9d;
		gc.weighty = 0.5d;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(tblUpper, gc);

		// 外航船ラベル
		lblLower.setLangMessageID("C11984");
		TGuiUtil.setComponentSize(lblLower, 550, 60);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 0);
		gc.gridx = 0;
		gc.gridy = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(lblLower, gc);

		// 外航船一覧
		tblLower.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		TGuiUtil.setComponentSize(tblLower, 550, 350);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 0);
		gc.weightx = 0.9d;
		gc.weighty = 0.5d;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(tblLower, gc);

		// ボタンパネル
		pnlBodyButton.setLayout(new GridBagLayout());
		pnlBodyButton.setMaximumSize(new Dimension(100, 820));
		pnlBodyButton.setMinimumSize(new Dimension(100, 820));
		pnlBodyButton.setPreferredSize(new Dimension(100, 820));
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridheight = 4;
		pnlBody.add(pnlBodyButton, gc);

		// 内航船追加ボタン
		btnCoastalAddVessel.setLangMessageID("C10140");// ←
		btnCoastalAddVessel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.insets = new Insets(70, 0, 50, 0);
		pnlBodyButton.add(btnCoastalAddVessel, gc);

		// 内航船削除ボタン
		btnCoastalDeleteVessel.setLangMessageID("C10139");// →
		btnCoastalDeleteVessel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(0, 0, 150, 0);
		pnlBodyButton.add(btnCoastalDeleteVessel, gc);

		// 外航船追加ボタン
		btnOceanAddVessel.setLangMessageID("C10140");// ←
		btnOceanAddVessel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 2;
		gc.insets = new Insets(160, 0, 0, 0);
		pnlBodyButton.add(btnOceanAddVessel, gc);

		// 外航船削除ボタン
		btnOceanDeleteVessel.setLangMessageID("C10139");// →
		btnOceanDeleteVessel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 3;
		gc.insets = new Insets(50, 0, 0, 0);
		pnlBodyButton.add(btnOceanDeleteVessel, gc);

		// 船一覧
		tblRight.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		TGuiUtil.setComponentSize(tblRight, 550, 770);
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.gridx = 2;
		gc.gridy = 0;
		gc.gridheight = 4;
		gc.insets = new Insets(60, 0, 0, 30);
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(tblRight, gc);
	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		tblUpper.setTabControlNo(i++);
		tblLower.setTabControlNo(i++);
		btnCoastalAddVessel.setTabControlNo(i++);
		btnCoastalDeleteVessel.setTabControlNo(i++);
		btnOceanAddVessel.setTabControlNo(i++);
		btnOceanDeleteVessel.setTabControlNo(i++);
		tblRight.setTabControlNo(i++);

		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}