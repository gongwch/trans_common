package jp.co.ais.trans.common.bizui.ctrl;

import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 伝票種別フィールドのコントロール
 * 
 * @author roh
 */
public class TSlipKindListTableCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0330SlipTypeMasterServlet";

	/** フィールド */
	private TSlipKindListTable field;

	/** 伝票種別ヘッダ */
	public String[] cslipTypelabel = new String[] { "", getWord("C00917") };

	/**
	 * コンストラクタ
	 * 
	 * @param itemField フィールド
	 */
	public TSlipKindListTableCtrl(TSlipKindListTable itemField) {
		try {
			this.field = itemField;

			setSpreadSheet();
		} catch (Exception e) {
			errorHandler(field, e);
		}

	}

	/**
	 * データシート設定 サーブレットからListを習得
	 */
	public void setSpreadSheet() {
		try {

			// 送信するパラメータを設定
			addSendValues("flag", "refSlip");

			// 会社コード
			addSendValues("kaiCode", field.getCompanyCode());
			addSendValues("isIncludeSystemEls", BooleanUtil.toString(field.isIncludeSystemElse()));

			if (!request(TARGET_SERVLET)) {
				// クライアント 受信解析エラー。
				errorHandler(field, "S00002");
			}

			// サーブレットから送られてきた結果を配列にセット
			List listDenSyu = super.getResultDtoList(DEN_SYU_MST.class);

			// データ区分が設定されていると
			if (field.getKbnCodeList() != null) {
				List copyedList = new LinkedList();
				for (Object densyu : listDenSyu) {
					DEN_SYU_MST denMst = (DEN_SYU_MST) densyu;
					if (isDataKbnExist(denMst.getDATA_KBN())) {
						copyedList.add(densyu);
					}
				}
				listDenSyu = copyedList;
			}

			// 伝票種別初期化
			this.setDataSource(listDenSyu);
		} catch (Exception e) {
			errorHandler(field, e);
		}
	}

	/**
	 * 指定のコードは設定したデータ区分にあるかどうかを判明
	 * 
	 * @param dataKbn
	 * @return boolean
	 */
	private boolean isDataKbnExist(String dataKbn) {
		for (String setterCode : field.getKbnCodeList()) {
			if (dataKbn != null) {
				if (dataKbn.equals(setterCode)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 伝票種別一覧初期化
	 * 
	 * @param list 種別リスト
	 */
	private void setDataSource(List<DEN_SYU_MST> list) {

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		for (DEN_SYU_MST bean : list) {
			Vector<Object> colum = new Vector<Object>();

			colum.add(0, "");
			// 種別コード：種別略称
			colum.add(1, bean.getDEN_SYU_CODE() + ":" + bean.getDEN_SYU_NAME_S());
			// データ区分
			colum.add(2, bean);

			cells.add(colum);
		}

		// SSデータの構築
		JCVectorDataSource ds = new JCVectorDataSource();

		ds.setColumnLabels(cslipTypelabel); // カラムラベル
		ds.setNumColumns(2); // カラム数
		ds.setNumRows(cells.size()); // 行数
		ds.setCells(cells);

		// データ羽意
		field.setDataSource(ds);

		// チェックボックスコンポーネントの配置
		field.setCheckBoxComponents();
	}
}
