package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 通貨レートマスタダイアログ
 */
public class MG0310EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0310EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid
	 */
	MG0310EditDisplayDialog(Frame parent, boolean modal, MG0310EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(620, 200);
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		ctrlCurrency = new TButtonField();
		dtOutLineBeginDate = new TLabelPopupCalendar();
		numRate = new TLabelNumericField();

		getContentPane().setLayout(new GridBagLayout());
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(580, 40));
		pnlButton.setMinimumSize(new Dimension(580, 40));
		pnlButton.setPreferredSize(new Dimension(580, 40));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(4);
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 325, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * 確定ボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				// 開ける種別の設定
				isSettle = true;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(5);
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 10);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			/**
			 * 戻るボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				// 開ける種別の設定
				isSettle = false;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 20, 0, 20);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(580, 80));
		pnlDetail.setMinimumSize(new Dimension(580, 80));
		pnlDetail.setPreferredSize(new Dimension(580, 80));
		ctrlCurrency.setButtonSize(85);
		ctrlCurrency.setFieldSize(45);
		ctrlCurrency.setLangMessageID("C01242");
		ctrlCurrency.setMaxLength(3);
		ctrlCurrency.setNoticeSize(410);
		ctrlCurrency.setTabControlNo(1);
		ctrlCurrency.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail.add(ctrlCurrency, gridBagConstraints);

		dtOutLineBeginDate.setLabelSize(85);
		dtOutLineBeginDate.setLangMessageID("C02046");
		dtOutLineBeginDate.setTabControlNo(2);
		dtOutLineBeginDate.setLabelHAlignment(2);
		dtOutLineBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtOutLineBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(dtOutLineBeginDate, gridBagConstraints);

		numRate.setFieldHAlignment(2);
		numRate.setFieldSize(135);
		numRate.setLabelSize(70);
		numRate.getField().setHorizontalAlignment(CellStyleModel.RIGHT);
		numRate.setLangMessageID("C00556");
		numRate.setMaxLength(15);
		numRate.setNumericFormat("##,###,###,##0.0000");
		numRate.setTabControlNo(3);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlDetail.add(numRate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 0, 20, 0);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	TButton btnReturn;

	TButton btnSettle;

	TButtonField ctrlCurrency;

	TLabelPopupCalendar dtOutLineBeginDate;

	TLabelNumericField numRate;

	TPanel pnlButton;

	TPanel pnlDetail;

}
