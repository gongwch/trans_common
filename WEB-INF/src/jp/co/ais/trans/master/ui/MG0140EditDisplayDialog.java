package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * @author zhangzhenxing
 */
public class MG0140EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0140EditDisplayDialogCtrl ctrl;

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
	MG0140EditDisplayDialog(Frame parent, boolean modal, MG0140EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		ctrlBankCode = new TLabelField();
		ctrlBankBranchCode = new TLabelField();
		ctrlBankBranchKanaName = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlBankKanaName = new TLabelField();
		ctrlBankName = new TLabelField();
		ctrlBankNameForSearch = new TLabelField();
		ctrlBankBranchName = new TLabelField();
		ctrlBankBranchNameForSearch = new TLabelField();

		getContentPane().setLayout(new GridBagLayout());

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(460, 40));
		pnlButton.setMinimumSize(new Dimension(460, 40));
		pnlButton.setPreferredSize(new Dimension(460, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(11);
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
		gridBagConstraints.insets = new Insets(10, 220, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(12);
		btnReturn.setForClose(true);
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

		pnlDetail.setMaximumSize(new Dimension(460, 270));
		pnlDetail.setMinimumSize(new Dimension(460, 270));
		pnlDetail.setPreferredSize(new Dimension(460, 270));
		ctrlBankCode.setDoubleBuffered(false);
		ctrlBankCode.setFieldSize(55);
		ctrlBankCode.setLabelSize(110);
		ctrlBankCode.setLangMessageID("C00779");
		ctrlBankCode.getField().setAllowedSpace(false);
		ctrlBankCode.setMaxLength(4);
		ctrlBankCode.setTabControlNo(1);
		ctrlBankCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlBankCode, gridBagConstraints);

		ctrlBankBranchCode.setFieldSize(45);
		ctrlBankBranchCode.setLabelSize(110);
		ctrlBankBranchCode.setLangMessageID("C00780");
		ctrlBankBranchCode.getField().setAllowedSpace(false);
		ctrlBankBranchCode.setMaxLength(3);
		ctrlBankBranchCode.setTabControlNo(2);
		ctrlBankBranchCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlBankBranchCode, gridBagConstraints);

		ctrlBankBranchKanaName.setFieldSize(175);
		ctrlBankBranchKanaName.setLabelSize(110);
		ctrlBankBranchKanaName.setLangMessageID("C00784");
		ctrlBankBranchKanaName.setMaxLength(15);
		ctrlBankBranchKanaName.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlBankBranchKanaName, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(9);

		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(110);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(10);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 20, 0);
		pnlDetail.add(dtEndDate, gridBagConstraints);

		ctrlBankKanaName.setFieldSize(175);
		ctrlBankKanaName.setLabelSize(110);
		ctrlBankKanaName.setLangMessageID("C00782");
		ctrlBankKanaName.setMaxLength(15);
		ctrlBankKanaName.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlBankKanaName, gridBagConstraints);

		ctrlBankName.setFieldSize(340);
		ctrlBankName.setLabelSize(110);
		ctrlBankName.setLangMessageID("C00781");
		ctrlBankName.setMaxLength(30);
		ctrlBankName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlBankName, gridBagConstraints);

		ctrlBankNameForSearch.setFieldSize(340);
		ctrlBankNameForSearch.setLabelSize(110);
		ctrlBankNameForSearch.setLangMessageID("C00829");
		ctrlBankNameForSearch.setMaxLength(30);
		ctrlBankNameForSearch.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlBankNameForSearch, gridBagConstraints);

		ctrlBankBranchName.setFieldSize(340);
		ctrlBankBranchName.setLabelSize(110);
		ctrlBankBranchName.setLangMessageID("C00783");
		ctrlBankBranchName.setMaxLength(30);
		ctrlBankBranchName.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlBankBranchName, gridBagConstraints);

		ctrlBankBranchNameForSearch.setFieldSize(340);
		ctrlBankBranchNameForSearch.setLabelSize(110);
		ctrlBankBranchNameForSearch.setLangMessageID("C00785");
		ctrlBankBranchNameForSearch.setMaxLength(30);
		ctrlBankBranchNameForSearch.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlBankBranchNameForSearch, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	TButton btnReturn;

	TImageButton btnSettle;

	TLabelField ctrlBankBranchCode;

	TLabelField ctrlBankBranchKanaName;

	TLabelField ctrlBankBranchName;

	TLabelField ctrlBankBranchNameForSearch;

	TLabelField ctrlBankCode;

	TLabelField ctrlBankKanaName;

	TLabelField ctrlBankName;

	TLabelField ctrlBankNameForSearch;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TPanel pnlButton;

	TPanel pnlDetail;

}
