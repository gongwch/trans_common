package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * @author zhangzhenxing
 */
public class MG0120EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0120EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleID
	 */
	MG0120EditDisplayDialog(Frame parent, boolean modal, MG0120EditDisplayDialogCtrl ctrl, String titleID) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleID);
		// 画面の設定
		setSize(680, 400);
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpMemoDivision = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		pnlMemoDivision = new TPanel();
		rdoSlipMemo = new TRadioButton();
		rdoRowMemo = new TRadioButton();
		ctrlDataDivision = new TLabelComboBox();
		ctrlMemoCode = new TLabelField();
		ctrlMemoName = new TLabelField();
		ctrlMemoNameForSearch = createForSearchCtrl();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();

		getContentPane().setLayout(new GridBagLayout());
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(650, 40));
		pnlButton.setMinimumSize(new Dimension(650, 40));
		pnlButton.setPreferredSize(new Dimension(650, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(8);
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * 確定ボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 320, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(9);
		btnReturn.setForClose(true);
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.addActionListener(new ActionListener() {

			/**
			 * 戻りボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(650, 230));
		pnlDetail.setMinimumSize(new Dimension(650, 230));
		pnlDetail.setPreferredSize(new Dimension(650, 230));
		pnlMemoDivision.setLayout(new GridBagLayout());

		pnlMemoDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		pnlMemoDivision.setLangMessageID("C00568");
		pnlMemoDivision.setMaximumSize(new Dimension(250, 50));
		pnlMemoDivision.setMinimumSize(new Dimension(250, 50));
		pnlMemoDivision.setPreferredSize(new Dimension(250, 50));
		rdoSlipMemo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpMemoDivision.add(rdoSlipMemo);
		rdoSlipMemo.setSelected(true);

		// 伝票摘要
		TGuiUtil.setComponentSize(rdoSlipMemo, 120, 15);
		rdoSlipMemo.setLangMessageID("C00569");
		rdoSlipMemo.setMargin(new Insets(0, 0, 0, 0));
		rdoSlipMemo.setTabControlNo(1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 20, 5, 0);
		pnlMemoDivision.add(rdoSlipMemo, gridBagConstraints);

		rdoRowMemo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpMemoDivision.add(rdoRowMemo);

		// 行摘要
		TGuiUtil.setComponentSize(rdoRowMemo, 120, 15);
		rdoRowMemo.setLangMessageID("C00119");
		rdoRowMemo.setMargin(new Insets(0, 0, 0, 0));
		rdoRowMemo.setTabControlNo(1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, -10, 5, 0);
		pnlMemoDivision.add(rdoRowMemo, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 25, 0, 247);
		pnlDetail.add(pnlMemoDivision, gridBagConstraints);

		// データ区分
		ctrlDataDivision.setLabelSize(145);
		ctrlDataDivision.setComboSize(190);
		ctrlDataDivision.setLangMessageID("C00567");
		ctrlDataDivision.setTabControlNo(2);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlDataDivision, gridBagConstraints);

		// 摘要コード
		ctrlMemoCode.setFieldSize(120);
		ctrlMemoCode.setLabelSize(145);
		ctrlMemoCode.setLangMessageID("C00564");
		ctrlMemoCode.getField().setAllowedSpace(false);
		ctrlMemoCode.setMaxLength(10);
		ctrlMemoCode.setTabControlNo(3);
		ctrlMemoCode.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlMemoCode, gridBagConstraints);

		ctrlMemoName.setFieldSize(500);
		ctrlMemoName.setLabelSize(145);
		ctrlMemoName.setLangMessageID("C00565");
		ctrlMemoName.setTabControlNo(4);
		ctrlMemoName.setMaxLength(80);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlMemoName, gridBagConstraints);

		ctrlMemoNameForSearch.setFieldSize(450);
		ctrlMemoNameForSearch.setLabelSize(145);
		ctrlMemoNameForSearch.setLangMessageID("C00566");
		ctrlMemoNameForSearch.setMaxLength(40);
		ctrlMemoNameForSearch.setTabControlNo(5);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlMemoNameForSearch, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(145);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(6);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(145);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(7);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 30, 200);
		pnlDetail.add(dtEndDate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	/**
	 * 検索名称フィールド作成
	 * 
	 * @return 検索名称フィールド
	 */
	protected TLabelField createForSearchCtrl() {
		return new TLabelField();
	}

	TButton btnReturn;

	TImageButton btnSettle;

	ButtonGroup btngrpMemoDivision;

	TLabelComboBox ctrlDataDivision;

	TLabelField ctrlMemoCode;

	TLabelField ctrlMemoName;

	TLabelField ctrlMemoNameForSearch;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlMemoDivision;

	TRadioButton rdoRowMemo;

	TRadioButton rdoSlipMemo;

}
