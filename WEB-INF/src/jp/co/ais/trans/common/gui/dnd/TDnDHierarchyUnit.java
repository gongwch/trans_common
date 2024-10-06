package jp.co.ais.trans.common.gui.dnd;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 階層データ作成基盤コンポーンネント
 */
public class TDnDHierarchyUnit extends TPanel {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** スプレッドシートDataSource */
	JCVectorDataSource ds;

	/** 階層データのルートデータ */
	public DnDData baseDndData = null;

	/** ベースパネル */
	protected TPanel pnlBase;

	/** 一覧panel */
	protected TPanel pnlJournal;

	/** 階層panel */
	protected JScrollPane pnlTree;

	/** 一覧 */
	protected TDnDTable ssJournal;

	/** 階層データ */
	protected TDnDTree tree;

	/** 階層データ作成基盤コンポーンネントのCTRL */
	private TDnDHierarchyUnitCtrl ctrl;

	/**
	 * コンストラクタ
	 * 
	 * @param dndData 階層データ
	 */
	public TDnDHierarchyUnit(DnDData dndData) {
		this.baseDndData = dndData;

		ctrl = new TDnDHierarchyUnitCtrl(this);

		// 画面構築
		initComponents();

		// スプレッドシート設定
		initSpreadSheet();

	}

	/**
	 * コンストラクタ
	 */
	public TDnDHierarchyUnit() {
		ctrl = new TDnDHierarchyUnitCtrl(this);

		// 画面構築
		initComponents();

		// スプレッドシート設定
		initSpreadSheet();
	}

	/**
	 * TTreeの機能拡張クラス
	 * 
	 * @return TDnDTree
	 */
	public TDnDTree createTDnDTree() {
		if (baseDndData == null) {
			return new TDnDTree();
		} else {
			return new TDnDTree(baseDndData);
		}
	}

	/**
	 * TTableの機能拡張クラス
	 * 
	 * @return TTable
	 */
	public TDnDTable createTDnDTable() {
		return new TDnDTable();
	}

	/**
	 * 画面構築
	 */
	public void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		pnlJournal = new TPanel();
		pnlTree = new JScrollPane();

		// TTableの機能拡張クラス
		ssJournal = createTDnDTable();

		// TTreeの機能拡張クラス
		tree = createTDnDTree();

		this.setMaximumSize(new java.awt.Dimension(600, 400));
		this.setMinimumSize(new java.awt.Dimension(600, 400));
		this.setPreferredSize(new java.awt.Dimension(600, 400));
		setLayout(new GridBagLayout());

		pnlBase.setMaximumSize(new java.awt.Dimension(600, 400));
		pnlBase.setMinimumSize(new java.awt.Dimension(600, 400));
		pnlBase.setPreferredSize(new java.awt.Dimension(600, 400));
		pnlBase.setLayout(new java.awt.GridBagLayout());

		pnlJournal.setMaximumSize(new java.awt.Dimension(274, 400));
		pnlJournal.setMinimumSize(new java.awt.Dimension(274, 400));
		pnlJournal.setPreferredSize(new java.awt.Dimension(274, 400));
		pnlJournal.setLayout(new BoxLayout(pnlJournal, BoxLayout.X_AXIS));
		pnlJournal.add(ssJournal);

		ssJournal.setTabControlNo(1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		pnlBase.add(pnlJournal, gridBagConstraints);

		pnlTree.setMaximumSize(new java.awt.Dimension(270, 400));
		pnlTree.setMinimumSize(new java.awt.Dimension(270, 400));
		pnlTree.setPreferredSize(new java.awt.Dimension(270, 400));
		pnlTree.getViewport().add(tree, null);

		tree.setTabControlNo(2);
		tree.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					deleteTreeData();
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				}
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 46, 0, 0);
		pnlBase.add(pnlTree, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		add(pnlBase, gridBagConstraints);
	}

	/**
	 * 一覧のカラム名称を設定する
	 * 
	 * @param colNames カラム名称
	 */
	public void setColumnNames(String[] colNames) {
		// ctrl.setColumnNames(colNames);
		ssJournal.setColumnTitleMessageID(colNames);
		ssJournal.setColumnLabels(colNames);
	}

	/**
	 * 一覧のカラム幅を設定する
	 * 
	 * @param colWidths カラム幅
	 */
	public void setColumnWidths(int[] colWidths) {
		ssJournal.setColumnWidths(colWidths);

	}

	/**
	 * 一覧のカラム位置を中央揃えにする
	 * 
	 * @param col カラムインデックス
	 */
	public void setCenterColumn(int col) {
		this.setCellStyle(col, CellStyleModel.CENTER);
	}

	/**
	 * 一覧のカラム位置を右寄せにする
	 * 
	 * @param col カラムインデックス
	 */
	public void setRightColumn(int col) {
		this.setCellStyle(col, CellStyleModel.RIGHT);
	}

	/**
	 * 一覧のカラム位置を設定する
	 * 
	 * @param col カラムインデックス
	 * @param colStyle カラム位置
	 */
	private void setCellStyle(int col, int colStyle) {
		CellStyleModel cellStyle = (CellStyleModel) ssJournal.getDefaultCellStyle().clone();
		cellStyle.setHorizontalAlignment(colStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, col, cellStyle);
	}

	/**
	 * 一覧にデータを反映する
	 * 
	 * @param cells 一覧データ
	 */
	public void setDataList(Vector<Vector> cells) {
		ssJournal.setDataList(cells);
	}

	/**
	 * 階層データを設定する
	 * 
	 * @param listDnDData 階層データ
	 */
	public void setTreeData(List<DnDData> listDnDData) {
		ctrl.setTreeData(listDnDData);
	}

	/**
	 * 階層データを取得する
	 * 
	 * @return 階層データ
	 */
	public List<DnDData> getTreeData() {
		return ctrl.getTreeData();
	}

	/**
	 * ルートノードを含めない階層データを取得する
	 * 
	 * @return 階層データ
	 */
	public List<DnDData> getTreeDataNoRoot() {
		return ctrl.getTreeDataNoRoot();
	}

	/**
	 * 階層データを削除する <br>
	 * DELETEキー 押下時の処理。階層データから選択ノードを削除する。
	 * 
	 * @return 階層データから選択ノード(For Override)
	 */
	public List<DnDData> deleteTreeData() {
		return ctrl.doDeleteTreeData();
	}

	/**
	 * スプレッドシート設定
	 */
	public void initSpreadSheet() {

		// "" , コード , 名称
		String[] columnLabelMessageIDs = new String[] { "", "C00174", "C00518" };

		// セル幅
		int[] columnWidths = new int[3];
		columnWidths[0] = 0;
		columnWidths[1] = 7;
		columnWidths[2] = 11;

		ssJournal.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// 複数行選択かどうかの設定
		ssJournal.setSelectMaltiLine(true);
		ssJournal.setSelectMultiRange(true);

		ssJournal.setCharWidth(-1, 2);

		ssJournal.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		setDataList(new Vector<Vector>());
	}

	/**
	 * タブ移動順番号
	 * 
	 * @param no
	 */
	public void setTabControlNo(int no) {
		ssJournal.setTabControlNo(no);
		tree.setTabControlNo(no);
	}

	/**
	 * 一覧
	 * 
	 * @return 一覧
	 */
	public TDnDTable getSsJournal() {
		return ssJournal;
	}

	/**
	 * 一覧
	 * 
	 * @param ssJournal 一覧設定する
	 */
	public void setSsJournal(TDnDTable ssJournal) {
		this.ssJournal = ssJournal;
	}

	/**
	 * 階層
	 * 
	 * @return 階層
	 */
	public TDnDTree getTree() {
		return tree;
	}

	/**
	 * 階層
	 * 
	 * @param tree 階層設定する
	 */
	public void setTree(TDnDTree tree) {
		this.tree = tree;
	}

}
