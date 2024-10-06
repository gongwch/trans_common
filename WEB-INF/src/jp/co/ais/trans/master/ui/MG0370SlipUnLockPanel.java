package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;

/**
 * 伝票排他管理マスタパネル
 * 
 * @author ais 盧
 */
public class MG0370SlipUnLockPanel extends TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 1285281438447797312L;

	/** コントロール */
	private MG0370SlipUnLockPanelCtrl ctrl;

	/** 削除ボタン */
	public TButton btnSettle;

	/** 上位ボタン配置パネル */
	public TMainHeaderPanel pnlButton;

	/** フッタパネル */
	public TPanel pnlFooter;

	/** 表示テーブル */
	public TTable lockTable;

	/** 詳細フィールド配置パネル */
	public TPanel pnlJournal;

	/**
	 * コンストラクタ
	 * 
	 * @param ctrl 画面コントロール
	 */
	public MG0370SlipUnLockPanel(MG0370SlipUnLockPanelCtrl ctrl) {
		this.ctrl = ctrl;

		initComponents();
		initSpreadSheet();

		super.initPanel();
	}

	/** 画面 構築 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnSettle = new TButton();
		pnlFooter = new TPanel();
		lockTable = new TTable();
		pnlJournal = new TPanel();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlButton, new Dimension(800, 40));

		btnSettle.setLangMessageID("C00056");
		btnSettle.setShortcutKey(KeyEvent.VK_F7);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 320, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.deleteDto();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		// テーブル設定
		lockTable.setTabControlNo(1);

		// Scroll位置設定
		lockTable.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		lockTable.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		pnlJournal.setLayout(new BoxLayout(pnlJournal, BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(780, 460));
		pnlJournal.setMinimumSize(new Dimension(780, 460));
		pnlJournal.setPreferredSize(new Dimension(780, 460));
		pnlJournal.add(lockTable);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(10, 20, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlJournal, gridBagConstraints);

		// フッタパネル設定
		pnlFooter.setLayout(new GridBagLayout());

		pnlFooter.setMaximumSize(new Dimension(800, 50));
		pnlFooter.setMinimumSize(new Dimension(800, 50));
		pnlFooter.setPreferredSize(new Dimension(800, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlFooter, gridBagConstraints);
	}

	/** スプレッド構成 */
	private void initSpreadSheet() {
		String[] slipTypelabel = new String[] { "", "C00217", "C00605", "C00917",
				ctrl.getLinkedWord("C00390", "C02906"), "C00569", "C03423", "C03424", "C03425" };
		int[] columnWidths = new int[] { 3, 8, 8, 8, 10, 10, 6, 8, 10 };

		// 列、行表題のスタイル設定
		lockTable.initSpreadSheet(slipTypelabel, columnWidths);

		lockTable.setFrozenColumnPlacement(JCTableEnum.PLACE_LEFT);
		lockTable.setFrozenColumns(0);
		lockTable.setFrozenColumns(1);

		CellStyleModel defaultStyle = lockTable.getDefaultCellStyle();

		JCCellStyle leftStyle = new JCCellStyle(defaultStyle);
		leftStyle.setHorizontalAlignment(CellStyleModel.LEFT);

		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 1, leftStyle);
		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 2, leftStyle);
		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 3, leftStyle);
		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 5, leftStyle);
		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 6, leftStyle);
		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 7, leftStyle);

		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);

		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 4, centerStyle);
		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
	}
}
