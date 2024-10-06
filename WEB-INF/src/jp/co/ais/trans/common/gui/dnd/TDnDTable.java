package jp.co.ais.trans.common.gui.dnd;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTableの機能拡張クラス。<BR>
 * ドラッグ＆ドロップ操作を行なうためのメソッドを追加する。
 * 
 * @author Yanwei
 */
public class TDnDTable extends TTable implements DropTargetListener, DragGestureListener, DragSourceListener {

	/** ドラッグ用 */
	private boolean dragFlg = false;

	/**
	 * コンストラクタ ドロップ用の設定を行なう。
	 */
	public TDnDTable() {
		// ②dropの対象になる コンポーネントを取得する。
		Component tableArea = super.getCellAreaHandler().getTable();
		// ③dropを実現するためのメソッドを呼ぶ。
		if (tableArea != null) {
			new DropTarget(tableArea, DnDConstants.ACTION_COPY_OR_MOVE, this);
		}
	}

	/**
	 * ドラッグ用の設定を行なう。
	 */
	@Override
	public void repaint() {
		super.repaint();

		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();
		if (ds != null && ds.getNumRows() == 0) {
			dragFlg = false;
		}
		if (dragFlg) {
			return;
		}
		// ②dragの対象になる コンポーネントを取得する。
		Component cellArea = super.getCellAreaHandler().getCellArea();
		// ③dragを実現するためのメソッドを呼ぶ。
		if (cellArea != null) {
			new DragSource().createDefaultDragGestureRecognizer(cellArea, DnDConstants.ACTION_COPY_OR_MOVE, this);
			dragFlg = true;
		}
	}

	/**
	 * ドラッグ開始時に呼ばれるメソッド ドラッグしたデータを転送する。
	 */
	public void dragGestureRecognized(DragGestureEvent e) {
		InputEvent inputEvent = e.getTriggerEvent();
		try {
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((e.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					// ①一覧.選択行のデータを取得。複数行選択されている場合は全て取得する。
					List<DnDData> listDnDData = new ArrayList<DnDData>();

					// 画面に選択されたデータ
					for (int nomRow = 0; nomRow < this.getNumRows(); nomRow++) {
						if (this.isSelected(nomRow, 0)) {
							listDnDData.add(getRowData(nomRow));// ② 取得したデータをListにする。
						}
					}
					// 選択データがない場合はドラッグしない
					if (listDnDData == null || listDnDData.size() == 0) {
						return;
					}

					// ③ ②.Listを引数にTTransferableクラスをインスタンス化する
					TTransferable transfer = new TTransferable(listDnDData);

					// ④ドラッグを開始する
					e.startDrag(DragSource.DefaultCopyDrop, transfer, this);
				}
			}
		} catch (InvalidDnDOperationException ioe) {
			// 処理なし
		}
	}

	/**
	 * ドロップ時に呼ばれるメソッド 転送データを設定する。
	 */
	public void drop(DropTargetDropEvent e) {
		// 転送をチェック
		if (e.isDataFlavorSupported(TTransferable.nodeFavor)) {

			// ①転送データ：List<DnDData>を取得する。
			List<DnDData> list = TTransferable.getDnDDataList(e.getTransferable());

			// ② 転送データが存在しない場合は処理終了
			if (list == null || list.isEmpty()) {
				e.dropComplete(false);
				return;
			}
			// 同コンポーネント内の（一覧からの）ドラッグ＆ドロップの場合は処理終了
			for (int nomRow = 0; nomRow < this.getNumRows(); nomRow++) {
				DnDData dndData = getRowData(nomRow);// 一覧.データ
				if (dndData.getCode().equals(list.get(0).getCode())) {
					e.dropComplete(false);
					return;
				}
			}

			// ③ 一覧に転送データを追加する。繰り返し処理
			e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			for (DnDData dndData : list) {
				addRow(dndData);
			}

			// ④ 一覧を ソートする。
			this.doSort();

			// ⑤ ドロップ処理を完了する。
			e.dropComplete(true);

		} else {
			e.rejectDrop();
			return;
		}
	}

	/**
	 * ドロップ終了時に呼ばれるメソッド ドラッグ＆ドロップを完了する。
	 */
	public void dragDropEnd(DragSourceDropEvent e) {

		// ①ドロップが正常に完了したかをチェックする。
		if (!e.getDropSuccess()) {
			return;
		}

		// ②他コンポーネントにデータを転送した場合、転送した行を一覧から削除する。
		// 転送データ：List<DnDData>を取得する。転送データはDragSourceDropEventで保持しているTransferableから取得する。
		List<DnDData> list = TTransferable.getDnDDataList(e.getDragSourceContext().getTransferable());
		if (list == null || list.isEmpty()) {
			return;
		}

		// 対象データを一覧から削除する
		for (DnDData dndData : list) {
			deleteRow(dndData);
		}
	}

	/**
	 * 一覧にデータを追加する。
	 * 
	 * @param dndData TTｒeeの階層データ受け渡しに使用するクラス
	 */
	public void addRow(DnDData dndData) {
		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();
		Vector<Vector> cells = ds.getCells();

		Vector<Object> colum = new Vector<Object>();
		colum.add(0, dndData); // カラム0列目（非表示カラム:引数.DnDDataインスタンス
		colum.add(1, dndData.getCode());// カラム1列目:引数.DnDData.コード
		colum.add(2, dndData.getName());// カラム2列目:引数.DnDData.名称

		// 追加
		cells.add(cells.size(), colum);

		// 一覧にデータを反映する
		setDataList(cells);
	}

	/**
	 * 指定行からデータを取得する
	 * 
	 * @param row 指定行
	 * @return DnDData
	 */
	public DnDData getRowData(int row) {

		if (row > this.getNumRows() - 1) {
			return null;
		}

		TableDataModel model = this.getDataView();

		DnDData dndData = new DnDData();
		dndData.setCode(model.getTableDataItem(row, 1).toString());
		dndData.setName(model.getTableDataItem(row, 2).toString());
		dndData.setTopCode("");

		return dndData;
	}

	/**
	 * 指対象データを一覧から削除する
	 * 
	 * @param dndData
	 */
	public void deleteRow(DnDData dndData) {
		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();
		Vector<Vector> cells = ds.getCells();

		TableDataModel model = this.getDataView();
		for (int row = 0; row < model.getNumRows(); row++) {
			String code = model.getTableDataItem(row, 1).toString();
			if (code.equals(dndData.getCode())) {
				cells.remove(row);
				break;
			}
		}

		// 一覧にデータを反映する
		setDataList(cells);

		if (cells.size() == 0) {
			dragFlg = false;
		}
	}

	/**
	 * 一覧をソートする
	 */
	public void doSort() {
		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();
		Vector<Vector> cells = ds.getCells();

		// 並び順
		Collections.sort(cells, new OrderByCode());

		// 一覧にデータを反映する
		setDataList(cells);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		// 未使用

	}

	public void dragExit(DropTargetEvent dte) {
		// 未使用

	}

	public void dragOver(DropTargetDragEvent dtde) {
		// 未使用
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// 未使用

	}

	public void dragEnter(DragSourceDragEvent dsde) {
		// 未使用

	}

	public void dragExit(DragSourceEvent dse) {
		// 未使用

	}

	public void dragOver(DragSourceDragEvent dsde) {
		// 未使用

	}

	public void dropActionChanged(DragSourceDragEvent dsde) {
		// 未使用

	}

	/**
	 * 一覧のVector並び順の比較クラス
	 * 
	 * @author Yanwei
	 */
	private class OrderByCode implements Comparator<Vector> {

		/**
		 * 一覧のVector条件
		 * 
		 * @param vecFir 比較対象の最初のオブジェクト
		 * @param vecSec 比較対象の 2 番目のオブジェクト
		 * @return int 比較結果
		 */
		public int compare(Vector vecFir, Vector vecSec) {

			String o1Code = vecFir.get(1).toString();// コード
			String o2Code = vecSec.get(1).toString();

			return o1Code.compareTo(o2Code);
		}
	}

	/**
	 * 一覧にデータを反映する
	 * 
	 * @param cells 一覧データ
	 */
	public void setDataList(Vector<Vector> cells) {
		// SSデータの構築
		JCVectorDataSource ds = new JCVectorDataSource();

		// 列表題を設定する
		ds.setColumnLabels(this.columnLabels);

		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ds.setNumColumns(3);

		this.setDataSource(ds);

		if (cells.size() > 0) {
			this.setRowSelection(0, 0);
			this.setCurrentCell(0, 0);
		}
	}

}