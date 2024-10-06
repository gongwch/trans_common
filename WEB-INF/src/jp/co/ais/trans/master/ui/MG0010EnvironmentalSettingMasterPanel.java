package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 環境設定マスタパネル
 */
public class MG0010EnvironmentalSettingMasterPanel extends TPanelBusiness {

	/** コントロールクラス */
	protected MG0010EnvironmentalSettingMasterPanelCtrl ctrl;

	/** スプレッドシートDataSource */
	protected JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	public MG0010EnvironmentalSettingMasterPanel(MG0010EnvironmentalSettingMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
		initSpreadSheet();

		super.initPanel();

	}

	protected void initSpreadSheet() {
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00685", "C00686", "C01150", "C01151", "C01152",
				"C00527", "C00393", "C00690", "C00055", "C00261" };
		int[] columnWidths = new int[] { 6, 20, 20, 30, 30, 50, 8, 8, 8, 6, 6, 0, 0, 0, 0, 0, 0 };

		// 列、行表題のスタイル設定
		ssEnvironmentalSettingList.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// スプレッドイベントの初期化
		ssEnvironmentalSettingList.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssEnvironmentalSettingList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssEnvironmentalSettingList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(11);
		ds.setNumRows(0);

		ssEnvironmentalSettingList.setDataSource(ds);

	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {

		// 数値を右寄せする
		CellStyleModel defaultStyle = ssEnvironmentalSettingList.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssEnvironmentalSettingList.setCellStyle(JCTableEnum.ALLCELLS, 9, centerStyle);
		ssEnvironmentalSettingList.setCellStyle(JCTableEnum.ALLCELLS, 10, centerStyle);

		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssEnvironmentalSettingList.setDataSource(ds);
	}

	/** シリアルUID */
	private static final long serialVersionUID = -9107617482788521697L;

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnNew = new TButton();
		btnEdit = new TButton();
		btnCopy = new TButton();
		btnDelete = new TButton();
		pnlDetail = new TPanel();
		ssEnvironmentalSettingList = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);
		btnNew.addActionListener(new ActionListener() {

			// 新規ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.insert();
			}
		});

		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);
		btnEdit.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.update();
			}
		});

		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);
		btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.copy();
			}
		});

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);
		btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new BoxLayout(pnlDetail, BoxLayout.X_AXIS));

		pnlDetail.setMaximumSize(new Dimension(780, 545));
		pnlDetail.setMinimumSize(new Dimension(780, 545));
		pnlDetail.setPreferredSize(new Dimension(780, 545));
		pnlDetail.add(ssEnvironmentalSettingList);
		ssEnvironmentalSettingList.setTabControlNo(1);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

	}

	TButton btnCopy;

	TButton btnDelete;

	TButton btnEdit;

	TButton btnNew;

	TPanel pnlButton;

	TPanel pnlDetail;

	TTable ssEnvironmentalSettingList;

}
