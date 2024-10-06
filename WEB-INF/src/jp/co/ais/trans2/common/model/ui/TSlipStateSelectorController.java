package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 伝票のステータス選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TSlipStateSelectorController extends TController {

	/** フィールド */
	protected TSlipStateSelector field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TSlipStateSelectorController(TSlipStateSelector field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	public void init() {

		boolean isDefaultOn = ClientConfig.isFlagOn("trans.report.slip.state.default.on");

		field.chkEntry.setSelected(isDefaultOn);
		field.chkApprove.setSelected(isDefaultOn);

		// 承認を使わない場合、承認のチェックボックスは非表示
		if (!getCompany().getAccountConfig().isUseApprove() && !getCompany().getAccountConfig().isUseFieldApprove()) {
			field.chkApprove.setSelected(false);
			field.chkApprove.setVisible(false);
		}

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 登録を含むか
	 * 
	 * @return 登録を含むか
	 */
	public boolean isEntry() {
		return field.chkEntry.isSelected();
	}

	/**
	 * 登録を含むか設定
	 * 
	 * @param isEntry 登録を含むか
	 */
	public void setEntry(boolean isEntry) {
		field.chkEntry.setSelected(isEntry);
	}

	/**
	 * 承認を含むか
	 * 
	 * @return 承認を含むか
	 */
	public boolean isApprove() {
		return field.chkApprove.isSelected();
	}

	/**
	 * 承認を含むか設定
	 * 
	 * @param isApprove 承認を含むか
	 */
	public void setApprove(boolean isApprove) {
		field.chkApprove.setSelected(isApprove);
	}

}
