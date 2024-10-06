package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.basic.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 実行ログ参照パネル
 * 
 * @author roh
 */
public class MG0028ExecutedLogPanel extends TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 6864139718117925182L;

	/** コントロール */
	private MG0028ExecutedLogPanelCtrl ctrl;

	/** ボタンパネル */
	public TMainHeaderPanel pnlButton;

	/** 詳細パネル */
	public TPanel pnlDetail;

	/** ユーザ検索パネル */
	public TPanel pnlUser;

	/** プログラム検索パネル */
	public TPanel pnlProgram;

	/** 日時パネル */
	public TPanel pnlDate;

	/** テーブル用パネル */
	public TPanel pnlTabel;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** エクセル出力ボタン */
	public TImageButton btnExcel;

	/** ユーザ用ラベル */
	public TLabel lblStart;

	/** ユーザ用ラベル */
	public TLabel lblEnd;

	/** プログラム用ラベル */
	public TLabel lblProStart;

	/** プログラム用ラベル */
	public TLabel lblProEnd;

	/** ログイン参照チェックボックス */
	public TCheckBox checkProgram;

	/** 検索開始日フィールド */
	public TLabelPopupCalendar fedStartDate;

	/** 検索終了日フィールド */
	public TLabelPopupCalendar fedEndDate;

	/** 開始ユーザ検索フィールド */
	public TButtonField fedStartUser;

	/** 終了ユーザ検索フィールド */
	public TButtonField fedEndUser;

	/** 開始プログラム検索フィールド */
	public TButtonField fedStartProgram;

	/** 終了プログラム検索フィールド */
	public TButtonField fedEndProgram;

	/** 並び順コンボボックス */
	public TLabelComboBox sortCombo;

	/** 実行ログテーブル */
	public TTable tblLog;

	/**
	 * コンストラクタ
	 * 
	 * @param ctrl 画面コントロール
	 */
	public MG0028ExecutedLogPanel(MG0028ExecutedLogPanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
		super.initPanel();
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;
		pnlButton = new TMainHeaderPanel();
		pnlDetail = new TPanel();
		pnlUser = new TPanel();
		pnlProgram = new TPanel();
		pnlDate = new TPanel();
		pnlTabel = new TPanel();
		btnSearch = new TImageButton(IconType.SEARCH);
		btnExcel = new TImageButton(IconType.EXCEL);
		checkProgram = new TCheckBox();
		fedStartDate = new TLabelPopupCalendar();
		fedEndDate = new TLabelPopupCalendar();
		fedStartUser = new TButtonField();
		fedEndUser = new TButtonField();
		fedStartProgram = new TButtonField();
		fedEndProgram = new TButtonField();
		tblLog = new TTable();
		lblEnd = new TLabel();
		sortCombo = new TLabelComboBox();
		lblStart = new TLabel();
		lblProEnd = new TLabel();
		lblProStart = new TLabel();

		// 基本レイアウト
		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		// ボタンパネルレイアウト
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(800, 45));
		pnlButton.setMinimumSize(new Dimension(800, 45));
		pnlButton.setPreferredSize(new Dimension(800, 45));

		// 検索ボタン
		btnSearch.setLangMessageID("C00155");
		btnSearch.setTabControlNo(9);
		btnSearch.setShortcutKey(KeyEvent.VK_F1);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 410);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlButton.add(btnSearch, gridBagConstraints);
		btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.searchLog();
			}
		});

		// エクセルボタン
		btnExcel.setLangMessageID("C01545");
		btnExcel.setTabControlNo(10);
		btnExcel.setShortcutKey(KeyEvent.VK_F9);
		btnExcel.setMaximumSize(new Dimension(130, 25));
		btnExcel.setMinimumSize(new Dimension(130, 25));
		btnExcel.setPreferredSize(new Dimension(130, 25));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(0, 92, 0, 0);
		pnlButton.add(btnExcel, gridBagConstraints);
		btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.exportToExcel();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		// 日付パネルレイアウト
		pnlDate.setLayout(new GridBagLayout());
		pnlDate.setMaximumSize(new Dimension(800, 22));
		pnlDate.setMinimumSize(new Dimension(800, 22));
		pnlDate.setPreferredSize(new Dimension(800, 22));

		// 検索開始フィールド
		fedStartDate.setLabelSize(61);
		fedStartDate.setPreferredSize(new Dimension(160, 20));
		fedStartDate.setTabControlNo(1);
		fedStartDate.setAllowableBlank(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 19, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDate.add(fedStartDate, gridBagConstraints);

		// 検索終了フィールド
		fedEndDate.setLangMessageID("C01333");
		fedEndDate.setLabelSize(20);
		fedEndDate.setPreferredSize(new Dimension(160, 20));
		fedEndDate.setTabControlNo(2);
		fedEndDate.setAllowableBlank(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 7, 0, 460);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDate.add(fedEndDate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 5, 0, 0);
		add(pnlDate, gridBagConstraints);

		// 詳細パネルレイアウト
		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(780, 120));
		pnlDetail.setMinimumSize(new Dimension(780, 120));
		pnlDetail.setPreferredSize(new Dimension(780, 120));

		// ユーザパネル
		pnlUser.setLayout(new GridBagLayout());

		pnlUser.setMaximumSize(new Dimension(350, 80));
		pnlUser.setMinimumSize(new Dimension(350, 80));
		pnlUser.setPreferredSize(new Dimension(350, 80));

		// ユーザ検索開始ラベル
		lblStart.setLangMessageID("C01012");
		lblStart.setMaximumSize(new Dimension(60, 20));
		lblStart.setPreferredSize(new Dimension(60, 20));
		lblStart.setMinimumSize(new Dimension(60, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlUser.add(lblStart, gridBagConstraints);

		// ユーザ検索開始フィールド
		fedStartUser.setLangMessageID("C00528");
		fedStartUser.setButtonSize(70);
		fedStartUser.setFieldSize(60);
		fedStartUser.setNoticeSize(110);
		fedStartUser.setMaxLength(20);
		fedStartUser.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlUser.add(fedStartUser, gridBagConstraints);

		// ユーザ検索終了ラベル
		lblEnd.setLangMessageID("C01143");
		lblEnd.setMaximumSize(new Dimension(60, 20));
		lblEnd.setPreferredSize(new Dimension(60, 20));
		lblEnd.setMinimumSize(new Dimension(60, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlUser.add(lblEnd, gridBagConstraints);

		// ユーザ検索終了フィールド
		fedEndUser.setLangMessageID("C00528");
		fedEndUser.setButtonSize(70);
		fedEndUser.setFieldSize(60);
		fedEndUser.setNoticeSize(110);
		fedEndUser.setMaxLength(20);
		fedEndUser.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlUser.add(fedEndUser, gridBagConstraints);

		pnlUser.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 5, 0);
		pnlDetail.add(pnlUser, gridBagConstraints);

		// プログラムパネル
		pnlProgram.setLayout(new GridBagLayout());
		pnlProgram.setMaximumSize(new Dimension(360, 110));
		pnlProgram.setMinimumSize(new Dimension(360, 110));
		pnlProgram.setPreferredSize(new Dimension(360, 110));

		// ログインプログラムチェックボックス
		checkProgram.setLangMessageID("C02910");
		checkProgram.setMaximumSize(new Dimension(200, 20));
		checkProgram.setPreferredSize(new Dimension(200, 20));
		checkProgram.setMinimumSize(new Dimension(200, 20));
		checkProgram.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlProgram.add(checkProgram, gridBagConstraints);

		// プログラム開始ラベル
		lblProStart.setLangMessageID("C01012");
		lblProStart.setMaximumSize(new Dimension(60, 20));
		lblProStart.setPreferredSize(new Dimension(60, 20));
		lblProStart.setMinimumSize(new Dimension(60, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlProgram.add(lblProStart, gridBagConstraints);

		// プログラム開始フィールド
		fedStartProgram.setLangMessageID("C00477");
		fedStartProgram.setButtonSize(80);
		fedStartProgram.setFieldSize(60);
		fedStartProgram.setNoticeSize(120);
		fedStartProgram.setMaxLength(10);
		fedStartProgram.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlProgram.add(fedStartProgram, gridBagConstraints);

		// プログラム終了ラベル
		lblProEnd.setLangMessageID("C01143");
		lblProEnd.setMaximumSize(new Dimension(60, 20));
		lblProEnd.setPreferredSize(new Dimension(60, 20));
		lblProEnd.setMinimumSize(new Dimension(60, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlProgram.add(lblProEnd, gridBagConstraints);

		// プログラム終了フィールド
		fedEndProgram.setLangMessageID("C00477");
		fedEndProgram.setButtonSize(80);
		fedEndProgram.setFieldSize(60);
		fedEndProgram.setNoticeSize(120);
		fedEndProgram.setMaxLength(10);
		fedEndProgram.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlProgram.add(fedEndProgram, gridBagConstraints);

		pnlProgram.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		pnlDetail.add(pnlProgram, gridBagConstraints);

		// sortコンボボックス
		sortCombo.setLangMessageID("C02839");
		sortCombo.setLabelSize(100);
		sortCombo.setComboSize(110);
		sortCombo.setPreferredSize(new Dimension(210, 20));
		sortCombo.setMaximumSize(new Dimension(220, 20));
		sortCombo.setMinimumSize(new Dimension(220, 20));
		sortCombo.setTabControlNo(8);
		sortCombo.getLabel().setHorizontalTextPosition(SwingConstants.CENTER);
		((BasicComboBoxRenderer) sortCombo.getComboBox().getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(3, 0, 8, 170);
		pnlDetail.add(sortCombo, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 20, 5, 5);
		add(pnlDetail, gridBagConstraints);

		// スプレッド
		String logdate = getWord("C00218") + getWord("C02906"); // 実行日時
		String userCode = getWord("C00218") + getWord("C00589");
		String userName = getWord("C00218") + getWord("C00691");

		// SpreadSheet を init する
		String[] columnIDs = new String[] { logdate, userCode, userName, "C02907", "C00818", "C00819", "C02908" };
		int[] columnWidths = new int[] { 12, 10, 10, 8, 9, 10, 8 };

		// 列表題
		tblLog.initSpreadSheet(columnIDs, columnWidths);

		// Scroll位置設定
		tblLog.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		tblLog.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// スタイル
		CellStyleModel defaultStyle = tblLog.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		tblLog.setCellStyle(JCTableEnum.ALLCELLS, 0, centerStyle);

		// テーブルパネル
		pnlTabel.setLayout(new BoxLayout(pnlTabel, BoxLayout.X_AXIS));
		pnlTabel.setMaximumSize(new Dimension(800, 435));
		pnlTabel.setMinimumSize(new Dimension(800, 435));
		pnlTabel.setPreferredSize(new Dimension(800, 435));
		pnlTabel.add(tblLog);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlTabel, gridBagConstraints);
	}
}
