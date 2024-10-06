package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 更新区分一覧コンポーネントコントロール
 */
public class TSlipStateListTableCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "InformationServlet";

	/** フィールド */
	private TSlipStateListTable field;

	/** 更新コードリスト */
	private List<Integer> updateCodeList;

	/** 更新区分ヘッダ */
	public String[] cupdateDivisionlabel;

	/** 表示ラベルマップ */
	private Map<Integer, String> LabelMap;

	/**
	 * ラベルマップを構成
	 */
	private void initMap() {
		LabelMap = new HashMap<Integer, String>();
		LabelMap.put(TSlipStateListTable.INSERT, getWord("C01258"));
		LabelMap.put(TSlipStateListTable.SPOT_ADMIT, getWord("C00157"));
		LabelMap.put(TSlipStateListTable.SPOT_DENY, getWord("C01617"));
		LabelMap.put(TSlipStateListTable.ACCOUNT_ADMIT, getWord("C01616"));
		LabelMap.put(TSlipStateListTable.ACCOUNT_DENY, getWord("C01615"));
		LabelMap.put(TSlipStateListTable.UPDATE, getWord("C00169"));
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field
	 */
	public TSlipStateListTableCtrl(TSlipStateListTable field) {
		this.field = field;
		initMap();
	}

	/**
	 * 更新区分一覧初期化
	 */
	public void setTypeUpdateInit() {
		try {
			String kaiCode = field.getCompanyCode();
			boolean isUpdateKbn = field.getIncludeUpdate();

			// 更新区分ヘッダ
			cupdateDivisionlabel = new String[] { "", getWord("C01069") };

			// 会社コントロールマスタの取得
			Map map = getCmpInfo(kaiCode);

			boolean bol = BooleanUtil.toBoolean(Util.avoidNull(map.get("FLAG")));
			if (!bol) {
				errorHandler(field, "W01002");
				return;
			}

			// 現場承認フラグの取得
			String g_shonin = Util.avoidNull(map.get("G_SHONIN_FLG"));
			// 経理承認フラグの取得
			String k_shonin = Util.avoidNull(map.get("K_SHONIN_FLG"));

			int[] updateCode;

			// 現場承認フラグ１：使用する 経理承認必須
			if ("1".equals(g_shonin)) {
				// 更新表示
				if (isUpdateKbn) {
					updateCode = new int[] { 1, 2, 3, 11, 12, 4 };
				}
				// 更新非表示
				else {
					updateCode = new int[] { 1, 2, 3, 11, 12 };
				}
			}
			// 現場承認フラグ０：使用しない
			else {
				// 経理承認フラグ１：使用する
				if ("1".equals(k_shonin)) {
					// 更新表示
					if (isUpdateKbn) {
						updateCode = new int[] { 1, 3, 12, 4 };
					}
					// 更新非表示
					else {
						// 更新区分名
						updateCode = new int[] { 1, 3, 12 };
					}
				}
				// 経理承認フラグ０：使用しない
				else {
					// 更新表示
					if (isUpdateKbn) {
						// 更新区分名
						updateCode = new int[] { 1, 4 };
					}
					// 更新非表示
					else {
						// 更新区分名
						updateCode = new int[] { 1 };
					}
				}
			}

			updateCodeList = new LinkedList<Integer>();
			// コード配列をリストに置き換える
			for (int code : updateCode) {
				updateCodeList.add(code);
			}

			setSpreadInfo();

		} catch (TRequestException e) {
			errorHandler(field);
		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}
	}

	/**
	 * スプレッドシートを再構成する
	 * 
	 * @param isDelete true : 削除 false = 追加
	 */
	public void resetSpread(boolean isDelete) {
		// 削除処理
		if (isDelete) {
			if (field.getInVisibleList() != null) {
				List<Integer> list = new LinkedList<Integer>();
				for (int code : updateCodeList) {
					if (!isSelected(code, field.getInVisibleList())) {
						list.add(code);
					}
				}
				updateCodeList = list;
				setSpreadInfo();
			}
		}

		// 追加処理
		else {
			if (field.getVisibleList() != null) {
				for (int code : field.getVisibleList()) {
					if (!isSelected(code, updateCodeList)) {
						updateCodeList.add(code);
					}
				}
				setSpreadInfo();
			}
		}
	}

	/**
	 * 外部からの設定と渡されたコードとの比較を行う
	 * 
	 * @param code
	 * @param list
	 * @return 一致するコードが存在するとtrueを返す
	 */
	private boolean isSelected(int code, List<Integer> list) {
		for (int setCode : list) {
			if (setCode == code) {
				return true;
			}
		}
		return false;
	}

	/**
	 * データ設定
	 */
	private void setSpreadInfo() {
		// スプレッドシートDataSource
		JCVectorDataSource ds = new JCVectorDataSource();
		Vector<Vector> updateCells = new Vector<Vector>();

		for (int i = 0; i < updateCodeList.size(); i++) {
			Vector<Object> updateColum = new Vector<Object>(3);
			updateColum.add(0, "");
			updateColum.add(1, LabelMap.get(updateCodeList.get(i)));
			updateColum.add(2, updateCodeList.get(i));

			updateCells.add(i, updateColum);
		}

		ds.setColumnLabels(cupdateDivisionlabel); // タイトル
		ds.setNumRows(updateCodeList.size()); // 行数
		ds.setNumColumns(2);
		ds.setCells(updateCells);

		// データを反映
		field.setDataSource(ds);

		// チェックボックスを0カラムに配置
		field.setCheckBoxComponents();
	}

	/**
	 * 会社コントロールマスタ取得
	 * 
	 * @param kaiCode 会社コード
	 * @return 結果
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Map getCmpInfo(String kaiCode) throws IOException, TRequestException {

		// 送信するパラメータを設定
		addSendValues("FLAG", "CmpInfo");
		// 会社コード
		addSendValues("KAI_CODE", kaiCode);

		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		return super.getResult();
	}
}
