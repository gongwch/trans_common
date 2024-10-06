package jp.co.ais.trans.master.ui;

import java.io.*;
import java.util.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;

/**
 * 会社階層マスタ画面コントロール
 * 
 * @author DXC
 */
public class OW0140CompanyHierarchicalMasterPanelCtrl extends TPanelCtrlBase {

	// =========================================
	// 定数 :
	// =========================================
	/** プログラムID */
	private static final String PROGRAM_ID = "OW0140";

	/** 処理サーブレット */
	private static final String TRAGET_SERVLET = "OW0140CompanyHierarchicalMasterServlet";

	/** パネル */
	protected OW0140CompanyHierarchicalMasterPanel panel;

	// サーブレットから送られてきた結果を配列にセット
	protected Vector<Vector> cells1 = new Vector<Vector>();

	protected Vector<Vector<String>> cells2 = new Vector<Vector<String>>();

	protected Vector<ArrayList<String>> cells3 = new Vector<ArrayList<String>>();

	protected Vector<Vector> cells4 = new Vector<Vector>();

	protected int changeflag = 0;

	protected int orgselectflag = 0;

	protected String kaiCode;

	protected boolean sskNewFlag = false;

	/**
	 * コンストラクタ.
	 */
	public OW0140CompanyHierarchicalMasterPanelCtrl() {
		try {
			panel = new OW0140CompanyHierarchicalMasterPanel(this);

			this.kaiCode = getLoginUserCompanyCode();

			this.getOrgCode();

			// ボタンロック
			unlockBtn();

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * パネル取得
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * @return String
	 */
	public String getKaiCode() {
		return this.kaiCode;
	}

	/**
	 * 新規登録処理
	 */
	void doNewOrg() {
		try {
			if (changeflag == 1) {
				boolean isOK = super.showConfirmMessage(panel, "Q00010", "");
				if (!isOK) {
					unlockBtn();
					return;
				}

			}

			OW0140EditNewOrganizationDialogCtrl dialog = new OW0140EditNewOrganizationDialogCtrl(
				panel.getParentFrame(), "C01995");

			dialog.show();

			if (!dialog.isSettle()) {
				unlockBtn();
				return;
			}

			cells2.removeAllElements();
			cells3.removeAllElements();
			cells1.removeAllElements();
			if (sskNewFlag) {
				cells4.remove(orgselectflag);
				sskNewFlag = false;
			}

			String dpkSsk = (String) dialog.getDataList().get("orgCode");
			String dpkLvl0 = (String) dialog.getDataList().get("lvl0Code");
			String dpkLvl0Name = (String) dialog.getDataList().get("lvl0Name");

			String newFlag = "new";
			Vector<String> colum4 = new Vector<String>();
			colum4.add(0, dpkSsk);
			colum4.add(1, dpkLvl0);
			colum4.add(2, dpkLvl0Name);
			colum4.add(3, newFlag);
			cells4.add(colum4);

			if (((String) (cells4.get(cells4.size() - 1)).get(1)).equals("")) {
				panel.ctrlLevel0.setValue(" ");
			} else {
				panel.ctrlLevel0.setValue((String) (cells4.get(cells4.size() - 1)).get(1));
			}
			if (((String) (cells4.get(cells4.size() - 1)).get(2)).equals("")) {
				panel.txtCompanyName.setValue(" ");
			} else {
				panel.txtCompanyName.setValue(((String) (cells4.get(cells4.size() - 1)).get(2)).trim());
			}

			this.setOrganizationCode(cells4);
			this.setctrlOrganizationCodeSelected((cells4.size() - 1), cells4);
			this.orgselectflag = cells4.size() - 1;
			changeflag = 1;
			sskNewFlag = true;
			this.refleshNewSsk(dpkLvl0);
			unlockBtn();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 削除処理
	 */
	void delete() {
		try {
			if (sskNewFlag) {
				return;
			}
			if (orgselectflag == 0) {
				return;
			}
			// 削除確認: 削除しますか？
			if (!showConfirmMessage(panel, FocusButton.NO, "Q00007")) {
				return;
			}

			int numSelect = (panel.ctrlOrganizationCode.getComboBox()).getSelectedIndex();
			if (numSelect == 0) {
				return;
			}
			if (((String) (cells4.get(numSelect)).get(2)) != "new") {
				String sskCode = (String) (cells4.get(numSelect)).get(0);
				super.clearSendValues();
				super.addSendValues("flag", "deleteOrgCode");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("sskCode", sskCode);

				boolean rt = super.request(TRAGET_SERVLET);

				if (rt == false) {
					errorHandler(panel);

				}

			}

			cells4.remove(numSelect);
			changeflag = 0;

			getOrgCode();

			this.reflesh(true);
		} catch (IOException e) {
			errorHandler(panel.getParentFrame(), e);
		}
	}

	/**
	 * 検索処理
	 */
	void find() {

		try {

			int numSelect = (panel.ctrlOrganizationCode.getComboBox()).getSelectedIndex();
			if (panel.isItemSetFlag == true) {
				return;
			} else {
				if (changeflag == 1) {
					boolean isOK = super.showConfirmMessage(panel, "Q00010", "");
					if (!isOK) {
						this.setctrlOrganizationCodeSelected(orgselectflag, cells4);
						return;
					}
					changeflag = 0;
					cells2.clear();
					cells3.clear();
					cells1.clear();
					if (sskNewFlag) {
						cells4.remove(orgselectflag);
						this.setOrganizationCode(cells4);
						this.setctrlOrganizationCodeSelected(numSelect, cells4);
						sskNewFlag = false;
					}

					changeflag = 0;
				}
				orgselectflag = numSelect;
			}
			if (orgselectflag == 0) {
				cells1.clear();
				cells2.clear();
				cells3.clear();
				panel.ctrlLevel0.setValue(" ");
				panel.txtCompanyName.setValue(" ");
				this.reflesh(true);
				return;
			}

			if (((String) (cells4.get(numSelect)).get(1)).equals("")) {
				panel.ctrlLevel0.setValue(" ");
			} else {
				panel.ctrlLevel0.setValue((String) (cells4.get(numSelect)).get(1));
			}
			if (((String) (cells4.get(numSelect)).get(2)).equals("")) {
				panel.txtCompanyName.setValue(" ");
			} else {
				panel.txtCompanyName.setValue(((String) (cells4.get(numSelect)).get(2)).trim());
			}

			changeflag = 0;
			this.reflesh(orgselectflag);
			// ボタンロック解除
			unlockBtn();
		} catch (IOException e) {
			errorHandler(panel.getParentFrame(), e, "E00015");
		}
	}

	/**
	 * ボタンロックの解除
	 */
	void unlockBtn() {

		if ((panel.ctrlOrganizationCode.getComboBox()).getSelectedIndex() > 0) {
			panel.btnDelete.setEnabled(true);
			panel.btnExcel.setEnabled(true);
		} else {
			panel.btnDelete.setEnabled(false);
			panel.btnExcel.setEnabled(false);
		}
		if (changeflag == 1) {
			panel.btnSettle.setEnabled(true);
		} else {
			panel.btnSettle.setEnabled(false);
		}
		if (panel.ssJournal2.getNumRows() > 1) {
			panel.btnUpperCompany.setEnabled(true);
			panel.btnLowerCompany.setEnabled(true);

		} else {
			panel.btnUpperCompany.setEnabled(false);
			panel.btnLowerCompany.setEnabled(false);

		}
		if (panel.ssJournal2.getNumRows() > 0) {
			panel.btnCompanyCancellation.setEnabled(true);
		} else {
			panel.btnCompanyCancellation.setEnabled(false);
			panel.btnSettle.setEnabled(false);
		}
		if (panel.ssJournal1.getNumRows() > 0) {
			panel.btnCompanyAdd.setEnabled(true);
		} else {
			panel.btnCompanyAdd.setEnabled(false);
		}
		panel.btnNew.setEnabled(true);

		if (sskNewFlag) {
			panel.btnExcel.setEnabled(false);
			panel.btnDelete.setEnabled(false);
		}
	}

	/**
	 * 画面リフレッシュ
	 * 
	 * @param numSelect
	 * @throws IOException
	 */
	private void reflesh(int numSelect) throws IOException {

		if (!getDataList1(numSelect)) {
			cells1.removeAllElements();
			cells2.removeAllElements();
			cells3.removeAllElements();
		} else if (!getDataList2(numSelect)) {
			cells1.removeAllElements();
			cells2.removeAllElements();
			cells3.removeAllElements();
		}

		reflesh(true);
	}

	private void reflesh(boolean refleshall) {

		if (refleshall) {
			this.setDataList1(cells1);
			this.setDataList2(cells2);
		} else {
			this.setDataList2(cells2);
		}
		unlockBtn();
	}

	private void refleshNewSsk(String kaicode) throws IOException {

		this.setDataList1(getDataListNewSsk(kaicode));

		getDataList2(0);
		this.setDataList2(cells2);

		unlockBtn();
	}

	/**
	 * 表示データの取得
	 * 
	 * @param numSelect
	 * @return データ
	 * @throws IOException
	 */
	private boolean getDataList1(int numSelect) throws IOException {

		String sskCode = (String) (cells4.get(numSelect)).get(0);
		sskCode = sskCode.trim();

		// 送信するパラメータを設定
		super.clearSendValues();
		super.addSendValues("flag", "findENVList");
		super.addSendValues("kaiCode", kaiCode);
		super.addSendValues("sskCode", sskCode);

		// 送信
		boolean rt = super.request(TRAGET_SERVLET);

		if (rt == false) {
			errorHandler(panel);
			return false;
		}

		cells1.removeAllElements();

		Iterator recordIte = super.getResultList().iterator();
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();

			Vector<String> colum1 = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum1.add(column, (String) dataIte.next());
			}
			cells1.add(row, colum1);
		}

		return true;
	}

	private boolean getDataList2(int numSelect) throws IOException {

		String sskCode = (String) (cells4.get(numSelect)).get(0);
		sskCode = sskCode.trim();

		// 送信するパラメータを設定
		super.clearSendValues();
		super.addSendValues("flag", "findEVKKaiCodeList");
		super.addSendValues("kaiCode", kaiCode);
		super.addSendValues("sskCode", sskCode);

		// 送信
		boolean rt = super.request(TRAGET_SERVLET);

		if (rt == false) {
			errorHandler(panel);
			return false;
		}

		cells2.removeAllElements();
		cells3.removeAllElements();

		Iterator recordIte = super.getResultList().iterator();
		int cellsrow = 0;
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();

			Vector<String> colum2 = new Vector<String>();
			ArrayList<String> colum3 = new ArrayList<String>(15);

			String dpkDepCode = "";

			String kaiName = " ";

			String dpkLvl = "";
			int lvlcount = 1;

			for (int column = 0; dataIte.hasNext(); column++) {

				if (column == 2) {
					dpkDepCode = (String) dataIte.next();
					colum3.add(column, dpkDepCode);
				} else if (column == 3) {
					dpkLvl = (String) dataIte.next();
					colum3.add(column, dpkLvl);
					lvlcount = Integer.parseInt(dpkLvl.trim());
				} else if (column == 16) {
					kaiName = (String) dataIte.next();
					kaiName.trim();
					if (kaiName.equals("")) kaiName = " ";
					colum3.add(column, kaiName);
				} else {
					String temp = (String) dataIte.next();
					if (temp.trim().equals("")) temp = " ";
					colum3.add(column, temp);
				}

			}
			colum2.add(0, dpkDepCode);
			colum2.add(1, dpkLvl);

			for (int j = 1; j < 10; j++) {
				if (j == lvlcount) {

					colum2.add(j + 1, kaiName);

				} else {
					colum2.add(j + 1, " ");
				}
			}

			if (lvlcount != 0) {
				cells2.add(cellsrow, colum2);
				cells3.add(cellsrow, colum3);
				cellsrow++;
			}
		}

		return true;

	}

	/**
	 * @param kaiCode1
	 * @return Vector
	 * @throws IOException
	 */
	private Vector getDataListNewSsk(String kaiCode1) throws IOException {

		// 送信するパラメータを設定
		super.clearSendValues();
		super.addSendValues("flag", "findENVListNewSsk");
		super.addSendValues("kaiCode", kaiCode1);

		// 送信
		super.request(TRAGET_SERVLET);

		cells1.removeAllElements();

		Iterator recordIte = super.getResultList().iterator();
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();

			Vector<String> colum1 = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum1.add(column, (String) dataIte.next());
			}
			cells1.add(row, colum1);
		}

		return cells1;
	}

	/**
	 * lowerCompany
	 */
	void doUpperCompany() { // doLowerCompany --> doUpperCompany

		try {
			if (orgselectflag == 0) {
				return;
			}
			if (cells2.size() < 2) {
				return;
			}

			OW0140EditCompanySelectionDialogCtrl dialog = new OW0140EditCompanySelectionDialogCtrl(panel
				.getParentFrame(), cells3, "C02395"); // C02396 --> C02395

			int nomRow = panel.ssJournal2.getCurrentRow();
			TableDataModel model = panel.ssJournal2.getDataSource();

			String strCol = (String) model.getTableDataItem(nomRow, 1);
			int nomCol = Integer.parseInt(strCol);
			if (nomCol == 0) {
				return;
			}

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			map.put("lvl", model.getTableDataItem(nomRow, 1));
			map.put("lvlName", model.getTableDataItem(nomRow, (nomCol + 1)));
			map.put("flag", "lowerCom"); // lowerCom

			dialog.setValues(map);
			dialog.show(true);

			if (!dialog.isSettle()) {
				unlockBtn();
				return;
			}

			modifyCompanySet(dialog.getDataList());

			changeflag = 1;
			this.reflesh(false);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * upperCompany
	 */
	void doLowerCompany() { // doUpperCompany --> doLowerCompany

		try {

			if (orgselectflag == 0) {
				return;
			}
			if (cells2.size() < 2) {
				return;
			}

			OW0140EditCompanySelectionDialogCtrl dialog = new OW0140EditCompanySelectionDialogCtrl(panel
				.getParentFrame(), cells3, "C02396"); // C02395 --> C02396
			int nomRow = panel.ssJournal2.getCurrentRow();
			TableDataModel model = panel.ssJournal2.getDataSource();

			String strCol = (String) model.getTableDataItem(nomRow, 1);
			int nomCol = Integer.parseInt(strCol);
			if (nomCol == 9) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			map.put("lvl", model.getTableDataItem(nomRow, 1));
			map.put("lvlName", model.getTableDataItem(nomRow, (nomCol + 1)));
			map.put("flag", "upperCom"); // upperCom

			dialog.setValues(map);
			dialog.show(true);

			if (!dialog.isSettle()) {
				unlockBtn();
				return;
			}

			modifyCompanySet(dialog.getDataList());

			changeflag = 1;
			this.reflesh(false);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	private void modifyCompanySet(Map map) {

		String lowerkaiName = (String) map.get("lowerkaiName");
		String upperkaiCode = (String) map.get("upperkaiCode");
		String lowerkaiCode = (String) map.get("lowerkaiCode");

		int lowerIndex = this.getKatCodeIndex(cells2, lowerkaiCode);

		resetCells(lowerIndex);

		String strDate = cells3.get(lowerIndex).get(14);
		String endDate = cells3.get(lowerIndex).get(15);

		cells2.remove(lowerIndex);
		cells3.remove(lowerIndex);

		int upperIndex = this.getKatCodeIndex(cells2, upperkaiCode);
		int upperlvlnum = Integer.parseInt((cells2.get(upperIndex)).get(1));

		Vector<String> colum2 = new Vector<String>();
		ArrayList colum1 = new ArrayList(15);
		colum1 = cells3.get(upperIndex);
		ArrayList<String> colum3 = new ArrayList<String>(15);

		for (int k = 0; k < colum1.size(); k++)
			colum3.add(k, (String) colum1.get(k));

		colum2.add(0, lowerkaiCode);
		colum3.set(2, lowerkaiCode);

		colum2.add(1, String.valueOf(upperlvlnum + 1));
		colum3.set(3, String.valueOf(upperlvlnum + 1));

		colum3.set(upperlvlnum + 5, lowerkaiCode);

		colum3.set(14, strDate);
		colum3.set(15, endDate);

		if (colum3.size() >= 17) colum3.set(16, lowerkaiName);

		for (int j = 1; j < 10; j++) {
			if (j == (upperlvlnum + 1)) {
				colum2.add(j + 1, lowerkaiName);
			} else {
				colum2.add(j + 1, "");
			}
		}

		cells2.add(upperIndex + 1, colum2);
		cells3.add(upperIndex + 1, colum3);

	}

	/**
	 * settle
	 */
	void settle() {

		try {

			if (orgselectflag == 0) {
				return;
			}
			if (changeflag != 1) {
				return;
			}

			// 登録しますか
			if (!showConfirmMessage(panel.getParentFrame(), "Q00005")) {
				return;
			}

			super.clearSendValues();
			super.addSendValues("flag", "update");
			super.addSendValues("prgId", PROGRAM_ID);
			super.addSendValues("usrId", getLoginUserID());
			super.addSendValues("updateDate", setStringDate());

			for (int i = 0; i < cells3.size(); i++) {
				super.addSendValues("updateDate", setStringDate(i));
			}

			if (!request(TRAGET_SERVLET)) {
				errorHandler(panel.getParentFrame());
				return;
			}

			changeflag = 0;
			sskNewFlag = false;

			// 処理が完了しました。
			super.showMessage(panel, "I00008");

			this.reflesh(orgselectflag);

		} catch (IOException e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * companyCancel
	 */
	void doCompanyCancel() {

		if (orgselectflag == 0) {
			return;
		}
		if (cells2.size() == 0) {
			return;
		}

		int nomRow = panel.ssJournal2.getCurrentRow();
		if (nomRow == -1) {
			return;
		}

		String kaiName = "";
		String kaiCode1 = (cells3.get(nomRow)).get(2);
		if ((cells3.get(nomRow)).size() >= 16) {
			kaiName = (cells3.get(nomRow)).get(16);
		}

		String strDate = cells3.get(nomRow).get(14);
		String endDate = cells3.get(nomRow).get(15);

		resetCells(nomRow);

		cells2.remove(nomRow);
		cells3.remove(nomRow);

		Vector<String> colum1 = new Vector<String>();
		colum1.add(0, kaiCode1);
		colum1.add(1, strDate);
		colum1.add(2, endDate);
		colum1.add(3, kaiName);

		int nomRowToInsert = getKaiCodeOrder(cells1, kaiCode1, 2);
		if (nomRowToInsert >= 0) {
			cells1.add(nomRowToInsert, colum1);
		} else {
			cells1.add(colum1);
		}

		changeflag = 1;
		this.reflesh(true);
		// ボタンロック解除
		unlockBtn();
	}

	/**
	 * companyAdd
	 */
	void doCompanyAdd() {

		if (orgselectflag == 0) {
			return;
		}
		if (cells1.size() == 0) {
			return;
		}

		int nomRow = panel.ssJournal1.getCurrentRow();

		int numSelect = (panel.ctrlOrganizationCode.getComboBox()).getSelectedIndex();
		String orgCode = (String) (cells4.get(numSelect)).get(0);
		orgCode = orgCode.trim();

		TableDataModel model = panel.ssJournal1.getDataSource();

		String dpkDepCode = (String) model.getTableDataItem(nomRow, 0);
		String kaiName = (String) cells1.get(nomRow).get(3);

		String strDate = (String) cells1.get(nomRow).get(1);
		String endDate = (String) cells1.get(nomRow).get(2);

		cells1.remove(nomRow);
		int nomRowToInsert = getKaiCodeOrderArragylist(cells3, kaiCode, 5);

		Vector<String> colum2 = new Vector<String>();
		int numlel = 1;
		colum2.add(0, dpkDepCode);
		colum2.add(1, "1");
		for (int j = 1; j < 10; j++) {
			if (j == numlel) {
				colum2.add(j + 1, kaiName);
			} else {
				colum2.add(j + 1, "");
			}
		}

		ArrayList<String> colum3 = new ArrayList<String>(15);
		colum3.add(0, kaiCode);
		colum3.add(1, orgCode);
		colum3.add(2, dpkDepCode);
		colum3.add(3, "1");
		colum3.add(4, (String) cells4.get(orgselectflag).get(1));
		colum3.add(5, dpkDepCode);
		for (int g = 6; g < 14; g++) {
			colum3.add(g, " ");
		}
		colum3.add(14, strDate);
		colum3.add(15, endDate);
		colum3.add(16, kaiName);

		if (nomRowToInsert >= 0) {
			cells2.add(nomRowToInsert, colum2);
			cells3.add(nomRowToInsert, colum3);
		} else {
			cells2.add(colum2);
			cells3.add(colum3);
		}

		changeflag = 1;
		this.reflesh(true);
		// ボタンロック解除
		unlockBtn();
	}

	/**
	 * listOutput
	 */
	void listOutput() {

		try {
			if (sskNewFlag) {
				return;
			}
			if (orgselectflag == 0) {
				return;
			}

			boolean isOK = super.showConfirmMessage(panel, "Q00011", "");
			if (!isOK) {
				this.setctrlOrganizationCodeSelected(orgselectflag, cells4);
				// settle(false);
				return;
			}

			this.reflesh(orgselectflag);

			if (panel.ssJournal2.getDataSource().getNumRows() == 0) {
				return;
			}

			String sskCode = (String) (cells4.get(orgselectflag)).get(0);

			Map<String, Object> mapUrl = new HashMap<String, Object>();

			mapUrl.put("flag", "listOut");
			mapUrl.put("kaiCode", kaiCode);
			mapUrl.put("sskCode", sskCode);

			this.download(panel, TRAGET_SERVLET, mapUrl);

			// クライアントで受
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	void getOrgCode() {
		try {

			// 送信するパラメータを設定
			super.clearSendValues();
			super.addSendValues("flag", "getOrgCode");
			super.addSendValues("kaiCode", this.kaiCode);

			// 送信
			if (!request(TRAGET_SERVLET)) {
				errorHandler(panel);
			}

			cells4.clear();

			Iterator recordIte = super.getResultList().iterator();
			for (int row = 0; recordIte.hasNext(); row++) {
				Iterator dataIte = ((List) recordIte.next()).iterator();

				Vector<String> colum4 = new Vector<String>();

				for (int column = 0; dataIte.hasNext(); column++) {
					colum4.add(column, (String) dataIte.next());
				}
				if (row == 0) {
					Vector<String> colum5 = new Vector<String>();

					for (int column = 0; column < 4; column++) {
						colum5.add(column, "");
					}
					cells4.add(row, colum5);
				}
				cells4.add(row + 1, colum4);
			}
			if (cells4.size() == 0) {
				Vector<String> colum5 = new Vector<String>();

				for (int column = 0; column < 4; column++) {
					colum5.add(column, "");
				}
				cells4.add(colum5);
			}
			this.setOrganizationCode(cells4);
			this.setctrlOrganizationCodeSelected(0, cells4);

			cells1.clear();
			cells2.clear();
			cells3.clear();
			// ボタンロック解除
			unlockBtn();
		} catch (IOException e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * @param cellsTemp
	 * @param kaiCode1
	 * @param Col
	 * @return int
	 */
	int getKaiCodeOrder(Vector<Vector> cellsTemp, String kaiCode1, int Col) {

		int kaicodenum = cellsTemp.size();
		for (int i = 0; i < kaicodenum; i++) {
			String kaicodeTemp = (String) (cellsTemp.get(i)).get(Col);
			if (kaicodeTemp.compareToIgnoreCase(kaiCode1) > 0) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * @param cellsTemp
	 * @param kaiCode1
	 * @param Col
	 * @return int
	 */
	int getKaiCodeOrderArragylist(Vector<ArrayList<String>> cellsTemp, String kaiCode1, int Col) {

		int kaicodenum = cellsTemp.size();
		for (int i = 0; i < kaicodenum; i++) {
			String kaicodeTemp = (cellsTemp.get(i)).get(Col);
			if (kaicodeTemp.compareToIgnoreCase(kaiCode1) > 0) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * @param cellsTemp
	 * @param kaiCode1
	 * @return int
	 */
	int getKatCodeIndex(Vector<Vector<String>> cellsTemp, String kaiCode1) {

		int kaicodenum = cellsTemp.size();
		for (int i = 0; i < kaicodenum; i++) {
			String kaicodeTemp = (cellsTemp.get(i)).get(0);
			if (kaicodeTemp.compareToIgnoreCase(kaiCode1) == 0) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * @param lowerIndex
	 */
	void resetCells(int lowerIndex) {
		int lowerlvl = Integer.parseInt((cells3.get(lowerIndex)).get(3));
		for (int i = lowerIndex + 1; i < cells3.size(); i++) {
			int templvl = Integer.parseInt((cells3.get(i)).get(3));
			if (templvl <= lowerlvl) {
				return;
			}
			(cells3.get(i)).set(3, String.valueOf(templvl - 1));
			(cells3.get(i)).remove(lowerlvl + 4);

			(cells3.get(i)).add(13, " ");

			(cells2.get(i)).set(1, String.valueOf(templvl - 1));
			(cells2.get(i)).remove(lowerlvl + 1);
			(cells2.get(i)).add(" ");
		}
	}

	String setStringDate(int i) {

		String[] strTemp = { (cells3.get(i)).get(0), (cells3.get(i)).get(1), (cells3.get(i)).get(2),
				(cells3.get(i)).get(3), (cells3.get(i)).get(4), (cells3.get(i)).get(5), (cells3.get(i)).get(6),
				(cells3.get(i)).get(7), (cells3.get(i)).get(8), (cells3.get(i)).get(9), (cells3.get(i)).get(10),
				(cells3.get(i)).get(11), (cells3.get(i)).get(12), (cells3.get(i)).get(13), (cells3.get(i)).get(14),
				(cells3.get(i)).get(15), "end" };
		String strReturn = StringUtil.toHTMLEscapeString(strTemp);
		return strReturn;

	}

	String setStringDate() {

		String[] strTemp = { this.kaiCode, (String) (cells4.get(orgselectflag)).get(0),
				(String) (cells4.get(orgselectflag)).get(1), "0", (String) (cells4.get(orgselectflag)).get(1), " ",
				" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "end" };
		String strReturn = StringUtil.toHTMLEscapeString(strTemp);

		return strReturn;

	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList1(Vector cells) {

		// Set the cell data in the data source.
		panel.ds1.setCells(cells);

		panel.ds1.setNumRows(cells.size());

		// Set the data source to the vector data source from earlier
		panel.ssJournal1.setDataSource(panel.ds1);

		if (cells.size() > 0) {
			// 指定rowにフォーカスを当てる
			panel.ssJournal1.clearSelectedCells();
			panel.ssJournal1.setRowSelection(0, 0);
			panel.ssJournal1.setCurrentCell(0, 0);
		}

	}

	void setDataList2(Vector cells) {

		// Set the cell data in the data source.
		panel.ds2.setCells(cells);

		panel.ds2.setNumRows(cells.size());

		// Set the data source to the vector data source from earlier
		panel.ssJournal2.setDataSource(panel.ds2);

		// 右寄せする
		CellStyleModel stDefault = panel.ssJournal2.getDefaultCellStyle();
		JCCellStyle rightStyle = new JCCellStyle(stDefault);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		panel.ssJournal2.setCellStyle(JCTableEnum.ALLCELLS, 1, rightStyle);

		if (cells.size() > 0) {
			// 指定rowにフォーカスを当てる
			panel.ssJournal2.clearSelectedCells();
			panel.ssJournal2.setRowSelection(0, 0);
			panel.ssJournal2.setCurrentCell(0, 0);
		}

	}

	void setOrganizationCode(Vector<Vector> cells) {
		TComboBox tcOrganizationCode = panel.ctrlOrganizationCode.getComboBox();
		panel.isItemSetFlag = true;
		tcOrganizationCode.removeAllItems();
		int iNum = cells.size();
		for (int i = 0; i < iNum; i++) {
			tcOrganizationCode.addItem((cells.get(i)).get(0));
		}
		panel.isItemSetFlag = false;
	}

	void setctrlOrganizationCodeSelected(int index, Vector<Vector> cells) {
		TComboBox tcOrganizationCode = panel.ctrlOrganizationCode.getComboBox();
		panel.isItemSetFlag = true;
		tcOrganizationCode.setSelectedIndex(index);
		panel.ctrlLevel0.setValue((String) cells.get(index).get(1));
		panel.txtCompanyName.setValue(cells.get(index).get(2));
		panel.isItemSetFlag = false;

	}

}
