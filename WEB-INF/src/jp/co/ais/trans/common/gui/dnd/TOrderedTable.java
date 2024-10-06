package jp.co.ais.trans.common.gui.dnd;

import java.awt.*;
import java.awt.dnd.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * Ttable拡張クラス･･･スプレッド内の順序変更をドラッグ&ドロップで実装したテーブル
 */
public abstract class TOrderedTable extends TTable implements DropTargetListener, DragGestureListener,
	DragSourceListener {

	/** エンティティを保持しているカラムインデックス */
	static final int BEAN_INDEX = 0;

	/**
	 * コンストラクタ
	 */
	public TOrderedTable() {

		// スプレッドの表示を設定する
		initTableStyle();

	}

	/**
	 * レイアウトを設定する
	 */
	@Override
	public void doLayout() {
		super.doLayout();

		// ドロップターゲットの追加
		Component cellArea = super.getCellAreaHandler().getCellArea();
		if (cellArea != null) {
			new DropTarget(cellArea, DnDConstants.ACTION_COPY_OR_MOVE, this);
			new DragSource().createDefaultDragGestureRecognizer(cellArea, DnDConstants.ACTION_COPY_OR_MOVE, this);
		}
	}

	/**
	 * ドラッグデータを転送する（ドラッグ開始時の処理）
	 */
	public void dragGestureRecognized(DragGestureEvent e) {

		try {

			if ((e.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

				// 選択行の取得
				List<OrderedData> list = getSelectedList();

				// 選択データがない場合はドラッグしない
				if (list.size() == 0) {
					return;
				}

				// ドラッグ処理の開始
				e.startDrag(DragSource.DefaultCopyDrop, new TOrderedTransferable(list), this);
			}

		} catch (InvalidDnDOperationException ioe) {
			// ドラッグ時に必ず発生するので処理しない
		}
	}

	/**
	 * 転送データを取得/設定する（ドロップ時の処理）
	 */
	public void drop(DropTargetDropEvent e) {

		try {
			Object droppedObject = e.getTransferable().getTransferData(TOrderedTransferable.orderedFlavor);

			// 転送データの取得
			List<OrderedData> list = (List<OrderedData>) droppedObject;

			// 転送データが存在しない場合は処理終了
			if (Util.isNullOrEmpty(list) || list.size() == 0) {
				e.dropComplete(false);
				return;
			}

			e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

			Point point = e.getLocation();
			Component comp = ((DropTarget) e.getSource()).getComponent();
			Point tablePoint = comp.getLocation();
			point.translate(tablePoint.x, tablePoint.y);

			JCCellPosition position = XYToCell(point.x, point.y);
			OrderedData dropTargetData = getOrderedData(position.row);

			// ドロップ位置にデータが存在しない場合は処理終了
			if (Util.isNullOrEmpty(dropTargetData)) {
				e.dropComplete(false);
				return;
			}

			// ドロップ行と転送行が同じ場合は処理終了
			for (OrderedData bean : list) {
				if (bean.equals(dropTargetData)) {
					e.dropComplete(false);
					return;
				}
			}

			// 転送行データの削除
			removeRow(list);

			// ドロップ行の下にデータを追加する
			List<OrderedData> updList = new ArrayList<OrderedData>();
			for (int rowIndex = 0; rowIndex < this.getNumRows(); rowIndex++) {
				OrderedData bean = getOrderedData(rowIndex);
				updList.add(bean);
				if (bean.equals(dropTargetData)) {
					updList.addAll(list);
				}
			}

			setOrderedData(updList);

			// ドロップ行を再選択
			for (int rowIndex = 0; rowIndex < this.getNumRows(); rowIndex++) {
				OrderedData bean = getOrderedData(rowIndex);
				if (bean.equals(dropTargetData)) {
					setRowSelection(rowIndex, rowIndex);
					setCurrentCell(rowIndex, 0);
				}
			}

			e.dropComplete(true);

		} catch (Exception ex) {
			e.dropComplete(false);
		}
	}

	/**
	 * 指定した行で保持しているエンティティデータを取得する
	 * 
	 * @param index
	 * @return エンティティ
	 */
	public OrderedData getOrderedData(int index) {

		return (OrderedData) this.getDataView().getTableDataItem(index, BEAN_INDEX);
	}

	/**
	 * 一致するデータを削除する
	 * 
	 * @param list 対象データリスト
	 */
	public void removeRow(List<OrderedData> list) {

		// 保持するデータを更新する
		List<OrderedData> updList = getOrderedData();
		setOrderedData(updList);

		// 削除対象行の取得
		updList = new ArrayList<OrderedData>();
		for (int rowIndex = 0; rowIndex < this.getNumRows(); rowIndex++) {
			boolean isAdd = true;
			OrderedData bean = getOrderedData(rowIndex);

			// データが一致する場合 追加しない。
			for (OrderedData deleteBean : list) {
				if (deleteBean.equals(bean)) {
					isAdd = false;
				}
			}

			if (isAdd) {
				updList.add(bean);
			}
		}

		setOrderedData(updList);
	}

	/**
	 * スプレッドの表示を設定する
	 */
	public abstract void initTableStyle();

	/**
	 * 選択行を取得する
	 * 
	 * @return list
	 */
	public abstract List<OrderedData> getSelectedList();

	/**
	 * 一覧にデータを設定する
	 * 
	 * @param list
	 */
	public abstract void setOrderedData(List<OrderedData> list);

	/**
	 * 一覧のデータを取得する
	 * 
	 * @return 一覧データ
	 */
	public abstract List<OrderedData> getOrderedData();

	// ドラッグを受け入れた場合、カーソルの表示を変える
	public void dragEnter(DragSourceDragEvent e) {
		e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	}

	// ドラッグを受け入れた場合、カーソルの表示を変える
	public void dragExit(DragSourceEvent e) {
		e.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	}

	public void dragOver(DragSourceDragEvent e) {
		// 未使用
	}

	public void dropActionChanged(DragSourceDragEvent e) {
		// 未使用
	}

	public void dragEnter(DropTargetDragEvent e) {
		// 未使用
	}

	public void dragExit(DropTargetEvent e) {
		// 未使用
	}

	public void dragOver(DropTargetDragEvent e) {
		// 未使用
	}

	public void dropActionChanged(DropTargetDragEvent e) {
		// 未使用
	}

	public void dragDropEnd(DragSourceDropEvent dsde) {
		// 未使用

	}

}
