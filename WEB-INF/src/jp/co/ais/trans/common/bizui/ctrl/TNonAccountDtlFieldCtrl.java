package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 非会計明細フィールドコントロール
 */
public class TNonAccountDtlFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "InformationServlet";

	/** パネル */
	protected TNonAccountDtlField panel;

	/**
	 * コンストラクタ
	 * 
	 * @param panel パネル
	 */
	public TNonAccountDtlFieldCtrl(TNonAccountDtlField panel) {
		this.panel = panel;
	}

	/**
	 * 会社情報取得
	 * 
	 * @return CMP_MSTエンティティ
	 */
	public CMP_MST getCmpData() {

		CMP_MST cmp = new CMP_MST();

		try {
			// フラグ
			addSendValues("FLAG", "CmpInfo");
			// 会社コード
			if (Util.isNullOrEmpty(panel.getCompanyCode())) {
				panel.setCompanyCode(super.getLoginUserCompanyCode());
			}

			addSendValues("KAI_CODE", panel.getCompanyCode());

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
			}

			// データ取得
			Map<String, String> map = getResult();
			cmp.setCMP_HM_KBN_1(Util.avoidNullAsInt(map.get("CMP_HM_KBN_1")));
			cmp.setCMP_HM_KBN_2(Util.avoidNullAsInt(map.get("CMP_HM_KBN_2")));
			cmp.setCMP_HM_KBN_3(Util.avoidNullAsInt(map.get("CMP_HM_KBN_3")));

			cmp.setCMP_HM_NAME_1(Util.avoidNull(map.get("CMP_HM_NAME_1")));
			cmp.setCMP_HM_NAME_2(Util.avoidNull(map.get("CMP_HM_NAME_2")));
			cmp.setCMP_HM_NAME_3(Util.avoidNull(map.get("CMP_HM_NAME_3")));

		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
		}

		return cmp;
	}

	/**
	 * 日付のセット
	 * 
	 * @param comp カレンダー
	 * @param value 値
	 */
	public void setToDateComp(TLabelPopupCalendar comp, String value) {
		try {
			if (comp.getCalendarType() == TPopupCalendar.TYPE_YMDHM) {
				comp.setValue(DateUtil.toYMDHMDate(Util.avoidNull(value)));
			} else {
				comp.setValue(DateUtil.toYMDDate(Util.avoidNull(value)));
			}
		} catch (ParseException e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
		}
	}
}
