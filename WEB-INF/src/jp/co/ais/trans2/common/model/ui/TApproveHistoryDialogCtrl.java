package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.history.ApproveHistory.SYO_FLG;

/**
 * 承認履歴ダイアログCtrl
 */
public class TApproveHistoryDialogCtrl extends TController {

	/** 承認履歴ダイアログ */
	protected TApproveHistoryDialog dialog = null;

	/**
	 * コンストラクター
	 * 
	 * @param dialog
	 */
	public TApproveHistoryDialogCtrl(TApproveHistoryDialog dialog) {
		this.dialog = dialog;

		addEvents();
	}

	/**
	 * イベント追加
	 */
	protected void addEvents() {

		dialog.btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
	}

	/**
	 * データ設定
	 * 
	 * @param list
	 */
	public void setData(List<ApproveHistory> list) {

		dialog.tbl.removeRow();

		if (list == null) {
			return;
		}

		for (ApproveHistory bean : list) {
			dialog.tbl.addRow(getRow(bean));
		}

	}

	/**
	 * @param bean
	 * @return 行データ
	 */
	protected List getRow(ApproveHistory bean) {

		String syoFlag = "";

		switch (bean.getSYO_FLG()) {
			case SYO_FLG.APPROVE:
				syoFlag = getWord("C01168"); // 0:承認
				break;
			case SYO_FLG.DENY:
				syoFlag = getWord("C00447"); // 1:否認
				break;
			case SYO_FLG.CANCEL:
				syoFlag = getWord("C00405"); // 2:取消
				break;
			case SYO_FLG.ENTRY:
				syoFlag = getWord("C01258"); // 3:登録
				break;
			case SYO_FLG.MODIFY:
				syoFlag = getWord("C01760"); // 4:修正
				break;
			case SYO_FLG.UPDATE:
				syoFlag = getWord("C00169"); // 5:更新
				break;
		}

		List<Object> list = new ArrayList<Object>();
		list.add(bean.getKAI_CODE());
		list.add(DateUtil.toYMDString(bean.getSWK_DEN_DATE()));
		list.add(bean.getSWK_DEN_NO());
		list.add(getWord(SlipState.getSlipStateName(bean.getSWK_UPD_KBN())));
		list.add(bean.getEMP_CODE());
		list.add(bean.getEMP_NAME());
		list.add(bean.getEMP_NAME_S());
		list.add(getWord(syoFlag));
		list.add(DateUtil.toYMDHMSString(bean.getINP_DATE()));
		return list;
	}
}
