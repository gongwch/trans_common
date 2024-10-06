package jp.co.ais.trans.common.bizui;

import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 更新区分一覧コンポーネント
 * 
 * @author ookawara
 */
public class TSlipStateListTable extends TTable {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** 登録 */
	public static final int INSERT = 1;

	/** 現場承認 */
	public static final int SPOT_ADMIT = 2;

	/** 現場否認 */
	public static final int SPOT_DENY = 11;

	/** 経理承認 */
	public static final int ACCOUNT_ADMIT = 3;

	/** 経理否認 */
	public static final int ACCOUNT_DENY = 12;

	/** 更新 */
	public static final int UPDATE = 4;

	/** 会社コード */
	private String companyCode;

	/** 更新区分 表示/非表示 */
	private boolean isIncludeUpdate;

	/** 表示すべき更新区分項目リスト */
	private List<Integer> visibleList;

	/** 表示されない更新区分項目リスト */
	private List<Integer> inVisibleList;

	/** Controller */
	private TSlipStateListTableCtrl ctrl;

	/**
	 * Constructor. デフォルト
	 */
	public TSlipStateListTable() {
		this(TClientLoginInfo.getInstance().getCompanyCode());
	}

	/**
	 * Constructor. 会社コード指定
	 * 
	 * @param companyCode 会社コード
	 */
	public TSlipStateListTable(String companyCode) {
		this(companyCode, true);

	}

	/**
	 * Constructor. 更新表示指定
	 * 
	 * @param isIncludeUpdate 更新 true：表示 false：非表示
	 */
	public TSlipStateListTable(boolean isIncludeUpdate) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), isIncludeUpdate);
	}

	/**
	 * Constructor. 会社コード・更新表示指定
	 * 
	 * @param companyCode 会社コード
	 * @param isIncludeUpdate 更新 true：表示 false：非表示
	 */
	public TSlipStateListTable(String companyCode, boolean isIncludeUpdate) {

		this.companyCode = companyCode;
		this.isIncludeUpdate = isIncludeUpdate;

		// 初期画面構築
		initComponents();

		// 値をスプレッドにセット
		ctrl = new TSlipStateListTableCtrl(this);

		// 更新区分一覧初期化
		ctrl.setTypeUpdateInit();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {

		// セル幅 初期設定
		setCharWidth(0, 2);
		setCharWidth(1, 5);

		setRowLabelDisplay(false);
	}

	/**
	 * 会社コードを返す
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 更新を含めるかを返す
	 * 
	 * @return isIncludeUpdate 更新を含めるか true：含める false:含めない
	 */
	public boolean getIncludeUpdate() {
		return this.isIncludeUpdate;
	}

	/**
	 * セル幅設定 チェックボックス
	 * 
	 * @param size サイズ
	 */
	public void setCheckColumn(int size) {
		setCharWidth(0, size);
	}

	/**
	 * セル幅設定 更新区分
	 * 
	 * @param size サイズ
	 */
	public void setUpdKbnColumn(int size) {
		setCharWidth(1, size);
	}

	/**
	 * タイトル設定
	 * 
	 * @param title タイトル
	 */
	public void setUpdKbnTitle(String[] title) {
		ctrl.cupdateDivisionlabel = title;
		ctrl.resetSpread(true);
	}

	/**
	 * 選択されている更新区分コードを取得する
	 * 
	 * @return コードリスト
	 */
	public int[] getSelectedCodes() {

		List<Integer> list = getSelectedCodesList();

		int[] ints = new int[list.size()];
		Iterator iter = list.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			ints[i] = ((Integer) iter.next()).intValue();
		}

		return ints;
	}

	/**
	 * 選択されている更新区分コードを取得する
	 * 
	 * @return コードリスト
	 */
	public List<Integer> getSelectedCodesList() {
		List<Integer> list = new LinkedList<Integer>();

		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();

		for (int row = 0; row < this.getNumRows(); row++) {

			boolean isCheck = ((TCheckBox) this.getComponent(row, 0)).isSelected();

			if (isCheck) {
				Vector rows = (Vector) ds.getCells().get(row);
				list.add((Integer) rows.get(2));
			}
		}

		return list;
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
	 * 非表示される項目のリスト
	 * 
	 * @return 非表示される項目リスト
	 */
	public List<Integer> getInVisibleList() {
		return inVisibleList;
	}

	/**
	 * 非表示される項目のリスト
	 * 
	 * @param inVisibleList
	 */
	public void setInVisibleList(List<Integer> inVisibleList) {
		this.inVisibleList = inVisibleList;
		ctrl.resetSpread(true);
	}

	/**
	 * 表示される項目のリスト取得
	 * 
	 * @return 表示される項目のリスト
	 */
	public List<Integer> getVisibleList() {
		return visibleList;
	}

	/**
	 * 表示される項目のリスト設定
	 * 
	 * @param visibleList
	 */
	public void setVisibleList(List<Integer> visibleList) {
		this.visibleList = visibleList;
		ctrl.resetSpread(false);
	}

}
