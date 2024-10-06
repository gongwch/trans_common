package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.table.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * MG0460TagMaster - 付箋マスタ - Main Panel Class
 * 
 * @author AIS
 */
public class MG0460TagMasterPanel extends TMainPanel {

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 編集ボタン */
	public TImageButton btnModify;

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** 付箋マスタ一覧 */
	public TTable tbList;

	/** 一覧のカラム定義 */
	public enum SC {

		/** bean */
		bean
		/**  */
		, CODE
		/**  */
		, COLOR
		/**  */
		, TITLE
	}

	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		tbList = new TTable();
		tbList.addColumn(SC.bean, "", -1);
		tbList.addColumn(SC.CODE, getWord("CM0081"), 120);
		tbList.addColumn(SC.COLOR, getWord("CM0082"), 120);
		tbList.addColumn(SC.TITLE, getWord("CM0083"), 360);
		tbList.getColumnModel().getColumn(SC.COLOR.ordinal()).setCellRenderer(new TagRenderer(tbList));
	}

	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setSize(25, 110);
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setSize(25, 110);
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setSize(25, 110);
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbList.addSpreadSheetSelectChange(btnModify);

		// 複写ボタン
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setSize(25, 110);
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setSize(25, 110);
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// 下部
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbList, gc);
	}

	/**
	 * セルレンダラ（更新処理後の付箋色変更対応）
	 */
	public class TagRenderer extends TBaseCellRenderer {

		/**
		 * コンストラクタ
		 * 
		 * @param tbl
		 */
		public TagRenderer(TTable tbl) {
			super(tbl);
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
			boolean paramBoolean2, int paramInt1, int paramInt2) {

			Tag hdr = getHeaderAt(paramInt1);
			Color foreC = hdr.getColor();

			Component comp = super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1,
				paramBoolean2, paramInt1, paramInt2);
			comp.setBackground(foreC);

			return comp;
		}

		/**
		 * @param row
		 * @return compornent
		 */
		protected Tag getHeaderAt(int row) {
			return (Tag) tbl.getRowValueAt(row, MG0460TagMasterPanel.SC.bean);
		}

	}

	@Override
	public void setTabIndex() {
		int i = 1;

		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		tbList.setTabControlNo(i++);
	}

}
