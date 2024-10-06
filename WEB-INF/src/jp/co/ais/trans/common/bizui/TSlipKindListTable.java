package jp.co.ais.trans.common.bizui;

import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 伝票種類フィールド
 * 
 * @author roh
 */
public class TSlipKindListTable extends TTable {

	/** 会社コード */
	private String companyCode;

	/** システム区分 true 設定した場合内部システム参照に変わる。 */
	private boolean includeSystemElse = true;

	/** データ区分リスト */
	private List<String> kbnCodeList;
	
	/** コントロール */
	private TSlipKindListTableCtrl ctrl;

	/**
	 * コンストラクタ
	 */
	public TSlipKindListTable() {
		this(TClientLoginInfo.getInstance().getCompanyCode());

	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode 会社コード
	 */
	public TSlipKindListTable(String companyCode) {
		this(companyCode, true, null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param includeSystemElse true:他システムから取り込んだ伝票種別を対象とする
	 */
	public TSlipKindListTable(boolean includeSystemElse) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), includeSystemElse, null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode
	 * @param includeSystemElse
	 */
	public TSlipKindListTable(String companyCode, boolean includeSystemElse) {
		this(companyCode, includeSystemElse, null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode 会社コード
	 * @param KbnCodeList データ区分リスト
	 */
	public TSlipKindListTable(String companyCode, List<String> KbnCodeList) {
		this(companyCode, true, KbnCodeList);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param includeSystemElse true:他システムから取り込んだ伝票種別を対象とする
	 * @param KbnCodeList データ区分リスト
	 */
	public TSlipKindListTable(boolean includeSystemElse, List<String> KbnCodeList) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), includeSystemElse, KbnCodeList);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param KbnCodeList データ区分リスト
	 */
	public TSlipKindListTable(List<String> KbnCodeList) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), true, KbnCodeList);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode 会社コード
	 * @param includeSystemElse true:他システムから取り込んだ伝票種別を対象とする
	 * @param KbnCodeList データ区分リスト
	 */
	public TSlipKindListTable(String companyCode, boolean includeSystemElse, List<String> KbnCodeList) {
		super();

		this.kbnCodeList = KbnCodeList;
		this.companyCode = companyCode;
		this.includeSystemElse = includeSystemElse;

		ctrl = new TSlipKindListTableCtrl(this);

		// セルの幅
		setCharWidth(0, 2);
		setCharWidth(1, 13);

		// ラベル設定なし
		setRowLabelDisplay(false);
	}

	/**
	 * 会社コード取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 他システムから取り込んだ伝票種別を対象とするか
	 * 
	 * @return true:他システムから取り込んだ伝票種別を対象とする
	 */
	public boolean isIncludeSystemElse() {
		return includeSystemElse;
	}
	
	/**
	 * タイトル設定
	 * 
	 * @param title タイトル
	 */
	public void setTableTitle(String[] title) {
		ctrl.cslipTypelabel = title;
		ctrl.setSpreadSheet();
	}

	/**
	 * 選択されている伝票種別データリストを取得する
	 * 
	 * @return 伝票種別データリスト
	 */
	public List<DEN_SYU_MST> getSelectedDataList() {
		List<DEN_SYU_MST> list = new LinkedList<DEN_SYU_MST>();

		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();

		for (int row = 0; row < this.getNumRows(); row++) {

			boolean isCheck = ((TCheckBox) this.getComponent(row, 0)).isSelected();

			if (isCheck) {
				Vector rows = (Vector) ds.getCells().get(row);
				list.add((DEN_SYU_MST) rows.get(2));
			}
		}

		return list;
	}

	/**
	 * 選択されている伝票種別コードを取得する
	 * 
	 * @return コードリスト
	 */
	public String[] getSelectedCodes() {
		List<DEN_SYU_MST> list = getSelectedDataList();

		String[] codes = new String[list.size()];

		int i = 0;
		for (DEN_SYU_MST bean : list) {
			codes[i] = bean.getDEN_SYU_CODE();
			i++;
		}

		return codes;
	}

	/**
	 * 選択されている更新区分コードがあるか
	 * 
	 * @return 選択されているとtrueを返す
	 */
	public boolean isSelected() {
		for (int row = 0; row < this.getNumRows(); row++) {
			boolean isCheck = ((TCheckBox) this.getComponent(row, 0)).isSelected();
			if (isCheck) {
				return true;
			}
		}
		return false;
	}

	/**
	 * データ区分リスト取得
	 * 
	 * @return データ区分リスト
	 */
	public List<String> getKbnCodeList() {
		return kbnCodeList;
	}

	/**
	 * データ区分リスト設定
	 * 
	 * @param kbnCodeList
	 */
	public void setKbnCodeList(List<String> kbnCodeList) {
		this.kbnCodeList = kbnCodeList;
	}

}
