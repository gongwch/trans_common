package jp.co.ais.trans.master.ui;

import java.util.*;

import com.klg.jclass.table.*;

/**
 * 会社コントロールマスタ
 * 
 * @author liuchengcheng
 */
public class MG0031CompanyControlMasterPanel extends MG0030CompanyControlMasterPanel {


	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	public MG0031CompanyControlMasterPanel(MG0031CompanyControlMasterPanelCtrl ctrl) {
		super(ctrl);
	}

	protected void initSpreadSheet() {
		// SpreadSheet を init する

		String strDirectKbn = ctrl.getWord("C10547") + ctrl.getWord("C00017") + ctrl.getWord("C00127");
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00700", "C00701", "C00942", "C00702", "C00936",
				"C00937", "C00938", "C00939", "C00940", "C00941", "C00703", "C00704", "C00705", "C00706", "C00707",
				"C00708", "C00943", "C00944", "C00945", "C00709", "C00710", "C00711", "C00105", "C00713", "C00714",
				"C00715", "C00224", "C01248", "C01000", "C02152", "C02153", "C00717", "C00557", "C00082", "C00083",
				strDirectKbn };
		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 6, 6, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 6, 9, 9, 9, 9, 9, 9, 4, 8, 8,
				8, 11, 10, 10, 10, 10, 8, 10, 12, 12, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		// 列、行表題のスタイル設定
		ssCompanyCodeRoleList.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// スプレッドイベントの初期化
		ssCompanyCodeRoleList.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssCompanyCodeRoleList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssCompanyCodeRoleList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(37);
		ds.setNumRows(0);

		ssCompanyCodeRoleList.setDataSource(ds);

	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		// 数値を右寄せする
		CellStyleModel defaultStyle = ssCompanyCodeRoleList.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 3, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 5, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 6, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 7, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 9, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 10, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 17, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 18, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 19, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 28, centerStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 32, centerStyle);

		JCCellStyle rightStyle = new JCCellStyle(defaultStyle);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 23, rightStyle);
		ssCompanyCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 27, rightStyle);

		this.btnListOutput.setEnabled(cells.size() > 0);
		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);

		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssCompanyCodeRoleList.setDataSource(ds);
	}
}
