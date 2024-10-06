package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 科目レベルフィールドのコントロール
 * 
 * @author roh
 */
public class TItemLevelFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	/** 科目パネル */
	private TItemLevelField field;

	/**
	 * コンストラクタ
	 * 
	 * @param panel
	 */
	public TItemLevelFieldCtrl(TItemLevelField panel) {
		try {
			this.field = panel;

			initControl();
		} catch (Exception e) {
			errorHandler(panel, e, "E00010");
		}
	}

	/**
	 * 画面表示構成
	 */
	public void initControl() {
		try {

			// 会社コントロール情報習得
			List<List<String>> list = getFieldDataList();

			if (list.isEmpty()) {
				return;
			}

			// 一行目のROW
			List<String> dataList = list.get(0);

			field.getItemButton().setText(dataList.get(0));
			field.getSubItemButton().setText(dataList.get(1));
			field.getBreakDownItemButton().setText(dataList.get(3));

			// 内訳表示、非表示
			field.getBreakDownItemButton().setVisible(BooleanUtil.toBoolean(dataList.get(2)));

		} catch (TException e) {
			errorHandler(field, e);
		}
	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @return List 科目レベル詳細リスト
	 * @throws TException
	 */
	private List<List<String>> getFieldDataList() throws TException {

		String buttonName = "refControl";

		try {
			// 送信するパラメータを設定
			addSendValues("flag", buttonName); // 区分flag
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}

			if (!request(TARGET_SERVLET)) {
				// クライアント 受信解析エラー。
				errorHandler(field, "S00002");
			}

			return getResultList();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}
}
