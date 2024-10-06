package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * システム区分マスタ
 */
public class MG0320EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	public MG0320EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	public boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid
	 */
	public MG0320EditDisplayDialog(Frame parent, boolean modal, MG0320EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(650, 220);
		super.initDialog();
	}

	/**
	 * コンポーネント初期化
	 */
	public void initComponents() {
		GridBagConstraints gridBagConstraints;

		getContentPane().setLayout(new GridBagLayout());

		// ボタンパネル
		pnlButton = new TPanel();
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(600, 40));
		pnlButton.setMinimumSize(new Dimension(600, 40));
		pnlButton.setPreferredSize(new Dimension(600, 40));

		// 確定ボタン
		btnSettle = new TImageButton(IconType.SETTLE);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 370, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				// 開ける種別の設定
				isSettle = true;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});

		// 取消ボタン
		btnReturn = new TButton();
		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 10);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				// 開ける種別の設定
				isSettle = false;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 0, 20);
		getContentPane().add(pnlButton, gridBagConstraints);

		// メインパネル
		pnlDetail = new TPanel();
		pnlDetail.setLayout(new GridBagLayout());
		pnlDetail.setMaximumSize(new Dimension(600, 132));
		pnlDetail.setMinimumSize(new Dimension(600, 132));
		pnlDetail.setPreferredSize(new Dimension(600, 132));

		// システム区分
		ctrlSystemDivision = new TLabelField();
		ctrlSystemDivision.setFieldSize(35);
		ctrlSystemDivision.setLabelSize(130);
		ctrlSystemDivision.setLangMessageID("C00217");
		ctrlSystemDivision.getField().setAllowedSpace(false);
		ctrlSystemDivision.setMaxLength(2);
		ctrlSystemDivision.setTabControlNo(1);
		ctrlSystemDivision.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail.add(ctrlSystemDivision, gridBagConstraints);

		// システム区分名称
		ctrlSystemDivisionName = new TLabelField();
		ctrlSystemDivisionName.setFieldSize(450);
		ctrlSystemDivisionName.setLabelSize(130);
		ctrlSystemDivisionName.setLangMessageID("C00832");
		ctrlSystemDivisionName.setMaxLength(40);
		ctrlSystemDivisionName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlSystemDivisionName, gridBagConstraints);

		// システム区分略称
		ctrlSystemDivisionAbbreviatedName = new TLabelField();
		ctrlSystemDivisionAbbreviatedName.setFieldSize(230);
		ctrlSystemDivisionAbbreviatedName.setLabelSize(130);
		ctrlSystemDivisionAbbreviatedName.setLangMessageID("C00833");
		ctrlSystemDivisionAbbreviatedName.setMaxLength(20);
		ctrlSystemDivisionAbbreviatedName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlSystemDivisionAbbreviatedName, gridBagConstraints);

		// システム区分検索名称
		ctrlSystemDivisionNameForSearch = createForSearchCtrl();
		ctrlSystemDivisionNameForSearch.setFieldSize(230);
		ctrlSystemDivisionNameForSearch.setLabelSize(130);
		ctrlSystemDivisionNameForSearch.setLangMessageID("C00834");
		ctrlSystemDivisionNameForSearch.setMaxLength(20);
		ctrlSystemDivisionNameForSearch.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlSystemDivisionNameForSearch, gridBagConstraints);

		// 外部システム区分
		ctrlOutsideSystemDivision = new TLabelComboBox();
		ctrlOutsideSystemDivision.setComboSize(170);
		ctrlOutsideSystemDivision.setFocusable(false);
		ctrlOutsideSystemDivision.setLabelSize(130);
		ctrlOutsideSystemDivision.setLangMessageID("C01018");
		ctrlOutsideSystemDivision.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlDetail.add(ctrlOutsideSystemDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 10, 10, 20);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	/**
	 * 検索名称フィールド作成
	 * 
	 * @return 検索名称フィールド
	 */
	public TLabelField createForSearchCtrl() {
		return new TLabelField();
	}

	/** ボタンパネル */
	public TPanel pnlButton;

	/** 取消パネル */

	public TPanel pnlDetail;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 取消ボタン */
	public TButton btnReturn;

	/** システム区分 */
	public TLabelField ctrlSystemDivision;

	/** システム区分名称 */
	public TLabelField ctrlSystemDivisionName;

	/** システム区分略称 */
	public TLabelField ctrlSystemDivisionAbbreviatedName;

	/** システム区分検索名称 */
	public TLabelField ctrlSystemDivisionNameForSearch;

	/** 外部システム区分 */
	public TLabelComboBox ctrlOutsideSystemDivision;
}